package org.inframesh.workstage.toolset.xml.validator.schematron;

/*
 * SchtrnValidator.java - Perform Schematron validation
 * Copyright (C) 2002 Eddie Robertsson
 *
 * You may use and modify this package for any purpose. Redistribution is
 * permitted, in both source and binary form, provided that this notice
 * remains intact in all source distributions of this package.
 */

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.inframesh.workstage.toolset.xml.validator.Setting;


public class SchtrnValidator {
	private final TransformerFactory theFactory;

	private StreamSource engineStylesheet = null;

	private boolean baseIsXML = true;

	public SchtrnValidator() {
		theFactory = TransformerFactory.newInstance();
	}

	public static String validateXMLBySchtron(StreamSource sourceStream, String filePathStr, String schematronPathStr) {

		if (schematronPathStr==null) {
			return null;
		}
		SchtrnValidator validator = new SchtrnValidator();

		validator.setEngineStylesheet(Setting.getInstance().getDiagnoseXslLocation());
		validator.setBaseXML(true);

		String fileURL = system_To_URL(filePathStr);
		String schematronURL = system_To_URL(schematronPathStr);
		
		StreamSource schematron = new StreamSource(schematronURL);
		StreamSource dialogXsl = validator.getEngineStylesheet();
		
		/*---------��ӡ����֤XLS-------------------*/
		String validateXslStr = validator.transform(schematron, dialogXsl);
		System.out.println(validateXslStr);
		StreamSource validationXsl = new StreamSource(new StringReader(validateXslStr));

		// Set the correct systemId on the validationStylesheet
		if (validator.baseIsXML) {
			validationXsl.setSystemId(fileURL);
		} else {
			validationXsl.setSystemId(schematron.getSystemId());
		}
		// Do the tranformation that performs the validation
		return validator.transform(sourceStream, validationXsl);
	}

	private String transform(StreamSource source, StreamSource stylesheet){
		// Create the transformer with the specified stylesheet
		Transformer transformer = null;
		try {
			transformer = theFactory.newTransformer(stylesheet);
		} catch (TransformerConfigurationException e1) {
			System.out.println("newTransformer failed");
//			e1.printStackTrace();
		}

		StringWriter stringWriter = new StringWriter();
		StreamResult target = new StreamResult(stringWriter);
		try {
			transformer.transform(source, target);
		} catch (TransformerException e1) {
			System.out.println("transform failed");
			e1.printStackTrace();
		}
		String str = stringWriter.toString();
		try {
			stringWriter.close();
		} catch (IOException e) {
			System.out.println("StringWriter close failed!");
//			e.printStackTrace();
		}
		return str;
	}

	public void setBaseXML(boolean useXML) {
		baseIsXML = useXML;
	}

	

	public StreamSource getEngineStylesheet() {
		return engineStylesheet;
	}

	public void setEngineStylesheet(String enginePath) {
		if (enginePath != null) {
			engineStylesheet = new StreamSource(system_To_URL(enginePath));
		}
	}
	
	static String system_To_URL(String path) {

		if (path.startsWith("file:")) {
			// If the path starts with file we assume that it's a valid URL...
			System.out.println(path);
			return path;
		}
		try {
			// Use Java's File class to convert the path to a URL
			URL url = new File(path).toURL();
			return url.toString();
		} catch (MalformedURLException e) {
			System.out.println("MalformedURL Failed!");
			return null;
		}
	}
}
