package com.android.systemui.deviceentry.domain.interactor;

import com.android.systemui.deviceentry.shared.model.AcquiredFaceAuthenticationStatus;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

final class FaceHelpMessageDeferralInteractor$startUpdatingFaceHelpMessageDeferral$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ FaceHelpMessageDeferralInteractor this$0;

    public FaceHelpMessageDeferralInteractor$startUpdatingFaceHelpMessageDeferral$1(FaceHelpMessageDeferralInteractor faceHelpMessageDeferralInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = faceHelpMessageDeferralInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new FaceHelpMessageDeferralInteractor$startUpdatingFaceHelpMessageDeferral$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((FaceHelpMessageDeferralInteractor$startUpdatingFaceHelpMessageDeferral$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FaceHelpMessageDeferralInteractor faceHelpMessageDeferralInteractor = this.this$0;
            ChannelFlowTransformLatest transformLatest = FlowKt.transformLatest(faceHelpMessageDeferralInteractor.biometricSettingsInteractor.isFaceAuthEnrolledAndEnabled, new FaceHelpMessageDeferralInteractor$startUpdatingFaceHelpMessageDeferral$1$invokeSuspend$$inlined$flatMapLatest$1(null, faceHelpMessageDeferralInteractor));
            final FaceHelpMessageDeferralInteractor faceHelpMessageDeferralInteractor2 = this.this$0;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.deviceentry.domain.interactor.FaceHelpMessageDeferralInteractor$startUpdatingFaceHelpMessageDeferral$1.2
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    AcquiredFaceAuthenticationStatus acquiredFaceAuthenticationStatus = (AcquiredFaceAuthenticationStatus) obj2;
                    int i2 = acquiredFaceAuthenticationStatus.acquiredInfo;
                    FaceHelpMessageDeferralInteractor faceHelpMessageDeferralInteractor3 = FaceHelpMessageDeferralInteractor.this;
                    if (i2 == 20) {
                        faceHelpMessageDeferralInteractor3.faceHelpMessageDeferral.reset$1();
                    }
                    faceHelpMessageDeferralInteractor3.faceHelpMessageDeferral.processFrame(acquiredFaceAuthenticationStatus.acquiredInfo);
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (transformLatest.collect(flowCollector, this) == coroutineSingletons) {
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
