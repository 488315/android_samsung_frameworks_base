package com.android.systemui.communal.ui.compose;

import androidx.compose.foundation.lazy.grid.LazyGridState;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
final class DragAndDropTargetState$onMoved$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Ref$ObjectRef<Integer> $scrollIndex;
    final /* synthetic */ Ref$ObjectRef<Integer> $scrollOffset;
    int label;
    final /* synthetic */ DragAndDropTargetState this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DragAndDropTargetState$onMoved$1$1(DragAndDropTargetState dragAndDropTargetState, Ref$ObjectRef<Integer> ref$ObjectRef, Ref$ObjectRef<Integer> ref$ObjectRef2, Continuation continuation) {
        super(2, continuation);
        this.this$0 = dragAndDropTargetState;
        this.$scrollIndex = ref$ObjectRef;
        this.$scrollOffset = ref$ObjectRef2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new DragAndDropTargetState$onMoved$1$1(this.this$0, this.$scrollIndex, this.$scrollOffset, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DragAndDropTargetState$onMoved$1$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            LazyGridState lazyGridState = this.this$0.state;
            int intValue = this.$scrollIndex.element.intValue();
            int intValue2 = this.$scrollOffset.element.intValue();
            this.label = 1;
            if (lazyGridState.scrollToItem(intValue, intValue2, this) == coroutineSingletons) {
                return coroutineSingletons;
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
