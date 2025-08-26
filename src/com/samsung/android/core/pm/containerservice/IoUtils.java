package com.samsung.android.core.pm.containerservice;

/* loaded from: classes6.dex */
public final class IoUtils {
    private IoUtils() {
    }

    public static void closeQuietly(AutoCloseable autoCloseable) throws Exception {
        if (autoCloseable != null) {
            try {
                autoCloseable.close();
            } catch (RuntimeException e) {
                throw e;
            } catch (Exception unused) {
            }
        }
    }
}
