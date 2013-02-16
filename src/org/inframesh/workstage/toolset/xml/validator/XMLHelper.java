package org.inframesh.workstage.toolset.xml.validator;

import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.xpath.XPathAPI;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMDocument;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMElement;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMModel;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

public class XMLHelper {

	public static IFile getFile(String delta) {
		IResource res = ResourcesPlugin.getWorkspace().getRoot().getFile(
				new Path(delta));

		return res instanceof IFile ? (IFile) res : null;
	}

	public static String getAttributeValueByXPath(IFile file,
			String elementPath, String attributeName) {

		IDOMModel xmlModel = XMLHelper.getModelForResource(file);		
		IDOMDocument document = xmlModel.getDocument();
		
		Node elementTemp = null;
		try {
			elementTemp = XPathAPI.selectSingleNode(document, elementPath);
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		IDOMElement element = (IDOMElement) elementTemp;
		
		return element.getAttribute(attributeName);
		
//		InputSource inputSource = new InputSource(file);
//		XPath xpath = XPathFactory.newInstance().newXPath();
//
//		Node result1;
//		try {
//			result1 = (Node) xpath.evaluate(elementPath, inputSource,
//					XPathConstants.NODE);
//
//			Node attr = result1.getAttributes().getNamedItem(attributeName);
//
//			return attr.getNodeValue();
//		} catch (XPathExpressionException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return null;
//		}
	}

	/**
	 * 
	 * @param file
	 *            the file to get the model for
	 * @return the file's XMLModel
	 */
	public static IDOMModel getModelForResource(IFile file) {
		IStructuredModel model = null;
		IModelManager manager = StructuredModelManager.getModelManager();

		try {
			model = manager.getModelForRead(file);
			// TODO.. HTML validator tries again to get a model a 2nd way
		} catch (Exception e) {
			e.printStackTrace();
		}

		return model instanceof IDOMModel ? (IDOMModel) model : null;
	}

	public static String createURIForFilePath(String filename) {
		if (!filename.startsWith(Constants.FILE_PROTOCOL_NO_SLASH)) {
			while (filename.startsWith("/")) //$NON-NLS-1$
			{
				filename = filename.substring(1);
			}
			filename = Constants.FILE_PROTOCOL + filename;
		}
		return filename;
	}

	public static String createURIForFile(IFile file) {
		IPath path = file.getLocation();
		String filename = path.toString();

		if (!filename.startsWith(Constants.FILE_PROTOCOL_NO_SLASH)) {
			while (filename.startsWith("/")) //$NON-NLS-1$
			{
				filename = filename.substring(1);
			}
			filename = Constants.FILE_PROTOCOL + filename;
		}
		return filename;
	}
}
