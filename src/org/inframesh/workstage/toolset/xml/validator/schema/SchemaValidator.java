package org.inframesh.workstage.toolset.xml.validator.schema;

import java.io.IOException;
import java.io.InputStream;

import org.apache.xerces.parsers.StandardParserConfiguration;
import org.eclipse.core.resources.IFile;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;
import org.eclipse.wst.validation.internal.provisional.core.IValidator;
import org.inframesh.workstage.toolset.xml.validator.MyStandardParserConfiguration;
import org.inframesh.workstage.toolset.xml.validator.Setting;
import org.inframesh.workstage.toolset.xml.validator.XMLSchemaErrorHandler;
import org.inframesh.workstage.toolset.xml.validator.XMLValidationInfo;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;


public class SchemaValidator {
	IFile file;

	public SchemaValidator(IFile _file) {
		file = _file;
	}

	public void validate(InputStream steam, IReporter reporter,
			IValidator validator) {
		try {
			XMLValidationInfo validationInfo = new XMLValidationInfo();
			StandardParserConfiguration configuration = new MyStandardParserConfiguration(
					validationInfo);
			XMLReader parser = new org.apache.xerces.parsers.SAXParser(
					configuration);

			// ����sax parser����
			//���ý�������֤�ĵ�(����DOCTYPE������DTD��֤) 
			parser.setFeature("http://xml.org/sax/features/validation", true);
			//���ý�������֤����ռ䣬(�������ⲿ��stylesheet��xls�ĵ�)  
			parser.setFeature("http://apache.org/xml/features/validation/schema", true);
			parser.setFeature("http://apache.org/xml/features/continue-after-fatal-error", true);
			parser.setFeature("http://apache.org/xml/features/validation/schema-full-checking", true);

			// ����XSDģʽ�ļ�·��
			Setting setting = Setting.getInstance();
			parser.setProperty("http://apache.org/xml/properties/schema/external-noNamespaceSchemaLocation",
							   setting.getXSDFile(file));

			// ����sax parser�Ĵ�������
			parser.setErrorHandler(new XMLSchemaErrorHandler(file, reporter, validator, validationInfo));
			// ��ʼ����
			parser.parse(new InputSource(steam));
			
		} catch (SAXException e) {
			System.out.print(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
