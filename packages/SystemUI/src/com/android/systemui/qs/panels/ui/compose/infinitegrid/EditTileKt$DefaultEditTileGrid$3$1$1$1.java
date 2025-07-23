package com.android.systemui.qs.panels.ui.compose.infinitegrid;

import androidx.compose.animation.core.SpringSpec;
import androidx.compose.foundation.ScrollState;
import androidx.compose.foundation.gestures.ScrollExtensionsKt;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import com.android.systemui.qs.panels.ui.compose.DragType;
import com.android.systemui.qs.panels.ui.compose.EditTileListState;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class EditTileKt$DefaultEditTileGrid$3$1$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ EditTileListState $listState;
    final /* synthetic */ ScrollState $scrollState;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public EditTileKt$DefaultEditTileGrid$3$1$1$1(EditTileListState editTileListState, ScrollState scrollState, Continuation continuation) {
        super(2, continuation);
        this.$listState = editTileListState;
        this.$scrollState = scrollState;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new EditTileKt$DefaultEditTileGrid$3$1$1$1(this.$listState, this.$scrollState, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((EditTileKt$DefaultEditTileGrid$3$1$1$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object obj2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            if (this.$listState.getDraggedCell() != null && ((DragType) ((SnapshotMutableStateImpl) this.$listState.dragType$delegate).getValue()) == DragType.Add) {
                ScrollState scrollState = this.$scrollState;
                this.label = 1;
                ScrollState.Companion companion = ScrollState.Companion;
                Object animateScrollBy = ScrollExtensionsKt.animateScrollBy(scrollState, 0 - scrollState.getValue(), new SpringSpec(0.0f, 0.0f, null, 7, null), this);
                if (animateScrollBy != obj2) {
                    animateScrollBy = Unit.INSTANCE;
                }
                if (animateScrollBy == obj2) {
                    return obj2;
                }
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
