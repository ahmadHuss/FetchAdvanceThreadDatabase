import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

public class Gui extends JFrame {
	private static Connector conni;
	private Connection conn = null;
	private JButton bt;
	private JPanel panel;

	public Gui() {
		super("Frame");
		panel = new JPanel();
		bt = new JButton("Connect to Database 'World'");
		panel.add(bt);
		bt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				conn = conni.Connector();

				if (conn != null) {

					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							dispose();
							new Gui2(conn);

						}
					});

				} else {
					System.out.println("Return false");

				}

			}

		});
		add(panel);

		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);

	}

}
