package com.samsung.android.knoxguard.service;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
