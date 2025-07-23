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
        synchronized (SemWifiApContentProviderHelper.class) {
            if (context.checkPermission(Manifest.permission.OVERRIDE_WIFI_CONFIG, -1, Binder.getCallingUid()) == 0) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
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
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }
    }

    public static synchronized String get(Context context, String str) {
        long clearCallingIdentity;
        synchronized (SemWifiApContentProviderHelper.class) {
            if (context.checkPermission(Manifest.permission.OVERRIDE_WIFI_CONFIG, -1, Binder.getCallingUid()) != 0) {
                return "";
            }
            try {
                String str2 = "";
                String[] strArr = {str};
                clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    Cursor query = context.getContentResolver().query(CONTENT_URI, null, "name = ?", strArr, null);
                    if (query != null) {
                        try {
                            if (query.moveToFirst()) {
                                str2 = query.getString(query.getColumnIndex("value"));
                            }
                            query.close();
                        } catch (Throwable th) {
                            query.close();
                            throw th;
                        }
                    }
                } catch (SQLiteException | IllegalStateException e) {
                    e.printStackTrace();
                    Log.e(TAG, "get: exception");
                    SemWifiApContentProvider.reCreateDB();
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return str2;
            } catch (Throwable th2) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th2;
            }
        }
    }

    private static synchronized boolean isKeypresent(Context context, String str) {
        boolean z;
        synchronized (SemWifiApContentProviderHelper.class) {
            String[] strArr = {str};
            z = false;
            try {
                Cursor query = context.getContentResolver().query(CONTENT_URI, null, "name = ?", strArr, null);
                if (query != null) {
                    try {
                        z = query.moveToFirst();
                        query.close();
                    } catch (Throwable th) {
                        query.close();
                        throw th;
                    }
                }
            } catch (SQLiteException | IllegalStateException e) {
                e.printStackTrace();
                Log.e(TAG, "isKeyPresent: exception");
                SemWifiApContentProvider.reCreateDB();
            }
        }
        return z;
    }
}
