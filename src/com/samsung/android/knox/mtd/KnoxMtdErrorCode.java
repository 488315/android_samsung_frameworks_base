package com.samsung.android.knox.mtd;

import java.util.HashMap;
import java.util.Map;

/* loaded from: classes6.dex */
public enum KnoxMtdErrorCode {
    NONE(-1),
    ALLOWLIST(0),
    MODEL_RESULT(1),
    SHORT_URL(2),
    INTERNET_NOT_AVAILABLE(3),
    UNICODE_URL(4),
    IP_URL(5),
    MALFORMED_URL(6),
    INTERNAL_ERROR(7),
    RDAP_METADATA_NOT_FOUND(8),
    BLOCKLIST(9),
    URL_NOT_EXISTS(10),
    CUSTOM_ALLOWLIST(11);

    private static final Map<Integer, KnoxMtdErrorCode> valueMap = new HashMap();
    private final int value;

    static {
        for (KnoxMtdErrorCode knoxMtdErrorCode : values()) {
            valueMap.put(Integer.valueOf(knoxMtdErrorCode.value), knoxMtdErrorCode);
        }
    }

    KnoxMtdErrorCode(int i) {
        this.value = i;
    }

    public int getValue() {
        return this.value;
    }

    public static KnoxMtdErrorCode getCodeFromValue(int i) {
        return valueMap.get(Integer.valueOf(i));
    }
}
