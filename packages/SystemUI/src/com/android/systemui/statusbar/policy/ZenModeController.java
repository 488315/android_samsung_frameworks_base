package com.android.systemui.statusbar.policy;

import android.app.NotificationManager;
import android.service.notification.ZenModeConfig;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface ZenModeController extends CallbackController {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
