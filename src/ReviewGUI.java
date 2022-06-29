package src;

import javax.swing.*;

public class ReviewGUI {
	private JPanel panel;
	private JLabel wineNameLabel;
	private JLabel barberLabel;
	private JButton submitButton;
	private JScrollPane jScrollPane;
	private JTable jTable1;
	private JTextField wineNameTxtField;
	private JTextField customerNameTxtField;
	private JTextArea wineReviewTxtArea;
	private JLabel reviewsList;
	private JTable jTable2;
	private JButton clearButton;
	private JButton connectButton;
	private JLabel connectionLabel;
	private JButton disconnectButton;
	private JScrollPane jTableReviews;


	private Integer wine_id;

	private JButton jButton = new JButton();

	public static void main(String[] args) {
		JFrame frame = new JFrame("Wine Review");

		frame.setContentPane(new ReviewGUI().panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

}
