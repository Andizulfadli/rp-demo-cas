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
 * Servlet implementation class SamlRequest
 */
@WebServlet("/SamlRequest")
public class SamlRequest extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public SamlRequest() {
        // TODO Auto-generated constructor stub
    }
//http://sysbible.org/2009/03/06/how-to-test-cas-saml-using-soapui/
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpClient httpclient = new DefaultHttpClient();
		String target = "http://localhost:8080/CasSaml/Application.jsp";
		String ticket = request.getParameter("ticket");
		HttpPost httpreq = new HttpPost("http://localhost:8080/CasServer/samlValidate?"
				+"TARGET="+target
				+"&ticket="+ticket
				);
		httpreq.setHeader("Content-Type",
				"text/xml");
		final String body = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\">  <SOAP-ENV:Header/>  <SOAP-ENV:Body>    <samlp:Request xmlns:samlp=\"urn:oasis:names:tc:SAML:1.0:protocol\" MajorVersion=\"1\"      MinorVersion=\"1\" RequestID=\"_192.168.16.51.1024506224022\"      IssueInstant=\"2002-06-19T17:03:44.022Z\">      <samlp:AssertionArtifact>        "+ticket+"      </samlp:AssertionArtifact>    </samlp:Request>  </SOAP-ENV:Body></SOAP-ENV:Envelope>";
		// HttpParams params = new HttpParams();
		// httpreq.setParams(params);
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
