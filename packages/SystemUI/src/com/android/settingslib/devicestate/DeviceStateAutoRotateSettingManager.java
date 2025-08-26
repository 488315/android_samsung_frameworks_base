package com.android.settingslib.devicestate;

import android.util.Dumpable;
import com.android.systemui.statusbar.policy.DeviceStateRotationLockSettingController$$ExternalSyntheticLambda1;

/* loaded from: classes.dex */
public interface DeviceStateAutoRotateSettingManager extends Dumpable {
    int getRotationLockSetting(int i);

    boolean isRotationLocked(int i);

    void registerListener(DeviceStateRotationLockSettingController$$ExternalSyntheticLambda1 deviceStateRotationLockSettingController$$ExternalSyntheticLambda1);

    void updateSetting(int i, boolean z);
}
