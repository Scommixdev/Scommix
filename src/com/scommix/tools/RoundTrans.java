package com.scommix.tools;

import android.graphics.Bitmap;

import com.scommix.homeandprofile.Status;
import com.squareup.picasso.Transformation;

public class RoundTrans implements Transformation{

	Bitmap load;
	


	@Override
	public String key() {
		// TODO Auto-generated method stub
		return "RoundTrans";
	}

	@Override
	public Bitmap transform(Bitmap arg0) {
		// TODO Auto-generated method stub
		load=arg0;
		load=Utils.getRoundedBitmap(load);
		arg0.recycle();
		return load;
	}

}
