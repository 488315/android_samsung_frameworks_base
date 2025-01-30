package com.samsung.android.core.p036pm.containerservice;

/* loaded from: classes5.dex */
public final class IoUtils {
    private IoUtils() {
    }

    public static void closeQuietly(AutoCloseable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (RuntimeException rethrown) {
                throw rethrown;
            } catch (Exception e) {
            }
        }
    }
}
