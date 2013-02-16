package org.inframesh.workstage.toolset.ormapper.core.model;

public class MapSetting {
	private String fieldName;
	private String jdbcType;
	private int length; //�ֶγ���
	private int scale;  //�ֶξ���
	private boolean nullable;
	private boolean pk; //�Ƿ�����
	
	private String propertyName;
	private String javaType;
	
	private String remarks;
	
	private boolean isExcluded = false;
	
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getJdbcType() {
		return jdbcType;
	}
	public void setJdbcType(String jdbcType) {
		this.jdbcType = jdbcType;
	}
	public String getPropertyName() {
		return propertyName;
	}
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	public String getJavaType() {
		return javaType;
	}
	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}
	public boolean isExcluded() {
		return isExcluded;
	}
	public void setExcluded(boolean isExcluded) {
		this.isExcluded = isExcluded;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public int getScale() {
		return scale;
	}
	public void setScale(int scale) {
		this.scale = scale;
	}
	public boolean isNullable() {
		return nullable;
	}
	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}
	public boolean isPk() {
		return pk;
	}
	public void setPk(boolean pk) {
		this.pk = pk;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}
