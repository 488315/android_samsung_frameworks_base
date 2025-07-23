package com.google.zxing.common;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public interface ECIInput {
    char charAt(int i);

    int getECIValue(int i);

    boolean isECI(int i);

    int length();

    CharSequence subSequence(int i, int i2);
}
