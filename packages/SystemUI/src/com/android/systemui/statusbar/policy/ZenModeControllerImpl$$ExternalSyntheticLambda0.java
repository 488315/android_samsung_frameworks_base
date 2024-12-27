package com.android.systemui.statusbar.policy;

import android.app.NotificationManager;
import android.service.notification.ZenModeConfig;
import com.android.systemui.statusbar.policy.ZenModeController;
import java.util.function.Consumer;

public final /* synthetic */ class ZenModeControllerImpl$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ int $r8$classId = 2;
    public final /* synthetic */ Object f$0;

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                ((ZenModeController.Callback) obj).onManualRuleChanged((ZenModeConfig.ZenRule) obj2);
                break;
            case 1:
                ((ZenModeController.Callback) obj).onConfigChanged((ZenModeConfig) obj2);
                break;
            default:
                ((ZenModeController.Callback) obj).onConsolidatedPolicyChanged((NotificationManager.Policy) obj2);
                break;
        }
    }
}
