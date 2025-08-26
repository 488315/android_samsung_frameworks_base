package androidx.compose.animation.core;

import androidx.compose.runtime.MonotonicFrameClockKt;
import androidx.compose.ui.platform.InfiniteAnimationPolicy;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
public abstract class InfiniteAnimationPolicyKt {
    public static final Object withInfiniteAnimationFrameNanos(Function1 function1, ContinuationImpl continuationImpl) {
        if (continuationImpl.getContext().get(InfiniteAnimationPolicy.Key) == null) {
            return MonotonicFrameClockKt.getMonotonicFrameClock(continuationImpl.getContext()).withFrameNanos(function1, continuationImpl);
        }
        throw new ClassCastException();
    }
}
