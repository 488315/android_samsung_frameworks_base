package com.android.systemui.statusbar.pipeline.satellite.data.prod;

import android.os.OutcomeReceiver;
import android.telephony.satellite.SatelliteManager;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0;
import com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.ExecutorsKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class DeviceBasedSatelliteRepositoryImpl$queryIsSatelliteProvisioned$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ SatelliteManager $sm;
    Object L$0;
    Object L$1;
    int label;
    final /* synthetic */ DeviceBasedSatelliteRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DeviceBasedSatelliteRepositoryImpl$queryIsSatelliteProvisioned$2(DeviceBasedSatelliteRepositoryImpl deviceBasedSatelliteRepositoryImpl, SatelliteManager satelliteManager, Continuation continuation) {
        super(2, continuation);
        this.this$0 = deviceBasedSatelliteRepositoryImpl;
        this.$sm = satelliteManager;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new DeviceBasedSatelliteRepositoryImpl$queryIsSatelliteProvisioned$2(this.this$0, this.$sm, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DeviceBasedSatelliteRepositoryImpl$queryIsSatelliteProvisioned$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final DeviceBasedSatelliteRepositoryImpl deviceBasedSatelliteRepositoryImpl = this.this$0;
            SatelliteManager satelliteManager = this.$sm;
            this.L$0 = deviceBasedSatelliteRepositoryImpl;
            this.L$1 = satelliteManager;
            this.label = 1;
            final CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt__IntrinsicsJvmKt.intercepted(this), 1);
            cancellableContinuationImpl.initCancellability();
            OutcomeReceiver outcomeReceiver = new OutcomeReceiver() { // from class: com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$queryIsSatelliteProvisioned$2$1$receiver$1
                @Override // android.os.OutcomeReceiver
                public final void onError(Throwable th) {
                    DeviceBasedSatelliteRepositoryImpl.Companion.access$e(DeviceBasedSatelliteRepositoryImpl.Companion, DeviceBasedSatelliteRepositoryImpl.this.logBuffer, "requestIsProvisioned.onError:", (SatelliteManager.SatelliteException) th);
                    CancellableContinuation cancellableContinuation = cancellableContinuationImpl;
                    int i2 = Result.$r8$clinit;
                    cancellableContinuation.resumeWith(Boolean.FALSE);
                }

                @Override // android.os.OutcomeReceiver
                public final void onResult(Object obj2) {
                    Boolean bool = (Boolean) obj2;
                    final boolean booleanValue = bool.booleanValue();
                    DeviceBasedSatelliteRepositoryImpl.Companion.i$default(DeviceBasedSatelliteRepositoryImpl.Companion, DeviceBasedSatelliteRepositoryImpl.this.logBuffer, new Function1() { // from class: com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$queryIsSatelliteProvisioned$2$1$receiver$1$onResult$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj3) {
                            return KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0.m("requestIsProvisioned.onResult: ", booleanValue);
                        }
                    });
                    CancellableContinuation cancellableContinuation = cancellableContinuationImpl;
                    int i2 = Result.$r8$clinit;
                    cancellableContinuation.resumeWith(bool);
                }
            };
            DeviceBasedSatelliteRepositoryImpl.Companion.i$default(DeviceBasedSatelliteRepositoryImpl.Companion, deviceBasedSatelliteRepositoryImpl.logBuffer, new Function1() { // from class: com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$queryIsSatelliteProvisioned$2$1$1
                @Override // kotlin.jvm.functions.Function1
                public final /* bridge */ /* synthetic */ Object invoke(Object obj2) {
                    return "Query for current satellite provisioned state.";
                }
            });
            try {
                satelliteManager.requestIsProvisioned(ExecutorsKt.asExecutor(deviceBasedSatelliteRepositoryImpl.bgDispatcher), outcomeReceiver);
            } catch (Exception e) {
                DeviceBasedSatelliteRepositoryImpl.Companion.access$e(DeviceBasedSatelliteRepositoryImpl.Companion, deviceBasedSatelliteRepositoryImpl.logBuffer, "Exception while calling SatelliteManager.requestIsProvisioned:", e);
                int i2 = Result.$r8$clinit;
                cancellableContinuationImpl.resumeWith(Boolean.FALSE);
            }
            obj = cancellableContinuationImpl.getResult();
            CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (obj == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return obj;
    }
}
