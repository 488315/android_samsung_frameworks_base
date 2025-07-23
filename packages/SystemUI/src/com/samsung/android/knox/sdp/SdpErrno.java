package com.samsung.android.knox.sdp;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class SdpErrno {
    public static final int ERROR_COMPROMISED = -14;
    public static final int ERROR_ENGINE_ACCESS_DENIED = -7;
    public static final int ERROR_ENGINE_ALREADY_EXISTS = -4;
    public static final int ERROR_ENGINE_LOCKED = -6;
    public static final int ERROR_ENGINE_NOT_EXISTS = -5;
    public static final int ERROR_ENGINE_THROTTLED = -8;
    public static final int ERROR_FAILED = -99;
    public static final int ERROR_FILE_IO = -12;
    public static final int ERROR_INTERNAL = -99;
    public static final int ERROR_INVALID_PARAMETER = -3;
    public static final int ERROR_INVALID_PASSWORD = -1;
    public static final int ERROR_INVALID_RESET_TOKEN = -2;
    public static final int ERROR_LICENSE_REQUIRED = -9;
    public static final int ERROR_NATIVE = -11;
    public static final int ERROR_NONE = 0;
    public static final int ERROR_NOT_SUPPORTED_DEVICE = -10;
    public static final int ERROR_SERVICE_NOT_FOUND = -13;
    public static final int SUCCESS = 0;

    public static String toString(int i) {
        if (i == -99) {
            return "Internal error occurred";
        }
        switch (i) {
            case -14:
            case -13:
            case -12:
            case -11:
                return "Internal error occurred";
            case -10:
                return "SDP not supported device";
            case -9:
                return "License required";
            case -8:
                return "SDP engine is throttled";
            case -7:
                return "SDP engine access denied";
            case -6:
                return "SDP engine is locked";
            case -5:
                return "SDP engine does not exist";
            case -4:
                return "SDP engine already exists";
            case -3:
                return "Invalid parameter";
            case -2:
                return "Invalid reset token";
            case -1:
                return "Invalid password";
            case 0:
                return "No error";
            default:
                return "Unknown error";
        }
    }
}
