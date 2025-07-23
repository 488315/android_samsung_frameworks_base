package androidx.compose.foundation.lazy.layout;

import androidx.compose.animation.core.AnimationState;
import androidx.compose.animation.core.AnimationStateKt;
import androidx.compose.animation.core.VectorConvertersKt;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.snapshots.Snapshot;
import androidx.compose.ui.unit.Density;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FloatCompanionObject;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class LazyLayoutScrollDeltaBetweenPasses {
    public AnimationState _scrollDeltaBetweenPasses;

    public LazyLayoutScrollDeltaBetweenPasses() {
        FloatCompanionObject floatCompanionObject = FloatCompanionObject.INSTANCE;
        this._scrollDeltaBetweenPasses = AnimationStateKt.AnimationState$default(VectorConvertersKt.FloatToVector);
    }

    public final void updateScrollDeltaForApproach$foundation_release(float f, Density density, CoroutineScope coroutineScope) {
        if (f <= density.mo57toPx0680j_4(LazyLayoutScrollDeltaBetweenPassesKt.DeltaThresholdForScrollAnimation)) {
            return;
        }
        Snapshot.Companion.getClass();
        Snapshot currentThreadSnapshot = Snapshot.Companion.getCurrentThreadSnapshot();
        Function1 readObserver = currentThreadSnapshot != null ? currentThreadSnapshot.getReadObserver() : null;
        Snapshot makeCurrentNonObservable = Snapshot.Companion.makeCurrentNonObservable(currentThreadSnapshot);
        try {
            float floatValue = ((Number) ((SnapshotMutableStateImpl) this._scrollDeltaBetweenPasses.value$delegate).getValue()).floatValue();
            AnimationState animationState = this._scrollDeltaBetweenPasses;
            if (animationState.isRunning) {
                this._scrollDeltaBetweenPasses = AnimationStateKt.copy$default(animationState, floatValue - f, 0.0f, 30);
                BuildersKt.launch$default(coroutineScope, null, null, new LazyLayoutScrollDeltaBetweenPasses$updateScrollDeltaForApproach$2$1(this, null), 3);
            } else {
                FloatCompanionObject floatCompanionObject = FloatCompanionObject.INSTANCE;
                this._scrollDeltaBetweenPasses = new AnimationState(VectorConvertersKt.FloatToVector, Float.valueOf(-f), null, 0L, 0L, false, 60, null);
                BuildersKt.launch$default(coroutineScope, null, null, new LazyLayoutScrollDeltaBetweenPasses$updateScrollDeltaForApproach$2$2(this, null), 3);
            }
            Snapshot.Companion.restoreNonObservable(currentThreadSnapshot, makeCurrentNonObservable, readObserver);
        } catch (Throwable th) {
            Snapshot.Companion.restoreNonObservable(currentThreadSnapshot, makeCurrentNonObservable, readObserver);
            throw th;
        }
    }
}
