package androidx.compose.ui.input.pointer;

import androidx.compose.ui.internal.PlatformOptimizedCancellationException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class CancelTimeoutCancellationException extends PlatformOptimizedCancellationException {
    public static final CancelTimeoutCancellationException INSTANCE = new CancelTimeoutCancellationException();

    private CancelTimeoutCancellationException() {
        super(null, 1, null);
    }
}
