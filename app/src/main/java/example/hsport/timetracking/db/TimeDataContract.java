package example.hsport.timetracking.db;

import android.net.Uri;

public final class TimeDataContract {

    public static final String AUTHORITY = "example.hsport.timetracking"; // Is this really correct???
    public static final Uri AUTHORITY_URI = Uri.parse("content://" + AUTHORITY);

}
