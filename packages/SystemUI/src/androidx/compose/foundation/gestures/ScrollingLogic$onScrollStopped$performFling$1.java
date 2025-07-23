package androidx.compose.foundation.gestures;

import androidx.compose.ui.unit.Velocity;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class ScrollingLogic$onScrollStopped$performFling$1 extends SuspendLambda implements Function2 {
    /* synthetic */ long J$0;
    long J$1;
    int label;
    final /* synthetic */ ScrollingLogic this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ScrollingLogic$onScrollStopped$performFling$1(ScrollingLogic scrollingLogic, Continuation continuation) {
        super(2, continuation);
        this.this$0 = scrollingLogic;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        ScrollingLogic$onScrollStopped$performFling$1 scrollingLogic$onScrollStopped$performFling$1 = new ScrollingLogic$onScrollStopped$performFling$1(this.this$0, continuation);
        scrollingLogic$onScrollStopped$performFling$1.J$0 = ((Velocity) obj).packedValue;
        return scrollingLogic$onScrollStopped$performFling$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ScrollingLogic$onScrollStopped$performFling$1) create(Velocity.m876boximpl(((Velocity) obj).packedValue), (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x007b, code lost:
    
        if (r0 != r6) goto L23;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r14) {
        /*
            r13 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r6 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r0 = r13.label
            r1 = 3
            r2 = 2
            r3 = 1
            if (r0 == 0) goto L33
            if (r0 == r3) goto L2c
            if (r0 == r2) goto L23
            if (r0 != r1) goto L1b
            long r0 = r13.J$1
            long r2 = r13.J$0
            kotlin.ResultKt.throwOnFailure(r14)
            r7 = r2
            r3 = r0
            r0 = r14
            goto L7e
        L1b:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L23:
            long r2 = r13.J$1
            long r7 = r13.J$0
            kotlin.ResultKt.throwOnFailure(r14)
            r0 = r14
            goto L62
        L2c:
            long r3 = r13.J$0
            kotlin.ResultKt.throwOnFailure(r14)
            r0 = r14
            goto L48
        L33:
            kotlin.ResultKt.throwOnFailure(r14)
            long r7 = r13.J$0
            androidx.compose.foundation.gestures.ScrollingLogic r0 = r13.this$0
            androidx.compose.ui.input.nestedscroll.NestedScrollDispatcher r0 = r0.nestedScrollDispatcher
            r13.J$0 = r7
            r13.label = r3
            java.lang.Object r0 = r0.m582dispatchPreFlingQWom1Mo(r7, r13)
            if (r0 != r6) goto L47
            goto L7d
        L47:
            r3 = r7
        L48:
            androidx.compose.ui.unit.Velocity r0 = (androidx.compose.ui.unit.Velocity) r0
            long r7 = r0.packedValue
            long r7 = androidx.compose.ui.unit.Velocity.m880minusAH228Gc(r3, r7)
            androidx.compose.foundation.gestures.ScrollingLogic r0 = r13.this$0
            r13.J$0 = r3
            r13.J$1 = r7
            r13.label = r2
            java.lang.Object r0 = r0.m81doFlingAnimationQWom1Mo(r7, r13)
            if (r0 != r6) goto L5f
            goto L7d
        L5f:
            r11 = r7
            r7 = r3
            r2 = r11
        L62:
            androidx.compose.ui.unit.Velocity r0 = (androidx.compose.ui.unit.Velocity) r0
            long r9 = r0.packedValue
            androidx.compose.foundation.gestures.ScrollingLogic r0 = r13.this$0
            androidx.compose.ui.input.nestedscroll.NestedScrollDispatcher r0 = r0.nestedScrollDispatcher
            long r2 = androidx.compose.ui.unit.Velocity.m880minusAH228Gc(r2, r9)
            r13.J$0 = r7
            r13.J$1 = r9
            r13.label = r1
            r5 = r13
            r1 = r2
            r3 = r9
            java.lang.Object r0 = r0.m580dispatchPostFlingRZ2iAVY(r1, r3, r5)
            if (r0 != r6) goto L7e
        L7d:
            return r6
        L7e:
            androidx.compose.ui.unit.Velocity r0 = (androidx.compose.ui.unit.Velocity) r0
            long r0 = r0.packedValue
            long r0 = androidx.compose.ui.unit.Velocity.m880minusAH228Gc(r3, r0)
            long r0 = androidx.compose.ui.unit.Velocity.m880minusAH228Gc(r7, r0)
            androidx.compose.ui.unit.Velocity r0 = androidx.compose.ui.unit.Velocity.m876boximpl(r0)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.gestures.ScrollingLogic$onScrollStopped$performFling$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
