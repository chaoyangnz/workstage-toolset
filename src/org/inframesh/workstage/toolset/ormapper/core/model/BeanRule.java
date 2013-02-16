package org.inframesh.workstage.toolset.ormapper.core.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class BeanRule implements Serializable {
	private String beanPrefix = "";
	private String beanSuffix = "";
	private boolean generateAnnotation = false;
	
	public void setBeanPrefix(String beanPrefix) {
		this.beanPrefix = beanPrefix;
	}
	public String getBeanPrefix() {
		return beanPrefix;
	}
	public void setBeanSuffix(String beanSuffix) {
		this.beanSuffix = beanSuffix;
	}
	public String getBeanSuffix() {
		return beanSuffix;
	}
	public boolean isGenerateAnnotation() {
		return generateAnnotation;
	}
	public void setGenerateAnnotation(boolean generateAnnotation) {
		this.generateAnnotation = generateAnnotation;
	}
}
