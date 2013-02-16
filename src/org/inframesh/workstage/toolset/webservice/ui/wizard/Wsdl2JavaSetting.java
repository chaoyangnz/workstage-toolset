package org.inframesh.workstage.toolset.webservice.ui.wizard;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Wsdl2JavaSetting implements Serializable {
	private String wsdlFileLocation = "";
	private String sourcePath = "";
	private String resourcePath = "";
	private String skeltonInterfaceName = "";
	private String skeltonClassName = "";
	

	public void setResourcePath(String resourcePath) {
		this.resourcePath = resourcePath;
	}
	public String getResourcePath() {
		return resourcePath;
	}
	public void setSkeltonInterfaceName(String skeltonInterfaceName) {
		this.skeltonInterfaceName = skeltonInterfaceName;
	}
	public String getSkeltonInterfaceName() {
		return skeltonInterfaceName;
	}
	public void setSkeltonClassName(String skeltonClassName) {
		this.skeltonClassName = skeltonClassName;
	}
	public String getSkeltonClassName() {
		return skeltonClassName;
	}
	public void setWsdlFileLocation(String wsdlFileLocation) {
		this.wsdlFileLocation = wsdlFileLocation;
	}
	public String getWsdlFileLocation() {
		return wsdlFileLocation;
	}
	public void setSourcePath(String sourcePath) {
		this.sourcePath = sourcePath;
	}
	public String getSourcePath() {
		return sourcePath;
	}
}
