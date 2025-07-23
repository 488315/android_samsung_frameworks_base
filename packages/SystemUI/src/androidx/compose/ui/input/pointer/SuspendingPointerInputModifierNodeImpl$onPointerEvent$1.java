package androidx.compose.ui.input.pointer;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class SuspendingPointerInputModifierNodeImpl$onPointerEvent$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ SuspendingPointerInputModifierNodeImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SuspendingPointerInputModifierNodeImpl$onPointerEvent$1(SuspendingPointerInputModifierNodeImpl suspendingPointerInputModifierNodeImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = suspendingPointerInputModifierNodeImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new SuspendingPointerInputModifierNodeImpl$onPointerEvent$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SuspendingPointerInputModifierNodeImpl$onPointerEvent$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x002d, code lost:
    
        if (r1.invoke(r5, r4) == r0) goto L17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x003a, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0038, code lost:
    
        if (r1.invoke(r5, r4) == r0) goto L17;
     */
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
            goto L15
        Ld:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L15:
            kotlin.ResultKt.throwOnFailure(r5)
            goto L3b
        L19:
            kotlin.ResultKt.throwOnFailure(r5)
            androidx.compose.ui.input.pointer.SuspendingPointerInputModifierNodeImpl r5 = r4.this$0
            kotlin.jvm.functions.Function2 r1 = r5._deprecatedPointerInputHandler
            if (r1 == 0) goto L30
            r1.getClass()
            androidx.compose.ui.input.pointer.SuspendingPointerInputModifierNodeImpl r5 = r4.this$0
            r4.label = r3
            java.lang.Object r4 = r1.invoke(r5, r4)
            if (r4 != r0) goto L3b
            goto L3a
        L30:
            androidx.compose.ui.input.pointer.PointerInputEventHandler r1 = r5._pointerInputEventHandler
            r4.label = r2
            java.lang.Object r4 = r1.invoke(r5, r4)
            if (r4 != r0) goto L3b
        L3a:
            return r0
        L3b:
            kotlin.Unit r4 = kotlin.Unit.INSTANCE
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.input.pointer.SuspendingPointerInputModifierNodeImpl$onPointerEvent$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
