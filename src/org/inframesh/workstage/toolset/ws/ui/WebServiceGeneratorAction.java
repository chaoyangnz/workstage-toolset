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
package org.inframesh.workstage.toolset.ws.ui;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.inframesh.eclipse.util.Logger;
import org.inframesh.workstage.toolset.ws.core.WebServiceGenerator;
import org.inframesh.workstage.toolset.ws.ui.wizard.SettingDialog;


/**
 * 
 * @since soadtoolkit
 * @version 
 *
 * @author <a href="mailto:josh.yoah@gmail.com">杨超 </a>
 */
public class WebServiceGeneratorAction implements IObjectActionDelegate {

	private IStructuredSelection selection;

	public void run(IAction action) {
//		List<IFile> files = selection.toList();
		IFile file = (IFile) selection.getFirstElement();
		
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		
		SettingDialog dialog = new SettingDialog(shell, file);
		if (dialog.open() == IDialogConstants.OK_ID) {
			WebServiceGenerator generator = new WebServiceGenerator();
			generator.file = file;
			generator.serviceName = dialog.serviceName;
			generator.wsdlFile = dialog.wsdlFile;
			generator.wsdlNsURI = dialog.wsdlNsURI;
			generator.typeXsdFile = dialog.typeXsdFile;
			generator.typeNsURI = dialog.typeNsURI;
			generator.typeNs = dialog.typeNs;
			generator.elemXsdFile = dialog.elemXsdFile;
			generator.elemNsURI = dialog.elemNsURI;
			generator.elemNs = dialog.elemNs;
			generator.methods = dialog.methods;
			
			generator.mixed = dialog.mixed;
			
			try {
				generator.run();
			} catch (Exception e) {//http://mosc.service.bankcomm.com/businessfunction/atomic/financialmarket.FxService/Schema
				Logger.log(Logger.ERROR, e);
			}
			
			MessageDialog.openInformation(shell, "Info", "Completed!");
		}
	}

	public void selectionChanged(IAction action, ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			this.selection = (IStructuredSelection) selection;
		}
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IObjectActionDelegate#setActivePart(org.eclipse.jface.action.IAction, org.eclipse.ui.IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		// TODO Auto-generated method stub
		
	}

}
