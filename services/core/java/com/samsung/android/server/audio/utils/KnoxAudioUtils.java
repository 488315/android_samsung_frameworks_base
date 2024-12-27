package com.samsung.android.server.audio.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.android.server.RCPManagerService$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class KnoxAudioUtils {
    public static boolean isRestrictedHeadphone(Context context) {
        int columnIndex;
        try {
            Cursor query =
                    context.getContentResolver()
                            .query(
                                    Uri.parse("content://com.sec.knox.provider/RestrictionPolicy"),
                                    null,
                                    "isHeadPhoneEnabled",
                                    new String[] {"true"},
                                    null);
            if (query != null) {
                try {
                    if (query.moveToFirst()
                            && (columnIndex = query.getColumnIndex("isHeadPhoneEnabled")) >= 0
                            && TextUtils.equals("false", query.getString(columnIndex))) {
                        Log.v("AS.KnoxAudioUtils", "Headset disabled");
                        query.close();
                        return true;
                    }
                } finally {
                }
            }
            if (query == null) {
                return false;
            }
            query.close();
            return false;
        } catch (Exception e) {
            RCPManagerService$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("isRestrictedHeadphone throws "), "AS.KnoxAudioUtils");
            return false;
        }
    }
}
