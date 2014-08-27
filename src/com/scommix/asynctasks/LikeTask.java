package com.scommix.asynctasks;











import com.scommix.WebServices.Common.Common;
import com.scommix.WebServices.Common.online;
import com.scommix.homeandprofile.MyProfileFragment;
import com.scommix.sharedpref.ScommixSharedPref;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

public class LikeTask extends AsyncTask<String, String, String> {

	
	FragmentActivity ctx;
	
	ProgressDialog pd;
	String string;
	public LikeTask(FragmentActivity profile, String string) {
		// TODO Auto-generated constructor stub
             this.ctx=profile;	
            this.string=string;
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		pd=new ProgressDialog(ctx);
		pd.setCancelable(false);
		pd.setMessage("Doing..");
		pd.show();
	
	}
	
	@Override
	protected String doInBackground(String... arg0) {
		// TODO Auto-generated method stub
			String idd=arg0[0];
		// TODO Auto-generated method stub
		Log.i("status id to be like", idd);
			try 
			{

				if(string.equals("Liked"))
				{
					new Common().UpdateStatusLikeNew(ScommixSharedPref.getUSERID(), idd);
				}
				else{
			        new Common().InsertStatusLikeNew(ScommixSharedPref.getUSERID(), idd);
				}
	
		} 
			catch(Exception e)
			{
				ctx.runOnUiThread(new Runnable() {
					
					@Override
					public void run() 
					{
						// TODO Auto-generated method stub
						Toast.makeText(ctx, "Check Your Internet Connection!", Toast.LENGTH_SHORT).show();

					}
				});
				e.printStackTrace();
			}
			return null;
	
	}
	
	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		pd.dismiss();
	
	}
}
