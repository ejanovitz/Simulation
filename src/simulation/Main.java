/*
 * Name; Ethan Janovitz
 * Due: April 14th
 * Description: creates the Window
 */
package simulation;

import javax.swing.JFrame;

public class Main {
	public static void main(String[] args) {
		Simulator s = new Simulator();
		JFrame frame = new JFrame("Pasture");
		frame.setVisible(true);
		frame.setSize(s.getNUM_COLS() * 10 + 16, s.getNUM_ROWS() * 10 + 38);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(s);


	}
}
