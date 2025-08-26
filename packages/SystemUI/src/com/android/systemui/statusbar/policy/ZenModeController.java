package com.android.systemui.statusbar.policy;

import android.app.NotificationManager;
import android.service.notification.ZenModeConfig;

/* loaded from: classes3.dex */
public interface ZenModeController extends CallbackController {

    public interface Callback {
        default void onConfigChanged(ZenModeConfig zenModeConfig) {
        }

        default void onConsolidatedPolicyChanged(NotificationManager.Policy policy) {
        }

        default void onZenAvailableChanged(boolean z) {
        }

        default void onZenChanged(int i) {
        }

        default void onNextAlarmChanged() {
        }
    }
}
