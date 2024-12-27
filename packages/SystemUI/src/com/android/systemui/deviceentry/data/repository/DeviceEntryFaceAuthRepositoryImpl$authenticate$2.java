package com.android.systemui.deviceentry.data.repository;

import android.hardware.biometrics.CryptoObject;
import android.hardware.face.FaceAuthenticateOptions;
import android.hardware.face.FaceManager;
import android.os.CancellationSignal;
import android.os.Handler;
import com.android.systemui.deviceentry.shared.FaceAuthUiEvent;
import com.android.systemui.keyguard.shared.model.SysUiFaceAuthenticateOptions;
import com.android.systemui.user.data.repository.UserRepositoryImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

final class DeviceEntryFaceAuthRepositoryImpl$authenticate$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ FaceAuthUiEvent $uiEvent;
    int label;
    final /* synthetic */ DeviceEntryFaceAuthRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DeviceEntryFaceAuthRepositoryImpl$authenticate$2(DeviceEntryFaceAuthRepositoryImpl deviceEntryFaceAuthRepositoryImpl, FaceAuthUiEvent faceAuthUiEvent, Continuation continuation) {
        super(2, continuation);
        this.this$0 = deviceEntryFaceAuthRepositoryImpl;
        this.$uiEvent = faceAuthUiEvent;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new DeviceEntryFaceAuthRepositoryImpl$authenticate$2(this.this$0, this.$uiEvent, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DeviceEntryFaceAuthRepositoryImpl$authenticate$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        this.this$0.authCancellationSignal = new CancellationSignal();
        this.this$0._isAuthRunning.updateState(null, Boolean.TRUE);
        DeviceEntryFaceAuthRepositoryImpl deviceEntryFaceAuthRepositoryImpl = this.this$0;
        deviceEntryFaceAuthRepositoryImpl.uiEventsLogger.logWithInstanceIdAndPosition(this.$uiEvent, 0, (String) null, deviceEntryFaceAuthRepositoryImpl.sessionTracker.getSessionId(1), this.$uiEvent.getExtraInfo());
        this.this$0.faceAuthLogger.authenticating(this.$uiEvent);
        DeviceEntryFaceAuthRepositoryImpl deviceEntryFaceAuthRepositoryImpl2 = this.this$0;
        FaceManager faceManager = deviceEntryFaceAuthRepositoryImpl2.faceManager;
        if (faceManager == null) {
            return null;
        }
        CancellationSignal cancellationSignal = deviceEntryFaceAuthRepositoryImpl2.authCancellationSignal;
        int i = ((UserRepositoryImpl) deviceEntryFaceAuthRepositoryImpl2.userRepository).getSelectedUserInfo().id;
        FaceAuthUiEvent faceAuthUiEvent = this.$uiEvent;
        SysUiFaceAuthenticateOptions sysUiFaceAuthenticateOptions = new SysUiFaceAuthenticateOptions(i, faceAuthUiEvent, faceAuthUiEvent.getExtraInfo());
        faceManager.authenticate((CryptoObject) null, cancellationSignal, deviceEntryFaceAuthRepositoryImpl2.faceAuthCallback, (Handler) null, new FaceAuthenticateOptions.Builder().setUserId(sysUiFaceAuthenticateOptions.userId).setAuthenticateReason(sysUiFaceAuthenticateOptions.authenticateReason).setWakeReason(sysUiFaceAuthenticateOptions.wakeReason).build());
        return Unit.INSTANCE;
    }
}
