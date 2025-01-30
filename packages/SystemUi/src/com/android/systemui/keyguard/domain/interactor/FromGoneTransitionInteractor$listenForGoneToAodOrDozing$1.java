package com.android.systemui.keyguard.domain.interactor;

import android.animation.ValueAnimator;
import com.android.app.animation.Interpolators;
import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository;
import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepositoryImpl;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.shared.model.TransitionInfo;
import com.android.systemui.keyguard.shared.model.TransitionStep;
import com.android.systemui.keyguard.shared.model.WakefulnessModel;
import com.android.systemui.keyguard.shared.model.WakefulnessState;
import com.android.systemui.util.kotlin.FlowKt;
import com.android.systemui.util.kotlin.Utils;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Triple;
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
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.SafeFlow;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.keyguard.domain.interactor.FromGoneTransitionInteractor$listenForGoneToAodOrDozing$1", m277f = "FromGoneTransitionInteractor.kt", m278l = {102}, m279m = "invokeSuspend")
/* loaded from: classes.dex */
final class FromGoneTransitionInteractor$listenForGoneToAodOrDozing$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ FromGoneTransitionInteractor this$0;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.keyguard.domain.interactor.FromGoneTransitionInteractor$listenForGoneToAodOrDozing$1$2 */
    final /* synthetic */ class C16662 extends AdaptedFunctionReference implements Function3 {
        public static final C16662 INSTANCE = new C16662();

        public C16662() {
            super(3, Pair.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;)V", 4);
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            return new Pair((TransitionStep) obj, Boolean.valueOf(((Boolean) obj2).booleanValue()));
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.keyguard.domain.interactor.FromGoneTransitionInteractor$listenForGoneToAodOrDozing$1$3 */
    final /* synthetic */ class C16673 extends AdaptedFunctionReference implements Function3 {
        public C16673(Object obj) {
            super(3, obj, Utils.Companion.class, "toTriple", "toTriple(Ljava/lang/Object;Lkotlin/Pair;)Lkotlin/Triple;", 4);
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            ((Utils.Companion) this.receiver).getClass();
            return Utils.Companion.toTriple((WakefulnessModel) obj, (Pair) obj2);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FromGoneTransitionInteractor$listenForGoneToAodOrDozing$1(FromGoneTransitionInteractor fromGoneTransitionInteractor, Continuation<? super FromGoneTransitionInteractor$listenForGoneToAodOrDozing$1> continuation) {
        super(2, continuation);
        this.this$0 = fromGoneTransitionInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new FromGoneTransitionInteractor$listenForGoneToAodOrDozing$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((FromGoneTransitionInteractor$listenForGoneToAodOrDozing$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FromGoneTransitionInteractor fromGoneTransitionInteractor = this.this$0;
            KeyguardInteractor keyguardInteractor = fromGoneTransitionInteractor.keyguardInteractor;
            SafeFlow sample = FlowKt.sample(keyguardInteractor.wakefulnessModel, new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(fromGoneTransitionInteractor.keyguardTransitionInteractor.startedKeyguardTransitionStep, keyguardInteractor.isAodAvailable, C16662.INSTANCE), new C16673(Utils.Companion));
            final FromGoneTransitionInteractor fromGoneTransitionInteractor2 = this.this$0;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.domain.interactor.FromGoneTransitionInteractor$listenForGoneToAodOrDozing$1.4
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    Triple triple = (Triple) obj2;
                    WakefulnessModel wakefulnessModel = (WakefulnessModel) triple.component1();
                    TransitionStep transitionStep = (TransitionStep) triple.component2();
                    boolean booleanValue = ((Boolean) triple.component3()).booleanValue();
                    KeyguardState keyguardState = transitionStep.f304to;
                    KeyguardState keyguardState2 = KeyguardState.GONE;
                    if (keyguardState == keyguardState2 && wakefulnessModel.state == WakefulnessState.STARTING_TO_SLEEP) {
                        FromGoneTransitionInteractor fromGoneTransitionInteractor3 = FromGoneTransitionInteractor.this;
                        KeyguardTransitionRepository keyguardTransitionRepository = fromGoneTransitionInteractor3.keyguardTransitionRepository;
                        KeyguardState keyguardState3 = booleanValue ? KeyguardState.AOD : KeyguardState.DOZING;
                        ValueAnimator valueAnimator = new ValueAnimator();
                        valueAnimator.setInterpolator(Interpolators.LINEAR);
                        valueAnimator.setDuration(Duration.m2863getInWholeMillisecondsimpl(FromGoneTransitionInteractor.DEFAULT_DURATION));
                        ((KeyguardTransitionRepositoryImpl) keyguardTransitionRepository).startTransition(new TransitionInfo(fromGoneTransitionInteractor3.name, keyguardState2, keyguardState3, valueAnimator), false);
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
