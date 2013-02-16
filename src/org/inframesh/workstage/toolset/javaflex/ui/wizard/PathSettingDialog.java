package org.inframesh.workstage.toolset.javaflex.ui.wizard;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.inframesh.eclipse.swt.widgets.FolderChooser;

public class PathSettingDialog extends TitleAreaDialog {

	private FolderChooser folderChooser;
	private Text packageNameText;
	
	public String packageName;
	public String path;
	/**
	 * Create the dialog
	 * @param parentShell
	 */
	public PathSettingDialog(Shell parentShell) {
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
		gridLayout.numColumns = 2;
		container.setLayout(gridLayout);
		container.setLayoutData(new GridData(GridData.FILL_BOTH));

		final Label savePathLabel = new Label(container, SWT.NONE);
		savePathLabel.setText("Save Path:");
		
		folderChooser = new FolderChooser(container, SWT.NONE);
		folderChooser.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		final Label packageNameLabel = new Label(container, SWT.NONE);
		packageNameLabel.setText("Package Name:");

		packageNameText = new Text(container, SWT.BORDER);
		packageNameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		setTitle("Path Setting");
		setMessage("Select a location to save  flex actionscript file");
		
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
	}

	/**
	 * Return the initial size of the dialog
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(500, 375);
	}
	
	@Override
	protected void okPressed() {
		this.packageName = this.packageNameText.getText();
		this.path = this.folderChooser.getFullPath();

	    super.okPressed();
	}

}
