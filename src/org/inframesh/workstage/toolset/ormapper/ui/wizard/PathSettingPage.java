package org.inframesh.workstage.toolset.ormapper.ui.wizard;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.inframesh.eclipse.swt.widgets.FolderChooser;
import org.inframesh.workstage.toolset.ormapper.core.model.PathSetting;


public class PathSettingPage extends WizardPage {
	
	public PathSetting pathSetting = new PathSetting();
	
	public FolderChooser folderChooser;
	public Text packageNameText;
	private Combo combo;
	protected PathSettingPage(String pageName) {
		super(pageName);
		setTitle("Path and package setting");
		setDescription("Specify the path and package name");
	}

	public void createControl(Composite parent) {

		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		composite.setLayout(layout);
		
		setControl(composite);

		final Label pathSavedLabel = new Label(composite, SWT.NONE);
		pathSavedLabel.setText("Path saved:");

		folderChooser = new FolderChooser(composite, SWT.NONE);
		folderChooser.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		folderChooser.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				updatePageComplete();			
			}
		});
		
//		combo = new Combo(composite, SWT.READ_ONLY);
//		combo.setItems(new String[]{"Database Naming Convention", "Object-Oriented Naming Convention"});
//		combo.select(0);
//		combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		final Label packageNameLabel = new Label(composite, SWT.NONE);
		packageNameLabel.setText("Package Name:");

		packageNameText = new Text(composite, SWT.BORDER);
		packageNameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		
		updatePageComplete();
	}
	
	void updatePageComplete() {
	    setPageComplete(false);

	    if(folderChooser.getFullPath()==null || folderChooser.getFullPath()=="") {
	        setMessage(null);
	        setErrorMessage("Path must be selected!");
	        return;
	    }

	    setPageComplete(true);
	    setMessage(null);
	    setErrorMessage(null);
	}

}
