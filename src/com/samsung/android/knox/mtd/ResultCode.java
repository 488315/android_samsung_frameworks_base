package com.samsung.android.knox.mtd;

import java.util.HashMap;
import java.util.Map;

/* loaded from: classes6.dex */
public enum ResultCode {
    UNSUPPORTED(-2),
    UNANALYZED(-1),
    BENIGN(0),
    MALICIOUS(1),
    TYPO_SQUATTING(2),
    INVALID(3),
    URL_EXPIRED(4);

    private static final Map<Integer, ResultCode> valueMap = new HashMap();
    private final int value;

    static {
        for (ResultCode resultCode : values()) {
            valueMap.put(Integer.valueOf(resultCode.value), resultCode);
        }
    }

    ResultCode(int i) {
        this.value = i;
    }

    public int getValue() {
        return this.value;
    }

    public static ResultCode getCodeFromValue(int i) {
        return valueMap.getOrDefault(Integer.valueOf(i), UNANALYZED);
    }
}
