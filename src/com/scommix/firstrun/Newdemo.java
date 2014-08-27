package com.scommix.firstrun;


import org.kobjects.mime.Decoder;

import com.svimedu.scommix.LoginFirstRun;
import com.svimedu.scommix.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class Newdemo extends Activity{
	ImageView iv;
	  Button go;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		 if (Build.VERSION.SDK_INT >= 19) {
				getWindow().addFlags(android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
				getWindow().addFlags(android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		
			 requestWindowFeature(Window.FEATURE_NO_TITLE);
		        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		            WindowManager.LayoutParams.FLAG_FULLSCREEN);
		 }
		 else{
			 
			 requestWindowFeature(Window.FEATURE_NO_TITLE);
		        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		            WindowManager.LayoutParams.FLAG_FULLSCREEN);
		 }
		 
		setContentView(R.layout.newdemo);
		iv=(ImageView)findViewById(R.id.img);
		
		
		 go=(Button)findViewById(R.id.go);
		   go.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent i=new Intent(Newdemo.this,LoginFirstRun.class);
					startActivity(i);
					finish();
				}
			});
		
//		BitmapFactory.Options o = new BitmapFactory.Options();
//	    o.inJustDecodeBounds = true;
//	    o.inSampleSize=4;
//		
//		
//		Bitmap bitmap=BitmapFactory.decodeResource(getResources(), R.drawable.harward,o);
//		
//		iv.setImageBitmap(bitmap);
		
	}

}
