package com.android.systemui.util;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface EventLog {
    int writeEvent(int i, float f);

    int writeEvent(int i, int i2);

    int writeEvent(int i, long j);

    int writeEvent(int i, String str);

    int writeEvent(int i, Object... objArr);
}
