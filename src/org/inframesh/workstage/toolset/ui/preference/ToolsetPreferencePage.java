package org.inframesh.workstage.toolset.ui.preference;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.inframesh.workstage.toolset.resource.ResourceToolkit;


public class ToolsetPreferencePage extends PreferencePage implements
		IWorkbenchPreferencePage {
	public ToolsetPreferencePage() {
	}

	@Override
	protected Control createContents(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout());
		Label label = new Label(composite, SWT.NONE);
		label.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
//		label.setText("xxx");
		label.setImage(ResourceToolkit.getImageRegistry().get(ResourceToolkit.PLUGINICON));

		final Label label_1 = new Label(composite, SWT.NONE);
		label_1.setText("Workstage Toolset (c) 2009 Inframesh.org\n Contributor: 杨超");
		label_1.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false));
		
		return composite;
	}

	public void init(IWorkbench workbench) {
		// TODO Auto-generated method stub
	}

}
