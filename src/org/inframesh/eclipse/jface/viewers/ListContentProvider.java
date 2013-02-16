package org.inframesh.eclipse.jface.viewers;



import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class ListContentProvider implements IStructuredContentProvider {

	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof List)// ��һ��List�����ж�
			return ((List) inputElement).toArray(); // ����ݼ�Listת��Ϊ����
		else
			return new Object[0]; // ���List�����򷵻�һ��������
	}

	public void dispose() {
	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}
}
