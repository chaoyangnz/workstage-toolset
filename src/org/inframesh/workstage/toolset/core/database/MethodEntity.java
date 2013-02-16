package org.inframesh.workstage.toolset.core.database;

import java.util.ArrayList;

public class MethodEntity {
	// �������
	private String methodName;

	// ���ط�������
	private int overloadMethodNum = 1;

	// �������������б�
	private Class[] methodParamTypes;

	// ������ط�������
	private ArrayList overloadMethodsParamTypes;

	/**
	 * ��ȡ�������
	 * 
	 * @return
	 */
	public String getMethodName() {
		return methodName;
	}

	/**
	 * ��ȡ�������������б�
	 * 
	 * @return
	 */
	public Class[] getMethodParamTypes() {
		return methodParamTypes;
	}

	/**
	 * ���ò������
	 * 
	 * @param string
	 */

	public void setMethodName(String string) {
		methodName = string;
	}

	/**
	 * ���ò��������б�
	 * 
	 * @param classes
	 */
	public void setMethodParamTypes(Class[] classes) {
		methodParamTypes = classes;
	}

	/**
	 * ��ȡ���ط�������
	 * 
	 * @return
	 */
	public int getOverloadMethodNum() {
		return overloadMethodNum;
	}

	/**
	 * ��ȡ��i�����ط��������б�
	 * 
	 * @return
	 */
	public Class[] getOverloadMethodsParamTypes(int i) {
		int count = this.overloadMethodsParamTypes.size();

		if (i <= count) {
			return (Class[]) this.overloadMethodsParamTypes.get(i);
		} else {
			throw new ArrayIndexOutOfBoundsException();
		}
	}

	/**
	 * �������ط�������
	 * 
	 * @param i
	 */
	public void setOverloadMethodNum(int i) {
		overloadMethodNum = i;
	}

	/**
	 * �������ط�����������
	 * 
	 * @param list
	 */
	public void setOverloadMethodsParamTypes(ArrayList list) {
		overloadMethodsParamTypes = list;
	}

	/**
	 * ��ȡ���ط�������
	 * 
	 * @return
	 */
	public ArrayList getOverloadMethodsParamTypes() {
		return overloadMethodsParamTypes;
	}

	/**
	 * �������ط������������б�
	 * 
	 * @param paramTypes
	 */
	public void setOverloadMethodsParamTypes(Class[] paramTypes) {
		if (this.overloadMethodsParamTypes == null)
			this.overloadMethodsParamTypes = new ArrayList();

		overloadMethodsParamTypes.add(paramTypes);

	}

}
