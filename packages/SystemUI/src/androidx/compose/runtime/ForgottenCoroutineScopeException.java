package androidx.compose.runtime;

import androidx.compose.runtime.internal.PlatformOptimizedCancellationException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class ForgottenCoroutineScopeException extends PlatformOptimizedCancellationException {
    public ForgottenCoroutineScopeException() {
        super("rememberCoroutineScope left the composition");
    }
}
