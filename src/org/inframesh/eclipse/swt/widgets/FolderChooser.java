package org.inframesh.eclipse.swt.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Text;

public class FolderChooser extends Composite {

	private String fullPath;
	
	private final Text text;
	private final Button browseButton;

	public FolderChooser(final Composite parent, int style) {
		super(parent, style);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;
		gridLayout.horizontalSpacing = 0;
		gridLayout.numColumns = 2;
		setLayout(gridLayout);

		text = new Text(this, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		browseButton = new Button(this, SWT.NONE);
		final GridData gd_browseButton = new GridData();
		gd_browseButton.horizontalIndent = 2;
		browseButton.setLayoutData(gd_browseButton);
		browseButton.setText("Br&owse...");

		browseButton.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				DirectoryDialog dialog = new DirectoryDialog(parent.getShell(),
						SWT.NONE);

				// String workspaceRoot =
				// ResourcesPlugin.getWorkspace().getRoot().getFullPath().makeAbsolute().toOSString();
				// dialog.setFilterPath(ResourcesPlugin.getWorkspace().getRoot().getFullPath().makeAbsolute().toOSString());
				String path = dialog.open();
				text.setText(path);
			}
		});
	}
	

	public String getFullPath() {
		this.fullPath = text.getText();
		return fullPath;
		
	}
	
	public void addModifyListener(ModifyListener listener) {
		this.text.addModifyListener(listener);
	}

	public void setFilterPath(String string) {

	}

}
