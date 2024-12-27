package com.android.systemui.searcle;

import android.content.Context;
import com.android.systemui.Prefs;
import java.util.Arrays;
import java.util.Calendar;
import kotlin.jvm.internal.StringCompanionObject;

public final class SearcleTipPopupUtil {
    public static final SearcleTipPopupUtil INSTANCE = new SearcleTipPopupUtil();

    private SearcleTipPopupUtil() {
    }

    public static int getSearcleTipCount(Context context) {
        return Prefs.get(context).getInt("SearcleTipCount", 0);
    }

    public static String getTimeFormatString(long j) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(j);
        int i = StringCompanionObject.$r8$clinit;
        return String.format("%02d:%02d:%02d.%03d", Arrays.copyOf(new Object[]{Integer.valueOf(calendar.get(11)), Integer.valueOf(calendar.get(12)), Integer.valueOf(calendar.get(13)), Integer.valueOf(calendar.get(14))}, 4));
    }
}
