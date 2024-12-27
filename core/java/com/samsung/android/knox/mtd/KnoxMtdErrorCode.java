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
    URL_NOT_EXISTS(10);

    private static final Map<Integer, KnoxMtdErrorCode> valueMap = new HashMap();
    private final int value;

    static {
        KnoxMtdErrorCode[] var0 = values();
        for (KnoxMtdErrorCode code : var0) {
            valueMap.put(Integer.valueOf(code.value), code);
        }
    }

    KnoxMtdErrorCode(int i) {
        this.value = i;
    }

    public int getValue() {
        return this.value;
    }

    public static KnoxMtdErrorCode getCodeFromValue(int value) {
        return valueMap.get(Integer.valueOf(value));
    }
}
