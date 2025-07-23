package com.android.systemui.util.kotlin;

import android.hardware.Sensor;
import android.hardware.TriggerEvent;
import android.hardware.TriggerEventListener;
import com.android.systemui.util.sensors.AsyncSensorManager;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class AsyncSensorManagerExtKt$observeTriggerSensor$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Sensor $sensor;
    final /* synthetic */ AsyncSensorManager $this_observeTriggerSensor;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AsyncSensorManagerExtKt$observeTriggerSensor$1(AsyncSensorManager asyncSensorManager, Sensor sensor, Continuation continuation) {
        super(2, continuation);
        this.$this_observeTriggerSensor = asyncSensorManager;
        this.$sensor = sensor;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit invokeSuspend$lambda$0(AtomicBoolean atomicBoolean, AsyncSensorManager asyncSensorManager, AsyncSensorManagerExtKt$observeTriggerSensor$1$callback$1 asyncSensorManagerExtKt$observeTriggerSensor$1$callback$1, Sensor sensor) {
        if (atomicBoolean.getAndSet(false)) {
            asyncSensorManager.cancelTriggerSensor(asyncSensorManagerExtKt$observeTriggerSensor$1$callback$1, sensor);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void invokeSuspend$registerCallbackInternal(AtomicBoolean atomicBoolean, AsyncSensorManager asyncSensorManager, Sensor sensor, TriggerEventListener triggerEventListener) {
        if (atomicBoolean.compareAndSet(false, true)) {
            asyncSensorManager.requestTriggerSensor(triggerEventListener, sensor);
        }
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        AsyncSensorManagerExtKt$observeTriggerSensor$1 asyncSensorManagerExtKt$observeTriggerSensor$1 = new AsyncSensorManagerExtKt$observeTriggerSensor$1(this.$this_observeTriggerSensor, this.$sensor, continuation);
        asyncSensorManagerExtKt$observeTriggerSensor$1.L$0 = obj;
        return asyncSensorManagerExtKt$observeTriggerSensor$1;
    }

    /* JADX WARN: Type inference failed for: r3v1, types: [android.hardware.TriggerEventListener, com.android.systemui.util.kotlin.AsyncSensorManagerExtKt$observeTriggerSensor$1$callback$1] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
            final AsyncSensorManager asyncSensorManager = this.$this_observeTriggerSensor;
            final Sensor sensor = this.$sensor;
            final ?? r3 = new TriggerEventListener() { // from class: com.android.systemui.util.kotlin.AsyncSensorManagerExtKt$observeTriggerSensor$1$callback$1
                @Override // android.hardware.TriggerEventListener
                public void onTrigger(TriggerEvent triggerEvent) {
                    ((ChannelCoroutine) ProducerScope.this).mo3456trySendJP2dKIU(Unit.INSTANCE);
                    if (atomicBoolean.getAndSet(false)) {
                        AsyncSensorManagerExtKt$observeTriggerSensor$1.invokeSuspend$registerCallbackInternal(atomicBoolean, asyncSensorManager, sensor, this);
                    }
                }
            };
            invokeSuspend$registerCallbackInternal(atomicBoolean, this.$this_observeTriggerSensor, this.$sensor, r3);
            final AsyncSensorManager asyncSensorManager2 = this.$this_observeTriggerSensor;
            final Sensor sensor2 = this.$sensor;
            Function0 function0 = new Function0() { // from class: com.android.systemui.util.kotlin.AsyncSensorManagerExtKt$observeTriggerSensor$1$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit invokeSuspend$lambda$0;
                    invokeSuspend$lambda$0 = AsyncSensorManagerExtKt$observeTriggerSensor$1.invokeSuspend$lambda$0(atomicBoolean, asyncSensorManager2, r3, sensor2);
                    return invokeSuspend$lambda$0;
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

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(ProducerScope producerScope, Continuation continuation) {
        return ((AsyncSensorManagerExtKt$observeTriggerSensor$1) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }
}
