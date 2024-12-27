package com.android.systemui.deviceentry.data.repository;

import android.hardware.face.FaceAuthenticateOptions;
import android.hardware.face.FaceManager;
import android.os.CancellationSignal;
import com.android.systemui.deviceentry.shared.FaceAuthUiEvent;
import com.android.systemui.keyguard.shared.model.SysUiFaceAuthenticateOptions;
import com.android.systemui.log.FaceAuthenticationLogger;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.user.data.repository.UserRepositoryImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class DeviceEntryFaceAuthRepositoryImpl$detect$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ FaceAuthUiEvent $uiEvent;
    int label;
    final /* synthetic */ DeviceEntryFaceAuthRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DeviceEntryFaceAuthRepositoryImpl$detect$2(DeviceEntryFaceAuthRepositoryImpl deviceEntryFaceAuthRepositoryImpl, FaceAuthUiEvent faceAuthUiEvent, Continuation continuation) {
        super(2, continuation);
        this.this$0 = deviceEntryFaceAuthRepositoryImpl;
        this.$uiEvent = faceAuthUiEvent;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new DeviceEntryFaceAuthRepositoryImpl$detect$2(this.this$0, this.$uiEvent, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DeviceEntryFaceAuthRepositoryImpl$detect$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        FaceAuthenticationLogger faceAuthenticationLogger = this.this$0.faceAuthLogger;
        faceAuthenticationLogger.getClass();
        faceAuthenticationLogger.logBuffer.log("DeviceEntryFaceAuthRepositoryLog", LogLevel.DEBUG, "Face detection started.", null);
        CancellationSignal cancellationSignal = this.this$0.detectCancellationSignal;
        if (cancellationSignal != null) {
            cancellationSignal.cancel();
        }
        this.this$0.detectCancellationSignal = new CancellationSignal();
        DeviceEntryFaceAuthRepositoryImpl deviceEntryFaceAuthRepositoryImpl = this.this$0;
        CancellationSignal cancellationSignal2 = deviceEntryFaceAuthRepositoryImpl.detectCancellationSignal;
        if (cancellationSignal2 == null) {
            return null;
        }
        FaceAuthUiEvent faceAuthUiEvent = this.$uiEvent;
        FaceManager faceManager = deviceEntryFaceAuthRepositoryImpl.faceManager;
        if (faceManager == null) {
            return null;
        }
        SysUiFaceAuthenticateOptions sysUiFaceAuthenticateOptions = new SysUiFaceAuthenticateOptions(((UserRepositoryImpl) deviceEntryFaceAuthRepositoryImpl.userRepository).getSelectedUserInfo().id, faceAuthUiEvent, faceAuthUiEvent.getExtraInfo());
        faceManager.detectFace(cancellationSignal2, deviceEntryFaceAuthRepositoryImpl.detectionCallback, new FaceAuthenticateOptions.Builder().setUserId(sysUiFaceAuthenticateOptions.userId).setAuthenticateReason(sysUiFaceAuthenticateOptions.authenticateReason).setWakeReason(sysUiFaceAuthenticateOptions.wakeReason).build());
        return Unit.INSTANCE;
    }
}
