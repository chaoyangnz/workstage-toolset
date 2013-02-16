package org.inframesh.workstage.toolset.ormapper.ui.wizard;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Composite;
import org.inframesh.workstage.toolset.ormapper.core.BeanGenerator;
import org.inframesh.workstage.toolset.ormapper.core.model.PathSetting;


public class BeanGenerationWizard extends Wizard {

	private BeanGenerationWizard outer = this;
	
	private BeanRulePage brPage;
	private TableSelectionPage tsPage;
	private MapSettingPage msPage;
	private PathSettingPage psPage;
	
	public BeanGenerationWizard() {
		super();
		// this.setDialogSettings(settings);
		this.setTitleBarColor(new RGB(255, 0, 0));
		this.setNeedsProgressMonitor(true);
		this.setWindowTitle("Bean Generation Wizard");
	}

	@Override
	public void createPageControls(Composite pageContainer) {
		// super.createPageControls(pageContainer);
	}

	@Override
	public boolean performFinish() {
//		Display display = outer.getShell().getDisplay();
//		display.asyncExec(new Runnable() {
//			public void run() {
//				
//			}
//		});
		
		BeanGenerator beanGenerator = BeanGenerator.getInstance();
		
		beanGenerator.setTables(msPage.list.getItems());
		
		PathSetting pathSetting = new PathSetting();
		pathSetting.setPath(psPage.folderChooser.getFullPath());
		pathSetting.setPackageName(psPage.packageNameText.getText());
		
		beanGenerator.setPathSetting(pathSetting);//psPage.pathSetting);
		
		beanGenerator.setSettingMap(msPage.map);
		
		beanGenerator.setRule(brPage.rule);

		try {
			beanGenerator.generate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		MessageDialog.openInformation(null, "Info", "Completed!");

		return true;
	}

	@Override
	public void addPages() {
		brPage = new BeanRulePage("brPage");
		this.addPage(brPage);
		tsPage = new TableSelectionPage("tsPage");
		this.addPage(tsPage);
		msPage = new MapSettingPage("msPage");
		this.addPage(msPage);
		psPage = new PathSettingPage("psPage");
		this.addPage(psPage);
	}

}
