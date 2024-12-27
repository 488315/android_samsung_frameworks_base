package com.android.systemui.util;

public interface EventLog {
    int writeEvent(int i, float f);

    int writeEvent(int i, int i2);

    int writeEvent(int i, long j);

    int writeEvent(int i, String str);

    int writeEvent(int i, Object... objArr);
}
