package com.android.systemui.controls.util;

import com.samsung.android.knox.foresight.KnoxForesight;
import kotlin.enums.EnumEntriesKt;

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
