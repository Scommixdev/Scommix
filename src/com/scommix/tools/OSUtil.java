package com.scommix.tools;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class OSUtil

{
	
	
    public static boolean IsNetworkAvailable(Context context)
    {
        boolean result = false;
        if (context!=null)
        {
            ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo[] networkInfos =   connectivityManager.getAllNetworkInfo();
            for (NetworkInfo networkInfo:networkInfos)
            {
                if(networkInfo.isConnected())
                {
                    result =true;
                    break;
                }
            }
        }
        return  result;
    }
}
