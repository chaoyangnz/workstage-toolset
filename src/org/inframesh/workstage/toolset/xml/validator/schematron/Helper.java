package org.inframesh.workstage.toolset.xml.validator.schematron;

import java.util.ArrayList;
import java.util.List;

public class Helper {
	// Copyright
	public static final String copyright = "(c) Copyright IBM Corporation 2006-2007.";

	private static final String XMLERROR_XMLMESSAGE = "XMLMessage";

	private static final String XMLERROR_XMLXPATH = "XMLXPATH";

	private static final String XMLERROR_ATTRIBUTE = "ATTRIBUTE";

	public static List getSchematronErrorList(String result) {

		List list = new ArrayList();

		int start = 0;
		int end = 0;
		String errorMsgStr;
		while (result != null && result.indexOf(XMLERROR_XMLMESSAGE, end) > 0) {

			// new a error message instance
			SchematronErrorMessage errMsg = new SchematronErrorMessage();

			start = result.indexOf(XMLERROR_XMLMESSAGE, start)
					+ XMLERROR_XMLMESSAGE.length() + 1;

			end = result.indexOf(XMLERROR_ATTRIBUTE, start);

			errorMsgStr = result.substring(start, end);
			// get the error message from the xslt parsed result
			errMsg.setErrorMessage(errorMsgStr);

			start = end + XMLERROR_ATTRIBUTE.length();
			end = result.indexOf(XMLERROR_ATTRIBUTE, start);
			errMsg.setErrorAttributeName(result.substring(start, end));

			start = result.indexOf(XMLERROR_XMLXPATH, end)
					+ XMLERROR_XMLXPATH.length();
			end = result.indexOf(XMLERROR_XMLXPATH, start + 1);

			// get the error element xpath from the xslt parsed result
			errMsg.setPath(result.substring(start, end));//.replace("/", "/:"));//+"/@"+errMsg.getErrorAttributeName());

			list.add(errMsg);
		}

		return list;
	}
	
	public static String hackingNamespacePrefix(String name) {
		String result = "";
		String [] segments = name.split("/");
		for(int i=0; i < segments.length; ++i) {
			if(!segments[i].contains(":") && !segments[i].equals("")) {
				segments[i] = ":" + segments[i];
			}
			if(i==0) {
				result += segments[i];
			} else {
				result += "/" + segments[i];
			}
			
		}
		return result;
		
	}
}
