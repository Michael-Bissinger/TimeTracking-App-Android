package example.hsport.timetracking.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

public class DbHelper  extends SQLiteOpenHelper {

    private static final String _DB_FILE_NAME = "time_tracking.db";
    private static final int _DB_VERSION = 1;
    private static final String _CREATE_TABLE = "CREATE TABLE \"time_data\" (\n" +
            "\t\"_id\"\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "\t\"start_time\"\tTEXT NOT NULL,\n" +
            "\t\"end_time\"\tTEXT\n" +
            ")";



    public DbHelper(@Nullable Context context) {
        super(context, _DB_FILE_NAME, null, _DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.println(1,"Db created", "Db created");
        db.execSQL(_CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
