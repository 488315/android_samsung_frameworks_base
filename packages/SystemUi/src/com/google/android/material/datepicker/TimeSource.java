package com.google.android.material.datepicker;

import java.util.TimeZone;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class TimeSource {
    public static final TimeSource SYSTEM_TIME_SOURCE = new TimeSource(null, null);
    public final Long fixedTimeMs;
    public final TimeZone fixedTimeZone;

    private TimeSource(Long l, TimeZone timeZone) {
        this.fixedTimeMs = l;
        this.fixedTimeZone = timeZone;
    }
}
