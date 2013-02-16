package org.inframesh.workstage.toolset.javaflex.core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jface.preference.IPreferenceStore;
import org.inframesh.eclipse.util.FileUtil;
import org.inframesh.workstage.toolset.Activator;
import org.inframesh.workstage.toolset.ToolsetConsole;
import org.inframesh.workstage.toolset.ui.preference.PreferenceConstants;


public class BeanConverter {

	public static final String BO_SUFFIX = "";//"BO";
	public static final String VO_SUFFIX = "";//"VO";
	public static final String BO_SUFFIX_EX = "";//"Bo";
	public static final String VO_SUFFIX_EX = "";//"Vo";

	private static String repeat(String c, int count) {
		String temp = "";
		for (int i = 0; i < count; i++) {
			temp += c;
		}

		return temp;
	}

	private static String convertType(String typeName) {
		// ToolsetConsole.out.println("typename " + typeName);
		// ��ֵ�͵Ķ�ת��Number
		if (isNumericType(typeName)) {
			return "Number";
		} else if (isBooleanType(typeName)) {
			return "boolean";
		} else if (isCollectionType(typeName)) {
			return "Array";
		} else if (isStringableType(typeName)) {
			return "String";
		} else {
			return typeName + BeanConverter.VO_SUFFIX;// �Զ������͵ģ�����VO��β
		}
	}

	private static boolean isBooleanType(String typeName) {
		return typeName.equals("Boolean") || typeName.equals("boolean");
	}

	private static boolean isNumericType(String typeName) {
		return typeName.equals("BigDecimal") || typeName.equals("Decimal")
				|| typeName.equals("Double") || typeName.equals("double")
				|| typeName.equals("Float") || typeName.equals("float")
				|| typeName.equals("Long") || typeName.equals("long")
				|| typeName.equals("Integer") || typeName.equals("int")
				|| typeName.equals("Short") || typeName.equals("short")
				|| typeName.equals("Byte") || typeName.equals("byte");

	}

	private static boolean isStringableType(String typeName) {
		return typeName.equals("String") || typeName.equals("Character")
				|| typeName.equals("char") || typeName.equals("Date")
				|| typeName.equals("Timestamp");
	}

	private static boolean isCollectionType(String typeName) {
		return typeName.equals("List") || typeName.equals("ArrayList")
				|| typeName.equals("Set") || typeName.equals("Map")
				|| typeName.equals("HashMap");
	}

	private static String getInitialValue(String typeName) {
		if (typeName.equals("Number")) {
			return "0";
		} else if (typeName.equals("boolean")) {
			return "false";
		} else if (typeName.equals("String")) {
			return "\"\"";
		} else {
			return "new " + typeName + BeanConverter.VO_SUFFIX;
		}

	}
	
	private static void makeDirByPackageName(String rootFolder, String packageName) {

		File folder = new File(rootFolder + "\\"
			+ packageName.replace(".", "\\"));
		if (!folder.exists()) {
		    ToolsetConsole.out.println("Now create the package hiberarchy..");
		    folder.mkdirs();
		    ToolsetConsole.out.println("OK, create directory done!");
		}
	}

	public static void generaterActionscriptFile(IFile file,
			String voPackageRootFolder, String voPackageName) throws Exception {
		
		IPreferenceStore pref = Activator.getDefault().getPreferenceStore();
		
		String absolutePath = file.getLocation().toOSString();
		String content = "";
		try {
			content = FileUtil.read(absolutePath);
		} catch (IOException e) {
			e.printStackTrace();
		}

		ASTParser parser = ASTParser.newParser(AST.JLS3);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setSource(content.toCharArray());
		CompilationUnit node = (CompilationUnit) parser.createAST(null);

		// ��ȡ����
		List types = node.types();

		// ȡ����������
		TypeDeclaration typeDec = (TypeDeclaration) types.get(0);

		// ##############��ȡԴ����ṹ��Ϣ#################
		// ����import
		List importList = node.imports();
		// ȡ�ð���package
		PackageDeclaration packetDec = node.getPackage();
		// ȡ������class
		String className = typeDec.getName().toString();
		// ȡ�ú���(Method)�����б�
		MethodDeclaration methodDec[] = typeDec.getMethods();
		// ȡ�ú���(Field)�����б�
		FieldDeclaration fieldDec[] = typeDec.getFields();

//		// ##############��ӡ����ṹ��Ϣ#################
//		// �������
//		ToolsetConsole.out.println("��:");
//		ToolsetConsole.out.println(packetDec.getName());
//		// �������import
//		ToolsetConsole.out.println("����import:");
//		for (Object obj : importList) {
//			ImportDeclaration importDec = (ImportDeclaration) obj;
//			ToolsetConsole.out.println(importDec.getName());
//		}
//		// �������
//		ToolsetConsole.out.println("��:");
//		ToolsetConsole.out.println(className);
//		// ѭ������������
//		ToolsetConsole.out.println("����:");
//		for (MethodDeclaration method : methodDec) {
//			ToolsetConsole.out.println(method.getName());
//		}
//
//		// ѭ���������
//		ToolsetConsole.out.println("����:");
//		for (FieldDeclaration fieldDecEle : fieldDec) {
////			// public static
////			for (Object modifiObj : fieldDecEle.modifiers()) {
////				Modifier modify = (Modifier) modifiObj;
////				ToolsetConsole.out.print(modify + "-");
////			}
//			ToolsetConsole.out.println(fieldDecEle.getType());
//			for (Object obj : fieldDecEle.fragments()) {
//				VariableDeclarationFragment frag = (VariableDeclarationFragment) obj;
//				ToolsetConsole.out.println("[FIELD_NAME:]" + frag.getName());
//			}
//		}
		
		// û�����ð����ȡ��java pojoһ��İ���
		if (voPackageName == null || "".equals(voPackageName)) {
			voPackageName = packetDec.getName().toString();
		}
		
		makeDirByPackageName(voPackageRootFolder, voPackageName);

		File f = new File(voPackageRootFolder
				+ "\\"
				+ voPackageName.replace(".", "\\")
				+ "\\"
				+ className.replace(BO_SUFFIX, VO_SUFFIX).replace(BO_SUFFIX_EX,
						VO_SUFFIX_EX) + ".as");

		BufferedWriter bw = new BufferedWriter(new FileWriter(f));

		// ע��
		String comments = pref.getString(PreferenceConstants.CODE_FILE_COMMENTS);

		bw.write(comments);
		
		String meta = "/**\n * Generated by ormapper/javaflex plug-in\n */\n\n";
		bw.write(meta);

		// ����
		bw.write("package " + voPackageName + "\n{\n");

		bw.write(repeat(" ", 4) + "[Bindable]\n");
		bw.write(repeat(" ", 4) + "[RemoteClass(alias=\"" + packetDec.getName().toString() + "." + className + "\")]\n");
		// д��
		bw.write(repeat(" ", 4)
				+ "public class "
				+ className.replace(BO_SUFFIX, VO_SUFFIX).replace(BO_SUFFIX_EX,
						VO_SUFFIX_EX) + "\n");
		bw.write(repeat(" ", 4) + "{\n");
		
		// дclassName
		bw.write(repeat(" ", 8) + "public var className:String = \"" + className.replace(BO_SUFFIX, VO_SUFFIX).replace(BO_SUFFIX_EX,
				VO_SUFFIX_EX) + "\";\n");
		
		// дpropertyNames
		bw.write(repeat(" ", 8) + "public var propertyNames:Array = [");
		int index = 0;
		for (FieldDeclaration fieldDecElem : fieldDec) {
			String fieldName = "";
			String fieldType = convertType(fieldDecElem.getType().toString());
//			 public static
//			 for (Object modifiObj : fieldDecElem.modifiers()) {
//			 Modifier modify = (Modifier) modifiObj;
//			 ToolsetConsole.out.print(modify + "-");
//			 }

			for (Object obj : fieldDecElem.fragments()) {
				VariableDeclarationFragment frag = (VariableDeclarationFragment) obj;
				fieldName = frag.getName().toString();
				break;
			}
			if(index != 0) bw.write(", ");
			bw.write("\"" + fieldName + "\"");
			
			++index;
		}
		bw.write("];\n\n");

		// д����
		for (FieldDeclaration fieldDecElem : fieldDec) {
			String fieldName = "";
			String fieldType = convertType(fieldDecElem.getType().toString());
//			 public static
//			 for (Object modifiObj : fieldDecElem.modifiers()) {
//			 Modifier modify = (Modifier) modifiObj;
//			 ToolsetConsole.out.print(modify + "-");
//			 }

			for (Object obj : fieldDecElem.fragments()) {
				VariableDeclarationFragment frag = (VariableDeclarationFragment) obj;
				fieldName = frag.getName().toString();
				break;
			}

			bw.write(repeat(" ", 8) + "private var _" + fieldName + ":"
					+ fieldType + " = " + getInitialValue(fieldType) + ";\n");
		}

		bw.write("\n");
		// д�յĹ��캯��
		bw.write(repeat(" ", 8)
				+ "public function "
				+ className.replace(BO_SUFFIX, VO_SUFFIX).replace(BO_SUFFIX_EX,
						VO_SUFFIX_EX) + "(){}\n\n");

		// д setter/getter ����
		for (FieldDeclaration fieldDecElem : fieldDec) {
			String fieldName = "";
			String fieldType = fieldDecElem.getType().toString();
//			// public static
//			for (Object modifiObj : fieldDecElem.modifiers()) {
//				Modifier modify = (Modifier) modifiObj;
//				ToolsetConsole.out.print(modify + "-");
//			}

			for (Object obj : fieldDecElem.fragments()) {
				VariableDeclarationFragment frag = (VariableDeclarationFragment) obj;
				fieldName = frag.getName().toString();
			}

			// setter
			bw.write(repeat(" ", 8) + "public function set " + fieldName
					+ "(value:" + convertType(fieldType) + "):void{\n");
			bw.write(repeat(" ", 12) + "this._" + fieldName + " = value;\n");
			bw.write(repeat(" ", 8) + "}\n\n");
			// getter
			bw.write(repeat(" ", 8) + "public function get " + fieldName
					+ "():" + convertType(fieldType) + "{\n");
			bw.write(repeat(" ", 12) + "return this._" + fieldName + ";\n");
			bw.write(repeat(" ", 8) + "}\n\n\n");

		}

		// -->class end
		bw.write(repeat(" ", 4) + "}\n");

		// ->package end
		bw.write("}");
		bw.close();
	}
	
	public static void generaterActionscriptFileBatch(IFile file,
			String voPackageRootFolder, String voPackageName) throws Exception {

	}
}
