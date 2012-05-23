<%@page import="org.jasig.cas.support.oauth.provider.impl.CasWrapperProvider20"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>OAuth Login</title>
</head>
<body>
<br />	
<%
	org.springframework.context.ApplicationContext applicationContext  = org.springframework.web.context.support.WebApplicationContextUtils.getWebApplicationContext(application);
	try { 
		CasWrapperProvider20 facebookProvider = (CasWrapperProvider20)applicationContext.getBean("caswrapper1"); %>
		<a href="<%=facebookProvider.getAuthorizationUrl(null)%>">Authenticate with CAS Wrapper</a>
<%	} catch (Exception e) { e.printStackTrace(); }
%>	
	

<!-- 
<%
	String oauth_url = "http://localhost:8080/CasAuthn/oauth2.0/authorize?";
	oauth_url += "&"+org.jasig.cas.support.oauth.OAuthConstants.CLIENT_ID+"=the_key_for_caswrapper1";
	oauth_url += "&"+org.jasig.cas.support.oauth.OAuthConstants.CLIENT_SECRET+"=the_secret_for_caswrapper1";
	oauth_url += "&"+org.jasig.cas.support.oauth.OAuthConstants.REDIRECT_URI+"="+java.net.URLEncoder.encode("http://localhost:8080/CasServer/login");

%>
<a href="<%=oauth_url%>">
Authenticate with another CAS server using OAuth v2.0 protocol wrapper</a><br />
-->
<br />
</body>
</html>