package com.android.systemui.statusbar.pipeline.satellite.ui;

import android.content.Context;
import com.android.internal.telephony.flags.Flags;
import com.android.systemui.statusbar.pipeline.satellite.ui.viewmodel.DeviceBasedSatelliteViewModel;

public final class DeviceBasedSatelliteBindableIcon {
    public final DeviceBasedSatelliteBindableIcon$initializer$1 initializer;
    public final boolean shouldBindIcon = Flags.oemEnabledSatelliteFlag();
    public final String slot;

    public DeviceBasedSatelliteBindableIcon(Context context, DeviceBasedSatelliteViewModel deviceBasedSatelliteViewModel) {
        this.slot = context.getString(17043151);
        this.initializer = new DeviceBasedSatelliteBindableIcon$initializer$1(this, deviceBasedSatelliteViewModel);
    }
}
