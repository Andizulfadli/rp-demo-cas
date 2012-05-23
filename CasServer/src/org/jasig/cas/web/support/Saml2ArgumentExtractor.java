package org.jasig.cas.web.support;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jasig.cas.authentication.principal.Saml2Service;
import org.jasig.cas.authentication.principal.WebApplicationService;

public class Saml2ArgumentExtractor extends AbstractSingleSignOutEnabledArgumentExtractor {
	private static final Log log = LogFactory.getLog(Saml2ArgumentExtractor.class);
    public WebApplicationService extractServiceInternal(final HttpServletRequest request) {
    	log.debug("Saml2ArgumentExtractor");
    	return Saml2Service.createServiceFrom(request, getHttpClientIfSingleSignOutEnabled());
    }
}
