package com.samsung.android.media.fmradio;

/* loaded from: classes6.dex */
public class SemFmPlayerException extends Exception {
    private static final long serialVersionUID = 1;
    private Throwable mThrowable;
    private String msg;

    public SemFmPlayerException(String str, Throwable th) {
        this.msg = str;
        this.mThrowable = th;
    }

    public Throwable getThrowable() {
        return this.mThrowable;
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        return this.msg;
    }
}
