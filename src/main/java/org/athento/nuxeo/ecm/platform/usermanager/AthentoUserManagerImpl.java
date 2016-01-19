package org.athento.nuxeo.ecm.platform.usermanager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.platform.usermanager.UserManagerImpl;
import org.nuxeo.ecm.platform.usermanager.exceptions.UserAlreadyExistsException;

/**
 * @author athento
 *
 */
public class AthentoUserManagerImpl extends UserManagerImpl {

	private static final long serialVersionUID = 1L;
	public static final String expression = "^[\\p{L} .'-]+$";

	@Override
	public DocumentModel createUser(DocumentModel userModel,
			DocumentModel context) throws UserAlreadyExistsException {
		Object userFirstName = userModel.getProperty(userSchemaName, "firstName");
		Object userLastName = userModel.getProperty(userSchemaName, "lastName");
		checkUserData(userFirstName, AthentoUserManagerImpl.expression);
		checkUserData(userLastName, AthentoUserManagerImpl.expression);
		return super.createUser(userModel, context);
	}

	@Override
	public void updateUser(DocumentModel userModel, DocumentModel context) {
		Object userFirstName = userModel.getProperty(userSchemaName, "firstName");
		Object userLastName = userModel.getProperty(userSchemaName, "lastName");
		checkUserData(userFirstName, AthentoUserManagerImpl.expression);
		checkUserData(userLastName, AthentoUserManagerImpl.expression);
		super.updateUser(userModel, context);
	}

	private void checkUserData(Object value, String expression) {
		if (_log.isDebugEnabled()) {
			_log.debug("Validating text: " + value);
		}
		String theValue = (String) value;
		if (isValid(theValue, expression)) {
			return;
		} else {
			_log.error("Value ["+value+"] not valid for expression ["+expression+"]");
			throw new UserAlreadyExistsException();
		}
	}

	private boolean isValid(String value, String regexp) {
		if (_log.isDebugEnabled()) {
			_log.debug("Validating [" + value + "] against regexp [" + regexp
					+ "]");
		}
		Pattern pattern = Pattern.compile(regexp);
		Matcher matcher = pattern.matcher(value);
		if (matcher.matches()) {
			if (_log.isDebugEnabled()) {
				_log.debug("Match successful for value: " + value);
			}
			return true;
		} else {
			if (_log.isDebugEnabled()) {
				_log.debug("Value [" + value + "] do not match regular expression: " + regexp);
			}
			return false;
		}
	}

	private static Log _log = LogFactory.getLog(AthentoUserManagerImpl.class);

}
