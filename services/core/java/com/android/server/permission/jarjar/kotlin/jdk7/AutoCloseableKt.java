package com.android.server.permission.jarjar.kotlin.jdk7;

import com.android.server.permission.jarjar.kotlin.ExceptionsKt;

public abstract class AutoCloseableKt {
    public static final void closeFinally(AutoCloseable autoCloseable, Throwable th) {
        if (autoCloseable != null) {
            if (th == null) {
                autoCloseable.close();
                return;
            }
            try {
                autoCloseable.close();
            } catch (Throwable th2) {
                ExceptionsKt.addSuppressed(th, th2);
            }
        }
    }
}
