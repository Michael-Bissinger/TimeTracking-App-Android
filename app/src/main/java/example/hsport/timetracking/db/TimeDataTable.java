package example.hsport.timetracking.db;

import android.database.sqlite.SQLiteDatabase;

final class TimeDataTable {

    private static final String _CREATE_TABLE = "CREATE TABLE \"time_data\" (\n" +
            "\t\"_id\"\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "\t\"start_time\"\tTEXT NOT NULL,\n" +
            "\t\"end_time\"\tTEXT\n" +
            ")";


    // ID for listing of this table
    public static final int ITEM_LIST_ID = 100;

    // Specific Dataset of this table
    public static final int ITEM_ID = 101;

    // ID for dataset, that isn't finished yet (starttime, but not endtime)
    public static final int NOT_FINISHED_ITEM_ID = 102;

    // Name of table
    public static final String TABLE_NAME = "time_data";

    static void createTable(SQLiteDatabase db) {
        db.execSQL(_CREATE_TABLE);
    }

}
