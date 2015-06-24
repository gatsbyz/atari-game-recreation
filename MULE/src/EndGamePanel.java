import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Component;
import java.util.ArrayList;

import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JSeparator;


@SuppressWarnings("serial")
public class EndGamePanel extends JPanel {
	JButton btnMainMenu;
	JLabel[] placeLabels = new JLabel[4];
	JLabel[] nameLabels = new JLabel[4];
	JLabel[] imgLabels = new JLabel[4];
	JLabel[] scoreLabels = new JLabel[4];
	JLabel[] moneyLabels = new JLabel[4];
	JLabel[] foodLabels = new JLabel[4];
	JLabel[] smithoreLabels = new JLabel[4];
	JLabel[] landOwnedLabels = new JLabel[4];
	
	/*JLabel lblPlace1;
	JLabel lblPlace2;
	JLabel lblPlace3;
	JLabel lblPlace4;
	JLabel lblName1;
	JLabel lblName2;
	JLabel lblName3;
	JLabel lblName4;*/

	/**
	 * Create the panel.
	 */
	public EndGamePanel() {
		setLayout(null);
		setBackground(new Color(227,164,9));
		JLabel lblGameOver = new JLabel("Game Over!");
		lblGameOver.setHorizontalAlignment(SwingConstants.CENTER);
		lblGameOver.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblGameOver.setFont(new Font("Narkisim", Font.BOLD, 25));
		lblGameOver.setBounds(250, 10, 400, 50);
		add(lblGameOver);
		
		btnMainMenu = new JButton("Main Menu");
		btnMainMenu.setFont(new Font("Narkisim", Font.BOLD, 15));
		btnMainMenu.setBounds(375, 566, 150, 30);
		btnMainMenu.setBackground(new Color(255, 255, 255, 150));
		add(btnMainMenu);
		
		for(int i=0; i<4; i++){
			placeLabels[i] = new JLabel();
			nameLabels[i] = new JLabel();
			imgLabels[i] = new JLabel();
			scoreLabels[i] = new JLabel();
			moneyLabels[i] = new JLabel();
			foodLabels[i] = new JLabel();
			smithoreLabels[i] = new JLabel();
			landOwnedLabels[i] = new JLabel();
		}
		
		placeLabels[0].setVisible(false);
		placeLabels[0].setText("1st Place");
		placeLabels[0].setFont(new Font("Narkisim", Font.BOLD, 30));
		placeLabels[0].setBounds(26, 122, 125, 50);
		add(placeLabels[0]);
		
		//imgLabels[0] = new JLabel("IMG");
		//imgLabels[0].setFont(new Font("Narkisim", Font.PLAIN, 13));
		imgLabels[0].setHorizontalAlignment(SwingConstants.CENTER);
		imgLabels[0].setBackground(Color.WHITE);
		imgLabels[0].setBounds(182, 113, 75, 75);
		add(imgLabels[0]);
		
		//nameLabels[0] = new JLabel("Name");
		nameLabels[0].setFont(new Font("Narkisim", Font.BOLD, 13));
		nameLabels[0].setHorizontalAlignment(SwingConstants.CENTER);
		nameLabels[0].setBounds(157, 88, 125, 14);
		add(nameLabels[0]);
		
		JLabel lblScoreTitle = new JLabel("Score");
		lblScoreTitle.setFont(new Font("Narkisim", Font.BOLD, 13));
		lblScoreTitle.setBounds(306, 88, 46, 14);
		add(lblScoreTitle);
		
		JLabel lblFoodTitle = new JLabel("Food");
		lblFoodTitle.setFont(new Font("Narkisim", Font.BOLD, 13));
		lblFoodTitle.setBounds(537, 88, 46, 14);
		add(lblFoodTitle);
		
		JLabel lblMoneyTitle = new JLabel("Money");
		lblMoneyTitle.setFont(new Font("Narkisim", Font.BOLD, 13));
		lblMoneyTitle.setBounds(419, 88, 46, 14);
		add(lblMoneyTitle);
		
		JLabel lblLandOwnedTitle = new JLabel("Land Owned");
		lblLandOwnedTitle.setFont(new Font("Narkisim", Font.BOLD, 13));
		lblLandOwnedTitle.setBounds(783, 88, 87, 14);
		add(lblLandOwnedTitle);
		
		//scoreLabels[1 = new JLabel("-Score Value-");
		scoreLabels[0].setFont(new Font("Narkisim", Font.BOLD, 15));
		scoreLabels[0].setBounds(306, 143, 75, 14);
		add(scoreLabels[0]);
		
		//moneyLabels[1 = new JLabel("-Money Value-");
		moneyLabels[0].setFont(new Font("Narkisim", Font.BOLD, 15));
		moneyLabels[0].setBounds(419, 143, 75, 14);
		add(moneyLabels[0]);
		
		//lblfoodValue1 = new JLabel("-Food Value-");
		foodLabels[0].setFont(new Font("Narkisim", Font.BOLD, 15));
		foodLabels[0].setBounds(537, 143, 75, 14);
		add(foodLabels[0]);
		
		//landOwnedLabels[1 = new JLabel("-Land Owned Value-");
		landOwnedLabels[0].setFont(new Font("Narkisim", Font.BOLD, 15));
		landOwnedLabels[0].setBounds(783, 143, 75, 14);
		add(landOwnedLabels[0]);
		
		placeLabels[1].setVisible(false);
		placeLabels[1].setText("2nd Place");
		placeLabels[1].setFont(new Font("Narkisim", Font.BOLD, 25));
		placeLabels[1].setBounds(26, 251, 125, 35);
		add(placeLabels[1]);
		
		//imgLabels2 = new JLabel("IMG");
		imgLabels[1].setHorizontalAlignment(SwingConstants.CENTER);
		//imgLabels[1].setFont(new Font("Narkisim", Font.PLAIN, 13));
		imgLabels[1].setBackground(Color.WHITE);
		imgLabels[1].setBounds(182, 248, 75, 75);
		add(imgLabels[1]);
		
		//nameLabels[1] = new JLabel("Name");
		nameLabels[1].setHorizontalAlignment(SwingConstants.CENTER);
		nameLabels[1].setFont(new Font("Narkisim", Font.BOLD, 13));
		nameLabels[1].setBounds(157, 223, 125, 14);
		add(nameLabels[1]);
		
		//scoreLabels[2 = new JLabel("-Score Value-");
		scoreLabels[1].setFont(new Font("Narkisim", Font.BOLD, 15));
		scoreLabels[1].setBounds(306, 278, 75, 14);
		add(scoreLabels[1]);
		
		//moneyLabels[2 = new JLabel("-Money Value-");
		moneyLabels[1].setFont(new Font("Narkisim", Font.BOLD, 15));
		moneyLabels[1].setBounds(419, 278, 75, 14);
		add(moneyLabels[1]);
		
		//foodLabels2 = new JLabel("-Food Value-");
		foodLabels[1].setFont(new Font("Narkisim", Font.BOLD, 15));
		foodLabels[1].setBounds(537, 278, 75, 14);
		add(foodLabels[1]);
		
		//landOwnedLabels[2 = new JLabel("-Land Owned Value-");
		landOwnedLabels[1].setFont(new Font("Narkisim", Font.BOLD, 15));
		landOwnedLabels[1].setBounds(783, 278, 75, 14);
		add(landOwnedLabels[1]);
		
		placeLabels[3].setVisible(false);
		placeLabels[3].setText("4th Place");
		placeLabels[3].setFont(new Font("Narkisim", Font.BOLD, 25));
		placeLabels[3].setBounds(26, 473, 125, 35);
		add(placeLabels[3]);
		
		//imgLabels4 = new JLabel("IMG");
		imgLabels[3].setHorizontalAlignment(SwingConstants.CENTER);
		//imgLabels4.setFont(new Font("Narkisim", Font.PLAIN, 13));
		imgLabels[3].setBackground(Color.WHITE);
		imgLabels[3].setBounds(182, 470, 75, 75);
		add(imgLabels[3]);
		
		//nameLabels[4 = new JLabel("Name");
		nameLabels[3].setHorizontalAlignment(SwingConstants.CENTER);
		nameLabels[3].setFont(new Font("Narkisim", Font.BOLD, 13));
		nameLabels[3].setBounds(157, 445, 125, 14);
		add(nameLabels[3]);
		
		//scoreLabels[4 = new JLabel("-Score Value-");
		scoreLabels[3].setFont(new Font("Narkisim", Font.BOLD, 15));
		scoreLabels[3].setBounds(306, 500, 75, 14);
		add(scoreLabels[3]);
		
		//moneyLabels[4 = new JLabel("-Money Value-");
		moneyLabels[3].setFont(new Font("Narkisim", Font.BOLD, 15));
		moneyLabels[3].setBounds(419, 500, 75, 14);
		add(moneyLabels[3]);
		
		//foodLabels4 = new JLabel("-Food Value-");
		foodLabels[3].setFont(new Font("Narkisim", Font.BOLD, 15));
		foodLabels[3].setBounds(537, 500, 75, 14);
		add(foodLabels[3]);
		
		//landOwnedLabels[4 = new JLabel("-Land Owned Value-");
		landOwnedLabels[3].setFont(new Font("Narkisim", Font.BOLD, 15));
		landOwnedLabels[3].setBounds(783, 500, 75, 14);
		add(landOwnedLabels[3]);
		
		placeLabels[2].setVisible(false);
		placeLabels[2].setText("3rd Place");
		placeLabels[2].setFont(new Font("Narkisim", Font.BOLD, 25));
		placeLabels[2].setBounds(26, 362, 125, 35);
		add(placeLabels[2]);
		
		//imgLabels3 = new JLabel("IMG");
		imgLabels[2].setHorizontalAlignment(SwingConstants.CENTER);
		//imgLabels3.setFont(new Font("Narkisim", Font.PLAIN, 13));
		imgLabels[2].setBackground(Color.WHITE);
		imgLabels[2].setBounds(182, 359, 75, 75);
		add(imgLabels[2]);
		
		//nameLabels[3 = new JLabel("Name");
		nameLabels[2].setHorizontalAlignment(SwingConstants.CENTER);
		nameLabels[2].setFont(new Font("Narkisim", Font.BOLD, 13));
		nameLabels[2].setBounds(157, 334, 125, 14);
		add(nameLabels[2]);
		
		//scoreLabels[3 = new JLabel("-Score Value-");
		scoreLabels[2].setFont(new Font("Narkisim", Font.BOLD, 15));
		scoreLabels[2].setBounds(306, 389, 75, 14);
		add(scoreLabels[2]);
		
		//moneyLabels[3 = new JLabel("-Money Value-");
		moneyLabels[2].setFont(new Font("Narkisim", Font.BOLD, 15));
		moneyLabels[2].setBounds(419, 389, 75, 14);
		add(moneyLabels[2]);
		
		//foodLabels3 = new JLabel("-Food Value-");
		foodLabels[2].setFont(new Font("Narkisim", Font.BOLD, 15));
		foodLabels[2].setBounds(537, 389, 75, 14);
		add(foodLabels[2]);
		
		//landOwnedLabels[3 = new JLabel("-Land Owned Value-");
		landOwnedLabels[2].setFont(new Font("Narkisim", Font.BOLD, 15));
		landOwnedLabels[2].setBounds(783, 389, 75, 14);
		add(landOwnedLabels[2]);
		
		JLabel lblSmithoreTitle = new JLabel("Smithore");
		lblSmithoreTitle.setFont(new Font("Narkisim", Font.BOLD, 13));
		lblSmithoreTitle.setBounds(659, 88, 63, 14);
		add(lblSmithoreTitle);
		
		//smithoreLabels[1 = new JLabel("-Smithore Value-");
		smithoreLabels[0].setFont(new Font("Narkisim", Font.BOLD, 15));
		smithoreLabels[0].setBounds(659, 143, 75, 14);
		add(smithoreLabels[0]);
		
		//smithoreLabels[2 = new JLabel("-Smithore Value-");
		smithoreLabels[1].setFont(new Font("Narkisim", Font.BOLD, 15));
		smithoreLabels[1].setBounds(659, 278, 75, 14);
		add(smithoreLabels[1]);
		
		//smithoreLabels[3 = new JLabel("-Smithore Value-");
		smithoreLabels[2].setFont(new Font("Narkisim", Font.BOLD, 15));
		smithoreLabels[2].setBounds(659, 389, 75, 14);
		add(smithoreLabels[2]);
		
		//smithoreLabels[4 = new JLabel("-Smithore Value-");
		smithoreLabels[3].setFont(new Font("Narkisim", Font.BOLD, 15));
		smithoreLabels[3].setBounds(659, 500, 75, 14);
		add(smithoreLabels[3]);
	}
	
	public JButton getBtnMainMenu(){
		return btnMainMenu;
	}
	
	public void fillInfo(Player[] playerArr){
		//Go through array of players, filling from 1st place to last place
		for(int i=0; i<playerArr.length; i++){
			//Get the index of the player with the next highest score.
			int leaderInd = 0;
			for(int j=1; j<playerArr.length; j++){
				Player leader = playerArr[leaderInd];
				Player curr = playerArr[j];
				if(leader==null || (curr!=null && curr.getScore()>leader.getScore()))
						leaderInd = j;
			}
			
			//Fill appropriate labels for current place.
			Player p = playerArr[leaderInd];
			Color pColor = p.getColorObject();
			System.out.println("player color:"+pColor);
			
			placeLabels[i].setForeground((pColor!=null) ? pColor:Color.BLACK);
			placeLabels[i].setVisible(true);
			nameLabels[i].setForeground((pColor!=null) ? pColor:Color.BLACK);
			nameLabels[i].setText(p.getName());;
			imgLabels[i].setIcon(p.getImage());
			scoreLabels[i].setForeground((pColor!=null) ? pColor:Color.BLACK);
			scoreLabels[i].setText(""+p.getScore());
			moneyLabels[i].setForeground((pColor!=null) ? pColor:Color.BLACK);
			moneyLabels[i].setText(""+p.getMoney());
			foodLabels[i].setForeground((pColor!=null) ? pColor:Color.BLACK);
			foodLabels[i].setText(""+p.getFood());
			smithoreLabels[i].setForeground((pColor!=null) ? pColor:Color.BLACK);
			smithoreLabels[i].setText(""+p.getOre());
			landOwnedLabels[i].setForeground((pColor!=null) ? pColor:Color.BLACK);
			landOwnedLabels[i].setText(""+p.getOwnedTiles().size());
			
			//Set player slot to null so the next iteration will skip it.\
			playerArr[leaderInd]=null;
		}
	}
}
