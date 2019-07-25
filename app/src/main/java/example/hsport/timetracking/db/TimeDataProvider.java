package example.hsport.timetracking.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

public class TimeDataProvider extends ContentProvider {

    private static final UriMatcher _URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        //Lookup for listing
        _URI_MATCHER.addURI(
                TimeDataContract.AUTHORITY,
                TimeDataContract.TimeData.CONTENT_DIRECTORY,
                TimeDataTable.ITEM_LIST_ID);

        //Lookup for dataset
        _URI_MATCHER.addURI(
                TimeDataContract.AUTHORITY,
                TimeDataContract.TimeData.CONTENT_DIRECTORY + "/#",
                TimeDataTable.ITEM_ID);

    }

    private DbHelper _dbHelper = null;

    @Override
    public boolean onCreate() {
        _dbHelper = new DbHelper(getContext());

        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Override
    public String getType(Uri uri) {

        // resolve uri
        final int uriType = _URI_MATCHER.match(uri);
        String type = null;

        // Determine datatype
        switch (uriType) {
            case TimeDataTable.ITEM_LIST_ID:
                type = TimeDataContract.TimeData.CONTENT_TYPE;
                break;

            case TimeDataTable.ITEM_ID:
                type = TimeDataContract.TimeData.CONTENT_ITEM_TYPE;
                break;
        }
        return type;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
