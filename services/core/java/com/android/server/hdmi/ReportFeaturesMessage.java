package com.android.server.hdmi;

import android.hardware.hdmi.DeviceFeatures;

import com.android.internal.util.FrameworkStatsLog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
