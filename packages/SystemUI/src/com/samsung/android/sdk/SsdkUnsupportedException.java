package com.samsung.android.sdk;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class SsdkUnsupportedException extends Exception {
    private int mErrorType;

    public SsdkUnsupportedException(String str, int i) {
        super(str);
        this.mErrorType = i;
    }
}
