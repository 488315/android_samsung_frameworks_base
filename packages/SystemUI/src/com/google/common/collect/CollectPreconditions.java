package com.google.common.collect;

/* loaded from: classes4.dex */
public final class CollectPreconditions {
    public static void checkNonnegative(int i, String str) {
        if (i >= 0) {
            return;
        }
        throw new IllegalArgumentException(str + " cannot be negative but was: " + i);
    }
}
