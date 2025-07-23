package com.android.systemui.bouncer.domain.interactor;

import com.android.systemui.bouncer.data.repository.KeyguardBouncerRepositoryImpl;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntryBiometricsAllowedInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1;
import kotlinx.coroutines.flow.StateFlowImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class AlternateBouncerInteractor$canShowAlternateBouncer$lambda$5$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ AlternateBouncerInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AlternateBouncerInteractor$canShowAlternateBouncer$lambda$5$$inlined$flatMapLatest$1(Continuation continuation, AlternateBouncerInteractor alternateBouncerInteractor) {
        super(3, continuation);
        this.this$0 = alternateBouncerInteractor;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        AlternateBouncerInteractor$canShowAlternateBouncer$lambda$5$$inlined$flatMapLatest$1 alternateBouncerInteractor$canShowAlternateBouncer$lambda$5$$inlined$flatMapLatest$1 = new AlternateBouncerInteractor$canShowAlternateBouncer$lambda$5$$inlined$flatMapLatest$1((Continuation) obj3, this.this$0);
        alternateBouncerInteractor$canShowAlternateBouncer$lambda$5$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        alternateBouncerInteractor$canShowAlternateBouncer$lambda$5$$inlined$flatMapLatest$1.L$1 = obj2;
        return alternateBouncerInteractor$canShowAlternateBouncer$lambda$5$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Flow combine;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            Pair pair = (Pair) this.L$1;
            KeyguardState keyguardState = (KeyguardState) pair.component1();
            if (keyguardState == KeyguardState.GONE) {
                combine = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.FALSE);
            } else {
                FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 = ((DeviceEntryBiometricsAllowedInteractor) this.this$0.deviceEntryBiometricsAllowedInteractor.get()).isFingerprintAuthCurrentlyAllowed;
                StateFlowImpl stateFlowImpl = ((KeyguardInteractor) this.this$0.keyguardInteractor.get()).isKeyguardDismissible;
                AlternateBouncerInteractor alternateBouncerInteractor = this.this$0;
                combine = FlowKt.combine(flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1, stateFlowImpl, ((KeyguardBouncerRepositoryImpl) alternateBouncerInteractor.bouncerRepository).primaryBouncerShow, alternateBouncerInteractor.isDozingOrAod, new AlternateBouncerInteractor$canShowAlternateBouncer$1$4$1(null));
            }
            this.label = 1;
            if (FlowKt.emitAll(flowCollector, combine, this) == coroutineSingletons) {
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
