package com.samsung.android.sdk.scs.base;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
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
