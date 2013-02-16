package org.inframesh.workstage.toolset.xml.validator;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.jface.preference.IPreferenceStore;
import org.inframesh.workstage.toolset.Activator;
import org.inframesh.workstage.toolset.ui.preference.PreferenceConstants;


public class Setting {

	private final static String SPRING_XSD = "";//"spring-beans-2.5.xsd";
	private final static String SPRING_SCH = "spring.sch";
	private final static String STRUTS_XSD = "";//"spring-beans-2.5.xsd";//"type.xsd";
	private final static String STRUTS_SCH = "struts.sch";
	private final static String DIAGNOSE_XSL = "Schematron1.5.xsl";

	private String springXsdLocation;
	private String springSchLocation;
	
	private String strutsXsdLocation;
	private String strutsSchLocation;
	
	private String diagnoseXslLocation;

	private final static String XSD_PATH = "xsd";
	private final static String SCHEMATRON_PATH = "sch";
	private final static String DIAGNOSE_PATH = "scripts";

	private static Setting instance;

	private Setting() {

	}
	
	public static boolean filterFileName(String fileName, String filter) {
		
		filter = filter.replace('.', '#'); // *��ʾ�������ַ�0+��
		filter = filter.replaceAll("#", "\\\\.");
		filter = filter.replace('*', '#');
		filter = filter.replaceAll("#", ".*");
		filter = filter.replace('?', '#'); // ?��ʾ���ⵥ���ַ�,������
        filter = filter.replaceAll("#", ".");
        filter = filter.replace('%', '#'); // %��ʾ���ⵥ���ַ�, ����0�λ�1��
        filter = filter.replaceAll("#", ".?");
	    filter = "^" + filter + "$";
	
	    System.out.println(filter);
	    Pattern p = Pattern.compile(filter);
	    
	    Matcher fMatcher = p.matcher(fileName);
	    if (fMatcher.matches()) {
	    	return true;
	    }
	    return false;
	}
	
	private static boolean filterSpringConfigureName(String fileName) {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();		
		String filter = store.getString(PreferenceConstants.VALIDATION_SPRING_FILE_FILTER);
		
		return filterFileName(fileName, filter);
	}
	
	private static boolean filterStrutsConfigureName(String fileName) {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();		
		String filter = store.getString(PreferenceConstants.VALIDATION_STRUTS_FILE_FILTER);
		
		return filterFileName(fileName, filter);
	}


	public String getSchematronFile(IFile file) {
			
		Setting setting = Setting.getInstance();

		String fileName = file.getName();
		if (filterSpringConfigureName(fileName)) {
			return setting.getSpringSchLocation();
		} 
		
		if(filterStrutsConfigureName(fileName)) {
			return setting.getStrutsSchLocation();
		}
		return null;
	}

	public String getXSDFile(IFile file) {
		
		Setting setting = Setting.getInstance();

		String fileName = file.getName();
		if (filterSpringConfigureName(fileName)) {
			return setting.getSpringXsdLocation();
		}
		
		if(filterStrutsConfigureName(fileName)) {
			return setting.getStrutsXsdLocation();
		}
		return null;
	}

	public static Setting getInstance() {
		if (instance == null) {
			instance = new Setting();
			instance.initialize();
		}
		return instance;
	}

	public String getPluginPath() {

		URL bundleRootURL = Activator.getDefault().getBundle().getEntry("/");
		try {
			URL pluginURL = FileLocator.resolve(bundleRootURL);
			String path = pluginURL.getPath();
			
			return path;
		} catch (IOException e) {
			System.out.println("Cannot resolve in Setting.java/getPluginPath");
			return null;
		}

	}

	public void initialize() {
		try {
			// �õ���ǰplugin��jar�ļ�
			String pluginPath = getPluginPath();
			
			String pluginConfigPath = pluginPath.contains(".jar") ? pluginPath.replace(".jar!", "")
																  : pluginPath;
			
			System.out.println("Plugin cofigure root path: " + pluginConfigPath);
			
			String xsdPath = pluginConfigPath + XSD_PATH;
			String schPath = pluginConfigPath + SCHEMATRON_PATH;
			String diagnoseXSLPath = pluginConfigPath + DIAGNOSE_PATH;
			
			setSpringXsdLocation(xsdPath + File.separator + SPRING_XSD);
			setSpringSchLocation(schPath + File.separator + SPRING_SCH);
			
			setStrutsXsdLocation(xsdPath + File.separator + STRUTS_XSD);
			setStrutsSchLocation(schPath + File.separator + STRUTS_SCH);
			
			setDiagnoseXslLocation(diagnoseXSLPath + File.separator	+ DIAGNOSE_XSL);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getSpringXsdLocation() {
		return springXsdLocation;
	}

	public void setSpringXsdLocation(String springXsdLocation) {
		this.springXsdLocation = springXsdLocation;
	}

	public String getSpringSchLocation() {
		return springSchLocation;
	}

	public void setSpringSchLocation(String typeSchLocation) {
		this.springSchLocation = typeSchLocation;
	}

	public String getStrutsXsdLocation() {
		return strutsXsdLocation;
	}

	public void setStrutsXsdLocation(String strutsXsdLocation) {
		this.strutsXsdLocation = strutsXsdLocation;
	}

	public String getStrutsSchLocation() {
		return strutsSchLocation;
	}

	public void setStrutsSchLocation(String strutsSchLocation) {
		this.strutsSchLocation = strutsSchLocation;
	}

	public String getDiagnoseXslLocation() {
		return diagnoseXslLocation;
	}

	public void setDiagnoseXslLocation(String diagnoseXSLLocation) {
		this.diagnoseXslLocation = diagnoseXSLLocation;
	}
}
