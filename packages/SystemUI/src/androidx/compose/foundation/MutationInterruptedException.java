package androidx.compose.foundation;

import androidx.compose.foundation.internal.PlatformOptimizedCancellationException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class MutationInterruptedException extends PlatformOptimizedCancellationException {
    public MutationInterruptedException() {
        super("Mutation interrupted");
    }
}
