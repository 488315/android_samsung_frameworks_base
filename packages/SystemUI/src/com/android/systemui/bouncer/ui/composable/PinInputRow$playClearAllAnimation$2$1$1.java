package com.android.systemui.bouncer.ui.composable;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class PinInputRow$playClearAllAnimation$2$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ PinInputEntry $entry;
    final /* synthetic */ int $index;
    int label;
    final /* synthetic */ PinInputRow this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PinInputRow$playClearAllAnimation$2$1$1(PinInputRow pinInputRow, int i, PinInputEntry pinInputEntry, Continuation continuation) {
        super(2, continuation);
        this.this$0 = pinInputRow;
        this.$index = i;
        this.$entry = pinInputEntry;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new PinInputRow$playClearAllAnimation$2$1$1(this.this$0, this.$index, this.$entry, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((PinInputRow$playClearAllAnimation$2$1$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0045, code lost:
    
        if (kotlinx.coroutines.CoroutineScopeKt.coroutineScope(new com.android.systemui.bouncer.ui.composable.PinInputEntry$animateClearAllCollapse$2(r7, null), r6) == r0) goto L15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0047, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0031, code lost:
    
        if (kotlinx.coroutines.DelayKt.m3449delayVtjQ1oo(r4, r6) == r0) goto L15;
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
            if (r1 == 0) goto L1c
            if (r1 == r3) goto L18
            if (r1 != r2) goto L10
            kotlin.ResultKt.throwOnFailure(r7)
            goto L48
        L10:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L18:
            kotlin.ResultKt.throwOnFailure(r7)
            goto L34
        L1c:
            kotlin.ResultKt.throwOnFailure(r7)
            com.android.systemui.bouncer.ui.composable.PinInputRow r7 = r6.this$0
            com.android.systemui.bouncer.ui.composable.ShapeAnimations r7 = r7.shapeAnimations
            long r4 = r7.dismissStaggerDelay
            int r7 = r6.$index
            long r4 = kotlin.time.Duration.m3442timesUwyO8pc(r7, r4)
            r6.label = r3
            java.lang.Object r7 = kotlinx.coroutines.DelayKt.m3449delayVtjQ1oo(r4, r6)
            if (r7 != r0) goto L34
            goto L47
        L34:
            com.android.systemui.bouncer.ui.composable.PinInputEntry r7 = r6.$entry
            r6.label = r2
            r7.getClass()
            com.android.systemui.bouncer.ui.composable.PinInputEntry$animateClearAllCollapse$2 r1 = new com.android.systemui.bouncer.ui.composable.PinInputEntry$animateClearAllCollapse$2
            r2 = 0
            r1.<init>(r7, r2)
            java.lang.Object r6 = kotlinx.coroutines.CoroutineScopeKt.coroutineScope(r1, r6)
            if (r6 != r0) goto L48
        L47:
            return r0
        L48:
            kotlin.Unit r6 = kotlin.Unit.INSTANCE
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.bouncer.ui.composable.PinInputRow$playClearAllAnimation$2$1$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
