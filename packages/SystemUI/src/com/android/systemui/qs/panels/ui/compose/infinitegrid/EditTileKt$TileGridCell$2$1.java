package com.android.systemui.qs.panels.ui.compose.infinitegrid;

import com.android.systemui.qs.panels.ui.compose.selection.ResizingState;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class EditTileKt$TileGridCell$2$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Function1 $onResize;
    final /* synthetic */ ResizingState $resizingState;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public EditTileKt$TileGridCell$2$1(Function1 function1, ResizingState resizingState, Continuation continuation) {
        super(2, continuation);
        this.$onResize = function1;
        this.$resizingState = resizingState;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new EditTileKt$TileGridCell$2$1(this.$onResize, this.$resizingState, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((EditTileKt$TileGridCell$2$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        this.$onResize.mo779invoke((ResizingState.ResizeOperation.TemporaryResizeOperation) this.$resizingState.temporaryResizeOperation$delegate.getValue());
        return Unit.INSTANCE;
    }
}
