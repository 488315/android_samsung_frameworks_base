package com.android.systemui.bouncer.domain.interactor;

import com.android.systemui.R;
import com.android.systemui.authentication.shared.model.BouncerInputSide;
import com.android.systemui.bouncer.data.repository.BouncerRepository;
import com.android.systemui.flags.FeatureFlagsClassicRelease;
import com.android.systemui.flags.Flags;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class BouncerInteractor$preferredBouncerInputSide$1 extends SuspendLambda implements Function3 {
    int label;
    final /* synthetic */ BouncerInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BouncerInteractor$preferredBouncerInputSide$1(BouncerInteractor bouncerInteractor, Continuation continuation) {
        super(3, continuation);
        this.this$0 = bouncerInteractor;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        return new BouncerInteractor$preferredBouncerInputSide$1(this.this$0, (Continuation) obj3).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        int i = this.this$0.repository.globalSettings.getInt("one_handed_keyguard_side", -1);
        BouncerInputSide bouncerInputSide = i != 0 ? i != 1 ? null : BouncerInputSide.RIGHT : BouncerInputSide.LEFT;
        if (bouncerInputSide != null) {
            return bouncerInputSide;
        }
        BouncerRepository bouncerRepository = this.this$0.repository;
        if (bouncerRepository.applicationContext.getResources().getBoolean(R.bool.config_enableBouncerUserSwitcher)) {
            if (((FeatureFlagsClassicRelease) bouncerRepository.flags).isEnabled(Flags.FULL_SCREEN_USER_SWITCHER)) {
                return BouncerInputSide.RIGHT;
            }
        }
        if (this.this$0.repository.applicationContext.getResources().getBoolean(R.bool.can_use_one_handed_bouncer)) {
            return BouncerInputSide.LEFT;
        }
        return null;
    }
}
