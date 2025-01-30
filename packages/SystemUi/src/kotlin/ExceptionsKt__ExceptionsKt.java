package kotlin;

import kotlin.internal.PlatformImplementationsKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class ExceptionsKt__ExceptionsKt {
    public static final void addSuppressed(Throwable th, Throwable th2) {
        if (th != th2) {
            PlatformImplementationsKt.IMPLEMENTATIONS.addSuppressed(th, th2);
        }
    }
}
