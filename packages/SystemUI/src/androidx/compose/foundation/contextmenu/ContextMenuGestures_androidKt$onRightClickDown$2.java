package androidx.compose.foundation.contextmenu;

import androidx.compose.ui.input.pointer.AwaitPointerEventScope;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class ContextMenuGestures_androidKt$onRightClickDown$2 extends RestrictedSuspendLambda implements Function2 {
    final /* synthetic */ Function1 $onDown;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ContextMenuGestures_androidKt$onRightClickDown$2(Function1 function1, Continuation continuation) {
        super(2, continuation);
        this.$onDown = function1;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        ContextMenuGestures_androidKt$onRightClickDown$2 contextMenuGestures_androidKt$onRightClickDown$2 = new ContextMenuGestures_androidKt$onRightClickDown$2(this.$onDown, continuation);
        contextMenuGestures_androidKt$onRightClickDown$2.L$0 = obj;
        return contextMenuGestures_androidKt$onRightClickDown$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ContextMenuGestures_androidKt$onRightClickDown$2) create((AwaitPointerEventScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x0050, code lost:
    
        if (r7 == r0) goto L15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0052, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0030, code lost:
    
        if (r7 == r0) goto L15;
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
            if (r1 == 0) goto L20
            if (r1 == r3) goto L18
            if (r1 != r2) goto L10
            kotlin.ResultKt.throwOnFailure(r7)
            goto L53
        L10:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L18:
            java.lang.Object r1 = r6.L$0
            androidx.compose.ui.input.pointer.AwaitPointerEventScope r1 = (androidx.compose.ui.input.pointer.AwaitPointerEventScope) r1
            kotlin.ResultKt.throwOnFailure(r7)
            goto L33
        L20:
            kotlin.ResultKt.throwOnFailure(r7)
            java.lang.Object r7 = r6.L$0
            r1 = r7
            androidx.compose.ui.input.pointer.AwaitPointerEventScope r1 = (androidx.compose.ui.input.pointer.AwaitPointerEventScope) r1
            r6.L$0 = r1
            r6.label = r3
            java.lang.Object r7 = androidx.compose.foundation.contextmenu.ContextMenuGestures_androidKt.access$awaitFirstRightClickDown(r1, r6)
            if (r7 != r0) goto L33
            goto L52
        L33:
            androidx.compose.ui.input.pointer.PointerInputChange r7 = (androidx.compose.ui.input.pointer.PointerInputChange) r7
            r7.consume()
            kotlin.jvm.functions.Function1 r3 = r6.$onDown
            long r4 = r7.position
            androidx.compose.ui.geometry.Offset r7 = androidx.compose.ui.geometry.Offset.m393boximpl(r4)
            r3.mo779invoke(r7)
            r7 = 0
            r6.L$0 = r7
            r6.label = r2
            kotlin.jvm.functions.Function3 r7 = androidx.compose.foundation.gestures.TapGestureDetectorKt.NoPressGesture
            androidx.compose.ui.input.pointer.PointerEventPass r7 = androidx.compose.ui.input.pointer.PointerEventPass.Main
            java.lang.Object r7 = androidx.compose.foundation.gestures.TapGestureDetectorKt.waitForUpOrCancellation(r1, r7, r6)
            if (r7 != r0) goto L53
        L52:
            return r0
        L53:
            androidx.compose.ui.input.pointer.PointerInputChange r7 = (androidx.compose.ui.input.pointer.PointerInputChange) r7
            if (r7 == 0) goto L5a
            r7.consume()
        L5a:
            kotlin.Unit r6 = kotlin.Unit.INSTANCE
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.contextmenu.ContextMenuGestures_androidKt$onRightClickDown$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
