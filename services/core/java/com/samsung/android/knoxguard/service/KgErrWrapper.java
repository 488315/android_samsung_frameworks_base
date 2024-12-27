package com.samsung.android.knoxguard.service;

public class KgErrWrapper {
    public static final int KGTA_FAILED = -1234;
    public static final int KGTA_PARAM_DEFAULT = 0;
    public static String TAG = "KgErrWrapper";
    public byte[] data;
    public int err;
    public int result;

    public KgErrWrapper() {
        this.err = KGTA_FAILED;
    }

    public KgErrWrapper(int i) {
        this.err = i;
    }

    public final String getStr() {
        byte[] bArr = this.data;
        if (bArr != null) {
            return new String(bArr);
        }
        return null;
    }

    public final void setErr(int i) {
        this.err = i;
    }
}
