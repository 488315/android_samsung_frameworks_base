package com.android.systemui.flags;

import java.util.concurrent.TimeUnit;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.flags.ConditionalRestarter$scheduleRestart$2", m277f = "ConditionalRestarter.kt", m278l = {64}, m279m = "invokeSuspend")
/* loaded from: classes.dex */
final class ConditionalRestarter$scheduleRestart$2 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ ConditionalRestarter this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ConditionalRestarter$scheduleRestart$2(ConditionalRestarter conditionalRestarter, Continuation<? super ConditionalRestarter$scheduleRestart$2> continuation) {
        super(2, continuation);
        this.this$0 = conditionalRestarter;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ConditionalRestarter$scheduleRestart$2(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ConditionalRestarter$scheduleRestart$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            long millis = TimeUnit.SECONDS.toMillis(this.this$0.restartDelaySec);
            this.label = 1;
            if (DelayKt.delay(millis, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        ConditionalRestarter conditionalRestarter = this.this$0;
        conditionalRestarter.systemExitRestarter.restartSystemUI(conditionalRestarter.pendingReason);
        return Unit.INSTANCE;
    }
}
