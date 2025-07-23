package com.android.systemui.keyguard.shared.model;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ClockSize {
    public static final /* synthetic */ ClockSize[] $VALUES;
    public static final ClockSize LARGE;
    public static final ClockSize SMALL;

    static {
        ClockSize clockSize = new ClockSize("SMALL", 0);
        SMALL = clockSize;
        ClockSize clockSize2 = new ClockSize("LARGE", 1);
        LARGE = clockSize2;
        ClockSize[] clockSizeArr = {clockSize, clockSize2};
        $VALUES = clockSizeArr;
        EnumEntriesKt.enumEntries(clockSizeArr);
    }

    private ClockSize(String str, int i) {
    }

    public static ClockSize valueOf(String str) {
        return (ClockSize) Enum.valueOf(ClockSize.class, str);
    }

    public static ClockSize[] values() {
        return (ClockSize[]) $VALUES.clone();
    }
}
