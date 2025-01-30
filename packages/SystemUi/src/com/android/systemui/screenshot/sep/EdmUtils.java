package com.android.systemui.screenshot.sep;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import androidx.picker3.widget.SeslColorSpectrumView$$ExternalSyntheticOutline0;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class EdmUtils {
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r8v0 */
    /* JADX WARN: Type inference failed for: r8v1 */
    /* JADX WARN: Type inference failed for: r8v2, types: [int] */
    /* JADX WARN: Type inference failed for: r8v4 */
    public static boolean isScreenCaptureEnabled(Context context) {
        String[] strArr = {"false"};
        Uri parse = Uri.parse("content://com.sec.knox.provider/RestrictionPolicy3");
        ?? r8 = -1;
        char c = 65535;
        r8 = -1;
        if (context == null) {
            Log.e("Screenshot", "getEnterprisePolicyEnabled: context is null");
        } else {
            Cursor query = context.getContentResolver().query(parse, null, "isScreenCaptureEnabled", strArr, null);
            if (query != null) {
                try {
                    query.moveToFirst();
                    boolean equals = query.getString(query.getColumnIndex("isScreenCaptureEnabled")).equals("true");
                    try {
                        query.close();
                    } catch (Exception unused) {
                    }
                    r8 = equals;
                } catch (Throwable th) {
                    try {
                        try {
                            query.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                        throw th;
                    } catch (Exception unused2) {
                    }
                }
            }
            SeslColorSpectrumView$$ExternalSyntheticOutline0.m43m("projectionArgs:isScreenCaptureEnabled/", (int) r8, "Screenshot");
            c = r8;
        }
        return c != 0;
    }
}
