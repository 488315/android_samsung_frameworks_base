package com.samsung.android.sdk.scs.p048ai.language;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public enum ErrorClassifier$ErrorCode {
    /* JADX INFO: Fake field, exist only in values array */
    DEVICE_ERROR(100),
    DEVICE_NETORK_ERROR(101),
    DEVICE_UNKNOWN_ERROR(199),
    CLIENT_ERROR(200),
    AUTH_ERROR(300),
    AUTH_SA_ERROR(301),
    SAFETY_FILTER_ERROR(400),
    SAFETY_FILTER_UNSUPPORTED_LANGUAGE_ERROR(401),
    SAFETY_FILTER_RECITATION_ERROR(402),
    SERVER_ERROR(500),
    SERVER_QUOTA_ERROR(501);

    private final int mError;

    ErrorClassifier$ErrorCode(int i) {
        this.mError = i;
    }
}
