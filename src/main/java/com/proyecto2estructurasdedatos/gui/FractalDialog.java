package com.proyecto2estructurasdedatos.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * @author sebas
 */
class Mandelbrot extends JPanel {
	Mandelbrot(int scrWidth, int scrHeight) {
		this.setSize(scrWidth, scrHeight);
		this.setPreferredSize(new Dimension(scrWidth, scrHeight));
		this.setMinimumSize(new Dimension(scrWidth, scrHeight));

		this.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
		});

		this.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent e) {
			}

			@Override
			public void mouseMoved(MouseEvent e) {
			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
	}
}

/**
 *@author sebas
 */
public class FractalDialog {
	FractalDialog() {
		var width = 800;
		var height = 600;
		var dialog = new JDialog();
		dialog.setModal(true);
		dialog.setResizable(false);

		var game = new Mandelbrot(width, height);

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
