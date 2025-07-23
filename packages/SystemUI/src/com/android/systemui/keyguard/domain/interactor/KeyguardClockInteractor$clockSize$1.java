package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.keyguard.shared.model.ClockSize;
import com.android.systemui.keyguard.shared.model.ClockSizeSetting;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class KeyguardClockInteractor$clockSize$1 extends SuspendLambda implements Function2 {
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ KeyguardClockInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardClockInteractor$clockSize$1(KeyguardClockInteractor keyguardClockInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = keyguardClockInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        KeyguardClockInteractor$clockSize$1 keyguardClockInteractor$clockSize$1 = new KeyguardClockInteractor$clockSize$1(this.this$0, continuation);
        keyguardClockInteractor$clockSize$1.L$0 = obj;
        return keyguardClockInteractor$clockSize$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((KeyguardClockInteractor$clockSize$1) create((ClockSizeSetting) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return ((ClockSizeSetting) this.L$0) == ClockSizeSetting.SMALL ? new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(ClockSize.SMALL) : this.this$0.dynamicClockSize;
    }
}
