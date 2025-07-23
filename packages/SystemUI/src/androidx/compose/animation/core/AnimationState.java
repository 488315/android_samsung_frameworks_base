package androidx.compose.animation.core;

import androidx.compose.animation.core.AnimationVector;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class AnimationState<T, V extends AnimationVector> implements State<T> {
    public long finishedTimeNanos;
    public boolean isRunning;
    public long lastFrameTimeNanos;
    public final TwoWayConverter typeConverter;
    public final MutableState value$delegate;
    public AnimationVector velocityVector;

    public AnimationState(TwoWayConverter<T, V> twoWayConverter, T t, V v, long j, long j2, boolean z) {
        AnimationVector animationVector;
        this.typeConverter = twoWayConverter;
        this.value$delegate = SnapshotStateKt.mutableStateOf$default(t);
        if (v != null) {
            animationVector = AnimationVectorsKt.copy(v);
        } else {
            animationVector = (AnimationVector) ((TwoWayConverterImpl) twoWayConverter).convertToVector.mo779invoke(t);
            animationVector.reset$animation_core();
        }
        this.velocityVector = animationVector;
        this.lastFrameTimeNanos = j;
        this.finishedTimeNanos = j2;
        this.isRunning = z;
    }

    @Override // androidx.compose.runtime.State
    public final Object getValue() {
        return ((SnapshotMutableStateImpl) this.value$delegate).getValue();
    }

    public final Object getVelocity() {
        return ((TwoWayConverterImpl) this.typeConverter).convertFromVector.mo779invoke(this.velocityVector);
    }

    public final String toString() {
        return "AnimationState(value=" + ((SnapshotMutableStateImpl) this.value$delegate).getValue() + ", velocity=" + getVelocity() + ", isRunning=" + this.isRunning + ", lastFrameTimeNanos=" + this.lastFrameTimeNanos + ", finishedTimeNanos=" + this.finishedTimeNanos + ')';
    }

    public /* synthetic */ AnimationState(TwoWayConverter twoWayConverter, Object obj, AnimationVector animationVector, long j, long j2, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(twoWayConverter, obj, (i & 4) != 0 ? null : animationVector, (i & 8) != 0 ? Long.MIN_VALUE : j, (i & 16) != 0 ? Long.MIN_VALUE : j2, (i & 32) != 0 ? false : z);
    }
}
