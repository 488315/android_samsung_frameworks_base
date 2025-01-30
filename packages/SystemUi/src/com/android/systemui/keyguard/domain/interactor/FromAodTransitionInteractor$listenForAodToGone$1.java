package com.android.systemui.keyguard.domain.interactor;

import android.animation.ValueAnimator;
import com.android.app.animation.Interpolators;
import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository;
import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepositoryImpl;
import com.android.systemui.keyguard.shared.model.BiometricUnlockModel;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.shared.model.TransitionInfo;
import com.android.systemui.util.kotlin.FlowKt;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.SafeFlow;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.keyguard.domain.interactor.FromAodTransitionInteractor$listenForAodToGone$1", m277f = "FromAodTransitionInteractor.kt", m278l = {73}, m279m = "invokeSuspend")
/* loaded from: classes.dex */
final class FromAodTransitionInteractor$listenForAodToGone$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ FromAodTransitionInteractor this$0;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.keyguard.domain.interactor.FromAodTransitionInteractor$listenForAodToGone$1$2 */
    final /* synthetic */ class C16512 extends AdaptedFunctionReference implements Function3 {
        public static final C16512 INSTANCE = new C16512();

        public C16512() {
            super(3, Pair.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;)V", 4);
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            return new Pair((BiometricUnlockModel) obj, (KeyguardState) obj2);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FromAodTransitionInteractor$listenForAodToGone$1(FromAodTransitionInteractor fromAodTransitionInteractor, Continuation<? super FromAodTransitionInteractor$listenForAodToGone$1> continuation) {
        super(2, continuation);
        this.this$0 = fromAodTransitionInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new FromAodTransitionInteractor$listenForAodToGone$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((FromAodTransitionInteractor$listenForAodToGone$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FromAodTransitionInteractor fromAodTransitionInteractor = this.this$0;
            SafeFlow sample = FlowKt.sample(fromAodTransitionInteractor.keyguardInteractor.biometricUnlockState, fromAodTransitionInteractor.keyguardTransitionInteractor.finishedKeyguardState, C16512.INSTANCE);
            final FromAodTransitionInteractor fromAodTransitionInteractor2 = this.this$0;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.domain.interactor.FromAodTransitionInteractor$listenForAodToGone$1.3
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    Pair pair = (Pair) obj2;
                    BiometricUnlockModel biometricUnlockModel = (BiometricUnlockModel) pair.component1();
                    KeyguardState keyguardState = (KeyguardState) pair.component2();
                    KeyguardState keyguardState2 = KeyguardState.AOD;
                    if (keyguardState == keyguardState2) {
                        BiometricUnlockModel.Companion.getClass();
                        if (BiometricUnlockModel.wakeAndUnlockModes.contains(biometricUnlockModel)) {
                            FromAodTransitionInteractor fromAodTransitionInteractor3 = FromAodTransitionInteractor.this;
                            KeyguardTransitionRepository keyguardTransitionRepository = fromAodTransitionInteractor3.keyguardTransitionRepository;
                            KeyguardState keyguardState3 = KeyguardState.GONE;
                            ValueAnimator valueAnimator = new ValueAnimator();
                            valueAnimator.setInterpolator(Interpolators.LINEAR);
                            valueAnimator.setDuration(500L);
                            ((KeyguardTransitionRepositoryImpl) keyguardTransitionRepository).startTransition(new TransitionInfo(fromAodTransitionInteractor3.name, keyguardState2, keyguardState3, valueAnimator), false);
                        }
                    }
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (sample.collect(flowCollector, this) == coroutineSingletons) {
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
