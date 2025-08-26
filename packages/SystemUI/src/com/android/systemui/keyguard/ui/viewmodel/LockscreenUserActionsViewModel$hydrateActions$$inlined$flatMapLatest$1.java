package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.shade.domain.interactor.ShadeModeInteractorImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;

/* loaded from: classes2.dex */
public final class LockscreenUserActionsViewModel$hydrateActions$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ LockscreenUserActionsViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LockscreenUserActionsViewModel$hydrateActions$$inlined$flatMapLatest$1(Continuation continuation, LockscreenUserActionsViewModel lockscreenUserActionsViewModel) {
        super(3, continuation);
        this.this$0 = lockscreenUserActionsViewModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        LockscreenUserActionsViewModel$hydrateActions$$inlined$flatMapLatest$1 lockscreenUserActionsViewModel$hydrateActions$$inlined$flatMapLatest$1 = new LockscreenUserActionsViewModel$hydrateActions$$inlined$flatMapLatest$1((Continuation) obj3, this.this$0);
        lockscreenUserActionsViewModel$hydrateActions$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        lockscreenUserActionsViewModel$hydrateActions$$inlined$flatMapLatest$1.L$1 = obj2;
        return lockscreenUserActionsViewModel$hydrateActions$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Flow flowCombine;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            if (((Boolean) this.L$1).booleanValue()) {
                LockscreenUserActionsViewModel lockscreenUserActionsViewModel = this.this$0;
                flowCombine = FlowKt.combine(lockscreenUserActionsViewModel.deviceEntryInteractor.isUnlocked, ((ShadeModeInteractorImpl) lockscreenUserActionsViewModel.shadeModeInteractor).shadeMode, lockscreenUserActionsViewModel.occlusionInteractor.isOccludingActivityShown, new LockscreenUserActionsViewModel$hydrateActions$2$1(null));
            } else {
                flowCombine = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(MapsKt__MapsKt.emptyMap());
            }
            this.label = 1;
            if (FlowKt.emitAll(flowCollector, flowCombine, this) == coroutineSingletons) {
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
