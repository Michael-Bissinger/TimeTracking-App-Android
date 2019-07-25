package example.hsport.timetracking.db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import java.util.Locale;

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

        // resolve uri
        final int uriType = _URI_MATCHER.match(uri);

        Uri insertUri = null;
        long newItemId = -1;

        // Determine action depending on uri
        switch (uriType) {
            case TimeDataTable.ITEM_LIST_ID:
            case TimeDataTable.ITEM_ID:
                SQLiteDatabase db = _dbHelper.getWritableDatabase();
                newItemId = db.insert(TimeDataTable.TABLE_NAME, null, values);
                db.close();
                break;

            default:
                // if uri is unknown
                throw new IllegalArgumentException(String.format(Locale.GERMANY, "Unknown URI: %s", uri));

        }

        // Dataset successfully created
        if (newItemId > 0) {
            // create URI
            insertUri = ContentUris.withAppendedId(
                    TimeDataContract.TimeData.CONTENT_URI,
                    newItemId);

            getContext().getContentResolver().notifyChange(uri, null);
        }

        return insertUri;
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
