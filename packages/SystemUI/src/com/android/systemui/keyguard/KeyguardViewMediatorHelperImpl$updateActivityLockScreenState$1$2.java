package com.android.systemui.keyguard;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class KeyguardViewMediatorHelperImpl$updateActivityLockScreenState$1$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ KeyguardViewMediatorHelperImpl$setLockScreenShownRunnable$1 $it;
    final /* synthetic */ long $shownDelay;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardViewMediatorHelperImpl$updateActivityLockScreenState$1$2(long j, KeyguardViewMediatorHelperImpl$setLockScreenShownRunnable$1 keyguardViewMediatorHelperImpl$setLockScreenShownRunnable$1, Continuation continuation) {
        super(2, continuation);
        this.$shownDelay = j;
        this.$it = keyguardViewMediatorHelperImpl$setLockScreenShownRunnable$1;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new KeyguardViewMediatorHelperImpl$updateActivityLockScreenState$1$2(this.$shownDelay, this.$it, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((KeyguardViewMediatorHelperImpl$updateActivityLockScreenState$1$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            long j = this.$shownDelay;
            this.label = 1;
            if (DelayKt.delay(j, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        Log.d("KeyguardViewMediator", "updateActivityLockScreenState: run in lockShownJob");
        this.$it.run();
        return Unit.INSTANCE;
    }
}
