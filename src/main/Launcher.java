package main;

import net.dv8tion.jda.core.AccountType;
import structures.LewdBot;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Launcher {
	private static LewdBot session = null;
	private static int width;
	private static int height;

	private JPanel Main;
	private JPanel background;
	private JButton LOGINButton;
	private JButton YIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIFFFFFFFFFFFFFFFFButton;
	private JButton GTFOButton;
	private JTextPane reasonsWhyLucarioIsTextPane;
	private JPasswordField passwordField1;

	public static class JPanelWithBackground extends JPanel {
		private Image backgroundImage;
		JPanelWithBackground(String fileName) throws IOException {
			backgroundImage = ImageIO.read(new File(fileName));
			Launcher.width = backgroundImage.getWidth(this);
			Launcher.height = backgroundImage.getHeight(this);
		}
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(backgroundImage, 0, 0, this);
		}
	}

	private void createUIComponents() {
		try {
			background = new Launcher.JPanelWithBackground("./src/assets/bg.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
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
			// Unavailable look and feel
		}
	}

	private Launcher() {
		createUIComponents();
		LOGINButton.addActionListener(e -> {
			if (Launcher.session != null) System.err.println("Already logged in!");
			else Launcher.session = new LewdBot(AccountType.BOT);
		});
		GTFOButton.addActionListener(e -> System.exit(0));
	}

	private static void init() {
		Launcher window = new Launcher();
		window.Main.setPreferredSize(new Dimension(Launcher.width, Launcher.height));
		setAppearance();
		JFrame frame = new JFrame("Lewd Bot");
		frame.setIconImage(new ImageIcon("./src/assets/icon.png").getImage());
		frame.setContentPane(window.Main);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		init();
	}
}

