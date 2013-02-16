package org.inframesh.workstage.toolset.ui.preference;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.ComboFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.inframesh.eclipse.jface.preference.PasswordFieldEditor;
import org.inframesh.workstage.toolset.Activator;
import org.inframesh.workstage.toolset.core.database.DatabaseManager;


public class JDBCPreferencePage extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage {

	public JDBCPreferencePage() {
		super(GRID);
		
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
//		setDescription("JDBC Configure");
	}
	
	@Override
	protected void createFieldEditors() {
		addField(new StringFieldEditor(PreferenceConstants.DATASOURCE_ALIAS,
				"Alias:", getFieldEditorParent()));
		addField(new ComboFieldEditor(PreferenceConstants.DATASOURCE_TYPE,
				"Database Type:", PreferenceConstants.DATASOURCE_TYPE_CADIDATES, getFieldEditorParent()));
		addField(new StringFieldEditor(PreferenceConstants.DATASOURCE_DRIVER_CLASS_NAME,
				"Database Driver(JDBC):", getFieldEditorParent()));
		addField(new StringFieldEditor(PreferenceConstants.DATASOURCE_URL,
				"Database URL:", getFieldEditorParent()));
		addField(new StringFieldEditor(PreferenceConstants.DATASOURCE_USERID,
				"Userid:", getFieldEditorParent()));
		addField(new PasswordFieldEditor(PreferenceConstants.DATASOURCE_PASSWORD,
				"Password:", getFieldEditorParent()));
		
		new Label(getFieldEditorParent(), SWT.NONE);
		final Button testConnectionButton = new Button(getFieldEditorParent(), SWT.NONE);
		testConnectionButton.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
		testConnectionButton.setText("Test Connection");
		testConnectionButton.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				
				String result = "Test failed!";

				if(DatabaseManager.testConnection()) {
					result = "Test success!";
				}
				MessageDialog.openInformation(null, "Info", result);
				
				if(result == "Test success!")
					DatabaseManager.getInstance().refresh();
			}
			
		});
	}

	public void init(IWorkbench workbench) {
	}

}
