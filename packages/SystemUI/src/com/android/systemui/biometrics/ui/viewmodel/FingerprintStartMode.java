package com.android.systemui.biometrics.ui.viewmodel;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
public final class FingerprintStartMode {
    public static final /* synthetic */ FingerprintStartMode[] $VALUES;
    public static final FingerprintStartMode Delayed;
    public static final FingerprintStartMode Normal;
    public static final FingerprintStartMode Pending;

    static {
        FingerprintStartMode fingerprintStartMode = new FingerprintStartMode("Pending", 0);
        Pending = fingerprintStartMode;
        FingerprintStartMode fingerprintStartMode2 = new FingerprintStartMode("Normal", 1);
        Normal = fingerprintStartMode2;
        FingerprintStartMode fingerprintStartMode3 = new FingerprintStartMode("Delayed", 2);
        Delayed = fingerprintStartMode3;
        FingerprintStartMode[] fingerprintStartModeArr = {fingerprintStartMode, fingerprintStartMode2, fingerprintStartMode3};
        $VALUES = fingerprintStartModeArr;
        EnumEntriesKt.enumEntries(fingerprintStartModeArr);
    }

    private FingerprintStartMode(String str, int i) {
    }

    public static FingerprintStartMode valueOf(String str) {
        return (FingerprintStartMode) Enum.valueOf(FingerprintStartMode.class, str);
    }

    public static FingerprintStartMode[] values() {
        return (FingerprintStartMode[]) $VALUES.clone();
    }
}
