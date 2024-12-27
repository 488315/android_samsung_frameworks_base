package com.android.systemui.communal.ui.compose;

import androidx.compose.foundation.lazy.grid.LazyGridState;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class GridDragDropStateKt$rememberGridDragDropState$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ LazyGridState $gridState;
    final /* synthetic */ GridDragDropState $state;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GridDragDropStateKt$rememberGridDragDropState$1(GridDragDropState gridDragDropState, LazyGridState lazyGridState, Continuation continuation) {
        super(2, continuation);
        this.$state = gridDragDropState;
        this.$gridState = lazyGridState;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new GridDragDropStateKt$rememberGridDragDropState$1(this.$state, this.$gridState, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((GridDragDropStateKt$rememberGridDragDropState$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: Regions count limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0028 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0039 A[RETURN] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:10:0x0037 -> B:11:0x001c). Please report as a decompilation issue!!! */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r5) {
        /*
            r4 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r4.label
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L19
            if (r1 == r3) goto L15
            if (r1 != r2) goto Ld
            goto L19
        Ld:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L15:
            kotlin.ResultKt.throwOnFailure(r5)
            goto L29
        L19:
            kotlin.ResultKt.throwOnFailure(r5)
        L1c:
            com.android.systemui.communal.ui.compose.GridDragDropState r5 = r4.$state
            kotlinx.coroutines.channels.BufferedChannel r5 = r5.scrollChannel
            r4.label = r3
            java.lang.Object r5 = r5.receive(r4)
            if (r5 != r0) goto L29
            return r0
        L29:
            java.lang.Number r5 = (java.lang.Number) r5
            float r5 = r5.floatValue()
            androidx.compose.foundation.lazy.grid.LazyGridState r1 = r4.$gridState
            r4.label = r2
            java.lang.Object r5 = androidx.compose.foundation.gestures.ScrollExtensionsKt.scrollBy(r1, r5, r4)
            if (r5 != r0) goto L1c
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.ui.compose.GridDragDropStateKt$rememberGridDragDropState$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
