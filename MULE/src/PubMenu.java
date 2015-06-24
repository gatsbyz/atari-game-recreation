import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class PubMenu extends BuildingMenu {
	private JLabel label;
	private JLabel turnOver;

	public PubMenu(Color background, Color border, JPanel parent) {
		super("Varsity", background, border, false, parent);

		initPub();
	}

	private void initPub() {
		label = new JLabel();
		label.setBounds(getX(), getY(), getWidth(), getHeight());
		label.setForeground(Color.BLUE);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setVerticalAlignment(JLabel.CENTER);
		label.setOpaque(false);
		label.setFont(new Font("Narkisim", Font.BOLD, 18));

		turnOver = new JLabel();
		turnOver.setBounds(getX(), getY(), getWidth(), getHeight());
		turnOver.setForeground(Color.WHITE);
		turnOver.setHorizontalAlignment(JLabel.CENTER);
		turnOver.setVerticalAlignment(JLabel.BOTTOM);
		turnOver.setOpaque(false);
		turnOver.setFont(new Font("Narkisim", Font.BOLD, 14));
	}

	public void displayPub(Player player, int money) {
		label.setText("<html><div style=\"text-align: center;\">"
				+ player.getName() + " received $" + money
				+ " by gambling.<br>Now " + player.getName() + " has $"
				+ player.getMoney() + "</html>");
		turnOver.setText("<html><div style=\"text-align: center;\">"
				+ player.getName() + "'s turn is over!" + "</html>");
		getParentPanel().add(label);
		getParentPanel().add(turnOver);
		getParentPanel().add(getWelcomeMessage());
	}

	public void removePub() {
		getParentPanel().remove(label);
		getParentPanel().remove(turnOver);
		getParentPanel().remove(getWelcomeMessage());
	}
}
