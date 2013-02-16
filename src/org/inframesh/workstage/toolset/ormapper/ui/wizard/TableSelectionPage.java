package org.inframesh.workstage.toolset.ormapper.ui.wizard;

import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.inframesh.eclipse.jface.viewers.ListContentProvider;
import org.inframesh.workstage.toolset.Activator;
import org.inframesh.workstage.toolset.ormapper.core.model.TableMeta;


public class TableSelectionPage extends WizardPage {

	public Table table;
	
	protected TableSelectionPage(String pageName) {
		super(pageName);
		setTitle("Table/View Selection");
		setDescription("Please select the tables or views that you generate beans based on");
	}

	public void createControl(Composite parent) {		
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout gridlayout = new GridLayout();
		gridlayout.numColumns = 1;
		gridlayout.marginWidth = 0;
		gridlayout.marginHeight = 0;
		
		composite.setLayout(gridlayout);

		setControl(composite);
		
		
		final TableViewer tableViewer = new TableViewer(composite, SWT.BORDER | SWT.CHECK | SWT.MULTI | SWT.VIRTUAL |SWT.V_SCROLL | SWT.H_SCROLL);
		table = tableViewer.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		TableLayout layout = new TableLayout();
		table.setLayout(layout);
		
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		layout.addColumnData(new ColumnWeightData(150));
		new TableColumn(table, SWT.NONE).setText("Table Name");

		layout.addColumnData(new ColumnWeightData(100));
		new TableColumn(table, SWT.NONE).setText("Table Type");
		
		layout.addColumnData(new ColumnWeightData(150));
		new TableColumn(table, SWT.NONE).setText("Create Time");
		
		Object input = Activator.getDefaultDatabaseManager().getAllTableMeta();
		
		tableViewer.setContentProvider(new ListContentProvider());
		tableViewer.setLabelProvider(new LabelProvider());
		tableViewer.setInput(input);
	}
	
	
	private class LabelProvider implements ITableLabelProvider {

		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		public String getColumnText(Object element, int columnIndex) {
			TableMeta elem = (TableMeta) element; // ����ת��
			if (columnIndex == 0)// ��һ��Ҫ��ʾʲô���
				return elem.getTableName();
			
			if (columnIndex == 1) {
				if("T".equals(elem.getType())) 
					return "Table";
				if("V".equals(elem.getType()))
					return "View";
				return "N/A";
			}			
			
			if (columnIndex == 2)
				return elem.getCreateTime();
			
			return null;
		}

		public void addListener(ILabelProviderListener listener) {
		}

		public void dispose() {
		}

		public boolean isLabelProperty(Object element, String property) {
			return false;
		}

		public void removeListener(ILabelProviderListener listener) {	
		}
	}

}
