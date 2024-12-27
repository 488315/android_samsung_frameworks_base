package com.android.systemui.shade.domain.startable;

import android.content.Context;
import com.android.systemui.CoreStartable;
import com.android.systemui.Dumpable;
import com.android.systemui.Flags;
import com.android.systemui.common.ui.data.repository.ConfigurationRepository;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.shade.DispatchTouchLogger;
import com.android.systemui.shade.ShadeExpansionStateManager;
import com.android.systemui.shade.TouchLogger;
import com.android.systemui.shade.data.repository.ShadeRepository;
import com.android.systemui.shade.transition.ScrimShadeTransitionController;
import com.android.systemui.shade.transition.ScrimShadeTransitionController$init$1;
import com.android.systemui.shade.transition.ScrimShadeTransitionController$init$currentState$1;
import com.android.systemui.statusbar.policy.SplitShadeStateController;
import java.io.PrintWriter;
import javax.inject.Provider;
import kotlin.text.StringsKt__IndentKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class ShadeStartable implements CoreStartable {
    public final Context applicationContext;
    public final CoroutineScope applicationScope;
    public final ConfigurationRepository configurationRepository;
    public final ScrimShadeTransitionController scrimShadeTransitionController;
    public final ShadeExpansionStateManager shadeExpansionStateManager;
    public final ShadeRepository shadeRepository;
    public final SplitShadeStateController splitShadeStateController;
    public final LogBuffer touchLog;

    public ShadeStartable(CoroutineScope coroutineScope, Context context, LogBuffer logBuffer, ConfigurationRepository configurationRepository, ShadeRepository shadeRepository, SplitShadeStateController splitShadeStateController, ScrimShadeTransitionController scrimShadeTransitionController, Provider provider, Provider provider2, ShadeExpansionStateManager shadeExpansionStateManager) {
        this.applicationScope = coroutineScope;
        this.applicationContext = context;
        this.touchLog = logBuffer;
        this.configurationRepository = configurationRepository;
        this.shadeRepository = shadeRepository;
        this.splitShadeStateController = splitShadeStateController;
        this.scrimShadeTransitionController = scrimShadeTransitionController;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        Flags.dualShade();
        BuildersKt.launch$default(this.applicationScope, null, null, new ShadeStartable$hydrateShadeMode$1(this, null), 3);
        Flags.sceneContainer();
        TouchLogger.Companion.getClass();
        TouchLogger.touchLogger = new DispatchTouchLogger(this.touchLog);
        final ScrimShadeTransitionController scrimShadeTransitionController = this.scrimShadeTransitionController;
        scrimShadeTransitionController.getClass();
        ScrimShadeTransitionController$init$currentState$1 scrimShadeTransitionController$init$currentState$1 = new ScrimShadeTransitionController$init$currentState$1(scrimShadeTransitionController);
        ShadeExpansionStateManager shadeExpansionStateManager = scrimShadeTransitionController.shadeExpansionStateManager;
        scrimShadeTransitionController.lastExpansionEvent = shadeExpansionStateManager.addExpansionListener(scrimShadeTransitionController$init$currentState$1);
        scrimShadeTransitionController.onStateChanged();
        shadeExpansionStateManager.stateListeners.add(new ScrimShadeTransitionController$init$1(scrimShadeTransitionController));
        DumpManager.registerDumpable$default(scrimShadeTransitionController.dumpManager, "ScrimShadeTransitionController", new Dumpable() { // from class: com.android.systemui.shade.transition.ScrimShadeTransitionController$init$2
            @Override // com.android.systemui.Dumpable
            public final void dump(PrintWriter printWriter, String[] strArr) {
                ScrimShadeTransitionController scrimShadeTransitionController2 = ScrimShadeTransitionController.this;
                printWriter.println(StringsKt__IndentKt.trimIndent("\n                ScrimShadeTransitionController:\n                  State:\n                    currentPanelState: " + scrimShadeTransitionController2.currentPanelState + "\n                    lastExpansionFraction: " + scrimShadeTransitionController2.lastExpansionFraction + "\n                    lastExpansionEvent: " + scrimShadeTransitionController2.lastExpansionEvent + "\n            "));
            }
        });
    }
}
