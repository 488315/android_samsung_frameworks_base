package com.sec.ims.settings;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import com.sec.ims.configuration.DATA;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class NvConfiguration {
    public static final String LOG_TAG = "NvConfiguration";
    public static final Uri URI = Uri.parse("content://com.sec.ims.settings/nvstorage/omadm/");

    private NvConfiguration() {
    }

    public static String get(Context context, String str, String str2) {
        Cursor query = context.getContentResolver().query(URI, new String[]{str}, null, null, null);
        if (query == null) {
            if (query != null) {
                query.close();
            }
            return str2;
        }
        try {
            if (query.moveToFirst()) {
                str2 = query.getString(1);
            }
            query.close();
            return str2;
        } finally {
        }
    }

    public static ContentValues getAll(Context context) {
        Cursor query = context.getContentResolver().query(URI, null, null, null, null);
        ContentValues contentValues = null;
        if (query == null) {
            if (query != null) {
                query.close();
            }
            return null;
        }
        try {
            if (query.moveToFirst()) {
                contentValues = new ContentValues();
                do {
                    contentValues.put(query.getString(0), query.getString(1));
                } while (query.moveToNext());
            }
            query.close();
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
        Cursor query = context.getContentResolver().query(URI.buildUpon().fragment("simslot" + i).build(), new String[]{str}, null, null, null);
        if (query == null) {
            if (query != null) {
                query.close();
            }
            return str2;
        }
        try {
            if (query.moveToFirst()) {
                str2 = query.getString(1);
            }
            query.close();
            return str2;
        } finally {
        }
    }
}
