package androidx.compose.animation.core;

import androidx.compose.animation.core.AnimationVector;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import kotlin.jvm.functions.Function0;

/* loaded from: classes.dex */
public final class AnimationScope<T, V extends AnimationVector> {
    public long finishedTimeNanos = Long.MIN_VALUE;
    public final MutableState isRunning$delegate;
    public long lastFrameTimeNanos;
    public final Function0 onCancel;
    public final long startTimeNanos;
    public final Object targetValue;
    public final TwoWayConverter typeConverter;
    public final MutableState value$delegate;
    public AnimationVector velocityVector;

    public AnimationScope(T t, TwoWayConverter<T, V> twoWayConverter, V v, long j, T t2, long j2, boolean z, Function0 function0) {
        this.typeConverter = twoWayConverter;
        this.targetValue = t2;
        this.startTimeNanos = j2;
        this.onCancel = function0;
        this.value$delegate = SnapshotStateKt.mutableStateOf$default(t);
        this.velocityVector = AnimationVectorsKt.copy(v);
        this.lastFrameTimeNanos = j;
        this.isRunning$delegate = SnapshotStateKt.mutableStateOf$default(Boolean.valueOf(z));
    }

    public final void cancelAnimation() {
        ((SnapshotMutableStateImpl) this.isRunning$delegate).setValue(Boolean.FALSE);
        this.onCancel.invoke();
    }

    public final Object getVelocity() {
        return ((TwoWayConverterImpl) this.typeConverter).convertFromVector.mo781invoke(this.velocityVector);
    }
}
