package com.android.systemui.deviceentry.data.repository;

import com.android.systemui.deviceentry.shared.model.ErrorFaceAuthenticationStatus;
import com.android.systemui.log.FaceAuthenticationLogger;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.flow.StateFlowImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final class DeviceEntryFaceAuthRepositoryImpl$cancel$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ DeviceEntryFaceAuthRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DeviceEntryFaceAuthRepositoryImpl$cancel$1(DeviceEntryFaceAuthRepositoryImpl deviceEntryFaceAuthRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = deviceEntryFaceAuthRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new DeviceEntryFaceAuthRepositoryImpl$cancel$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DeviceEntryFaceAuthRepositoryImpl$cancel$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            this.label = 1;
            if (DelayKt.delay(3000L, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        DeviceEntryFaceAuthRepositoryImpl deviceEntryFaceAuthRepositoryImpl = this.this$0;
        FaceAuthenticationLogger faceAuthenticationLogger = deviceEntryFaceAuthRepositoryImpl.faceAuthLogger;
        boolean booleanValue = ((Boolean) deviceEntryFaceAuthRepositoryImpl._isAuthRunning.getValue()).booleanValue();
        boolean booleanValue2 = ((Boolean) this.this$0._isLockedOut.getValue()).booleanValue();
        boolean booleanValue3 = ((Boolean) this.this$0.cancellationInProgress.getValue()).booleanValue();
        AuthenticationRequest authenticationRequest = (AuthenticationRequest) this.this$0.pendingAuthenticateRequest.getValue();
        faceAuthenticationLogger.cancelSignalNotReceived(authenticationRequest != null ? authenticationRequest.uiEvent : null, booleanValue, booleanValue2, booleanValue3);
        StateFlowImpl stateFlowImpl = this.this$0._authenticationStatus;
        ErrorFaceAuthenticationStatus.Companion.getClass();
        stateFlowImpl.updateState(null, new ErrorFaceAuthenticationStatus(-1, "", 0L, 4, null));
        DeviceEntryFaceAuthRepositoryImpl.access$onFaceAuthRequestCompleted(this.this$0);
        return Unit.INSTANCE;
    }
}
