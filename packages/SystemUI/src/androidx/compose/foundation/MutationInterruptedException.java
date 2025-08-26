package androidx.compose.foundation;

import androidx.compose.foundation.internal.PlatformOptimizedCancellationException;

/* loaded from: classes.dex */
public final class MutationInterruptedException extends PlatformOptimizedCancellationException {
    public MutationInterruptedException() {
        super("Mutation interrupted");
    }
}
