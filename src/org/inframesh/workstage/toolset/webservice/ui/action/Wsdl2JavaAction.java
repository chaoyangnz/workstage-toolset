package org.inframesh.workstage.toolset.webservice.ui.action;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.inframesh.workstage.toolset.ui.console.Console;
import org.inframesh.workstage.toolset.webservice.ui.wizard.Wsdl2JavaSettingDialog;


public class Wsdl2JavaAction implements IObjectActionDelegate {
	
	private IStructuredSelection selection;

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		// TODO Auto-generated method stub
		
	}

	public void run(IAction action) {
		IFile wsdl = (IFile) selection.getFirstElement();
		
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		
		
		
		Wsdl2JavaSettingDialog dialog = new Wsdl2JavaSettingDialog(shell);
        dialog.info.setWsdlFileLocation(wsdl.getFullPath().toString());
		if (dialog.open() == IDialogConstants.OK_ID) {  
			String projectName = dialog.info.getSourcePath().split("/")[1];
			Console.out.println("ProjectName: " + projectName);
			String projectRoot = root.findMember("/" + projectName).getLocation().toOSString();
			Console.out.println("ProjectRoot: " + projectRoot);
			Console.out.println("ProjectRoot uplevel: " + projectRoot.replace("\\" + projectName, ""));
        	String cmd = "\"D:\\ICMS\\axis2\\bin\\wsdl2java.bat\" " 
        				 + "-o \"" 
        				 + projectRoot.replace("\\" + projectName, "")
        				 + "\" -ss -ssi -u -uw -or -noBuildXML -S \"" 
        				 + dialog.info.getSourcePath().substring(1)
        				 + "\" -R \""
        				 + dialog.info.getResourcePath().substring(1)
        				 + "\" -sin "
        				 + dialog.info.getSkeltonInterfaceName()
        				 + " -scn "
        				 + dialog.info.getSkeltonClassName()
        				 + " -uri \"" 
        				 + root.findMember(dialog.info.getWsdlFileLocation()).getLocation().toOSString()
        				 + "\"";
        	Console.out.println(cmd);

			try {
				final Process process = Runtime.getRuntime().exec(cmd);
				new Runnable() {
					public void run() {
						BufferedInputStream stdin = new BufferedInputStream(process.getInputStream());
					    BufferedReader br = new BufferedReader(new InputStreamReader(stdin));
					    String s;
					    try {
							while ((s = br.readLine()) != null)
							   Console.out.println(s);
						} catch (IOException e) {
							e.printStackTrace();
						}				
					}
				}.run();
				new Runnable() {
					public void run() {
						BufferedInputStream stderr = new BufferedInputStream(process.getErrorStream());
					    BufferedReader br = new BufferedReader(new InputStreamReader(stderr));
					    String s;
					    try {
							while ((s = br.readLine()) != null)
							   Console.out.println(s);
						} catch (IOException e) {
							e.printStackTrace();
						}				
					}
					
				}.run();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
		
	}

	public void selectionChanged(IAction action, ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			this.selection = (IStructuredSelection) selection;
		}
		
	}

}
