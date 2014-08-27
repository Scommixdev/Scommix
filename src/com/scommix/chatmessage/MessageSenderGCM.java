package com.scommix.chatmessage;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.util.Log;

import com.scommix.sharedpref.ScommixSharedPref;

public class MessageSenderGCM {


    // calling android push method                                                                               
                                                                                                                                                     
                                                                                                                                                       
//Android push message to GCM server method                                                                                       
//Spublic static String AndroidPush(String message)                                                                                                                      
//    {                                                                                                                                                   
//// your RegistrationID paste here which is received from GCM server.                                                               
//   String regId = ScommixSharedPref.getdevicetoken();                                                                                              
//// applicationID means google Api key                                                                                                     
//   String applicationID = "AIzaSyB5qJUp_uTe7Z6IIeDuDCEVgJcL7MS9L5k";                                                         
////SENDER_ID is nothing but your ProjectID (from API Console- google code)//                                          
//   String SENDER_ID = GCMglobals.GCM_SENDER_ID;                                                                                            
//
//       HttpClient httpclient=new DefaultHttpClient();
//       HttpPost httpost=new HttpPost("https://android.googleapis.com/gcm/send");
//
//       List<NameValuePair> formparams = new ArrayList<NameValuePair>();
//
//       formparams.add(new BasicNameValuePair("registration_id", regId));
//       formparams.add(new BasicNameValuePair("data.message", message));
//       // UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams,
//       // "UTF-8");
//
//      
//       HttpResponse response = null;
//
//       httpost.setHeader("Authorization",
//               "key="+applicationID);
//       httpost.setHeader("Content-Type",
//               "application/x-www-form-urlencoded;charset=UTF-8");
//
//       try {
//    	   httpost.setEntity(new UrlEncodedFormEntity(formparams, "utf-8"));
//          
//
//           //Get the response  
//           response = httpclient.execute(httpost);
//
//           int responseCode = response.getStatusLine().getStatusCode();
//            String responseText = Integer.toString(responseCode);      
//            System.out.println("HTTP POST : " + responseText);
//
//            /*Checking response */
//            if(response!=null){
//                InputStream in = response.getEntity().getContent(); //Get the data in the entity
//                System.out.println("HTTP POST : " + in.toString());
//            }
//           //Print result
//           System.out.println(response.toString());
//
//       } catch (UnsupportedEncodingException e) {
//           // TODO Auto-generated catch block
//           e.printStackTrace();
//       } catch (ClientProtocolException e) {
//           // TODO Auto-generated catch block
//           e.printStackTrace();
//       } catch (IOException e) {
//           // TODO Auto-generated catch block
//           e.printStackTrace();
//       }
//	return response.toString();                                                                                                              
//           }      

public static String getResponseByXML(String request) {  

	final DefaultHttpClient httpClient=new DefaultHttpClient();
// request parameters
HttpParams params = httpClient.getParams();
   HttpConnectionParams.setConnectionTimeout(params, 10000);
   HttpConnectionParams.setSoTimeout(params, 15000);
   // set parameter
HttpProtocolParams.setUseExpectContinue(httpClient.getParams(), true);

// POST the envelope
HttpPost httppost = new HttpPost("http://scommix.cloudapp.net/webservices/common.asmx");
// add headers
   httppost.setHeader("soapaction", "http://tempuri.org/SendMessage");
   httppost.setHeader("Content-Type", "text/xml; charset=utf-8");
   
   String responseString="";
   try {
    
    // the entity holds the request
 HttpEntity entity = new StringEntity(request); 
 httppost.setEntity(entity);
 
 // Response handler
 ResponseHandler rh=new ResponseHandler() {
  // invoked when client receives response
  public String handleResponse(HttpResponse response)
    throws ClientProtocolException, IOException {
   
   // get response entity
   HttpEntity entity = response.getEntity();
   
   // read the response as byte array
         StringBuffer out = new StringBuffer();
         byte[] b = EntityUtils.toByteArray(entity);
         
         // write the response byte array to a string buffer
         out.append(new String(b, 0, b.length));        
         return out.toString();
  }
 };
 
 responseString=httpClient.execute(httppost, rh); 

} 
   catch (Exception e) {
    Log.v("exception", e.toString());
}

   // close the connection
httpClient.getConnectionManager().shutdown();
return responseString;
}


}