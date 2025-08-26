package com.android.systemui.shade.domain.startable;

import android.content.Context;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.CoreStartable;
import com.android.systemui.Dumpable;
import com.android.systemui.biometrics.domain.interactor.DisplayStateInteractor;
import com.android.systemui.common.ui.data.repository.ConfigurationRepository;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.shade.DispatchTouchLogger;
import com.android.systemui.shade.ShadeExpansionStateManager;
import com.android.systemui.shade.TouchLogger;
import com.android.systemui.shade.data.repository.ShadeRepository;
import com.android.systemui.shade.transition.ScrimShadeTransitionController;
import com.android.systemui.shade.transition.ScrimShadeTransitionController$init$1;
import com.android.systemui.shade.transition.ScrimShadeTransitionController$init$currentState$1;
import com.android.systemui.statusbar.PulseExpansionHandler;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.phone.ScrimController;
import com.android.systemui.statusbar.policy.SplitShadeStateController;
import java.io.PrintWriter;
import javax.inject.Provider;
import kotlin.text.StringsKt__IndentKt;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes3.dex */
public final class ShadeStartable implements CoreStartable {
    public final CoroutineScope applicationScope;
    public final ConfigurationRepository configurationRepository;
    public final Context context;
    public final DisplayStateInteractor displayStateInteractor;
    public final NotificationStackScrollLayoutController nsslc;
    public final PulseExpansionHandler pulseExpansionHandler;
    public final ScrimController scrimController;
    public final ScrimShadeTransitionController scrimShadeTransitionController;
    public final ShadeExpansionStateManager shadeExpansionStateManager;
    public final ShadeRepository shadeRepository;
    public final SplitShadeStateController splitShadeStateController;
    public final LogBuffer touchLog;

    public ShadeStartable(CoroutineScope coroutineScope, Context context, LogBuffer logBuffer, ConfigurationRepository configurationRepository, ShadeRepository shadeRepository, SplitShadeStateController splitShadeStateController, ScrimShadeTransitionController scrimShadeTransitionController, Provider provider, Provider provider2, ShadeExpansionStateManager shadeExpansionStateManager, PulseExpansionHandler pulseExpansionHandler, DisplayStateInteractor displayStateInteractor, NotificationStackScrollLayoutController notificationStackScrollLayoutController, ScrimController scrimController) {
        this.applicationScope = coroutineScope;
        this.context = context;
        this.touchLog = logBuffer;
        this.configurationRepository = configurationRepository;
        this.shadeRepository = shadeRepository;
        this.splitShadeStateController = splitShadeStateController;
        this.scrimShadeTransitionController = scrimShadeTransitionController;
        this.shadeExpansionStateManager = shadeExpansionStateManager;
        this.pulseExpansionHandler = pulseExpansionHandler;
        this.displayStateInteractor = displayStateInteractor;
        this.nsslc = notificationStackScrollLayoutController;
        this.scrimController = scrimController;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        CoroutineTracingKt.launchTraced$default(this.applicationScope, null, null, new ShadeStartable$hydrateShadeLayoutWidth$1(this, null), 7);
        TouchLogger.Companion.getClass();
        TouchLogger.touchLogger = new DispatchTouchLogger(this.touchLog);
        final ScrimShadeTransitionController scrimShadeTransitionController = this.scrimShadeTransitionController;
        scrimShadeTransitionController.getClass();
        ScrimShadeTransitionController$init$currentState$1 scrimShadeTransitionController$init$currentState$1 = new ScrimShadeTransitionController$init$currentState$1(scrimShadeTransitionController);
        ShadeExpansionStateManager shadeExpansionStateManager = scrimShadeTransitionController.shadeExpansionStateManager;
        scrimShadeTransitionController.lastExpansionEvent = shadeExpansionStateManager.addExpansionListener(scrimShadeTransitionController$init$currentState$1);
        scrimShadeTransitionController.onStateChanged();
        shadeExpansionStateManager.stateListeners.add(new ScrimShadeTransitionController$init$1(scrimShadeTransitionController));
        scrimShadeTransitionController.dumpManager.registerNormalDumpable("ScrimShadeTransitionController", new Dumpable() { // from class: com.android.systemui.shade.transition.ScrimShadeTransitionController$init$2
            @Override // com.android.systemui.Dumpable
            public final void dump(PrintWriter printWriter, String[] strArr) {
                ScrimShadeTransitionController scrimShadeTransitionController2 = scrimShadeTransitionController;
                printWriter.println(StringsKt__IndentKt.trimIndent("\n                ScrimShadeTransitionController:\n                  State:\n                    currentPanelState: " + scrimShadeTransitionController2.currentPanelState + "\n                    lastExpansionFraction: " + scrimShadeTransitionController2.lastExpansionFraction + "\n                    lastExpansionEvent: " + scrimShadeTransitionController2.lastExpansionEvent + "\n            "));
            }
        });
        this.pulseExpansionHandler.stackScrollerController = this.nsslc;
    }
}
