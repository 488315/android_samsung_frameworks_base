package com.android.internal.org.bouncycastle.util;

public interface Memoable {
    Memoable copy();

    void reset(Memoable memoable);
}
