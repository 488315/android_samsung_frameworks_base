package com.android.systemui.doze;

import com.android.systemui.statusbar.phone.DozeServiceHost$$ExternalSyntheticLambda3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface DozeHost {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface PulseCallback {
        void onPulseFinished();

        void onPulseStarted();
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Callback {
        default void onAlwaysOnSuppressedChanged(boolean z) {
        }

        default void onNotificationAlerted(DozeServiceHost$$ExternalSyntheticLambda3 dozeServiceHost$$ExternalSyntheticLambda3) {
        }

        default void onPowerSaveChanged() {
        }
    }
}
