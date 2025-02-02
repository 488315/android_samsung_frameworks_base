package com.airbnb.lottie;

import java.util.Arrays;

/* loaded from: classes.dex */
public final class LottieResult<V> {
    private final Throwable exception;
    private final V value;

    /* JADX WARN: Multi-variable type inference failed */
    public LottieResult(LottieComposition lottieComposition) {
        this.value = lottieComposition;
        this.exception = null;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof LottieResult)) {
            return false;
        }
        LottieResult lottieResult = (LottieResult) obj;
        V v = this.value;
        if (v != null && v.equals(lottieResult.value)) {
            return true;
        }
        Throwable th = this.exception;
        if (th == null || lottieResult.exception == null) {
            return false;
        }
        return th.toString().equals(th.toString());
    }

    public final Throwable getException() {
        return this.exception;
    }

    public final V getValue() {
        return this.value;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.value, this.exception});
    }

    public LottieResult(Throwable th) {
        this.exception = th;
        this.value = null;
    }
}
