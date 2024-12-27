package com.android.systemui.statusbar.policy;

public interface IndividualSensorPrivacyController extends CallbackController {

    public interface Callback {
        void onSensorBlockedChanged(int i, boolean z);
    }
}
