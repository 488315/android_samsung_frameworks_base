package com.android.systemui.statusbar.pipeline.satellite.ui;

import android.content.Context;
import com.android.internal.telephony.flags.Flags;
import com.android.systemui.statusbar.pipeline.satellite.ui.viewmodel.DeviceBasedSatelliteViewModel;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class DeviceBasedSatelliteBindableIcon {
    public final DeviceBasedSatelliteBindableIcon$initializer$1 initializer;
    public final boolean shouldBindIcon = Flags.oemEnabledSatelliteFlag();
    public final String slot;

    public DeviceBasedSatelliteBindableIcon(Context context, DeviceBasedSatelliteViewModel deviceBasedSatelliteViewModel) {
        this.slot = context.getString(17043151);
        this.initializer = new DeviceBasedSatelliteBindableIcon$initializer$1(this, deviceBasedSatelliteViewModel);
    }
}
