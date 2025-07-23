package com.samsung.android.sume.core.exception;

/* loaded from: classes6.dex */
public class StreamFilterExitException extends IllegalStateException implements IntendedQuitException {
    public StreamFilterExitException() {
    }

    public StreamFilterExitException(String str) {
        super(str);
    }

    public StreamFilterExitException(String str, Throwable th) {
        super(str, th);
    }

    public StreamFilterExitException(Throwable th) {
        super(th);
    }
}
