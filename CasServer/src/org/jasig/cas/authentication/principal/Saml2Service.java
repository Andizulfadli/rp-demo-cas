package org.jasig.cas.authentication.principal;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jasig.cas.util.HttpClient;
import org.springframework.util.StringUtils;

public final class Saml2Service extends AbstractWebApplicationService {
	
	
	public static void main(String[] args){
		SamlService serv= new SamlService("id", "service", "artifactId", null, "requestId");
		System.out.println("serv "+serv);
	}

    private static final Log log = LogFactory.getLog(SamlService.class);

    /** Constant representing service. */
    private static final String CONST_PARAM_SERVICE = "TARGET";

    /** Constant representing artifact. */
    private static final String CONST_PARAM_TICKET = "SAMLart";

    private static final String CONST_START_ARTIFACT_XML_TAG_NO_NAMESPACE = "<Artifact>";//"<AssertionArtifact>";

    private static final String CONST_END_ARTIFACT_XML_TAG_NO_NAMESPACE = "</Artifact>";//"</AssertionArtifact>";
    
    private static final String CONST_START_ARTIFACT_XML_TAG = "<samlp:Artifact>";//"<samlp:AssertionArtifact>";
    
    private static final String CONST_END_ARTIFACT_XML_TAG = "</samlp:Artifact>";//"</samlp:AssertionArtifact>";

    private String requestId;

    /**
     * Unique Id for serialization.
     */
    private static final long serialVersionUID = -6867572626767140223L;

    protected Saml2Service(final String id) {
        super(id, id, null, new HttpClient());
    }

    protected Saml2Service(final String id, final String originalUrl, final String artifactId, final HttpClient httpClient, final String requestId) {
        super(id, originalUrl, artifactId, httpClient);
        log.debug("requestId "+requestId);
        this.requestId = requestId;
    }

    /**
     * This always returns true because a SAML Service does not receive the TARGET value on validation.
     */
    public boolean matches(final Service service) {
        return true;
    }

    public String getRequestID() {
        return this.requestId;
    }

    public static SamlService createServiceFrom(
        final HttpServletRequest request, final HttpClient httpClient) {
        final String service = request.getParameter(CONST_PARAM_SERVICE);
        final String artifactId;
        final String requestBody = getRequestBody(request);
        final String requestId;
        log.debug("---------------- SAML2 start ------------");
        if (!StringUtils.hasText(service) && !StringUtils.hasText(requestBody)) {
        	log.debug("service is null "+!StringUtils.hasText(service)+" or requestBody is null "+!StringUtils.hasText(requestBody));
            return null;
        }

        final String id = cleanupUrl(service);
        
        if (StringUtils.hasText(requestBody)) {

            final String tagStart;
            final String tagEnd;
            if (requestBody.contains(CONST_START_ARTIFACT_XML_TAG)) {
                tagStart = CONST_START_ARTIFACT_XML_TAG;
                tagEnd = CONST_END_ARTIFACT_XML_TAG;
            } else {
                tagStart = CONST_START_ARTIFACT_XML_TAG_NO_NAMESPACE;
                tagEnd = CONST_END_ARTIFACT_XML_TAG_NO_NAMESPACE;
            }
            final int startTagLocation = requestBody.indexOf(tagStart);
            final int artifactStartLocation = startTagLocation + tagStart.length();
            final int endTagLocation = requestBody.indexOf(tagEnd);

            artifactId = requestBody.substring(artifactStartLocation, endTagLocation).trim();

            // is there a request id?
            requestId = extractRequestId(requestBody);
        } else {
            artifactId = null;
            requestId = null;
        }

        if (log.isDebugEnabled()) {
            log.debug("Attempted to extract Request from HttpServletRequest.  Results:");
            log.debug(String.format("Request Body: %s", requestBody));
            log.debug(String.format("Extracted ArtifactId: %s", artifactId));
            log.debug(String.format("Extracted Request Id: %s", requestId));
        }

        SamlService serv= new SamlService(id, service, artifactId, httpClient, requestId);
       // log.debug("SamlService here "+serv);
        log.debug("---------------- SAML2 end ------------");
        return serv;
    }

    public Response getResponse(final String ticketId) {
        final Map<String, String> parameters = new HashMap<String, String>();

        parameters.put(CONST_PARAM_TICKET, ticketId);
        parameters.put(CONST_PARAM_SERVICE, getOriginalUrl());

        return Response.getRedirectResponse(getOriginalUrl(), parameters);
    }

    protected static String extractRequestId(final String requestBody) {
        if (!requestBody.contains("RequestID")) {
            return null;
        }

        try {
            final int position = requestBody.indexOf("RequestID=\"") + 11;
            final int nextPosition = requestBody.indexOf("\"", position);

            return requestBody.substring(position,  nextPosition);
        } catch (final Exception e) {
            log.debug("Exception parsing RequestID from request." ,e);
            return null;
        }
    }
    
    protected static String getRequestBody(final HttpServletRequest request) {
        final StringBuilder builder = new StringBuilder();
        try {
            final BufferedReader reader = request.getReader();
            
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            return builder.toString();
        } catch (final Exception e) {
           return null;
        }
    }
}