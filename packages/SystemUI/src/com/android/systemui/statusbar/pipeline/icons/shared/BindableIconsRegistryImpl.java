package com.android.systemui.statusbar.pipeline.icons.shared;

import com.android.systemui.statusbar.pipeline.mobile.ui.StackedMobileBindableIcon;
import com.android.systemui.statusbar.pipeline.satellite.ui.DeviceBasedSatelliteBindableIcon;
import java.util.Arrays;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class BindableIconsRegistryImpl implements BindableIconsRegistry {
    public final List bindableIcons;

    public BindableIconsRegistryImpl(DeviceBasedSatelliteBindableIcon deviceBasedSatelliteBindableIcon, StackedMobileBindableIcon stackedMobileBindableIcon) {
        this.bindableIcons = Arrays.asList(deviceBasedSatelliteBindableIcon, stackedMobileBindableIcon);
    }
}
