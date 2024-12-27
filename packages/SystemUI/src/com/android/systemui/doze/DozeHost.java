package com.android.systemui.doze;

import com.android.systemui.statusbar.phone.DozeServiceHost$$ExternalSyntheticLambda2;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface DozeHost {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface PulseCallback {
        void onPulseFinished();

        void onPulseStarted();
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface Callback {
        default void onPowerSaveChanged() {
        }

        default void onAlwaysOnSuppressedChanged(boolean z) {
        }

        default void onNotificationAlerted(DozeServiceHost$$ExternalSyntheticLambda2 dozeServiceHost$$ExternalSyntheticLambda2) {
        }
    }
}
