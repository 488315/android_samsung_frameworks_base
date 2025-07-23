package com.samsung.android.sdk.sfe.util;

import android.os.Build;

/* loaded from: classes6.dex */
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

    public static void ThrowUncheckedException(int i) {
        if (i == 1) {
            throw new RuntimeException("E_UNKNOWN");
        }
        if (i == 2) {
            throw new OutOfMemoryError("E_OUT_OF_MEMORY");
        }
        if (i == 3) {
            throw new IndexOutOfBoundsException("E_OUT_OF_RANGE");
        }
        if (i == 4) {
            throw new IllegalStateException("E_ALREADY_INIT");
        }
        if (i == 5) {
            throw new IllegalStateException("E_ALREADY_SET");
        }
        if (i == 7) {
            throw new IllegalArgumentException("E_INVALID_ARG");
        }
        if (i == 8) {
            throw new IllegalStateException("E_INVALID_STATE");
        }
        if (i == 9) {
            throw new IllegalStateException("E_DATA_NOT_FOUND");
        }
        if (i == 12) {
            throw new IllegalStateException("E_UNSUPPORTED_VERSION");
        }
        if (i == 18) {
            throw new IllegalStateException("E_INSTANCE_NOT_LOADED");
        }
        throw new RuntimeException("Error number is " + i);
    }

    public static int getError() {
        return Error_GetError();
    }

    private static boolean isBuildTypeEngMode() {
        return "eng".equals(Build.TYPE);
    }
}
