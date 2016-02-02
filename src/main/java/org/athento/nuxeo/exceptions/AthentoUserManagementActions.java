/**
 * 
 */
package org.athento.nuxeo.exceptions;

import org.nuxeo.ecm.core.api.ClientException;
import org.nuxeo.ecm.webapp.security.UserManagementActions;
import org.jboss.seam.annotations.Install;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.StatusMessage;

/**
 * @author athento
 *
 */
@Name("userManagementActions")
@Scope(org.jboss.seam.ScopeType.CONVERSATION)
@Install(precedence = org.jboss.seam.annotations.Install.DEPLOYMENT)
public class AthentoUserManagementActions extends UserManagementActions {
	
	public void createUser(boolean createAnotherUser) throws ClientException {
		try {
			super.createUser(createAnotherUser);
		} catch (UserInfoValidationException e) {
			facesMessages.add(StatusMessage.Severity.ERROR,
				resourcesAccessor.getMessages().get("error.userManager.validationException"));
		}
	}
}
