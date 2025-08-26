package androidx.compose.foundation.internal;

import java.util.concurrent.CancellationException;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public abstract class PlatformOptimizedCancellationException extends CancellationException {
    /* JADX WARN: Multi-variable type inference failed */
    public PlatformOptimizedCancellationException() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    @Override // java.lang.Throwable
    public final Throwable fillInStackTrace() {
        setStackTrace(PlatformOptimizedCancellationException_jvmKt.EmptyStackTraceElements);
        return this;
    }

    public PlatformOptimizedCancellationException(String str) {
        super(str);
    }

    public /* synthetic */ PlatformOptimizedCancellationException(String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : str);
    }
}
