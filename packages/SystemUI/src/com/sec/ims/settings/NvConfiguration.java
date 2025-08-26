package com.sec.ims.settings;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import com.sec.ims.configuration.DATA;

/* loaded from: classes4.dex */
public class NvConfiguration {
    public static final String LOG_TAG = "NvConfiguration";
    public static final Uri URI = Uri.parse("content://com.sec.ims.settings/nvstorage/omadm/");

    private NvConfiguration() {
    }

    public static String get(Context context, String str, String str2) {
        Cursor cursorQuery = context.getContentResolver().query(URI, new String[]{str}, null, null, null);
        if (cursorQuery == null) {
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            return str2;
        }
        try {
            if (cursorQuery.moveToFirst()) {
                str2 = cursorQuery.getString(1);
            }
            cursorQuery.close();
            return str2;
        } finally {
        }
    }

    public static ContentValues getAll(Context context) {
        Cursor cursorQuery = context.getContentResolver().query(URI, null, null, null, null);
        ContentValues contentValues = null;
        if (cursorQuery == null) {
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            return null;
        }
        try {
            if (cursorQuery.moveToFirst()) {
                contentValues = new ContentValues();
                do {
                    contentValues.put(cursorQuery.getString(0), cursorQuery.getString(1));
                } while (cursorQuery.moveToNext());
            }
            cursorQuery.close();
            return contentValues;
        } finally {
        }
    }

    public static boolean getSmsIpNetworkIndi(Context context, int i) {
        try {
            return Integer.parseInt(get(context, DATA.DM_NODE.SMS_OVER_IMS, "1", i)) == 1;
        } catch (NumberFormatException unused) {
            return true;
        }
    }

    public static void insert(Context context, ContentValues contentValues) {
        context.getContentResolver().insert(URI, contentValues);
    }

    public static Cursor query(Context context, String[] strArr) {
        return context.getContentResolver().query(URI, strArr, null, null, null);
    }

    public static void set(Context context, String str, String str2, int i) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(str, str2);
        context.getContentResolver().insert(URI.buildUpon().fragment("simslot" + i).build(), contentValues);
    }

    public static void setSmsIpNetworkIndi(Context context, boolean z, int i) {
        set(context, DATA.DM_NODE.SMS_OVER_IMS, z ? "1" : "0", i);
    }

    public static String get(Context context, String str, String str2, int i) {
        Cursor cursorQuery = context.getContentResolver().query(URI.buildUpon().fragment("simslot" + i).build(), new String[]{str}, null, null, null);
        if (cursorQuery == null) {
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            return str2;
        }
        try {
            if (cursorQuery.moveToFirst()) {
                str2 = cursorQuery.getString(1);
            }
            cursorQuery.close();
            return str2;
        } finally {
        }
    }
}
