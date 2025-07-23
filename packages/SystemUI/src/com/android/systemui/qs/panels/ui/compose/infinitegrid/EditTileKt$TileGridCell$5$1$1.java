package com.android.systemui.qs.panels.ui.compose.infinitegrid;

import androidx.compose.foundation.gestures.AnchoredDraggableKt;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import com.android.systemui.qs.panels.ui.compose.selection.QSDragAnchor;
import com.android.systemui.qs.panels.ui.compose.selection.ResizingState;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class EditTileKt$TileGridCell$5$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ ResizingState $resizingState;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public EditTileKt$TileGridCell$5$1$1(ResizingState resizingState, Continuation continuation) {
        super(2, continuation);
        this.$resizingState = resizingState;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new EditTileKt$TileGridCell$5$1$1(this.$resizingState, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((EditTileKt$TileGridCell$5$1$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object obj2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            ResizingState resizingState = this.$resizingState;
            this.label = 1;
            Object value = ((SnapshotMutableStateImpl) resizingState.anchoredDraggableState.currentValue$delegate).getValue();
            QSDragAnchor qSDragAnchor = QSDragAnchor.Icon;
            if (value == qSDragAnchor) {
                qSDragAnchor = QSDragAnchor.Large;
            }
            Object animateTo$default = AnchoredDraggableKt.animateTo$default(resizingState.anchoredDraggableState, qSDragAnchor, this);
            if (animateTo$default != obj2) {
                animateTo$default = Unit.INSTANCE;
            }
            if (animateTo$default != obj2) {
                animateTo$default = Unit.INSTANCE;
            }
            if (animateTo$default == obj2) {
                return obj2;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
