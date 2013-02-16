package org.inframesh.workstage.toolset.xml.validator;

import org.eclipse.core.resources.IFile;
import org.eclipse.wst.validation.internal.operations.WorkbenchContext;

public class XMLValidatorHelper extends WorkbenchContext {
	public IFile getFile(String fileName) {

		IFile file = XMLHelper.getFile(fileName);

		return file;

	}
}
