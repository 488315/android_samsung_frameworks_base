package com.android.systemui.keyguard.shared.model;

import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;

public final class ClockSizeSetting {
    public static final /* synthetic */ ClockSizeSetting[] $VALUES;
    public static final Companion Companion;
    public static final ClockSizeSetting DYNAMIC;
    public static final ClockSizeSetting SMALL;
    public static final String TAG;
    private final int settingValue;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        ClockSizeSetting clockSizeSetting = new ClockSizeSetting("DYNAMIC", 0, 1);
        DYNAMIC = clockSizeSetting;
        ClockSizeSetting clockSizeSetting2 = new ClockSizeSetting("SMALL", 1, 0);
        SMALL = clockSizeSetting2;
        ClockSizeSetting[] clockSizeSettingArr = {clockSizeSetting, clockSizeSetting2};
        $VALUES = clockSizeSettingArr;
        EnumEntriesKt.enumEntries(clockSizeSettingArr);
        Companion = new Companion(null);
        String simpleName = Reflection.getOrCreateKotlinClass(ClockSizeSetting.class).getSimpleName();
        Intrinsics.checkNotNull(simpleName);
        TAG = simpleName;
    }

    private ClockSizeSetting(String str, int i, int i2) {
        this.settingValue = i2;
    }

    public static ClockSizeSetting valueOf(String str) {
        return (ClockSizeSetting) Enum.valueOf(ClockSizeSetting.class, str);
    }

    public static ClockSizeSetting[] values() {
        return (ClockSizeSetting[]) $VALUES.clone();
    }

    public final int getSettingValue() {
        return this.settingValue;
    }
}
