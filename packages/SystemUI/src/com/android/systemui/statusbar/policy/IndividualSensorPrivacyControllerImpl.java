package com.android.systemui.statusbar.policy;

import android.hardware.SensorPrivacyManager;
import android.util.SparseBooleanArray;
import com.android.systemui.statusbar.policy.IndividualSensorPrivacyController;
import com.android.systemui.util.ListenerSet;

public final class IndividualSensorPrivacyControllerImpl implements IndividualSensorPrivacyController {
    public static final int[] SENSORS = {2, 1};
    public final SensorPrivacyManager mSensorPrivacyManager;
    public final SparseBooleanArray mSoftwareToggleState = new SparseBooleanArray();
    public final SparseBooleanArray mHardwareToggleState = new SparseBooleanArray();
    public final ListenerSet mCallbacks = new ListenerSet();

    public IndividualSensorPrivacyControllerImpl(SensorPrivacyManager sensorPrivacyManager) {
        this.mSensorPrivacyManager = sensorPrivacyManager;
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void addCallback(Object obj) {
        this.mCallbacks.addIfAbsent((IndividualSensorPrivacyController.Callback) obj);
    }

    public final boolean isSensorBlocked(int i) {
        return this.mSoftwareToggleState.get(i, false) || this.mHardwareToggleState.get(i, false);
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void removeCallback(Object obj) {
        this.mCallbacks.remove((IndividualSensorPrivacyController.Callback) obj);
    }

    public final void setSensorBlocked(int i, int i2, boolean z) {
        this.mSensorPrivacyManager.setSensorPrivacyForProfileGroup(i, i2, z);
    }
}
