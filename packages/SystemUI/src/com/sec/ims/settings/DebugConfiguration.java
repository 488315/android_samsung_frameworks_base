package com.sec.ims.settings;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

/* loaded from: classes4.dex */
public class DebugConfiguration {
    public static final String FAKE_PANI = "fake_pani";
    public static final String FAKE_REG_RESPONSE = "fake_reg_response";
    public static final String REAL_PANI = "real_pani";
    private static final String URIString = "content://com.sec.ims.settings/debugconfig/";

    private DebugConfiguration() {
    }

    public static String getDebugConfig(Context context, int i, String str, String str2) {
        Cursor cursorQuery = context.getContentResolver().query(Uri.parse(URIString + i), new String[]{str}, null, null, null);
        if (cursorQuery != null) {
            try {
                if (cursorQuery.moveToFirst()) {
                    str2 = cursorQuery.getString(0);
                }
            } finally {
            }
        }
        if (cursorQuery != null) {
            cursorQuery.close();
        }
        return str2;
    }

    public static void setDebugConfig(Context context, int i, String str, String str2) {
        Uri uri = Uri.parse(URIString + i);
        ContentValues contentValues = new ContentValues();
        contentValues.put(str, str2);
        context.getContentResolver().insert(uri, contentValues);
    }

    public static void setDebugConfig(Context context, int i, String str, int i2) {
        Uri uri = Uri.parse(URIString + i);
        ContentValues contentValues = new ContentValues();
        contentValues.put(str, Integer.valueOf(i2));
        context.getContentResolver().insert(uri, contentValues);
    }

    public static int getDebugConfig(Context context, int i, String str, int i2) {
        try {
            return Integer.parseInt(getDebugConfig(context, i, str, ""));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return i2;
        }
    }

    public static boolean getDebugConfig(Context context, int i, String str, boolean z) {
        Cursor cursorQuery = context.getContentResolver().query(Uri.parse(URIString + i), new String[]{str}, null, null, null);
        if (cursorQuery != null) {
            try {
                if (cursorQuery.moveToFirst()) {
                    z = "true".equals(cursorQuery.getString(0));
                }
            } finally {
            }
        }
        if (cursorQuery != null) {
            cursorQuery.close();
        }
        return z;
    }

    public static void setDebugConfig(Context context, int i, String str, boolean z) {
        Uri uri = Uri.parse(URIString + i);
        ContentValues contentValues = new ContentValues();
        contentValues.put(str, z ? "true" : "false");
        context.getContentResolver().insert(uri, contentValues);
    }
}
