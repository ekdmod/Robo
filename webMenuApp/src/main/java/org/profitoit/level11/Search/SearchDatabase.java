package org.webmenu.level11.Search;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.text.TextUtils;
import android.util.Log;

import org.webmenu.webmenuapp.R;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by EK on 15.1.12.
 */
public final class SearchDatabase {

    private static final String TAG = "DictionaryDatabase";

    //The columns we'll include in the dictionary table
    public static final String COL_WORD = "WORD";
    public static final String COL_DEFINITION = "DEFINITION";

    /**
     * The authority we use to get to our sample provider.
     */

    private static final String DATABASE_NAME = "DICTIONARY";
    private static final int DATABASE_VERSION = 1;
    public Cursor cursor;
    public String[][][][][] results;
/*

  a VARCHAR(10),
  b NVARCHAR(15),
  c TEXT,
  d INTEGER,
  e FLOAT,
  f BOOLEAN,
  g CLOB,
  h BLOB,
  i TIMESTAMP,
  j NUMERIC(10,5)
  k VARYING CHARACTER (24),
  l NATIONAL VARYING CHARACTER(16)

 */
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + SearchDb.TABLE_NAME + " (" +
                    SearchDb._ID + " INTEGER PRIMARY KEY," +
                    SearchDb.COLUMN_ID + TEXT_TYPE + COMMA_SEP +
                    SearchDb.COLUMN_NAME_ID + TEXT_TYPE + COMMA_SEP +
                    SearchDb.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                    SearchDb.COLUMN_NAME_CONTENT_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
                    SearchDb.COLUMN_NAME_CONTENT_URI + TEXT_TYPE + COMMA_SEP +
                    SearchDb.COLUMN_NAME_CONTENT_URI_FILTER + TEXT_TYPE + COMMA_SEP +
                    SearchDb.COLUMN_SUMMARY + TEXT_TYPE + COMMA_SEP +
                    SearchDb.COLUMN_NAME_UPDATED + " BOOLEAN" + COMMA_SEP +
                    SearchDb.COLUMN_NAME_NULLABLE + " BOOLEAN" +
            " )";

    SearchDbOpenHelper mDbHelper;

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + SearchDb.TABLE_NAME;

    public SearchDatabase() {

    }

    public SearchDatabase(Context context) {
        mDbHelper = new SearchDbOpenHelper(context);
    }

    public static abstract class SearchDb implements BaseColumns {
        public static final String TABLE_NAME = "mainMenuSearchingDb";
        public static final String COLUMN_ID = "menuCtName";
        public static final String COLUMN_NAME_ID = "menuId";
        public static final String COLUMN_NAME_TITLE = "menuTitle";
        public static final String COLUMN_NAME_CONTENT_DESCRIPTION = "menuSummary";
        public static final String COLUMN_NAME_CONTENT_URI = "menuUrl";
        public static final String COLUMN_NAME_CONTENT_URI_FILTER = "menuUrlFilter";
        public static final String COLUMN_SUMMARY = "description";
        public static final String COLUMN_NAME_NULLABLE = "nullable";
        public static final String COLUMN_NAME_UPDATED = "updated";
    }

    public class SearchDbOpenHelper extends SQLiteOpenHelper {
        // If you change the database schema, you must increment the database version.
        private final Context mHelperContext;
        private SQLiteDatabase mDatabase;
        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "SearchDb.db";

        public SearchDbOpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            mHelperContext = context;
        }

        public void onCreate(SQLiteDatabase db) {
            mDatabase = db;
            db.execSQL(SQL_DELETE_ENTRIES);
            db.execSQL(SQL_CREATE_ENTRIES);
            addWordsFromResources();
        }
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // This database is only a cache for online data, so its upgrade policy is
            // to simply to discard the data and start over
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL(SQL_DELETE_ENTRIES);
            onCreate(db);
        }
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onUpgrade(db, oldVersion, newVersion);
        }
        private void loadDictionary() {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        loadWordsFromFiles();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }).start();
        }

        private void loadWordsFromFiles() throws IOException {
            final Resources resources = mHelperContext.getResources();
          //  InputStream inputStream = resources.openRawResource(resources.getIdentifier("main_raw/searching/searchables.txt", "main_raw", mHelperContext.getPackageName()));
            InputStream inputStream = new BufferedInputStream(new FileInputStream("file://data/searchables.txt"));
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            try {
                String line;
                int columnId = 0;
                while ((line = reader.readLine()) != null) {
                    String[] strings = TextUtils.split(line, "-");
                    if (strings.length < 2) continue;
                    // long id = addWord(strings[0].trim(), strings[1].trim());
                    long id = putIntoDB(columnId++,strings);
                    if (id < 0) {
                        Log.e(TAG, "unable to add word: " + strings[0].trim());
                    }
                }
            } finally {
                reader.close();
            }
        }

        private void addWordsFromResources() {
            final Resources resources = mHelperContext.getResources();
            String[] searcDialogList = resources.getStringArray(R.array.search_dialog_list_view);
            // R.array.class.getField(searcDialogList[n]).getInt(null);
            // Iterate for all search menu items.
            String[] mListItem;
            String[] menuIds;
            String[] menuTitles;
            String[] menuSummarys;
            String[] webMenuDataFiles;
            String[] res = {"","","","",""};
            for(int id = 0, n = 0; n < searcDialogList.length; n++) {
                mListItem = resources.getStringArray(resources.getIdentifier(searcDialogList[n],"array", mHelperContext.getPackageName()));
                for (int dt = 0; dt < mListItem.length; dt++) {
                    menuIds = resources.getStringArray(resources.getIdentifier(mListItem[dt].concat("_id"), "array", mHelperContext.getPackageName()));
                    menuTitles = resources.getStringArray(resources.getIdentifier(mListItem[dt].concat("_titles"), "array", mHelperContext.getPackageName()));
                    menuSummarys = resources.getStringArray(resources.getIdentifier(mListItem[dt].concat("_summary"), "array", mHelperContext.getPackageName()));
                    webMenuDataFiles = resources.getStringArray(resources.getIdentifier(mListItem[dt].concat("_files"), "array", mHelperContext.getPackageName()));
                    for (int t = 0; t < menuTitles.length; t++) {
                        res[0] = mListItem[dt];
                        res[1] = menuIds[t];
                        res[2] = menuTitles[t];
                        res[3] = menuSummarys[t];
                        res[4] = webMenuDataFiles[t];
                        putIntoDB(id++,res);
                    }
                }
            }
        }

    }

    public long putIntoDB(int columnId, String[] searchMenuContent) {
        // Gets the data repository in write mode
        // SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(SearchDb._ID, columnId);
        values.put(SearchDb.COLUMN_ID, searchMenuContent[0]);
        values.put(SearchDb.COLUMN_NAME_ID, searchMenuContent[1]);
        values.put(SearchDb.COLUMN_NAME_TITLE, searchMenuContent[2]);
        values.put(SearchDb.COLUMN_NAME_CONTENT_DESCRIPTION, searchMenuContent[3]);
        values.put(SearchDb.COLUMN_NAME_CONTENT_URI, searchMenuContent[4]);
        values.put(SearchDb.COLUMN_NAME_CONTENT_URI_FILTER, "");
        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        long virtualRowId;
        newRowId = mDbHelper.mDatabase.insert(
                SearchDb.TABLE_NAME,
                SearchDb.COLUMN_NAME_NULLABLE,
                values);
        return newRowId;
    }

    public void execSelectQuery(String search, int type) {

        search = '%' + search + '%';
        String selectQuery = null;
        String[] selectionArgs = null;

        if (type == 0) {
            selectionArgs = new String[]{search, search};
            selectQuery = SearchDb.COLUMN_NAME_TITLE + " LIKE ? OR " + SearchDb.COLUMN_NAME_CONTENT_DESCRIPTION + " LIKE ?";
        } else if(type == 1) {
            selectionArgs = new String[]{search};
            selectQuery = SearchDb.COLUMN_NAME_TITLE + " LIKE ?";
        } else if (type == 2) {
            selectionArgs = new String[]{search};
            selectQuery = SearchDb.COLUMN_NAME_CONTENT_DESCRIPTION + " LIKE ?";
        }
        cursor = readFromDb(selectQuery, selectionArgs);

    }

    public Cursor readFromDb(String selection, String[] selectionArgs) {
         SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                SearchDb._ID,
                SearchDb.COLUMN_ID,
                SearchDb.COLUMN_NAME_ID,
                SearchDb.COLUMN_NAME_TITLE,
                SearchDb.COLUMN_NAME_CONTENT_DESCRIPTION,
                SearchDb.COLUMN_NAME_CONTENT_URI,
                SearchDb.COLUMN_NAME_CONTENT_URI_FILTER,
        };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                SearchDb.COLUMN_NAME_TITLE + " ASC";

        String groupBy = SearchDb.COLUMN_NAME_TITLE;

        Cursor cursor = db.query(
                SearchDb.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                groupBy,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        if (cursor == null) {
            return null;
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        return cursor;
    }

    public void deteleFromDB(long rowId, String table_name) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        // Define 'where' part of query.
        String selection = SearchDb.COLUMN_NAME_ID + " LIKE ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = { String.valueOf(rowId) };
        // Issue SQL statement.
        db.delete(table_name, selection, selectionArgs);
    }

    public void updateDb(long rowId, String title) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // New value for one column
        ContentValues values = new ContentValues();
        values.put(SearchDb.COLUMN_NAME_TITLE, title);

        // Which row to update, based on the ID
        String selection = SearchDb.COLUMN_NAME_ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(rowId) };

        int count = db.update(
                SearchDb.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }

}
