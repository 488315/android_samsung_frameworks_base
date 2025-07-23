package com.android.settingslib.devicestate;

import android.util.Dumpable;
import com.android.systemui.statusbar.policy.DeviceStateRotationLockSettingController$$ExternalSyntheticLambda1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface DeviceStateAutoRotateSettingManager extends Dumpable {
    int getRotationLockSetting(int i);

    boolean isRotationLocked(int i);

    void registerListener(DeviceStateRotationLockSettingController$$ExternalSyntheticLambda1 deviceStateRotationLockSettingController$$ExternalSyntheticLambda1);

    void updateSetting(int i, boolean z);
}
