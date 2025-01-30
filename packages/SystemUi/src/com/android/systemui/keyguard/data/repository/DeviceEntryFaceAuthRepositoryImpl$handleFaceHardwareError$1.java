package com.android.systemui.keyguard.data.repository;

import com.android.keyguard.FaceAuthUiEvent;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.keyguard.data.repository.DeviceEntryFaceAuthRepositoryImpl$handleFaceHardwareError$1", m277f = "DeviceEntryFaceAuthRepository.kt", m278l = {453, 456}, m279m = "invokeSuspend")
/* loaded from: classes.dex */
final class DeviceEntryFaceAuthRepositoryImpl$handleFaceHardwareError$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ DeviceEntryFaceAuthRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DeviceEntryFaceAuthRepositoryImpl$handleFaceHardwareError$1(DeviceEntryFaceAuthRepositoryImpl deviceEntryFaceAuthRepositoryImpl, Continuation<? super DeviceEntryFaceAuthRepositoryImpl$handleFaceHardwareError$1> continuation) {
        super(2, continuation);
        this.this$0 = deviceEntryFaceAuthRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new DeviceEntryFaceAuthRepositoryImpl$handleFaceHardwareError$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DeviceEntryFaceAuthRepositoryImpl$handleFaceHardwareError$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            this.label = 1;
            if (DelayKt.delay(500L, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                if (i != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            }
            ResultKt.throwOnFailure(obj);
        }
        DeviceEntryFaceAuthRepositoryImpl deviceEntryFaceAuthRepositoryImpl = this.this$0;
        int i2 = deviceEntryFaceAuthRepositoryImpl.retryCount;
        if (i2 < 20) {
            deviceEntryFaceAuthRepositoryImpl.faceAuthLogger.attemptingRetryAfterHardwareError(i2);
            DeviceEntryFaceAuthRepositoryImpl deviceEntryFaceAuthRepositoryImpl2 = this.this$0;
            FaceAuthUiEvent faceAuthUiEvent = FaceAuthUiEvent.FACE_AUTH_TRIGGERED_RETRY_AFTER_HW_UNAVAILABLE;
            this.label = 2;
            if (deviceEntryFaceAuthRepositoryImpl2.authenticate(faceAuthUiEvent, false, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        }
        return Unit.INSTANCE;
    }
}
