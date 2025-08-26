package com.android.systemui.biometrics.data.repository;

import android.hardware.camera2.CameraManager;
import com.android.systemui.common.coroutine.ChannelExt;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* loaded from: classes.dex */
final class FacePropertyRepositoryImpl$cameraInfo$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Executor $mainExecutor;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ FacePropertyRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FacePropertyRepositoryImpl$cameraInfo$1(FacePropertyRepositoryImpl facePropertyRepositoryImpl, Executor executor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = facePropertyRepositoryImpl;
        this.$mainExecutor = executor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        FacePropertyRepositoryImpl$cameraInfo$1 facePropertyRepositoryImpl$cameraInfo$1 = new FacePropertyRepositoryImpl$cameraInfo$1(this.this$0, this.$mainExecutor, continuation);
        facePropertyRepositoryImpl$cameraInfo$1.L$0 = obj;
        return facePropertyRepositoryImpl$cameraInfo$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((FacePropertyRepositoryImpl$cameraInfo$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [android.hardware.camera2.CameraManager$AvailabilityCallback, com.android.systemui.biometrics.data.repository.FacePropertyRepositoryImpl$cameraInfo$1$callback$1] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final FacePropertyRepositoryImpl facePropertyRepositoryImpl = this.this$0;
            final ?? r1 = new CameraManager.AvailabilityCallback() { // from class: com.android.systemui.biometrics.data.repository.FacePropertyRepositoryImpl$cameraInfo$1$callback$1
                @Override // android.hardware.camera2.CameraManager.AvailabilityCallback
                public final void onPhysicalCameraAvailable(String str, String str2) {
                    Object obj2;
                    FacePropertyRepositoryImpl facePropertyRepositoryImpl2 = facePropertyRepositoryImpl;
                    facePropertyRepositoryImpl2.currentPhysicalCameraId = str2;
                    ArrayList arrayList = (ArrayList) facePropertyRepositoryImpl2.cameraInfoList;
                    int size = arrayList.size();
                    int i2 = 0;
                    while (true) {
                        if (i2 >= size) {
                            obj2 = null;
                            break;
                        }
                        obj2 = arrayList.get(i2);
                        i2++;
                        if (Intrinsics.areEqual(str2, ((CameraInfo) obj2).cameraPhysicalId)) {
                            break;
                        }
                    }
                    CameraInfo cameraInfo = (CameraInfo) obj2;
                    ChannelExt channelExt = ChannelExt.INSTANCE;
                    channelExt.getClass();
                    ChannelExt.trySendWithFailureLogging(producerScope, cameraInfo, "FaceSensorPropertyRepositoryImpl", "Update face sensor location to " + cameraInfo + ".");
                }

                @Override // android.hardware.camera2.CameraManager.AvailabilityCallback
                public final void onPhysicalCameraUnavailable(String str, String str2) {
                    Object obj2;
                    FacePropertyRepositoryImpl facePropertyRepositoryImpl2 = facePropertyRepositoryImpl;
                    if (facePropertyRepositoryImpl2.currentPhysicalCameraId == null) {
                        ArrayList arrayList = (ArrayList) facePropertyRepositoryImpl2.cameraInfoList;
                        int size = arrayList.size();
                        int i2 = 0;
                        while (true) {
                            if (i2 >= size) {
                                obj2 = null;
                                break;
                            }
                            obj2 = arrayList.get(i2);
                            i2++;
                            if (!Intrinsics.areEqual(str2, ((CameraInfo) obj2).cameraPhysicalId)) {
                                break;
                            }
                        }
                        CameraInfo cameraInfo = (CameraInfo) obj2;
                        facePropertyRepositoryImpl.currentPhysicalCameraId = cameraInfo != null ? cameraInfo.cameraPhysicalId : null;
                        ChannelExt channelExt = ChannelExt.INSTANCE;
                        channelExt.getClass();
                        ChannelExt.trySendWithFailureLogging(producerScope, cameraInfo, "FaceSensorPropertyRepositoryImpl", "Update face sensor location to " + cameraInfo + ".");
                    }
                }
            };
            this.this$0.cameraManager.registerAvailabilityCallback(this.$mainExecutor, (CameraManager.AvailabilityCallback) r1);
            final FacePropertyRepositoryImpl facePropertyRepositoryImpl2 = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.biometrics.data.repository.FacePropertyRepositoryImpl$cameraInfo$1$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    facePropertyRepositoryImpl2.cameraManager.unregisterAvailabilityCallback(r1);
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (ProduceKt.awaitClose(producerScope, function0, this) == coroutineSingletons) {
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
