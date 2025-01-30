package com.android.systemui.statusbar.pipeline.satellite.ui;

import android.content.Context;
import com.android.systemui.statusbar.pipeline.satellite.ui.viewmodel.DeviceBasedSatelliteViewModel;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DeviceBasedSatelliteBindableIcon {
    public final DeviceBasedSatelliteBindableIcon$initializer$1 initializer;
    public final String slot;

    public DeviceBasedSatelliteBindableIcon(Context context, DeviceBasedSatelliteViewModel deviceBasedSatelliteViewModel) {
        this.slot = context.getString(17042936);
        this.initializer = new DeviceBasedSatelliteBindableIcon$initializer$1(this, deviceBasedSatelliteViewModel);
    }
}
