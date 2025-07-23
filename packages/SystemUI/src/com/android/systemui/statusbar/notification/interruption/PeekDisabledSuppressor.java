package com.android.systemui.statusbar.notification.interruption;

import android.os.Handler;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManager;
import com.android.systemui.util.settings.GlobalSettings;
import java.util.Collections;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class PeekDisabledSuppressor extends VisualInterruptionCondition {
    public final GlobalSettings globalSettings;
    public final HeadsUpManager headsUpManager;
    public final VisualInterruptionDecisionLogger logger;

    public PeekDisabledSuppressor(GlobalSettings globalSettings, HeadsUpManager headsUpManager, VisualInterruptionDecisionLogger visualInterruptionDecisionLogger, Handler handler) {
        super(Collections.singleton(VisualInterruptionType.PEEK), "peek disabled by global setting");
        this.globalSettings = globalSettings;
        this.headsUpManager = headsUpManager;
        this.logger = visualInterruptionDecisionLogger;
    }
}
