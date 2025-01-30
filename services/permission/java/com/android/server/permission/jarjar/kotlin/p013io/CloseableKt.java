package com.android.server.permission.jarjar.kotlin.p013io;

import com.android.server.permission.jarjar.kotlin.ExceptionsKt__ExceptionsKt;
import java.io.Closeable;

/* compiled from: Closeable.kt */
/* loaded from: classes2.dex */
public abstract class CloseableKt {
    public static final void closeFinally(Closeable closeable, Throwable th) {
        if (closeable != null) {
            if (th == null) {
                closeable.close();
                return;
            }
            try {
                closeable.close();
            } catch (Throwable th2) {
                ExceptionsKt__ExceptionsKt.addSuppressed(th, th2);
            }
        }
    }
}
