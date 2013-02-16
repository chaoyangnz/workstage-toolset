package org.inframesh.workstage.toolset.core.database;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.preference.IPreferenceStore;
import org.inframesh.workstage.toolset.Activator;
import org.inframesh.workstage.toolset.ToolsetConsole;
import org.inframesh.workstage.toolset.ormapper.core.PluginToolkit;
import org.inframesh.workstage.toolset.ormapper.core.model.MapSetting;
import org.inframesh.workstage.toolset.ormapper.core.model.TableMeta;
import org.inframesh.workstage.toolset.ui.preference.PreferenceConstants;


public class DatabaseManager {

	public String driverClassName = "";
	public String url = "";
	public String userid = "";
	public String password = "";
	
	public boolean initialized = false;

	private final ConnectionPool pool;

	/*-------------------For Singleton--------------------------------*/
	private static DatabaseManager manager;

	private DatabaseManager() {
		initDatabaseConfig();
		pool = new ConnectionPool(driverClassName, url, userid, password);

		try {
			pool.createPool();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static DatabaseManager getInstance() {
		if (manager == null) {
				manager = new DatabaseManager();
		}

		return manager;
	}

	/*----------------------------------------------------------------*/

	private void initDatabaseConfig() {
		IPreferenceStore pref = Activator.getDefault().getPreferenceStore();

		driverClassName = pref.getString(PreferenceConstants.DATASOURCE_DRIVER_CLASS_NAME);
		url = pref.getString(PreferenceConstants.DATASOURCE_URL);
		userid = pref.getString(PreferenceConstants.DATASOURCE_USERID);
		password = pref.getString(PreferenceConstants.DATASOURCE_PASSWORD);
	}
	
//	private void initDatabaseConfig() {
//		driverClassName = "com.ibm.db2.jcc.DB2Driver";
//		url = "jdbc:db2://localhost:50001/icms";
//		userid = "db2admin";
//		password = "password";
//	}

	public ResultSet executeQuery(String sql) throws SQLException {
		Connection conn = pool.getConnection();
		Statement statement = conn.createStatement();
		ResultSet rs = statement.executeQuery(sql);
		
		pool.returnConnection(conn);
		return rs;
	}

	public List executeQuery(String sql, Class entityClass) throws Exception {
		List list = new ArrayList();

		ResultSet rs = executeQuery(sql);
		while (rs.next()) {
			Object bean = getBeanFromResultSet(rs, entityClass);
			list.add(bean);
		}

		return list;
	}
	
	public List executeQuery(String sql, Class entityClass, Map nameMap) throws Exception {
		List list = new ArrayList();

		ResultSet rs = executeQuery(sql);
		while (rs.next()) {
			Object bean = getBeanFromResultSet(rs, entityClass, nameMap);
			list.add(bean);
		}

		return list;
	}


	private TableEntity parseTableEntity(ResultSet rs) throws SQLException {

		TableEntity table = null;

		// ����ResultSet�ṹ����Ϣ
		if (rs != null) {
			ResultSetMetaData rsMetaData = rs.getMetaData();

			int columnCount = rsMetaData.getColumnCount();

			table = new TableEntity(columnCount);

			// ��ȡ�ֶ���ƣ�����
			for (int i = 0; i < columnCount; i++) {
				String columnName = rsMetaData.getColumnName(i + 1);

				int columnType = rsMetaData.getColumnType(i + 1);

				table.setColumnName(columnName, i);

				table.setColumnType(columnType, i);
			}
		}

		return table;
	}

	private Map parseBeanMethod(Class entityClass) {
		// ��ȡʵ���ж���ķ���
		Map hmMethods = new HashMap();

		for (int i = 0; i < entityClass.getDeclaredMethods().length; i++) {
			MethodEntity methodEntity = new MethodEntity();

			// ���������
			String methodName = entityClass.getDeclaredMethods()[i].getName();

			String methodKey = methodName.toUpperCase();

			// �����Ĳ���
			Class[] paramTypes = entityClass.getDeclaredMethods()[i].getParameterTypes();

			methodEntity.setMethodName(methodName);

			methodEntity.setMethodParamTypes(paramTypes);

			// ���?������
			if (hmMethods.containsKey(methodKey)) {
				methodEntity.setOverloadMethodNum(methodEntity.getOverloadMethodNum() + 1);

				methodEntity.setOverloadMethodsParamTypes(paramTypes);

			} else {
				hmMethods.put(methodKey, methodEntity);
			}
		}

		return hmMethods;
	}

	public Object getBeanFromResultSet(ResultSet rs, Class entityClass) throws Exception {
		
		TableEntity dataTable = this.parseTableEntity(rs);
		
		Map hsMethods = this.parseBeanMethod(entityClass);
		

		Object objEntity = entityClass.newInstance();

		Method method = null;

		int nColumnCount = dataTable.getColumnCount();

		String[] strColumnNames = dataTable.getColumnNames();

		for (int i = 0; i < nColumnCount; i++) {
			// ��ȡ�ֶ�ֵ
			Object objColumnValue = rs.getObject(strColumnNames[i]);

			// HashMap�еķ�����keyֵ
			String strMethodKey = null;

			// ��ȡset������
			if (strColumnNames[i] != null) {
				strMethodKey = String.valueOf("SET"	+ strColumnNames[i].toUpperCase());
			}

			// ֵ�ͷ�������Ϊ���Ҵ���,���﷽����Ϊ�ռ���,ֵ����Ϊ�յ�
			if (strMethodKey != null && hsMethods.containsKey(strMethodKey)) {

				// �ж��ֶε�����,�������������
				try {					
					MethodEntity methodEntity = (MethodEntity) hsMethods.get(strMethodKey);

					String methodName = methodEntity.getMethodName();

					int overloadMethodNum = methodEntity.getOverloadMethodNum();

					Class[] paramTypes = methodEntity.getMethodParamTypes();

					method = entityClass.getMethod(methodName, paramTypes);

					// ������ط����� >
					// 1�����ж��Ƿ���java.lang.IllegalArgumentException�쳣��ѭ������
					try {
						// ���ò���,ʵ�����ʵ����󷽷�����
						method.invoke(objEntity, new Object[] { objColumnValue });
					} catch (java.lang.IllegalArgumentException e) {

						// �������ط���
						for (int j = 1; j < overloadMethodNum; j++) {
							try {
								Class[] overloadParamTypes = methodEntity.getOverloadMethodsParamTypes(j - 1);

								method = entityClass.getMethod(methodName,
										overloadParamTypes);

								method.invoke(objEntity, new Object[] { objColumnValue });

								break;

							} catch (java.lang.IllegalArgumentException ex) {
								continue;
							}
						}
					}
				} catch (NoSuchMethodException e) {
					throw new NoSuchMethodException();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}

		return objEntity;
	}
	
	public Object getBeanFromResultSet(ResultSet rs, Class entityClass, Map nameMap) throws Exception {
		
		TableEntity dataTable = this.parseTableEntity(rs);
		
		Map hsMethods = this.parseBeanMethod(entityClass);
		

		Object objEntity = entityClass.newInstance();

		Method method = null;

		int nColumnCount = dataTable.getColumnCount();

		String[] strColumnNames = dataTable.getColumnNames();

		for (int i = 0; i < nColumnCount; i++) {
			// ��ȡ�ֶ�ֵ
			Object objColumnValue = rs.getObject(strColumnNames[i]);

			// HashMap�еķ�����keyֵ
			String strMethodKey = null;

			// ��ȡset������
			if (strColumnNames[i] != null) {
				strMethodKey = String.valueOf("SET"	+ strColumnNames[i].toUpperCase());
			}

			// ֵ�ͷ�������Ϊ��,���﷽����Ϊ�ռ���,ֵ����Ϊ�յ�
			if (strMethodKey == null || !hsMethods.containsKey(strMethodKey)) {
				String mappedName = (String) nameMap.get(strColumnNames[i]);
				if(mappedName != null && hsMethods.containsKey(String.valueOf("SET"	+ mappedName.toUpperCase()))) {
					strMethodKey = String.valueOf("SET"	+ mappedName.toUpperCase());
				} else {
					continue;
				}			
			}
			
			// �ж��ֶε�����,�������������
			try {					
				MethodEntity methodEntity = (MethodEntity) hsMethods.get(strMethodKey);

				String methodName = methodEntity.getMethodName();

				int overloadMethodNum = methodEntity.getOverloadMethodNum();

				Class[] paramTypes = methodEntity.getMethodParamTypes();

				method = entityClass.getMethod(methodName, paramTypes);

				// ������ط����� >
				// 1�����ж��Ƿ���java.lang.IllegalArgumentException�쳣��ѭ������
				try {
					// ���ò���,ʵ�����ʵ����󷽷�����
					method.invoke(objEntity, new Object[] { objColumnValue });
				} catch (java.lang.IllegalArgumentException e) {

					// �������ط���
					for (int j = 1; j < overloadMethodNum; j++) {
						try {
							Class[] overloadParamTypes = methodEntity.getOverloadMethodsParamTypes(j - 1);

							method = entityClass.getMethod(methodName,
									overloadParamTypes);

							method.invoke(objEntity, new Object[] { objColumnValue });

							break;

						} catch (java.lang.IllegalArgumentException ex) {
							continue;
						}
					}
				}
			} catch (NoSuchMethodException e) {
				throw new NoSuchMethodException();
			} catch (Exception ex) {
				ex.printStackTrace();
			}		
		}

		return objEntity;
	}

	// ����������
	public static boolean testConnection() {
		try {
			IPreferenceStore pref = Activator.getDefault().getPreferenceStore();

			String driverClassName = pref.getString(PreferenceConstants.DATASOURCE_DRIVER_CLASS_NAME);
			String url = pref.getString(PreferenceConstants.DATASOURCE_URL);
			String userid = pref.getString(PreferenceConstants.DATASOURCE_USERID);
			String password = pref.getString(PreferenceConstants.DATASOURCE_PASSWORD);
			
			Class driverClass = Class.forName(driverClassName);
			Driver driver = (Driver) driverClass.newInstance();

			DriverManager.registerDriver(driver);
			
			Connection testConnection = DriverManager.getConnection(url, userid, password);
			Statement statement = testConnection.createStatement();
			
			ToolsetConsole.out.println("Test connection success!");
			
			statement.close();
			testConnection.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	// ���������ݿ����ã�ˢ�����manager�е�pool
	public void refresh() {
			if(pool != null) {
				dispose();
			}
			
			ToolsetConsole.out.println("Refresh: disposed old pool, now create new pool..");
			manager = new DatabaseManager();
		
	}

	public void dispose() {
		// �������̹߳ر�ԭ��pool,��Ϊ�رղ����п����еȴ�5���ͣ��
		final ConnectionPool oldPool = pool;
//		new Thread() {
//			@Override
//			public void run() {
//				try {
//					oldPool.closePool();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//			}
//		}.start();
		try {
			oldPool.closePool();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		manager = null;
		ToolsetConsole.out.println("I am dying...Bye-bye!");
	}
	
public List getAllTableMeta() {
		
		List list = new ArrayList();
		try {
			ResultSet result =  this.executeQuery("select DISTINCT TABNAME AS NAME, TYPE, CREATE_TIME AS CTIME from SYSCAT.TABLES WHERE TABSCHEMA=CURRENT SCHEMA");
			while(result.next()) {
				TableMeta table = new TableMeta();
				table.setTableName(result.getString("name"));
				table.setType(result.getString("type"));
				table.setCreateTime(result.getString("ctime"));
				list.add(table);
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		
		return list;
	}
	
	public List getDefaultMapSetting(String tableName) {
		
		List list = new ArrayList();
		try {
			ResultSet result =  this.executeQuery("SELECT COLNAME AS NAME, TYPENAME AS COLTYPE, LENGTH, SCALE, " +
					//"IDENTITY, " +
					"KEYSEQ, NULLS, REMARKS FROM SYSCAT.COLUMNS WHERE TABSCHEMA=CURRENT SCHEMA AND TABNAME='" + tableName + "' order by COLNO");
			while(result.next()) {
				MapSetting mapSetting = new MapSetting();
				mapSetting.setFieldName(result.getString("NAME"));
				mapSetting.setJdbcType(fix(result.getString("COLTYPE")));
				mapSetting.setLength(result.getInt("LENGTH"));
				mapSetting.setScale(result.getInt("SCALE"));
				mapSetting.setPk(result.getObject("KEYSEQ") != null);//("Y".equals(result.getString("IDENTITY")) ? true : false);
				mapSetting.setNullable("Y".equals(result.getString("NULLS")) ? true : false);
				mapSetting.setRemarks(result.getString("REMARKS"));

				String propertyName = PluginToolkit.convertName(mapSetting.getFieldName(), "_", true);
				mapSetting.setPropertyName(propertyName);
				
				String javaType = PluginToolkit.convertType(mapSetting.getJdbcType(), "DB2", "8.x");
				mapSetting.setJavaType(javaType);
				
				list.add(mapSetting);
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		
		return list;
	}

	private String fix(String string) {
		string = string.trim();
		if("TIMESTMP".equals(string)) {
			string = "TIMESTAMP"; //DB2中TIMESTAMP为TIMESTMP
		}
		return string;
	}
	
//	public static void main(String[] args) {
//		DatabaseManager manager = DatabaseManager.getInstance();
//		List list = null;
//		Map nameMap = new HashMap();
//		nameMap.put("NAME", "tableName");
//		nameMap.put("CTIME", "createTime");
//		
//		try {
//			list = manager.executeQuery("select distinct name, type, ctime from SYSIBM.SYSTABLES where creator ='DB2ADMIN'", TableMeta.class, nameMap);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
}
