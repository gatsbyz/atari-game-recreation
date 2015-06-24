import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;


public class LandMenu extends BuildingMenu{
	private JButton sellButton, buyButton;
	private JLabel priceLabelBuy, priceLabelSell, howToLabel;
	
	public LandMenu(Color background, Color border, JPanel parent) {
		super("Land Office", background, border, true, parent);

		initLandMenu();
	}

	private void initLandMenu() {
		priceLabelBuy = new JLabel();
		priceLabelBuy.setBounds(getX(), getY(), getWidth() / 2, getHeight() - 15);
		priceLabelBuy.setForeground(Color.WHITE);
		priceLabelBuy.setHorizontalAlignment(JLabel.CENTER);
		priceLabelBuy.setVerticalAlignment(JLabel.CENTER);
		priceLabelBuy.setOpaque(false);
		priceLabelBuy.setFont(new Font("Narkisim", Font.BOLD, 18));
		
		priceLabelSell = new JLabel();
		priceLabelSell.setBounds(getX() + getWidth() / 2, getY(), getWidth() / 2, getHeight() - 15);
		priceLabelSell.setForeground(Color.WHITE);
		priceLabelSell.setHorizontalAlignment(JLabel.CENTER);
		priceLabelSell.setVerticalAlignment(JLabel.CENTER);
		priceLabelSell.setOpaque(false);
		priceLabelSell.setFont(new Font("Narkisim", Font.BOLD, 18));

		howToLabel = new JLabel("Click a button above, then click a plot to buy or sell!");
		howToLabel.setBounds(getX(), getY(), getWidth(), getHeight());
		howToLabel.setForeground(Color.WHITE);
		howToLabel.setHorizontalAlignment(JLabel.CENTER);
		howToLabel.setVerticalAlignment(JLabel.BOTTOM);
		howToLabel.setOpaque(false);
		howToLabel.setFont(new Font("Narkisim", Font.BOLD, 14));
		
		sellButton = new JButton("Sell Land");
		sellButton.setBounds(getX() + getWidth() / 2 + 50, getY() + 140, 200,
				27);
		sellButton.setBackground(new Color(255, 255, 255, 200));
		sellButton.setFocusable(false);
		
		buyButton = new JButton("Buy Land");
		buyButton.setBounds(getX() + 50, getY() + 140, 200,
				27);
		buyButton.setBackground(new Color(255, 255, 255, 200));
		buyButton.setFocusable(false);
	}
	
	public void displayLandMenu(int buy, int sell){
		priceLabelBuy.setText("Buy land for $" + Integer.toString(buy) + "!");
		priceLabelSell.setText("Sell land for $" + Integer.toString(sell) + "!");
		parentPanel.add(welcomeMessage);
		parentPanel.add(priceLabelBuy);
		parentPanel.add(priceLabelSell);
		parentPanel.add(howToLabel);
		parentPanel.add(sellButton);
		parentPanel.add(buyButton);
	}
	
	public void removeLandMenu(){
		parentPanel.remove(welcomeMessage);
		parentPanel.remove(priceLabelBuy);
		parentPanel.remove(priceLabelSell);
		parentPanel.remove(howToLabel);
		parentPanel.remove(sellButton);
		parentPanel.remove(buyButton);
	}
	
	public JButton getSellButton(){
		return sellButton;
	}
	
	public JButton getBuyButton(){
		return buyButton;
	}
}