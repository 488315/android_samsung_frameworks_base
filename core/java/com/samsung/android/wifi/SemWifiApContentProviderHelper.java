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

public class SemWifiApContentProviderHelper {
    public static final String KEY_AUTO_HOTSPOT_CONNECTED_USER = "auto_hotspot_connected_user";
    public static final String NAME = "name";
    public static final String VALUE = "value";
    private static String TAG = "SemWifiApContentProviderHelper";
    static final String URL = "content://com.samsung.android.wifi.softap/softapInfo";
    static final Uri CONTENT_URI = Uri.parse(URL);

    public static synchronized void insert(Context mContext, String key, String val) {
        synchronized (SemWifiApContentProviderHelper.class) {
            boolean hasPermission =
                    mContext.checkPermission(
                                    Manifest.permission.OVERRIDE_WIFI_CONFIG,
                                    -1,
                                    Binder.getCallingUid())
                            == 0;
            if (hasPermission) {
                long ident = Binder.clearCallingIdentity();
                ContentValues values = new ContentValues();
                if (val == null) {
                    val = "";
                }
                values.put("name", key);
                values.put("value", val);
                try {
                } catch (SQLiteException | IllegalStateException e) {
                    e.printStackTrace();
                    Log.e(TAG, "insert: exception");
                    SemWifiApContentProvider.reCreateDB();
                } finally {
                    Binder.restoreCallingIdentity(ident);
                }
                if (isKeypresent(mContext, key)) {
                    String[] selectionArgs = {key};
                    mContext.getContentResolver()
                            .update(CONTENT_URI, values, "name = ?", selectionArgs);
                } else {
                    mContext.getContentResolver().insert(CONTENT_URI, values);
                    Log.i(TAG, "Inserting Key:" + key);
                }
            }
        }
    }

    public static synchronized String get(Context mContext, String key) {
        String returnValue;
        synchronized (SemWifiApContentProviderHelper.class) {
            boolean hasPermission =
                    mContext.checkPermission(
                                    Manifest.permission.OVERRIDE_WIFI_CONFIG,
                                    -1,
                                    Binder.getCallingUid())
                            == 0;
            if (!hasPermission) {
                return "";
            }
            returnValue = "";
            String[] selectionArgs = {key};
            long ident = Binder.clearCallingIdentity();
            try {
                try {
                    Cursor c =
                            mContext.getContentResolver()
                                    .query(CONTENT_URI, null, "name = ?", selectionArgs, null);
                    if (c != null) {
                        try {
                            returnValue =
                                    c.moveToFirst() ? c.getString(c.getColumnIndex("value")) : "";
                            c.close();
                        } catch (Throwable th) {
                            c.close();
                            throw th;
                        }
                    }
                } catch (SQLiteException | IllegalStateException e) {
                    e.printStackTrace();
                    Log.e(TAG, "get: exception");
                    SemWifiApContentProvider.reCreateDB();
                    Binder.restoreCallingIdentity(ident);
                }
                return returnValue;
            } finally {
                Binder.restoreCallingIdentity(ident);
            }
        }
    }

    private static synchronized boolean isKeypresent(Context mContext, String key) {
        boolean ret;
        synchronized (SemWifiApContentProviderHelper.class) {
            String[] selectionArgs = {key};
            ret = false;
            try {
                Cursor c =
                        mContext.getContentResolver()
                                .query(CONTENT_URI, null, "name = ?", selectionArgs, null);
                if (c != null) {
                    try {
                        ret = c.moveToFirst();
                        c.close();
                    } catch (Throwable th) {
                        c.close();
                        throw th;
                    }
                }
            } catch (SQLiteException | IllegalStateException e) {
                e.printStackTrace();
                Log.e(TAG, "isKeyPresent: exception");
                SemWifiApContentProvider.reCreateDB();
            }
        }
        return ret;
    }
}
