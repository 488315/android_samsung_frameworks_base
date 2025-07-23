package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.util.kotlin.Septuple;
import com.android.systemui.util.kotlin.Utils;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class FromAlternateBouncerTransitionInteractor$listenForAlternateBouncerToLockscreenHubAodOrDozing$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ FromAlternateBouncerTransitionInteractor this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.keyguard.domain.interactor.FromAlternateBouncerTransitionInteractor$listenForAlternateBouncerToLockscreenHubAodOrDozing$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
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
                if (DelayKt.delay(150L, this) == coroutineSingletons) {
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

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FromAlternateBouncerTransitionInteractor$listenForAlternateBouncerToLockscreenHubAodOrDozing$1(FromAlternateBouncerTransitionInteractor fromAlternateBouncerTransitionInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = fromAlternateBouncerTransitionInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new FromAlternateBouncerTransitionInteractor$listenForAlternateBouncerToLockscreenHubAodOrDozing$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((FromAlternateBouncerTransitionInteractor$listenForAlternateBouncerToLockscreenHubAodOrDozing$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FromAlternateBouncerTransitionInteractor fromAlternateBouncerTransitionInteractor = this.this$0;
            Utils.Companion companion = Utils.Companion;
            FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 flowKt__TransformKt$onEach$$inlined$unsafeTransform$1 = new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(fromAlternateBouncerTransitionInteractor.keyguardInteractor.alternateBouncerShowing, new AnonymousClass1(null));
            FromAlternateBouncerTransitionInteractor fromAlternateBouncerTransitionInteractor2 = this.this$0;
            KeyguardInteractor keyguardInteractor = fromAlternateBouncerTransitionInteractor2.keyguardInteractor;
            TransitionInteractor$filterRelevantKeyguardStateAnd$$inlined$filter$1 transitionInteractor$filterRelevantKeyguardStateAnd$$inlined$filter$1 = new TransitionInteractor$filterRelevantKeyguardStateAnd$$inlined$filter$1(companion.sample(flowKt__TransformKt$onEach$$inlined$unsafeTransform$1, keyguardInteractor.primaryBouncerShowing, fromAlternateBouncerTransitionInteractor2.powerInteractor.isAwake, keyguardInteractor.isAodAvailable, fromAlternateBouncerTransitionInteractor2.communalSceneInteractor.isIdleOnCommunal, keyguardInteractor.isDreaming, keyguardInteractor.isKeyguardOccluded), fromAlternateBouncerTransitionInteractor, new FromAlternateBouncerTransitionInteractor$listenForAlternateBouncerToPrimaryBouncer$1$$ExternalSyntheticLambda0(1));
            final FromAlternateBouncerTransitionInteractor fromAlternateBouncerTransitionInteractor3 = this.this$0;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.domain.interactor.FromAlternateBouncerTransitionInteractor$listenForAlternateBouncerToLockscreenHubAodOrDozing$1.3
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    Septuple septuple = (Septuple) obj2;
                    boolean booleanValue = ((Boolean) septuple.component3()).booleanValue();
                    boolean booleanValue2 = ((Boolean) septuple.component4()).booleanValue();
                    boolean booleanValue3 = ((Boolean) septuple.component5()).booleanValue();
                    boolean booleanValue4 = ((Boolean) septuple.component6()).booleanValue();
                    boolean booleanValue5 = ((Boolean) septuple.component7()).booleanValue();
                    FromAlternateBouncerTransitionInteractor fromAlternateBouncerTransitionInteractor4 = FromAlternateBouncerTransitionInteractor.this;
                    if (((Boolean) fromAlternateBouncerTransitionInteractor4.communalInteractor.editModeOpen.$$delegate_0.getValue()).booleanValue()) {
                        return Unit.INSTANCE;
                    }
                    fromAlternateBouncerTransitionInteractor4.communalSettingsInteractor.isV2FlagEnabled();
                    Object startTransitionTo$default = TransitionInteractor.startTransitionTo$default(FromAlternateBouncerTransitionInteractor.this, !booleanValue ? booleanValue2 ? KeyguardState.AOD : KeyguardState.DOZING : booleanValue3 ? KeyguardState.GLANCEABLE_HUB : (!booleanValue5 || booleanValue4) ? booleanValue4 ? KeyguardState.DREAMING : KeyguardState.LOCKSCREEN : KeyguardState.OCCLUDED, null, null, null, continuation, 14);
                    return startTransitionTo$default == CoroutineSingletons.COROUTINE_SUSPENDED ? startTransitionTo$default : Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (transitionInteractor$filterRelevantKeyguardStateAnd$$inlined$filter$1.collect(flowCollector, this) == coroutineSingletons) {
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
