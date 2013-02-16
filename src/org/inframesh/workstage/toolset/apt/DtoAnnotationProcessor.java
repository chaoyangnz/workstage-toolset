package org.inframesh.workstage.toolset.apt;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.apt.core.env.EclipseAnnotationProcessorEnvironment;
import org.inframesh.workstage.toolset.ToolsetConsole;

import com.sun.mirror.apt.AnnotationProcessor;
import com.sun.mirror.apt.AnnotationProcessorEnvironment;
import com.sun.mirror.apt.Messager;
import com.sun.mirror.declaration.AnnotationMirror;
import com.sun.mirror.declaration.AnnotationTypeDeclaration;
import com.sun.mirror.declaration.AnnotationTypeElementDeclaration;
import com.sun.mirror.declaration.AnnotationValue;
import com.sun.mirror.declaration.ClassDeclaration;
import com.sun.mirror.declaration.Declaration;
import com.sun.mirror.declaration.EnumConstantDeclaration;
import com.sun.mirror.declaration.FieldDeclaration;
import com.sun.mirror.util.SourcePosition;

public class DtoAnnotationProcessor implements AnnotationProcessor {
	
	private AnnotationProcessorEnvironment env;
	
	private EclipseAnnotationProcessorEnvironment eenv;
	@SuppressWarnings("unused")
	private IProject project;
	private Messager messager;
	
	private AnnotationTypeDeclaration tableDeclaration;
	
	private AnnotationTypeDeclaration columnDeclaration;


	public DtoAnnotationProcessor(AnnotationProcessorEnvironment env) {
		this.env = env;
		this.tableDeclaration = (AnnotationTypeDeclaration) env.getTypeDeclaration("com.bankcomm.icms.comm.utils.annotation.Table");
		this.columnDeclaration = (AnnotationTypeDeclaration) env.getTypeDeclaration("com.bankcomm.icms.comm.utils.annotation.Column");
	}
	
	public DtoAnnotationProcessor(EclipseAnnotationProcessorEnvironment eenv) {
		this.eenv = eenv;
		this.tableDeclaration = (AnnotationTypeDeclaration) eenv.getTypeDeclaration("com.bankcomm.icms.comm.utils.annotation.Table");
		this.columnDeclaration = (AnnotationTypeDeclaration) eenv.getTypeDeclaration("com.bankcomm.icms.comm.utils.annotation.Column");
	}

	public void process() {
		if (env  instanceof EclipseAnnotationProcessorEnvironment) {
			eenv  = (EclipseAnnotationProcessorEnvironment) env;
			project = eenv.getJavaProject().getProject();
			messager = eenv.getMessager();
			
			Collection<Declaration> classDeclarations = eenv.getDeclarationsAnnotatedWith(tableDeclaration);
			for (Declaration declaration : classDeclarations) {
				processTableAnnotations((ClassDeclaration) declaration);
			}
			
			if(classDeclarations.size()==0) return;
			
			Collection<Declaration> fieldDeclarations = eenv.getDeclarationsAnnotatedWith(columnDeclaration);
			for(Declaration declaration : fieldDeclarations) {
				processColumnAnnotations((FieldDeclaration) declaration);
			}
			
		}
	}
	
	private void processTableAnnotations(ClassDeclaration classDeclaration) {
		Collection<AnnotationMirror> annotations = classDeclaration.getAnnotationMirrors();
		
		// iterate over the mirrors.
		for (AnnotationMirror mirror : annotations) {
			// if the mirror in this iteration is for our @Table declaration...
			if(mirror.getAnnotationType().getDeclaration().equals(tableDeclaration)) {
				
				// print out the goodies.
				SourcePosition position = mirror.getPosition();
				Map<AnnotationTypeElementDeclaration, AnnotationValue> values = mirror.getElementValues();
		
				ToolsetConsole.out.println("Class Declaration: " + classDeclaration.toString());
				ToolsetConsole.out.println("Position: " + position);
				ToolsetConsole.out.println("Values:");
				for (Map.Entry<AnnotationTypeElementDeclaration, AnnotationValue> entry : values.entrySet()) {
					AnnotationTypeElementDeclaration elemDecl = entry.getKey();
					AnnotationValue value = entry.getValue();
					ToolsetConsole.out.println("    " + elemDecl + "=" + value);
					
					if("name".equals(elemDecl.getSimpleName()) && "".equals(value)) {
						messager.printError(position, "@Tableע��ȱ��name����");
					}
				}
			}
		}
	}

	private void processColumnAnnotations(FieldDeclaration fieldDeclaration) {
		String javaType = fieldDeclaration.getType().toString();
		
		Collection<AnnotationMirror> annotations = fieldDeclaration.getAnnotationMirrors();
		
		for (AnnotationMirror mirror : annotations) {
			// if the mirror in this iteration is for our @Column declaration...
			if(mirror.getAnnotationType().getDeclaration().equals(columnDeclaration)) {
				
				// print out the goodies.
				SourcePosition position = mirror.getPosition();
				// ��ȡannotation�е�ֵ,��name, JdbcType..
				Map<AnnotationTypeElementDeclaration, AnnotationValue> map = mirror.getElementValues();
		
				ToolsetConsole.out.println("Field Declaration: " + fieldDeclaration.toString());
				ToolsetConsole.out.println("Position: " + position);
				ToolsetConsole.out.println("Values:");
				for (Map.Entry<AnnotationTypeElementDeclaration, AnnotationValue> entry : map.entrySet()) {
					AnnotationTypeElementDeclaration elemDecl = entry.getKey();
					AnnotationValue value = entry.getValue();
					ToolsetConsole.out.println("    " + elemDecl + "=" + value);
					
					if("type".equals(elemDecl.getSimpleName())){
						EnumConstantDeclaration enumValue = (EnumConstantDeclaration) value.getValue();
						String jdbcType = enumValue.getSimpleName();
						
						String message = compatible(javaType, jdbcType);
						if( message != null) {
							messager.printWarning(position, message);
						}
					}
				}

			}
		}
	}
	
	//�������ͼ�
	private static Set<String> char_set = new HashSet();
	private static Set<String> int_set = new HashSet();
	private static Set<String> decimal_set = new HashSet();
	private static Set<String> date_set = new HashSet();
	static {
		char_set.add(String.class.getName());
		
		int_set.add("int");
		int_set.add("long");
		int_set.add("short");
		int_set.add(Integer.class.getName());
		int_set.add(Long.class.getName());
		int_set.add(Short.class.getName());
		
		decimal_set.add("float");
		decimal_set.add("double");
		decimal_set.add(Float.class.getName());
		decimal_set.add(Double.class.getName());
		decimal_set.add(BigDecimal.class.getName());
		
		date_set.add(String.class.getName());
	}
	

	private String compatible(String javaType, String jdbcType) {
		String message = null;
		if("CHAR".equals(jdbcType) || "VARCHAR".equals(jdbcType)){
			if(!char_set.contains(javaType)) {
				message = jdbcType + "(Jdbc����)��" + javaType + "(JAVA����)�����ݣ���ݿ�������ܳ���.";
				message += "�Ƽ���Java���ͣ�";
				for(String item : char_set) {
					message += item + "  ";
				}
			}
		} 
		
		if("SMALLINT".equals(javaType) || "INTEGER".equals(jdbcType) || "BIGINT".equals(jdbcType)){
			if(!int_set.contains(javaType)){
				message = jdbcType + "(Jdbc����)��" + javaType + "(JAVA����)�����ݣ���ݿ�������ܳ���.";
				message += "�Ƽ���Java���ͣ�";
				for(String item : int_set) {
					message += item + "  ";
				}
			}
		}
		
		if("DECIMAL".equals(jdbcType) || "NUMERIC".equals(jdbcType) || "DECFLOAT".equals(jdbcType) ||
			"REAL".equals(jdbcType) || "FLOAT".equals(jdbcType) || "DOUBLE".equals(jdbcType)	){
			if(!decimal_set.contains(javaType)) {
				message = jdbcType + "(Jdbc����)��" + javaType + "(JAVA����)�����ݣ���ݿ�������ܳ���.";
				message += "�Ƽ���Java���ͣ�";
				for(String item : decimal_set) {
					message += item + "  ";
				}
			}
		}
		
		if("TIMESTAMP".equals(jdbcType) || "DATE".equals(jdbcType) || "TIME".equals(jdbcType) ){
			if(!date_set.contains(javaType)) {
				message = jdbcType + "(Jdbc����)ӳ�����Ͳ��Ƽ���";
				message += "�Ƽ���Java���ͣ�";
				for(String item : date_set) {
					message += item + "  ";
				}
			}
		}
		
		return message;
	}
}
