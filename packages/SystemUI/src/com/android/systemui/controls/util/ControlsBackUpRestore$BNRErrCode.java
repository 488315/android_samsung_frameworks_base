package com.android.systemui.controls.util;

import com.samsung.android.knox.foresight.KnoxForesight;
import kotlin.enums.EnumEntriesKt;

public final class ControlsBackUpRestore$BNRErrCode {
    public static final /* synthetic */ ControlsBackUpRestore$BNRErrCode[] $VALUES;
    public static final ControlsBackUpRestore$BNRErrCode INVALID_DATA;
    public static final ControlsBackUpRestore$BNRErrCode SUCCESS;
    private final int value;

    static {
        ControlsBackUpRestore$BNRErrCode controlsBackUpRestore$BNRErrCode = new ControlsBackUpRestore$BNRErrCode(KnoxForesight.SUCCESS, 0, 0);
        SUCCESS = controlsBackUpRestore$BNRErrCode;
        ControlsBackUpRestore$BNRErrCode controlsBackUpRestore$BNRErrCode2 = new ControlsBackUpRestore$BNRErrCode("UNKNOWN_ERROR", 1, 1);
        ControlsBackUpRestore$BNRErrCode controlsBackUpRestore$BNRErrCode3 = new ControlsBackUpRestore$BNRErrCode("STORAGE_FULL", 2, 2);
        ControlsBackUpRestore$BNRErrCode controlsBackUpRestore$BNRErrCode4 = new ControlsBackUpRestore$BNRErrCode("INVALID_DATA", 3, 3);
        INVALID_DATA = controlsBackUpRestore$BNRErrCode4;
        ControlsBackUpRestore$BNRErrCode[] controlsBackUpRestore$BNRErrCodeArr = {controlsBackUpRestore$BNRErrCode, controlsBackUpRestore$BNRErrCode2, controlsBackUpRestore$BNRErrCode3, controlsBackUpRestore$BNRErrCode4, new ControlsBackUpRestore$BNRErrCode("PERMISSION_FAIL", 4, 4), new ControlsBackUpRestore$BNRErrCode("LOCKED", 5, 5), new ControlsBackUpRestore$BNRErrCode("PARTIAL_SUCCESS", 6, 6)};
        $VALUES = controlsBackUpRestore$BNRErrCodeArr;
        EnumEntriesKt.enumEntries(controlsBackUpRestore$BNRErrCodeArr);
    }

    private ControlsBackUpRestore$BNRErrCode(String str, int i, int i2) {
        this.value = i2;
    }

    public static ControlsBackUpRestore$BNRErrCode valueOf(String str) {
        return (ControlsBackUpRestore$BNRErrCode) Enum.valueOf(ControlsBackUpRestore$BNRErrCode.class, str);
    }

    public static ControlsBackUpRestore$BNRErrCode[] values() {
        return (ControlsBackUpRestore$BNRErrCode[]) $VALUES.clone();
    }

    public final int getValue() {
        return this.value;
    }
}
