package com.android.systemui.statusbar.pipeline.satellite.p027ui.binder;

import com.android.systemui.statusbar.pipeline.satellite.p027ui.viewmodel.DeviceBasedSatelliteViewModel;
import com.android.systemui.statusbar.pipeline.shared.p028ui.view.SingleBindableStatusBarIconView;
import com.android.systemui.statusbar.pipeline.shared.p028ui.view.SingleBindableStatusBarIconView$Companion$withDefaultBinding$2;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DeviceBasedSatelliteIconBinder {
    public static final DeviceBasedSatelliteIconBinder INSTANCE = new DeviceBasedSatelliteIconBinder();

    private DeviceBasedSatelliteIconBinder() {
    }

    public static SingleBindableStatusBarIconView$Companion$withDefaultBinding$2 bind(SingleBindableStatusBarIconView singleBindableStatusBarIconView, final DeviceBasedSatelliteViewModel deviceBasedSatelliteViewModel) {
        SingleBindableStatusBarIconView.Companion companion = SingleBindableStatusBarIconView.Companion;
        Function0 function0 = new Function0() { // from class: com.android.systemui.statusbar.pipeline.satellite.ui.binder.DeviceBasedSatelliteIconBinder$bind$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return Boolean.valueOf(DeviceBasedSatelliteViewModel.this.icon.getValue() != null);
            }
        };
        DeviceBasedSatelliteIconBinder$bind$2 deviceBasedSatelliteIconBinder$bind$2 = new DeviceBasedSatelliteIconBinder$bind$2(deviceBasedSatelliteViewModel, singleBindableStatusBarIconView, null);
        companion.getClass();
        return SingleBindableStatusBarIconView.Companion.withDefaultBinding(singleBindableStatusBarIconView, function0, deviceBasedSatelliteIconBinder$bind$2);
    }
}
