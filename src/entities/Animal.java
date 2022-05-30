/*
 * Name; Ethan Janovitz
 * Due: April 14th
 * Description: sets up most of the parameters for the sheep and wolves
 */

package entities;

import simulation.Pasture;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Animal extends Entity {

    private int delay;  // number of ticks between moves
    private int hunger; // Variable to keep tracker of animals hunger
    private int age;    // Variable to keep track of animals age

    /**
     * Constructor
     *
     * @param pasture Reference to the object that created this Entity
     */
    public Animal(Pasture pasture, int row, int col, Color colour) {
        super(pasture, row, col, colour);

        this.hunger = 0;
        this.age = 0;

        // Assign a random delay before next move
        Random rand = new Random();
        delay = rand.nextInt(5)+1;
    }

    @Override
    public void tick() {
        // Move when delay counts down to 0
        if(delay-- == 0) {
            move();
            increaseHunger();
            increaseAge();
            checkHealth();

            // Assign a random delay before next move
            Random rand = new Random();
            delay = rand.nextInt(5)+1;
        }
    }

    /*
     * Get an ArrayList of available locations and pick one randomly
     */
    void move() {
        Random rand = new Random();

        ArrayList<int[]> a = pasture.getFreePositions(this);
        if (a.size() > 0) {
            int moveTo = rand.nextInt(a.size());  //choose a random location
            int[] newLoc = a.get(moveTo);

            if (pasture.checkEdible(this, newLoc)) {
                this.decreaseHunger();
            }
            pasture.moveEntity(this, newLoc);
        }
    }

    /*
     * Deceased Animal
     * - Check the current animal's health
     * - if the hunger level is greater than eighty, remove animal from pasture
     * - if the age is greater than eighty, there is a random chance animal will
     * - die and be removed from the pasture.
     *
     * New Born
     * - if the animal has a hunger less than 20, there is a random chance animal
     * - will give birth
     */
    private void checkHealth() {
        Random rand = new Random();
        if (this.hunger > 80) {                                 // Change to increase or decease death date
            pasture.removeAnimal(this);
            System.out.println("An animal died of hunger");
        }

        int deathChance = rand.nextInt(5);              // Change to increase or decease death rate
        if (this.age > 80 && deathChance == 1){
            pasture.removeAnimal(this);
            System.out.println("An animal died of old age");
        }

        int birthChance = rand.nextInt(20);             // Change to increase or decease birth rate
        if (this.hunger < 20 && birthChance == 1) {
            pasture.addAnimal(this);
        }
    }

    // Increase hunger level by one point
    private void increaseHunger() {
        this.hunger += 1;
        System.out.println(this.hunger);
    }

    // If hunger is greater than zero, decrease hunger by one point
    private void decreaseHunger() {
        if (this.hunger > 0) {
            this.hunger -= 1;
        }
    }

    // Increase age by one point
    private void increaseAge() {
        this.age += 1;
    }
}