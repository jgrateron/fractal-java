package com.fresco;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class App extends JFrame {
	private static final long serialVersionUID = 1L;

	public App() {
		init();
	}

	public void init() {
		setTitle("Java Fractal");
		setSize(1280, 768);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		var panel = new PanelFractalSierpinski();
		add(panel);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				super.windowOpened(e);
				panel.start();
			}
		});
	}

	public static void main(String[] args) {
		var app = new App();
		app.setVisible(true);
	}

}
