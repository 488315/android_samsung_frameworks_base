package com.samsung.android.sdk.scs.base;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
