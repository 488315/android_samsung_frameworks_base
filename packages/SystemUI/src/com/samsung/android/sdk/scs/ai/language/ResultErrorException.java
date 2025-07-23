package com.samsung.android.sdk.scs.ai.language;

import com.samsung.android.sdk.scs.base.ResultException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class ResultErrorException extends ResultException {
    public final int mErrorCode;

    public ResultErrorException(int i) {
        super(i);
        this.mErrorCode = ErrorClassifier$ErrorCode.DEVICE_UNKNOWN_ERROR.ordinal();
    }

    public final ErrorClassifier$ErrorCode getErrorCodeClassified() {
        int i = this.mErrorCode;
        int i2 = i / 1000;
        if (i >= 1 && i <= 16) {
            return ErrorClassifier$ErrorCode.DEVICE_NETWORK_ERROR;
        }
        if (i == 102) {
            return ErrorClassifier$ErrorCode.DEVICE_INIT_ERROR;
        }
        if (i == 180) {
            return ErrorClassifier$ErrorCode.DEVICE_WATCH_CONNECTION_ERROR;
        }
        if (i == 181) {
            return ErrorClassifier$ErrorCode.DEVICE_WATCH_DATA_LAYER_ERROR;
        }
        if (i == 182) {
            return ErrorClassifier$ErrorCode.DEVICE_WATCH_ON_DEVICE_PACKAGE_ERROR;
        }
        if (i == 183) {
            return ErrorClassifier$ErrorCode.DEVICE_WATCH_INTERNAL_ERROR;
        }
        if (i == 184) {
            return ErrorClassifier$ErrorCode.DEVICE_WATCH_NOT_SUPPORT_TASK_ERROR;
        }
        if (i2 == 1) {
            if (i == 1010) {
                return ErrorClassifier$ErrorCode.CLIENT_ERROR_INVALID_REQUEST_TYPE;
            }
            if (i == 1024) {
                return ErrorClassifier$ErrorCode.CLIENT_ERROR_MANDATORY_FIELD_MISSING;
            }
            if (i == 1026) {
                return ErrorClassifier$ErrorCode.CLIENT_ERROR_INPUT_TOO_LONG;
            }
            if (i == 1900) {
                return ErrorClassifier$ErrorCode.CLIENT_ERROR_UNSUPPORTED_LANGUAGE;
            }
            switch (i) {
                case 1097:
                    return ErrorClassifier$ErrorCode.CLIENT_ERROR_UNSUPPORTED_DEVICE;
                case 1098:
                    return ErrorClassifier$ErrorCode.CLIENT_ERROR_BUSY;
                case 1099:
                    return ErrorClassifier$ErrorCode.CLIENT_ERROR_CANCEL;
                default:
                    return ErrorClassifier$ErrorCode.CLIENT_ERROR;
            }
        }
        if (i2 == 2) {
            return (i == 2200 || i == 2201) ? ErrorClassifier$ErrorCode.AUTH_SA_ERROR : ErrorClassifier$ErrorCode.AUTH_ERROR;
        }
        if (i2 == 4) {
            return ErrorClassifier$ErrorCode.SERVER_QUOTA_ERROR;
        }
        if (i2 != 5) {
            return (i2 == 8 || i2 == 9) ? i != 9000 ? i != 9001 ? ErrorClassifier$ErrorCode.SERVER_ERROR : ErrorClassifier$ErrorCode.SERVER_UNVAILABLE : ErrorClassifier$ErrorCode.SERVER_INTERNAL_ERROR : ErrorClassifier$ErrorCode.DEVICE_UNKNOWN_ERROR;
        }
        if (i != 5120) {
            if (i != 5152) {
                if (i == 5210) {
                    return ErrorClassifier$ErrorCode.SAFETY_FILTER_RECITATION_ERROR;
                }
                if (i != 5220) {
                    if (i != 5252) {
                        return ErrorClassifier$ErrorCode.SAFETY_FILTER_ERROR;
                    }
                }
            }
            return ErrorClassifier$ErrorCode.SAFETY_FILTER_TOXIC_ERROR;
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
