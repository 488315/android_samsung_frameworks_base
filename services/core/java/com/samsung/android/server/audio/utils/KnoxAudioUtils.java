package com.samsung.android.server.audio.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

/* loaded from: classes2.dex */
public abstract class KnoxAudioUtils {
    public static boolean isRestrictedHeadphone(Context context) {
        int columnIndex;
        try {
            Cursor query = context.getContentResolver().query(Uri.parse("content://com.sec.knox.provider/RestrictionPolicy"), null, "isHeadPhoneEnabled", new String[]{"true"}, null);
            if (query != null) {
                try {
                    if (query.moveToFirst() && (columnIndex = query.getColumnIndex("isHeadPhoneEnabled")) >= 0 && TextUtils.equals("false", query.getString(columnIndex))) {
                        Log.v("AS.KnoxAudioUtils", "Headset disabled");
                        query.close();
                        return true;
                    }
                } finally {
                }
            }
            if (query != null) {
                query.close();
            }
        } catch (Exception e) {
            Log.e("AS.KnoxAudioUtils", "isRestrictedHeadphone throws " + e.getMessage());
        }
        return false;
    }
}
