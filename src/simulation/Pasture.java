/*
* Name; Ethan Janovitz
* Due: April 14th
* Description: Places the animals, plants and allows them to eat stuff and move around
 */

package simulation;

import java.util.ArrayList;
import java.util.Random;

import entities.*;

/*
 * Pasture
 * - maintains an array of the field
 * - provides functionality for Entity Objects to interact with the array
 *   (movement, determining nearby objects, etc.)  
 */
public class Pasture {

	/*------------FIELDS-------------*/
	private final Entity[][][] entities;// 3 Dimensional array to represent pasture
	private final int NUM_COLS;
	private final int NUM_ROWS;
	private final int ANIMAL = 0;		// Animals are on Level 0 of the entities array
	private final int PLANT = 1;		// Grass is on Level 1 of the entities array



	/**
	 * Constructor
	 *
	 * @param NUM_ROWS (int)
	 * @param NUM_COLS (int)
	 */
	public Pasture(int NUM_ROWS, int NUM_COLS, int NUM_LEVELS) {
		this.NUM_ROWS = NUM_ROWS;
		this.NUM_COLS = NUM_COLS;
		entities = new Entity[NUM_ROWS][NUM_COLS][NUM_LEVELS];

		/*----------POPULATION PARAMETERS-----------*/
		final int NUM_SHEEP = 70;		// starting Sheep population
		final int NUM_WOLVES = 5;		// Starting Wolf population
		final int NUM_GRASS = 175;		// Starting Grass population

		// initialize Sheep population
		for (int i = 0; i < NUM_SHEEP; i++) {
			placeEntity(new Sheep(this));	// place a Sheep
		}

		// initialize Wolf population
		for (int i=0; i < NUM_WOLVES; i++) {
			placeEntity(new Wolf(this));	// place a Wolf
		}

		// initialize Grass population
		for (int i=0; i < NUM_GRASS; i++) {
			placeEntity(new Grass(this));	// place Grass
		}
	}

	/*
	 *  Determine if a space is already taken by an Entity
	 */
	private boolean isOccupied(Entity e, int row, int col) {
		if (e instanceof Animal) {
			return entities[row][col][ANIMAL] != null;	// Check the Animal level if entity is an animal
		} else {
			return entities[row][col][PLANT] != null;	// If not an animal check the Plant Level
		}
	}

	// Checks to see if a sheep is occupying a space
	private boolean isSheep(int row, int col) {
		return entities[row][col][ANIMAL] instanceof Sheep;
	}

	/*
	 * Methods to determine if a specific row or column is valid
	 */
	private boolean inRowRange(int r) {
		return r >= 0 && r < NUM_ROWS;
	}

	private boolean inColRange(int c) {
		return c >= 0 && c < NUM_COLS;
	}

	/*
	 * Add an Entity to the array
	 * - highly inefficient for large populations!
	 * - will freeze the program if no spaces remain!
	 */
	private void placeEntity(Entity e) {
		Random rand = new Random();
		// picks random rows and columns
		int row = rand.nextInt(NUM_ROWS);
		int col = rand.nextInt(NUM_COLS);

		// Find an empty spot
		while (this.isOccupied(e, row, col)) {
			row = rand.nextInt(NUM_ROWS);
			col = rand.nextInt(NUM_COLS);
		}

		//Place Entity in location (row, col)
		e.setRow(row);
		e.setCol(col);

		if (e instanceof Animal) {
			entities[row][col][ANIMAL] = e;
		} else {
			entities[row][col][PLANT] = e;
		}
	}

	/**
	 * Moves an Entity to a new location
	 * - Note: not currently checking for valid moves
	 *
	 * @param e Entity to be moved
	 * @param newLoc int[row, col]
	 */
	public void moveEntity(Entity e, int[] newLoc) {
		// Temporarily store the previous row and column
		int oldRow = e.getRow();
		int oldCol = e.getCol();

		// Update this Entity's row and column to the new location
		e.setRow(newLoc[0]);
		e.setCol(newLoc[1]);

		/* Move the Entity to the new location and set its old location to null
		 * - If the entity is a sheep, it will have eaten the grass, so set the old
		 * - location in the Grass Level to null, and the old location in the
		 * - Animal Level.
		 *  - If the entity is not a sheep (wolf), leave the grass entity in place
		 *  - as the wolf would not have eaten the grass
		 */
		entities[e.getRow()][e.getCol()][ANIMAL] = e;
		if (e instanceof Sheep) {
			entities[oldRow][oldCol][PLANT] = null;
		}
		entities[oldRow][oldCol][ANIMAL] = null;

	}

	boolean isAlive(Entity e) {
		return e.equals(entities[e.getRow()][e.getCol()][ANIMAL]);
	}

	public void growEntity(int[] newLoc) {

		// Create a new grass entity and
		// Update the entities row and column to the new location
		Grass newGrass = new Grass(this);
		newGrass.setRow(newLoc[0]);
		newGrass.setCol(newLoc[1]);

		// Added the newly grown grass to the new location on the Plant Level
		entities[newGrass.getRow()][newGrass.getCol()][PLANT] = newGrass;
	}

	/*
	 * Check if the location moving to, contains an entity that is edible to the
	 * current entity. For a wolf that would be a sheep and for a sheep it would
	 * be grass
	 */
	public boolean checkEdible(Animal e, int[] newLoc) {
		int row = newLoc[0];
		int col = newLoc[1];

		if (e instanceof Wolf && entities[row][col][ANIMAL] instanceof Sheep) {
			return true;
		} else return e instanceof Sheep && entities[row][col][PLANT] instanceof Grass;
	}

	// Remove and Animal from the pasture, trigger by hunger and age
	public void removeAnimal(Entity e) {
		int row = e.getRow();
		int col = e.getCol();

		entities[row][col][ANIMAL] = null;
	}

	// Add an Animal to the pasture, trigger by births
	public void addAnimal(Entity e) {
		if (e instanceof Wolf) {
			placeEntity(new Wolf(this));
		} else {
			placeEntity(new Sheep(this));
		}
	}

	/*
	 * Returns a List of all Entity objects in the array
	 *
	 * @return ArrayList<Entity>
	 */
	ArrayList<Entity> getEntities() {
		ArrayList<Entity> a = new ArrayList<>();
		Random rand = new Random();

		for (int row = 0; row < NUM_ROWS; row++) {
			for (int col = 0; col < NUM_COLS; col++) {
				Entity animal = entities[row][col][ANIMAL];
				Entity plant = entities[row][col][PLANT];
				if (animal != null) {
					/*
					 * Add Animal to a random location to remove bias
					 * p is a random index location in the array
					 * if an entity already exists at the location, all entities
					 * will be shifted to the right
					 */
					int p = rand.nextInt(a.size() + 1);
					a.add(p, animal);
				} else if (plant != null) {
					// Add plant to a random location to remove bias
					int p = rand.nextInt((a.size() + 1));
					a.add(p, plant);
				}
			}
		}
		return a;
	}

	/**
	 * Returns a List of empty positions in the 8 adjacent cells to the target Entity
	 *
	 * @param e Entity checking for locations in adjacent positions
	 * @return ArrayList<int[row, col]> of positions containing no Entities
	 */
	public ArrayList<int[]> getFreePositions(Entity e) {

		ArrayList<int[]> a = new ArrayList<>();

		int row = e.getRow();
		int col = e.getCol();

		for(int r = row - 1; r <= row + 1; r++) {
			for(int c = col - 1; c <= col + 1; c++) {
				if (inRowRange(r) && inColRange(c) && !(r == row && c == col) && !isOccupied(e, r, c)) {
					int[] i = {r,c};
					a.add(i);
				}
			}
		}
		return a;
	}

	//gets the sheeps locations
	public ArrayList<int[]> getSheepPositions(Entity e) {

		ArrayList<int[]> a = new ArrayList<>();

		int row = e.getRow();
		int col = e.getCol();

		for(int r = row - 1; r <= row + 1; r++) {
			for(int c = col - 1; c <= col + 1; c++) {
				if (inRowRange(r) && inColRange(c) && !(r == row && c == col) && isSheep(r, c)) {
					int[] i = {r,c};
					a.add(i);
				}
			}
		}
		return a;
	}
}