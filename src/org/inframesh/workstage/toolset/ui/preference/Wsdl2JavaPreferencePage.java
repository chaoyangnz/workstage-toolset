package org.inframesh.workstage.toolset.ui.preference;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.ComboFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.inframesh.workstage.toolset.Activator;
import org.inframesh.workstage.toolset.core.database.DatabaseManager;


public class Wsdl2JavaPreferencePage extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage {

	public Wsdl2JavaPreferencePage() {
		super(GRID);
		
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setTitle("Specify WSDL2Java options:");
	}
	
	@Override
	protected void createFieldEditors() {
		addField(new ComboFieldEditor(PreferenceConstants.DATASOURCE_TYPE,
				"Database Type:", PreferenceConstants.DATASOURCE_TYPE_CADIDATES, getFieldEditorParent()));
		
		new Label(getFieldEditorParent(), SWT.NONE);
		final Button testConnectionButton = new Button(getFieldEditorParent(), SWT.NONE);

		{
			addField(new FileFieldEditor("", "wsdl2java.bat Location:", getFieldEditorParent()));
		}

		{
			addField(new BooleanFieldEditor("", "Generate server side code (-ss)", getFieldEditorParent()));
		}

		{
			addField(new BooleanFieldEditor("", "Generate service descriptor (i.e. services.xml) (-sd)", getFieldEditorParent()));
		}

		{
			addField(new BooleanFieldEditor("", "Generate an interface for the service implementation (Default: off) (-ssi)", getFieldEditorParent()));
		}

		{
			addField(new BooleanFieldEditor("", "Unpacks the databinding classes (-u)", getFieldEditorParent()));
		}

		{
			addField(new BooleanFieldEditor("", "Switch on un-wrapping (-uw)", getFieldEditorParent()));
		}

		{
			addField(new BooleanFieldEditor("", "Overwrite the existing classes (-or)", getFieldEditorParent()));
		}

		{
			addField(new BooleanFieldEditor("", "Dont generate the build.xml in the output directory (--noBuildXML)", getFieldEditorParent()));
		}
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
