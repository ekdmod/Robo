package org.webmenu.level11.Search;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
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
 * Created by EK on 15.1.23.
 */
public final class SearchDatabaseFts4 {

    private static final String TAG = "DictionaryDatabase";

    //The columns we'll include in the dictionary table
    public static final String COL_WORD = "WORD";
    public static final String COL_DEFINITION = "DEFINITION";

    /**
     * The authority we use to get to our sample provider.
     */

    private static final String DATABASE_NAME = "DICTIONARY";
    private static final String FTS_VIRTUAL_TABLE = "FTS";
    private static final String FTS_SEARCH_TABLE = "SearchFTS4";
    private static final int DATABASE_VERSION = 1;
    public Cursor result;
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

    public SearchDatabaseFts4() {

    }

    public SearchDatabaseFts4(Context context) {
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

        private static final String FTS_TABLE_CREATE =
                "CREATE VIRTUAL TABLE " + FTS_VIRTUAL_TABLE +
                        " USING fts3 (" +
                        COL_WORD + ", " +
                        COL_DEFINITION + ")";

        private static final String FTS_TABLE_CREATE_2 = "CREATE VIRTUAL TABLE " + FTS_SEARCH_TABLE + " USING fts4 (" +
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

        public SearchDbOpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            mHelperContext = context;
        }
        public void onCreate(SQLiteDatabase db) {
            mDatabase = db;
            db.execSQL(SQL_CREATE_ENTRIES);
            loadWordsFromResources();
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
                while ((line = reader.readLine()) != null) {
                    String[] strings = TextUtils.split(line, "-");
                    if (strings.length < 2) continue;
                    long id = addWord(strings[0].trim(), strings[1].trim());
                    if (id < 0) {
                        Log.e(TAG, "unable to add word: " + strings[0].trim());
                    }
                }
            } finally {
                reader.close();
            }
        }

        private void loadWordsFromResources() {
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
                String item = searcDialogList[n];
                // id = resources.getIdentifier(searcDialogList[n],"array", mHelperContext.getPackageName());
                mListItem = resources.getStringArray(resources.getIdentifier(searcDialogList[n],"array", mHelperContext.getPackageName()));
                for (int it = 0; it < mListItem.length; it++) {
                    menuIds = resources.getStringArray(resources.getIdentifier(mListItem[it].concat("_id"), "array", mHelperContext.getPackageName()));
                    menuTitles = resources.getStringArray(resources.getIdentifier(mListItem[it].concat("_titles"), "array", mHelperContext.getPackageName()));
                    menuSummarys = resources.getStringArray(resources.getIdentifier(mListItem[it].concat("_summary"), "array", mHelperContext.getPackageName()));
                    webMenuDataFiles = resources.getStringArray(resources.getIdentifier(mListItem[it].concat("_files"), "array", mHelperContext.getPackageName()));
                    for (int m = 0; m < menuIds.length; m++) {
                        res[0] = mListItem[it];
                        res[1] = menuIds[m];
                        res[2] = menuTitles[m];
                        res[3] = menuSummarys[m];
                        res[4] = webMenuDataFiles[m];
                        putIntoDB(id++,res);
                    }
                }
            }
        }

        public long addWord(String word, String definition) {
            ContentValues initialValues = new ContentValues();
            initialValues.put(COL_WORD, word);
            initialValues.put(COL_DEFINITION, definition);

            return mDatabase.insert(FTS_VIRTUAL_TABLE, null, initialValues);
        }
    }

    public void putIntoDB(int columnId, String[] searchMenuContent) {
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
        values.put(SearchDb.COLUMN_NAME_CONTENT_URI_FILTER, "webDataItem");
        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        long virtualRowId;
        newRowId = mDbHelper.mDatabase.insert(
                SearchDb.TABLE_NAME,
                SearchDb.COLUMN_NAME_NULLABLE,
                values);
        virtualRowId = mDbHelper.mDatabase.insert(
                FTS_SEARCH_TABLE,
                SearchDb.COLUMN_NAME_NULLABLE,
                values);
    }

    public void execSelectQuery(String search, int type) {

        search = '%' + search + '%';
        String selectQuery = null;
        String[] selectionArgs = null;

        switch(type) {
            case 0:
                selectionArgs = new String[]{search, search};
                selectQuery = SearchDb.COLUMN_NAME_TITLE + " LIKE ? OR " + SearchDb.COLUMN_NAME_CONTENT_DESCRIPTION + " LIKE ?";
            break;
            case 1:
                selectionArgs = new String[]{search, search};
                selectQuery = SearchDb.COLUMN_NAME_TITLE + " MATCH ? OR " + SearchDb.COLUMN_NAME_CONTENT_DESCRIPTION + " MATCH ?";
            break;
            case 2:
                selectionArgs = new String[]{search};
                selectQuery = SearchDb.COLUMN_NAME_TITLE + " LIKE ?";
            break;
            case 3:
                selectionArgs = new String[]{search};
                selectQuery = SearchDb.COLUMN_NAME_TITLE + " MATCH ?";
            break;
            case 4:
                selectionArgs = new String[]{search};
                selectQuery = SearchDb.COLUMN_NAME_CONTENT_DESCRIPTION + " LIKE ?";
            break;
            case 5:
                selectionArgs = new String[]{search};
                selectQuery = SearchDb.COLUMN_NAME_CONTENT_DESCRIPTION + " MATCH ?";
            break;
        }

        result = readFromDb(type, selectQuery, selectionArgs);

    }

    public Cursor readFromDb(int type, String selection, String[] selectionArgs) {
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
                SearchDb.COLUMN_NAME_UPDATED,
        };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                SearchDb.COLUMN_NAME_UPDATED + " DESC";

        Cursor cursor = db.query(
                SearchDb.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
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

    public Cursor getWordMatches(String query, String[] columns) {
        String selection = COL_WORD + " MATCH ?";
        String[] selectionArgs = new String[] {query+"*"};

        return query(selection, selectionArgs, columns);
    }

    private Cursor query(String selection, String[] selectionArgs, String[] columns) {
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(FTS_VIRTUAL_TABLE);

        Cursor cursor = builder.query(mDbHelper.getReadableDatabase(),
                columns, selection, selectionArgs, null, null, null);

        if (cursor == null) {
            return null;
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        return cursor;
    }
}
