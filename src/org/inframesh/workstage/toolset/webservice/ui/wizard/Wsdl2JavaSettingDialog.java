package org.inframesh.workstage.toolset.webservice.ui.wizard;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;

public class Wsdl2JavaSettingDialog extends TitleAreaDialog {
	private DataBindingContext m_bindingContext;
	public Wsdl2JavaSetting info = new Wsdl2JavaSetting();

	private Text wsdlFileLocationText;
	private Text sourcePathText;
	private Text resourcePathText;
	private Text skeltonInterfaceNameText;
	private Text skeltonClassNameText;

	/**
	 * Create the dialog
	 * @param parentShell
	 */
	public Wsdl2JavaSettingDialog(Shell parentShell) {
		super(parentShell);
		this.setTitle("Path Setting");
	}

	/**
	 * Create contents of the dialog
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		container.setLayout(gridLayout);
		container.setLayoutData(new GridData(GridData.FILL_BOTH));

		final Label wsdlLocationuriLabel = new Label(container, SWT.NONE);
		wsdlLocationuriLabel.setText("Wsdl File Location (-uri):");

		wsdlFileLocationText = new Text(container, SWT.BORDER);
		wsdlFileLocationText.setEditable(false);
		final GridData gd_wsdlFileLocationText = new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1);
		wsdlFileLocationText.setLayoutData(gd_wsdlFileLocationText);

		final Label savePathLabel = new Label(container, SWT.NONE);
		savePathLabel.setLayoutData(new GridData());
		savePathLabel.setToolTipText("Specify a directory path for the generated code.");
		savePathLabel.setText("Source Save Path (-S):");

		sourcePathText = new Text(container, SWT.BORDER);
		final GridData gd_sourcePathText = new GridData(SWT.FILL, SWT.CENTER, true, false);
		sourcePathText.setLayoutData(gd_sourcePathText);

		final Button browserButton = new Button(container, SWT.NONE);
		browserButton.setLayoutData(new GridData());
		browserButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
				ContainerSelectionDialog dialog = new ContainerSelectionDialog(   
		                shell,
		                ResourcesPlugin.getWorkspace().getRoot(),
		                true,
		                "Please select a Path from the tree");
		        if (dialog.open() == ContainerSelectionDialog.OK) {   
		            Object[] result = dialog.getResult();
		            if (result.length == 1) {
		            	String selectedPath = ((Path) result[0]).toString();
		                sourcePathText.setText(selectedPath);
		            }
		            dialog.close();
		        }
			}
		});
		browserButton.setText("&Browse...");

		final Label packageNameLabel = new Label(container, SWT.NONE);
		packageNameLabel.setLayoutData(new GridData());
		packageNameLabel.setToolTipText("Specify a directory path for generated resources");
		packageNameLabel.setText("Resource Save Path (-R):");

		resourcePathText = new Text(container, SWT.BORDER);
		final GridData gd_resourcePathText = new GridData(SWT.FILL, SWT.CENTER, true, false);
		resourcePathText.setLayoutData(gd_resourcePathText);

		final Button browseButton = new Button(container, SWT.NONE);
		browseButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
				ContainerSelectionDialog dialog = new ContainerSelectionDialog(   
		                shell,
		                ResourcesPlugin.getWorkspace().getRoot(),
		                true,
		                "Please select a Path from the tree");
		        if (dialog.open() == ContainerSelectionDialog.OK) {   
		            Object[] result = dialog.getResult();
		            if (result.length == 1) {
		            	String selectedPath = ((Path) result[0]).toString();
		                resourcePathText.setText(selectedPath);
		            }
		            dialog.close();
		        }
			}
		});
		final GridData gd_browseButton = new GridData();
		browseButton.setLayoutData(gd_browseButton);
		browseButton.setText("&Browse...");

		final Label skeltonInterfaceNameLabel = new Label(container, SWT.NONE);
		skeltonInterfaceNameLabel.setLayoutData(new GridData());
		skeltonInterfaceNameLabel.setToolTipText("used to specify a name for skelton interface other than the default one ");
		skeltonInterfaceNameLabel.setText("Skelton interface name (-sin):");

		skeltonInterfaceNameText = new Text(container, SWT.BORDER);
		final GridData gd_skeltonInterfaceNameText = new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1);
		skeltonInterfaceNameText.setLayoutData(gd_skeltonInterfaceNameText);

		final Label skeltonClassNameLabel = new Label(container, SWT.NONE);
		skeltonClassNameLabel.setToolTipText("used to specify a name for skelton class other than the default ");
		skeltonClassNameLabel.setText("Skelton class name (-scn):");

		skeltonClassNameText = new Text(container, SWT.BORDER);
		final GridData gd_skeltonClassNameText = new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1);
		skeltonClassNameText.setLayoutData(gd_skeltonClassNameText);
		setTitle("WSDL2Java Command Setting");
		setMessage("Fill the detail infomation so that generating the java code");
		
		//
		return area;
	}

	/**
	 * Create contents of the button bar
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
		m_bindingContext = initDataBindings();
	}

	/**
	 * Return the initial size of the dialog
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(500, 451);
	}
	
	protected DataBindingContext initDataBindings() {
		IObservableValue skeltonClassNameTextTextObserveWidget = SWTObservables.observeText(skeltonClassNameText, SWT.Modify);
		IObservableValue skeltonInterfaceNameTextTextObserveWidget = SWTObservables.observeText(skeltonInterfaceNameText, SWT.Modify);
		IObservableValue infoSkeltonClassNameObserveValue = BeansObservables.observeValue(info, "skeltonClassName");
		IObservableValue infoCodePathObserveValue = BeansObservables.observeValue(info, "sourcePath");
		IObservableValue wsdlFileLocationTextTextObserveWidget = SWTObservables.observeText(wsdlFileLocationText, SWT.Modify);
		IObservableValue infoWsdlFileLocationObserveValue = BeansObservables.observeValue(info, "wsdlFileLocation");
		IObservableValue resourcePathTextTextObserveWidget = SWTObservables.observeText(resourcePathText, SWT.Modify);
		IObservableValue infoSkeltonInterfaceNameObserveValue = BeansObservables.observeValue(info, "skeltonInterfaceName");
		IObservableValue infoResourcePathObserveValue = BeansObservables.observeValue(info, "resourcePath");
		IObservableValue sourcePathTextTextObserveWidget = SWTObservables.observeText(sourcePathText, SWT.Modify);
		//
		//
		DataBindingContext bindingContext = new DataBindingContext();
		//
		bindingContext.bindValue(sourcePathTextTextObserveWidget, infoCodePathObserveValue, null, null);
		bindingContext.bindValue(resourcePathTextTextObserveWidget, infoResourcePathObserveValue, null, null);
		bindingContext.bindValue(skeltonInterfaceNameTextTextObserveWidget, infoSkeltonInterfaceNameObserveValue, null, null);
		bindingContext.bindValue(skeltonClassNameTextTextObserveWidget, infoSkeltonClassNameObserveValue, null, null);
		bindingContext.bindValue(wsdlFileLocationTextTextObserveWidget, infoWsdlFileLocationObserveValue, null, null);
		//
		return bindingContext;
	}

}
