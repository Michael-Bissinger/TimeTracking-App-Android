package example.hsport.timetracking.db;

import android.content.ContentResolver;
import android.net.Uri;

public final class TimeDataContract {

    public static final String AUTHORITY = "example.hsport.timetracking.provider"; // Is this really correct???
    public static final Uri AUTHORITY_URI = Uri.parse("content://" + AUTHORITY);


    public static final class TimeData {

        // directory for data
        public static final String CONTENT_DIRECTORY = "time";

        // Uri for the data
        public static final Uri CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, CONTENT_DIRECTORY);

        // Datatype for listing of Data
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_DIRECTORY;

        // Datatype for a single set of data
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_DIRECTORY;

    }


}
