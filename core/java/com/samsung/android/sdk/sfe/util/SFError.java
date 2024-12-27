package com.samsung.android.sdk.sfe.util;

import android.os.Build;

public class SFError {
    public static final int E_ALREADY_INIT = 4;
    public static final int E_ALREADY_SET = 5;
    public static final int E_DATA_NOT_FOUND = 9;
    public static final int E_INSTANCE_NOT_LOADED = 18;
    public static final int E_INVALID_ARG = 7;
    public static final int E_INVALID_STATE = 8;
    public static final int E_OUT_OF_MEMORY = 2;
    public static final int E_OUT_OF_RANGE = 3;
    public static final int E_UNKNOWN = 1;
    public static final int E_UNSUPPORTED_VERSION = 12;

    private static native int Error_GetError();

    public static void ThrowUncheckedException(int exceptionNum) {
        switch (exceptionNum) {
            case 1:
                throw new RuntimeException("E_UNKNOWN");
            case 2:
                throw new OutOfMemoryError("E_OUT_OF_MEMORY");
            case 3:
                throw new IndexOutOfBoundsException("E_OUT_OF_RANGE");
            case 4:
                throw new IllegalStateException("E_ALREADY_INIT");
            case 5:
                throw new IllegalStateException("E_ALREADY_SET");
            case 6:
            case 10:
            case 11:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            default:
                throw new RuntimeException("Error number is " + exceptionNum);
            case 7:
                throw new IllegalArgumentException("E_INVALID_ARG");
            case 8:
                throw new IllegalStateException("E_INVALID_STATE");
            case 9:
                throw new IllegalStateException("E_DATA_NOT_FOUND");
            case 12:
                throw new IllegalStateException("E_UNSUPPORTED_VERSION");
            case 18:
                throw new IllegalStateException("E_INSTANCE_NOT_LOADED");
        }
    }

    public static int getError() {
        return Error_GetError();
    }

    private static boolean isBuildTypeEngMode() {
        return "eng".equals(Build.TYPE);
    }
}
