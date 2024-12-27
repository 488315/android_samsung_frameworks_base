package com.android.systemui.statusbar.pipeline.icons.shared;

import com.android.systemui.statusbar.pipeline.satellite.ui.DeviceBasedSatelliteBindableIcon;
import java.util.Collections;
import java.util.List;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class BindableIconsRegistryImpl implements BindableIconsRegistry {
    public final List bindableIcons;

    public BindableIconsRegistryImpl(DeviceBasedSatelliteBindableIcon deviceBasedSatelliteBindableIcon) {
        this.bindableIcons = Collections.singletonList(deviceBasedSatelliteBindableIcon);
    }
}
