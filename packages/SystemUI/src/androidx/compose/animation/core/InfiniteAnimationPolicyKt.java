package androidx.compose.animation.core;

import androidx.compose.runtime.MonotonicFrameClockKt;
import androidx.compose.ui.platform.InfiniteAnimationPolicy;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class InfiniteAnimationPolicyKt {
    public static final Object withInfiniteAnimationFrameNanos(Function1 function1, ContinuationImpl continuationImpl) {
        if (continuationImpl.getContext().get(InfiniteAnimationPolicy.Key) == null) {
            return MonotonicFrameClockKt.getMonotonicFrameClock(continuationImpl.getContext()).withFrameNanos(function1, continuationImpl);
        }
        throw new ClassCastException();
    }
}
