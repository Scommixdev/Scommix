package com.scommix.sharedpref;

import android.content.Context;
import android.content.SharedPreferences;


public class ScommixSharedPref {
	
	static SharedPreferences pref;
	static ScommixSharedPref scomixpref;
	public static String USERNAME = "one";
	public static String EMAILID = "emailid";
	public static String PASSWORD = "two";
    public static String ISREGISTERED="isRegistered";
	public static String USERID="id";
	public static String USERTYPE="typ";
	public static String DEVICETOKEN="devicetoken";
	public static String FirstRun ="FirstRun";
	public static String APPVERSION="appversion";
	public static String INSTITUTEID="insid";

	public static String PROFILEPICNAME="profilepicname";
	public static String COVERPICNAME="coverpicname";
	public static String CLASSID="clsid";
	public static String SVIMEDUID="svmid";
	public static String INSTITUTEBACKGROUND="intituebackground";

	
	
	
	
	
	
	
	

	public static String getINSTITUTEBACKGROUND() {
		return pref.getString(INSTITUTEBACKGROUND, "");
	}

	public static void setINSTITUTEBACKGROUND(String iNSTITUTEBACKGROUND) {
		pref.edit().putString(INSTITUTEBACKGROUND,iNSTITUTEBACKGROUND).commit();
	}

	public static String getSVIMEDUID() {
		return pref.getString(SVIMEDUID, "");
	}

	public static void setSVIMEDUID(String sVIMEDUID) {
		pref.edit().putString(SVIMEDUID,sVIMEDUID).commit();
	}

	public static String getCLASSID() {
		return pref.getString(CLASSID, "");
	}

	public static void setCLASSID(String cLASSID) {
		pref.edit().putString(CLASSID, cLASSID).commit();
	}
	public static String getINSTITUTEID() {
		return pref.getString(INSTITUTEID, "");
	}

	public static void setINSTITUTEID(String iNSTITUTEID) {
		pref.edit().putString(INSTITUTEID, iNSTITUTEID).commit();
	}

	

	public static String getPROFILEPICNAME() {
		return pref.getString(PROFILEPICNAME, "");
	}

	public static void setPROFILEPICNAME(String pROFILEPICNAME) {
		pref.edit().putString(PROFILEPICNAME, pROFILEPICNAME).commit();
	}

	public static String getCOVERPICNAME() {
		return pref.getString(COVERPICNAME, "");
	}

	public static void setCOVERPICNAME(String cOVERPICNAME) {
		pref.edit().putString(COVERPICNAME, cOVERPICNAME).commit();

	}

	public static String getAPPVERSION() {
		return pref.getString(APPVERSION, "");
	}

	public static void setAPPVERSION(String aPPVERSION) {
		pref.edit().putString(APPVERSION, aPPVERSION).commit();
	}

	
	public static  void setdevicetoken(String devicetoken) {
		pref.edit().putString(DEVICETOKEN, devicetoken).commit();
	}

	public static String getdevicetoken() {
		return pref.getString(DEVICETOKEN, "");

	}
	
	public static String getEMAILID() {
    	return pref.getString(EMAILID,"");
	}

	public static void setEMAILID(String eMAILID) {
		pref.edit().putString(EMAILID, eMAILID).commit();
	}

	public static String getUSERID() {
		return pref.getString(USERID,"");
	}

	public static void setUSERID(String uSERID) {
		pref.edit().putString(USERID, uSERID).commit();
	}

	public static String getUSERTYPE() {
		return pref.getString(USERTYPE,"");
	}

	public static void setUSERTYPE(String uSERTYPE) {
		pref.edit().putString(USERTYPE, uSERTYPE).commit();
	}

    
    public static ScommixSharedPref getInstance(Context context) {
		if (scomixpref == null) {
			scomixpref = new ScommixSharedPref(context);
		}
		return scomixpref;
	}

	private ScommixSharedPref(Context context) {
		pref = context.getSharedPreferences("scommixpreferences",
				Context.MODE_PRIVATE);
	}
	
	

	

	public static String getUSERNAME() {
		return pref.getString(USERNAME,"");
	}

	public static void setUSERNAME(String uSERNAME) {
		pref.edit().putString(USERNAME, uSERNAME).commit();
	}

	public static String getPASSWORD() {
		return pref.getString(PASSWORD, "");
	}

	public static void setPASSWORD(String pASSWORD) {
		pref.edit().putString(PASSWORD, pASSWORD).commit();
	}

	public static void setisRegistered( boolean devicetoken) {
		pref.edit().putBoolean(ISREGISTERED, devicetoken).commit();
	}

	public static  boolean getisRegistered() {
		return pref.getBoolean(ISREGISTERED, false);
	}





	public static void setFirstRun( boolean devicetoken) {
		pref.edit().putBoolean(FirstRun, devicetoken).commit();
	}

	public static  boolean getFirstRun() {
		return pref.getBoolean(FirstRun, true);
	}
	

}
