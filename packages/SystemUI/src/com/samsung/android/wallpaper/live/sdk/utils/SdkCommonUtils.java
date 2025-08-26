package com.samsung.android.wallpaper.live.sdk.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import com.samsung.android.knox.SemPersonaManager;

/* loaded from: classes4.dex */
public class SdkCommonUtils {
    public static String dumpBundleToString(Bundle bundle) {
        if (bundle == null) {
            return "null";
        }
        if (bundle.isEmpty()) {
            return "empty";
        }
        StringBuilder sb = new StringBuilder();
        for (String str : bundle.keySet()) {
            sb.append(str);
            sb.append("=");
            Object obj = bundle.get(str);
            if (obj instanceof Bundle) {
                sb.append("[");
                sb.append(dumpBundleToString((Bundle) obj));
                sb.append("]");
            } else {
                sb.append(obj);
            }
            sb.append(", ");
        }
        String string = sb.toString();
        return string.endsWith(", ") ? string.substring(0, string.length() - 2) : string;
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0031  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x003f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static int getFocusedUserId(Context context) {
        int focusedKnoxId;
        try {
            focusedKnoxId = ActivityManager.semGetCurrentUser();
            if (focusedKnoxId != 0) {
                try {
                    SdkLog.d("SdkCommonUtils", "getFocusedUserId : " + focusedKnoxId);
                } catch (SecurityException e) {
                    e = e;
                    SdkLog.e("SdkCommonUtils", "getFocusedUserId : e=" + e, e);
                    if (focusedKnoxId == 0) {
                    }
                    if (focusedKnoxId != 0) {
                    }
                    return focusedKnoxId;
                }
            }
        } catch (SecurityException e2) {
            e = e2;
            focusedKnoxId = 0;
        }
        if (focusedKnoxId == 0) {
            focusedKnoxId = ((SemPersonaManager) context.getSystemService("persona")).getFocusedKnoxId();
        }
        if (focusedKnoxId != 0) {
            SdkLog.d("SdkCommonUtils", "getFocusedUserId : id = " + focusedKnoxId);
        }
        return focusedKnoxId;
    }
}
