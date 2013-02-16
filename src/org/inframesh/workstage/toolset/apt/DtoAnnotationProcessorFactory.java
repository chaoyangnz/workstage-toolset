package org.inframesh.workstage.toolset.apt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import org.eclipse.jdt.apt.core.env.EclipseAnnotationProcessorEnvironment;
import org.eclipse.jdt.apt.core.env.EclipseAnnotationProcessorFactory;
import org.inframesh.workstage.toolset.ui.console.Console;

import com.sun.mirror.apt.AnnotationProcessor;
import com.sun.mirror.apt.AnnotationProcessorEnvironment;
import com.sun.mirror.apt.AnnotationProcessors;
import com.sun.mirror.declaration.AnnotationTypeDeclaration;

public class DtoAnnotationProcessorFactory implements EclipseAnnotationProcessorFactory {

	private static ArrayList<String> supportedAnnotations = new ArrayList<String>();
	
	static {
		supportedAnnotations.add( "com.bankcomm.icms.comm.utils.annotation.Table" );
		supportedAnnotations.add( "com.bankcomm.icms.comm.utils.annotation.Column" );
	}
	
	public AnnotationProcessor getProcessorFor(Set<AnnotationTypeDeclaration> declarations,	EclipseAnnotationProcessorEnvironment env) {
		Console.out.println("hello, Annotation");
		AnnotationProcessor result;
		if(declarations.isEmpty()) {
			result = AnnotationProcessors.NO_OP;
		} else {
			result = new DtoAnnotationProcessor(env);
		}
		return result;
	}
	
	public AnnotationProcessor getProcessorFor(Set<AnnotationTypeDeclaration> atds,	AnnotationProcessorEnvironment env) {
		AnnotationProcessor result;
		if(atds.isEmpty()) {
			result = AnnotationProcessors.NO_OP;
		} else {
			result = new DtoAnnotationProcessor(env);
		}
		return result;
	}


	public Collection<String> supportedAnnotationTypes() {
		
		return supportedAnnotations;
	}

	public Collection<String> supportedOptions() {
		
		return Collections.emptySet();
	}

}
