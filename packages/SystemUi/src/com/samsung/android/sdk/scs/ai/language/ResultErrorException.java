package com.samsung.android.sdk.scs.ai.language;

import com.samsung.android.sdk.scs.base.ResultException;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class ResultErrorException extends ResultException {
    public final int mErrorCode;

    public ResultErrorException(int i) {
        super(i);
        this.mErrorCode = ErrorClassifier$ErrorCode.DEVICE_UNKNOWN_ERROR.ordinal();
    }

    public final ErrorClassifier$ErrorCode getErrorCodeClassified() {
        int i = this.mErrorCode;
        int i2 = i / 1000;
        if (i >= 1 && i <= 14) {
            return ErrorClassifier$ErrorCode.DEVICE_NETORK_ERROR;
        }
        if (i2 == 1) {
            return ErrorClassifier$ErrorCode.CLIENT_ERROR;
        }
        if (i2 == 2) {
            return (i == 2200 || i == 2201) ? ErrorClassifier$ErrorCode.AUTH_SA_ERROR : ErrorClassifier$ErrorCode.AUTH_ERROR;
        }
        if (i2 == 4) {
            return ErrorClassifier$ErrorCode.SERVER_QUOTA_ERROR;
        }
        if (i2 != 5) {
            return (i2 == 8 || i2 == 9) ? ErrorClassifier$ErrorCode.SERVER_ERROR : ErrorClassifier$ErrorCode.DEVICE_UNKNOWN_ERROR;
        }
        if (i != 5120) {
            if (i == 5210) {
                return ErrorClassifier$ErrorCode.SAFETY_FILTER_RECITATION_ERROR;
            }
            if (i != 5220) {
                return ErrorClassifier$ErrorCode.SAFETY_FILTER_ERROR;
            }
        }
        return ErrorClassifier$ErrorCode.SAFETY_FILTER_UNSUPPORTED_LANGUAGE_ERROR;
    }

    public ResultErrorException(int i, String str) {
        super(i, str);
        this.mErrorCode = ErrorClassifier$ErrorCode.DEVICE_UNKNOWN_ERROR.ordinal();
    }

    public ResultErrorException(int i, int i2, String str) {
        super(i2, str);
        this.mErrorCode = i2;
    }
}
