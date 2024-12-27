package com.android.systemui.camera.data.repository;

import android.os.UserHandle;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.FlowCollector;

final class CameraAutoRotateRepositoryImpl$isCameraAutoRotateSettingEnabled$1$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ UserHandle $userHandle;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ CameraAutoRotateRepositoryImpl this$0;

    public CameraAutoRotateRepositoryImpl$isCameraAutoRotateSettingEnabled$1$2(CameraAutoRotateRepositoryImpl cameraAutoRotateRepositoryImpl, UserHandle userHandle, Continuation continuation) {
        super(2, continuation);
        this.this$0 = cameraAutoRotateRepositoryImpl;
        this.$userHandle = userHandle;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        CameraAutoRotateRepositoryImpl$isCameraAutoRotateSettingEnabled$1$2 cameraAutoRotateRepositoryImpl$isCameraAutoRotateSettingEnabled$1$2 = new CameraAutoRotateRepositoryImpl$isCameraAutoRotateSettingEnabled$1$2(this.this$0, this.$userHandle, continuation);
        cameraAutoRotateRepositoryImpl$isCameraAutoRotateSettingEnabled$1$2.L$0 = obj;
        return cameraAutoRotateRepositoryImpl$isCameraAutoRotateSettingEnabled$1$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CameraAutoRotateRepositoryImpl$isCameraAutoRotateSettingEnabled$1$2) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            Boolean valueOf = Boolean.valueOf(this.this$0.secureSettings.getIntForUser("camera_autorotate", 0, this.$userHandle.getIdentifier()) == 1);
            this.label = 1;
            if (flowCollector.emit(valueOf, this) == coroutineSingletons) {
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
