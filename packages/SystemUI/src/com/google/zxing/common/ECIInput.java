package com.google.zxing.common;

/* loaded from: classes4.dex */
public interface ECIInput {
    char charAt(int i);

    int getECIValue(int i);

    boolean isECI(int i);

    int length();

    CharSequence subSequence(int i, int i2);
}
