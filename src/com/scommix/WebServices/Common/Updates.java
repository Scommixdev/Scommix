package com.scommix.WebServices.Common;

//------------------------------------------------------------------------------
// <wsdl2code-generated>
//    This code was generated by http://www.wsdl2code.com version  2.5
//
// Date Of Creation: 4/1/2014 2:30:02 PM
//    Please dont change this code, regeneration will override your changes
//</wsdl2code-generated>
//
//------------------------------------------------------------------------------
//
//This source code was auto-generated by Wsdl2Code  Version
//
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class Updates implements Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	
	public String name;
    public String username;
    public String userid;
    public String status;
    public String statustime;
    public String userstatusid;
    public String active;
    public String userpic;
    public String userprofileimage;
    public Bitmap roundedbitmap; 


	public Updates(){}
    
    public Updates(String name,String username,String userid,String status,String statustime,String userstatusid,String active,String userpic,String userprofileimage)
    {
    	this.name=name;
        this.username=username;
         this.userid=userid;
         this.status=status;
         this.statustime=statustime;
         this.userstatusid=userstatusid;
         this.active=active;
         this.userpic=userpic;
         this.userprofileimage=userprofileimage;
    }
    
    
    
    
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatustime() {
		return statustime;
	}

	public void setStatustime(String statustime) {
		this.statustime = statustime;
	}

	public String getUserstatusid() {
		return userstatusid;
	}

	public void setUserstatusid(String userstatusid) {
		this.userstatusid = userstatusid;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getUserpic() {
		return userpic;
	}

	public void setUserpic(String userpic) {
		this.userpic = userpic;
	}

	public String getUserprofileimage() {
		return userprofileimage;
	}

	public void setUserprofileimage(String userprofileimage) {
		this.userprofileimage = userprofileimage;
	}

	public Updates(SoapObject soapObject)
    {
        if (soapObject == null)
            return;
        if (soapObject.hasProperty("name"))
        {
            Object obj = soapObject.getProperty("name");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) obj;
                name = j.toString();
            }else if (obj!= null && obj instanceof String){
                name = (String) obj;
            }
        }
        if (soapObject.hasProperty("username"))
        {
            Object obj = soapObject.getProperty("username");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) obj;
                username = j.toString();
            }else if (obj!= null && obj instanceof String){
                username = (String) obj;
            }
        }
        if (soapObject.hasProperty("userid"))
        {
            Object obj = soapObject.getProperty("userid");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) obj;
                userid = j.toString();
            }else if (obj!= null && obj instanceof String){
                userid = (String) obj;
            }
        }
        if (soapObject.hasProperty("status"))
        {
            Object obj = soapObject.getProperty("status");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) obj;
                status = j.toString();
            }else if (obj!= null && obj instanceof String){
                status = (String) obj;
            }
        }
        
        if (soapObject.hasProperty("statustime"))
        {
            Object obj = soapObject.getProperty("statustime");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) obj;
                statustime = j.toString();
            }else if (obj!= null && obj instanceof String){
                statustime = (String) obj;
            }
        }
        if (soapObject.hasProperty("userstatusid"))
        {
            Object obj = soapObject.getProperty("userstatusid");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) obj;
                userstatusid = j.toString();
            }else if (obj!= null && obj instanceof String){
                userstatusid = (String) obj;
            }
        }
        if (soapObject.hasProperty("active"))
        {
            Object obj = soapObject.getProperty("active");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) obj;
                active = j.toString();
            }else if (obj!= null && obj instanceof String){
                active = (String) obj;
            }
        }
        if (soapObject.hasProperty("userpic"))
        {
            Object obj = soapObject.getProperty("userpic");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) obj;
                userpic = j.toString();
            }else if (obj!= null && obj instanceof String){
                userpic = (String) obj;
            }
        }
        if (soapObject.hasProperty("userprofileimage"))
        {
            Object obj = soapObject.getProperty("userprofileimage");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) obj;
                userprofileimage = j.toString();
            }else if (obj!= null && obj instanceof String){
                userprofileimage = (String) obj;
            }
        }
    }
//    @Override
//    public Object getProperty(int arg0) {
//        switch(arg0){
//            case 0:
//                return name;
//            case 1:
//                return username;
//            case 2:
//                return userid;
//            case 3:
//                return status;
//            case 4:
//                return statustime;
//            case 5:
//                return userstatusid;
//            case 6:
//                return active;
//            case 7:
//                return userpic;
//        }
//        return null;
//    }
    
//    @Override
//    public int getPropertyCount() {
//        return 8;
//    }
    
   // @Override
//    public void getPropertyInfo(int index, @SuppressWarnings("rawtypes") Hashtable arg1, PropertyInfo info) {
//        switch(index){
//            case 0:
//                info.type = PropertyInfo.STRING_CLASS;
//                info.name = "name";
//                break;
//            case 1:
//                info.type = PropertyInfo.STRING_CLASS;
//                info.name = "username";
//                break;
//            case 2:
//                info.type = PropertyInfo.STRING_CLASS;
//                info.name = "userid";
//                break;
//            case 3:
//                info.type = PropertyInfo.STRING_CLASS;
//                info.name = "status";
//                break;
//            case 4:
//                info.type = PropertyInfo.STRING_CLASS;
//                info.name = "statustime";
//                break;
//            case 5:
//                info.type = PropertyInfo.STRING_CLASS;
//                info.name = "userstatusid";
//                break;
//            case 6:
//                info.type = PropertyInfo.STRING_CLASS;
//                info.name = "active";
//                break;
//            case 7:
//                info.type = PropertyInfo.STRING_CLASS;
//                info.name = "userpic";
//                break;
//        }
//    }
    
//    @Override
//    public void setProperty(int arg0, Object arg1) {
//    }

	

	
	

		
    
}
