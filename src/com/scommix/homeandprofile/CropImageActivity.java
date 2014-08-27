package com.scommix.homeandprofile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;




import com.scommix.WebServices.Common.Common;
import com.scommix.WebServices.Common.VectorByte;
import com.scommix.customviews.CropImageView;
import com.scommix.sharedpref.ScommixSharedPref;
import com.scommix.tools.Utils;
import com.svimedu.scommix.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class CropImageActivity extends ActionBarActivity{
	
    private static final int DEFAULT_ASPECT_RATIO_VALUES = 10;
    private static final int ROTATE_NINETY_DEGREES = 90;
    private static final String ASPECT_RATIO_X = "ASPECT_RATIO_X";
    private static final String ASPECT_RATIO_Y = "ASPECT_RATIO_Y";
    private static final int ON_TOUCH = 1;
	  private int mAspectRatioX = DEFAULT_ASPECT_RATIO_VALUES;
	    private int mAspectRatioY = DEFAULT_ASPECT_RATIO_VALUES;
         Bitmap croppedImage;
		 byte[] byteArray;
		 ContentResolver resolver;
	     ContentValues cv;
	     String what;
	     
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.cropactivity);
		Intent fromprofile=getIntent();
		String mypicuri=fromprofile.getStringExtra("picuri");
		
		what=fromprofile.getStringExtra("what");
		
	
		Bitmap mypic=BitmapFactory.decodeFile(mypicuri);
		
		
		
		    Typeface mFont = Typeface.createFromAsset(getAssets(), "bahamal.ttf");
	        ViewGroup root = (ViewGroup) findViewById(R.id.mylayout);
	        setFont(root, mFont);
	        
	        // Initialize components of the app
	        final CropImageView cropImageView = (CropImageView) findViewById(R.id.CropImageView);
	        cropImageView.setImageBitmap(mypic);
	        
	      
	        
	        //Sets the rotate button
	        final Button rotateButton = (Button) findViewById(R.id.Button_rotate);
	        rotateButton.setOnClickListener(new View.OnClickListener() {

	            @Override
	            public void onClick(View v) 
	            {
	                cropImageView.rotateImage(ROTATE_NINETY_DEGREES);
	            }
	        });
	        
	   

	        // Sets initial aspect ratio to 10/10, for demonstration purposes
	        cropImageView.setAspectRatio(DEFAULT_ASPECT_RATIO_VALUES, DEFAULT_ASPECT_RATIO_VALUES);

	        final Button cropButton = (Button) findViewById(R.id.Button_crop);
	        cropButton.setOnClickListener(new View.OnClickListener() {

	            @Override
	            public void onClick(View v) {
	                croppedImage = cropImageView.getCroppedImage();
	                byte[] data = null;
	                ByteArrayOutputStream stream = new ByteArrayOutputStream();
	                croppedImage.compress(CompressFormat.JPEG, 70,stream);
	                data = stream.toByteArray();
	               new SavePhotoTask().execute(data);
	                
	            	 
	                
	                 
	              
	            }
	        });
	        
     
	        
	}
	
	  @Override
	    public void onConfigurationChanged(Configuration newConfig) {
	    	// TODO Auto-generated method stub
	    	super.onConfigurationChanged(newConfig);
	    	
	    	if ((getResources().getConfiguration().screenLayout &      Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE) {     
	        	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);


	    	}
	    	else if ((getResources().getConfiguration().screenLayout &      Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE) {     
	    	 	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);


	    	} 
	    	else if ((getResources().getConfiguration().screenLayout &      Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_NORMAL) {     
	        	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


	    	} 
	    	else if ((getResources().getConfiguration().screenLayout &      Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_SMALL) {     
	    		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	    	}
	    	else {
	    		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	    	}
	    }
	    
    public void setFont(ViewGroup group, Typeface font) {
        int count = group.getChildCount();
        View v;
        for (int i = 0; i < count; i++) {
            v = group.getChildAt(i);
            if (v instanceof TextView || v instanceof EditText || v instanceof Button) {
                ((TextView) v).setTypeface(font);
            } else if (v instanceof ViewGroup)
                setFont((ViewGroup) v, font);
        }
    }
@Override
protected void onResume() {
	// TODO Auto-generated method stub
	super.onResume();

}


	
	   @Override
	    protected void onSaveInstanceState(Bundle bundle) {
	        super.onSaveInstanceState(bundle);
	        bundle.putInt(ASPECT_RATIO_X, mAspectRatioX);
	        bundle.putInt(ASPECT_RATIO_Y, mAspectRatioY);
	    }
	    @Override
	    protected void onRestoreInstanceState(Bundle bundle) {
	        super.onRestoreInstanceState(bundle);
	        mAspectRatioX = bundle.getInt(ASPECT_RATIO_X);
	        mAspectRatioY = bundle.getInt(ASPECT_RATIO_Y);
	    }
	    
	    class SavePhotoTask extends AsyncTask<byte[], String, String> {
	    	ProgressDialog pd;
	    	@Override
	    	protected void onPreExecute() {
	    		// TODO Auto-generated method stub
	    		pd=new ProgressDialog(CropImageActivity.this);
	    		pd.setMessage("Preparing...");
	    		pd.show();
	    		super.onPreExecute();
	    	}
	        @Override
	        protected String doInBackground(byte[]... jpeg) {
	        	String Filename="temp.jpg";
	          File photo=new File(Environment.getExternalStorageDirectory(), Filename);
	          ScommixSharedPref.setPROFILEPICNAME(photo.getAbsolutePath());
	          if (photo.exists()) {
	                photo.delete();
	          }

	          try {
	        	  
	            FileOutputStream fos=new FileOutputStream(photo.getPath());
                fos.write(jpeg[0]);
	            fos.close();
	          
	          }
	          
	          catch (java.io.IOException e) {
	        	  e.printStackTrace();
	          }
	          
	          try{
	        	  VectorByte img=new VectorByte(jpeg[0]);
	        	
	        	new Common().InsertProfileImage(ScommixSharedPref.getUSERID(), img);
	          }
	          catch(Exception e)
	          {
	        	  
	          }

	          return(null);
	        }
	        
	        @Override
	        protected void onPostExecute(String result) {
	        	// TODO Auto-generated method stub
	        	super.onPostExecute(result);
	        	pd.dismiss();
	        	
	        	 Intent i=new Intent();
                 // i.putExtra("bytearray",data);
                  
                  setResult(RESULT_OK, i);
                  
                  if(what.equals("profilepic"))
                  {
                	  setResult(124,i);
                  }
                
	        	
	        	 finish();
	        }
	    }

}
