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
final class DragGestureDetectorKt$detectHorizontalDragGestures$5 extends RestrictedSuspendLambda implements Function2 {
    final /* synthetic */ Function0 $onDragCancel;
    final /* synthetic */ Function0 $onDragEnd;
    final /* synthetic */ Function1 $onDragStart;
    final /* synthetic */ Function2 $onHorizontalDrag;
    private /* synthetic */ Object L$0;
    Object L$1;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DragGestureDetectorKt$detectHorizontalDragGestures$5(Function1 function1, Function2 function2, Function0 function0, Function0 function02, Continuation continuation) {
        super(2, continuation);
        this.$onDragStart = function1;
        this.$onHorizontalDrag = function2;
        this.$onDragEnd = function0;
        this.$onDragCancel = function02;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        DragGestureDetectorKt$detectHorizontalDragGestures$5 dragGestureDetectorKt$detectHorizontalDragGestures$5 = new DragGestureDetectorKt$detectHorizontalDragGestures$5(this.$onDragStart, this.$onHorizontalDrag, this.$onDragEnd, this.$onDragCancel, continuation);
        dragGestureDetectorKt$detectHorizontalDragGestures$5.L$0 = obj;
        return dragGestureDetectorKt$detectHorizontalDragGestures$5;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DragGestureDetectorKt$detectHorizontalDragGestures$5) create((AwaitPointerEventScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0094, code lost:
    
        if (r12 == r0) goto L24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0044, code lost:
    
        if (r12 == r0) goto L24;
     */
    /* JADX WARN: Removed duplicated region for block: B:19:0x006a  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r12) {
        /*
            r11 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r11.label
            r2 = 0
            r3 = 2
            r4 = 3
            r5 = 1
            if (r1 == 0) goto L34
            if (r1 == r5) goto L2b
            if (r1 == r3) goto L1e
            if (r1 != r4) goto L16
            kotlin.ResultKt.throwOnFailure(r12)
            r10 = r11
            goto L97
        L16:
            java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
            java.lang.String r12 = "call to 'resume' before 'invoke' with coroutine"
            r11.<init>(r12)
            throw r11
        L1e:
            java.lang.Object r1 = r11.L$1
            kotlin.jvm.internal.Ref$FloatRef r1 = (kotlin.jvm.internal.Ref$FloatRef) r1
            java.lang.Object r3 = r11.L$0
            androidx.compose.ui.input.pointer.AwaitPointerEventScope r3 = (androidx.compose.ui.input.pointer.AwaitPointerEventScope) r3
            kotlin.ResultKt.throwOnFailure(r12)
            r10 = r11
            goto L66
        L2b:
            java.lang.Object r1 = r11.L$0
            androidx.compose.ui.input.pointer.AwaitPointerEventScope r1 = (androidx.compose.ui.input.pointer.AwaitPointerEventScope) r1
            kotlin.ResultKt.throwOnFailure(r12)
        L32:
            r5 = r1
            goto L47
        L34:
            kotlin.ResultKt.throwOnFailure(r12)
            java.lang.Object r12 = r11.L$0
            r1 = r12
            androidx.compose.ui.input.pointer.AwaitPointerEventScope r1 = (androidx.compose.ui.input.pointer.AwaitPointerEventScope) r1
            r11.L$0 = r1
            r11.label = r5
            java.lang.Object r12 = androidx.compose.foundation.gestures.TapGestureDetectorKt.awaitFirstDown$default(r1, r2, r11, r3)
            if (r12 != r0) goto L32
            goto L96
        L47:
            androidx.compose.ui.input.pointer.PointerInputChange r12 = (androidx.compose.ui.input.pointer.PointerInputChange) r12
            kotlin.jvm.internal.Ref$FloatRef r1 = new kotlin.jvm.internal.Ref$FloatRef
            r1.<init>()
            long r6 = r12.id
            androidx.compose.foundation.gestures.DragGestureDetectorKt$detectHorizontalDragGestures$5$drag$1 r9 = new androidx.compose.foundation.gestures.DragGestureDetectorKt$detectHorizontalDragGestures$5$drag$1
            r9.<init>()
            r11.L$0 = r5
            r11.L$1 = r1
            r11.label = r3
            int r8 = r12.type
            r10 = r11
            java.lang.Object r12 = androidx.compose.foundation.gestures.DragGestureDetectorKt.m67awaitHorizontalPointerSlopOrCancellationgDDlDlE(r5, r6, r8, r9, r10)
            if (r12 != r0) goto L65
            goto L96
        L65:
            r3 = r5
        L66:
            androidx.compose.ui.input.pointer.PointerInputChange r12 = (androidx.compose.ui.input.pointer.PointerInputChange) r12
            if (r12 == 0) goto Laa
            kotlin.jvm.functions.Function1 r11 = r10.$onDragStart
            long r5 = r12.position
            androidx.compose.ui.geometry.Offset r5 = androidx.compose.ui.geometry.Offset.m393boximpl(r5)
            r11.mo779invoke(r5)
            kotlin.jvm.functions.Function2 r11 = r10.$onHorizontalDrag
            float r1 = r1.element
            java.lang.Float r5 = new java.lang.Float
            r5.<init>(r1)
            r11.invoke(r12, r5)
            androidx.compose.foundation.gestures.DragGestureDetectorKt$detectHorizontalDragGestures$5$1 r11 = new androidx.compose.foundation.gestures.DragGestureDetectorKt$detectHorizontalDragGestures$5$1
            kotlin.jvm.functions.Function2 r1 = r10.$onHorizontalDrag
            r11.<init>()
            r10.L$0 = r2
            r10.L$1 = r2
            r10.label = r4
            long r1 = r12.id
            java.lang.Object r12 = androidx.compose.foundation.gestures.DragGestureDetectorKt.m72horizontalDragjO51t88(r3, r1, r11, r10)
            if (r12 != r0) goto L97
        L96:
            return r0
        L97:
            java.lang.Boolean r12 = (java.lang.Boolean) r12
            boolean r11 = r12.booleanValue()
            if (r11 == 0) goto La5
            kotlin.jvm.functions.Function0 r11 = r10.$onDragEnd
            r11.invoke()
            goto Laa
        La5:
            kotlin.jvm.functions.Function0 r11 = r10.$onDragCancel
            r11.invoke()
        Laa:
            kotlin.Unit r11 = kotlin.Unit.INSTANCE
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.gestures.DragGestureDetectorKt$detectHorizontalDragGestures$5.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
