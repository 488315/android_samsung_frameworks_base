package com.android.net.module.util;

/* loaded from: classes6.dex */
public class SdkUtil {
    public static boolean isAtLeast25Q2() {
        return true;
    }

    public static class LateSdk<T> {
        public final T value;

        public LateSdk(T t) {
            this.value = t;
        }
    }
}
