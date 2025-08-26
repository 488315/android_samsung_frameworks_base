package com.android.systemui.bouncer.ui.composable;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.time.Duration;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;

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

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0045, code lost:
    
        if (kotlinx.coroutines.CoroutineScopeKt.coroutineScope(new com.android.systemui.bouncer.ui.composable.PinInputEntry$animateClearAllCollapse$2(r7, null), r6) == r0) goto L15;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            long jM3462timesUwyO8pc = Duration.m3462timesUwyO8pc(this.$index, this.this$0.shapeAnimations.dismissStaggerDelay);
            this.label = 1;
            if (DelayKt.m3469delayVtjQ1oo(jM3462timesUwyO8pc, this) != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i != 1) {
            if (i != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return Unit.INSTANCE;
        }
        ResultKt.throwOnFailure(obj);
        PinInputEntry pinInputEntry = this.$entry;
        this.label = 2;
        pinInputEntry.getClass();
    }
}
