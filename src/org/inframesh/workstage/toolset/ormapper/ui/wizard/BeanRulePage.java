package org.inframesh.workstage.toolset.ormapper.ui.wizard;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.inframesh.workstage.toolset.ormapper.core.model.BeanRule;


public class BeanRulePage extends WizardPage {

	private Button button_1;
	private DataBindingContext m_bindingContext;
	public BeanRule rule = new BeanRule();
	
	private Combo combo_2;
	private Combo combo_1;
	private Combo combo;
	private Text text;
	private Text text_1;
	protected BeanRulePage(String pageName) {
		super(pageName);
		setTitle("JavaBean Generation Rules");
		setDescription("Specify the rules by which you generate Javabeans");
	}

	public void createControl(Composite parent) {

		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		composite.setLayout(layout);
		
		setControl(composite);

		final Label label_1 = new Label(composite, SWT.NONE);
		label_1.setText("Datatable Naming Style:");

		combo = new Combo(composite, SWT.READ_ONLY);
		combo.setItems(new String[]{"Upper Underscore Name Convetion", "Lower Camel Name Convention"});
		combo.select(0);
		combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		final Label label_2 = new Label(composite, SWT.NONE);
		label_2.setText("Bean Naming Style:");

		combo_1 = new Combo(composite, SWT.READ_ONLY);
		combo_1.setItems(new String[]{"Lower Underscore Name Convention", "Lower Camel Name Convention"});
		combo_1.select(1);
		combo_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		final Label label = new Label(composite, SWT.NONE);
		label.setText("Bean Name Prefix:");

		text = new Text(composite, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		final Label label_5 = new Label(composite, SWT.NONE);
		label_5.setText("Bean Name Suffix:");

		text_1 = new Text(composite, SWT.BORDER);
		text_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		final Label label_4 = new Label(composite, SWT.NONE);
		label_4.setLayoutData(new GridData());
		label_4.setText("Generate Mapping Config:");

		final Button button = new Button(composite, SWT.CHECK);
		button.setLayoutData(new GridData());
		button.setSelection(true);

		final Label label_3 = new Label(composite, SWT.NONE);
		label_3.setText("O/R Map Framework:");

		combo_2 = new Combo(composite, SWT.READ_ONLY);
		combo_2.setItems(new String[] {"Hibernate", "iBATIS"});
		combo_2.select(1);
		combo_2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		final Label generateAnnotationLabel = new Label(composite, SWT.NONE);
		generateAnnotationLabel.setText("Generate @Annotation:");

		button_1 = new Button(composite, SWT.CHECK);
		m_bindingContext = initDataBindings();
	}
	protected DataBindingContext initDataBindings() {
		IObservableValue textTextObserveWidget = SWTObservables.observeText(text, SWT.Modify);
		IObservableValue text_1TextObserveWidget = SWTObservables.observeText(text_1, SWT.Modify);
		IObservableValue ruleBeanPrefixObserveValue = BeansObservables.observeValue(rule, "beanPrefix");
		IObservableValue ruleGenerateAnnotationObserveValue = BeansObservables.observeValue(rule, "generateAnnotation");
		IObservableValue button_1SelectionObserveWidget = SWTObservables.observeSelection(button_1);
		IObservableValue ruleBeanSuffixObserveValue = BeansObservables.observeValue(rule, "beanSuffix");
		//
		//
		DataBindingContext bindingContext = new DataBindingContext();
		//
		bindingContext.bindValue(textTextObserveWidget, ruleBeanPrefixObserveValue, null, null);
		bindingContext.bindValue(text_1TextObserveWidget, ruleBeanSuffixObserveValue, null, null);
		bindingContext.bindValue(button_1SelectionObserveWidget, ruleGenerateAnnotationObserveValue, null, null);
		//
		return bindingContext;
	}
}
