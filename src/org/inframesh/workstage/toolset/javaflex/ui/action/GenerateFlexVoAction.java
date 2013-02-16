package org.inframesh.workstage.toolset.javaflex.ui.action;

import java.util.List;

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
import org.inframesh.workstage.toolset.javaflex.core.BeanConverter;
import org.inframesh.workstage.toolset.javaflex.ui.wizard.PathSettingDialog;


public class GenerateFlexVoAction implements IObjectActionDelegate {

	private IStructuredSelection selection;

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		// TODO Auto-generated method stub

	}

	public void run(IAction action) {

		List list = selection.toList();
		
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		
		PathSettingDialog dialog = new PathSettingDialog(null);
		String voPackageRootFolder = "";
		String voPackageName = "";
		if (dialog.open() == IDialogConstants.OK_ID) {
			voPackageRootFolder = dialog.path;
			voPackageName = dialog.packageName;
			
			for(IFile file : (List<IFile>)list) { // ������ֻ��Ҫѡ��һ������
//	   		String fullPath = file.getFullPath().toOSString(); // �ļ�·��
		
				try {
					BeanConverter.generaterActionscriptFile(file, voPackageRootFolder, voPackageName);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			MessageDialog.openInformation(null, "Info", "Completed!");
		}
		
	}

	public void selectionChanged(IAction action, ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			this.selection = (IStructuredSelection) selection;
		}
	}

}
