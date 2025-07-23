package com.airbnb.lottie;

import java.util.Arrays;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class LottieResult {
    public final Throwable exception;
    public final Object value;

    public LottieResult(Object obj) {
        this.value = obj;
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
        Object obj2 = this.value;
        if (obj2 != null && obj2.equals(lottieResult.value)) {
            return true;
        }
        Throwable th = this.exception;
        if (th == null || lottieResult.exception == null) {
            return false;
        }
        return th.toString().equals(this.exception.toString());
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.value, this.exception});
    }

    public LottieResult(Throwable th) {
        this.exception = th;
        this.value = null;
    }
}
