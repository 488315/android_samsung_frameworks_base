package com.android.systemui.lowlightclock;

import android.content.ComponentName;
import android.content.pm.PackageManager;
import com.android.systemui.biometrics.domain.interactor.DisplayStateInteractor;
import com.android.systemui.biometrics.domain.interactor.DisplayStateInteractorImpl;
import com.android.systemui.shared.condition.Monitor;
import com.android.systemui.util.condition.ConditionalCoreStartable;
import com.android.systemui.util.kotlin.BooleanFlowOperators;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import dagger.Lazy;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
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
        BuildersKt.launch$default(this.scope, null, null, new LowLightMonitor$onStart$1(this, null), 3);
    }
}
