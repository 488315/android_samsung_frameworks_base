package android.util;

import android.os.SystemProperties;

/* loaded from: classes4.dex */
public class SystemPropertySetter {
    public static final int PROPERTY_FAILURE_RETRY_DELAY_MILLIS = 200;
    public static final int PROPERTY_FAILURE_RETRY_LIMIT = 5;

    public static void setWithRetry(String str, String str2) throws InterruptedException {
        setWithRetry(str, str2, 200, 5L);
    }

    public static void setWithRetry(String str, String str2, int i, long j) throws InterruptedException {
        if (i < 0) {
            throw new IllegalArgumentException("invalid retry count: " + i);
        }
        if (j <= 0) {
            throw new IllegalArgumentException("invalid retry delay: " + j);
        }
        RuntimeException runtimeException = null;
        for (int i2 = 0; i2 < i; i2++) {
            try {
                SystemProperties.set(str, str2);
                return;
            } catch (RuntimeException e) {
                if (runtimeException == null) {
                    runtimeException = e;
                }
                try {
                    Thread.sleep(j);
                } catch (InterruptedException unused) {
                }
            }
        }
        throw runtimeException;
    }
}
