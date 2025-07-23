package com.google.android.material.datepicker;

import java.util.TimeZone;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class TimeSource {
    public static final TimeSource SYSTEM_TIME_SOURCE = new TimeSource(null, null);
    public final Long fixedTimeMs;
    public final TimeZone fixedTimeZone;

    private TimeSource(Long l, TimeZone timeZone) {
        this.fixedTimeMs = l;
        this.fixedTimeZone = timeZone;
    }
}
