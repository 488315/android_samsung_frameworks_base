package com.android.systemui.screenshot.sep;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import com.android.keyguard.ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0;

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
        Uri uri = Uri.parse("content://com.sec.knox.provider/RestrictionPolicy3");
        int iEquals = -1;
        iEquals = -1;
        iEquals = -1;
        char c = 65535;
        iEquals = -1;
        iEquals = -1;
        if (context == null) {
            Log.e("Screenshot", "getEnterprisePolicyEnabled: context is null");
        } else {
            Cursor cursorQuery = context.getContentResolver().query(uri, null, "isScreenCaptureEnabled", strArr, null);
            try {
                if (cursorQuery != null) {
                    try {
                        cursorQuery.moveToFirst();
                        iEquals = cursorQuery.getString(cursorQuery.getColumnIndex("isScreenCaptureEnabled")).equals("true");
                        cursorQuery.close();
                    } finally {
                    }
                }
            } catch (Exception unused) {
            }
            ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(iEquals, "projectionArgs:isScreenCaptureEnabled/", "Screenshot");
            c = iEquals;
        }
        return c != 0;
    }
}
