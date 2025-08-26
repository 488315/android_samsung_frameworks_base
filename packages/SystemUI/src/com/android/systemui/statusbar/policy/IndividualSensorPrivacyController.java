package com.android.systemui.statusbar.policy;

/* loaded from: classes3.dex */
public interface IndividualSensorPrivacyController extends CallbackController {

    public interface Callback {
        void onSensorBlockedChanged(int i, boolean z);
    }
}
