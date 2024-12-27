package com.android.net.module.util;

public class SdkUtil {

    public static class LateSdk<T> {
        public final T value;

        public LateSdk(T value) {
            this.value = value;
        }
    }
}
