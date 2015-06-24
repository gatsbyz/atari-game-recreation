import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

public abstract class BuildingMenu implements Drawable {

	private final int menuBorderSize = 8;
	private final int xPOS = 150;
	private final int yPOS = 25;
	private final int WIDTH = 600;
	private final int HEIGHT = 250;

	protected Color border, background;
	private boolean divider;

	private String type;
	protected JPanel parentPanel;
	protected JLabel welcomeMessage;

	public BuildingMenu(String type, Color background, Color border,
			boolean divider, JPanel parent) {

		this.type = type;
		this.parentPanel = parent;
		this.background = background;
		this.border = border;
		this.divider = divider;

		welcomeMessage = new JLabel("Welcome to the " + type + "!");
		welcomeMessage.setBounds(xPOS, yPOS + 5, WIDTH, 30);
		welcomeMessage.setHorizontalAlignment(JLabel.CENTER);
		welcomeMessage.setForeground(border);
		welcomeMessage.setFont(new Font("Narkisim", Font.BOLD, 20));
	}

	public int getHeight() {
		return HEIGHT;
	}

	public int getWidth() {
		return WIDTH;
	}

	public int getX() {
		return xPOS;
	}

	public int getY() {
		return yPOS;
	}

	public JLabel getWelcomeMessage() {
		return welcomeMessage;
	}

	public JPanel getParentPanel() {
		return parentPanel;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(background);
		g.fillRect(xPOS, yPOS, WIDTH, HEIGHT);
		g.setColor(border);
		if (divider)
			g.fillRect(xPOS + WIDTH / 2 - 3, yPOS + 60, 6, HEIGHT - 105);
		for (int i = 1; i <= menuBorderSize; i++)
			g.drawRect(xPOS - i, yPOS - i, WIDTH + 2 * i - 1, HEIGHT + 2 * i
					- 1);
	}
}