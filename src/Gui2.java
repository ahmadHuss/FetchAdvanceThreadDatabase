import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingWorker;
import javax.swing.table.AbstractTableModel;

public class Gui2 extends JFrame {
	public static Connection connection = null;
	private Statement state = null;
	private ResultSet rs = null;

	public static JProgressBar bar;
	public static JTable table;
	private JButton bt;
	public static JButton delete;

	private JTextField text;
	private JPanel panel;
	private GridBagLayout layout;
	private GridBagConstraints constraints;

	public Gui2(Connection conn) {
		connection = conn;
		layout = new GridBagLayout();
		constraints = new GridBagConstraints();
		panel = new JPanel();
		panel.setLayout(layout);

		text = new JTextField(15);
		bar = new JProgressBar();
		bt = new JButton("Submit Query");
		delete = new JButton("Delete Selected Row");

		constraints.insets = new Insets(5, 2, 5, 10);
		constraints.gridy = 0;// row 0
		constraints.gridx = 0;// column 0
		// TextField add on JPanel with given constraints
		panel.add(text, constraints);
		constraints.gridx++;
		panel.add(delete, constraints);
		constraints.gridx++;
		panel.add(bt, constraints);

		constraints.gridy++;
		constraints.gridx = 0;
		panel.add(bar, constraints);

		// North BorderLayout
		add(panel, BorderLayout.NORTH);

		try {
			state = conn.createStatement();
			rs = state.executeQuery("select * from city");
		} catch (SQLException e) {

			JOptionPane.showMessageDialog(null, e.getMessage());
		}

		table = new JTable();
		JScrollPane spane = new JScrollPane(table);

		/*
		 * spane.getVerticalScrollBar().addAdjustmentListener(new
		 * AdjustmentListener() { public void
		 * adjustmentValueChanged(AdjustmentEvent e) {
		 * e.getAdjustable().setValue(e.getAdjustable().getMaximum()); }
		 * 
		 * });
		 */

		add(spane, BorderLayout.CENTER);

		// TableModel md = new TableModel(rs);
		table.setModel(new OurModel(rs));// i created Anonymous object

		setSize(817, 538);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);

	}

}// class END