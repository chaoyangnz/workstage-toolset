package org.inframesh.workstage.toolset.xml.validator;

public class XMLValidationInfo {
	protected int column;

	protected String currentErrorKey;

	protected Object messageArguments[] = null;

	public String getCurrentErrorKey() {
		return currentErrorKey;
	}

	public void setCurrentErrorKey(String currentErrorKey) {
		this.currentErrorKey = currentErrorKey;
	}

	public Object[] getMessageArguments() {
		return messageArguments;
	}

	public void setMessageArguments(Object[] messageArguments) {
		this.messageArguments = messageArguments;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

}
