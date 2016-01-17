import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.table.AbstractTableModel;

//JTable all methods Based on index value 0
public class OurModel extends AbstractTableModel implements ActionListener {

	private List ColumnHeader;
	private List tableData;
	private ResultSetMetaData meta;
	private ResultSet rs = null;

	public OurModel(ResultSet rsSet) {
		rs = rsSet;
		try {

			// List rowData;
			meta = rs.getMetaData();

			int totalcolumn = meta.getColumnCount();

			ColumnHeader = new ArrayList(totalcolumn);

			tableData = new ArrayList();

			for (int i = 1; i <= totalcolumn; i++) {
				ColumnHeader.add(meta.getColumnName(i));

			}
			JDBCWorker worker = new JDBCWorker();
			// Execute Swing Worker
			worker.execute();

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}

		// Delete Function
		Gui2.delete.addActionListener(this);

	}// constructor end

	@Override
	public int getColumnCount() {

		return ColumnHeader.size();
	}

	public String getColumnName(int columnIndex) {
		return (String) ColumnHeader.get(columnIndex);

	}

	@Override
	public int getRowCount() {
		return tableData.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		List rowData2 = (List) tableData.get(rowIndex);

		return rowData2.get(columnIndex);
	}

	private class JDBCWorker extends SwingWorker<Boolean, Object> {

		@Override
		protected Boolean doInBackground() throws Exception {

			Gui2.bar.setIndeterminate(true);

			try {
				int totalColumns = meta.getColumnCount();

				while (rs.next()) {

					List<Object> obj = new ArrayList(totalColumns);

					for (int i = 1; i <= totalColumns; i++) {

						obj.add(rs.getObject(i));
					}

					publish(obj);
					Thread.sleep(100);

				} // while close
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
			}

			return true;

		}

		@Override
		protected void process(List<Object> chunks) {
			int n = tableData.size();

			for (Object giveitToMe : chunks) {

				tableData.add(giveitToMe);

			}
			fireTableRowsInserted(n, n + chunks.size());

		}

		@Override
		protected void done() {

			Gui2.bar.setIndeterminate(false);
		}

	}// Class Inner End

	@Override
	public void actionPerformed(ActionEvent e) {
		int rowIndex = Gui2.table.getSelectedRow();

		Object columnIndexValue = Gui2.table.getModel().getValueAt(rowIndex, 0);

		String columnName = Gui2.table.getModel().getColumnName(0);

		String query = "delete from world.city" + " where " + columnName + "=" + columnIndexValue;

		try {

			PreparedStatement pre = Gui2.connection.prepareStatement(query);

			pre.executeUpdate();

			JOptionPane.showMessageDialog(null, "Row Deleted Successfully");
			delte_raw(rowIndex);

		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage());
		}
	}

	public void delte_raw(int raw) {
		if (!tableData.isEmpty()) {
			this.fireTableRowsDeleted(raw + 1, raw);
			tableData.remove(raw);
		}
	}

}// Class Outer End