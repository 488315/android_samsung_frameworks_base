package com.android.systemui.keyguard.domain.interactor;

import android.util.Log;
import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository;
import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepositoryImpl;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.shared.model.TransitionInfo;
import com.android.systemui.keyguard.shared.model.TransitionState;
import com.android.systemui.keyguard.shared.model.TransitionStep;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final class KeyguardTransitionBootInteractor$start$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ KeyguardTransitionBootInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardTransitionBootInteractor$start$1(KeyguardTransitionBootInteractor keyguardTransitionBootInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = keyguardTransitionBootInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new KeyguardTransitionBootInteractor$start$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((KeyguardTransitionBootInteractor$start$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            KeyguardTransitionBootInteractor$special$$inlined$map$1 keyguardTransitionBootInteractor$special$$inlined$map$1 = this.this$0.showLockscreenOnBoot;
            this.label = 1;
            obj = FlowKt.first(keyguardTransitionBootInteractor$special$$inlined$map$1, this);
            if (obj == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                if (i != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            }
            ResultKt.throwOnFailure(obj);
        }
        KeyguardState keyguardState = ((Boolean) obj).booleanValue() ? KeyguardState.LOCKSCREEN : KeyguardState.GONE;
        KeyguardState keyguardState2 = ((TransitionInfo) this.this$0.keyguardTransitionInteractor.currentTransitionInfoInternal.$$delegate_0.getValue()).from;
        KeyguardState keyguardState3 = KeyguardState.OFF;
        if (keyguardState2 != keyguardState3) {
            Log.e("KeyguardTransitionInteractor", "showLockscreenOnBoot emitted, but we've already transitioned to a state other than OFF. We'll respect that transition, but this should not happen.");
        } else {
            KeyguardTransitionRepository keyguardTransitionRepository = this.this$0.repository;
            this.label = 2;
            KeyguardTransitionRepositoryImpl keyguardTransitionRepositoryImpl = (KeyguardTransitionRepositoryImpl) keyguardTransitionRepository;
            keyguardTransitionRepositoryImpl._currentTransitionInfo.updateState(null, new TransitionInfo("KeyguardTransitionRepository(boot)", keyguardState3, keyguardState, null, null, 16, null));
            keyguardTransitionRepositoryImpl.emitTransition(new TransitionStep(keyguardState3, keyguardState, 0.0f, TransitionState.STARTED, "KeyguardTransitionRepository(boot)"), false);
            keyguardTransitionRepositoryImpl.emitTransition(new TransitionStep(keyguardState3, keyguardState, 1.0f, TransitionState.FINISHED, "KeyguardTransitionRepository(boot)"), false);
            if (Unit.INSTANCE == coroutineSingletons) {
                return coroutineSingletons;
            }
        }
        return Unit.INSTANCE;
    }
}
