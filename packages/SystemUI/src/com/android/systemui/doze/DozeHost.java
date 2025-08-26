package com.android.systemui.doze;

import com.android.systemui.statusbar.phone.DozeServiceHost$$ExternalSyntheticLambda3;

/* loaded from: classes2.dex */
public interface DozeHost {

    public interface PulseCallback {
        void onPulseFinished();

        void onPulseStarted();
    }

    public interface Callback {
        default void onAlwaysOnSuppressedChanged(boolean z) {
        }

        default void onNotificationAlerted(DozeServiceHost$$ExternalSyntheticLambda3 dozeServiceHost$$ExternalSyntheticLambda3) {
        }

        default void onPowerSaveChanged() {
        }
    }
}
