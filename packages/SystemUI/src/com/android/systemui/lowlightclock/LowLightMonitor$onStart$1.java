package com.android.systemui.lowlightclock;

import android.content.ComponentName;
import com.android.dream.lowlight.LowLightDreamManager;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogLevel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class LowLightMonitor$onStart$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ LowLightMonitor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LowLightMonitor$onStart$1(LowLightMonitor lowLightMonitor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = lowLightMonitor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new LowLightMonitor$onStart$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((LowLightMonitor$onStart$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            LowLightMonitor lowLightMonitor = this.this$0;
            ComponentName componentName = lowLightMonitor.lowLightDreamService;
            if (componentName == null) {
                return Unit.INSTANCE;
            }
            lowLightMonitor.packageManager.setComponentEnabledSetting(componentName, 1, 1);
            LowLightMonitor lowLightMonitor2 = this.this$0;
            Flow distinctUntilChanged = FlowKt.distinctUntilChanged(FlowKt.transformLatest(lowLightMonitor2.isScreenOn, new LowLightMonitor$onStart$1$invokeSuspend$$inlined$flatMapLatest$1(null, lowLightMonitor2)));
            final LowLightMonitor lowLightMonitor3 = this.this$0;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.lowlightclock.LowLightMonitor$onStart$1.2
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    boolean booleanValue = ((Boolean) obj2).booleanValue();
                    LowLightMonitor lowLightMonitor4 = LowLightMonitor.this;
                    LowLightLogger lowLightLogger = lowLightMonitor4.logger;
                    lowLightLogger.getClass();
                    LogBuffer.log$default(lowLightLogger.buffer, "LowLightMonitor", LogLevel.DEBUG, "Low light enabled: " + booleanValue);
                    ((LowLightDreamManager) lowLightMonitor4.lowLightDreamManager.get()).setAmbientLightMode(booleanValue ? 2 : 1);
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (distinctUntilChanged.collect(flowCollector, this) == coroutineSingletons) {
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
