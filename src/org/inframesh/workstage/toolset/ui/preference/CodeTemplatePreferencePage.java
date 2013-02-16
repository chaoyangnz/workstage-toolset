package org.inframesh.workstage.toolset.ui.preference;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.inframesh.eclipse.jface.preference.StringFieldEditorEx;
import org.inframesh.workstage.toolset.Activator;


public class CodeTemplatePreferencePage extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage {

	public CodeTemplatePreferencePage() {
		super(GRID);
		
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
//		setDescription("JDBC Configure");
	}
	
	@Override
	protected void createFieldEditors() {
		StringFieldEditorEx fileComments = new StringFieldEditorEx(PreferenceConstants.CODE_FILE_COMMENTS,
				"File Header Comments:", getFieldEditorParent());
		addField(fileComments);
		
		addField(new BooleanFieldEditor(PreferenceConstants.CODE_MAPPING_META,
				"Embedded Mapping Meta", getFieldEditorParent()));
	}

	public void init(IWorkbench workbench) {
	}

}
