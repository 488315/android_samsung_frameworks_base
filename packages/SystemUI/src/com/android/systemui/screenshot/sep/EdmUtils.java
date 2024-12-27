package com.android.systemui.screenshot.sep;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import com.android.keyguard.KeyguardSecPatternView$$ExternalSyntheticOutline0;

public final class EdmUtils {
    public static boolean isScreenCaptureEnabled(Context context) {
        String[] strArr = {"false"};
        Uri parse = Uri.parse("content://com.sec.knox.provider/RestrictionPolicy3");
        int i = -1;
        i = -1;
        i = -1;
        char c = 65535;
        i = -1;
        i = -1;
        if (context == null) {
            Log.e("Screenshot", "getEnterprisePolicyEnabled: context is null");
        } else {
            Cursor query = context.getContentResolver().query(parse, null, "isScreenCaptureEnabled", strArr, null);
            try {
                if (query != null) {
                    try {
                        query.moveToFirst();
                        i = query.getString(query.getColumnIndex("isScreenCaptureEnabled")).equals("true");
                        query.close();
                    } finally {
                    }
                }
            } catch (Exception unused) {
            }
            KeyguardSecPatternView$$ExternalSyntheticOutline0.m(i, "projectionArgs:isScreenCaptureEnabled/", "Screenshot");
            c = i;
        }
        return c != 0;
    }
}
