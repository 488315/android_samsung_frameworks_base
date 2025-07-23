package com.android.systemui.statusbar.policy;

import android.hardware.SensorPrivacyManager;
import android.util.SparseBooleanArray;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.policy.IndividualSensorPrivacyController;
import com.android.systemui.util.ListenerSet;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class IndividualSensorPrivacyControllerImpl implements IndividualSensorPrivacyController {
    public static final int[] SENSORS = {2, 1};
    public final SensorPrivacyManager mSensorPrivacyManager;
    public final UserTracker mUserTracker;
    public final SparseBooleanArray mSoftwareToggleState = new SparseBooleanArray();
    public final SparseBooleanArray mHardwareToggleState = new SparseBooleanArray();
    public final ListenerSet mCallbacks = new ListenerSet();

    public IndividualSensorPrivacyControllerImpl(SensorPrivacyManager sensorPrivacyManager, UserTracker userTracker) {
        this.mSensorPrivacyManager = sensorPrivacyManager;
        this.mUserTracker = userTracker;
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
        this.mSensorPrivacyManager.setSensorPrivacyForProfileGroup(i, i2, z, ((UserTrackerImpl) this.mUserTracker).getUserId());
    }
}
