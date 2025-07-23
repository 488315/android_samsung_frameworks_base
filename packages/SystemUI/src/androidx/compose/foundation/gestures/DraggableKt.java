package androidx.compose.foundation.gestures;

import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.ui.Modifier;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class DraggableKt {
    public static final Function3 NoOpOnDragStarted = new DraggableKt$NoOpOnDragStarted$1(null);
    public static final Function3 NoOpOnDragStopped = new DraggableKt$NoOpOnDragStopped$1(null);

    public static Modifier draggable$default(Modifier modifier, DraggableState draggableState, Orientation orientation, boolean z, MutableInteractionSource mutableInteractionSource, boolean z2, Function3 function3, Function3 function32, boolean z3, int i) {
        if ((i & 4) != 0) {
            z = true;
        }
        boolean z4 = z;
        if ((i & 8) != 0) {
            mutableInteractionSource = null;
        }
        return modifier.then(new DraggableElement(draggableState, orientation, z4, mutableInteractionSource, (i & 16) != 0 ? false : z2, (i & 32) != 0 ? NoOpOnDragStarted : function3, function32, (i & 128) != 0 ? false : z3));
    }

    public static final DraggableState rememberDraggableState(Composer composer, Function1 function1) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.foundation.gestures.rememberDraggableState (Draggable.kt:127)");
        }
        final MutableState rememberUpdatedState = SnapshotStateKt.rememberUpdatedState(function1, composer);
        ComposerImpl composerImpl = (ComposerImpl) composer;
        Object rememberedValue = composerImpl.rememberedValue();
        Composer.Companion.getClass();
        if (rememberedValue == Composer.Companion.Empty) {
            DefaultDraggableState defaultDraggableState = new DefaultDraggableState(new Function1() { // from class: androidx.compose.foundation.gestures.DraggableKt$rememberDraggableState$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo779invoke(Object obj) {
                    ((Function1) rememberUpdatedState.getValue()).mo779invoke(Float.valueOf(((Number) obj).floatValue()));
                    return Unit.INSTANCE;
                }
            });
            composerImpl.updateRememberedValue(defaultDraggableState);
            rememberedValue = defaultDraggableState;
        }
        DraggableState draggableState = (DraggableState) rememberedValue;
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return draggableState;
    }
}
