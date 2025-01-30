package com.android.systemui.doze;

import com.android.systemui.statusbar.phone.DozeServiceHost$$ExternalSyntheticLambda1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public interface DozeHost {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface PulseCallback {
        void onPulseFinished();

        void onPulseStarted();
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Callback {
        default void onAlwaysOnSuppressedChanged(boolean z) {
        }

        default void onNotificationAlerted(DozeServiceHost$$ExternalSyntheticLambda1 dozeServiceHost$$ExternalSyntheticLambda1) {
        }

        default void onPowerSaveChanged() {
        }
    }
}
