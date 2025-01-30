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
import com.android.systemui.util.kotlin.Quad;
import com.android.systemui.util.kotlin.Utils;
import kotlin.ResultKt;
import kotlin.Triple;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.AdaptedFunctionReference;
import kotlin.time.Duration;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.SafeFlow;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.keyguard.domain.interactor.FromPrimaryBouncerTransitionInteractor$listenForPrimaryBouncerToLockscreenOrOccluded$1", m277f = "FromPrimaryBouncerTransitionInteractor.kt", m278l = {68}, m279m = "invokeSuspend")
/* renamed from: com.android.systemui.keyguard.domain.interactor.FromPrimaryBouncerTransitionInteractor$listenForPrimaryBouncerToLockscreenOrOccluded$1 */
/* loaded from: classes.dex */
final class C1695x3fc45d93 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ FromPrimaryBouncerTransitionInteractor this$0;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.keyguard.domain.interactor.FromPrimaryBouncerTransitionInteractor$listenForPrimaryBouncerToLockscreenOrOccluded$1$2, reason: invalid class name */
    final /* synthetic */ class AnonymousClass2 extends AdaptedFunctionReference implements Function4 {
        public static final AnonymousClass2 INSTANCE = new AnonymousClass2();

        public AnonymousClass2() {
            super(4, Triple.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V", 4);
        }

        @Override // kotlin.jvm.functions.Function4
        public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
            return new Triple((WakefulnessModel) obj, (TransitionStep) obj2, Boolean.valueOf(((Boolean) obj3).booleanValue()));
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.keyguard.domain.interactor.FromPrimaryBouncerTransitionInteractor$listenForPrimaryBouncerToLockscreenOrOccluded$1$3, reason: invalid class name */
    final /* synthetic */ class AnonymousClass3 extends AdaptedFunctionReference implements Function3 {
        public AnonymousClass3(Object obj) {
            super(3, obj, Utils.Companion.class, "toQuad", "toQuad(Ljava/lang/Object;Lkotlin/Triple;)Lcom/android/systemui/util/kotlin/Quad;", 4);
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            boolean booleanValue = ((Boolean) obj).booleanValue();
            Utils.Companion companion = (Utils.Companion) this.receiver;
            Boolean valueOf = Boolean.valueOf(booleanValue);
            companion.getClass();
            return Utils.Companion.toQuad(valueOf, (Triple) obj2);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C1695x3fc45d93(FromPrimaryBouncerTransitionInteractor fromPrimaryBouncerTransitionInteractor, Continuation<? super C1695x3fc45d93> continuation) {
        super(2, continuation);
        this.this$0 = fromPrimaryBouncerTransitionInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new C1695x3fc45d93(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((C1695x3fc45d93) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FromPrimaryBouncerTransitionInteractor fromPrimaryBouncerTransitionInteractor = this.this$0;
            KeyguardInteractor keyguardInteractor = fromPrimaryBouncerTransitionInteractor.keyguardInteractor;
            SafeFlow sample = FlowKt.sample(keyguardInteractor.primaryBouncerShowing, kotlinx.coroutines.flow.FlowKt.combine(keyguardInteractor.wakefulnessModel, fromPrimaryBouncerTransitionInteractor.keyguardTransitionInteractor.startedKeyguardTransitionStep, keyguardInteractor.isKeyguardOccluded, AnonymousClass2.INSTANCE), new AnonymousClass3(Utils.Companion));
            final FromPrimaryBouncerTransitionInteractor fromPrimaryBouncerTransitionInteractor2 = this.this$0;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.domain.interactor.FromPrimaryBouncerTransitionInteractor$listenForPrimaryBouncerToLockscreenOrOccluded$1.4
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    WakefulnessState wakefulnessState;
                    Quad quad = (Quad) obj2;
                    boolean booleanValue = ((Boolean) quad.first).booleanValue();
                    WakefulnessModel wakefulnessModel = (WakefulnessModel) quad.second;
                    TransitionStep transitionStep = (TransitionStep) quad.third;
                    boolean booleanValue2 = ((Boolean) quad.fourth).booleanValue();
                    if (!booleanValue) {
                        KeyguardState keyguardState = transitionStep.f304to;
                        KeyguardState keyguardState2 = KeyguardState.PRIMARY_BOUNCER;
                        if (keyguardState == keyguardState2 && ((wakefulnessState = wakefulnessModel.state) == WakefulnessState.AWAKE || wakefulnessState == WakefulnessState.STARTING_TO_WAKE)) {
                            KeyguardState keyguardState3 = booleanValue2 ? KeyguardState.OCCLUDED : KeyguardState.LOCKSCREEN;
                            FromPrimaryBouncerTransitionInteractor fromPrimaryBouncerTransitionInteractor3 = FromPrimaryBouncerTransitionInteractor.this;
                            KeyguardTransitionRepository keyguardTransitionRepository = fromPrimaryBouncerTransitionInteractor3.keyguardTransitionRepository;
                            ValueAnimator valueAnimator = new ValueAnimator();
                            valueAnimator.setInterpolator(Interpolators.LINEAR);
                            valueAnimator.setDuration(Duration.m2863getInWholeMillisecondsimpl(FromPrimaryBouncerTransitionInteractor.DEFAULT_DURATION));
                            ((KeyguardTransitionRepositoryImpl) keyguardTransitionRepository).startTransition(new TransitionInfo(fromPrimaryBouncerTransitionInteractor3.name, keyguardState2, keyguardState3, valueAnimator), false);
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
