package example.hsport.timetracking.db;

import android.database.sqlite.SQLiteDatabase;

final class TimeDataTable {

    private static final String _CREATE_TABLE = "CREATE TABLE \"time_data\" (\n" +
            "\t\"_id\"\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "\t\"start_time\"\tTEXT NOT NULL,\n" +
            "\t\"end_time\"\tTEXT\n" +
            ")";

    static void createTable(SQLiteDatabase db) {
        db.execSQL(_CREATE_TABLE);
    }

}
