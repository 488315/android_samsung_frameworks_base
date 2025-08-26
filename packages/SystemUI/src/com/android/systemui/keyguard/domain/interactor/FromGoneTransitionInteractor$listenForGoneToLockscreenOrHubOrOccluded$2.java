package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.communal.domain.interactor.CommunalSceneInteractor;
import com.android.systemui.communal.shared.model.CommunalScenes;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.util.kotlin.FlowKt;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* loaded from: classes2.dex */
final class FromGoneTransitionInteractor$listenForGoneToLockscreenOrHubOrOccluded$2 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ FromGoneTransitionInteractor this$0;

    /* renamed from: com.android.systemui.keyguard.domain.interactor.FromGoneTransitionInteractor$listenForGoneToLockscreenOrHubOrOccluded$2$4, reason: invalid class name */
    final /* synthetic */ class AnonymousClass4 extends AdaptedFunctionReference implements Function3 {
        public static final AnonymousClass4 INSTANCE = new AnonymousClass4();

        public AnonymousClass4() {
            super(3, Pair.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;)V", 4);
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            Boolean bool = (Boolean) obj;
            bool.booleanValue();
            Boolean bool2 = (Boolean) obj2;
            bool2.booleanValue();
            return new Pair(bool, bool2);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FromGoneTransitionInteractor$listenForGoneToLockscreenOrHubOrOccluded$2(FromGoneTransitionInteractor fromGoneTransitionInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = fromGoneTransitionInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new FromGoneTransitionInteractor$listenForGoneToLockscreenOrHubOrOccluded$2(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((FromGoneTransitionInteractor$listenForGoneToLockscreenOrHubOrOccluded$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FromGoneTransitionInteractor fromGoneTransitionInteractor = this.this$0;
            Flow flowSample = FlowKt.sample(new TransitionInteractor$filterRelevantKeyguardStateAnd$$inlined$filter$1(fromGoneTransitionInteractor.keyguardInteractor.isKeyguardShowing, fromGoneTransitionInteractor, new FromAlternateBouncerTransitionInteractor$listenForAlternateBouncerToPrimaryBouncer$1$$ExternalSyntheticLambda0(0)), this.this$0.communalSettingsInteractor.autoOpenEnabled, AnonymousClass4.INSTANCE);
            final FromGoneTransitionInteractor fromGoneTransitionInteractor2 = this.this$0;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.domain.interactor.FromGoneTransitionInteractor$listenForGoneToLockscreenOrHubOrOccluded$2.5
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    KeyguardState keyguardState;
                    boolean zBooleanValue = ((Boolean) ((Pair) obj2).component2()).booleanValue();
                    FromGoneTransitionInteractor fromGoneTransitionInteractor3 = fromGoneTransitionInteractor2;
                    if (((Boolean) fromGoneTransitionInteractor3.keyguardInteractor.isKeyguardOccluded.getValue()).booleanValue()) {
                        keyguardState = KeyguardState.OCCLUDED;
                    } else {
                        if (zBooleanValue) {
                            CommunalSceneInteractor.changeScene$default(fromGoneTransitionInteractor3.communalSceneInteractor, CommunalScenes.Communal, "keyguard interactor says keyguard is showing", null, null, 12);
                            return Unit.INSTANCE;
                        }
                        keyguardState = KeyguardState.LOCKSCREEN;
                    }
                    Object objStartTransitionTo$default = TransitionInteractor.startTransitionTo$default(fromGoneTransitionInteractor2, keyguardState, null, null, "keyguard interactor says keyguard is showing", continuation, 6);
                    return objStartTransitionTo$default == CoroutineSingletons.COROUTINE_SUSPENDED ? objStartTransitionTo$default : Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (flowSample.collect(flowCollector, this) == coroutineSingletons) {
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
