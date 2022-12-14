package src;

import src.SQL.DataControl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReviewGUI {
	public static final String ACTION = "Action";
	private JPanel panel;
	private JLabel wineNameLabel;
	private JLabel wineReviewLabel;
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
	private JButton printButton;
	private JScrollPane jTableReviews;

	private Socket socket;
	private PrintWriter printWriter;
	private BufferedReader bufferedReader;

	private Integer wine_id;

	private JButton jButton = new JButton();

	public static void main(String[] args) {
		JFrame frame = new JFrame("Wine Review");

		frame.setContentPane(new ReviewGUI().panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);

	}
	public ReviewGUI() {

		listWines();
		listReviews(null);

		submitButton.addActionListener(new ActionListener() { //adds the review to the wine
			@Override
			public void actionPerformed(ActionEvent e) {
				if(wine_id == null) { 				//checking if wine is not null
					JOptionPane.showMessageDialog(jTable2 , "Choose Wine first !");
				}else {
					DataControl.addReview(customerNameTxtField.getText(), wineReviewTxtArea.getText(), wine_id);	//adds review from inputted texts

					listReviews(DataControl.getReviews(wine_id));
					customerNameTxtField.setText(null);
					wineReviewTxtArea.setText(null);

					JOptionPane.showMessageDialog(jTable2 , "Success add review");
				}

			}

		});

		clearButton.addActionListener(new ActionListener() { //clears text fields
			@Override
			public void actionPerformed(ActionEvent e) {
				wineNameTxtField.setText("");
				customerNameTxtField.setText("");
				wineReviewTxtArea.setText("");
			}
		});

		connectButton.addActionListener(new ActionListener() { //connects to server by calling reconnect class
			@Override
			public void actionPerformed(ActionEvent e) {
				reconnectToServer();
			}
		});
		disconnectButton.addActionListener(new ActionListener() {  //disconnects from server by calling close connection class
			@Override
			public void actionPerformed(ActionEvent e) {
				closeConnection();
			}
		});
		printButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					jTable1.print(JTable.PrintMode.FIT_WIDTH, null, null);
				} catch (PrinterException ex) {
					ex.printStackTrace();
				}
			}
		});
	}
	//reconnecting to server
	private void reconnectToServer () {
		closeConnection();
		connectionLabel.setText("Attempting to connect to server");
		try {
			socket = new Socket("127.0.0.1", 2000);
			printWriter = new PrintWriter(socket.getOutputStream(), true);
			bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			connectionLabel.setText("Connected to server!");
		} catch (IOException ex) {
			Logger.getLogger(ReviewGUI.class.getName()).log(Level.SEVERE, null, ex);
			connectionLabel.setText("Connection Failed"); // connection failed
		}
	}
	//closing connetion to server
	private void closeConnection () {
		if (socket != null) {

			connectionLabel.setText("Connection Lost!");
			try {
				socket.close();
			} catch (IOException ex) {
				Logger.getLogger(ReviewGUI.class.getName()).log(Level.SEVERE, null, ex);
			} finally {
				socket = null;
			}
		}
	}

	//creates a button in the table for getting the reviews with getTableCellRendererComponent
	class ButtonRenderer extends JButton implements TableCellRenderer {
		public ButtonRenderer() {
			setOpaque(true);
		}

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			setText((value == null) ? "Get Reviews" : value.toString());
			return this;
		}
	}

	//get review button action editor component
	class ButtonEditor extends DefaultCellEditor {
		private String label;

		public ButtonEditor(JCheckBox checkBox) { //sets button editor class with check box
			super(checkBox);
		}

		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) { //component class to change button in table
			label = (value == null) ? "Get Reviews" : value.toString();
			jButton.setText(label);
			wine_id = Integer.parseInt(table.getValueAt(row, 0).toString()); //gets wine id from table row and convert to string, and later to int

			String wineName = table.getValueAt(row, 1).toString(); //gets name from wine and convert to string

			reviewsList.setText("Review List : " + wineName); //setts review list text

			listReviews(DataControl.getReviews(Integer.parseInt(table.getValueAt(row, 0).toString()))); //displayes reviews
			wineNameTxtField.setText(wineName);
			return jButton;
		}

		public Object getCellEditorValue() {
			return new String(label);
		} //gets new tabled edited value to label
	}

	//creates a button in the table for deleting the reviews
	class ButtonRenderer2 extends JButton implements TableCellRenderer {
		public ButtonRenderer2() {
			setOpaque(true);
		}

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			setText((value == null) ? "Delete" : value.toString());
			return this;
		}
	}

	//delete button action editor
	class ButtonEditor2 extends DefaultCellEditor {
		private String label;

		public ButtonEditor2(JCheckBox checkBox) {
			super(checkBox);
		}

		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) { //component class to change button in table similar with button 1
			label = (value == null) ? "Delete" : value.toString();
			jButton.setText(label);
			wine_id = Integer.parseInt(table.getValueAt(row, 0).toString());

			DataControl.softDeleteWine(wine_id);
			listWines();
			JOptionPane.showMessageDialog(jTable2 , "Success delete wine");
			return jButton;
		}

		public Object getCellEditorValue() {
			return new String(label);
		}
	}

	//lists reviews and sets them on the table 2
	private void listReviews(String[][] data) {
		String[] columnNames = {"Wine Name", "Customer Name", "Review"};
		DefaultTableModel defaultTableModel = new DefaultTableModel(data, columnNames);

		jTable2.setModel(defaultTableModel);
		jTable2.revalidate();
	}
	//lists wines and components and sets them on the table 1
	private void listWines() {
		String[] columnNames = {"Id", "Name", "Variety", "Description", "Designation", "Country", ACTION ,"Delete"};
		DefaultTableModel defaultTableModel = new DefaultTableModel(DataControl.getWine(), columnNames);

		jTable1.setModel(defaultTableModel);
		jTable1.getAutoCreateRowSorter();
		jTable1.getColumn(ACTION).setCellRenderer(new ButtonRenderer());
		jTable1.getColumn(ACTION).setCellEditor(new ButtonEditor(new JCheckBox()));
		jTable1.getColumn("Delete").setCellRenderer(new ButtonRenderer2());
		jTable1.getColumn("Delete").setCellEditor(new ButtonEditor2(new JCheckBox()));
	}

}
