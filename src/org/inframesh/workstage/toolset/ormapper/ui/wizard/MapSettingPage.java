package org.inframesh.workstage.toolset.ormapper.ui.wizard;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.inframesh.eclipse.jface.viewers.ListContentProvider;
import org.inframesh.workstage.toolset.Activator;
import org.inframesh.workstage.toolset.ormapper.core.model.MapSetting;
import org.inframesh.workstage.toolset.resource.ResourceToolkit;
import org.inframesh.workstage.toolset.ui.preference.PreferenceConstants;


public class MapSettingPage extends WizardPage {

	public List list;
	public Map map = new HashMap();
	private Table table;
	private TableViewer tableViewer;
	private Button restoreDefaultButton;

	protected MapSettingPage(String pageName) {
		super(pageName);
		setTitle("Table/View Selection");
		setDescription("Please select the tables or views that you generate beans based on");
	}

	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout gridlayout = new GridLayout();
		gridlayout.numColumns = 2;
		gridlayout.marginWidth = 0;
		gridlayout.marginHeight = 0;

		composite.setLayout(gridlayout);

		setControl(composite);

		list = new List(composite, SWT.V_SCROLL | SWT.BORDER);
		final GridData gd_list = new GridData(SWT.LEFT, SWT.FILL, false, false);
		gd_list.widthHint = 100;
		list.setLayoutData(gd_list);
		

		tableViewer = new TableViewer(composite, SWT.BORDER	//| SWT.CHECK 
				| SWT.MULTI | SWT.VIRTUAL | SWT.V_SCROLL
				| SWT.H_SCROLL | SWT.FULL_SELECTION);
		table = tableViewer.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		TableLayout layout = new TableLayout();
		table.setLayout(layout);

		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		//��0�� checkbox
		layout.addColumnData(new ColumnWeightData(10));
		new TableColumn(table, SWT.NONE).setText("");
		
		layout.addColumnData(new ColumnWeightData(200));
		new TableColumn(table, SWT.NONE).setText("Field Name");

		layout.addColumnData(new ColumnWeightData(225));
		new TableColumn(table, SWT.NONE).setText("JDBC Type");

		layout.addColumnData(new ColumnWeightData(250));
		new TableColumn(table, SWT.NONE).setText("Property Name");

		//��4��
		layout.addColumnData(new ColumnWeightData(225));
		new TableColumn(table, SWT.NONE).setText("Java Type");
		
		tableViewer.setContentProvider(new ListContentProvider());
		tableViewer.setLabelProvider(new LabelProvider());

		
		/*---------���ÿɱ༭--------------------------------*/
		tableViewer.setColumnProperties(new String[] { "isExcluded", "fieldName", "jdbcType", "propertyName", "javaType"});

		CellEditor cellEditors[] = new  CellEditor[5];
		cellEditors[0] = new CheckboxCellEditor(table);
		cellEditors[1] = null;
		cellEditors[2] = null;
		cellEditors[3] = new TextCellEditor(table);
		cellEditors[4] = new ComboBoxCellEditor(table, PreferenceConstants.MAP_TYPES, SWT.READ_ONLY);
		tableViewer.setCellEditors(cellEditors);
        tableViewer.setCellModifier(new CellModifier(tableViewer));

        /*--------------------------------------------------------*/
		
		list.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}

			//�����и����⣬һ��Ҫ�����֮��Ż�����ݵ�map��
			//�������map��ֻ���������޸Ļ�û�޸ĵ�setting
			public void widgetSelected(SelectionEvent e) {
				String [] selection = list.getSelection();
				if(selection.length == 0) return;
				
				// ���map���о�ֱ����ʾ
				if(map.containsKey(selection[0])) {
					tableViewer.setInput(map.get(selection[0]));
				} else {
					//����ݿ�鲢����map
					java.util.List input = Activator.getDefaultDatabaseManager().getDefaultMapSetting(selection[0]);
					map.put(selection[0], input);
					tableViewer.setInput(input);
				}
				
				//���ûָ���ť
				restoreDefaultButton.setEnabled(true);
			}
		});
		new Label(composite, SWT.NONE);

		restoreDefaultButton = new Button(composite, SWT.NONE);	
		restoreDefaultButton.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
		restoreDefaultButton.setText("Restore &Defaults");
		//�ָ���ť�¼�
		restoreDefaultButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				String [] selection = list.getSelection();
				if(map.containsKey(selection[0])) {
					map.remove(selection[0]);
				}
				//���´���ݿ�鲢����map
				java.util.List input = Activator.getDefaultDatabaseManager().getDefaultMapSetting(selection[0]);
				map.put(selection[0], input);
				tableViewer.setInput(input);
			}
		});
	}

	@Override
	public void setVisible(boolean visible) {
		if (visible) {
			list.removeAll();
//			map.clear();
			
			TableSelectionPage page = (TableSelectionPage)this.getPreviousPage();
			TableItem[] items = page.table.getItems();
			for(int i=0; i < items.length; ++i) {
				if(items[i].getChecked()) {
					list.add(items[i].getText());
				}
			}
			tableViewer.setInput(new Object());
			tableViewer.refresh();
		}
		
		//�����ûָ���ť����Ϊû��ѡ��ĳ����
		restoreDefaultButton.setEnabled(false);
		
		super.setVisible(visible);
	}

	

	private class LabelProvider implements ITableLabelProvider {

		public Image getColumnImage(Object element, int columnIndex) {
			MapSetting elem = (MapSetting) element;
			
			if(columnIndex == 0) 
				return elem.isExcluded() ? ResourceToolkit.getImageRegistry().get(ResourceToolkit.UNCHECKED)
										 : ResourceToolkit.getImageRegistry().get(ResourceToolkit.CHECKED);
			
			return null;
		}

		public String getColumnText(Object element, int columnIndex) {
			MapSetting elem = (MapSetting) element; // ����ת��
			
			if (columnIndex == 1)// �ڶ���Ҫ��ʾʲô���
				return elem.getFieldName();

			if (columnIndex == 2)
				return elem.getJdbcType();

			if (columnIndex == 3)
				return elem.getPropertyName();

			if (columnIndex == 4)
				return elem.getJavaType();

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
	
	private class CellModifier implements ICellModifier{
        private final TableViewer tv;
        
        public CellModifier(TableViewer tv){
                this.tv = tv;
        }
        public boolean canModify(Object element, String property) {
            return true;
        }

        public Object getValue(Object element, String property) {
            MapSetting elem = (MapSetting)element;
            if(property.equals("isExcluded")){
                return elem.isExcluded();
            } else if(property.equals("propertyName")){
                return elem.getPropertyName();
            } else if(property.equals("javaType")){
            	return getTypeIndex(elem.getJavaType());
            } else {
            	throw new RuntimeException("error column name : " + property);
            }
        }
        
        private int getTypeIndex(String type) {
        	for (int i = 0; i < PreferenceConstants.MAP_TYPES.length; i++) {
	        	if (PreferenceConstants.MAP_TYPES[i].equals(type))
	        		return i;
        	}
        	
        	return -1;
        }


        public void modify(Object element, String property, Object value) {
            TableItem item = (TableItem)element;
            MapSetting m = (MapSetting)item.getData();
            if (property.equals("isExcluded")){
                boolean newValue = (Boolean)value;
                m.setExcluded(newValue);
            } else if (property.equals("propertyName")){
                String newValue = (String)value;
                m.setPropertyName(newValue);
            } else if(property.equals("javaType")){
            	Integer comboIndex = (Integer) value;
                String newValue = PreferenceConstants.MAP_TYPES[comboIndex];;
                m.setJavaType(newValue);
            } else{
                throw new RuntimeException("��������:" + property);
            }
            tv.update(m, null);
        }
        
    }


}
