package my.edu.utar.group;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "Login.db";
    public static final String TABLE_NAME = "users";
    public static final String UID = "id";
    public static final String COL_1 = "username";
    public static final String COL_2 = "password";
    private static final String SCRIPT_CREATE_DATABASE = "create Table " + TABLE_NAME + " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_1 + " TEXT, "+ COL_2 + " TEXT);";
//---------------------------------------------------
    public static final String TABLE_NAME2 = "history";
    public static final String HID = "id";
    public static final String Before = "original";
    public static final String After = "translated";
    private static final String SCRIPT_CREATE_DATABASE2 = "create Table " + TABLE_NAME2 + " (" + HID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + Before + " TEXT, " + After + " TEXT, " + "user_id INTEGER, FOREIGN KEY('user_id') REFERENCES " + TABLE_NAME + "(" + UID + "));";


    private SQLiteDatabase sqLiteDatabase;
    public DBHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SCRIPT_CREATE_DATABASE);
        db.execSQL(SCRIPT_CREATE_DATABASE2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists " + TABLE_NAME );
        db.execSQL("drop Table if exists " + TABLE_NAME2 );
        onCreate(db);
    }

    public Boolean insertData(String username, String password){
        sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(COL_1, username);
        contentValues.put(COL_2, password);
        long result = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        if(result == -1) return false;
        else
            return true;
    }

    public Boolean insertHistory(String original, String translated, int userId){
        sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(Before, original);
        contentValues.put(After, translated);
        contentValues.put("user_id", UID);
        long result = sqLiteDatabase.insert(TABLE_NAME2, null, contentValues);
        if(result == -1) return false;
        else
            return true;
    }

    public Boolean checkusername(String username) {
        sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME, null, COL_1 +" = ?", new String[]{username}, null, null, null);
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public Boolean checkusernamepassword(String username, String password){
        sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME, null, COL_1 + " = ? AND " +COL_2 + " = ?", new String[]{username, password}, null, null, null);
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }
}