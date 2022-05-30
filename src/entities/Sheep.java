/*
 * Name; Ethan Janovitz
 * Due: April 14th
 * Description: sets up the sheeps
 */

package entities;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import simulation.Pasture;

/*
 * Dummy
 * - Sample subclass of Entity
 * - You can turn this into one of the assigned classes,
 *   or just keep it as a sample
 */
public class Sheep extends Animal {

	/**
	 * Constructor
	 * 	
	 * @param pasture Reference to the object that created this Entity
	 */
	public Sheep(Pasture pasture) {
		super(pasture, 0, 0,new Color(255,255,255));
	}
}
