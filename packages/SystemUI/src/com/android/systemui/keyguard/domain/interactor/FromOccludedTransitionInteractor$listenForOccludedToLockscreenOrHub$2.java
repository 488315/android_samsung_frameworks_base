package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.communal.domain.interactor.CommunalInteractor;
import com.android.systemui.util.kotlin.Quint;
import com.android.systemui.util.kotlin.Utils;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final class FromOccludedTransitionInteractor$listenForOccludedToLockscreenOrHub$2 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ FromOccludedTransitionInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FromOccludedTransitionInteractor$listenForOccludedToLockscreenOrHub$2(FromOccludedTransitionInteractor fromOccludedTransitionInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = fromOccludedTransitionInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new FromOccludedTransitionInteractor$listenForOccludedToLockscreenOrHub$2(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((FromOccludedTransitionInteractor$listenForOccludedToLockscreenOrHub$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FromOccludedTransitionInteractor fromOccludedTransitionInteractor = this.this$0;
            Utils.Companion companion = Utils.Companion;
            KeyguardInteractor keyguardInteractor = fromOccludedTransitionInteractor.keyguardInteractor;
            Flow flow = keyguardInteractor.isKeyguardOccluded;
            CommunalInteractor communalInteractor = fromOccludedTransitionInteractor.communalInteractor;
            TransitionInteractor$filterRelevantKeyguardStateAnd$$inlined$filter$1 transitionInteractor$filterRelevantKeyguardStateAnd$$inlined$filter$1 = new TransitionInteractor$filterRelevantKeyguardStateAnd$$inlined$filter$1(companion.sample(flow, keyguardInteractor.isKeyguardShowing, communalInteractor.isIdleOnCommunal, communalInteractor.showCommunalFromOccluded, communalInteractor.dreamFromOccluded), fromOccludedTransitionInteractor, new Function1() { // from class: com.android.systemui.keyguard.domain.interactor.FromOccludedTransitionInteractor$listenForOccludedToLockscreenOrHub$2.1
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj2) {
                    Quint quint = (Quint) obj2;
                    return Boolean.valueOf(!((Boolean) quint.component1()).booleanValue() && ((Boolean) quint.component2()).booleanValue());
                }
            });
            final FromOccludedTransitionInteractor fromOccludedTransitionInteractor2 = this.this$0;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.domain.interactor.FromOccludedTransitionInteractor$listenForOccludedToLockscreenOrHub$2.2
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    Quint quint = (Quint) obj2;
                    boolean booleanValue = ((Boolean) quint.component3()).booleanValue();
                    boolean booleanValue2 = ((Boolean) quint.component4()).booleanValue();
                    ((Boolean) quint.component5()).getClass();
                    FromOccludedTransitionInteractor fromOccludedTransitionInteractor3 = FromOccludedTransitionInteractor.this;
                    Object access$startTransitionToLockscreenOrHub = FromOccludedTransitionInteractor.access$startTransitionToLockscreenOrHub(fromOccludedTransitionInteractor3, fromOccludedTransitionInteractor3, booleanValue, booleanValue2, continuation);
                    return access$startTransitionToLockscreenOrHub == CoroutineSingletons.COROUTINE_SUSPENDED ? access$startTransitionToLockscreenOrHub : Unit.INSTANCE;
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
