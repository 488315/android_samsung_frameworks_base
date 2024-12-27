package com.android.systemui.keyguard.shared.model;

import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class ClockSizeSetting {
    public static final /* synthetic */ ClockSizeSetting[] $VALUES;
    public static final Companion Companion;
    public static final ClockSizeSetting DYNAMIC;
    public static final ClockSizeSetting SMALL;
    public static final String TAG;
    private final int settingValue;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
