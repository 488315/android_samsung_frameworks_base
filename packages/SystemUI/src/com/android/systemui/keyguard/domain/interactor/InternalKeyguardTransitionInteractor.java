package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository;
import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepositoryImpl;
import com.android.systemui.keyguard.shared.model.TransitionInfo;
import com.android.systemui.keyguard.shared.model.TransitionState;
import java.util.UUID;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;

/* loaded from: classes2.dex */
public final class InternalKeyguardTransitionInteractor {
    public final KeyguardTransitionRepository repository;

    public InternalKeyguardTransitionInteractor(KeyguardTransitionRepository keyguardTransitionRepository) {
        this.repository = keyguardTransitionRepository;
    }

    public final TransitionInfo currentTransitionInfoInternal$frameworks__base__packages__SystemUI__android_common__SystemUI_core() {
        return ((KeyguardTransitionRepositoryImpl) this.repository).currentTransitionInfo;
    }

    public final Object updateTransition(UUID uuid, float f, TransitionState transitionState, Continuation continuation) {
        Object objUpdateTransition = ((KeyguardTransitionRepositoryImpl) this.repository).updateTransition(uuid, f, transitionState, continuation);
        return objUpdateTransition == CoroutineSingletons.COROUTINE_SUSPENDED ? objUpdateTransition : Unit.INSTANCE;
    }
}
