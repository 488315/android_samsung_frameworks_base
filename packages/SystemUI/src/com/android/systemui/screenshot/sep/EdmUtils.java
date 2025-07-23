package com.android.systemui.screenshot.sep;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import com.android.keyguard.ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class EdmUtils {
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r8v0 */
    /* JADX WARN: Type inference failed for: r8v1 */
    /* JADX WARN: Type inference failed for: r8v10 */
    /* JADX WARN: Type inference failed for: r8v4, types: [boolean] */
    /* JADX WARN: Type inference failed for: r8v6 */
    /* JADX WARN: Type inference failed for: r8v7 */
    /* JADX WARN: Type inference failed for: r8v9 */
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
            ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(i, "projectionArgs:isScreenCaptureEnabled/", "Screenshot");
            c = i;
        }
        return c != 0;
    }
}
