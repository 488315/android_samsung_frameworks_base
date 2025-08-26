package com.samsung.android.sdk;

/* loaded from: classes4.dex */
public class SsdkUnsupportedException extends Exception {
    private int mErrorType;

    public SsdkUnsupportedException(String str, int i) {
        super(str);
        this.mErrorType = i;
    }
}
