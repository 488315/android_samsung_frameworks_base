package com.android.systemui.doze;

import com.android.systemui.statusbar.phone.DozeServiceHost$$ExternalSyntheticLambda2;

public interface DozeHost {

    public interface PulseCallback {
        void onPulseFinished();

        void onPulseStarted();
    }

    public interface Callback {
        default void onPowerSaveChanged() {
        }

        default void onAlwaysOnSuppressedChanged(boolean z) {
        }

        default void onNotificationAlerted(DozeServiceHost$$ExternalSyntheticLambda2 dozeServiceHost$$ExternalSyntheticLambda2) {
        }
    }
}
