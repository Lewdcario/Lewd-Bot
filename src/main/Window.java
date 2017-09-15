package main;

import net.dv8tion.jda.core.AccountType;
import structures.LewdBot;

import javax.swing.*;

public class Window {
	private static LewdBot session = null;
	private JPanel Frame;
	private JButton LOGINButton;
	private JButton LOGTHEFUCKOUTButton;
	private JLabel lewdBotLabel;

	private Window() {
		LOGINButton.addActionListener(e -> {
			if (Window.session != null) System.err.println("Already logged in!");
			else Window.session = new LewdBot(AccountType.BOT);
		});
		LOGTHEFUCKOUTButton.addActionListener(e -> System.exit(0));
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Window");
		frame.setContentPane(new Window().Frame);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
}
