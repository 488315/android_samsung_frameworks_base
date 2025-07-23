package androidx.compose.foundation.gestures;

import androidx.compose.foundation.MutatePriority;
import androidx.compose.foundation.MutatorMutex;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScopeKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class DefaultScrollableState implements ScrollableState {
    public final MutableState isLastScrollBackwardState;
    public final MutableState isLastScrollForwardState;
    public final MutableState isScrollingState;
    public final Function1 onDelta;
    public final DefaultScrollableState$scrollScope$1 scrollScope = new ScrollScope() { // from class: androidx.compose.foundation.gestures.DefaultScrollableState$scrollScope$1
        @Override // androidx.compose.foundation.gestures.ScrollScope
        public final float scrollBy(float f) {
            if (Float.isNaN(f)) {
                return 0.0f;
            }
            DefaultScrollableState defaultScrollableState = DefaultScrollableState.this;
            float floatValue = ((Number) defaultScrollableState.onDelta.mo779invoke(Float.valueOf(f))).floatValue();
            ((SnapshotMutableStateImpl) defaultScrollableState.isLastScrollForwardState).setValue(Boolean.valueOf(floatValue > 0.0f));
            ((SnapshotMutableStateImpl) defaultScrollableState.isLastScrollBackwardState).setValue(Boolean.valueOf(floatValue < 0.0f));
            return floatValue;
        }
    };
    public final MutatorMutex scrollMutex = new MutatorMutex();

    /* JADX WARN: Type inference failed for: r2v1, types: [androidx.compose.foundation.gestures.DefaultScrollableState$scrollScope$1] */
    public DefaultScrollableState(Function1 function1) {
        this.onDelta = function1;
        Boolean bool = Boolean.FALSE;
        this.isScrollingState = SnapshotStateKt.mutableStateOf$default(bool);
        this.isLastScrollForwardState = SnapshotStateKt.mutableStateOf$default(bool);
        this.isLastScrollBackwardState = SnapshotStateKt.mutableStateOf$default(bool);
    }

    @Override // androidx.compose.foundation.gestures.ScrollableState
    public final float dispatchRawDelta(float f) {
        return ((Number) this.onDelta.mo779invoke(Float.valueOf(f))).floatValue();
    }

    @Override // androidx.compose.foundation.gestures.ScrollableState
    public final boolean isScrollInProgress() {
        return ((Boolean) ((SnapshotMutableStateImpl) this.isScrollingState).getValue()).booleanValue();
    }

    @Override // androidx.compose.foundation.gestures.ScrollableState
    public final Object scroll(MutatePriority mutatePriority, Function2 function2, Continuation continuation) {
        Object coroutineScope = CoroutineScopeKt.coroutineScope(new DefaultScrollableState$scroll$2(this, mutatePriority, function2, null), continuation);
        return coroutineScope == CoroutineSingletons.COROUTINE_SUSPENDED ? coroutineScope : Unit.INSTANCE;
    }
}
