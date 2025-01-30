package com.android.systemui.keyguard.data.repository;

import android.hardware.biometrics.CryptoObject;
import android.hardware.face.FaceAuthenticateOptions;
import android.hardware.face.FaceManager;
import android.os.CancellationSignal;
import android.os.Handler;
import com.android.keyguard.FaceAuthUiEvent;
import com.android.systemui.user.data.repository.UserRepositoryImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.keyguard.data.repository.DeviceEntryFaceAuthRepositoryImpl$authenticate$2", m277f = "DeviceEntryFaceAuthRepository.kt", m278l = {}, m279m = "invokeSuspend")
/* loaded from: classes.dex */
final class DeviceEntryFaceAuthRepositoryImpl$authenticate$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ FaceAuthUiEvent $uiEvent;
    int label;
    final /* synthetic */ DeviceEntryFaceAuthRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DeviceEntryFaceAuthRepositoryImpl$authenticate$2(DeviceEntryFaceAuthRepositoryImpl deviceEntryFaceAuthRepositoryImpl, FaceAuthUiEvent faceAuthUiEvent, Continuation<? super DeviceEntryFaceAuthRepositoryImpl$authenticate$2> continuation) {
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
        this.this$0._isAuthRunning.setValue(Boolean.TRUE);
        DeviceEntryFaceAuthRepositoryImpl deviceEntryFaceAuthRepositoryImpl = this.this$0;
        deviceEntryFaceAuthRepositoryImpl.uiEventsLogger.logWithInstanceIdAndPosition(this.$uiEvent, 0, (String) null, deviceEntryFaceAuthRepositoryImpl.sessionTracker.getSessionId(1), this.$uiEvent.getExtraInfo());
        this.this$0.faceAuthLogger.authenticating(this.$uiEvent);
        DeviceEntryFaceAuthRepositoryImpl deviceEntryFaceAuthRepositoryImpl2 = this.this$0;
        FaceManager faceManager = deviceEntryFaceAuthRepositoryImpl2.faceManager;
        if (faceManager == null) {
            return null;
        }
        faceManager.authenticate((CryptoObject) null, deviceEntryFaceAuthRepositoryImpl2.authCancellationSignal, deviceEntryFaceAuthRepositoryImpl2.faceAuthCallback, (Handler) null, new FaceAuthenticateOptions.Builder().setUserId(((UserRepositoryImpl) this.this$0.userRepository).getSelectedUserInfo().id).build());
        return Unit.INSTANCE;
    }
}
