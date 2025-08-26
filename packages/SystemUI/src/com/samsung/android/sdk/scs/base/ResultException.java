package com.samsung.android.sdk.scs.base;

/* loaded from: classes4.dex */
public class ResultException extends RuntimeException {
    public final int mResultCode;

    public ResultException(int i) {
        this.mResultCode = i;
    }

    public ResultException(int i, String str) {
        super(str);
        this.mResultCode = i;
    }
}
