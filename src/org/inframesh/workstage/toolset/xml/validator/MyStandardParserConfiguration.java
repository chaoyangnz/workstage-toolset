package org.inframesh.workstage.toolset.xml.validator;

import org.apache.xerces.impl.XMLErrorReporter;
import org.apache.xerces.parsers.StandardParserConfiguration;
import org.apache.xerces.xni.XNIException;

public class MyStandardParserConfiguration extends StandardParserConfiguration {
	public XMLValidationInfo _validationInfo;

	public MyStandardParserConfiguration(XMLValidationInfo validationInfo) {
		_validationInfo = validationInfo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.xerces.parsers.DTDConfiguration#createErrorReporter()
	 */
	protected XMLErrorReporter createErrorReporter() {
		return new XMLErrorReporter() {
			public void reportError(String domain, String key,
					Object[] arguments, short severity) throws XNIException {
				_validationInfo.setCurrentErrorKey(key);
				_validationInfo.setMessageArguments(arguments);

				super.reportError(domain, key, arguments, severity);
			}
		};
	}
}
