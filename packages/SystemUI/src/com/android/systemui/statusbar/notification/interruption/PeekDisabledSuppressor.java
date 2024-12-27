package com.android.systemui.statusbar.notification.interruption;

import android.os.Handler;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import com.android.systemui.util.settings.GlobalSettings;
import java.util.Collections;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class PeekDisabledSuppressor extends VisualInterruptionCondition {
    public final GlobalSettings globalSettings;
    public final HeadsUpManager headsUpManager;
    public final VisualInterruptionDecisionLogger logger;

    public PeekDisabledSuppressor(GlobalSettings globalSettings, HeadsUpManager headsUpManager, VisualInterruptionDecisionLogger visualInterruptionDecisionLogger, Handler handler) {
        super(Collections.singleton(VisualInterruptionType.PEEK), "peek disabled by global setting");
        this.globalSettings = globalSettings;
    }
}
