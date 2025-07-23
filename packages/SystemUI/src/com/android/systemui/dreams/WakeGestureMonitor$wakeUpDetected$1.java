package com.android.systemui.dreams;

import android.hardware.Sensor;
import com.android.systemui.util.kotlin.AsyncSensorManagerExtKt;
import com.android.systemui.util.sensors.AsyncSensorManager;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.EmptyFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class WakeGestureMonitor$wakeUpDetected$1 extends SuspendLambda implements Function2 {
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ WakeGestureMonitor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WakeGestureMonitor$wakeUpDetected$1(WakeGestureMonitor wakeGestureMonitor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = wakeGestureMonitor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        WakeGestureMonitor$wakeUpDetected$1 wakeGestureMonitor$wakeUpDetected$1 = new WakeGestureMonitor$wakeUpDetected$1(this.this$0, continuation);
        wakeGestureMonitor$wakeUpDetected$1.Z$0 = ((Boolean) obj).booleanValue();
        return wakeGestureMonitor$wakeUpDetected$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        Boolean bool = (Boolean) obj;
        bool.booleanValue();
        return ((WakeGestureMonitor$wakeUpDetected$1) create(bool, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        if (!this.Z$0 || ((Sensor) this.this$0.pickupSensor$delegate.getValue()) == null) {
            return EmptyFlow.INSTANCE;
        }
        WakeGestureMonitor wakeGestureMonitor = this.this$0;
        AsyncSensorManager asyncSensorManager = wakeGestureMonitor.asyncSensorManager;
        Sensor sensor = (Sensor) wakeGestureMonitor.pickupSensor$delegate.getValue();
        sensor.getClass();
        return AsyncSensorManagerExtKt.observeTriggerSensor(asyncSensorManager, sensor);
    }
}
