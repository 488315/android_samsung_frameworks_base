package com.android.wm.shell.bubbles;

import java.util.List;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class BubbleDataRepository$loadBubbles$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Function1 $cb;
    final /* synthetic */ List<Integer> $currentUsers;
    final /* synthetic */ int $userId;
    int label;
    final /* synthetic */ BubbleDataRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BubbleDataRepository$loadBubbles$1(BubbleDataRepository bubbleDataRepository, List<Integer> list, int i, Function1 function1, Continuation continuation) {
        super(2, continuation);
        this.this$0 = bubbleDataRepository;
        this.$currentUsers = list;
        this.$userId = i;
        this.$cb = function1;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new BubbleDataRepository$loadBubbles$1(this.this$0, this.$currentUsers, this.$userId, this.$cb, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((BubbleDataRepository$loadBubbles$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:58:0x018c  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x018f A[SYNTHETIC] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r23) {
        /*
            Method dump skipped, instructions count: 430
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.bubbles.BubbleDataRepository$loadBubbles$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
