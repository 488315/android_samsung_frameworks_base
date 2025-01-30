package com.android.systemui.keyguard.data.repository;

import android.hardware.face.FaceAuthenticateOptions;
import android.hardware.face.FaceManager;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
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
@DebugMetadata(m276c = "com.android.systemui.keyguard.data.repository.DeviceEntryFaceAuthRepositoryImpl$detect$2", m277f = "DeviceEntryFaceAuthRepository.kt", m278l = {}, m279m = "invokeSuspend")
/* loaded from: classes.dex */
final class DeviceEntryFaceAuthRepositoryImpl$detect$2 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ DeviceEntryFaceAuthRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DeviceEntryFaceAuthRepositoryImpl$detect$2(DeviceEntryFaceAuthRepositoryImpl deviceEntryFaceAuthRepositoryImpl, Continuation<? super DeviceEntryFaceAuthRepositoryImpl$detect$2> continuation) {
        super(2, continuation);
        this.this$0 = deviceEntryFaceAuthRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new DeviceEntryFaceAuthRepositoryImpl$detect$2(this.this$0, continuation);
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
        LogBuffer.log$default(this.this$0.faceAuthLogger.logBuffer, "DeviceEntryFaceAuthRepositoryLog", LogLevel.DEBUG, "Face detection started.", null, 8, null);
        DeviceEntryFaceAuthRepositoryImpl deviceEntryFaceAuthRepositoryImpl = this.this$0;
        FaceManager faceManager = deviceEntryFaceAuthRepositoryImpl.faceManager;
        if (faceManager == null) {
            return null;
        }
        faceManager.detectFace(deviceEntryFaceAuthRepositoryImpl.detectCancellationSignal, deviceEntryFaceAuthRepositoryImpl.detectionCallback, new FaceAuthenticateOptions.Builder().setUserId(((UserRepositoryImpl) this.this$0.userRepository).getSelectedUserInfo().id).build());
        return Unit.INSTANCE;
    }
}
