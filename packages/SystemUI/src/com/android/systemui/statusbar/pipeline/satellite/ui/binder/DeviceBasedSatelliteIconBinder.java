package com.android.systemui.statusbar.pipeline.satellite.ui.binder;

import com.android.systemui.statusbar.pipeline.satellite.ui.viewmodel.DeviceBasedSatelliteViewModel;
import com.android.systemui.statusbar.pipeline.satellite.ui.viewmodel.DeviceBasedSatelliteViewModelImpl;
import com.android.systemui.statusbar.pipeline.shared.ui.view.SingleBindableStatusBarIconView;
import com.android.systemui.statusbar.pipeline.shared.ui.view.SingleBindableStatusBarIconView$Companion$withDefaultBinding$2;
import kotlin.jvm.functions.Function0;

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
                return Boolean.valueOf(((DeviceBasedSatelliteViewModelImpl) DeviceBasedSatelliteViewModel.this).icon.$$delegate_0.getValue() != null);
            }
        };
        DeviceBasedSatelliteIconBinder$bind$2 deviceBasedSatelliteIconBinder$bind$2 = new DeviceBasedSatelliteIconBinder$bind$2(deviceBasedSatelliteViewModel, singleBindableStatusBarIconView, null);
        companion.getClass();
        return SingleBindableStatusBarIconView.Companion.withDefaultBinding(singleBindableStatusBarIconView, function0, deviceBasedSatelliteIconBinder$bind$2);
    }
}
