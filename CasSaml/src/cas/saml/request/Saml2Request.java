package cas.saml.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentProducer;
import org.apache.http.entity.EntityTemplate;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * Servlet implementation class Saml2Request
 */
@WebServlet("/Saml2Request")
public class Saml2Request extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Saml2Request() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpClient httpclient = new DefaultHttpClient();
		String target = "http://localhost:8080/CasSaml/Application.jsp";
		String ticket = request.getParameter("ticket");
		HttpPost httpreq = new HttpPost("http://localhost:8080/CasServer/saml2Validate?"
				+"TARGET="+target
				+"&ticket="+ticket
				);
		httpreq.setHeader("Content-Type",
				"text/xml");
		final String body = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\">  <SOAP-ENV:Header/>  <SOAP-ENV:Body>"
				+"<samlp:ArtifactResolve xmlns:samlp=\"urn:oasis:names:tc:SAML:2.0:protocol\" xmlns:saml=\"urn:oasis:names:tc:SAML:2.0:assertion\""
			    +"ID=\"identifier_4\""
			    +"Version=\"2.0\""
			    +"IssueInstant=\"2004-12-05T09:22:04Z\""
			    +"Destination=\"https://idp.example.org/SAML2/ArtifactResolution\">"
			    +"<saml:Issuer>http://hp.com</saml:Issuer>"
			    +"<ds:Signature"
			    +"  xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\">...</ds:Signature>"
			    +"<samlp:Artifact>"+ticket+"</samlp:Artifact>"
			    +"</samlp:ArtifactResolve>"
				+"</SOAP-ENV:Body></SOAP-ENV:Envelope>";


		ContentProducer cp = new ContentProducer() {  
		          
		        public void writeTo(OutputStream outstream) throws IOException {  
		            Writer writer = new OutputStreamWriter(outstream, "UTF-8");  
		            writer.write(body);  
		            writer.flush();  
		       }  
		        };  
		       HttpEntity entity = new EntityTemplate(cp);
		httpreq.setEntity(entity);
		try{
			HttpResponse resp = httpclient.execute(httpreq);
			BufferedReader br = null;
			br = new BufferedReader(new InputStreamReader(resp.getEntity().getContent()));
	        String readLine ;
	        String result  = "";
	        while(((readLine = br.readLine()) != null)) {
	          //System.err.println(readLine);
	        	result+=readLine;
	        }
			response.getOutputStream().write(result.getBytes());
		} catch (Exception e) {
		    System.err.println(e);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
