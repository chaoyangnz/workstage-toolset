package org.inframesh.workstage.toolset.xml.validator;

public class XMLMessageInfoHelper {
	public XMLMessageInfoHelper() {
		super();
	}

	public String[] createMessageInfo(String errorKey, Object[] messageArguments) {
		String selectionStrategy = null;
		String nameOrValue = null;

		if (errorKey != null) {
			if (errorKey.equals("cvc-complex-type.2.4.a") || errorKey.equals("cvc-complex-type.2.4.d") || errorKey.equals("cvc-complex-type.2.4.b") || errorKey.equals("MSG_CONTENT_INVALID") //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
					| errorKey.equals("MSG_CONTENT_INCOMPLETE") || errorKey.equals("MSG_REQUIRED_ATTRIBUTE_NOT_SPECIFIED") || errorKey.equals("cvc-complex-type.4")) //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			{
				selectionStrategy = "START_TAG"; //$NON-NLS-1$
			} else if (errorKey.equals("cvc-type.3.1.3")) //$NON-NLS-1$
			{
				selectionStrategy = "TEXT"; //$NON-NLS-1$
			} else if (errorKey.equals("cvc-complex-type.2.3")) //$NON-NLS-1$
			{
				selectionStrategy = "FIRST_NON_WHITESPACE_TEXT"; //$NON-NLS-1$
			} else if (errorKey.equals("cvc-type.3.1.1")) //$NON-NLS-1$
			{
				selectionStrategy = "ALL_ATTRIBUTES"; //$NON-NLS-1$
			} else if (errorKey.equals("cvc-complex-type.3.2.2") || errorKey.equals("MSG_ATTRIBUTE_NOT_DECLARED")) //$NON-NLS-1$ //$NON-NLS-2$
			{
				selectionStrategy = "ATTRIBUTE_NAME"; //$NON-NLS-1$
				// in this case we need nameOrValue to be the name of the
				// attribute to underline
				nameOrValue = (String) messageArguments[1];
			} else if (errorKey.equals("cvc-attribute.3") || errorKey.equals("MSG_ATTRIBUTE_VALUE_NOT_IN_LIST") || errorKey.equals("cvc-complex-type.3.1")) //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			{
				selectionStrategy = "ATTRIBUTE_VALUE"; //$NON-NLS-1$
				// in this case we need nameOrValue to be the name of the
				// Attribute
				if (errorKey.equals("cvc-attribute.3") || errorKey.equals("cvc-complex-type.3.1")) //$NON-NLS-1$ //$NON-NLS-2$
				{
					nameOrValue = (String) messageArguments[1];
				} else if (errorKey.equals("MSG_ATTRIBUTE_VALUE_NOT_IN_LIST")) //$NON-NLS-1$
				{
					nameOrValue = (String) messageArguments[0];
				}
			} else if (errorKey.equals("cvc-elt.4.2")) //$NON-NLS-1$
			{
				selectionStrategy = "VALUE_OF_ATTRIBUTE_WITH_GIVEN_VALUE"; //$NON-NLS-1$
				// in this case we need nameOrValue to be the value of the
				// attribute we want to unerline
				nameOrValue = (String) messageArguments[1];
			} else if (errorKey.equals("EntityNotDeclared")) //$NON-NLS-1$
			{
				selectionStrategy = "TEXT_ENTITY_REFERENCE"; //$NON-NLS-1$
				nameOrValue = (String) messageArguments[0];
			} else if (errorKey.equals("ElementUnterminated")) //$NON-NLS-1$
			{
				selectionStrategy = "ENTIRE_ELEMENT"; //$NON-NLS-1$
			}
		}
		String messageInfo[] = new String[2];
		messageInfo[0] = selectionStrategy != null ? selectionStrategy : ""; //$NON-NLS-1$
		messageInfo[1] = nameOrValue;
		return messageInfo;
	}
}
