package entities;

import simulation.Pasture;

/*
 * Name; Ethan Janovitz
 * Due: April 14th
 * Description: sets up the grass
 */

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Grass extends Entity{

    private int delay;  // number of ticks between moves

    /**
     * Constructor
     *
     * @param pasture Reference to the object that created this Entity
     */
    public Grass(Pasture pasture) {
        super(pasture, 0, 0,new Color(54, 148, 37));

        // Assign a random delay before next move
        Random rand = new Random();
        delay = rand.nextInt(200)+1;
    }

    /*
     *  Timing method
     *  - Dummy moves once every 1-5 ticks (int delay)
     */
    @Override
    public void tick() {
        // Move when delay counts down to 0
        if (delay-- == 0) {
            grow();

            // Assign a random delay before next move
            Random rand = new Random();
            delay = rand.nextInt(200) + 1;
        }
    }

    // allows the grass to grow
    private void grow() {
        Random rand = new Random();

        ArrayList<int[]> a = pasture.getFreePositions(this);
        if (a.size() > 0) {
            int moveTo = rand.nextInt(a.size());  //choose a random location
            int[] newLoc = a.get(moveTo);
            pasture.growEntity(newLoc);

        }

    }
}
