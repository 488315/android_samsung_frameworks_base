package androidx.compose.foundation;

import androidx.compose.foundation.gestures.PressGestureScope;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class AbstractClickableNode$handlePressInteraction$2$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ MutableInteractionSource $interactionSource;
    final /* synthetic */ long $offset;
    final /* synthetic */ PressGestureScope $this_handlePressInteraction;
    private /* synthetic */ Object L$0;
    boolean Z$0;
    int label;
    final /* synthetic */ AbstractClickableNode this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AbstractClickableNode$handlePressInteraction$2$1(PressGestureScope pressGestureScope, long j, MutableInteractionSource mutableInteractionSource, AbstractClickableNode abstractClickableNode, Continuation continuation) {
        super(2, continuation);
        this.$this_handlePressInteraction = pressGestureScope;
        this.$offset = j;
        this.$interactionSource = mutableInteractionSource;
        this.this$0 = abstractClickableNode;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        AbstractClickableNode$handlePressInteraction$2$1 abstractClickableNode$handlePressInteraction$2$1 = new AbstractClickableNode$handlePressInteraction$2$1(this.$this_handlePressInteraction, this.$offset, this.$interactionSource, this.this$0, continuation);
        abstractClickableNode$handlePressInteraction$2$1.L$0 = obj;
        return abstractClickableNode$handlePressInteraction$2$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AbstractClickableNode$handlePressInteraction$2$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x009f, code lost:
    
        if (r15.emit(r1, r14) == r0) goto L40;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0092, code lost:
    
        if (r4.emit(r15, r14) == r0) goto L40;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x00bf, code lost:
    
        if (r2.emit(r15, r14) == r0) goto L40;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x005d, code lost:
    
        if (r15 == r0) goto L40;
     */
    /* JADX WARN: Removed duplicated region for block: B:20:0x007c  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r15) {
        /*
            r14 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r14.label
            r2 = 3
            r3 = 0
            r4 = 5
            r5 = 4
            r6 = 2
            r7 = 1
            if (r1 == 0) goto L3a
            if (r1 == r7) goto L32
            if (r1 == r6) goto L2c
            if (r1 == r2) goto L24
            if (r1 == r5) goto L1f
            if (r1 != r4) goto L17
            goto L1f
        L17:
            java.lang.IllegalStateException r14 = new java.lang.IllegalStateException
            java.lang.String r15 = "call to 'resume' before 'invoke' with coroutine"
            r14.<init>(r15)
            throw r14
        L1f:
            kotlin.ResultKt.throwOnFailure(r15)
            goto Lc2
        L24:
            java.lang.Object r1 = r14.L$0
            androidx.compose.foundation.interaction.PressInteraction$Release r1 = (androidx.compose.foundation.interaction.PressInteraction$Release) r1
            kotlin.ResultKt.throwOnFailure(r15)
            goto L95
        L2c:
            boolean r1 = r14.Z$0
            kotlin.ResultKt.throwOnFailure(r15)
            goto L7a
        L32:
            java.lang.Object r1 = r14.L$0
            kotlinx.coroutines.Job r1 = (kotlinx.coroutines.Job) r1
            kotlin.ResultKt.throwOnFailure(r15)
            goto L60
        L3a:
            kotlin.ResultKt.throwOnFailure(r15)
            java.lang.Object r15 = r14.L$0
            kotlinx.coroutines.CoroutineScope r15 = (kotlinx.coroutines.CoroutineScope) r15
            androidx.compose.foundation.AbstractClickableNode$handlePressInteraction$2$1$delayJob$1 r8 = new androidx.compose.foundation.AbstractClickableNode$handlePressInteraction$2$1$delayJob$1
            androidx.compose.foundation.AbstractClickableNode r9 = r14.this$0
            long r10 = r14.$offset
            androidx.compose.foundation.interaction.MutableInteractionSource r12 = r14.$interactionSource
            r13 = 0
            r8.<init>(r9, r10, r12, r13)
            kotlinx.coroutines.StandaloneCoroutine r1 = kotlinx.coroutines.BuildersKt.launch$default(r15, r3, r3, r8, r2)
            androidx.compose.foundation.gestures.PressGestureScope r15 = r14.$this_handlePressInteraction
            r14.L$0 = r1
            r14.label = r7
            androidx.compose.foundation.gestures.PressGestureScopeImpl r15 = (androidx.compose.foundation.gestures.PressGestureScopeImpl) r15
            java.lang.Object r15 = r15.tryAwaitRelease(r14)
            if (r15 != r0) goto L60
            goto Lc1
        L60:
            java.lang.Boolean r15 = (java.lang.Boolean) r15
            boolean r15 = r15.booleanValue()
            boolean r7 = r1.isActive()
            if (r7 == 0) goto La2
            r14.L$0 = r3
            r14.Z$0 = r15
            r14.label = r6
            java.lang.Object r1 = kotlinx.coroutines.JobKt.cancelAndJoin(r1, r14)
            if (r1 != r0) goto L79
            goto Lc1
        L79:
            r1 = r15
        L7a:
            if (r1 == 0) goto Lc2
            androidx.compose.foundation.interaction.PressInteraction$Press r15 = new androidx.compose.foundation.interaction.PressInteraction$Press
            long r6 = r14.$offset
            r15.<init>(r6, r3)
            androidx.compose.foundation.interaction.PressInteraction$Release r1 = new androidx.compose.foundation.interaction.PressInteraction$Release
            r1.<init>(r15)
            androidx.compose.foundation.interaction.MutableInteractionSource r4 = r14.$interactionSource
            r14.L$0 = r1
            r14.label = r2
            java.lang.Object r15 = r4.emit(r15, r14)
            if (r15 != r0) goto L95
            goto Lc1
        L95:
            androidx.compose.foundation.interaction.MutableInteractionSource r15 = r14.$interactionSource
            r14.L$0 = r3
            r14.label = r5
            java.lang.Object r15 = r15.emit(r1, r14)
            if (r15 != r0) goto Lc2
            goto Lc1
        La2:
            androidx.compose.foundation.AbstractClickableNode r1 = r14.this$0
            androidx.compose.foundation.interaction.PressInteraction$Press r1 = r1.pressInteraction
            if (r1 == 0) goto Lc2
            androidx.compose.foundation.interaction.MutableInteractionSource r2 = r14.$interactionSource
            if (r15 == 0) goto Lb2
            androidx.compose.foundation.interaction.PressInteraction$Release r15 = new androidx.compose.foundation.interaction.PressInteraction$Release
            r15.<init>(r1)
            goto Lb7
        Lb2:
            androidx.compose.foundation.interaction.PressInteraction$Cancel r15 = new androidx.compose.foundation.interaction.PressInteraction$Cancel
            r15.<init>(r1)
        Lb7:
            r14.L$0 = r3
            r14.label = r4
            java.lang.Object r15 = r2.emit(r15, r14)
            if (r15 != r0) goto Lc2
        Lc1:
            return r0
        Lc2:
            androidx.compose.foundation.AbstractClickableNode r14 = r14.this$0
            r14.pressInteraction = r3
            kotlin.Unit r14 = kotlin.Unit.INSTANCE
            return r14
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.AbstractClickableNode$handlePressInteraction$2$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
