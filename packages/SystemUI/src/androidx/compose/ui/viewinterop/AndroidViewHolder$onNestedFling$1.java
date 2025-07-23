package androidx.compose.ui.viewinterop;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class AndroidViewHolder$onNestedFling$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ boolean $consumed;
    final /* synthetic */ long $viewVelocity;
    int label;
    final /* synthetic */ AndroidViewHolder this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AndroidViewHolder$onNestedFling$1(boolean z, AndroidViewHolder androidViewHolder, long j, Continuation continuation) {
        super(2, continuation);
        this.$consumed = z;
        this.this$0 = androidViewHolder;
        this.$viewVelocity = j;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new AndroidViewHolder$onNestedFling$1(this.$consumed, this.this$0, this.$viewVelocity, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AndroidViewHolder$onNestedFling$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0034, code lost:
    
        if (r4.m580dispatchPostFlingRZ2iAVY(0, r7, r10) == r0) goto L17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x004e, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x004c, code lost:
    
        if (r1.m580dispatchPostFlingRZ2iAVY(r2, 0, r10) == r0) goto L17;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r11) {
        /*
            r10 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r10.label
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L19
            if (r1 == r3) goto L15
            if (r1 != r2) goto Ld
            goto L15
        Ld:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r11)
            throw r10
        L15:
            kotlin.ResultKt.throwOnFailure(r11)
            goto L4f
        L19:
            kotlin.ResultKt.throwOnFailure(r11)
            boolean r11 = r10.$consumed
            if (r11 != 0) goto L37
            androidx.compose.ui.viewinterop.AndroidViewHolder r11 = r10.this$0
            androidx.compose.ui.input.nestedscroll.NestedScrollDispatcher r4 = r11.dispatcher
            androidx.compose.ui.unit.Velocity$Companion r11 = androidx.compose.ui.unit.Velocity.Companion
            r11.getClass()
            long r7 = r10.$viewVelocity
            r10.label = r3
            r5 = 0
            r9 = r10
            java.lang.Object r10 = r4.m580dispatchPostFlingRZ2iAVY(r5, r7, r9)
            if (r10 != r0) goto L4f
            goto L4e
        L37:
            r6 = r10
            androidx.compose.ui.viewinterop.AndroidViewHolder r10 = r6.this$0
            androidx.compose.ui.input.nestedscroll.NestedScrollDispatcher r1 = r10.dispatcher
            r10 = r2
            long r2 = r6.$viewVelocity
            androidx.compose.ui.unit.Velocity$Companion r11 = androidx.compose.ui.unit.Velocity.Companion
            r11.getClass()
            r6.label = r10
            r4 = 0
            java.lang.Object r10 = r1.m580dispatchPostFlingRZ2iAVY(r2, r4, r6)
            if (r10 != r0) goto L4f
        L4e:
            return r0
        L4f:
            kotlin.Unit r10 = kotlin.Unit.INSTANCE
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.viewinterop.AndroidViewHolder$onNestedFling$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
