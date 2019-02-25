package Project2.MineSweeper;


import javax.swing.*;
import java.awt.*;

public class MineSweeperGUI {
	public static void main (String[] args)
	{
		JFrame frame = new JFrame ("Mine Sweeper!");
		frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		MineSweeperPanel panel = new MineSweeperPanel();
		frame.getContentPane().add(panel);
		frame.setSize(400, 400);
		frame.setVisible(true);

		//Sets up the Menu Bar
		JMenuBar menuBar;
		JMenu menu;
		JMenuItem options, quit;

		menuBar = new JMenuBar();

		menu = new JMenu("Menu");
		menuBar.add(menu);
		options = new JMenuItem("Options");
		menu.add(options);
		menu.addSeparator();
		quit = new JMenuItem("Quit");
		menu.add(quit);
		frame.setJMenuBar(menuBar);

		//Closes the application
		quit.addActionListener(e -> System.exit(0));

		options.addActionListener(e -> panel.showOptionPanel());
	}

}

