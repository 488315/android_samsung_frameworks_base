package com.android.systemui.statusbar.policy;

import android.app.NotificationManager;
import android.service.notification.ZenModeConfig;

public interface ZenModeController extends CallbackController {

    public interface Callback {
        default void onEffectsSupressorChanged() {
        }

        default void onNextAlarmChanged() {
        }

        default void onConfigChanged(ZenModeConfig zenModeConfig) {
        }

        default void onConsolidatedPolicyChanged(NotificationManager.Policy policy) {
        }

        default void onManualRuleChanged(ZenModeConfig.ZenRule zenRule) {
        }

        default void onZenAvailableChanged(boolean z) {
        }

        default void onZenChanged(int i) {
        }
    }
}
