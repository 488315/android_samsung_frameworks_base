package com.android.systemui.keyguard.shared.model;

import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;

public final class ClockSize {
    public static final /* synthetic */ ClockSize[] $VALUES;
    public static final ClockSize LARGE;
    public static final ClockSize SMALL;
    private final int legacyValue;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        ClockSize clockSize = new ClockSize("SMALL", 0, 1);
        SMALL = clockSize;
        ClockSize clockSize2 = new ClockSize("LARGE", 1, 0);
        LARGE = clockSize2;
        ClockSize[] clockSizeArr = {clockSize, clockSize2};
        $VALUES = clockSizeArr;
        EnumEntriesKt.enumEntries(clockSizeArr);
        new Companion(null);
        Intrinsics.checkNotNull(Reflection.getOrCreateKotlinClass(ClockSize.class).getSimpleName());
    }

    private ClockSize(String str, int i, int i2) {
        this.legacyValue = i2;
    }

    public static ClockSize valueOf(String str) {
        return (ClockSize) Enum.valueOf(ClockSize.class, str);
    }

    public static ClockSize[] values() {
        return (ClockSize[]) $VALUES.clone();
    }
}
