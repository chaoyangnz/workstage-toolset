package org.inframesh.workstage.toolset.ui.preference;

public interface PreferenceConstants {
	
	String DATASOURCE_ALIAS = "datasource.alias";
	String DATASOURCE_TYPE = "datasource.type";
	String DATASOURCE_DRIVER_CLASS_NAME = "datasource.driverClassName";
	String DATASOURCE_URL = "datasource.url";
	String DATASOURCE_USERID = "datasource.ursername";
	String DATASOURCE_PASSWORD = "datasource.password";	
	
	String DATASOURCE_ALIAS_DEFAULT = "default";
	String DATASOURCE_TYPE_DEFAULT = "1";
	String DATASOURCE_DRIVER_CLASS_NAME_DEFAULT = "com.ibm.db2.jcc.DB2Driver";
	String DATASOURCE_URL_DEFAULT = "jdbc:db2://localhost:50000/";
	String DATASOURCE_URSERID_DEFAULT = "db2admin";
	String DATASOURCE_PASSWORD_DEFAULT = "password";
	
	String[][] DATASOURCE_TYPE_CADIDATES = new String [][]{{"DB2", "1"}, 
													       {"MySQL", "2"},
													       {"Oracle", "3"},
													       {"SQL Server", "4"}};
	
	String[][] RULE_DATATABLE = new String[][] {{"Database Naming Convention(Splitted by UNDERLINE(_))", "1"},
												{"Obeject-Oriented Naming Convention(Camel Style)", "2"}};
	
	/*---------------����ģ��------------------------------------*/
	String CODE_FILE_COMMENTS = "code.fileComments";
	String CODE_FILE_COMMENTS_DEFAULT = "/**\n * @author ${user}\n *\n * ${tags}\n */\n\n";
	
	String CODE_MAPPING_META = "code.mappingMeta";
	boolean CODE_MAPPING_META_DEFAULT = true;
	
	
	/*----------------ӳ��---------------------------------------*/
	String MAP_CHAR_PREF = "map.char";
	String MAP_VARCHAR_PREF = "map.varchar";
	String MAP_INTEGER_PREF = "map.integer";
	String MAP_BIGINT_PREF = "map.bigint";
	String MAP_SMALLINT_PREF = "map.smallint";
	String MAP_FLOAT_PREF = "map.float";
	String MAP_DOUBLE_PREF = "map.double";
	String MAP_REAL_PREF = "map.real";
	String MAP_NUMERIC_PREF = "map.numeric";
	String MAP_DECIMAL_PREF = "map.decimal";
	String MAP_DATE_PREF = "map.date";
	String MAP_TIME_PREF = "map.time";
	String MAP_TIMESTAMP_PREF = "map.timestamp";
	String MAP_BLOB_PREF = "map.blob";
	String MAP_CLOB_PREF = "map.clob";
	String MAP_GRAPHIC_PREF = "map.graphic";
	
	String[] MAP_TYPES = {"boolean", "char", "String", "byte", "short", "int", "long", "float", "double", "java.math.BigInteger", "java.math.BigDecimal", "java.sql.Date", "java.util.Date", "java.sql.Time", "java.sql.Timestamp", "byte[]", "Object"};
	
	String[][] MAP_PREF_CADIDATES = new String[][] {{"boolean", "boolean"}, //0
			
													{"char", "char"}, //1
													{"String", "String"}, //2
													
													{"byte", "byte"}, //3 
													{"short", "short"}, //4
													{"int", "int"}, //5
													{"long", "long"}, //6
													{"float", "float"}, //7
													{"double", "double"}, //8
													
													{"java.lang.Boolean", "java.lang.Boolean"}, //9
													{"java.lang.Character", "java.lang.Character"}, //10
													{"java.lang.Byte", "java.lang.Byte"}, //11
													{"java.lang.Short", "java.lang.Short"}, //12
													{"java.lang.Integer", "java.lang.Integer"}, //13
													{"java.lang.Long", "java.lang.Long"}, //14
													{"java.lang.Float", "java.lang.Float"}, //15
													{"java.lang.Double", "java.lang.Double"}, //16
													
													{"java.math.BigInteger", "java.math.BigInteger"}, //17
													{"java.math.BigDecimal", "java.math.BigDecimal"}, //18
													
													{"java.util.Date", "java.util.Date"}, //19
													
													{"java.sql.Date", "java.sql.Date"}, //20
													{"java.sql.Time", "java.sql.Time"}, //21
													{"java.sql.Timestamp", "java.sql.Timestamp"}, //22
													
													{"byte[]", "byte[]"}}; //23
	
	String MAP_CHAR_PREF_DEFAULT = MAP_PREF_CADIDATES[2][1];
	String MAP_VARCHAR_PREF_DEFAULT = MAP_PREF_CADIDATES[2][1];
	String MAP_INTEGER_PREF_DEFAULT = MAP_PREF_CADIDATES[5][1];
	String MAP_BIGINT_PREF_DEFAULT = MAP_PREF_CADIDATES[6][1];
	String MAP_SMALLINT_PREF_DEFAULT = MAP_PREF_CADIDATES[5][1];
	String MAP_FLOAT_PREF_DEFAULT = MAP_PREF_CADIDATES[7][1];
	String MAP_DOUBLE_PREF_DEFAULT = MAP_PREF_CADIDATES[8][1];
	String MAP_REAL_PREF_DEFAULT = MAP_PREF_CADIDATES[7][1];
	String MAP_NUMERIC_PREF_DEFAULT = MAP_PREF_CADIDATES[18][1];
	String MAP_DECIMAL_PREF_DEFAULT = MAP_PREF_CADIDATES[18][1];
	String MAP_DATE_PREF_DEFAULT = MAP_PREF_CADIDATES[19][1];
	String MAP_TIME_PREF_DEFAULT = MAP_PREF_CADIDATES[21][1];
	String MAP_TIMESTAMP_PREF_DEFAULT = MAP_PREF_CADIDATES[22][1];
	String MAP_BLOB_PREF_DEFAULT = MAP_PREF_CADIDATES[2][1];
	String MAP_CLOB_PREF_DEFAULT = MAP_PREF_CADIDATES[2][1];
	String MAP_GRAPHIC_PREF_DEFAULT = MAP_PREF_CADIDATES[2][1];
	
	
	/*----------------��֤---------------------------------------*/
	String VALIDATION_SPRING_FILE_FILTER = "validation.srping.fileFilter";
	String VALIDATION_STRUTS_FILE_FILTER = "validation.struts.fileFilter";
	
	String VALIDATION_SPRING_FILE_FILTER_DEFAULT = "applicationContext*.xml";
	String VALIDATION_STRUTS_FILE_FILTER_DEFAULT = "struts*.xml";
	
	String VALIDATION_SPRING_SCHEMA_VALIDATION = "validation.spring.schemaValidation";
	String VALIDATION_STRUTS_SCHEMA_VALIDATION = "validation.struts.schemaValidation";
	
	boolean VALIDATION_SPRING_SCHEMA_VALIDATION_DEFAULT = false;
	boolean VALIDATION_STRUTS_SCHEMA_VALIDATION_DEFAULT = false;
	
	String VALIDATION_SPRING_SCHEMATRON_CLASSREF_VALIDATION = "validation.spring.schematronValidation.classRef";
	String VALIDATION_SPRING_SCHEMATRON_IDCONFLICT_VALIDATION = "validation.spring.schematronValidation.idConflict";
	String VALIDATION_SPRING_SCHEMATRON_PARENTREF_VALIDATION = "validation.spring.schematronValidation.parentRef";
	String VALIDATION_SPRING_SCHEMATRON_PROPERTYREF_VALIDATION = "validation.spring.schematronValidation.propertyRef";
	String VALIDATION_SPRING_SCHEMATRON_PROPERTYNAME_VALIDATION = "validation.spring.schematronValidation.propertyName";
	
	boolean VALIDATION_SPRING_SCHEMATRON_CLASSREF_VALIDATION_DEFAULT = true;
	boolean VALIDATION_SPRING_SCHEMATRON_IDCONFLICT_VALIDATION_DEFAULT = true;
	boolean VALIDATION_SPRING_SCHEMATRON_PROPERTYREF_VALIDATION_DEFAULT = false;
	boolean VALIDATION_SPRING_SCHEMATRON_PARENTREF_VALIDATION_DEFAULT = false;
	boolean VALIDATION_SPRING_SCHEMATRON_PROPERTYNAME_VALIDATION_DEFAULT = true;
	
	String VALIDATION_STRUTS_SCHEMATRON_ACTIONCLASSREF_VALIDATION = "validation.struts.schematronValidation.classRef";
	String VALIDATION_STRUTS_SCHEMATRON_ACTIONID_VALIDATION = "validation.struts.schematronValidation.actionid";
	String VALIDATION_STRUTS_SCHEMATRON_RESULTNAME_VALIDATION = "validation.struts.schematronValidation.parentRef";
	
	boolean VALIDATION_STRUTS_SCHEMATRON_ACTIONCLASSREF_VALIDATION_DEFAULT = true;
	boolean VALIDATION_STRUTS_SCHEMATRON_ACTIONID_VALIDATION_DEFAULT = true;
	boolean VALIDATION_STRUTS_SCHEMATRON_RESULTNAME_VALIDATION_DEFAULT = true;
	
	
	
}
