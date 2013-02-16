/*  
 *  Copyright(c) 2010-2011 Drowell Information Technology Limited 
 *  
 *  Licensed under the Drowell Business Software License (DBSL), 
 *	Version 1.0 ; you may obtain a copy of the license at
 *
 *  	http://www.drowell.com/licenses/LICENSE-1.0 
 *  
 *  Software distributed under the License is distributed  on an "AS IS" 
 *  BASIS but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the License 
 *  for more details.
 */
package org.inframesh.workstage.toolset.ws.core;

import static org.inframesh.fundus.base.text.NameConvention.LOWER_CAMEL;
import static org.inframesh.fundus.base.text.NameConvention.UPPER_CAMEL;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IMember;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.ISourceRange;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.Signature;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.BodyDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Javadoc;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.NodeFinder;
import org.eclipse.jdt.core.dom.TagElement;
import org.eclipse.jdt.core.dom.TextElement;
import org.inframesh.fundus.base.Containers;
import org.inframesh.fundus.base.text.Joiner;
import org.inframesh.workstage.toolset.ToolsetConsole;
import org.inframesh.workstage.toolset.ws.model.SchemaComplexType;
import org.inframesh.workstage.toolset.ws.model.SchemaElement;
import org.inframesh.workstage.toolset.ws.model.SchemaListType;
import org.inframesh.workstage.toolset.ws.model.SchemaSimpleType;
import org.inframesh.workstage.toolset.ws.model.SchemaType;
import org.inframesh.workstage.toolset.ws.model.WsdlBinding;
import org.inframesh.workstage.toolset.ws.model.WsdlBindingOperation;
import org.inframesh.workstage.toolset.ws.model.WsdlMessage;
import org.inframesh.workstage.toolset.ws.model.WsdlOperation;
import org.inframesh.workstage.toolset.ws.model.WsdlPortType;


/**
 * 
 * @since soadtoolkit
 * @version 
 *
 * @author <a href="mailto:Josh.Yoah@gmail.com">杨超 </a>
 */
public class WebServiceGenerator {
	
	public IFile file;
	
	public String serviceName;
	public String wsdlFile;
	public String wsdlNsURI;
	
	public String typeXsdFile;
	public String typeNs;//namespace缩写
	public String typeNsURI;
	
	public String elemXsdFile;
	public String elemNs;
	public String elemNsURI;
	
	public boolean mixed;
	
	public List<IMethod> methods;
	
	/************************************************************/
	
	private static final String SIMPLE_TYPE_SURFIX = "_SType";
	private static final String COMPLEX_TYPE_SURFIX = "_CType";
	
	private static final String REQ_TYPE_SURFIX = "Request";
	private static final String RSP_TYPE_SURFIX = "Response";
	
	private static final String WSDL_MESSAGE_SURFIX = "Message";
	private static final String WSDL_PORTTYPE_SURFIX = "PortType";
	private static final String WSDL_BINDING_SURFIX = "SOAP";
	
	private static final String HEADER_TEMPLATE = "headerOfSOAP.vm";
	private static final String WSDL_TEMPLATE = "wsdl.vm";
	private static final String TYPE_XSD_TEMPLATE = "types.xsd.vm";
	private static final String ELEM_XSD_TEMPLATE = "elements.xsd.vm";
	
	private Set<SchemaSimpleType> STYPES = new HashSet<SchemaSimpleType>();
	private Set<SchemaListType> LTYPES = new HashSet<SchemaListType>();
	private Set<SchemaComplexType> CTYPES = new HashSet<SchemaComplexType>();
	private Set<SchemaComplexType> REQTYPES = new HashSet<SchemaComplexType>();
	private Set<SchemaComplexType> RSPTYPES = new HashSet<SchemaComplexType>();
	
	private Set<SchemaElement> ELEMENTS = new HashSet<SchemaElement>();
	
	private static final Map<String, String> SIMPLE_TYPES = new HashMap<String, String>();
	private static final Set<String> LIST_TYPES = new HashSet<String>();
	static {
		SIMPLE_TYPES.put("boolean", "boolean");					SIMPLE_TYPES.put("java.lang.Boolean", "bool");
		SIMPLE_TYPES.put("byte", "byte");						SIMPLE_TYPES.put("java.lang.Byte", "byte");
		SIMPLE_TYPES.put("short", "short");						SIMPLE_TYPES.put("java.lang.Short", "short");
		SIMPLE_TYPES.put("int", "int");							SIMPLE_TYPES.put("java.lang.Integer", "int");
		SIMPLE_TYPES.put("long", "long");						SIMPLE_TYPES.put("java.lang.Long", "long");
		SIMPLE_TYPES.put("float", "float");						SIMPLE_TYPES.put("java.lang.Float", "float");
		SIMPLE_TYPES.put("double", "double");					SIMPLE_TYPES.put("java.lang.Double", "double");
		SIMPLE_TYPES.put("java.math.BigDecimal", "decimal");	SIMPLE_TYPES.put("java.math.BigInteger", "int");
		SIMPLE_TYPES.put("java.lang.String", "string");			SIMPLE_TYPES.put("java.util.Date", "date"); 			
		SIMPLE_TYPES.put("java.sql.Date", "date");				SIMPLE_TYPES.put("java.sql.Timestamp", "dateTime");
		//List or Set
		LIST_TYPES.add("java.util.List");						LIST_TYPES.add("java.util.Set");
	}
	
	/**
	 * 获取所有的Clsss定义中涉及到Type的Qualified名字
	 * 
	 * @param declaringType
	 * @param typeSignature
	 * @return
	 * @throws JavaModelException
	 * @throws IllegalArgumentException
	 */
	private static String getQualifiedNameBySignature(IType declaringType, String typeSignature) throws JavaModelException, IllegalArgumentException {
		int kind = Signature.getTypeSignatureKind(typeSignature);
		if(kind == Signature.BASE_TYPE_SIGNATURE) {
			return Signature.toString(typeSignature);
		} else {
			
			String[][] s = declaringType.resolveType(Signature.toString(typeSignature));
			if(s == null) {
				ToolsetConsole.out.println(typeSignature + " in " + declaringType);
			}
			return Joiner.on(".").join(s[0]);
		}
	}
	
	/**
	 * 对于BASE_TYPE_SIGNATURE(primetive or void),返回null
	 * 
	 * @param declaringType
	 * @param typeSignature
	 * @return
	 * @throws JavaModelException
	 * @throws IllegalArgumentException
	 */
	private static IType findTypeBySignature(IType declaringType, String typeSignature) throws JavaModelException, IllegalArgumentException {

		return declaringType.getJavaProject().findType(getQualifiedNameBySignature(declaringType, typeSignature));
	}
	
	/**
	 * 
	 * 
	 * @param typeSignature
	 * @param hintName
	 * @return
	 * @throws IllegalArgumentException 
	 * @throws JavaModelException 
	 */
	private SchemaSimpleType defineSchemaSimpleType(IType declaringType, String typeSignature, String hintName, String comments) throws JavaModelException, IllegalArgumentException {
		String qualifiedTypeName = getQualifiedNameBySignature(declaringType, typeSignature);
		
		SchemaSimpleType stype = new SchemaSimpleType();
		stype.setName(LOWER_CAMEL.to(UPPER_CAMEL, hintName) + SIMPLE_TYPE_SURFIX);
		stype.setDocumentation(comments);
		stype.setRestriction(SIMPLE_TYPES.get(qualifiedTypeName));
		
		STYPES.add(stype);
		
		return stype;
	}
	
	/**
	 * 
	 * 
	 * @param typeSignature
	 * @param hintName
	 * @return
	 * @throws IllegalArgumentException 
	 * @throws JavaModelException 
	 */
	private SchemaListType defineSchemaListType(IType declaringType, String typeSignature, String hintName, String comments) throws JavaModelException, IllegalArgumentException {
		SchemaListType ltype = new SchemaListType();
		ltype.setName(LOWER_CAMEL.to(UPPER_CAMEL, hintName) + COMPLEX_TYPE_SURFIX);
		ltype.setDocumentation(comments);
		String typeArgumentSignature = Signature.getTypeArguments(typeSignature)[0];
		String qualifiedTypeArgumentName = getQualifiedNameBySignature(declaringType, typeArgumentSignature);
		if(Containers.containsElement(SIMPLE_TYPES.keySet(), qualifiedTypeArgumentName)) {
			ltype.setItemType(SIMPLE_TYPES.get(qualifiedTypeArgumentName));
			ltype.setItemName(hintName + "Item");//TODO
			ltype.setBuiltin(true);
		} else {
			SchemaType schemaType = defineSchemaType(declaringType, typeArgumentSignature, null, "");//argument name?? FIXME
			
			ltype.setItemType(schemaType.getName());
			ltype.setItemName(UPPER_CAMEL.to(LOWER_CAMEL, schemaType.getName().split("_")[0]));
		}
		
		
		LTYPES.add(ltype);
		
		return ltype;
	}
	
	/**
	 * 递归反射JavaBean，并逐个定义SchemaType
	 * 
	 * @param type
	 * @return
	 * @throws JavaModelException
	 */
	private SchemaComplexType defineSchemaComplexType(IType declaringType, String typeSignature) throws JavaModelException {
		IType type = findTypeBySignature(declaringType, typeSignature);
		
		SchemaComplexType ctype = new SchemaComplexType();
		ctype.setName(type.getElementName() + COMPLEX_TYPE_SURFIX);
		ctype.setDocumentation(getJavadocComments(type));
		
		for(IField field : type.getFields()) {
			String fieldName = field.getElementName();
			String fieldTypeSignature = field.getTypeSignature();
			
			SchemaType schemaType = defineSchemaType(type, fieldTypeSignature, fieldName, getJavadocComments(field));
			
			ctype.addElement(new SchemaElement(fieldName, schemaType.getName()));
		}
		
		CTYPES.add(ctype);
		
		return ctype;
	}
		
	private SchemaType defineSchemaType(IType declaringType, String typeSignature, String hintName, String comments) throws JavaModelException {
		String qualifiedTypeName = getQualifiedNameBySignature(declaringType, typeSignature);
		
		SchemaType t = null;
		
		if(Containers.containsElement(SIMPLE_TYPES.keySet(), qualifiedTypeName)) { 
			t = defineSchemaSimpleType(declaringType, typeSignature, hintName, comments);
		} else if(Containers.containsElement(LIST_TYPES, qualifiedTypeName)) {
			t = defineSchemaListType(declaringType, typeSignature, hintName, comments);
		} else {
			t = defineSchemaComplexType(declaringType, typeSignature);
		}
		
		return t;
	}
	
	private static Javadoc getJavadoc(IMember member) throws JavaModelException {
		ASTParser parser = ASTParser.newParser(AST.JLS3);
		parser.setSource(member.getCompilationUnit());
		CompilationUnit cu = (CompilationUnit) parser.createAST(null);
		
		ISourceRange sourceRange = member.getSourceRange();

		BodyDeclaration bodyDeclaration = (BodyDeclaration) NodeFinder.perform(cu, sourceRange.getOffset(), sourceRange.getLength());
		return bodyDeclaration.getJavadoc();
	}
	
	private static String getJavadocComments(IMember member) throws JavaModelException {
		Javadoc javadoc = getJavadoc(member);
		if(javadoc != null) {
			List<TagElement> tags = javadoc.tags();
			if(tags.size() > 0) {
				TagElement tagElement = tags.get(0);
				if(tagElement.getTagName() == null) {
					List fragments = tagElement.fragments();
					
					return Joiner.on(" ").join(fragments);
				}
			}
		}
		
		return "";
	}
	
	/**
	 * 
	 * @param method
	 * @return 
	 * @throws JavaModelException
	 */
	private static String getReturnTag(IMethod method) throws JavaModelException {
		String returnName = method.getElementName() + "Return";
		
		Javadoc javadoc = getJavadoc(method);
		if(javadoc != null) {
			List<TagElement> tags = javadoc.tags();
			for(TagElement tag: tags) {
				if("@return".equals(tag.getTagName())) {
					List fragments = tag.fragments();
					if(fragments != null && fragments.size() > 0) {
						
						returnName = ((TextElement)fragments.get(0)).getText().trim();
					}
				}
			}
		}
	
		return returnName;
	}
	
	private static String getReturnComments(IMethod method) throws JavaModelException {
		String returnComments = "";
		
		Javadoc javadoc = getJavadoc(method);
		if(javadoc != null) {
			List<TagElement> tags = javadoc.tags();
			for(TagElement tag: tags) {
				if("@return".equals(tag.getTagName())) {
					List fragments = tag.fragments();
					if(fragments != null && fragments.size() > 1) {
						
						returnComments = Joiner.on(" ").join(fragments.subList(1, fragments.size()));
					}
				}
			}
		}
	
		return returnComments;
	}
	
	private static String getParamTag(IMethod method) throws JavaModelException {
		String paramName = "";
		
		Javadoc javadoc = getJavadoc(method);
		if(javadoc != null) {
			List<TagElement> tags = javadoc.tags();
			for(TagElement tag: tags) {
				if("@param".equals(tag.getTagName())) {
					List fragments = tag.fragments();
					if(fragments != null && fragments.size() > 0 ) {
						
						paramName = ((Name)fragments.get(0)).toString().trim();
					}
				}
			}
		}
	
		return paramName;
	}
	
	private static String getParamComments(IMethod method) throws JavaModelException {
		String paramComments = "";
		
		Javadoc javadoc = getJavadoc(method);
		if(javadoc != null) {
			List<TagElement> tags = javadoc.tags();
			for(TagElement tag: tags) {
				if("@param".equals(tag.getTagName())) {
					List fragments = tag.fragments();
					if(fragments != null && fragments.size() > 1 ) {
						
						paramComments = Joiner.on(" ").join(fragments.subList(1, fragments.size()));
					}
				}
			}
		}
	
		return paramComments;
	}

	public void run() throws Exception {
		ICompilationUnit compilationUnit = JavaCore.createCompilationUnitFrom(file);
		
		String pathRoot = compilationUnit.getJavaProject().getUnderlyingResource().getLocation().toOSString() + "/src/";
		
		Set<WsdlMessage> messages = new HashSet<WsdlMessage>();
		
		WsdlPortType portType = new WsdlPortType();
		portType.setName(serviceName + WSDL_PORTTYPE_SURFIX);
		
		WsdlBinding binding = new WsdlBinding();
		binding.setName(serviceName + WSDL_BINDING_SURFIX);
		binding.setPortType(portType.getName());
		
		for(IMethod method : methods) {
			String[] paramNames = method.getParameterNames();
			String[] paramTypeSignatures = method.getParameterTypes();
			
			//For Parame and RequestBean
			SchemaComplexType reqType = new SchemaComplexType();
			reqType.setName(LOWER_CAMEL.to(UPPER_CAMEL, method.getElementName()) + REQ_TYPE_SURFIX + COMPLEX_TYPE_SURFIX);
			reqType.setDocumentation("");
			for(int i = 0; i< method.getNumberOfParameters(); ++i ){
				String paramTypeSignature = paramTypeSignatures[i];
				String paramName = paramNames[i];
				
				SchemaType paramSchemaType = defineSchemaType(method.getDeclaringType(), paramTypeSignature, paramName, getParamComments(method));
	
				reqType.addElement(new SchemaElement(paramName, paramSchemaType.getName()));
			}
			
			REQTYPES.add(reqType);

			//For Response
			SchemaComplexType rspType = new SchemaComplexType();
			rspType.setName(LOWER_CAMEL.to(UPPER_CAMEL, method.getElementName()) + RSP_TYPE_SURFIX + COMPLEX_TYPE_SURFIX);
			rspType.setDocumentation("");
			String returnTypeSignature = method.getReturnType();
			if(! "void".equals(Signature.toString(returnTypeSignature))) {
				SchemaType schemaType = defineSchemaType(method.getDeclaringType(), returnTypeSignature, getReturnTag(method), getReturnComments(method));
				rspType.addElement(new SchemaElement(schemaType.getName()));
			}	
			
			RSPTYPES.add(rspType);
			
			
			SchemaElement reqElement = new SchemaElement(method.getElementName() + REQ_TYPE_SURFIX, reqType.getName());
			SchemaElement rspElement = new SchemaElement(method.getElementName() + RSP_TYPE_SURFIX, rspType.getName());
			ELEMENTS.add(reqElement);
			ELEMENTS.add(rspElement);
			
			/*----------------------------WSDL---------------------------*/
			WsdlMessage reqMessage = new WsdlMessage();
			reqMessage.setName(reqElement.getName() + WSDL_MESSAGE_SURFIX);
			reqMessage.setPartName(reqElement.getName());
			reqMessage.setElement(reqElement.getName());
			
			WsdlMessage rspMessage = new WsdlMessage();
			rspMessage.setName(rspElement.getName() + WSDL_MESSAGE_SURFIX);
			rspMessage.setPartName(rspElement.getName());
			rspMessage.setElement(rspElement.getName());
			
			messages.add(reqMessage);
			messages.add(rspMessage);
			
			WsdlOperation operation = new WsdlOperation();
			operation.setName(method.getElementName());
			operation.setDocumentation("");
			operation.setInputMessage(reqMessage.getName());
			operation.setOutputMessage(rspMessage.getName());
			portType.addOperation(operation);
			
			WsdlBindingOperation bindingOperation = new WsdlBindingOperation();
			bindingOperation.setName(method.getElementName());
			bindingOperation.setSoapAction(wsdlNsURI + "/" + method.getElementName());
			binding.addBindingOperation(bindingOperation);
		}

		VelocityEngine ve = new VelocityEngine(); 
		ve.setProperty(Velocity.RESOURCE_LOADER, "class"); 
		ve.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader"); 
		ve.init();


		//取得velocity的上下文context 
		VelocityContext context = new VelocityContext(); 
		
		/***********For Type XSD*****************/
		//把数据填入上下文 
		context.put("typeNsURI", typeNsURI); 
		context.put("stypes", STYPES);
		context.put("ltypes", LTYPES);
		context.put("ctypes", CTYPES);
		context.put("reqtypes", REQTYPES);
		context.put("rsptypes", RSPTYPES);
		
		if(mixed) {
			context.put("typeNs", "tns");
			context.put("elements", ELEMENTS);
		}


		//转换输出 
		File f = new File(pathRoot + typeXsdFile);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f),"UTF-8"));
		//取得velocity的模版 
		Template tpl = ve.getTemplate(TYPE_XSD_TEMPLATE); 
		tpl.merge(context, bw); 
		bw.close();
		
		if(!mixed) {
		
			/***********For Element XSD*****************/
			//把数据填入上下文 
			context.put("elemNsURI", elemNsURI); 
			context.put("typeNsURI", typeNsURI); 
			context.put("typeNs", typeNs);
			context.put("typeXsdFile", typeXsdFile);
			context.put("elements", ELEMENTS);
	
	
			//转换输出 
			f = new File(pathRoot + elemXsdFile);
			bw = new BufferedWriter(new FileWriter(f));
			//取得velocity的模版 
			tpl = ve.getTemplate(ELEM_XSD_TEMPLATE); 
			tpl.merge(context, bw); 
			bw.close();
		
		}
		
		/***********For WSDL*****************/
		//把数据填入上下文 
		if(mixed) {
			context.put("elemNsURI", typeNsURI); 
			context.put("elemNs", typeNs);
			context.put("elemXsdFile", typeXsdFile);
		} else {
			context.put("elemNsURI", elemNsURI); 
			context.put("elemNs", elemNs);
			context.put("elemXsdFile", elemXsdFile);
		}
		context.put("wsdlNsURI", wsdlNsURI);
		context.put("serviceName", serviceName);
		context.put("messages", messages);
		context.put("portType", portType);
		context.put("binding", binding);


		//转换输出 
		f = new File(pathRoot + wsdlFile);
		bw = new BufferedWriter(new FileWriter(f));
		//取得velocity的模版 
		tpl = ve.getTemplate(WSDL_TEMPLATE); 
		tpl.merge(context, bw); 
		bw.close();
		
		/***********For headerOfSOAP*****************/
		//转换输出 
		f = new File(pathRoot + "headerOfSOAP.xsd");
		bw = new BufferedWriter(new FileWriter(f));
		//取得velocity的模版 
		tpl = ve.getTemplate(HEADER_TEMPLATE); 
		tpl.merge(context, bw); 
		bw.close();
	}
	
	
}
