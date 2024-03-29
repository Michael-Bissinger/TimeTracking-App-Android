package example.hsport.timetracking.db;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public final class TimeDataContract {

    public static final String AUTHORITY = "example.hsport.timetracking.provider"; // Is this really correct???
    public static final Uri AUTHORITY_URI = Uri.parse("content://" + AUTHORITY);


    public static final class TimeData {

        // directory for data
        public static final String CONTENT_DIRECTORY = "time";

        // directory for open datasets
        public static final String NOT_FINISHED_CONTENT_DIRECTORY =
                CONTENT_DIRECTORY + "/not_finished";

        // Uri for the data
        public static final Uri CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, CONTENT_DIRECTORY);

        // Datatype for listing of Data
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_DIRECTORY;

        // Datatype for a single set of data
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_DIRECTORY;

        // URI for not yet finished data
        public static final Uri NOT_FINISHED_CONTENT_URI =
                Uri.withAppendedPath(AUTHORITY_URI, NOT_FINISHED_CONTENT_DIRECTORY);

        public interface Columns extends BaseColumns {
            String START_TIME = "start_time";

            String END_TIME = "end_time";
        }
    }


    public static final class Converter {
        private static  final String _ISO_8601_PATTERN = "yyyy-MM-dd'T'HH:mm";

        public static final DateFormat DB_DATE_TIME_FORMATTER =
                new SimpleDateFormat(_ISO_8601_PATTERN, Locale.GERMANY);

        public static Calendar parse(String dbTime) throws ParseException {
            Calendar date = Calendar.getInstance();
            date.setTime(DB_DATE_TIME_FORMATTER.parse(dbTime));
            return date;
        }

        public static String format(Calendar dateTime) {
            return DB_DATE_TIME_FORMATTER.format(dateTime.getTime());
        }

    }

}
