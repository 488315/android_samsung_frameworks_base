package androidx.compose.foundation.gestures;

import androidx.compose.foundation.MutatePriority;
import androidx.compose.foundation.MutatorMutex;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScopeKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class DefaultDraggableState implements DraggableState {
    public final Function1 onDelta;
    public final DefaultDraggableState$dragScope$1 dragScope = new DragScope() { // from class: androidx.compose.foundation.gestures.DefaultDraggableState$dragScope$1
        @Override // androidx.compose.foundation.gestures.DragScope
        public final void dragBy(float f) {
            DefaultDraggableState.this.onDelta.mo779invoke(Float.valueOf(f));
        }
    };
    public final MutatorMutex scrollMutex = new MutatorMutex();

    /* JADX WARN: Type inference failed for: r1v1, types: [androidx.compose.foundation.gestures.DefaultDraggableState$dragScope$1] */
    public DefaultDraggableState(Function1 function1) {
        this.onDelta = function1;
    }

    @Override // androidx.compose.foundation.gestures.DraggableState
    public final Object drag(MutatePriority mutatePriority, Function2 function2, Continuation continuation) {
        Object coroutineScope = CoroutineScopeKt.coroutineScope(new DefaultDraggableState$drag$2(this, mutatePriority, function2, null), continuation);
        return coroutineScope == CoroutineSingletons.COROUTINE_SUSPENDED ? coroutineScope : Unit.INSTANCE;
    }
}
