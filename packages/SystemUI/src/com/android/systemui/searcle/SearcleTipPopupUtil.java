package com.android.systemui.searcle;

import android.content.Context;
import com.android.systemui.Prefs;
import java.util.Arrays;
import java.util.Calendar;
import kotlin.jvm.internal.StringCompanionObject;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SearcleTipPopupUtil {
    public static final SearcleTipPopupUtil INSTANCE = new SearcleTipPopupUtil();

    private SearcleTipPopupUtil() {
    }

    public static int getSearcleTipCount(Context context) {
        return Prefs.getInt(context, "SearcleTipCount", 0);
    }

    public static String getTimeFormatString(long j) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(j);
        int i = StringCompanionObject.$r8$clinit;
        return String.format("%02d:%02d:%02d.%03d", Arrays.copyOf(new Object[]{Integer.valueOf(calendar.get(11)), Integer.valueOf(calendar.get(12)), Integer.valueOf(calendar.get(13)), Integer.valueOf(calendar.get(14))}, 4));
    }
}
