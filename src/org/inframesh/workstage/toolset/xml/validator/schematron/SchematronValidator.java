package org.inframesh.workstage.toolset.xml.validator.schematron;

import java.io.InputStream;
import java.util.List;

import javax.xml.transform.stream.StreamSource;

import org.apache.xpath.XPathAPI;
import org.eclipse.core.resources.IFile;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;
import org.eclipse.wst.validation.internal.provisional.core.IValidator;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMDocument;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMElement;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMModel;
import org.inframesh.workstage.toolset.xml.validator.Constants;
import org.inframesh.workstage.toolset.xml.validator.LocalizedMessage;
import org.inframesh.workstage.toolset.xml.validator.Setting;
import org.inframesh.workstage.toolset.xml.validator.XMLHelper;
import org.inframesh.workstage.toolset.xml.validator.classreference.ClassReferenceValidator;
import org.w3c.dom.Node;


public class SchematronValidator {
	IFile file;

	public SchematronValidator(IFile _file) {
		file = _file;
	}

	public void validate(InputStream steam, IReporter reporter, IValidator validator) {
		try {
			String filePathStr = file.getLocation().makeAbsolute().toFile().getAbsolutePath();

			String result = SchtrnValidator.validateXMLBySchtron(new StreamSource(steam), filePathStr, 
					Setting.getInstance().getSchematronFile(file));

			System.out.println("Current validating file name: " + file.getName());
			System.out.println(result);
			
			List errorList = Helper.getSchematronErrorList(result);			

			IDOMModel xmlModel = XMLHelper.getModelForResource(file);		
			IDOMDocument document = xmlModel.getDocument();

			for (int i = 0; i < errorList.size(); i++) {
				SchematronErrorMessage msg = (SchematronErrorMessage) errorList.get(i);
				
				// Select article titles into DOM node list.
				Node elementTemp = XPathAPI.selectSingleNode(document, msg.getPath());

				//DO SOMETHING HACKING BY hackingNamespacePrefix()
				if(elementTemp == null) {
					msg.setPath(Helper.hackingNamespacePrefix(msg.getPath()));
					elementTemp = XPathAPI.selectSingleNode(document, msg.getPath());
				}
				IDOMElement element = (IDOMElement) elementTemp;

				int offset = element.getStartEndOffset();

				int line = document.getStructuredDocument().getLineOfOffset(element.getStartOffset());

				String errorAttribute = msg.getErrorAttributeName();
				String errorAttributeValue = null;
				if (errorAttribute.startsWith(Constants.REFERENCE_CLASS_MARK)&& errorAttribute.endsWith(Constants.REFERENCE_CLASS_MARK)) {
					
					errorAttribute = errorAttribute.replaceAll(Constants.REFERENCE_CLASS_MARK, "");

					errorAttributeValue = XMLHelper.getAttributeValueByXPath(file, msg.getPath(), errorAttribute);

					//��������֤
					ClassReferenceValidator classValidator = new ClassReferenceValidator();
					String classValidatorResult = classValidator.validate(file,	errorAttributeValue);

					// no error
					if (null == classValidatorResult) {
						continue;
					} 
				}

				LocalizedMessage message;

				message = new LocalizedMessage(IMessage.HIGH_SEVERITY, msg.getErrorMessage(), file);
				message.setLineNo(line);

				message.setAttribute(Constants.OFFSET_NUMBER_ATTRIBUTE, new Integer(offset));
				message.setAttribute(Constants.SQUIGGLE_SELECTION_STRATEGY_ATTRIBUTE, Constants.ATTRIBUTE_VALUE);
				message.setAttribute(Constants.SQUIGGLE_NAME_OR_VALUE_ATTRIBUTE, errorAttribute);
				message.getOffset();

				reporter.addMessage(validator, message);
			}
			xmlModel.releaseFromRead();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
