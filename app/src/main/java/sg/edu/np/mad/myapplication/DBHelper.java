package sg.edu.np.mad.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "UserData.db", null, 1); //this creates the DB with the name "UserData.db" in version 1
    }

    //CREATE DATABASE
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE = "CREATE TABLE Userdetails (ID INTEGER PRIMARY KEY AUTOINCREMENT, Name TEXT , Description TEXT,  Followed BOOLEAN)";
        db.execSQL(CREATE); //creates user details table by executing the above statement
    }

    //UPGRADE TO USE THE NEWEST VERSION OF DB //NOTE drop detroys the whole table
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS Userdetails"); //drops the existing table and then upgrade it
        onCreate(db); //by calling this and create a new table
    }

    //INSERTING VALUES INTO DATABASE (Userdetails table)
    //**When u insert the primary key, if a primray key already exisit then the data will not be inserted
    public Boolean insertUserData(String name, String description, int id, boolean followed ){
        SQLiteDatabase db = this.getWritableDatabase(); //GETs a writable database and passes it to DB for writing

        ContentValues contentValues = new ContentValues(); //to attach and insert values into our table
        contentValues.put("ID", id);
        contentValues.put("Name", name);
        contentValues.put("Description", description);
        contentValues.put("Followed", followed);

        long result = db.insert("Userdetails", null, contentValues); //here you access the writeable DB you opened above and then insert values

        db.close();//close the data as soon so it is not corrupted

        if(result == -1){ //if -1 is given means insertion failed (as a primary key of such alr exist in the DB)
            return false;
        }else{
            return true;
        }
    }

    //UPDATING UserValues into DB
    public Boolean updateUserData( int id, boolean followed ){
        SQLiteDatabase db = this.getWritableDatabase(); //GETs a writable database and passes it to DB for writing

        ContentValues contentValues = new ContentValues(); //to attach and update values into our table
        //contentValues.put("ID", id);                     //the primary key will be used to locate the user obj and update the Userdetails
        //contentValues.put("Name", name);
        //contentValues.put("Description", description);
        contentValues.put("Followed", followed);

        Cursor cursor = db.rawQuery("SELECT * FROM Userdetails WHERE ID = ?", new String[]{String.valueOf(id)});
        //*** cursor is for selecting the row, hence what ever is selected will be loaded into the cursor, then you can traverse the cursor ROW by ROW.
        // Hence the above statement should load one row, because there's only one instance of a user obj with this ID

        if (cursor.getCount() > 0){ //meaning if cursor have more than 0 rows (like cursor have 1 row), then it will execute the following code
            long result = db.update("Userdetails", contentValues, "ID=?", new String[]{String.valueOf(id)}); //here you access the writeable DB you opened above and then update values based on the ID
            //Use String.valueOf(id) which will return the String representation of your int.
            db.close();
            if(result == -1){ //if -1 is given means updating failed
                return false;
            }else{             //else updating is successful
                return true;
            }
        }else{
            return false; //meaning cursor has nothing and no instance of such user obj was found in DB
        }
    }

    //Delete All UserValues into DB
    public Boolean deleteAllUserData(){
        SQLiteDatabase db = this.getWritableDatabase(); //GETs a writable database and passes it to DB for writing

        long result = db.delete("Userdetails", null, null); //here you access the writeable DB you opened above and then insert values

        db.close();//close the data as soon so it is not corrupted

        if(result == -1){ //if -1 is given means insertion failed (as a primary key of such alr exist in the DB)
            return false;
        }else{
            return true;
        }
    }

    //GETTING DATA back info from DB
    public ArrayList<User> getUsers(){
        SQLiteDatabase db = this.getWritableDatabase(); //GETs a writable database and passes it to DB for writing
        Cursor cursor = db.rawQuery("SELECT * FROM Userdetails", null); //dont need any argument since we dont need to locate a specific data
        //Cursor will be loaded with user data from UserDetails table


        //Array List to store the Obj
        ArrayList<User> userObjFromDB = new ArrayList<User>();

        //The cursor reads each row from the DB and add it to the user obj to be inserted into the list
        while(cursor.moveToNext()){ //sees the boolean value until its false then it stops
            Integer Id = Integer.parseInt(cursor.getString(0));
            String name = cursor.getString(1);
            String desc = cursor.getString(2);
            Boolean followed = Boolean.parseBoolean(cursor.getString(3));

            //Create the user obj then add it into the user obj List
            User random_userObj = new User(name, desc, Id, followed );
            userObjFromDB.add(random_userObj);
        }//this will help us retrive everything from the DB and add the created Obj to the list

        return userObjFromDB; //this returns the array list
    }
}
