package com.android.systemui.statusbar.pipeline.icons.shared;

import com.android.systemui.statusbar.pipeline.mobile.ui.StackedMobileBindableIcon;
import com.android.systemui.statusbar.pipeline.satellite.ui.DeviceBasedSatelliteBindableIcon;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes3.dex */
public final class BindableIconsRegistryImpl implements BindableIconsRegistry {
    public final List bindableIcons;

    public BindableIconsRegistryImpl(DeviceBasedSatelliteBindableIcon deviceBasedSatelliteBindableIcon, StackedMobileBindableIcon stackedMobileBindableIcon) {
        this.bindableIcons = Arrays.asList(deviceBasedSatelliteBindableIcon, stackedMobileBindableIcon);
    }
}
