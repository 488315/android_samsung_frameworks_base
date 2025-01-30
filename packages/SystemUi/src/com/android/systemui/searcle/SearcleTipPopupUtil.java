package com.android.systemui.searcle;

import java.util.Arrays;
import java.util.Calendar;
import kotlin.jvm.internal.StringCompanionObject;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SearcleTipPopupUtil {
    public static final SearcleTipPopupUtil INSTANCE = new SearcleTipPopupUtil();

    private SearcleTipPopupUtil() {
    }

    public static String getTimeFormatString(long j) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(j);
        int i = StringCompanionObject.$r8$clinit;
        return String.format("%02d:%02d:%02d.%03d", Arrays.copyOf(new Object[]{Integer.valueOf(calendar.get(11)), Integer.valueOf(calendar.get(12)), Integer.valueOf(calendar.get(13)), Integer.valueOf(calendar.get(14))}, 4));
    }
}
