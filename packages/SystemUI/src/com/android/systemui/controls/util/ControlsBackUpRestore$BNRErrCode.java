package com.android.systemui.controls.util;

import com.samsung.android.knox.foresight.KnoxForesight;
import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
