package org.inframesh.workstage.toolset.xml.validator;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.wst.sse.core.internal.provisional.IndexedRegion;
import org.eclipse.wst.validation.internal.core.IMessageAccess;
import org.eclipse.wst.validation.internal.core.Message;
import org.eclipse.wst.validation.internal.core.ValidationException;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;
import org.eclipse.wst.validation.internal.provisional.core.IValidationContext;
import org.eclipse.wst.validation.internal.provisional.core.IValidator;
import org.eclipse.wst.validation.internal.provisional.core.IValidatorJob;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMAttr;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMDocument;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMElement;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMModel;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMNode;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMText;
import org.inframesh.workstage.toolset.xml.validator.schematron.SchematronValidator;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class XMLValidator implements IValidatorJob {

	protected static final String ERROR_SIDE = "ERROR_SIDE";

	protected static final String ERROR_SIDE_LEFT = "ERROR_SIDE_LEFT";

	protected static final String ERROR_SIDE_RIGHT = "ERROR_SIDE_RIGHT";

	class MyReporter implements IReporter {
		List list = new ArrayList();

		public MyReporter() {
			super();
		}

		public void addMessage(IValidator origin, IMessage message) {
			list.add(message);
		}

		public void displaySubtask(IValidator validator, IMessage message) {
			/* do not need to implement */
		}

		public IMessageAccess getMessageAccess() {
			return null;
		}

		public boolean isCancelled() {
			return false;
		}

		public void removeAllMessages(IValidator origin, Object object) { // do
			/* do not need to implement */
		}

		public void removeAllMessages(IValidator origin) {
			/* do not need to implement */
		}

		public void removeMessageSubset(IValidator validator, Object obj,
				String groupName) {// do
			/* do not need to implement */
		}

		public List getMessages() {
			return list;
		}
	}

	public void cleanup(IReporter reporter) {
		// This validator doesn't cache anything so it doesn't need to clean
		// anything up.
	}

	public void validate(IValidationContext helper, IReporter reporter){
		if (helper == null) {
			return;
		}
		if ((reporter != null) && (reporter.isCancelled() == true)) {
			throw new OperationCanceledException();
		}
		String[] deltaArray = helper.getURIs();
		if (deltaArray != null && deltaArray.length > 0) {
			try {
				validateDelta(helper, reporter);
			} catch (FileNotFoundException e) {

				e.printStackTrace();
			} catch (SAXException e) {

				e.printStackTrace();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
	}

	/**
	 * @throws IOException
	 * @throws SAXException
	 * @throws FileNotFoundException
	 */
	private void validateDelta(IValidationContext helper, IReporter reporter)
			throws FileNotFoundException, SAXException, IOException {
		String[] deltaArray = helper.getURIs();
		for (int i = 0; i < deltaArray.length; i++) {
			String delta = deltaArray[i];
			if (delta == null)
				continue;

			if (reporter != null) {
				Message message = new LocalizedMessage(IMessage.LOW_SEVERITY,
						"" + (i + 1) + "/" + deltaArray.length + " - "
								+ delta.substring(1));
				reporter.displaySubtask(this, message);
			}

			validateFile(helper, reporter, delta);

		}
	}

	/**
	 * @throws IOException
	 * @throws SAXException
	 * @throws FileNotFoundException
	 */
	private void validateFile(IValidationContext helper, IReporter reporter,
			String uri) throws FileNotFoundException, SAXException, IOException {
		if ((reporter != null) && (reporter.isCancelled() == true)) {
			throw new OperationCanceledException();
		}
		Object[] parms = { uri };

		IFile file = (IFile) helper.loadModel(Constants.GET_FILE, parms);

		IDOMModel xmlModel = XMLHelper.getModelForResource(file);
		try {
			IDOMDocument document = xmlModel.getDocument();

			MyReporter vReporter = new MyReporter();

//			SchemaValidator schemaValidator = new SchemaValidator(file);
//
//			InputStream steam = new ByteArrayInputStream(xmlModel
//					.getStructuredDocument().get().getBytes("UTF-8"));
//
//			schemaValidator.validate(steam, vReporter, this);

			//����������
			InputStream steam1 = new ByteArrayInputStream(xmlModel.getStructuredDocument().get().getBytes("UTF-8"));

			SchematronValidator schematronValidator = new SchematronValidator(file);
			schematronValidator.validate(steam1, vReporter, this);

			List messages = vReporter.list;
			clearMarkers(file, this, reporter);
			// set the offset and length
			updateValidationMessages(file, messages, document, reporter);

//			steam.close();
			steam1.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (xmlModel != null) {
				xmlModel.releaseFromRead();
			}
		}
	}

	private void clearMarkers(IFile iFile, IValidator validator,
			IReporter reporter) {
		if (fileIsAccessible(iFile)) {
			reporter.removeAllMessages(validator, iFile);
		}
	}

	private boolean fileIsAccessible(IFile file) {
		if (file != null && file.exists() && file.getProject().isAccessible()) {
			return true;
		}
		return false;
	}

	protected void updateValidationMessages(IFile file, List messages,
			IDOMDocument document, IReporter reporter) throws CoreException {
		for (int i = 0; i < messages.size(); i++) {
			IMessage message = (IMessage) messages.get(i);

			try {
				if (message.getAttribute(Constants.COLUMN_NUMBER_ATTRIBUTE) != null
						|| message
								.getAttribute(Constants.OFFSET_NUMBER_ATTRIBUTE) != null) {
					String selectionStrategy = (String) message
							.getAttribute(Constants.SQUIGGLE_SELECTION_STRATEGY_ATTRIBUTE);
					String nameOrValue = (String) message
							.getAttribute(Constants.SQUIGGLE_NAME_OR_VALUE_ATTRIBUTE);

					int start = 0;
					if (null != message
							.getAttribute(Constants.COLUMN_NUMBER_ATTRIBUTE)) {
						int column = ((Integer) message
								.getAttribute(Constants.COLUMN_NUMBER_ATTRIBUTE))
								.intValue();
						start = document.getStructuredDocument().getLineOffset(
								message.getLineNumber() - 1)
								+ column - 1;
					} else if (null != message
							.getAttribute(Constants.OFFSET_NUMBER_ATTRIBUTE)) {
						start = ((Integer) message
								.getAttribute(Constants.OFFSET_NUMBER_ATTRIBUTE))
								.intValue();
					}

					// calculate the "better" start and end offset:
					int[] result = computeStartAndEndLocation(start,
							selectionStrategy, getErrorSide(message),
							nameOrValue, document);
					if (result != null) {
						message.setOffset(result[0]);
						message.setLength(result[1] - result[0]);

						reporter.addMessage(this, message);
					}

				}
			} catch (BadLocationException e) { // this exception should not
				// occur - it is thrown if
				// trying to convert an
				// invalid line number to and
				// offset

				e.printStackTrace();
			}

		}
	}

	public IResource getResource(String delta) {
		return ResourcesPlugin.getWorkspace().getRoot()
				.getFile(new Path(delta));
	}

	protected int[] computeStartAndEndLocation(int startOffset,
			String selectionStrategy, String errorSide, String nameOrValue,
			IDOMDocument document) {
		try {
			int startEndPositions[] = new int[2];

			IndexedRegion region = document.getModel().getIndexedRegion(
					startOffset);
			IndexedRegion prevRegion = document.getModel().getIndexedRegion(
					startOffset - 1);

			if (prevRegion != region) {

				if (ERROR_SIDE_LEFT.equals(errorSide)) {
					region = prevRegion;
				}
			}

			if (region != null) {
				startEndPositions[0] = region.getStartOffset();
				startEndPositions[1] = startEndPositions[0];
			} else {
				startEndPositions[0] = 0;
				startEndPositions[1] = 0;
			}
			if (region instanceof Node) {
				Node node = (Node) region;

				if (Constants.START_TAG.equals(selectionStrategy)) {// then we

					if (node.getNodeType() == Node.ELEMENT_NODE) {
						IDOMElement element = (IDOMElement) node;
						startEndPositions[0] = element.getStartOffset() + 1;
						startEndPositions[1] = startEndPositions[0]
								+ element.getTagName().length();
					}
				} else if (Constants.ATTRIBUTE_NAME.equals(selectionStrategy)) { // in

					if (node.getNodeType() == Node.ELEMENT_NODE) {
						IDOMElement element = (IDOMElement) node;
						IDOMNode attributeNode = (IDOMNode) (element
								.getAttributeNode(nameOrValue));
						if (attributeNode != null) {
							startEndPositions[0] = attributeNode
									.getStartOffset();
							startEndPositions[1] = attributeNode
									.getStartOffset()
									+ nameOrValue.length();
						}
					}
				} else if (Constants.ATTRIBUTE_VALUE.equals(selectionStrategy)) {

					if (node.getNodeType() == Node.ELEMENT_NODE) {
						IDOMElement element = (IDOMElement) node;
						IDOMAttr attributeNode = (IDOMAttr) (element
								.getAttributeNode(nameOrValue));
						if (attributeNode != null) {
							startEndPositions[0] = attributeNode
									.getValueRegionStartOffset();
							startEndPositions[1] = startEndPositions[0]
									+ attributeNode.getValueRegionText()
											.length();
						}
					}
				} else if (Constants.ALL_ATTRIBUTES.equals(selectionStrategy)) {

					if (node.getNodeType() == Node.ELEMENT_NODE) {
						IDOMElement element = (IDOMElement) node;
						NamedNodeMap attributes = element.getAttributes();
						if (attributes != null) {
							IDOMNode first = (IDOMNode) attributes.item(0);
							IDOMNode last = (IDOMNode) attributes
									.item(attributes.getLength() - 1);
							if ((first != null) && (last != null)) {
								startEndPositions[0] = first.getStartOffset();
								startEndPositions[1] = last.getEndOffset();
							}
						}
					}
				} else if (Constants.TEXT.equals(selectionStrategy)) {

					if (node.getNodeType() == Node.TEXT_NODE) {
						IDOMText textNode = (IDOMText) node;
						int start = textNode.getStartOffset();
						String value = textNode.getNodeValue();
						int index = 0;
						char curChar = value.charAt(index);

						while ((curChar == '\n') || (curChar == '\t')
								|| (curChar == '\r') || (curChar == ' ')) {
							curChar = value.charAt(index);
							index++;
						}
						if (index > 0) {
							index--;

						}
						start = start + index;
						startEndPositions[0] = start + index;
						startEndPositions[1] = start + value.trim().length();
					} else if (node.getNodeType() == Node.ELEMENT_NODE) {
						IDOMElement element = (IDOMElement) node;
						Node child = element.getFirstChild();
						if (child instanceof IDOMNode) {
							IDOMNode xmlChild = ((IDOMNode) child);
							startEndPositions[0] = xmlChild.getStartOffset();
							startEndPositions[1] = xmlChild.getEndOffset();
						}
					}
				} else if (Constants.FIRST_NON_WHITESPACE_TEXT
						.equals(selectionStrategy)) {

					if (node.getNodeType() == Node.ELEMENT_NODE) {
						NodeList nodes = node.getChildNodes();
						for (int i = 0; i < nodes.getLength(); i++) {
							Node currentNode = nodes.item(i);
							if (currentNode.getNodeType() == Node.TEXT_NODE) {

								IDOMText textNode = (IDOMText) currentNode;
								if (textNode.getNodeValue().trim().length() > 0) {
									String value = textNode.getNodeValue();
									int index = 0;
									int start = textNode.getStartOffset();
									char curChar = value.charAt(index);

									while ((curChar == '\n')
											|| (curChar == '\t')
											|| (curChar == '\r')
											|| (curChar == ' ')) {
										curChar = value.charAt(index);
										index++;
									}
									if (index > 0) {
										index--;

									}
									start = start + index;
									startEndPositions[0] = start;
									startEndPositions[1] = start
											+ value.trim().length();
									break;
								}
							}

						}
					}
				}

				else if (Constants.TEXT_ENTITY_REFERENCE
						.equals(selectionStrategy)) {
					if (node.getNodeType() == Node.ENTITY_REFERENCE_NODE) {
						startEndPositions[0] = region.getStartOffset();
						startEndPositions[1] = region.getEndOffset();
					} else if (node.getNodeType() == Node.ELEMENT_NODE) {

						String entity = "&" + nameOrValue + ";"; //$NON-NLS-1$ //$NON-NLS-2$
						NamedNodeMap attributes = node.getAttributes();
						for (int i = 0; i < attributes.getLength(); i++) {
							IDOMAttr attr = (IDOMAttr) attributes.item(i);
							String nodeValue = attr.getNodeValue();
							int index = nodeValue.indexOf(entity);
							if (index != -1) {
								startEndPositions[0] = attr
										.getValueRegionStartOffset()
										+ index + 1;
								startEndPositions[1] = startEndPositions[0]
										+ entity.length();
							}
						}
					}

				} else if (Constants.VALUE_OF_ATTRIBUTE_WITH_GIVEN_VALUE
						.equals(selectionStrategy)) {

					if (node.getNodeType() == Node.ELEMENT_NODE) {

						NamedNodeMap attributes = node.getAttributes();
						for (int i = 0; i < attributes.getLength(); i++) {
							IDOMAttr attr = (IDOMAttr) attributes.item(i);
							String nodeValue = attr.getNodeValue().trim();
							if (nodeValue.equals(nameOrValue)) {
								startEndPositions[0] = attr
										.getValueRegionStartOffset() + 1;
								startEndPositions[1] = startEndPositions[0]
										+ nodeValue.length();
								break;
							}
						}
					}
				}
			}
			return startEndPositions;
		}

		finally {
		}

	}

	protected String getErrorSide(IMessage message) {

		Object value = message.getAttribute(ERROR_SIDE);
		return ERROR_SIDE_RIGHT.equals(value) ? ERROR_SIDE_RIGHT
				: ERROR_SIDE_LEFT;
	}

	public ISchedulingRule getSchedulingRule(IValidationContext helper) {

		return null;
	}

	public IStatus validateInJob(IValidationContext helper, IReporter reporter)
			throws ValidationException {
		if (helper == null) {
			return Status.CANCEL_STATUS;
		}
		if ((reporter != null) && (reporter.isCancelled() == true)) {
			throw new OperationCanceledException();
		}
		String[] deltaArray = helper.getURIs();
		if (deltaArray != null && deltaArray.length > 0) {
			try {
				validateDelta(helper, reporter);
			} catch (FileNotFoundException e) {

				e.printStackTrace();
			} catch (SAXException e) {

				e.printStackTrace();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
		if (reporter.isCancelled())
			return Status.CANCEL_STATUS;
		return Status.OK_STATUS;
	}
}
