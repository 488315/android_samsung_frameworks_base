package com.android.systemui.statusbar.notification.interruption;

import android.os.Handler;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import com.android.systemui.util.settings.GlobalSettings;
import java.util.Collections;

public final class PeekDisabledSuppressor extends VisualInterruptionCondition {
    public final GlobalSettings globalSettings;
    public final HeadsUpManager headsUpManager;
    public final VisualInterruptionDecisionLogger logger;

    public PeekDisabledSuppressor(GlobalSettings globalSettings, HeadsUpManager headsUpManager, VisualInterruptionDecisionLogger visualInterruptionDecisionLogger, Handler handler) {
        super(Collections.singleton(VisualInterruptionType.PEEK), "peek disabled by global setting");
        this.globalSettings = globalSettings;
    }
}
