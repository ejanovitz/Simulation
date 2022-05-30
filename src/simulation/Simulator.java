/*
 * Name; Ethan Janovitz
 * Due: April 14th
 * Description: sets the rows and columns and paints stuff on the screen
 */

package simulation;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;

import javax.swing.*;

import entities.Entity;
import entities.Grass;

/*
 * Simulator
 * - Creates the GUI
 * - ticks every SPEED milliseconds 
 */

@SuppressWarnings("serial")
public class Simulator extends JPanel implements ActionListener {

	/*----------PARAMETERS-----------*/
	private final int NUM_ROWS = 100;
	private final int NUM_COLS = 120;
	private final Color DIRT = new Color(139,69,19);  // ground colour

	/*------------FIELDS-------------*/
	Pasture pasture;
	Timer time;

	/**
	 * Constructor
	 * - Create a new Pasture and initialize timer.
	 * - Adjust animation speed by changing SPEED field above
	 */
	public Simulator() {

		/* Populate a new world
		 * - Level 0 is the Animal Level
		 * - Level 1 is the Grass Level
		 */
		this.pasture = new Pasture(NUM_ROWS, NUM_COLS, 2);

		//Start the simulation
		final int SPEED = 100;	// 0 (fast) --> 1000 (slow)
		time = new Timer(SPEED, this);
		time.start();
	}

	/*
	 * Getters
	 */

	public int getNUM_COLS() {
		return NUM_COLS;
	}

	public int getNUM_ROWS() {
		return NUM_ROWS;
	}


	/**
	 * Update each Entity in the Pasture
	 */
	@Override
	public void actionPerformed(ActionEvent e1) {
		//1 unit of time passes
		ArrayList<Entity> list = pasture.getEntities();

		for(Entity e:list) {
			if(e instanceof Grass) e.tick();
			else if (pasture.isAlive(e)) e.tick();
		}

		//Redraw pasture
		repaint();
	}

	/**
	 * Redraw Screen
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		//Draw the ground
		g.setColor(DIRT);
		g.fillRect(0, 0, NUM_COLS * 10, NUM_ROWS * 10);

		//Draw the entities
		ArrayList<Entity> a = pasture.getEntities();

		for(Entity e:a) {
			g.setColor(e.getColor());
			g.fillRect(e.getCol()*10, e.getRow()*10, 10, 10);
		}
	}
}