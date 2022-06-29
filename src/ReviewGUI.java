package src;

import src.SQL.DataControl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class ReviewGUI {
	public static final String ACTION = "Action";
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
	public ReviewGUI() {

		listWines();


	}

	class ButtonRenderer extends JButton implements TableCellRenderer {
		public ButtonRenderer() {
			setOpaque(true);
		}

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			setText((value == null) ? "Get Reviews" : value.toString());
			return this;
		}
	}

	class ButtonEditor extends DefaultCellEditor {
		private String label;

		public ButtonEditor(JCheckBox checkBox) {
			super(checkBox);
		}

		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
			label = (value == null) ? "Get Reviews" : value.toString();
			jButton.setText(label);
			wine_id = Integer.parseInt(table.getValueAt(row, 0).toString());

			String wineName = table.getValueAt(row, 1).toString();

			reviewsList.setText("Review List : " + wineName);

			wineNameTxtField.setText(wineName);
			return jButton;
		}

		public Object getCellEditorValue() {
			return new String(label);
		}
	}
	private void listWines() {
		String[] columnNames = {"Id", "Name", "Variety", "Description", "Designation", "Country", ACTION ,"Delete"};
		DefaultTableModel defaultTableModel = new DefaultTableModel(DataControl.getWine(), columnNames);

		jTable1.setModel(defaultTableModel);
		jTable1.getAutoCreateRowSorter();
		jTable1.getColumn(ACTION).setCellRenderer(new ButtonRenderer());
		jTable1.getColumn(ACTION).setCellEditor(new ButtonEditor(new JCheckBox()));

	}

}
