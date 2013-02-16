package org.inframesh.workstage.toolset;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.inframesh.workstage.toolset.core.database.DatabaseManager;
import org.inframesh.workstage.toolset.ui.preference.PreferenceConstants;
import org.osgi.framework.BundleContext;


/**
 * The activator class controls the plug-in life cycle
 */
@SuppressWarnings("unchecked")
public class Activator extends AbstractUIPlugin {

	// The shared instance
	private static Activator plugin;
	
	public static String PLUGIN_ID;
		
	public static DatabaseManager getDefaultDatabaseManager() {
		return DatabaseManager.getInstance();
	}
	
	/**
	 * The constructor
	 */
	public Activator() {
		super();
	}
	
	public static Shell getShell() {
		return PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		PLUGIN_ID = Activator.getDefault().getBundle().getSymbolicName();
		
		ToolsetConsole.init(false);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		getDefaultDatabaseManager().dispose();
		plugin = null;
		ToolsetConsole.dispose();
		
		super.stop(context);
		
		
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}
	
	@Override
	protected void initializeDefaultPreferences(IPreferenceStore store) {
		store.setDefault(PreferenceConstants.DATASOURCE_ALIAS, PreferenceConstants.DATASOURCE_ALIAS_DEFAULT);
		store.setDefault(PreferenceConstants.DATASOURCE_TYPE, PreferenceConstants.DATASOURCE_TYPE_DEFAULT);
		store.setDefault(PreferenceConstants.DATASOURCE_DRIVER_CLASS_NAME, PreferenceConstants.DATASOURCE_DRIVER_CLASS_NAME_DEFAULT);
		store.setDefault(PreferenceConstants.DATASOURCE_URL, PreferenceConstants.DATASOURCE_URL_DEFAULT);
		store.setDefault(PreferenceConstants.DATASOURCE_USERID, PreferenceConstants.DATASOURCE_URSERID_DEFAULT);
		store.setDefault(PreferenceConstants.DATASOURCE_PASSWORD, PreferenceConstants.DATASOURCE_PASSWORD_DEFAULT);
		
		store.setDefault(PreferenceConstants.MAP_CHAR_PREF, PreferenceConstants.MAP_CHAR_PREF_DEFAULT);
		store.setDefault(PreferenceConstants.MAP_VARCHAR_PREF, PreferenceConstants.MAP_VARCHAR_PREF_DEFAULT);
		store.setDefault(PreferenceConstants.MAP_INTEGER_PREF, PreferenceConstants.MAP_INTEGER_PREF_DEFAULT);
		store.setDefault(PreferenceConstants.MAP_BIGINT_PREF, PreferenceConstants.MAP_BIGINT_PREF_DEFAULT);
		store.setDefault(PreferenceConstants.MAP_SMALLINT_PREF, PreferenceConstants.MAP_SMALLINT_PREF_DEFAULT);
		store.setDefault(PreferenceConstants.MAP_FLOAT_PREF, PreferenceConstants.MAP_FLOAT_PREF_DEFAULT);
		store.setDefault(PreferenceConstants.MAP_DOUBLE_PREF, PreferenceConstants.MAP_DOUBLE_PREF_DEFAULT);
		store.setDefault(PreferenceConstants.MAP_REAL_PREF, PreferenceConstants.MAP_REAL_PREF_DEFAULT);
		store.setDefault(PreferenceConstants.MAP_NUMERIC_PREF, PreferenceConstants.MAP_NUMERIC_PREF_DEFAULT);
		store.setDefault(PreferenceConstants.MAP_DECIMAL_PREF, PreferenceConstants.MAP_DECIMAL_PREF_DEFAULT);
		store.setDefault(PreferenceConstants.MAP_DATE_PREF, PreferenceConstants.MAP_DATE_PREF_DEFAULT);
		store.setDefault(PreferenceConstants.MAP_TIME_PREF, PreferenceConstants.MAP_TIME_PREF_DEFAULT);
		store.setDefault(PreferenceConstants.MAP_TIMESTAMP_PREF, PreferenceConstants.MAP_TIMESTAMP_PREF_DEFAULT);
		store.setDefault(PreferenceConstants.MAP_BLOB_PREF, PreferenceConstants.MAP_BLOB_PREF_DEFAULT);
		store.setDefault(PreferenceConstants.MAP_CLOB_PREF, PreferenceConstants.MAP_CLOB_PREF_DEFAULT);
		store.setDefault(PreferenceConstants.MAP_GRAPHIC_PREF, PreferenceConstants.MAP_GRAPHIC_PREF_DEFAULT);
		
		store.setDefault(PreferenceConstants.CODE_FILE_COMMENTS, PreferenceConstants.CODE_FILE_COMMENTS_DEFAULT);
		store.setDefault(PreferenceConstants.CODE_MAPPING_META, PreferenceConstants.CODE_MAPPING_META_DEFAULT);
	
		store.setDefault(PreferenceConstants.VALIDATION_SPRING_FILE_FILTER, PreferenceConstants.VALIDATION_SPRING_FILE_FILTER_DEFAULT);
		store.setDefault(PreferenceConstants.VALIDATION_STRUTS_FILE_FILTER, PreferenceConstants.VALIDATION_STRUTS_FILE_FILTER_DEFAULT);
	
		store.setDefault(PreferenceConstants.VALIDATION_SPRING_SCHEMA_VALIDATION, PreferenceConstants.VALIDATION_SPRING_SCHEMA_VALIDATION_DEFAULT);
		store.setDefault(PreferenceConstants.VALIDATION_STRUTS_SCHEMA_VALIDATION, PreferenceConstants.VALIDATION_STRUTS_SCHEMA_VALIDATION_DEFAULT);
		
		store.setDefault(PreferenceConstants.VALIDATION_SPRING_SCHEMATRON_CLASSREF_VALIDATION, PreferenceConstants.VALIDATION_SPRING_SCHEMATRON_CLASSREF_VALIDATION_DEFAULT);
		store.setDefault(PreferenceConstants.VALIDATION_SPRING_SCHEMATRON_IDCONFLICT_VALIDATION, PreferenceConstants.VALIDATION_SPRING_SCHEMATRON_IDCONFLICT_VALIDATION_DEFAULT);
		store.setDefault(PreferenceConstants.VALIDATION_SPRING_SCHEMATRON_PARENTREF_VALIDATION, PreferenceConstants.VALIDATION_SPRING_SCHEMATRON_PARENTREF_VALIDATION_DEFAULT);
		store.setDefault(PreferenceConstants.VALIDATION_SPRING_SCHEMATRON_PROPERTYREF_VALIDATION, PreferenceConstants.VALIDATION_SPRING_SCHEMATRON_PROPERTYREF_VALIDATION_DEFAULT);
		store.setDefault(PreferenceConstants.VALIDATION_SPRING_SCHEMATRON_PROPERTYNAME_VALIDATION, PreferenceConstants.VALIDATION_SPRING_SCHEMATRON_PROPERTYNAME_VALIDATION_DEFAULT);
		
		store.setDefault(PreferenceConstants.VALIDATION_STRUTS_SCHEMATRON_ACTIONCLASSREF_VALIDATION, PreferenceConstants.VALIDATION_STRUTS_SCHEMATRON_ACTIONCLASSREF_VALIDATION_DEFAULT);
		store.setDefault(PreferenceConstants.VALIDATION_STRUTS_SCHEMATRON_ACTIONID_VALIDATION, PreferenceConstants.VALIDATION_STRUTS_SCHEMATRON_ACTIONID_VALIDATION_DEFAULT);
		store.setDefault(PreferenceConstants.VALIDATION_STRUTS_SCHEMATRON_RESULTNAME_VALIDATION, PreferenceConstants.VALIDATION_STRUTS_SCHEMATRON_RESULTNAME_VALIDATION_DEFAULT);
	
	}

}
