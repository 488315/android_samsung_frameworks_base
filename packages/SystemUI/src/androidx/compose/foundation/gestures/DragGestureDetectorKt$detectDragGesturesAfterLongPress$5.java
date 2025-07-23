package androidx.compose.foundation.gestures;

import androidx.compose.ui.input.pointer.AwaitPointerEventScope;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class DragGestureDetectorKt$detectDragGesturesAfterLongPress$5 extends RestrictedSuspendLambda implements Function2 {
    final /* synthetic */ Function2 $onDrag;
    final /* synthetic */ Function0 $onDragCancel;
    final /* synthetic */ Function0 $onDragEnd;
    final /* synthetic */ Function1 $onDragStart;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DragGestureDetectorKt$detectDragGesturesAfterLongPress$5(Function1 function1, Function0 function0, Function0 function02, Function2 function2, Continuation continuation) {
        super(2, continuation);
        this.$onDragStart = function1;
        this.$onDragEnd = function0;
        this.$onDragCancel = function02;
        this.$onDrag = function2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        DragGestureDetectorKt$detectDragGesturesAfterLongPress$5 dragGestureDetectorKt$detectDragGesturesAfterLongPress$5 = new DragGestureDetectorKt$detectDragGesturesAfterLongPress$5(this.$onDragStart, this.$onDragEnd, this.$onDragCancel, this.$onDrag, continuation);
        dragGestureDetectorKt$detectDragGesturesAfterLongPress$5.L$0 = obj;
        return dragGestureDetectorKt$detectDragGesturesAfterLongPress$5;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DragGestureDetectorKt$detectDragGesturesAfterLongPress$5) create((AwaitPointerEventScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0081 A[Catch: CancellationException -> 0x0017, TryCatch #0 {CancellationException -> 0x0017, blocks: (B:8:0x0013, B:9:0x0079, B:11:0x0081, B:13:0x0091, B:15:0x009d, B:17:0x00a0, B:20:0x00a3, B:24:0x00a9, B:28:0x0026, B:29:0x0055, B:31:0x0059, B:36:0x002e, B:37:0x0046, B:41:0x003a), top: B:2:0x0007 }] */
    /* JADX WARN: Removed duplicated region for block: B:24:0x00a9 A[Catch: CancellationException -> 0x0017, TRY_LEAVE, TryCatch #0 {CancellationException -> 0x0017, blocks: (B:8:0x0013, B:9:0x0079, B:11:0x0081, B:13:0x0091, B:15:0x009d, B:17:0x00a0, B:20:0x00a3, B:24:0x00a9, B:28:0x0026, B:29:0x0055, B:31:0x0059, B:36:0x002e, B:37:0x0046, B:41:0x003a), top: B:2:0x0007 }] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0059 A[Catch: CancellationException -> 0x0017, TryCatch #0 {CancellationException -> 0x0017, blocks: (B:8:0x0013, B:9:0x0079, B:11:0x0081, B:13:0x0091, B:15:0x009d, B:17:0x00a0, B:20:0x00a3, B:24:0x00a9, B:28:0x0026, B:29:0x0055, B:31:0x0059, B:36:0x002e, B:37:0x0046, B:41:0x003a), top: B:2:0x0007 }] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r8) {
        /*
            r7 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r7.label
            r2 = 1
            r3 = 2
            r4 = 3
            if (r1 == 0) goto L32
            if (r1 == r2) goto L2a
            if (r1 == r3) goto L22
            if (r1 != r4) goto L1a
            java.lang.Object r0 = r7.L$0
            androidx.compose.ui.input.pointer.AwaitPointerEventScope r0 = (androidx.compose.ui.input.pointer.AwaitPointerEventScope) r0
            kotlin.ResultKt.throwOnFailure(r8)     // Catch: java.util.concurrent.CancellationException -> L17
            goto L79
        L17:
            r8 = move-exception
            goto Lb1
        L1a:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r8)
            throw r7
        L22:
            java.lang.Object r1 = r7.L$0
            androidx.compose.ui.input.pointer.AwaitPointerEventScope r1 = (androidx.compose.ui.input.pointer.AwaitPointerEventScope) r1
            kotlin.ResultKt.throwOnFailure(r8)     // Catch: java.util.concurrent.CancellationException -> L17
            goto L55
        L2a:
            java.lang.Object r1 = r7.L$0
            androidx.compose.ui.input.pointer.AwaitPointerEventScope r1 = (androidx.compose.ui.input.pointer.AwaitPointerEventScope) r1
            kotlin.ResultKt.throwOnFailure(r8)     // Catch: java.util.concurrent.CancellationException -> L17
            goto L46
        L32:
            kotlin.ResultKt.throwOnFailure(r8)
            java.lang.Object r8 = r7.L$0
            r1 = r8
            androidx.compose.ui.input.pointer.AwaitPointerEventScope r1 = (androidx.compose.ui.input.pointer.AwaitPointerEventScope) r1
            r7.L$0 = r1     // Catch: java.util.concurrent.CancellationException -> L17
            r7.label = r2     // Catch: java.util.concurrent.CancellationException -> L17
            r8 = 0
            java.lang.Object r8 = androidx.compose.foundation.gestures.TapGestureDetectorKt.awaitFirstDown$default(r1, r8, r7, r3)     // Catch: java.util.concurrent.CancellationException -> L17
            if (r8 != r0) goto L46
            goto L77
        L46:
            androidx.compose.ui.input.pointer.PointerInputChange r8 = (androidx.compose.ui.input.pointer.PointerInputChange) r8     // Catch: java.util.concurrent.CancellationException -> L17
            long r5 = r8.id     // Catch: java.util.concurrent.CancellationException -> L17
            r7.L$0 = r1     // Catch: java.util.concurrent.CancellationException -> L17
            r7.label = r3     // Catch: java.util.concurrent.CancellationException -> L17
            java.lang.Object r8 = androidx.compose.foundation.gestures.DragGestureDetectorKt.m69awaitLongPressOrCancellationrnUCldI(r1, r5, r7)     // Catch: java.util.concurrent.CancellationException -> L17
            if (r8 != r0) goto L55
            goto L77
        L55:
            androidx.compose.ui.input.pointer.PointerInputChange r8 = (androidx.compose.ui.input.pointer.PointerInputChange) r8     // Catch: java.util.concurrent.CancellationException -> L17
            if (r8 == 0) goto Lae
            kotlin.jvm.functions.Function1 r2 = r7.$onDragStart     // Catch: java.util.concurrent.CancellationException -> L17
            long r5 = r8.position     // Catch: java.util.concurrent.CancellationException -> L17
            androidx.compose.ui.geometry.Offset r3 = androidx.compose.ui.geometry.Offset.m393boximpl(r5)     // Catch: java.util.concurrent.CancellationException -> L17
            r2.mo779invoke(r3)     // Catch: java.util.concurrent.CancellationException -> L17
            long r2 = r8.id     // Catch: java.util.concurrent.CancellationException -> L17
            androidx.compose.foundation.gestures.DragGestureDetectorKt$detectDragGesturesAfterLongPress$5$1 r8 = new androidx.compose.foundation.gestures.DragGestureDetectorKt$detectDragGesturesAfterLongPress$5$1     // Catch: java.util.concurrent.CancellationException -> L17
            kotlin.jvm.functions.Function2 r5 = r7.$onDrag     // Catch: java.util.concurrent.CancellationException -> L17
            r8.<init>()     // Catch: java.util.concurrent.CancellationException -> L17
            r7.L$0 = r1     // Catch: java.util.concurrent.CancellationException -> L17
            r7.label = r4     // Catch: java.util.concurrent.CancellationException -> L17
            java.lang.Object r8 = androidx.compose.foundation.gestures.DragGestureDetectorKt.m71dragjO51t88(r1, r2, r8, r7)     // Catch: java.util.concurrent.CancellationException -> L17
            if (r8 != r0) goto L78
        L77:
            return r0
        L78:
            r0 = r1
        L79:
            java.lang.Boolean r8 = (java.lang.Boolean) r8     // Catch: java.util.concurrent.CancellationException -> L17
            boolean r8 = r8.booleanValue()     // Catch: java.util.concurrent.CancellationException -> L17
            if (r8 == 0) goto La9
            androidx.compose.ui.input.pointer.PointerEvent r8 = r0.getCurrentEvent()     // Catch: java.util.concurrent.CancellationException -> L17
            java.util.List r8 = r8.changes     // Catch: java.util.concurrent.CancellationException -> L17
            r0 = r8
            java.util.Collection r0 = (java.util.Collection) r0     // Catch: java.util.concurrent.CancellationException -> L17
            int r0 = r0.size()     // Catch: java.util.concurrent.CancellationException -> L17
            r1 = 0
        L8f:
            if (r1 >= r0) goto La3
            java.lang.Object r2 = r8.get(r1)     // Catch: java.util.concurrent.CancellationException -> L17
            androidx.compose.ui.input.pointer.PointerInputChange r2 = (androidx.compose.ui.input.pointer.PointerInputChange) r2     // Catch: java.util.concurrent.CancellationException -> L17
            boolean r3 = androidx.compose.ui.input.pointer.PointerEventKt.changedToUp(r2)     // Catch: java.util.concurrent.CancellationException -> L17
            if (r3 == 0) goto La0
            r2.consume()     // Catch: java.util.concurrent.CancellationException -> L17
        La0:
            int r1 = r1 + 1
            goto L8f
        La3:
            kotlin.jvm.functions.Function0 r8 = r7.$onDragEnd     // Catch: java.util.concurrent.CancellationException -> L17
            r8.invoke()     // Catch: java.util.concurrent.CancellationException -> L17
            goto Lae
        La9:
            kotlin.jvm.functions.Function0 r8 = r7.$onDragCancel     // Catch: java.util.concurrent.CancellationException -> L17
            r8.invoke()     // Catch: java.util.concurrent.CancellationException -> L17
        Lae:
            kotlin.Unit r7 = kotlin.Unit.INSTANCE
            return r7
        Lb1:
            kotlin.jvm.functions.Function0 r7 = r7.$onDragCancel
            r7.invoke()
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.gestures.DragGestureDetectorKt$detectDragGesturesAfterLongPress$5.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
