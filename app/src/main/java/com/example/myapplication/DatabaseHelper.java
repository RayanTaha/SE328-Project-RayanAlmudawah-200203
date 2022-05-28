package com.example.myapplication;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * Created by Mitch on 2016-05-13.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Student.db";
    public static final String TABLE_NAME = "Students_data";
    public static final String COL1 = "ID";
    public static final String COL2 = "NAME";
    public static final String COL3 = "SURNAME";
    public static final String COL4 = "FATHER_NAME";
    public static final String COL5 = "NATIONAL_ID";
    public static final String COL6 = "DATE_OF_BIRTH";
    public static final String COL7 = "GENDER";

    /* Constructor */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    /* Code runs automatically when the dB is created */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME +
                " (ID INTEGER PRIMARY KEY, " +
                " NAME TEXT, SURNAME TEXT, FATHER_NAME TEXT, NATIONAL_ID TEXT, DATE_OF_BIRTH TEXT, GENDER TEXT)";
        db.execSQL(createTable);
    }

    /* Every time the dB is updated (or upgraded) */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    /* Basic function to add data. REMEMBER: The fields
       here, must be in accordance with those in
       the onCreate method above.
    */
    public boolean addData(String ID, String name, String surname, String fatherName, String NationalID, String dateOfBirth, String gender) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, ID);
        contentValues.put(COL2, name);
        contentValues.put(COL3, surname);
        contentValues.put(COL4, fatherName);
        contentValues.put(COL5, NationalID);
        contentValues.put(COL6, dateOfBirth);
        contentValues.put(COL7, gender);

        long result = db.insert(TABLE_NAME, null, contentValues);

        //if data are inserted incorrectly, it will return -1
        if(result == -1) {return false;} else {return true;}
    }

    /* Returns only one result */
    public Cursor structuredQuery(String ID) {
        SQLiteDatabase db = this.getReadableDatabase(); // No need to write
        Cursor cursor = db.query(TABLE_NAME, new String[]{COL1,
                        COL2, COL3, COL4, COL5, COL6, COL7}, COL1 + "=?",
                new String[]{ID}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        return cursor;
    }

    public Cursor getSpecificResult(String ID){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME+" Where ID="+ID,null);
        return data;
    }

    // Return everything inside the dB
    public Cursor getListContents() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return data;
    }

    //new Delete method based on the product ID
    public boolean deleteData (String product_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, COL1 + "=" + product_id, null) > 0;
    }
    public boolean updateData(String ID, String name, String surname, String fatherName, String NationalID, String dateOfBirth, String gender){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, ID);
        contentValues.put(COL2, name);
        contentValues.put(COL3, surname);
        contentValues.put(COL4, fatherName);
        contentValues.put(COL5, NationalID);
        contentValues.put(COL6, dateOfBirth);
        contentValues.put(COL7, gender);
        int result = db.update(TABLE_NAME, contentValues, "ID=?", new String[]{ID});
        if(result == 1)
            return true;
        else
            return false;

    }
}
