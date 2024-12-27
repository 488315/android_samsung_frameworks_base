package com.android.systemui.screenshot.sep;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import com.android.keyguard.KeyguardSecPatternView$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class EdmUtils {
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r7v0 */
    /* JADX WARN: Type inference failed for: r7v1 */
    /* JADX WARN: Type inference failed for: r7v10 */
    /* JADX WARN: Type inference failed for: r7v4, types: [boolean] */
    /* JADX WARN: Type inference failed for: r7v6 */
    /* JADX WARN: Type inference failed for: r7v7 */
    /* JADX WARN: Type inference failed for: r7v9 */
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
