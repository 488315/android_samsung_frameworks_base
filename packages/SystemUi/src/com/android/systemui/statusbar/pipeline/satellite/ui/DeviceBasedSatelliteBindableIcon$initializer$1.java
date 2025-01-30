package com.android.systemui.statusbar.pipeline.satellite.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.android.systemui.R;
import com.android.systemui.statusbar.pipeline.icons.shared.model.ModernStatusBarViewCreator;
import com.android.systemui.statusbar.pipeline.satellite.ui.binder.DeviceBasedSatelliteIconBinder;
import com.android.systemui.statusbar.pipeline.satellite.ui.viewmodel.DeviceBasedSatelliteViewModel;
import com.android.systemui.statusbar.pipeline.shared.ui.view.SingleBindableStatusBarIconView;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DeviceBasedSatelliteBindableIcon$initializer$1 implements ModernStatusBarViewCreator {
    public final /* synthetic */ DeviceBasedSatelliteViewModel $viewModel;
    public final /* synthetic */ DeviceBasedSatelliteBindableIcon this$0;

    public DeviceBasedSatelliteBindableIcon$initializer$1(DeviceBasedSatelliteBindableIcon deviceBasedSatelliteBindableIcon, DeviceBasedSatelliteViewModel deviceBasedSatelliteViewModel) {
        this.this$0 = deviceBasedSatelliteBindableIcon;
        this.$viewModel = deviceBasedSatelliteViewModel;
    }

    public final SingleBindableStatusBarIconView createAndBind(Context context) {
        SingleBindableStatusBarIconView.Companion.getClass();
        final SingleBindableStatusBarIconView singleBindableStatusBarIconView = (SingleBindableStatusBarIconView) LayoutInflater.from(context).inflate(R.layout.bindable_status_bar_icon, (ViewGroup) null);
        String str = this.this$0.slot;
        final DeviceBasedSatelliteViewModel deviceBasedSatelliteViewModel = this.$viewModel;
        singleBindableStatusBarIconView.initView(str, new Function0() { // from class: com.android.systemui.statusbar.pipeline.satellite.ui.DeviceBasedSatelliteBindableIcon$initializer$1$1$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                DeviceBasedSatelliteIconBinder deviceBasedSatelliteIconBinder = DeviceBasedSatelliteIconBinder.INSTANCE;
                SingleBindableStatusBarIconView singleBindableStatusBarIconView2 = SingleBindableStatusBarIconView.this;
                DeviceBasedSatelliteViewModel deviceBasedSatelliteViewModel2 = deviceBasedSatelliteViewModel;
                deviceBasedSatelliteIconBinder.getClass();
                return DeviceBasedSatelliteIconBinder.bind(singleBindableStatusBarIconView2, deviceBasedSatelliteViewModel2);
            }
        });
        return singleBindableStatusBarIconView;
    }
}
