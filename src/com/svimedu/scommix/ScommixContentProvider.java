package com.svimedu.scommix;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

public class ScommixContentProvider extends ContentProvider{
	
	public static final String KEY_ID="_id";
	public static final String USERID="USERID";
	public static final String USERNAME="USERNAME";
	public static final String USERTYPE="USERTYPE";
	public static final String EMAIL="EMAIL";
	public static final String PASSWORD="PASSWORD";
	public static final String GENDER="GENDER";
	public static final String DATEOFBIRTH="DATEOFBIRTH";
	public static final String FATHERNAME="FATHERNAME";
	public static final String MOTHERNAME="MOTHERNAME";
	public static final String MOBILENO="MOBILENO";
	public static final String LANDLINE="LANDLINE";
	public static final String SIBLINGS="SIBLINGS";
	public static final String SIBLINGSNO ="SIBLINGSNO";
    public static final String _CLASS ="_CLASS";
    public static final String FATHERSOCCUPATION ="FATHERSOCCUPATION";
    public static final String MOTHERSOCCUPATION="MOTHERSOCCUPATION";
    public static final String COUNTRY="COUNTRY";
    public static final String NAME="NAME";
    public static final String ADDRESS1="ADDRESS1";
    public static final String ADDRESS2="ADDRESS2";
    public static final String ZIPCODE="ZIPCODE";
    public static final String STATE="STATE";
    public static final String CITY="CITY";
    public static final String CADDRESS1="CADDRESS1";
    public static final String CADDRESS2="CADDRESS2";
	public static final String PROFILEPIC = "PROFILEPIC";	
	public static final String INSTITUTEPIC = "INSTITUTEPIC";
	private static final String AUTHORITY = "com.svimedu.scommix.ScommixContentProvider";
	
	public static final String DATABASE_NAME="scommixdatabase.db";
	public static final String DATABASE_TABLEuserinfo="userinfo";

	

	

	
	private static final int DATABASE_VERSION=1;
	public static final Uri CONTENT_URI_UserInfo = Uri.parse("content://"+ AUTHORITY + "/" + DATABASE_TABLEuserinfo);
	

ScommixContentProvider Help;
	SQLiteDatabase database;
	
	private class OpenHelper extends SQLiteOpenHelper{



		private static final String SQLuserinfo= "create table "+DATABASE_TABLEuserinfo+" ("+KEY_ID+
				" integer primary key autoincrement, "+USERID+" text ,"+USERNAME+" text ,"+USERTYPE+" text ,"+EMAIL+
				" text ,"+PASSWORD+" text ,"+GENDER+" text ,"+DATEOFBIRTH+" text ,"+FATHERNAME+
				" text,"+MOTHERNAME+" text,"+MOBILENO+" text,"+LANDLINE+" text,"+SIBLINGS+" text,"+SIBLINGSNO+" text,"+_CLASS+" text not null,"+FATHERSOCCUPATION+" text,"+
				MOTHERSOCCUPATION+" text,"+COUNTRY+" text,"+NAME+" text,"+ADDRESS1+" text,"+ADDRESS2+" text,"+ZIPCODE+" text,"+STATE+" text,"+CITY+" text,"+CADDRESS1+" text,"+CADDRESS2+" text,"+PROFILEPIC+" text,"+INSTITUTEPIC+" text ); " ;
		
		
		public OpenHelper(Context context) 
		{
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			System.out.println("db created;");
		db.execSQL(SQLuserinfo);
	
		
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLEuserinfo);
			
			onCreate(db);
		}
		
	}

	
	OpenHelper Helper;
	
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		 int count = 0;
		database=Helper.getWritableDatabase();
	
			count=this.database.delete(DATABASE_TABLEuserinfo, selection, null);

		
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues initialValues) {
		// TODO Auto-generated method stub
		Log.i("id", ""+uri.toString());
		ContentValues values;
		if (initialValues == null) {
			values = new ContentValues();
		} else {
			values = new ContentValues(initialValues);
		}
		
		long id;
		database = Helper.getWritableDatabase();

			id=database.insert(DATABASE_TABLEuserinfo, null, values);
			Log.i("id", ""+id);
			uri = ContentUris.withAppendedId(ScommixContentProvider.CONTENT_URI_UserInfo,
					id);
			
			getContext().getContentResolver().notifyChange(uri, null);
		
	
		
		 getContext().getContentResolver().notifyChange(uri, null);
				return uri;
			}
	
		
			
		
	
	

	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		Helper = new OpenHelper(getContext());
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		SQLiteDatabase db=Helper.getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

			
			qb.setTables(DATABASE_TABLEuserinfo);
			Log.i("id"," tables set in query");
		
		
		
		
			
	Cursor c = qb.query(db, projection, selection, selectionArgs,
				sortOrder, null, null);
		
		c.setNotificationUri(getContext().getContentResolver(), uri);
		return c;
		
		
		
		
		}
		
		
		
		
	

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		 int count = 0;
		 database=Helper.getWritableDatabase();
         count = database.update(DATABASE_TABLEuserinfo, values, selection, selectionArgs);
         getContext().getContentResolver().notifyChange(uri, null);
	     return count;
	}

}
