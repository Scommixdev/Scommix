package com.scommix.tools;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Base64;
  
public class Utils {
    private static byte[] x;

    public static String url="http://scommix.cloudapp.net/ProfileImage/";
    public static String url1="http://scommix.cloudapp.net/InstituteLogo/";
	public static void CopyStream(InputStream is, OutputStream os)
    {
        final int buffer_size=1024;
        try
        {
            byte[] bytes=new byte[buffer_size];
            for(;;)
            {
              int count=is.read(bytes, 0, buffer_size);
              if(count==-1)
                  break;
              os.write(bytes, 0, count);
            }
        }
        catch(Exception ex){
        	
        }
    }
	
	public static Bitmap getRoundedBitmap(Bitmap bitmap) {
	    final Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
	    final Canvas canvas = new Canvas(output);
	 
	    final int color = Color.RED;
	    final Paint paint = new Paint();
	    final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
	    final RectF rectF = new RectF(rect);
	 
	    paint.setAntiAlias(true);
	    canvas.drawARGB(0, 0, 0, 0);
	    paint.setColor(color);
	    canvas.drawOval(rectF, paint);
	 
	    paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
	    canvas.drawBitmap(bitmap, rect, rect, paint);
	 
	   
//	    if (bitmap != null && !bitmap.isRecycled()) {
//	    	bitmap.recycle();
//	    	bitmap = null; 
//	    }
	  
	 
	    return output;
	  }
    
//    public static String decodeBAse64(String input)
//    {
//    	try {
//			 x=input.getBytes("UTF-8");
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//    	
//    	 String decoded = new String(Base64.encode(x,Base64.DEFAULT));
//    	  
//    	 return decoded;
//     	
//    }
    
    public static String decodeBAse64(String input)
    {
    	try {
			 x=input.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	 String decoded = new String(Base64.decode(x,Base64.DEFAULT));
    	  
    	 return decoded;
     	
    }
}