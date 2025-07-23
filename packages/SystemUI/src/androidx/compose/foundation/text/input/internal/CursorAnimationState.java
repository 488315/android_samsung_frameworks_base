package androidx.compose.foundation.text.input.internal;

import androidx.compose.runtime.MutableFloatState;
import androidx.compose.runtime.PrimitiveSnapshotStateKt;
import java.util.concurrent.atomic.AtomicReference;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlinx.coroutines.CoroutineScopeKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class CursorAnimationState {
    public final boolean animate;
    public final AtomicReference animationJob = new AtomicReference(null);
    public final MutableFloatState cursorAlpha$delegate = PrimitiveSnapshotStateKt.mutableFloatStateOf(0.0f);

    public CursorAnimationState(boolean z) {
        this.animate = z;
    }

    public final Object snapToVisibleAndAnimate(Continuation continuation) {
        Object coroutineScope = CoroutineScopeKt.coroutineScope(new CursorAnimationState$snapToVisibleAndAnimate$2(this, null), continuation);
        return coroutineScope == CoroutineSingletons.COROUTINE_SUSPENDED ? coroutineScope : Unit.INSTANCE;
    }
}
