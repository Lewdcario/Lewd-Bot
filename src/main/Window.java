package main;

import net.dv8tion.jda.core.AccountType;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

import structures.LewdBot;

import javax.swing.*;
import java.awt.*;

public class Window {
	private static LewdBot session = null;
	private JPanel Main;
	private JButton LOGINButton;
	private JButton GTFOButton;
	private JPasswordField passwordField1;
	private JPanel background;
	private JTextPane TOKENBITCHTextPane;
	private static int width;
	private static int height;

	private void createUIComponents() {
		try {
			background = new JPanelWithBackground("./src/assets/bg.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Window() {
		createUIComponents();
		LOGINButton.addActionListener(e -> {
			if (Window.session != null) System.err.println("Already logged in!");
			else Window.session = new LewdBot(AccountType.BOT);
		});
		GTFOButton.addActionListener(e -> System.exit(0));
	}

	private static void init() {
		Window window = new Window();
		window.Main.setPreferredSize(new Dimension(Window.width, Window.height));
		setAppearance();
		JFrame frame = new JFrame("Lewd Bot");
		frame.setIconImage(new ImageIcon("./src/assets/icon.png").getImage());
		frame.setContentPane(window.Main);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	public static class JPanelWithBackground extends JPanel {
		private Image backgroundImage;
		JPanelWithBackground(String fileName) throws IOException {
			backgroundImage = ImageIO.read(new File(fileName));
			Window.width = backgroundImage.getWidth(this);
			Window.height = backgroundImage.getHeight(this);
		}
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(backgroundImage, 0, 0, this);
		}
	}

	public static void main(String[] args) {
		init();
	}

	private static void setAppearance() {
		try {
			for (UIManager.LookAndFeelInfo info: UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		}
		catch (Exception e) {
			// If Nimbus is not available, you can set the GUI to another look and feel.
		}
	}
}
