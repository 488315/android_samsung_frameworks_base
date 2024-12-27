package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import com.android.systemui.util.kotlin.FlowKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;

public final class DeviceEntryIconViewModel$special$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ ShadeInteractor $shadeInteractor$inlined;
    final /* synthetic */ KeyguardTransitionInteractor $transitionInteractor$inlined;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ DeviceEntryIconViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DeviceEntryIconViewModel$special$$inlined$flatMapLatest$1(Continuation continuation, KeyguardTransitionInteractor keyguardTransitionInteractor, ShadeInteractor shadeInteractor, DeviceEntryIconViewModel deviceEntryIconViewModel) {
        super(3, continuation);
        this.$transitionInteractor$inlined = keyguardTransitionInteractor;
        this.$shadeInteractor$inlined = shadeInteractor;
        this.this$0 = deviceEntryIconViewModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        DeviceEntryIconViewModel$special$$inlined$flatMapLatest$1 deviceEntryIconViewModel$special$$inlined$flatMapLatest$1 = new DeviceEntryIconViewModel$special$$inlined$flatMapLatest$1((Continuation) obj3, this.$transitionInteractor$inlined, this.$shadeInteractor$inlined, this.this$0);
        deviceEntryIconViewModel$special$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        deviceEntryIconViewModel$special$$inlined$flatMapLatest$1.L$1 = obj2;
        return deviceEntryIconViewModel$special$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Flow flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            if (((Boolean) this.L$1).booleanValue()) {
                Flow sample = FlowKt.sample(this.$transitionInteractor$inlined.startedKeyguardTransitionStep, ((ShadeInteractorImpl) this.$shadeInteractor$inlined).isAnyFullyExpanded, DeviceEntryIconViewModel$burnInOffsets$1$2.INSTANCE);
                DeviceEntryIconViewModel deviceEntryIconViewModel = this.this$0;
                flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = kotlinx.coroutines.flow.FlowKt.combine(sample, deviceEntryIconViewModel.animatedBurnInOffsets, deviceEntryIconViewModel.nonAnimatedBurnInOffsets, new DeviceEntryIconViewModel$burnInOffsets$1$3(null));
            } else {
                flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(new BurnInOffsets(0, 0, 0.0f));
            }
            this.label = 1;
            if (kotlinx.coroutines.flow.FlowKt.emitAll(this, flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2, flowCollector) == coroutineSingletons) {
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
