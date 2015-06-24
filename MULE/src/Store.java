import java.io.Serializable;

/**
 * The Store class is the store in the M.U.L.E. game. It sells food, energy,
 * smithore, crystite, and mules. The starting quantities and prices of these
 * goods are determined by the game difficulty.
 * 
 */
@SuppressWarnings("serial")
public class Store implements Serializable {
	private Goods food, energy, smithore, crystite, mules; // goods that can be
															// sold

	/**
	 * Constructor for the Store. Game difficulty determines starting prices and
	 * quantities
	 * 
	 * @param difficulty
	 *            the game difficulty setting
	 */
	public Store(String difficulty) {
		switch (difficulty) {
		case "Beginner": // beginner difficulty
					//price , quantity
			food = new Goods(30, 16);
			energy = new Goods(25, 16);
			smithore = new Goods(50, 0);
			crystite = new Goods(100, 0);
			mules = new Goods(100, 25);
			break;
		default: // all other difficulties
			food = new Goods(30, 8);
			energy = new Goods(25, 8);
			smithore = new Goods(50, 8);
			crystite = new Goods(100, 0);
			mules = new Goods(100, 14);
		}
	}

	/**
	 * sellGoods sells some quantity of a good. This simply reduces the
	 * quantity. It does not add it to the player who buys it
	 * 
	 * @param type
	 *            the type of good to be sold
	 * @param quantity
	 *            the number of goods to be sold
	 */
	public void sellGoods(String type, int quantity) {
		if (type.equals("Food"))
			food.sellQuantity(quantity);
		else if (type.equals("Energy"))
			energy.sellQuantity(quantity);
		else if (type.equals("Smithore"))
			smithore.sellQuantity(quantity);
		else if (type.equals("Crystite"))
			crystite.sellQuantity(quantity);
		else if (type.equals("Mules"))
			mules.sellQuantity(quantity);
	}

	public void buyGoods(String type, int quantity) {
		if (type.equals("Food"))
			food.buyQuantity(quantity);
		else if (type.equals("Energy"))
			energy.buyQuantity(quantity);
		else if (type.equals("Smithore")) {
			smithore.buyQuantity(quantity);
			mules.buyQuantity(quantity); // adds a mule and a ore to the store.
											// M2 says that
											// the # of mules is related to the
											// ore but does not say how
		} else if (type.equals("Crystite"))
			crystite.buyQuantity(quantity);
	}

	/**
	 * Gets the currentPrice of a good
	 * 
	 * @param type
	 *            the good get the price of
	 * @return the current price
	 */
	public int getCurrentPrice(String type) {
		switch (type) {
		case "Food":
			return food.getCurrentPrice();
		case "Energy":
			return energy.getCurrentPrice();
		case "Smithore":
			return smithore.getCurrentPrice();
		case "Crystite":
			return crystite.getCurrentPrice();
		case "Mules":
			return mules.getCurrentPrice();
		}
		return -1;
	}

	/**
	 * Gets the current quantity in the store of a good
	 * 
	 * @param type
	 *            the type of good to get the quantity of
	 * @return the quantity in the store
	 */
	public int getQuantity(String type) {
		switch (type) {
		case "Food":
			return food.getQuantity();
		case "Energy":
			return energy.getQuantity();
		case "Smithore":
			return smithore.getQuantity();
		case "Crystite":
			return crystite.getQuantity();
		case "Mules":
			return mules.getQuantity();
		}
		return -1;
	}

	/**
	 * The Goods class is a container for a specific type of good. It is used to
	 * hold the current number of the good in the store as well as the current
	 * price.
	 * 
	 * @author root
	 * 
	 */
	private class Goods implements Serializable {
		private int currentPrice;
		private int quantity;

		private Goods(int currentPrice, int quantity) {
			this.currentPrice = currentPrice;
			this.quantity = quantity;
		}

		public int getCurrentPrice() {
			return currentPrice;
		}

		public int getQuantity() {
			return quantity;
		}

		@SuppressWarnings("unused")
		private void setCurrentPrice(int price) {
			this.currentPrice = price;
		}

		private void sellQuantity(int quantity) {
			if(this.quantity < quantity){
				return;
			}
			else{
				this.quantity -= quantity;
			}
		}
		private void buyQuantity(int quantity) {
			this.quantity += quantity;
		}
	}

}
