package com.samsung.android.wifi.ap;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/* loaded from: classes6.dex */
public class SemWifiApContentProvider extends ContentProvider {
    static final String CREATE_DB_TABLE = " CREATE TABLE SemWifiApContentProvider (_id INTEGER PRIMARY KEY AUTOINCREMENT,  name TEXT NOT NULL,  value TEXT NOT NULL);";
    static final String DATABASE_NAME = "SemWifiApContentProvider.db";
    static final int DATABASE_VERSION = 1;
    static final String NAME = "name";
    static final String PROVIDER_NAME = "com.samsung.android.wifi.softap";
    static final int SOFTAPINFO = 1;
    static final int SOFTAPINFO_ID = 2;
    private static HashMap<String, String> SOFTAPINFO_PROJECTION_MAP = null;
    static final String SOFTAPINFO_TABLE_NAME = "SemWifiApContentProvider";
    private static final String TAG = "SemWifiApContentProvider";
    static final String VALUE = "value";
    static final String _ID = "_id";
    private static SQLiteDatabase db;
    private static DatabaseHelper dbHelper;
    private static Context mContext;
    private static List<String> mMHSDumpLogs;
    static final UriMatcher uriMatcher;
    static final String URL = "content://com.samsung.android.wifi.softap/softapInfo";
    static final Uri CONTENT_URI = Uri.parse(URL);

    static {
        UriMatcher uriMatcher2 = new UriMatcher(-1);
        uriMatcher = uriMatcher2;
        uriMatcher2.addURI(PROVIDER_NAME, "softapInfo", 1);
        uriMatcher2.addURI(PROVIDER_NAME, "softapInfo/#", 2);
        mMHSDumpLogs = new ArrayList();
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, SemWifiApContentProvider.DATABASE_NAME, (SQLiteDatabase.CursorFactory) null, 1);
            SemWifiApContentProvider.addMHSDumpLog("DatabaseHelper constructor");
            SemWifiApContentProvider.mContext = context;
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onCreate(SQLiteDatabase sQLiteDatabase) {
            SemWifiApContentProvider.addMHSDumpLog("DatabaseHelper onCreate");
            createTable(sQLiteDatabase);
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
            SemWifiApContentProvider.addMHSDumpLog("DatabaseHelper onUpgrade");
            dropTable(sQLiteDatabase);
            onCreate(sQLiteDatabase);
        }

        SQLiteDatabase getDatabase(boolean z) {
            if (z) {
                return getWritableDatabase();
            }
            return getReadableDatabase();
        }

        public void createTable(SQLiteDatabase sQLiteDatabase) {
            SemWifiApContentProvider.addMHSDumpLog("createTable");
            try {
                sQLiteDatabase.execSQL(SemWifiApContentProvider.CREATE_DB_TABLE);
            } catch (SQLException unused) {
                Log.e("SemWifiApContentProvider", "couldn't create table in  database");
                SemWifiApContentProvider.addMHSDumpLog("couldn't create table in  database");
            }
        }

        public void dropTable(SQLiteDatabase sQLiteDatabase) {
            SemWifiApContentProvider.addMHSDumpLog("dropTable");
            try {
                sQLiteDatabase.execSQL("DROP TABLE IF EXISTS SemWifiApContentProvider");
            } catch (SQLException unused) {
                Log.e("SemWifiApContentProvider", "couldn't drop table in  database");
                SemWifiApContentProvider.addMHSDumpLog("couldn't drop table in  database");
            }
        }
    }

    public SemWifiApContentProvider() {
        addMHSDumpLog("SemWifiApContentProvider constructor");
    }

    @Override // android.content.ContentProvider, android.content.ContentInterface
    public String getType(Uri uri) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override // android.content.ContentProvider
    public Uri insert(Uri uri, ContentValues contentValues) {
        long jInsert = db.insert("SemWifiApContentProvider", "", contentValues);
        if (jInsert > 0) {
            Uri uriWithAppendedId = ContentUris.withAppendedId(CONTENT_URI, jInsert);
            Log.i("SemWifiApContentProvider", "inserted" + uriWithAppendedId);
            return uriWithAppendedId;
        }
        Log.e("SemWifiApContentProvider", "Could not add" + uri);
        return null;
    }

    @Override // android.content.ContentProvider
    public boolean onCreate() {
        DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
        dbHelper = databaseHelper;
        db = databaseHelper.getWritableDatabase();
        StringBuilder sb = new StringBuilder("SemWifiApContentProvider onCreate,db created?");
        sb.append(db != null);
        addMHSDumpLog(sb.toString());
        return db != null;
    }

    @Override // android.content.ContentProvider
    public synchronized Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        SQLiteQueryBuilder sQLiteQueryBuilder;
        sQLiteQueryBuilder = new SQLiteQueryBuilder();
        sQLiteQueryBuilder.setTables("SemWifiApContentProvider");
        int iMatch = uriMatcher.match(uri);
        if (iMatch == 1) {
            sQLiteQueryBuilder.setProjectionMap(SOFTAPINFO_PROJECTION_MAP);
        } else if (iMatch == 2) {
            sQLiteQueryBuilder.appendWhere("_id=" + uri.getPathSegments().get(1));
        }
        return sQLiteQueryBuilder.query(db, strArr, str, strArr2, null, null, str2);
    }

    @Override // android.content.ContentProvider
    public synchronized int delete(Uri uri, String str, String[] strArr) {
        int iDelete;
        String str2;
        int iMatch = uriMatcher.match(uri);
        if (iMatch == 1) {
            iDelete = db.delete("SemWifiApContentProvider", str, strArr);
        } else if (iMatch == 2) {
            String str3 = uri.getPathSegments().get(1);
            SQLiteDatabase sQLiteDatabase = db;
            StringBuilder sb = new StringBuilder("_id = ");
            sb.append(str3);
            if (TextUtils.isEmpty(str)) {
                str2 = "";
            } else {
                str2 = " AND (" + str + ')';
            }
            sb.append(str2);
            iDelete = sQLiteDatabase.delete("SemWifiApContentProvider", sb.toString(), strArr);
        } else {
            Log.d("SemWifiApContentProvider", "delete Unknown URI " + uri);
            iDelete = 0;
        }
        return iDelete;
    }

    @Override // android.content.ContentProvider
    public synchronized int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        int iUpdate;
        String str2;
        int iMatch = uriMatcher.match(uri);
        if (iMatch == 1) {
            iUpdate = db.update("SemWifiApContentProvider", contentValues, str, strArr);
        } else if (iMatch == 2) {
            SQLiteDatabase sQLiteDatabase = db;
            StringBuilder sb = new StringBuilder("_id = ");
            sb.append(uri.getPathSegments().get(1));
            if (TextUtils.isEmpty(str)) {
                str2 = "";
            } else {
                str2 = " AND (" + str + ')';
            }
            sb.append(str2);
            iUpdate = sQLiteDatabase.update("SemWifiApContentProvider", contentValues, sb.toString(), strArr);
        } else {
            Log.e("SemWifiApContentProvider", "Could not update" + uri);
            return 0;
        }
        Log.i("SemWifiApContentProvider", "updated:" + uri);
        return iUpdate;
    }

    public static void insert(Context context, String str, String str2) {
        ContentValues contentValues = new ContentValues();
        if (str2 == null) {
            str2 = "";
        }
        contentValues.put("name", str);
        contentValues.put("value", str2);
        if (isKeypresent(context, str)) {
            context.getContentResolver().update(CONTENT_URI, contentValues, null, null);
        } else {
            context.getContentResolver().insert(CONTENT_URI, contentValues);
        }
    }

    public static String get(Context context, String str) {
        Cursor cursorQuery = context.getContentResolver().query(Uri.parse("content://com.samsung.android.wifi.softap"), null, "name = ?", new String[]{str}, null);
        String string = "";
        if (cursorQuery == null) {
            return "";
        }
        try {
            if (cursorQuery.moveToFirst()) {
                string = cursorQuery.getString(cursorQuery.getColumnIndex("value"));
            }
            return string;
        } finally {
            cursorQuery.close();
        }
    }

    private static boolean isKeypresent(Context context, String str) {
        Cursor cursorQuery = context.getContentResolver().query(Uri.parse("content://com.samsung.android.wifi.softap"), null, "name = ?", new String[]{str}, null);
        if (cursorQuery == null) {
            return false;
        }
        try {
            return cursorQuery.moveToFirst();
        } finally {
            cursorQuery.close();
        }
    }

    public static synchronized void reCreateDB() {
        File file;
        try {
            SQLiteDatabase sQLiteDatabase = db;
            if (sQLiteDatabase != null) {
                String path = sQLiteDatabase.getPath();
                Log.i("SemWifiApContentProvider", "reCreateDB: dbPath " + path);
                file = new File(path);
            } else {
                file = null;
            }
            if (db == null || !file.exists() || !db.isDatabaseIntegrityOk()) {
                addMHSDumpLog("databaseIntegrity is not Ok");
                mContext.deleteDatabase(DATABASE_NAME);
                SQLiteDatabase sQLiteDatabase2 = db;
                if (sQLiteDatabase2 != null && sQLiteDatabase2.isOpen()) {
                    addMHSDumpLog("databaseIntegrity is not Ok,closing DB");
                    db.close();
                }
                db = dbHelper.getWritableDatabase();
                StringBuilder sb = new StringBuilder("SemWifiApContentProvider query,db created?");
                sb.append(db != null);
                addMHSDumpLog(sb.toString());
            }
        } catch (SQLException | IllegalStateException e) {
            e.printStackTrace();
            Log.e("SemWifiApContentProvider", "reCreateDB: exception");
        }
    }

    public static synchronized void addMHSDumpLog(String str) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(new SimpleDateFormat("MM-dd HH:mm:ss.SSS", Locale.US).format(Long.valueOf(System.currentTimeMillis())) + " " + str + ShaderAssembler.NEWLINE);
        Log.i("SemWifiApContentProvider", str);
        if (mMHSDumpLogs.size() > 100) {
            mMHSDumpLogs.remove(0);
        }
        mMHSDumpLogs.add(stringBuffer.toString());
    }

    public static String getDumpLogs() {
        StringBuffer stringBuffer = new StringBuffer("====== SemWifiApContentProvider dump =======  \n");
        stringBuffer.append(mMHSDumpLogs.toString());
        stringBuffer.append(ShaderAssembler.NEWLINE);
        return stringBuffer.toString();
    }
}
