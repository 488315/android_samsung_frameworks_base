package com.android.systemui.keyguard.domain.interactor;

import android.util.Log;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.util.kotlin.Quad;
import com.android.systemui.util.kotlin.Utils;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* loaded from: classes2.dex */
final class FromPrimaryBouncerTransitionInteractor$listenForPrimaryBouncerNotShowing$2 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ FromPrimaryBouncerTransitionInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FromPrimaryBouncerTransitionInteractor$listenForPrimaryBouncerNotShowing$2(FromPrimaryBouncerTransitionInteractor fromPrimaryBouncerTransitionInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = fromPrimaryBouncerTransitionInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new FromPrimaryBouncerTransitionInteractor$listenForPrimaryBouncerNotShowing$2(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((FromPrimaryBouncerTransitionInteractor$listenForPrimaryBouncerNotShowing$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Utils.Companion companion = Utils.Companion;
            FromPrimaryBouncerTransitionInteractor fromPrimaryBouncerTransitionInteractor = this.this$0;
            TransitionInteractor$filterRelevantKeyguardStateAnd$$inlined$filter$1 transitionInteractor$filterRelevantKeyguardStateAnd$$inlined$filter$1 = new TransitionInteractor$filterRelevantKeyguardStateAnd$$inlined$filter$1(fromPrimaryBouncerTransitionInteractor.keyguardInteractor.primaryBouncerShowing, fromPrimaryBouncerTransitionInteractor, new FromPrimaryBouncerTransitionInteractor$listenForPrimaryBouncerNotShowing$2$$ExternalSyntheticLambda0());
            FromPrimaryBouncerTransitionInteractor fromPrimaryBouncerTransitionInteractor2 = this.this$0;
            Flow flowSample = companion.sample(transitionInteractor$filterRelevantKeyguardStateAnd$$inlined$filter$1, fromPrimaryBouncerTransitionInteractor2.powerInteractor.isAwake, fromPrimaryBouncerTransitionInteractor2.keyguardInteractor.isDreaming, fromPrimaryBouncerTransitionInteractor2.communalSceneInteractor.isIdleOnCommunal);
            final FromPrimaryBouncerTransitionInteractor fromPrimaryBouncerTransitionInteractor3 = this.this$0;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.domain.interactor.FromPrimaryBouncerTransitionInteractor$listenForPrimaryBouncerNotShowing$2.2
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    KeyguardState keyguardState;
                    Quad quad = (Quad) obj2;
                    boolean zBooleanValue = ((Boolean) quad.component2()).booleanValue();
                    boolean zBooleanValue2 = ((Boolean) quad.component3()).booleanValue();
                    boolean zBooleanValue3 = ((Boolean) quad.component4()).booleanValue();
                    FromPrimaryBouncerTransitionInteractor fromPrimaryBouncerTransitionInteractor4 = fromPrimaryBouncerTransitionInteractor3;
                    boolean zBooleanValue4 = ((Boolean) fromPrimaryBouncerTransitionInteractor4.keyguardInteractor.isKeyguardOccluded.getValue()).booleanValue();
                    fromPrimaryBouncerTransitionInteractor4.communalSettingsInteractor.isV2FlagEnabled();
                    if (zBooleanValue) {
                        keyguardState = (!zBooleanValue4 || zBooleanValue2) ? zBooleanValue3 ? KeyguardState.GLANCEABLE_HUB : zBooleanValue2 ? KeyguardState.DREAMING : KeyguardState.LOCKSCREEN : KeyguardState.OCCLUDED;
                    } else {
                        Log.i("FromPrimaryBouncerTransitionInteractor", "Going back to sleeping state to correct an attempt to show bouncer");
                        keyguardState = (KeyguardState) fromPrimaryBouncerTransitionInteractor4.keyguardInteractor.asleepKeyguardState.$$delegate_0.getValue();
                    }
                    Object objStartTransitionTo$default = TransitionInteractor.startTransitionTo$default(fromPrimaryBouncerTransitionInteractor3, keyguardState, null, null, null, continuation, 14);
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
