import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class StoreMenu extends BuildingMenu implements Serializable {
	private JLabel menuLabel, errorMessage;
	private JLabel[] comboBoxLabels;
	private JTextArea[] menuItems;
	private JButton menuButton;
	private ArrayList<JComboBox<String>> comboBoxes;
	private String[][] comboBoxEntries;
	private Store store;
	private Player activePlayer;

	public StoreMenu(Color background, Color border, JPanel parent) {

		super("Store", background, border, true, parent);

		initStoreMenu();
	}

	public void setStore(Store store) {
		this.store = store;
	}

	private void initStoreMenu() {

		menuLabel = new JLabel("Todays Deals");
		menuLabel.setBounds(getX(), getY() + 40, getWidth() / 2 - 40, 20);
		menuLabel.setHorizontalAlignment(JLabel.CENTER);
		menuLabel.setForeground(Color.WHITE);
		menuLabel.setFont(new Font("Narkisim", Font.BOLD, 15));

		errorMessage = new JLabel("");
		errorMessage.setBounds(getX(), getY() + getHeight() - 30, getWidth(),
				20);
		errorMessage.setHorizontalAlignment(JLabel.CENTER);
		errorMessage.setForeground(Color.RED);
		errorMessage.setFont(new Font("Narkisim", Font.BOLD, 12));

		JComboBox<String> tmp;
		comboBoxes = new ArrayList<JComboBox<String>>();
		comboBoxEntries = new String[3][];
		comboBoxEntries[0] = new String[] { "Buy", "Sell" };
		comboBoxEntries[1] = new String[] { "Food", "Energy", "Smithore",
				"Mules" };
		comboBoxEntries[2] = new String[] { "Food Mule", "Energy Mule",
				"Smithore Mule" };
		for (int i = 0; i < 3; i++) {
			if (i < 2) {
				tmp = new JComboBox<String>(comboBoxEntries[i]);
				tmp.addActionListener(new StoreMenuListener());
				tmp.setFocusable(false);
			} else {
				tmp = new JComboBox<String>();
				tmp.addItem("0");
				tmp.setFocusable(false);
			}
			tmp.setBounds(getX() + getWidth() / 2 + 20, getY() + 60 + 30 * i,
					144, 27);
			comboBoxes.add(tmp);
		}

		comboBoxLabels = new JLabel[3];
		for (int i = 0; i < comboBoxLabels.length; i++) {
			comboBoxLabels[i] = new JLabel();
			comboBoxLabels[i].setBounds(getX() + getWidth() / 2 + 170, getY()
					+ 60 + 30 * i, 100, 27);
			comboBoxLabels[i].setForeground(Color.white);
			comboBoxLabels[i].setFont(new Font("Narkisim", Font.BOLD, 10));
		}
		comboBoxLabels[0].setText("Buy or Sell?");
		comboBoxLabels[1].setText("Which Resource?");
		comboBoxLabels[2].setText("How Many?");

		menuItems = new JTextArea[comboBoxEntries[1].length + 4];
		for (int i = 0; i < menuItems.length; i++) {
			menuItems[i] = new JTextArea();
			menuItems[i].setForeground(Color.WHITE);
			menuItems[i].setFont(new Font("Narkisim", Font.BOLD, 12));
			menuItems[i].setEditable(false);
			menuItems[i].setOpaque(false);
			if (i < comboBoxEntries[1].length) {
				menuItems[i].setBounds(getX() + 40, getY() + 63 + i * 20,
						getWidth() / 2 - 5, 20);
				menuItems[i].setTabSize(7);
			} else {
				menuItems[i].setBounds(getX() + 40, getY() + 78 + i * 20,
						getWidth() / 2 - 5, 20);
				menuItems[i].setTabSize(4);
			}
		}
		menuItems[4].setText("Mule Outfitting:");
		menuItems[5].setText("    Food Mule\t\t$25");
		menuItems[6].setText("    Energy Mule\t$50");
		menuItems[7].setText("    Smithore Mule\t$75");

		menuButton = new JButton("Complete Transaction");
		menuButton.setBounds(getX() + getWidth() / 2 + 50, getY() + 180, 200,
				27);
		menuButton.setBackground(new Color(255, 255, 255, 200));
		menuButton.setFocusable(false);
		menuButton.addActionListener(new StoreMenuListener());
	}

	public void updateInventoryLabels() {
		int numLeft;
		int price;
		String numLeftString;
		for (int i = 0; i < menuItems.length - 4; i++) {
			price = store.getCurrentPrice(comboBoxEntries[1][i]);
			numLeft = store.getQuantity(comboBoxEntries[1][i]);
			numLeftString = numLeft > 0 ? numLeft + " left!" : "SOLD OUT";
			numLeftString = price >= 100 ? "    " + numLeftString : "      "
					+ numLeftString;
			menuItems[i].setText(comboBoxEntries[1][i] + ":\t$" + price
					+ numLeftString);
		}
	}

	public void displayStoreMenu(Player activePlayer, Store store) {
		this.activePlayer = activePlayer;
		comboBoxes.get(0).setSelectedIndex(0);
		comboBoxes.get(1).setSelectedIndex(0);
		comboBoxes.get(2).setSelectedIndex(0);
		updateInventoryLabels();
		for (int i = 0; i < menuItems.length; i++)
			getParentPanel().add(menuItems[i]);
		for (int i = 0; i < comboBoxLabels.length; i++)
			getParentPanel().add(comboBoxLabels[i]);
		for (int i = 0; i < comboBoxes.size(); i++)
			getParentPanel().add(comboBoxes.get(i));
		getParentPanel().add(getWelcomeMessage());
		getParentPanel().add(menuLabel);
		getParentPanel().add(errorMessage);
		getParentPanel().add(menuButton);
	}

	public void removeStoreMenu() {
		for (int i = 0; i < menuItems.length; i++)
			getParentPanel().remove(menuItems[i]);
		for (int i = 0; i < comboBoxLabels.length; i++)
			getParentPanel().remove(comboBoxLabels[i]);
		for (int i = 0; i < comboBoxes.size(); i++)
			getParentPanel().remove(comboBoxes.get(i));
		getParentPanel().remove(getWelcomeMessage());
		getParentPanel().remove(menuLabel);
		getParentPanel().remove(errorMessage);
		getParentPanel().remove(menuButton);
	}

	public ArrayList<Object> getEntries() {
		ArrayList<Object> entries = new ArrayList<Object>();
		for (int i = 0; i < comboBoxes.size(); i++)
			entries.add(comboBoxes.get(i).getSelectedItem());
		return entries;
	}

	private class StoreMenuListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			int quantity;
			String selection = comboBoxes.get(1).getSelectedItem().toString();
			String buyOrSell = comboBoxes.get(0).getSelectedItem().toString();
			getParentPanel().remove(errorMessage);

			if (buyOrSell.equals("Buy")) {
				quantity = store.getQuantity(selection);
				if (selection.equals("Mules") && quantity > 0)
					setMuleBox();
				else
					setQuantityBox(quantity);
			} else {
				if (selection.equals("Mules"))
					quantity = activePlayer.hasMule() ? 1 : 0;
				else
					quantity = activePlayer.getGoods(selection);
				setQuantityBox(quantity);
			}
			updateInventoryLabels();
		}
	}

	private void setMuleBox() {
		comboBoxes.get(2).setModel(
				new DefaultComboBoxModel<String>(comboBoxEntries[2]));
		comboBoxLabels[2].setText("Which Type?");
		if (activePlayer.hasMule()) {
			menuButton.setEnabled(false);
		} else {
			menuButton.setEnabled(true);
		}
	}

	private void setQuantityBox(int quantity) {
		comboBoxLabels[2].setText("How Many?");
		comboBoxes.get(2).setModel(new DefaultComboBoxModel<String>());
		for (int i = comboBoxes.get(2).getItemCount(); i <= quantity; i++)
			comboBoxes.get(2).addItem("" + i);
		menuButton.setEnabled(true);
	}

	public JButton getButton() {
		return menuButton;
	}

	public void setErrorMessage(int cost) {
		errorMessage.setText("Not enough money! You need $" + cost + ".");
		getParentPanel().add(errorMessage);
	}

}