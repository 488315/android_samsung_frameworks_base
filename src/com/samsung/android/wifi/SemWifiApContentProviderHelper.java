package com.samsung.android.wifi;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.Binder;
import android.util.Log;
import com.samsung.android.wifi.ap.SemWifiApContentProvider;

/* loaded from: classes6.dex */
public class SemWifiApContentProviderHelper {
    public static final String KEY_AUTO_HOTSPOT_CONNECTED_USER = "auto_hotspot_connected_user";
    public static final String NAME = "name";
    private static String TAG = "SemWifiApContentProviderHelper";
    public static final String VALUE = "value";
    static final String URL = "content://com.samsung.android.wifi.softap/softapInfo";
    static final Uri CONTENT_URI = Uri.parse(URL);

    public static synchronized void insert(Context context, String str, String str2) {
        if (context.checkPermission(Manifest.permission.OVERRIDE_WIFI_CONFIG, -1, Binder.getCallingUid()) == 0) {
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            ContentValues contentValues = new ContentValues();
            if (str2 == null) {
                str2 = "";
            }
            contentValues.put("name", str);
            contentValues.put("value", str2);
            try {
                try {
                } catch (SQLiteException | IllegalStateException e) {
                    e.printStackTrace();
                    Log.e(TAG, "insert: exception");
                    SemWifiApContentProvider.reCreateDB();
                }
                if (isKeypresent(context, str)) {
                    context.getContentResolver().update(CONTENT_URI, contentValues, "name = ?", new String[]{str});
                } else {
                    context.getContentResolver().insert(CONTENT_URI, contentValues);
                    Log.i(TAG, "Inserting Key:" + str);
                }
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }
    }

    public static synchronized String get(Context context, String str) {
        long jClearCallingIdentity;
        if (context.checkPermission(Manifest.permission.OVERRIDE_WIFI_CONFIG, -1, Binder.getCallingUid()) != 0) {
            return "";
        }
        try {
            String string = "";
            String[] strArr = {str};
            jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                Cursor cursorQuery = context.getContentResolver().query(CONTENT_URI, null, "name = ?", strArr, null);
                if (cursorQuery != null) {
                    try {
                        if (cursorQuery.moveToFirst()) {
                            string = cursorQuery.getString(cursorQuery.getColumnIndex("value"));
                        }
                        cursorQuery.close();
                    } catch (Throwable th) {
                        cursorQuery.close();
                        throw th;
                    }
                }
            } catch (SQLiteException | IllegalStateException e) {
                e.printStackTrace();
                Log.e(TAG, "get: exception");
                SemWifiApContentProvider.reCreateDB();
            }
            Binder.restoreCallingIdentity(jClearCallingIdentity);
            return string;
        } catch (Throwable th2) {
            Binder.restoreCallingIdentity(jClearCallingIdentity);
            throw th2;
        }
    }

    private static synchronized boolean isKeypresent(Context context, String str) {
        boolean zMoveToFirst;
        String[] strArr = {str};
        zMoveToFirst = false;
        try {
            Cursor cursorQuery = context.getContentResolver().query(CONTENT_URI, null, "name = ?", strArr, null);
            if (cursorQuery != null) {
                try {
                    zMoveToFirst = cursorQuery.moveToFirst();
                    cursorQuery.close();
                } catch (Throwable th) {
                    cursorQuery.close();
                    throw th;
                }
            }
        } catch (SQLiteException | IllegalStateException e) {
            e.printStackTrace();
            Log.e(TAG, "isKeyPresent: exception");
            SemWifiApContentProvider.reCreateDB();
        }
        return zMoveToFirst;
    }
}
