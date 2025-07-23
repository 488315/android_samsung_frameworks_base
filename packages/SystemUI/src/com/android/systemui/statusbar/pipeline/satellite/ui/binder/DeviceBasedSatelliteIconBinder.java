package com.android.systemui.statusbar.pipeline.satellite.ui.binder;

import com.android.systemui.statusbar.pipeline.satellite.ui.viewmodel.DeviceBasedSatelliteViewModel;
import com.android.systemui.statusbar.pipeline.satellite.ui.viewmodel.DeviceBasedSatelliteViewModelImpl;
import com.android.systemui.statusbar.pipeline.shared.ui.view.SingleBindableStatusBarIconView;
import com.android.systemui.statusbar.pipeline.shared.ui.view.SingleBindableStatusBarIconView$Companion$withDefaultBinding$2;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class DeviceBasedSatelliteIconBinder {
    public static final DeviceBasedSatelliteIconBinder INSTANCE = new DeviceBasedSatelliteIconBinder();

    private DeviceBasedSatelliteIconBinder() {
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [com.android.systemui.statusbar.pipeline.satellite.ui.binder.DeviceBasedSatelliteIconBinder$$ExternalSyntheticLambda0] */
    public static SingleBindableStatusBarIconView$Companion$withDefaultBinding$2 bind(SingleBindableStatusBarIconView singleBindableStatusBarIconView, final DeviceBasedSatelliteViewModel deviceBasedSatelliteViewModel) {
        SingleBindableStatusBarIconView.Companion companion = SingleBindableStatusBarIconView.Companion;
        ?? r1 = new Function0() { // from class: com.android.systemui.statusbar.pipeline.satellite.ui.binder.DeviceBasedSatelliteIconBinder$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                DeviceBasedSatelliteIconBinder deviceBasedSatelliteIconBinder = DeviceBasedSatelliteIconBinder.INSTANCE;
                return Boolean.valueOf(((DeviceBasedSatelliteViewModelImpl) DeviceBasedSatelliteViewModel.this).icon.$$delegate_0.getValue() != null);
            }
        };
        DeviceBasedSatelliteIconBinder$bind$2 deviceBasedSatelliteIconBinder$bind$2 = new DeviceBasedSatelliteIconBinder$bind$2(deviceBasedSatelliteViewModel, singleBindableStatusBarIconView, null);
        companion.getClass();
        return SingleBindableStatusBarIconView.Companion.withDefaultBinding(singleBindableStatusBarIconView, r1, deviceBasedSatelliteIconBinder$bind$2);
    }
}
