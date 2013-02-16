package org.inframesh.workstage.toolset.core.database;

public class TableEntity {
	// ��ѯ����ReslutSet�е��ֶ�����
	private int columnCount = 0;

	// �ֶ��������
	private String[] columnNames;

	// �ֶ���������
	private int[] columnTypes;

	// Ĭ�Ϲ�����
	public TableEntity() {
		this(0);
	}

	// ��ʼ��������
	public TableEntity(int columnCount) {
		this.columnCount = columnCount;
		this.columnNames = new String[columnCount];
		this.columnTypes = new int[columnCount];
	}

	// ��ȡ�ֶ�����
	public int getColumnCount() {
		return this.columnCount;
	}

	// ��ȡ�ֶ��������
	public String[] getColumnNames() {
		return this.columnNames;
	}

	// ��ȡ��index���ֶ���ƣ����index�ֶβ����ڣ����׳�ArrayIndexOutOfBoundsException�쳣
	public String getColumnName(int index) {
		if (index <= this.columnCount) {
			return this.columnNames[index];
		}

		else {
			throw new ArrayIndexOutOfBoundsException();
		}
	}

	// �����ֶ��������
	public void setColumnNames(String[] columnNames) {
		this.columnNames = columnNames;
	}

	// ���õ�index���ֶ���ƣ����index�ֶβ����ڣ����׳�ArrayIndexOutOfBoundsException�쳣
	public void setColumnName(String columnName, int index) {
		if (index <= this.columnCount) {
			this.columnNames[index] = columnName;

		} else {
			throw new ArrayIndexOutOfBoundsException();

		}
	}

	// ��ȡ�ֶ���������
	public int[] getColumnTypes() {
		return this.columnTypes;
	}

	// ��ȡ�ֶ�����
	public int getColumnType(int index) {
		if (index <= this.columnCount) {
			return this.columnTypes[index];
		} else {
			throw new ArrayIndexOutOfBoundsException();
		}
	}

	// �����ֶ���������
	public void setColumnTypes(int[] columnTypes) {
		this.columnTypes = columnTypes;
	}

	// ��ȡ�ֶ�����
	public void setColumnType(int columnType, int index) {
		if (index <= this.columnCount) {
			this.columnTypes[index] = columnType;

		} else {
			throw new ArrayIndexOutOfBoundsException();

		}
	}
}
