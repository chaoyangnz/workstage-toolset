package org.inframesh.workstage.toolset.ui.preference;

import org.eclipse.jface.preference.ComboFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.inframesh.workstage.toolset.Activator;


public class MapTemplatePreferencePage extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage {

	public MapTemplatePreferencePage() {
		super(GRID);
		
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
//		setDescription("JDBC Configure");
	}
	
	private final static String[][] DateCadidates = new String[][] {
		
	};
	@Override
	protected void createFieldEditors() {
		addField(new ComboFieldEditor(PreferenceConstants.MAP_CHAR_PREF, "CHAR:", PreferenceConstants.MAP_PREF_CADIDATES, getFieldEditorParent()));
		addField(new ComboFieldEditor(PreferenceConstants.MAP_VARCHAR_PREF, "VARCHAR:", PreferenceConstants.MAP_PREF_CADIDATES, getFieldEditorParent()));
		addField(new ComboFieldEditor(PreferenceConstants.MAP_INTEGER_PREF, "INTEGER:", PreferenceConstants.MAP_PREF_CADIDATES, getFieldEditorParent()));
		addField(new ComboFieldEditor(PreferenceConstants.MAP_BIGINT_PREF, "BIGINT:", PreferenceConstants.MAP_PREF_CADIDATES, getFieldEditorParent()));
		addField(new ComboFieldEditor(PreferenceConstants.MAP_SMALLINT_PREF, "SMALLINT:", PreferenceConstants.MAP_PREF_CADIDATES, getFieldEditorParent()));
		addField(new ComboFieldEditor(PreferenceConstants.MAP_FLOAT_PREF, "FLOAT:", PreferenceConstants.MAP_PREF_CADIDATES, getFieldEditorParent()));
		addField(new ComboFieldEditor(PreferenceConstants.MAP_DOUBLE_PREF, "DOUBLE:", PreferenceConstants.MAP_PREF_CADIDATES, getFieldEditorParent()));
		addField(new ComboFieldEditor(PreferenceConstants.MAP_REAL_PREF, "REAL:", PreferenceConstants.MAP_PREF_CADIDATES, getFieldEditorParent()));
		addField(new ComboFieldEditor(PreferenceConstants.MAP_NUMERIC_PREF, "NUMERIC:", PreferenceConstants.MAP_PREF_CADIDATES, getFieldEditorParent()));
		addField(new ComboFieldEditor(PreferenceConstants.MAP_DECIMAL_PREF, "DECIMAL:", PreferenceConstants.MAP_PREF_CADIDATES, getFieldEditorParent()));
		addField(new ComboFieldEditor(PreferenceConstants.MAP_DATE_PREF, "DATE:", PreferenceConstants.MAP_PREF_CADIDATES, getFieldEditorParent()));
		addField(new ComboFieldEditor(PreferenceConstants.MAP_TIME_PREF, "TIME:", PreferenceConstants.MAP_PREF_CADIDATES, getFieldEditorParent()));
		addField(new ComboFieldEditor(PreferenceConstants.MAP_TIMESTAMP_PREF, "TIMESTAMP:", PreferenceConstants.MAP_PREF_CADIDATES, getFieldEditorParent()));
		addField(new ComboFieldEditor(PreferenceConstants.MAP_BLOB_PREF, "BLOB:", PreferenceConstants.MAP_PREF_CADIDATES, getFieldEditorParent()));
		addField(new ComboFieldEditor(PreferenceConstants.MAP_CLOB_PREF, "CLOB:", PreferenceConstants.MAP_PREF_CADIDATES, getFieldEditorParent()));
		addField(new ComboFieldEditor(PreferenceConstants.MAP_GRAPHIC_PREF, "GRAPHIC:", PreferenceConstants.MAP_PREF_CADIDATES, getFieldEditorParent()));
	}

	public void init(IWorkbench workbench) {
	}

}
