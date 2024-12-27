package com.android.systemui.media.taptotransfer.sender;

import kotlin.enums.EnumEntriesKt;

public final class TimeoutLength {
    public static final /* synthetic */ TimeoutLength[] $VALUES;
    public static final TimeoutLength DEFAULT;
    public static final TimeoutLength LONG;

    static {
        TimeoutLength timeoutLength = new TimeoutLength("DEFAULT", 0);
        DEFAULT = timeoutLength;
        TimeoutLength timeoutLength2 = new TimeoutLength("LONG", 1);
        LONG = timeoutLength2;
        TimeoutLength[] timeoutLengthArr = {timeoutLength, timeoutLength2};
        $VALUES = timeoutLengthArr;
        EnumEntriesKt.enumEntries(timeoutLengthArr);
    }

    private TimeoutLength(String str, int i) {
    }

    public static TimeoutLength valueOf(String str) {
        return (TimeoutLength) Enum.valueOf(TimeoutLength.class, str);
    }

    public static TimeoutLength[] values() {
        return (TimeoutLength[]) $VALUES.clone();
    }
}
