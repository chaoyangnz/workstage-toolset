package org.inframesh.workstage.toolset.xml.validator;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.wst.validation.internal.ConfigurationManager;
import org.eclipse.wst.validation.internal.ProjectConfiguration;
import org.eclipse.wst.validation.internal.ValidationRegistryReader;
import org.eclipse.wst.validation.internal.ValidatorMetaData;
import org.eclipse.wst.validation.internal.core.ValidationException;
import org.eclipse.wst.validation.internal.provisional.ValidationFactory;
import org.eclipse.wst.validation.internal.provisional.core.IProjectValidationContext;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;
import org.eclipse.wst.validation.internal.provisional.core.IValidationContext;
import org.eclipse.wst.validation.internal.provisional.core.IValidator;
import org.eclipse.wst.validation.internal.provisional.core.IValidatorJob;

public class DelegatingXMLValidator implements IValidator {

	public DelegatingXMLValidator() {
		super(); // constructor
	}

	public void cleanup(IReporter arg0) { // don't need to implement
	}

	protected IValidator getDelegateValidator() {
		IValidator result = null;
		try {
			result = ValidationFactory.instance
					.getValidator(Constants.VALIDATOR_CLASS);
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
		return result;
	}

	public void validate(IValidationContext helper, IReporter reporter) {
		String[] delta = helper.getURIs();
		if (delta.length > 0) {
			IFile file = XMLHelper.getFile(delta[0]);
			try {
				if (isDelegateValidatorEnabled(file)) {
					IValidator validator = getDelegateValidator();
					if (validator != null) {
						IValidationContext vHelper = new MyHelper(file);

						if (validator instanceof IValidatorJob) {
							((IValidatorJob) validator).validateInJob(vHelper,
									reporter);
						} else {
							validator.validate(vHelper, reporter);
						}
					}
				}
			} catch (ValidationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	protected boolean isDelegateValidatorEnabled(IFile file) {
		boolean enabled = true;
		try {

			ProjectConfiguration configuration = ConfigurationManager
					.getManager().getProjectConfiguration(file.getProject());
			ValidatorMetaData vmd = ValidationRegistryReader.getReader()
					.getValidatorMetaData(Constants.VALIDATOR_CLASS);
			if (configuration.isBuildEnabled(vmd)
					|| configuration.isManualEnabled(vmd)) {
				enabled = true;
			} else {
				enabled = false;
			}
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return enabled;
	}

	class MyHelper implements IProjectValidationContext {
		IFile file;

		public MyHelper(IFile file) {
			this.file = file;
		}

		public Object loadModel(String symbolicName, Object[] parms) {
			if (symbolicName.equals(Constants.GET_FILE)) {
				return file;
			}
			return null;
		}

		public String[] getURIs() {
			if (file != null) {

				String uri = XMLHelper.createURIForFile(file);
				return new String[] { uri };
			}
			return new String[0];
		}

		public IProject getProject() {
			if (file != null) {
				return file.getProject();
			}
			return null;
		}

		public Object loadModel(String symbolicName) {
			if (symbolicName.equals(Constants.GET_FILE)) {
				return file;
			}
			return null;
		}

	}
}
