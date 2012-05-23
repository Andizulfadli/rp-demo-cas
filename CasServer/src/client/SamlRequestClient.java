package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentProducer;
import org.apache.http.entity.EntityTemplate;
import org.apache.http.impl.client.DefaultHttpClient;

public class SamlRequestClient {
	public static void main(String args[]) {
		String result = null;
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httpreq = new HttpPost("http://localhost:8080/CasServer/samlValidate");
		httpreq.setHeader("Content-Type",
				"text/xml");
		final String body = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\">  <SOAP-ENV:Header/>  <SOAP-ENV:Body>    <samlp:Request xmlns:samlp=\"urn:oasis:names:tc:SAML:1.0:protocol\" MajorVersion=\"1\"      MinorVersion=\"1\" RequestID=\"_192.168.16.51.1024506224022\"      IssueInstant=\"2002-06-19T17:03:44.022Z\">      <samlp:AssertionArtifact>        ST-1-u4hrm3td92cLxpCvrjylcas.example.com      </samlp:AssertionArtifact>    </samlp:Request>  </SOAP-ENV:Body></SOAP-ENV:Envelope>";
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
	        String readLine;
	        while(((readLine = br.readLine()) != null)) {
	          System.err.println(readLine);
	      }
		} catch (Exception e) {
		    System.err.println(e);
		}
	    /*HttpClient client = new DefaultHttpClient();
	    client.getParams().setParameter("http.useragent", "Test Client");

	    BufferedReader br = null;

	    PostMethod method = new PostMethod("http://search.yahoo.com/search");
	    method.addParameter("p", "\"java2s\"");

	    try{
	      int returnCode = client.executeMethod(method);

	      if(returnCode == HttpStatus.SC_NOT_IMPLEMENTED) {
	        System.err.println("The Post method is not implemented by this URI");
	        // still consume the response body
	        method.getResponseBodyAsString();
	      } else {
	        br = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream()));
	        String readLine;
	        while(((readLine = br.readLine()) != null)) {
	          System.err.println(readLine);
	      }
	      }
	    } catch (Exception e) {
	      System.err.println(e);
	    } finally {
	      method.releaseConnection();
	      if(br != null) try { br.close(); } catch (Exception fe) {}
	    }*/

	  }
}
