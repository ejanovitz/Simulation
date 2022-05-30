/*
 * Name; Ethan Janovitz
 * Due: April 14th
 * Description: sets up the wolves
 */

package entities;

import simulation.Pasture;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Wolf extends Animal {

    /**
     * Constructor
     *
     * @param pasture Reference to the object that created this Entity
     */
    public Wolf(Pasture pasture) {
        super(pasture, 0, 0, new Color(64, 38, 10));
    }

    // allows the wolves to target blocks that are occupied by sheep
    // if there are no sheep then the method calls the move method in the animal super class
    void move() {
        Random rand = new Random();

        ArrayList<int[]> a = pasture.getSheepPositions(this);
        if (a.size() > 0) {
            int moveTo = rand.nextInt(a.size());  //choose a random location
            int[] newLoc = a.get(moveTo);


            pasture.moveEntity(this, newLoc);
        } else {
            super.move();
        }
    }
}
