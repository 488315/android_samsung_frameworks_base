package com.android.systemui.statusbar.pipeline.icons.shared;

import com.android.systemui.statusbar.pipeline.satellite.ui.DeviceBasedSatelliteBindableIcon;
import java.util.Collections;
import java.util.List;

public final class BindableIconsRegistryImpl implements BindableIconsRegistry {
    public final List bindableIcons;

    public BindableIconsRegistryImpl(DeviceBasedSatelliteBindableIcon deviceBasedSatelliteBindableIcon) {
        this.bindableIcons = Collections.singletonList(deviceBasedSatelliteBindableIcon);
    }
}
