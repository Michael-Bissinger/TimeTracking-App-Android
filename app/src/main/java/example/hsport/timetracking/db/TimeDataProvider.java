package example.hsport.timetracking.db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

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
    private static final String _ID_WHERE = BaseColumns._ID + "=?";

    @Override
    public boolean onCreate() {
        _dbHelper = new DbHelper(getContext());

        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        final int uriType = _URI_MATCHER.match(uri);
        Cursor data;
        SQLiteDatabase db = _dbHelper.getReadableDatabase();

        switch (uriType) {
            case TimeDataTable.ITEM_LIST_ID:
                data = db.query(TimeDataTable.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;

            case TimeDataTable.ITEM_ID:
                final long id = ContentUris.parseId(uri);
                data = db.query(TimeDataTable.TABLE_NAME, projection, _ID_WHERE, idAsArray(id),
                        null, null, null);
                break;

            default:
                throw new IllegalArgumentException(String.format(Locale.GERMANY,
                        "Unbekannte URI: %s", uri));
        }

        if (data != null) {
            data.setNotificationUri(getContext().getContentResolver(), uri);
        }

        return data;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

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

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

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
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        final int uriType = _URI_MATCHER.match(uri);
        int deletedItems = 0;
        SQLiteDatabase db = _dbHelper.getWritableDatabase();


        // Determine action depending on uri
        switch (uriType) {
            case TimeDataTable.ITEM_LIST_ID:
                deletedItems = db.delete(TimeDataTable.TABLE_NAME, selection, selectionArgs);
                db.close();
                break;

            case TimeDataTable.ITEM_ID:
                final long id = ContentUris.parseId(uri);
                final String idWhere = BaseColumns._ID + "=?";
                final String[] idArgs = new String[]{String.valueOf(id)};
                deletedItems = db.delete(TimeDataTable.TABLE_NAME, idWhere, idArgs);
                db.close();
                break;

             default:
                 //unknown uri
                 throw new IllegalArgumentException(String.format(Locale.GERMANY,
                         "Unbekannte uri: %s", uri));
        }

        // Datasets successfully deleted
        if (deletedItems > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return deletedItems;
    }


    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values,
                      @Nullable String selection, @Nullable String[] selectionArgs) {
        final int uriType = _URI_MATCHER.match(uri);
        int updateItems = 0;
        SQLiteDatabase db = _dbHelper.getWritableDatabase();

        switch (uriType) {
            case TimeDataTable.ITEM_LIST_ID:
                updateItems = db.update(TimeDataTable.TABLE_NAME, values, selection, selectionArgs);
                db.close();
                break;
             case TimeDataTable.ITEM_ID:
                 final long id = ContentUris.parseId(uri);
                 updateItems = db.update(TimeDataTable.TABLE_NAME, values, _ID_WHERE, idAsArray(id));

                 // where is _ID_WHERE???

                 db.close();
                 break;

            default:
                throw new IllegalArgumentException(String.format(Locale.GERMANY, "Unbekannte Uris: %s", uri));

        }

        if (updateItems > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return updateItems;
    }

    private String[] idAsArray(long id) {
        return new String[]{String.valueOf(id)};
    }

}
