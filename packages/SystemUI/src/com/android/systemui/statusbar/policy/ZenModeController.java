package com.android.systemui.statusbar.policy;

import android.app.NotificationManager;
import android.service.notification.ZenModeConfig;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface ZenModeController extends CallbackController {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
