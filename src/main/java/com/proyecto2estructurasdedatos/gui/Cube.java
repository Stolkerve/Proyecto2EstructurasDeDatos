package com.proyecto2estructurasdedatos.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * @author sebas
 */
class CubeCanvas extends JPanel {
	int width = 800;
	int height = 600;
	CubeCanvas() {
		this.setSize(width, height);
		this.setPreferredSize(new Dimension(width, height));
		this.setMinimumSize(new Dimension(width, height));
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
	}
}

/**
 * @author sebas
 */
public class Cube {
	Cube() {
		var dialog = new JDialog();
		dialog.setModal(true);
		dialog.setResizable(false);

		var game = new CubeCanvas();

		var al = new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				game.repaint();
			}
		};
		var timer = new Timer(50, al);// create the timer which calls the actionperformed method for every 1000
										// millisecond(1 second=1000 millisecond)
		timer.setRepeats(true);
		timer.start();

		dialog.add(game);

		dialog.pack();
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
	}
}
