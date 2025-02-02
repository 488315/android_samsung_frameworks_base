package com.airbnb.lottie.utils;

/* loaded from: classes.dex */
public final class MeanCalculator {

    /* renamed from: n */
    private int f20n;

    public final void add() {
        int i = this.f20n + 1;
        this.f20n = i;
        if (i == Integer.MAX_VALUE) {
            this.f20n = i / 2;
        }
    }
}
