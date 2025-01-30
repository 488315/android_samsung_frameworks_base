package com.sec.android.diagmonagent.log.ged.util;

import android.content.Context;
import android.content.SharedPreferences;
import com.sec.android.diagmonagent.common.logger.AppLog;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class PreferenceUtils {
    public static String getDiagmonPreference(Context context, String str, String str2) {
        return context.getSharedPreferences("DIAGMON_PREFERENCE", 0).getString(str, str2);
    }

    public static void setDiagmonPreference(Context context, String str, String str2) {
        if (str2 == null) {
            AppLog.m269d(str.concat(" - value is null"));
            return;
        }
        SharedPreferences.Editor edit = context.getSharedPreferences("DIAGMON_PREFERENCE", 0).edit();
        edit.putString(str, str2);
        edit.apply();
    }
}
