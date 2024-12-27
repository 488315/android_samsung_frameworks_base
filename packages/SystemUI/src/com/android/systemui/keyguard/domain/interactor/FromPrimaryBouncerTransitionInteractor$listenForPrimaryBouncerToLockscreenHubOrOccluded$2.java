package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.util.kotlin.Sextuple;
import com.android.systemui.util.kotlin.Utils;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final class FromPrimaryBouncerTransitionInteractor$listenForPrimaryBouncerToLockscreenHubOrOccluded$2 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ FromPrimaryBouncerTransitionInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FromPrimaryBouncerTransitionInteractor$listenForPrimaryBouncerToLockscreenHubOrOccluded$2(FromPrimaryBouncerTransitionInteractor fromPrimaryBouncerTransitionInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = fromPrimaryBouncerTransitionInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new FromPrimaryBouncerTransitionInteractor$listenForPrimaryBouncerToLockscreenHubOrOccluded$2(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((FromPrimaryBouncerTransitionInteractor$listenForPrimaryBouncerToLockscreenHubOrOccluded$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FromPrimaryBouncerTransitionInteractor fromPrimaryBouncerTransitionInteractor = this.this$0;
            Utils.Companion companion = Utils.Companion;
            KeyguardInteractor keyguardInteractor = fromPrimaryBouncerTransitionInteractor.keyguardInteractor;
            TransitionInteractor$filterRelevantKeyguardStateAnd$$inlined$filter$1 transitionInteractor$filterRelevantKeyguardStateAnd$$inlined$filter$1 = new TransitionInteractor$filterRelevantKeyguardStateAnd$$inlined$filter$1(companion.sample(keyguardInteractor.primaryBouncerShowing, fromPrimaryBouncerTransitionInteractor.powerInteractor.isAwake, keyguardInteractor.isKeyguardOccluded, keyguardInteractor.isDreaming, keyguardInteractor.isActiveDreamLockscreenHosted, fromPrimaryBouncerTransitionInteractor.communalInteractor.isIdleOnCommunal), fromPrimaryBouncerTransitionInteractor, new Function1() { // from class: com.android.systemui.keyguard.domain.interactor.FromPrimaryBouncerTransitionInteractor$listenForPrimaryBouncerToLockscreenHubOrOccluded$2.1
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj2) {
                    Sextuple sextuple = (Sextuple) obj2;
                    return Boolean.valueOf((((Boolean) sextuple.component1()).booleanValue() || !((Boolean) sextuple.component2()).booleanValue() || ((Boolean) sextuple.component5()).booleanValue()) ? false : true);
                }
            });
            final FromPrimaryBouncerTransitionInteractor fromPrimaryBouncerTransitionInteractor2 = this.this$0;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.domain.interactor.FromPrimaryBouncerTransitionInteractor$listenForPrimaryBouncerToLockscreenHubOrOccluded$2.2
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    Sextuple sextuple = (Sextuple) obj2;
                    boolean booleanValue = ((Boolean) sextuple.component3()).booleanValue();
                    boolean booleanValue2 = ((Boolean) sextuple.component4()).booleanValue();
                    Object startTransitionTo$default = TransitionInteractor.startTransitionTo$default(FromPrimaryBouncerTransitionInteractor.this, (!booleanValue || booleanValue2) ? ((Boolean) sextuple.component6()).booleanValue() ? KeyguardState.GLANCEABLE_HUB : booleanValue2 ? KeyguardState.DREAMING : KeyguardState.LOCKSCREEN : KeyguardState.OCCLUDED, null, null, null, continuation, 14);
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
