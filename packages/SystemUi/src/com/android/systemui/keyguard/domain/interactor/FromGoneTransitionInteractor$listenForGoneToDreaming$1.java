package com.android.systemui.keyguard.domain.interactor;

import android.animation.ValueAnimator;
import com.android.app.animation.Interpolators;
import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository;
import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepositoryImpl;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.shared.model.TransitionInfo;
import com.android.systemui.keyguard.shared.model.TransitionStep;
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
import kotlin.time.Duration;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.SafeFlow;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.keyguard.domain.interactor.FromGoneTransitionInteractor$listenForGoneToDreaming$1", m277f = "FromGoneTransitionInteractor.kt", m278l = {76}, m279m = "invokeSuspend")
/* loaded from: classes.dex */
final class FromGoneTransitionInteractor$listenForGoneToDreaming$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ FromGoneTransitionInteractor this$0;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.keyguard.domain.interactor.FromGoneTransitionInteractor$listenForGoneToDreaming$1$2 */
    final /* synthetic */ class C16692 extends AdaptedFunctionReference implements Function3 {
        public static final C16692 INSTANCE = new C16692();

        public C16692() {
            super(3, Pair.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;)V", 4);
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            return new Pair(Boolean.valueOf(((Boolean) obj).booleanValue()), (TransitionStep) obj2);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FromGoneTransitionInteractor$listenForGoneToDreaming$1(FromGoneTransitionInteractor fromGoneTransitionInteractor, Continuation<? super FromGoneTransitionInteractor$listenForGoneToDreaming$1> continuation) {
        super(2, continuation);
        this.this$0 = fromGoneTransitionInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new FromGoneTransitionInteractor$listenForGoneToDreaming$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((FromGoneTransitionInteractor$listenForGoneToDreaming$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FromGoneTransitionInteractor fromGoneTransitionInteractor = this.this$0;
            SafeFlow sample = FlowKt.sample(fromGoneTransitionInteractor.keyguardInteractor.isAbleToDream, fromGoneTransitionInteractor.keyguardTransitionInteractor.startedKeyguardTransitionStep, C16692.INSTANCE);
            final FromGoneTransitionInteractor fromGoneTransitionInteractor2 = this.this$0;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.domain.interactor.FromGoneTransitionInteractor$listenForGoneToDreaming$1.3
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    Pair pair = (Pair) obj2;
                    boolean booleanValue = ((Boolean) pair.component1()).booleanValue();
                    TransitionStep transitionStep = (TransitionStep) pair.component2();
                    if (booleanValue) {
                        KeyguardState keyguardState = transitionStep.f304to;
                        KeyguardState keyguardState2 = KeyguardState.GONE;
                        if (keyguardState == keyguardState2) {
                            FromGoneTransitionInteractor fromGoneTransitionInteractor3 = FromGoneTransitionInteractor.this;
                            KeyguardTransitionRepository keyguardTransitionRepository = fromGoneTransitionInteractor3.keyguardTransitionRepository;
                            KeyguardState keyguardState3 = KeyguardState.DREAMING;
                            FromGoneTransitionInteractor.Companion.getClass();
                            long j = FromGoneTransitionInteractor.TO_DREAMING_DURATION;
                            ValueAnimator valueAnimator = new ValueAnimator();
                            valueAnimator.setInterpolator(Interpolators.LINEAR);
                            valueAnimator.setDuration(Duration.m2863getInWholeMillisecondsimpl(j));
                            ((KeyguardTransitionRepositoryImpl) keyguardTransitionRepository).startTransition(new TransitionInfo(fromGoneTransitionInteractor3.name, keyguardState2, keyguardState3, valueAnimator), false);
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
