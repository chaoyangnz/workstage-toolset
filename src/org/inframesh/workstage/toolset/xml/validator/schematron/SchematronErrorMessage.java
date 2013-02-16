package org.inframesh.workstage.toolset.xml.validator.schematron;

public class SchematronErrorMessage {
	// Copyright
	public static final String copyright = "(c) Copyright IBM Corporation 2006-2007.";

	// error message
	private String errorMessage;

	// xpath
	private String path;

	// error attribute name
	private String errorAttributeName;

	private String errorAttributeValue;

	public static String getCopyright() {
		return copyright;
	}

	public String getErrorAttributeName() {
		return errorAttributeName;
	}

	public void setErrorAttributeName(String errorAttributeName) {
		this.errorAttributeName = errorAttributeName;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorAttributeValue() {
		return errorAttributeValue;
	}

	public void setErrorAttributeValue(String errorTagValue) {
		this.errorAttributeValue = errorTagValue;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String toString() {
		return errorMessage + ":" + path + ":" + errorAttributeName + ":"
				+ errorAttributeValue;
	}
}
