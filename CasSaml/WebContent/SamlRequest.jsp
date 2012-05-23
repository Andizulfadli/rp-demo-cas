<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="css/common.css">
<title>SAML 1.1 Request</title>
</head>
<body>
<form method='post' action='SamlRequest'>

<table align=center style="margin-top:100px;">
	<tbody>
	<tr>
		<td colspan=2>
			<p>You can try to send the SAML 1.1 request now.</p>
		</td>
	</tr>
	<tr>
		<td colspan=2>
			&nbsp;
		</td>
	</tr>
	<tr>
		<td>
			Please input ticket  &nbsp;&nbsp;
		</td>
		<td>
			<input value='' size=40 name='ticket' type=text>
		</td>
	</tr>
	<tr>
		<td colspan=2>
		</td>
	</tr>
	<tr>
		<td>	
			<input type=submit value='Send Request...'>
		</td>
	</tr>
	</tbody>
</table>	
	
	
</form>


</body>
</html>