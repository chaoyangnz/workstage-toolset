package org.inframesh.workstage.toolset.ormapper.core.model;

public class TableMeta {
	private String name;
	private String type;
	private String ctime;
	private String alertTime;
	
	public String getTableName() {
		return name;
	}
	public void setTableName(String tableName) {
		this.name = tableName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCreateTime() {
		return ctime;
	}
	public void setCreateTime(String createTime) {
		this.ctime = createTime;
	}
	public String getAlertTime() {
		return alertTime;
	}
	public void setAlertTime(String alertTime) {
		this.alertTime = alertTime;
	}
}
