package com.android.systemui.media.taptotransfer.sender;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
