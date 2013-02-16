package org.inframesh.workstage.toolset.ormapper.core;

import org.eclipse.jface.preference.IPreferenceStore;
import org.inframesh.workstage.toolset.Activator;
import org.inframesh.workstage.toolset.ui.preference.PreferenceConstants;


public class PluginToolkit {
	
	public static String convertName(String name, String seprator,
			boolean isLowerCaseInCapital) {
		String[] fragments = name.trim().split(seprator);
		String newName = "";
		for (int i = 0; i < fragments.length; ++i) {
			if (i == 0 && isLowerCaseInCapital) {
				newName += fragments[i].toLowerCase();
			} else {
				newName += fragments[i].substring(0, 1).toUpperCase()
						+ fragments[i].substring(1).toLowerCase();
			}
		}

		return newName;
	}

	public static String convertType(String jdbcType, String database, String version) {
		IPreferenceStore pref = Activator.getDefault().getPreferenceStore();
		
		String javaType = "String";
		if ("DB2".equals(database.toUpperCase())&& "8.x".equals(version.toLowerCase())) {
		   if ("CHARACTER".equals(jdbcType.toUpperCase())
					|| "CHAR".equals(jdbcType.toUpperCase())) {
				javaType= pref.getString(PreferenceConstants.MAP_CHAR_PREF);
			} else if ("VARCHAR".equals(jdbcType.toUpperCase())			
					|| "LONG VARCHAR".equals(jdbcType.toUpperCase())) {
				javaType= pref.getString(PreferenceConstants.MAP_VARCHAR_PREF);
			} else if ("INTEGER".equals(jdbcType.toUpperCase())) {
				javaType = pref.getString(PreferenceConstants.MAP_INTEGER_PREF);
			} else if ("BIGINT".equals(jdbcType.toUpperCase())) {
				javaType = pref.getString(PreferenceConstants.MAP_BIGINT_PREF);
			} else if ("SMALLINT".equals(jdbcType.toUpperCase())) {
				javaType = pref.getString(PreferenceConstants.MAP_SMALLINT_PREF);
			} else if ("FLOAT".equals(jdbcType.toUpperCase())) {
				javaType = pref.getString(PreferenceConstants.MAP_FLOAT_PREF);
			} else if ("DOUBLE".equals(jdbcType.toUpperCase())) {
				javaType = pref.getString(PreferenceConstants.MAP_DOUBLE_PREF);
			} else if ("REAL".equals(jdbcType.toUpperCase())) {
				javaType = pref.getString(PreferenceConstants.MAP_REAL_PREF);
			} else if ("NUMERIC".equals(jdbcType.toUpperCase())) {
				javaType = pref.getString(PreferenceConstants.MAP_NUMERIC_PREF);
			} else if ("DECIMAL".equals(jdbcType.toUpperCase())) {
				javaType = pref.getString(PreferenceConstants.MAP_DECIMAL_PREF);
			} else if ("DATE".equals(jdbcType.toUpperCase())) {
				javaType = pref.getString(PreferenceConstants.MAP_DATE_PREF);
			} else if ("TIME".equals(jdbcType.toUpperCase())) {
				javaType = pref.getString(PreferenceConstants.MAP_TIME_PREF);
			} else if ("TIMESTAMP".equals(jdbcType.toUpperCase())) {
				javaType = pref.getString(PreferenceConstants.MAP_TIMESTAMP_PREF);
			} else if ("BLOB".equals(jdbcType.toUpperCase())
					|| "DBCLOB".equals(jdbcType.toUpperCase())) {
				javaType = pref.getString(PreferenceConstants.MAP_BLOB_PREF);
			} else if ("CLOB".equals(jdbcType.toUpperCase())) {
				javaType = pref.getString(PreferenceConstants.MAP_CLOB_PREF);
			} else if ("GRAPHIC".equals(jdbcType.toUpperCase())
					|| "VARGRAPHIC".equals(jdbcType.toUpperCase())
					|| "LONG VARGRAPHIC".equals(jdbcType.toUpperCase())) {
				javaType = pref.getString(PreferenceConstants.MAP_GRAPHIC_PREF);
			} else {
				javaType = "Object";
			}
		}

		return javaType;
	}

}
