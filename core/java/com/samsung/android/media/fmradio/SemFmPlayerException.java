package com.samsung.android.media.fmradio;

public class SemFmPlayerException extends Exception {
    private static final long serialVersionUID = 1;
    private Throwable mThrowable;
    private String msg;

    public SemFmPlayerException(String msg, Throwable throwable) {
        this.msg = msg;
        this.mThrowable = throwable;
    }

    public Throwable getThrowable() {
        return this.mThrowable;
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        return this.msg;
    }
}
