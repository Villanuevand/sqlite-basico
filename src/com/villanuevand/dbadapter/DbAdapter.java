package com.villanuevand.dbadapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * @author Villanuevand
 *
 */
public class DbAdapter {

	DbHelper helper;
	public DbAdapter(Context context){
		helper = new DbHelper(context);
	}
	
	/**Inserta la data a la base de datos.
	 * @param name
	 * @param password
	 * @return
	 */
	public long insertData(String name, String password){	
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(DbHelper.COLUMN_NAME_NAME, name);
		contentValues.put(DbHelper.COLUMN_NAME_PASSWORD, password);
		long id = db.insert(DbHelper.TABLE_NAME, null, contentValues);
		return id;
	}
	
	/**Obtiene todos los valores de la base de datos.
	 * @return
	 */
	public String getAllData(){
		SQLiteDatabase db = helper.getWritableDatabase();
		/*Sencia SQL a ejecutar
		 *SELECT id,name,password FROM USER 
		 */
		String[] columns = {helper.COLUMN_NAME_ID,helper.COLUMN_NAME_NAME,helper.COLUMN_NAME_PASSWORD};
		Cursor cursor = db.query(helper.TABLE_NAME, columns, null, null, null, null, null);
		StringBuffer buffer = new StringBuffer();
		while (cursor.moveToNext()) {
			int cid = cursor.getInt(0);
			String name = cursor.getString(1);
			String password = cursor.getString(2);
			buffer.append(cid + " " + name + " " + password + "\n");
		}
		return buffer.toString();
	}
	
	
	/**Obtiene los valores nombre y contraseña, de un usuario especifico.
	 * @param name
	 * @return
	 */
	public String getData(String name){
		SQLiteDatabase db = helper.getWritableDatabase();
		/*Sentencia SQL a ejecutar
		 * SELECT name,password from user where name="name";
		 */
		String[] columns = {helper.COLUMN_NAME_NAME,helper.COLUMN_NAME_PASSWORD};
		//Estableciendo la consulta SQL
		Cursor cursor = db.query(helper.TABLE_NAME, columns, helper.COLUMN_NAME_NAME +" = '"+ name +"'", null, null, null, null, null);
		StringBuffer buffer = new StringBuffer();
		while (cursor.moveToNext()) {
			//Obteniendo los valores de los indices de las columnas.
			int index1 = cursor.getColumnIndex(helper.COLUMN_NAME_NAME);
			int index2 = cursor.getColumnIndex(helper.COLUMN_NAME_PASSWORD);
			String nameCol = cursor.getString(index1);
			String passCol = cursor.getString(index2);
			buffer.append(nameCol + " " + passCol +"\n");					
		}
		return buffer.toString();
		
	}
	
	static class DbHelper extends SQLiteOpenHelper{
		/*
		 * Si se cambia el numero de la versión DATABASE_VERSION 
		 * automaticamente entra en el método onUpgrade
		 */
		private static final String TAG_SQL = "EJEMPLO-SQLITE";
		private static final String DATABASE_NAME = "slidenerd.sqlite";
		private static final int DATABASE_VERSION = 2;
		private static final String TABLE_NAME = "user";	
		private static final String COLUMN_NAME_ID = "id";
		private static final String COLUMN_NAME_NAME = "name";
		private static final String COLUMN_NAME_PASSWORD = "password";
		private static final String SQL_CREATE_TABLE_USER = "CREATE TABLE " +  TABLE_NAME + "(" + COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME_NAME + " TEXT,"+ COLUMN_NAME_PASSWORD +" TEXT);";
		private static final String SQL_DROP_TABLE = "DROP TABLE IF EXISTS "+ TABLE_NAME;
		@SuppressWarnings("unused")
		private Context context;
		
		public DbHelper(Context context){
			super(context,DATABASE_NAME, null, DATABASE_VERSION);
			this.context = context;
			
		}
		@Override
		public void onCreate(SQLiteDatabase db) {
			try {
				db.execSQL(SQL_CREATE_TABLE_USER);
				Log.i(TAG_SQL, "table user created!");
			} catch (SQLException e) {
				Log.e(TAG_SQL, e.toString());
			}
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			try {
				db.execSQL(SQL_DROP_TABLE);
				onCreate(db);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				Log.e(TAG_SQL, e.toString());
			}
			

		}
	}

}

