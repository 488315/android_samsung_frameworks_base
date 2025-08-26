package com.android.systemui.communal.ui.viewmodel;

import androidx.compose.foundation.gestures.AnchoredDraggableState;
import com.android.systemui.communal.ui.viewmodel.ResizeableItemFrameViewModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* loaded from: classes2.dex */
final class ResizeableItemFrameViewModel$onActivated$2$1 extends SuspendLambda implements Function2 {
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ ResizeableItemFrameViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ResizeableItemFrameViewModel$onActivated$2$1(ResizeableItemFrameViewModel resizeableItemFrameViewModel, Continuation continuation) {
        super(2, continuation);
        this.this$0 = resizeableItemFrameViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        ResizeableItemFrameViewModel$onActivated$2$1 resizeableItemFrameViewModel$onActivated$2$1 = new ResizeableItemFrameViewModel$onActivated$2$1(this.this$0, continuation);
        resizeableItemFrameViewModel$onActivated$2$1.L$0 = obj;
        return resizeableItemFrameViewModel$onActivated$2$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ResizeableItemFrameViewModel$onActivated$2$1) create((ResizeableItemFrameViewModel.GridLayoutInfo) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        ResizeableItemFrameViewModel.GridLayoutInfo gridLayoutInfo = (ResizeableItemFrameViewModel.GridLayoutInfo) this.L$0;
        ResizeableItemFrameViewModel resizeableItemFrameViewModel = this.this$0;
        AnchoredDraggableState.updateAnchors$default(resizeableItemFrameViewModel.topDragState, ResizeableItemFrameViewModel.access$calculateAnchorsForHandle(resizeableItemFrameViewModel, DragHandle.TOP, gridLayoutInfo));
        ResizeableItemFrameViewModel resizeableItemFrameViewModel2 = this.this$0;
        AnchoredDraggableState.updateAnchors$default(resizeableItemFrameViewModel2.bottomDragState, ResizeableItemFrameViewModel.access$calculateAnchorsForHandle(resizeableItemFrameViewModel2, DragHandle.BOTTOM, gridLayoutInfo));
        return Unit.INSTANCE;
    }
}
