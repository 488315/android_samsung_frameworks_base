package com.android.systemui.lowlightclock;

import android.content.ComponentName;
import android.content.pm.PackageManager;
import com.android.dream.lowlight.LowLightDreamManager;
import com.android.systemui.biometrics.domain.interactor.DisplayStateInteractor;
import com.android.systemui.biometrics.domain.interactor.DisplayStateInteractorImpl;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.shared.condition.Monitor;
import com.android.systemui.util.condition.ConditionalCoreStartable;
import com.android.systemui.util.kotlin.BooleanFlowOperators;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import dagger.Lazy;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

/* loaded from: classes2.dex */
public final class LowLightMonitor extends ConditionalCoreStartable {
    public final Monitor conditionsMonitor;
    public final Flow isLowLight;
    public final Flow isScreenOn;
    public final LowLightLogger logger;
    public final Lazy lowLightConditions;
    public final Lazy lowLightDreamManager;
    public final ComponentName lowLightDreamService;
    public final PackageManager packageManager;
    public final CoroutineScope scope;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.lowlightclock.LowLightMonitor$onStart$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return LowLightMonitor.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                LowLightMonitor lowLightMonitor = LowLightMonitor.this;
                ComponentName componentName = lowLightMonitor.lowLightDreamService;
                if (componentName == null) {
                    return Unit.INSTANCE;
                }
                lowLightMonitor.packageManager.setComponentEnabledSetting(componentName, 1, 1);
                LowLightMonitor lowLightMonitor2 = LowLightMonitor.this;
                Flow flowDistinctUntilChanged = FlowKt.distinctUntilChanged(FlowKt.transformLatest(lowLightMonitor2.isScreenOn, new LowLightMonitor$onStart$1$invokeSuspend$$inlined$flatMapLatest$1(null, lowLightMonitor2)));
                final LowLightMonitor lowLightMonitor3 = LowLightMonitor.this;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.lowlightclock.LowLightMonitor.onStart.1.2
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        boolean zBooleanValue = ((Boolean) obj2).booleanValue();
                        LowLightMonitor lowLightMonitor4 = lowLightMonitor3;
                        LowLightLogger lowLightLogger = lowLightMonitor4.logger;
                        lowLightLogger.getClass();
                        LogBuffer.log$default(lowLightLogger.buffer, "LowLightMonitor", LogLevel.DEBUG, "Low light enabled: " + zBooleanValue);
                        ((LowLightDreamManager) lowLightMonitor4.lowLightDreamManager.get()).setAmbientLightMode(zBooleanValue ? 2 : 1);
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (flowDistinctUntilChanged.collect(flowCollector, this) == coroutineSingletons) {
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

    static {
        new Companion(null);
    }

    public LowLightMonitor(Lazy lazy, Monitor monitor, Lazy lazy2, DisplayStateInteractor displayStateInteractor, LowLightLogger lowLightLogger, ComponentName componentName, PackageManager packageManager, CoroutineScope coroutineScope) {
        super(monitor);
        this.lowLightDreamManager = lazy;
        this.conditionsMonitor = monitor;
        this.lowLightConditions = lazy2;
        this.logger = lowLightLogger;
        this.lowLightDreamService = componentName;
        this.packageManager = packageManager;
        this.scope = coroutineScope;
        this.isScreenOn = FlowKt.distinctUntilChanged(BooleanFlowOperators.INSTANCE.not(((DisplayStateInteractorImpl) displayStateInteractor).isDefaultDisplayOff));
        this.isLowLight = FlowConflatedKt.conflatedCallbackFlow(new LowLightMonitor$isLowLight$1(this, null));
    }

    @Override // com.android.systemui.util.condition.ConditionalCoreStartable
    public final void onStart() {
        BuildersKt.launch$default(this.scope, null, null, new AnonymousClass1(null), 3);
    }
}
