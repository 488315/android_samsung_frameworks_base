package com.android.compose.gesture;

import androidx.compose.ui.input.pointer.AwaitPointerEventScope;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class NestedDraggableNode$trackDownPosition$2 extends RestrictedSuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ NestedDraggableNode this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NestedDraggableNode$trackDownPosition$2(NestedDraggableNode nestedDraggableNode, Continuation continuation) {
        super(2, continuation);
        this.this$0 = nestedDraggableNode;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        NestedDraggableNode$trackDownPosition$2 nestedDraggableNode$trackDownPosition$2 = new NestedDraggableNode$trackDownPosition$2(this.this$0, continuation);
        nestedDraggableNode$trackDownPosition$2.L$0 = obj;
        return nestedDraggableNode$trackDownPosition$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((NestedDraggableNode$trackDownPosition$2) create((AwaitPointerEventScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:29:0x0062, code lost:
    
        if (r9 != r0) goto L24;
     */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:21:0x0062 -> B:8:0x0065). Please report as a decompilation issue!!! */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r9) {
        /*
            r8 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r8.label
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L27
            if (r1 == r3) goto L1f
            if (r1 != r2) goto L17
            java.lang.Object r1 = r8.L$0
            androidx.compose.ui.input.pointer.AwaitPointerEventScope r1 = (androidx.compose.ui.input.pointer.AwaitPointerEventScope) r1
            kotlin.ResultKt.throwOnFailure(r9)     // Catch: java.lang.Throwable -> L14
            goto L65
        L14:
            r9 = move-exception
            goto Lb9
        L17:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r9)
            throw r8
        L1f:
            java.lang.Object r1 = r8.L$0
            androidx.compose.ui.input.pointer.AwaitPointerEventScope r1 = (androidx.compose.ui.input.pointer.AwaitPointerEventScope) r1
            kotlin.ResultKt.throwOnFailure(r9)     // Catch: java.lang.Throwable -> L14
            goto L3b
        L27:
            kotlin.ResultKt.throwOnFailure(r9)
            java.lang.Object r9 = r8.L$0
            r1 = r9
            androidx.compose.ui.input.pointer.AwaitPointerEventScope r1 = (androidx.compose.ui.input.pointer.AwaitPointerEventScope) r1
            r8.L$0 = r1     // Catch: java.lang.Throwable -> L14
            r8.label = r3     // Catch: java.lang.Throwable -> L14
            r9 = 0
            java.lang.Object r9 = androidx.compose.foundation.gestures.TapGestureDetectorKt.awaitFirstDown$default(r1, r9, r8, r2)     // Catch: java.lang.Throwable -> L14
            if (r9 != r0) goto L3b
            goto L64
        L3b:
            androidx.compose.ui.input.pointer.PointerInputChange r9 = (androidx.compose.ui.input.pointer.PointerInputChange) r9     // Catch: java.lang.Throwable -> L14
            com.android.compose.gesture.NestedDraggableNode r3 = r8.this$0     // Catch: java.lang.Throwable -> L14
            long r4 = r9.position     // Catch: java.lang.Throwable -> L14
            androidx.compose.ui.geometry.Offset r4 = androidx.compose.ui.geometry.Offset.m393boximpl(r4)     // Catch: java.lang.Throwable -> L14
            r3.lastFirstDown = r4     // Catch: java.lang.Throwable -> L14
            com.android.compose.gesture.NestedDraggableNode r3 = r8.this$0     // Catch: java.lang.Throwable -> L14
            java.util.LinkedHashMap r3 = r3.pointersDown     // Catch: java.lang.Throwable -> L14
            long r4 = r9.id     // Catch: java.lang.Throwable -> L14
            androidx.compose.ui.input.pointer.PointerId r4 = androidx.compose.ui.input.pointer.PointerId.m590boximpl(r4)     // Catch: java.lang.Throwable -> L14
            int r9 = r9.type     // Catch: java.lang.Throwable -> L14
            androidx.compose.ui.input.pointer.PointerType r9 = androidx.compose.ui.input.pointer.PointerType.m597boximpl(r9)     // Catch: java.lang.Throwable -> L14
            r3.put(r4, r9)     // Catch: java.lang.Throwable -> L14
        L5a:
            r8.L$0 = r1     // Catch: java.lang.Throwable -> L14
            r8.label = r2     // Catch: java.lang.Throwable -> L14
            java.lang.Object r9 = androidx.compose.ui.input.pointer.AwaitPointerEventScope.awaitPointerEvent$default(r1, r8)     // Catch: java.lang.Throwable -> L14
            if (r9 != r0) goto L65
        L64:
            return r0
        L65:
            androidx.compose.ui.input.pointer.PointerEvent r9 = (androidx.compose.ui.input.pointer.PointerEvent) r9     // Catch: java.lang.Throwable -> L14
            java.util.List r9 = r9.changes     // Catch: java.lang.Throwable -> L14
            java.lang.Iterable r9 = (java.lang.Iterable) r9     // Catch: java.lang.Throwable -> L14
            com.android.compose.gesture.NestedDraggableNode r3 = r8.this$0     // Catch: java.lang.Throwable -> L14
            java.util.Iterator r9 = r9.iterator()     // Catch: java.lang.Throwable -> L14
        L71:
            boolean r4 = r9.hasNext()     // Catch: java.lang.Throwable -> L14
            if (r4 == 0) goto La5
            java.lang.Object r4 = r9.next()     // Catch: java.lang.Throwable -> L14
            androidx.compose.ui.input.pointer.PointerInputChange r4 = (androidx.compose.ui.input.pointer.PointerInputChange) r4     // Catch: java.lang.Throwable -> L14
            boolean r5 = androidx.compose.ui.input.pointer.PointerEventKt.changedToDownIgnoreConsumed(r4)     // Catch: java.lang.Throwable -> L14
            long r6 = r4.id
            if (r5 == 0) goto L95
            java.util.LinkedHashMap r5 = r3.pointersDown     // Catch: java.lang.Throwable -> L14
            androidx.compose.ui.input.pointer.PointerId r6 = androidx.compose.ui.input.pointer.PointerId.m590boximpl(r6)     // Catch: java.lang.Throwable -> L14
            int r4 = r4.type     // Catch: java.lang.Throwable -> L14
            androidx.compose.ui.input.pointer.PointerType r4 = androidx.compose.ui.input.pointer.PointerType.m597boximpl(r4)     // Catch: java.lang.Throwable -> L14
            r5.put(r6, r4)     // Catch: java.lang.Throwable -> L14
            goto L71
        L95:
            boolean r4 = androidx.compose.ui.input.pointer.PointerEventKt.changedToUpIgnoreConsumed(r4)     // Catch: java.lang.Throwable -> L14
            if (r4 == 0) goto L71
            java.util.LinkedHashMap r4 = r3.pointersDown     // Catch: java.lang.Throwable -> L14
            androidx.compose.ui.input.pointer.PointerId r5 = androidx.compose.ui.input.pointer.PointerId.m590boximpl(r6)     // Catch: java.lang.Throwable -> L14
            r4.remove(r5)     // Catch: java.lang.Throwable -> L14
            goto L71
        La5:
            com.android.compose.gesture.NestedDraggableNode r9 = r8.this$0     // Catch: java.lang.Throwable -> L14
            java.util.LinkedHashMap r9 = r9.pointersDown     // Catch: java.lang.Throwable -> L14
            int r9 = r9.size()     // Catch: java.lang.Throwable -> L14
            if (r9 > 0) goto L5a
            com.android.compose.gesture.NestedDraggableNode r8 = r8.this$0
            java.util.LinkedHashMap r8 = r8.pointersDown
            r8.clear()
            kotlin.Unit r8 = kotlin.Unit.INSTANCE
            return r8
        Lb9:
            com.android.compose.gesture.NestedDraggableNode r8 = r8.this$0
            java.util.LinkedHashMap r8 = r8.pointersDown
            r8.clear()
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.compose.gesture.NestedDraggableNode$trackDownPosition$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
