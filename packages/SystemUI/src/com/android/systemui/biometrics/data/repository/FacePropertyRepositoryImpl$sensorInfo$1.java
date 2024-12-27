package com.android.systemui.biometrics.data.repository;

import android.hardware.face.FaceManager;
import android.hardware.face.FaceSensorPropertiesInternal;
import android.hardware.face.IFaceAuthenticatorsRegisteredCallback;
import com.android.systemui.biometrics.shared.model.SensorStrengthKt;
import com.android.systemui.common.coroutine.ChannelExt;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class FacePropertyRepositoryImpl$sensorInfo$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ FacePropertyRepositoryImpl this$0;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.biometrics.data.repository.FacePropertyRepositoryImpl$sensorInfo$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ FacePropertyRepositoryImpl$sensorInfo$1$callback$1 $callback;
        int label;
        final /* synthetic */ FacePropertyRepositoryImpl this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(FacePropertyRepositoryImpl facePropertyRepositoryImpl, FacePropertyRepositoryImpl$sensorInfo$1$callback$1 facePropertyRepositoryImpl$sensorInfo$1$callback$1, Continuation continuation) {
            super(2, continuation);
            this.this$0 = facePropertyRepositoryImpl;
            this.$callback = facePropertyRepositoryImpl$sensorInfo$1$callback$1;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.this$0, this.$callback, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            FaceManager faceManager = this.this$0.faceManager;
            if (faceManager == null) {
                return null;
            }
            faceManager.addAuthenticatorsRegisteredCallback(this.$callback);
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FacePropertyRepositoryImpl$sensorInfo$1(FacePropertyRepositoryImpl facePropertyRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = facePropertyRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        FacePropertyRepositoryImpl$sensorInfo$1 facePropertyRepositoryImpl$sensorInfo$1 = new FacePropertyRepositoryImpl$sensorInfo$1(this.this$0, continuation);
        facePropertyRepositoryImpl$sensorInfo$1.L$0 = obj;
        return facePropertyRepositoryImpl$sensorInfo$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((FacePropertyRepositoryImpl$sensorInfo$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Type inference failed for: r9v2, types: [com.android.systemui.biometrics.data.repository.FacePropertyRepositoryImpl$sensorInfo$1$callback$1] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        final ProducerScope producerScope;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            producerScope = (ProducerScope) this.L$0;
            ?? r9 = new IFaceAuthenticatorsRegisteredCallback.Stub() { // from class: com.android.systemui.biometrics.data.repository.FacePropertyRepositoryImpl$sensorInfo$1$callback$1
                public final void onAllAuthenticatorsRegistered(List list) {
                    if (list.isEmpty()) {
                        return;
                    }
                    ChannelExt channelExt = ChannelExt.INSTANCE;
                    ProducerScope producerScope2 = ProducerScope.this;
                    FaceSensorInfo faceSensorInfo = new FaceSensorInfo(((FaceSensorPropertiesInternal) CollectionsKt___CollectionsKt.first(list)).sensorId, SensorStrengthKt.toSensorStrength(((FaceSensorPropertiesInternal) CollectionsKt___CollectionsKt.first(list)).sensorStrength));
                    channelExt.getClass();
                    ChannelExt.trySendWithFailureLogging(producerScope2, faceSensorInfo, "FaceSensorPropertyRepositoryImpl", "onAllAuthenticatorsRegistered");
                }
            };
            FacePropertyRepositoryImpl facePropertyRepositoryImpl = this.this$0;
            CoroutineDispatcher coroutineDispatcher = facePropertyRepositoryImpl.backgroundDispatcher;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(facePropertyRepositoryImpl, r9, null);
            this.L$0 = producerScope;
            this.label = 1;
            if (BuildersKt.withContext(coroutineDispatcher, anonymousClass1, this) == coroutineSingletons) {
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
            producerScope = (ProducerScope) this.L$0;
            ResultKt.throwOnFailure(obj);
        }
        AnonymousClass2 anonymousClass2 = new Function0() { // from class: com.android.systemui.biometrics.data.repository.FacePropertyRepositoryImpl$sensorInfo$1.2
            @Override // kotlin.jvm.functions.Function0
            public final /* bridge */ /* synthetic */ Object invoke() {
                return Unit.INSTANCE;
            }
        };
        this.L$0 = null;
        this.label = 2;
        if (ProduceKt.awaitClose(producerScope, anonymousClass2, this) == coroutineSingletons) {
            return coroutineSingletons;
        }
        return Unit.INSTANCE;
    }
}
