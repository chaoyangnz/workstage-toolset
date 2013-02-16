package org.inframesh.workstage.toolset.xml.validator.classreference;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.core.builder.NameEnvironment;

public class ClassReferenceValidator {

	public String validate(IFile file, String importStr) {
		char[][] imports = { importStr.toCharArray() };
		return validate(file.getProject(), imports);
	}

	public String validate(IFile file, char[][] imports) {
		return validate(file.getProject(), imports);
	}

	/**
	 * ��֤import��·���ַ��Ƿ���project��Ŀ��classpath��
	 * 
	 * @param project,
	 *            ��Ҫ��֤�����ļ����ڵ���Ŀ
	 * @param imports,
	 *            import����·���ַ�
	 * @return, ���import�ַ���ȷ�򷵻�null,���򷵻س����·��
	 */
	public String validate(IProject project, char[][] imports) {
		String result = null;
		IJavaProject javaProject = JavaCore.create(project);
		if (javaProject == null) {
			return null;
		}

		else {
			NameEnvironment nameEnviroment = new NameEnvironment(javaProject);

			for (int i = 0; i < imports.length; i++) {
				char[] importDeclaration = imports[i];
				char[][] splitDeclaration = CharOperation.splitOn('.',
						importDeclaration);
				int splitLength = splitDeclaration.length;
				if (splitLength > 0) {
					char[] pkgName = splitDeclaration[splitLength - 1];
					if (pkgName.length == 1 && pkgName[0] == '*') {
						char[][] parentName;
						switch (splitLength) {
						case 1:
							parentName = null;
							break;
						case 2:
							parentName = null;
							pkgName = splitDeclaration[splitLength - 2];
							break;
						default:
							parentName = CharOperation.subarray(
									splitDeclaration, 0, splitLength - 2);
							pkgName = splitDeclaration[splitLength - 2];
						}
						if (!nameEnviroment.isPackage(parentName, pkgName)) {
							result = new String(importDeclaration);
						}
					} else {
						if (nameEnviroment.findType(splitDeclaration) == null) {
							result = new String(importDeclaration);
						}
					}
				} else {
					result = new String(importDeclaration);
				}

			}
		}

		return result;
	}
}
