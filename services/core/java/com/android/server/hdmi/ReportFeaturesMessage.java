package com.android.server.hdmi;

import android.hardware.hdmi.DeviceFeatures;

import com.android.internal.util.FrameworkStatsLog;

public final class ReportFeaturesMessage extends HdmiCecMessage {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final int mCecVersion;
    public final DeviceFeatures mDeviceFeatures;

    public ReportFeaturesMessage(
            int i, int i2, byte[] bArr, int i3, DeviceFeatures deviceFeatures) {
        super(
                i,
                i2,
                FrameworkStatsLog
                        .DEVICE_POLICY_EVENT__EVENT_ID__CROSS_PROFILE_SETTINGS_PAGE_MISSING_PERSONAL_APP,
                0,
                bArr);
        this.mCecVersion = i3;
        this.mDeviceFeatures = deviceFeatures;
    }
}
