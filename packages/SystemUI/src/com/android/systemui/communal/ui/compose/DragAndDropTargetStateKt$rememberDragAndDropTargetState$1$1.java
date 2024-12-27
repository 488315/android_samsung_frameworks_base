package com.android.systemui.communal.ui.compose;

import androidx.compose.foundation.lazy.grid.LazyGridState;
import androidx.compose.runtime.MutableFloatState;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

final class DragAndDropTargetStateKt$rememberDragAndDropTargetState$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ MutableFloatState $autoScrollSpeed;
    final /* synthetic */ LazyGridState $gridState;
    private /* synthetic */ Object L$0;
    int label;

    public DragAndDropTargetStateKt$rememberDragAndDropTargetState$1$1(MutableFloatState mutableFloatState, LazyGridState lazyGridState, Continuation continuation) {
        super(2, continuation);
        this.$autoScrollSpeed = mutableFloatState;
        this.$gridState = lazyGridState;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        DragAndDropTargetStateKt$rememberDragAndDropTargetState$1$1 dragAndDropTargetStateKt$rememberDragAndDropTargetState$1$1 = new DragAndDropTargetStateKt$rememberDragAndDropTargetState$1$1(this.$autoScrollSpeed, this.$gridState, continuation);
        dragAndDropTargetStateKt$rememberDragAndDropTargetState$1$1.L$0 = obj;
        return dragAndDropTargetStateKt$rememberDragAndDropTargetState$1$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DragAndDropTargetStateKt$rememberDragAndDropTargetState$1$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x0037, code lost:
    
        if (((androidx.compose.runtime.SnapshotMutableFloatStateImpl) r6.$autoScrollSpeed).getFloatValue() == 0.0f) goto L23;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r7) {
        /*
            r6 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r6.label
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L25
            if (r1 == r3) goto L1d
            if (r1 != r2) goto L15
            java.lang.Object r1 = r6.L$0
            kotlinx.coroutines.CoroutineScope r1 = (kotlinx.coroutines.CoroutineScope) r1
            kotlin.ResultKt.throwOnFailure(r7)
        L13:
            r7 = r1
            goto L3a
        L15:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L1d:
            java.lang.Object r1 = r6.L$0
            kotlinx.coroutines.CoroutineScope r1 = (kotlinx.coroutines.CoroutineScope) r1
            kotlin.ResultKt.throwOnFailure(r7)
            goto L56
        L25:
            kotlin.ResultKt.throwOnFailure(r7)
            java.lang.Object r7 = r6.L$0
            kotlinx.coroutines.CoroutineScope r7 = (kotlinx.coroutines.CoroutineScope) r7
            androidx.compose.runtime.MutableFloatState r1 = r6.$autoScrollSpeed
            androidx.compose.runtime.SnapshotMutableFloatStateImpl r1 = (androidx.compose.runtime.SnapshotMutableFloatStateImpl) r1
            float r1 = r1.getFloatValue()
            r4 = 0
            int r1 = (r1 > r4 ? 1 : (r1 == r4 ? 0 : -1))
            if (r1 != 0) goto L3a
            goto L63
        L3a:
            boolean r1 = kotlinx.coroutines.CoroutineScopeKt.isActive(r7)
            if (r1 == 0) goto L63
            androidx.compose.foundation.lazy.grid.LazyGridState r1 = r6.$gridState
            androidx.compose.runtime.MutableFloatState r4 = r6.$autoScrollSpeed
            androidx.compose.runtime.SnapshotMutableFloatStateImpl r4 = (androidx.compose.runtime.SnapshotMutableFloatStateImpl) r4
            float r4 = r4.getFloatValue()
            r6.L$0 = r7
            r6.label = r3
            java.lang.Object r1 = androidx.compose.foundation.gestures.ScrollExtensionsKt.scrollBy(r1, r4, r6)
            if (r1 != r0) goto L55
            return r0
        L55:
            r1 = r7
        L56:
            r6.L$0 = r1
            r6.label = r2
            r4 = 10
            java.lang.Object r7 = kotlinx.coroutines.DelayKt.delay(r4, r6)
            if (r7 != r0) goto L13
            return r0
        L63:
            kotlin.Unit r6 = kotlin.Unit.INSTANCE
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.ui.compose.DragAndDropTargetStateKt$rememberDragAndDropTargetState$1$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
