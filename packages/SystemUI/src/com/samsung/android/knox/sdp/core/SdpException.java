package com.samsung.android.knox.sdp.core;

import com.samsung.android.knox.sdp.SdpErrno;

/* loaded from: classes4.dex */
public class SdpException extends Exception {
    private int mErrorCode;
    private int mTimeout;

    public SdpException(int i) {
        super(SdpErrno.toString(classify(i)));
        this.mErrorCode = classify(i);
        this.mTimeout = 0;
    }

    private static int classify(int i) {
        if (i != -99) {
            switch (i) {
                case -14:
                case -13:
                case -12:
                case -11:
                    break;
                default:
                    return i;
            }
        }
        return -99;
    }

    public int getErrorCode() {
        return this.mErrorCode;
    }

    public int getTimeout() {
        return this.mTimeout;
    }

    public SdpException(int i, int i2) {
        super(SdpErrno.toString(classify(i)));
        this.mErrorCode = classify(i);
        this.mTimeout = i2;
    }
}
