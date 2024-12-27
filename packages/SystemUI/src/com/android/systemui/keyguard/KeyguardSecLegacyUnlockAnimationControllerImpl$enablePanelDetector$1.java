package com.android.systemui.keyguard;

import android.animation.AnimatorSet;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class KeyguardSecLegacyUnlockAnimationControllerImpl$enablePanelDetector$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ KeyguardSecLegacyUnlockAnimationControllerImpl this$0;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.keyguard.KeyguardSecLegacyUnlockAnimationControllerImpl$enablePanelDetector$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        /* synthetic */ boolean Z$0;
        int label;
        final /* synthetic */ KeyguardSecLegacyUnlockAnimationControllerImpl this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(KeyguardSecLegacyUnlockAnimationControllerImpl keyguardSecLegacyUnlockAnimationControllerImpl, Continuation continuation) {
            super(2, continuation);
            this.this$0 = keyguardSecLegacyUnlockAnimationControllerImpl;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, continuation);
            anonymousClass1.Z$0 = ((Boolean) obj).booleanValue();
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            Boolean bool = (Boolean) obj;
            bool.booleanValue();
            return ((AnonymousClass1) create(bool, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            boolean z = this.Z$0;
            if (z) {
                Log.i("KeyguardUnlock", "Detector detected shade interaction");
                final KeyguardSecLegacyUnlockAnimationControllerImpl keyguardSecLegacyUnlockAnimationControllerImpl = this.this$0;
                keyguardSecLegacyUnlockAnimationControllerImpl.unlockAnimationExecutor.execute(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardSecLegacyUnlockAnimationControllerImpl.enablePanelDetector.1.1.1
                    @Override // java.lang.Runnable
                    public final void run() {
                        KeyguardSecLegacyUnlockAnimationControllerImpl.this.forceEnded = true;
                        Log.d("KeyguardUnlock", "playCannedUnlockAnimation: forceEnded=true");
                        AnimatorSet animatorSet = KeyguardSecLegacyUnlockAnimationControllerImpl.this.cannedAnimatorSet;
                        if (animatorSet != null) {
                            animatorSet.end();
                        }
                    }
                });
                ((KeyguardSurfaceControllerImpl) this.this$0.keyguardSurfaceControllerLazy.get()).restoreKeyguardSurfaceIfVisible();
            }
            return Boolean.valueOf(z);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardSecLegacyUnlockAnimationControllerImpl$enablePanelDetector$1(KeyguardSecLegacyUnlockAnimationControllerImpl keyguardSecLegacyUnlockAnimationControllerImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = keyguardSecLegacyUnlockAnimationControllerImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new KeyguardSecLegacyUnlockAnimationControllerImpl$enablePanelDetector$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((KeyguardSecLegacyUnlockAnimationControllerImpl$enablePanelDetector$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Log.i("KeyguardUnlock", "Enable panel detector");
            ReadonlyStateFlow readonlyStateFlow = ((ShadeInteractorImpl) ((ShadeInteractor) this.this$0.shadeInteractorLazy.get())).isUserInteracting;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, null);
            this.label = 1;
            if (FlowKt.first(readonlyStateFlow, anonymousClass1, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
