package com.example.complin;

import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.BitmapFactory;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;


/**
 * This class provides some (basic) database functionality.
 */
public class MyDatabaseHelper extends SQLiteOpenHelper {

    public static final String TABLE_USERS = "users";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_MOBILE = "mobile";
    public static final String COLUMN_PASSWORD = "password";
//complain table
Context context;
    public static final String COLUMN_CATEGORY = "category";
    public static final String COLUMN_SEVERITY = "severity";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String TABLE_COMPLAIN = "complain";
    public static final String COLUMN_IMAGE = "image" ;

    private static final String DATABASE_NAME = "ComplainApp.db";
    private static final int DATABASE_VERSION = 1;

    private ByteArrayOutputStream objectByteArrayOutputStream;
    private byte[] imageByte;





    // Database creation sql statement
    private String DATABASE_CREATE = "CREATE TABLE IF NOT EXISTS " +
            TABLE_USERS + "(" + COLUMN_ID + " integer primary key autoincrement, " +
            COLUMN_NAME + " text not null, " + COLUMN_EMAIL +
            " text not null, " + COLUMN_MOBILE + " integer, " +
            COLUMN_PASSWORD + " text not null);";



    // Initialize the database object.

    /**
     * Initialises the database object.
     * @param context the context passed on to the super.
     */
    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;

    }

    /**
     * Converts the show table in the database to a JSON string.
     * @param database the database to get the data from.
     * @return a string in JSON format containing all the show data.
     */
    private String getJSONExportString(SQLiteDatabase database) {
        String selectQuery = "SELECT * FROM " + TABLE_USERS;
        Cursor cursor = database.rawQuery(selectQuery, null);

        // Convert the database to JSON
        JSONArray databaseSet = new JSONArray();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int totalColumn = cursor.getColumnCount();
            JSONObject rowObject = new JSONObject();

            for (int i = 0; i < totalColumn; i++) {
                if (cursor.getColumnName(i) != null) {
                    try {
                        if (cursor.getString(i) != null) {
                            rowObject.put(cursor.getColumnName(i), cursor.getString(i));
                        } else {
                            rowObject.put(cursor.getColumnName(i), "");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            databaseSet.put(rowObject);
            cursor.moveToNext();
        }
        cursor.close();

        // Convert databaseSet to string and put in file
        return databaseSet.toString();
    }


    /**
     * Returns the database name.
     * @return the database name.
     */
    public static String getDatabaseFileName() {
        return DATABASE_NAME;
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        // Create the database with the database creation statement.
        String sql="CREATE TABLE COMPLAIN (_id INTEGER PRIMARY KEY AUTOINCREMENT,CATEGORY TEXT,SEVERITY TEXT ,DESCRIPTION TEXT ,IMAGE BLOB)";
        String COMPLAIN_TABLE = " CREATE TABLE IF NOT EXISTS " + TABLE_COMPLAIN + "(" + COLUMN_ID + " integer primary key autoincrement, "+ COLUMN_CATEGORY + " text not null, " + COLUMN_SEVERITY + " text not null, " + COLUMN_DESCRIPTION + " text not null, " + COLUMN_IMAGE + " imageBLOB );";
        try {
            database.execSQL(DATABASE_CREATE);
            database.execSQL(COMPLAIN_TABLE);
            database.execSQL(sql);

        } catch (Exception e) {
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //if (oldVersion > 1) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_USERS);
        //}
    }
    public boolean addUser(String name, String email, String mobile, String password){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues c=new ContentValues();
        c.put(COLUMN_NAME,name);
        c.put(COLUMN_EMAIL,email);
        c.put(COLUMN_MOBILE,mobile);
        c.put(COLUMN_PASSWORD,password);
        long result=db.insert(TABLE_USERS,null,c);
        if (result==-1){
            return false;
        } else {
            return true;
        }

    }

    public String getLoginData(String email,String password)
    {
        SQLiteDatabase sql=this.getReadableDatabase();
        String query=" select count(*) from "+TABLE_USERS+" where email ='"+email+"' and password='"+password+"'";
        Cursor cursor =sql.rawQuery(query,null);
        cursor.moveToFirst();
        String count = cursor.getString(cursor.getColumnIndex(cursor.getColumnName(0)));
        return count;

    }

//    store complain method
public void storecomplain(ModelClass objectModelClass){
        try{
            SQLiteDatabase db=this.getWritableDatabase();
            Bitmap imageToStoreBitmap = objectModelClass.getImage();

            objectByteArrayOutputStream= new ByteArrayOutputStream();
            imageToStoreBitmap.compress(Bitmap.CompressFormat.JPEG,100,objectByteArrayOutputStream);

            imageByte=objectByteArrayOutputStream.toByteArray();
            ContentValues objectContentValues = new ContentValues();

            objectContentValues.put("category",objectModelClass.getCategory());
            objectContentValues.put("description",objectModelClass.getDescription());
            objectContentValues.put("severity",objectModelClass.getSeverity());
            objectContentValues.put("image",imageByte);

            long result = db.insert(TABLE_COMPLAIN,null,objectContentValues);
    if(result!=-1) {
        Toast.makeText(context, "Your complain is added successfully" , Toast.LENGTH_SHORT).show();
        db.close();
    }
    else{
        Toast.makeText(context, "Faild to add complain" , Toast.LENGTH_SHORT).show();

    }



        } catch (Exception e) {
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT).show();

        }

}


public ArrayList<ModelClass> getAllData(){
        try{
            SQLiteDatabase objectSqLiteDatabase = this.getReadableDatabase();
            ArrayList<ModelClass> objectModelClassList=new ArrayList<>();

            Cursor objectCursor=objectSqLiteDatabase.rawQuery("select * from complain",null);
            if(objectCursor.getCount()!=0){
                while (objectCursor.moveToNext()){
                    String category=  objectCursor.getString(0);
                    String severity=  objectCursor.getString(1);
                    String description=  objectCursor.getString(2);
                    byte[] imageBytes=objectCursor.getBlob(3);
                    Bitmap  objectBitmap= BitmapFactory.decodeByteArray(imageBytes, 0 ,imageBytes.length);
                    objectModelClassList.add(new ModelClass(category,severity,description,objectBitmap));
                }
                return objectModelClassList;
            }
            else {
                Toast.makeText(context,"NO values exist",Toast.LENGTH_SHORT).show();
                return null;

            }
        } catch (Exception e) {
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT).show();
            return null;
        }
}
}
