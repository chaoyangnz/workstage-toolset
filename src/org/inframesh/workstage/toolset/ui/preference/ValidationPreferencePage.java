package org.inframesh.workstage.toolset.ui.preference;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.inframesh.workstage.toolset.Activator;


public class ValidationPreferencePage extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage {

	public ValidationPreferencePage() {
		super(GRID);
		
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setTitle("Validation Preference");
		setDescription("Specify configure file filter(wildcard: * and ? available), file extension suffix expected:");
	}
	
	@Override
	protected void createFieldEditors() {
		{
			addField(new StringFieldEditor(PreferenceConstants.VALIDATION_SPRING_FILE_FILTER, "Spring Configure File Name Filter:", getFieldEditorParent()));
		}

		{
			addField(new StringFieldEditor(PreferenceConstants.VALIDATION_STRUTS_FILE_FILTER, "Struts Configure File Name Filter:", getFieldEditorParent()));
		}
		
		{
			(new Label(this.getFieldEditorParent(), SWT.NONE)).setText("Schema validation options:");
			//addField(new RadioGroupFieldEditor("", "Shematron validation options:", 1, new String[][] {}, getFieldEditorParent()));
		}

		{
			addField(new BooleanFieldEditor(PreferenceConstants.VALIDATION_SPRING_SCHEMA_VALIDATION, "Spring Schema validation", getFieldEditorParent()));
			addField(new BooleanFieldEditor(PreferenceConstants.VALIDATION_STRUTS_SCHEMA_VALIDATION, "Struts Schema validation", getFieldEditorParent()));
		}

		{
			(new Label(getFieldEditorParent(), SWT.NONE)).setText("Schematron validation options for Spring:");
			//addField(new RadioGroupFieldEditor("", "Shematron validation options:", 1, new String[][] {}, getFieldEditorParent()));
		}

		

		{
			addField(new BooleanFieldEditor(PreferenceConstants.VALIDATION_SPRING_SCHEMATRON_CLASSREF_VALIDATION, "bean \"class\" attribute resolving validation: <bean class=\"\"></bean>", getFieldEditorParent()));
		}

		{
			addField(new BooleanFieldEditor(PreferenceConstants.VALIDATION_SPRING_SCHEMATRON_IDCONFLICT_VALIDATION, "bean \"id\" attribute conflict validation: <bean id=\"\"></bean>", getFieldEditorParent()));
		}

		{
			addField(new BooleanFieldEditor(PreferenceConstants.VALIDATION_SPRING_SCHEMATRON_PARENTREF_VALIDATION, "bean \"parent\" attribute availability validation: <bean parent=\"\"></bean>", getFieldEditorParent()));
		}

		{
			addField(new BooleanFieldEditor(PreferenceConstants.VALIDATION_SPRING_SCHEMATRON_PROPERTYREF_VALIDATION, "bean property \"ref\" attribute availability validation: <bean><property ref=\"\" /></bean>", getFieldEditorParent()));
		}
		
		{
			addField(new BooleanFieldEditor(PreferenceConstants.VALIDATION_SPRING_SCHEMATRON_PROPERTYNAME_VALIDATION, "bean property \"name\" attribute conflict validation: <bean><property name=\"\" /></bean>", getFieldEditorParent()));
		}
		
		{
			(new Label(getFieldEditorParent(), SWT.NONE)).setText("Schematron validation options for Struts:");
			//addField(new RadioGroupFieldEditor("", "Shematron validation options:", 1, new String[][] {}, getFieldEditorParent()));
		}

		

		{
			addField(new BooleanFieldEditor(PreferenceConstants.VALIDATION_STRUTS_SCHEMATRON_ACTIONCLASSREF_VALIDATION, "action \"class\" attribute resolving validation: <action class=\"\"></action>", getFieldEditorParent()));
		}

		{
			addField(new BooleanFieldEditor(PreferenceConstants.VALIDATION_STRUTS_SCHEMATRON_ACTIONID_VALIDATION, "action \"id\" attribute conflict validation: <action name=\"\"></action>", getFieldEditorParent()));
		}

		{
			addField(new BooleanFieldEditor(PreferenceConstants.VALIDATION_STRUTS_SCHEMATRON_RESULTNAME_VALIDATION, "result \"name\" attribute conflict validation: <action><result name=\"\"></result></action>", getFieldEditorParent()));
		}

	}

	public void init(IWorkbench workbench) {
	}

}
