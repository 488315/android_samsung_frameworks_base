package com.android.systemui.controls.util;

import com.samsung.android.knox.foresight.KnoxForesight;
import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ControlsBackUpRestore$BNRResult {
    public static final /* synthetic */ ControlsBackUpRestore$BNRResult[] $VALUES;
    public static final ControlsBackUpRestore$BNRResult FAIL;
    public static final ControlsBackUpRestore$BNRResult SUCCESS;
    private final int value;

    static {
        ControlsBackUpRestore$BNRResult controlsBackUpRestore$BNRResult = new ControlsBackUpRestore$BNRResult(KnoxForesight.SUCCESS, 0, 0);
        SUCCESS = controlsBackUpRestore$BNRResult;
        ControlsBackUpRestore$BNRResult controlsBackUpRestore$BNRResult2 = new ControlsBackUpRestore$BNRResult("FAIL", 1, 1);
        FAIL = controlsBackUpRestore$BNRResult2;
        ControlsBackUpRestore$BNRResult[] controlsBackUpRestore$BNRResultArr = {controlsBackUpRestore$BNRResult, controlsBackUpRestore$BNRResult2, new ControlsBackUpRestore$BNRResult("USER_CANCEL", 2, 2)};
        $VALUES = controlsBackUpRestore$BNRResultArr;
        EnumEntriesKt.enumEntries(controlsBackUpRestore$BNRResultArr);
    }

    private ControlsBackUpRestore$BNRResult(String str, int i, int i2) {
        this.value = i2;
    }

    public static ControlsBackUpRestore$BNRResult valueOf(String str) {
        return (ControlsBackUpRestore$BNRResult) Enum.valueOf(ControlsBackUpRestore$BNRResult.class, str);
    }

    public static ControlsBackUpRestore$BNRResult[] values() {
        return (ControlsBackUpRestore$BNRResult[]) $VALUES.clone();
    }

    public final int getValue() {
        return this.value;
    }
}
