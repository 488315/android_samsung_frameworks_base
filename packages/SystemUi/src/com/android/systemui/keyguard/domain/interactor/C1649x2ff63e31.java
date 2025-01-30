package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepositoryImpl;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.shared.model.TransitionInfo;
import com.android.systemui.keyguard.shared.model.TransitionStep;
import com.android.systemui.keyguard.shared.model.WakefulnessModel;
import com.android.systemui.keyguard.shared.model.WakefulnessState;
import com.android.systemui.util.kotlin.FlowKt;
import com.android.systemui.util.kotlin.Quad;
import com.android.systemui.util.kotlin.Quint;
import com.android.systemui.util.kotlin.Utils;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function5;
import kotlin.jvm.internal.AdaptedFunctionReference;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.SafeFlow;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.keyguard.domain.interactor.FromAlternateBouncerTransitionInteractor$listenForAlternateBouncerToLockscreenAodOrDozing$1", m277f = "FromAlternateBouncerTransitionInteractor.kt", m278l = {70}, m279m = "invokeSuspend")
/* renamed from: com.android.systemui.keyguard.domain.interactor.FromAlternateBouncerTransitionInteractor$listenForAlternateBouncerToLockscreenAodOrDozing$1 */
/* loaded from: classes.dex */
final class C1649x2ff63e31 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ FromAlternateBouncerTransitionInteractor this$0;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    @DebugMetadata(m276c = "com.android.systemui.keyguard.domain.interactor.FromAlternateBouncerTransitionInteractor$listenForAlternateBouncerToLockscreenAodOrDozing$1$1", m277f = "FromAlternateBouncerTransitionInteractor.kt", m278l = {59}, m279m = "invokeSuspend")
    /* renamed from: com.android.systemui.keyguard.domain.interactor.FromAlternateBouncerTransitionInteractor$listenForAlternateBouncerToLockscreenAodOrDozing$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            ((Boolean) obj).booleanValue();
            return new AnonymousClass1((Continuation) obj2).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.label = 1;
                if (DelayKt.delay(50L, this) == coroutineSingletons) {
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.keyguard.domain.interactor.FromAlternateBouncerTransitionInteractor$listenForAlternateBouncerToLockscreenAodOrDozing$1$2, reason: invalid class name */
    final /* synthetic */ class AnonymousClass2 extends AdaptedFunctionReference implements Function5 {
        public AnonymousClass2(Object obj) {
            super(5, obj, Utils.Companion.class, "toQuad", "toQuad(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Lcom/android/systemui/util/kotlin/Quad;", 4);
        }

        @Override // kotlin.jvm.functions.Function5
        public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
            boolean booleanValue = ((Boolean) obj).booleanValue();
            boolean booleanValue2 = ((Boolean) obj4).booleanValue();
            Utils.Companion companion = (Utils.Companion) this.receiver;
            Boolean valueOf = Boolean.valueOf(booleanValue);
            Boolean valueOf2 = Boolean.valueOf(booleanValue2);
            companion.getClass();
            return new Quad(valueOf, (TransitionStep) obj2, (WakefulnessModel) obj3, valueOf2);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.keyguard.domain.interactor.FromAlternateBouncerTransitionInteractor$listenForAlternateBouncerToLockscreenAodOrDozing$1$3, reason: invalid class name */
    final /* synthetic */ class AnonymousClass3 extends AdaptedFunctionReference implements Function3 {
        public AnonymousClass3(Object obj) {
            super(3, obj, Utils.Companion.class, "toQuint", "toQuint(Ljava/lang/Object;Lcom/android/systemui/util/kotlin/Quad;)Lcom/android/systemui/util/kotlin/Quint;", 4);
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            boolean booleanValue = ((Boolean) obj).booleanValue();
            Quad quad = (Quad) obj2;
            Utils.Companion companion = (Utils.Companion) this.receiver;
            Boolean valueOf = Boolean.valueOf(booleanValue);
            companion.getClass();
            return new Quint(valueOf, quad.first, quad.second, quad.third, quad.fourth);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C1649x2ff63e31(FromAlternateBouncerTransitionInteractor fromAlternateBouncerTransitionInteractor, Continuation<? super C1649x2ff63e31> continuation) {
        super(2, continuation);
        this.this$0 = fromAlternateBouncerTransitionInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new C1649x2ff63e31(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((C1649x2ff63e31) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 flowKt__TransformKt$onEach$$inlined$unsafeTransform$1 = new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(this.this$0.keyguardInteractor.alternateBouncerShowing, new AnonymousClass1(null));
            FromAlternateBouncerTransitionInteractor fromAlternateBouncerTransitionInteractor = this.this$0;
            KeyguardInteractor keyguardInteractor = fromAlternateBouncerTransitionInteractor.keyguardInteractor;
            StateFlow stateFlow = keyguardInteractor.primaryBouncerShowing;
            KeyguardTransitionInteractor$special$$inlined$filter$4 keyguardTransitionInteractor$special$$inlined$filter$4 = fromAlternateBouncerTransitionInteractor.keyguardTransitionInteractor.startedKeyguardTransitionStep;
            Utils.Companion companion = Utils.Companion;
            SafeFlow sample = FlowKt.sample(flowKt__TransformKt$onEach$$inlined$unsafeTransform$1, kotlinx.coroutines.flow.FlowKt.combine(stateFlow, keyguardTransitionInteractor$special$$inlined$filter$4, keyguardInteractor.wakefulnessModel, keyguardInteractor.isAodAvailable, new AnonymousClass2(companion)), new AnonymousClass3(companion));
            final FromAlternateBouncerTransitionInteractor fromAlternateBouncerTransitionInteractor2 = this.this$0;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.domain.interactor.FromAlternateBouncerTransitionInteractor$listenForAlternateBouncerToLockscreenAodOrDozing$1.4
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    Quint quint = (Quint) obj2;
                    boolean booleanValue = ((Boolean) quint.first).booleanValue();
                    boolean booleanValue2 = ((Boolean) quint.second).booleanValue();
                    TransitionStep transitionStep = (TransitionStep) quint.third;
                    WakefulnessModel wakefulnessModel = (WakefulnessModel) quint.fourth;
                    boolean booleanValue3 = ((Boolean) quint.fifth).booleanValue();
                    if (!booleanValue && !booleanValue2) {
                        KeyguardState keyguardState = transitionStep.f304to;
                        KeyguardState keyguardState2 = KeyguardState.ALTERNATE_BOUNCER;
                        if (keyguardState == keyguardState2) {
                            WakefulnessState wakefulnessState = wakefulnessModel.state;
                            KeyguardState keyguardState3 = (wakefulnessState == WakefulnessState.STARTING_TO_SLEEP || wakefulnessState == WakefulnessState.ASLEEP) ? booleanValue3 ? KeyguardState.AOD : KeyguardState.DOZING : KeyguardState.LOCKSCREEN;
                            FromAlternateBouncerTransitionInteractor fromAlternateBouncerTransitionInteractor3 = FromAlternateBouncerTransitionInteractor.this;
                            ((KeyguardTransitionRepositoryImpl) fromAlternateBouncerTransitionInteractor3.keyguardTransitionRepository).startTransition(new TransitionInfo(fromAlternateBouncerTransitionInteractor3.name, keyguardState2, keyguardState3, FromAlternateBouncerTransitionInteractor.access$getAnimator(fromAlternateBouncerTransitionInteractor3)), false);
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
