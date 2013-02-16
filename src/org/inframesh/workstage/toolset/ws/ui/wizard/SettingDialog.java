/* 
 *  Copyright (c)2009-2010 The Inframesh Software Foundation (ISF)
 *
 *  Licensed under the Inframesh Software License (the "License"), 
 *	Version 1.0 ; you may obtain a copy of the license at
 *
 *  	http://www.inframesh.org/licenses/LICENSE-1.0
 *
 *  Software distributed under the License is distributed  on an "AS IS" 
 *  BASIS but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the License 
 *  for more details.
 *  
 *  Inframesh Software Foundation is donated by Drowell Technology Limited.
 */
package org.inframesh.workstage.toolset.ws.ui.wizard;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.ui.JavaElementLabelProvider;
import org.eclipse.jdt.ui.StandardJavaElementContentProvider;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.inframesh.fundus.base.text.Texts;
import org.eclipse.swt.widgets.Button;

/**
 * 
 * @since soadtoolkit
 * @version 
 *
 * @author <a href="mailto:josh.yoah@gmail.com">杨超 </a>
 */
public class SettingDialog extends TitleAreaDialog {
	private IFile file;
	
	private TreeViewer viewer;
	private Text txtWsdlFile;
	private Text txtWsdlNsURI;
	private Text txtTypeXsdFile;
	private Text txtTypeNs;
	private Text txtTypeNsURI;
	private Text txtElemXsdFile;
	private Text txtElemNs;
	private Text txtElemNsURI;
	private Button btnMixSchemaTypes;

	public String serviceName;
	public String wsdlFile;
	public String typeXsdFile;
	public String typeNsURI;
	public String typeNs;
	public String elemXsdFile;
	public String elemNsURI;
	public String elemNs;
	public String wsdlNsURI;
	public boolean mixed;
	
	public List<IMethod> methods = new ArrayList<IMethod>();
	private Text txtServiceName;

	
	
	

	public SettingDialog(Shell parentShell, IFile file) {
		super(parentShell);
		this.file = file;
	}
	
	@Override
	protected Control createDialogArea(Composite parent) {
		setTitle("Java to WSDL/Schema Setting");
		setMessage("Selection method to expose as WebService and related settings");
		
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		container.setLayout(gridLayout);
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		viewer= new TreeViewer(container);
		// Provide members of a compilation unit or class file
	    ITreeContentProvider contentProvider= new StandardJavaElementContentProvider(true);
	    viewer.setContentProvider(contentProvider);
	    // There are more flags defined in class JavaElementLabelProvider
	    ILabelProvider labelProvider= new JavaElementLabelProvider(
	    	JavaElementLabelProvider.SHOW_PARAMETERS |
//	        JavaElementLabelProvider.SHOW_DEFAULT |
//	        JavaElementLabelProvider.SHOW_QUALIFIED |
//	        JavaElementLabelProvider.SHOW_ROOT |
	        JavaElementLabelProvider.SHOW_RETURN_TYPE);
	    viewer.setLabelProvider(labelProvider);
	    // Using the Java model as the viewers input present Java projects on the first level.
	    viewer.setInput(JavaCore.createCompilationUnitFrom(file));
	    viewer.addFilter(new ViewerFilter() {

			@Override
			public boolean select(Viewer viewer, Object parentElement, Object element) {
				if(element instanceof IType || element instanceof IMethod)
					return true;
				
				return false;
			}
	    	
	    });
	    Tree tree = viewer.getTree();
		GridData gd_tree = new GridData(SWT.FILL, SWT.FILL, false, false, 3, 1);
		gd_tree.heightHint = 200;
		tree.setLayoutData(gd_tree);
		
		Label lblServiceName = new Label(container, SWT.NONE);
		lblServiceName.setText("Service Name:");
		
		txtServiceName = new Text(container, SWT.BORDER);
//		txtServiceName.setText("SpotExchageService");
		txtServiceName.setToolTipText("e.g. SpotExchageService");
		txtServiceName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		Label lblWsdlFile = new Label(container, SWT.NONE);
		lblWsdlFile.setText("WSDL File:");
		
		txtWsdlFile = new Text(container, SWT.BORDER);
//		txtWsdlFile.setText("SpotExchageService.wsdl");
		txtWsdlFile.setToolTipText("e.g. SpotExchageService.wsdl");
		GridData gd_txtWsdl = new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1);
		gd_txtWsdl.minimumWidth = 300;
		txtWsdlFile.setLayoutData(gd_txtWsdl);
		
		Label lblWsdlNamespace = new Label(container, SWT.NONE);
		lblWsdlNamespace.setText("WSDL Namespace:");
		
		txtWsdlNsURI = new Text(container, SWT.BORDER);
//		txtWsdlNsURI.setText("http://mosc.service.bankcomm.com/businessfunction/composite/financialmarket.SpotExchangeService");
		txtWsdlNsURI.setToolTipText("e.g. http://mosc.service.bankcomm.com/businessfunction/composite/financialmarket.SpotExchangeService");
		txtWsdlNsURI.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		btnMixSchemaTypes = new Button(container, SWT.CHECK);
		btnMixSchemaTypes.setSelection(true);
		btnMixSchemaTypes.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 3, 1));
		btnMixSchemaTypes.setText("Mix schema Types and Element defination in one file");
		
		final Label lblTypeSchemaFile = new Label(container, SWT.NONE);
		lblTypeSchemaFile.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblTypeSchemaFile.setText("XSD File:");
		
		txtTypeXsdFile = new Text(container, SWT.BORDER);
		txtTypeXsdFile.setToolTipText("e.g. FxService.xsd");
//		txtTypeXsdFile.setText("FxService.xsd");
		txtTypeXsdFile.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		final Label lblTypeXsdNamespace = new Label(container, SWT.NONE);
		GridData gd_lblTypeXsdNamespace = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_lblTypeXsdNamespace.widthHint = 155;
		lblTypeXsdNamespace.setLayoutData(gd_lblTypeXsdNamespace);
		lblTypeXsdNamespace.setText("XSD Namespace:");
		
		txtTypeNs = new Text(container, SWT.BORDER);
//		txtTypeNs.setText("fx");
		txtTypeNs.setToolTipText("e.g. fx");
		GridData gd_txtTypeNs = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_txtTypeNs.widthHint = 30;
		gd_txtTypeNs.minimumWidth = 40;
		txtTypeNs.setLayoutData(gd_txtTypeNs);
		
		txtTypeNsURI = new Text(container, SWT.BORDER);
		txtTypeNsURI.setToolTipText("e.g. http://mosc.service.bankcomm.com/businessfunction/atomic/financialmarket.FxService/Schema");
//		txtTypeNsURI.setText("http://mosc.service.bankcomm.com/businessfunction/atomic/financialmarket.FxService/Schema");
		txtTypeNsURI.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		final Composite composite = new Composite(container, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 3, 1));
		final GridLayout gridLayout1 = new GridLayout();
		gridLayout1.numColumns = 3;
		composite.setLayout(gridLayout1);
		composite.setVisible(false);
		
		Label lblElementXsdFile = new Label(composite, SWT.NONE);
		lblElementXsdFile.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblElementXsdFile.setText("Element XSD File:");
		
		txtElemXsdFile = new Text(composite, SWT.BORDER);
//		txtElemXsdFile.setText("SpotExchangeService.xsd");
		txtElemXsdFile.setToolTipText("e.g. SpotExchangeService.xsd");
		txtElemXsdFile.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		Label lblElementXsdNamespace = new Label(composite, SWT.NONE);
		lblElementXsdNamespace.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblElementXsdNamespace.setText("Element XSD Namespace:");
		
		txtElemNs = new Text(composite, SWT.BORDER);
//		txtElemNs.setText("mosc");
		txtElemNs.setToolTipText("e.g. mosc");
		GridData gd_txtElemNs = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_txtElemNs.widthHint = 30;
		txtElemNs.setLayoutData(gd_txtElemNs);
		
		txtElemNsURI = new Text(composite, SWT.BORDER);
//		txtElemNsURI.setText("http://mosc.service.bankcomm.com/businessfunction/composite/financialmarket.SpotExchangeService/Schema");
		txtElemNsURI.setToolTipText("e.g. http://mosc.service.bankcomm.com/businessfunction/composite/financialmarket.SpotExchangeService/Schema");
		txtElemNsURI.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		
		btnMixSchemaTypes.addSelectionListener(new SelectionAdapter() {
			/**
			 * Sent when selection occurs in the control.
			 * The default behavior is to do nothing.
			 *
			 * @param e an event containing information about the selection
			 */
			public void widgetSelected(SelectionEvent e) {
				if(btnMixSchemaTypes.getSelection()) {
					composite.setVisible(false);
					lblTypeSchemaFile.setText("Type XSD File:");
					lblTypeXsdNamespace.setText("XSD Namespace:");
				} else {
					composite.setVisible(true);
					lblTypeSchemaFile.setText("XSD File:");
					lblTypeXsdNamespace.setText("Namespace:");
				}
			}

			/**
			 * Sent when default selection occurs in the control.
			 * The default behavior is to do nothing.
			 *
			 * @param e an event containing information about the default selection
			 */
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		
		
		
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
	}

	/**
	 * Return the initial size of the dialog
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(613, 613);
	}
	
	@Override
	protected void okPressed() {
		this.serviceName = txtServiceName.getText();
		this.wsdlFile = txtWsdlFile.getText();
		this.wsdlNsURI = txtWsdlNsURI.getText();
		this.typeXsdFile = txtTypeXsdFile.getText();
		this.typeNsURI = txtTypeNsURI.getText();
		this.typeNs = txtTypeNs.getText();
		this.elemXsdFile = txtElemXsdFile.getText();
		this.elemNsURI = txtElemNsURI.getText();
		this.elemNs = txtElemNs.getText();
		
		this.mixed = btnMixSchemaTypes.getSelection();
		
		IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();//may cast exception
		for(Object obj : selection.toList()) {
			if(obj instanceof IMethod) {
				methods.add((IMethod) obj);
			}
		}
		
		if(! validate()) return;

	    super.okPressed();
	}


	private boolean validate() {
		if(methods.size() == 0) {
			setErrorMessage("At lease one method must be selected!"); return false;
		}
		
		if(! Texts.hasLength(serviceName)) {
			setErrorMessage("Service name required!"); return false;
		}
		
		if(! Texts.hasLength(wsdlFile)) {
			setErrorMessage("WSDL file name required!"); return false;
		}
		
		if(! Texts.hasLength(wsdlFile)) {
			setErrorMessage("WSDL file name required!"); return false;
		}
		
		if(! Texts.hasLength(wsdlNsURI)) {
			setErrorMessage("WSDL namespace URI required!"); return false;
		}
		
		if(! Texts.hasLength(typeXsdFile)) {
			setErrorMessage("Type XSD file name required!"); return false;
		}
		
		if(! Texts.hasLength(typeNsURI)) {
			setErrorMessage("Type XSD namespace URI required!"); return false;
		}
		
		if(! Texts.hasLength(typeNs)) {
			setErrorMessage("Type XSD namespace required!"); return false;
		}
		
		if(! Texts.hasLength(elemXsdFile) && !mixed) {
			setErrorMessage("Element XSD file name required!"); return false;
		}
		
		if(! Texts.hasLength(elemNsURI)&& !mixed) {
			setErrorMessage("Element XSD namespace URI required!"); return false;
		}
		
		if(! Texts.hasLength(elemNs)&& !mixed) {
			setErrorMessage("Element XSD namespace required!"); return false;
		}
		
		return true;
	}

}
