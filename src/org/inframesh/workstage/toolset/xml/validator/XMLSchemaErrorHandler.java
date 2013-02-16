package org.inframesh.workstage.toolset.xml.validator;

import org.eclipse.core.resources.IFile;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;
import org.eclipse.wst.validation.internal.provisional.core.IValidator;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class XMLSchemaErrorHandler implements ErrorHandler {

	IFile uri;

	IReporter _reporter;

	IValidator _validator = null;

	XMLValidationInfo _validationInfo;

	public XMLSchemaErrorHandler() {

	}

	public XMLSchemaErrorHandler(IFile uri, IReporter reporter,
			IValidator validator, XMLValidationInfo info) {
		super();
		this.uri = uri;
		this._validator = validator;
		this._reporter = reporter;
		_validationInfo = info;
	}

	public void warning(SAXParseException exception) {
		LocalizedMessage message;

		message = new LocalizedMessage(IMessage.LOW_SEVERITY, exception
				.getLocalizedMessage(), uri);
		message.setLineNo(exception.getLineNumber());

		System.out.println(exception.getMessage());

		_validationInfo.setColumn(exception.getColumnNumber());
		addInfoToMessage(_validationInfo, message);
		_reporter.addMessage(_validator, message);
	}

	public void error(SAXParseException exception) throws SAXException {
		LocalizedMessage message;

		message = new LocalizedMessage(IMessage.HIGH_SEVERITY, exception
				.getLocalizedMessage(), uri);
		message.setLineNo(exception.getLineNumber());

		System.out.println(exception.getMessage());
		_validationInfo.setColumn(exception.getColumnNumber());
		addInfoToMessage(_validationInfo, message);
		_reporter.addMessage(_validator, message);
	}

	public void fatalError(SAXParseException exception) throws SAXException {
		LocalizedMessage message;

		message = new LocalizedMessage(IMessage.HIGH_SEVERITY, exception
				.getLocalizedMessage(), uri);
		message.setLineNo(exception.getLineNumber());

		System.out.println(exception.getMessage());

		_validationInfo.setColumn(exception.getColumnNumber());
		addInfoToMessage(_validationInfo, message);
		_reporter.addMessage(_validator, message);
	}

	protected void addInfoToMessage(XMLValidationInfo info, IMessage message) {
		String key = info.getCurrentErrorKey();
		if (key != null) {
			XMLMessageInfoHelper messageInfoHelper = new XMLMessageInfoHelper();
			String[] messageInfo = messageInfoHelper.createMessageInfo(key,
					info.getMessageArguments());

			message.setAttribute(Constants.COLUMN_NUMBER_ATTRIBUTE,
					new Integer(info.getColumn()));
			message.setAttribute(
					Constants.SQUIGGLE_SELECTION_STRATEGY_ATTRIBUTE,
					messageInfo[0]);
			message.setAttribute(Constants.SQUIGGLE_NAME_OR_VALUE_ATTRIBUTE,
					messageInfo[1]);
		}
	}

}