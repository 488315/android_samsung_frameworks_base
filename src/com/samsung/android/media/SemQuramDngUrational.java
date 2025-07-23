package com.samsung.android.media;

/* loaded from: classes6.dex */
public class SemQuramDngUrational {
    long d;
    long n;

    public float getReal32() {
        long j = this.d;
        return j == 0 ? j : this.n / j;
    }

    public double getReal64() {
        long j = this.d;
        return j == 0 ? j : this.n / j;
    }

    public long getNumerator() {
        return this.n;
    }

    public long getDenominator() {
        return this.d;
    }
}
