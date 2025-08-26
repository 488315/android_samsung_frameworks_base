package com.android.systemui.statusbar.pipeline.satellite.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.android.systemui.R;
import com.android.systemui.statusbar.pipeline.icons.shared.model.BindableIcon;
import com.android.systemui.statusbar.pipeline.icons.shared.model.ModernStatusBarViewCreator;
import com.android.systemui.statusbar.pipeline.satellite.ui.binder.DeviceBasedSatelliteIconBinder;
import com.android.systemui.statusbar.pipeline.satellite.ui.viewmodel.DeviceBasedSatelliteViewModel;
import com.android.systemui.statusbar.pipeline.shared.ui.view.ModernStatusBarView;
import com.android.systemui.statusbar.pipeline.shared.ui.view.SingleBindableStatusBarIconView;
import kotlin.jvm.functions.Function0;

/* loaded from: classes3.dex */
public final class DeviceBasedSatelliteBindableIcon implements BindableIcon {
    public final DeviceBasedSatelliteBindableIcon$initializer$1 initializer;
    public final boolean shouldBindIcon = true;
    public final String slot;

    /* JADX WARN: Type inference failed for: r2v2, types: [com.android.systemui.statusbar.pipeline.satellite.ui.DeviceBasedSatelliteBindableIcon$initializer$1] */
    public DeviceBasedSatelliteBindableIcon(Context context, final DeviceBasedSatelliteViewModel deviceBasedSatelliteViewModel) {
        this.slot = context.getString(17043293);
        this.initializer = new ModernStatusBarViewCreator() { // from class: com.android.systemui.statusbar.pipeline.satellite.ui.DeviceBasedSatelliteBindableIcon$initializer$1
            @Override // com.android.systemui.statusbar.pipeline.icons.shared.model.ModernStatusBarViewCreator
            public final ModernStatusBarView createAndBind(Context context2) {
                SingleBindableStatusBarIconView.Companion.getClass();
                final SingleBindableStatusBarIconView singleBindableStatusBarIconView = (SingleBindableStatusBarIconView) LayoutInflater.from(context2).inflate(R.layout.bindable_status_bar_icon, (ViewGroup) null);
                String str = this.this$0.slot;
                final DeviceBasedSatelliteViewModel deviceBasedSatelliteViewModel2 = deviceBasedSatelliteViewModel;
                singleBindableStatusBarIconView.initView(str, new Function0() { // from class: com.android.systemui.statusbar.pipeline.satellite.ui.DeviceBasedSatelliteBindableIcon$initializer$1$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        DeviceBasedSatelliteIconBinder.INSTANCE.getClass();
                        return DeviceBasedSatelliteIconBinder.bind(singleBindableStatusBarIconView, deviceBasedSatelliteViewModel2);
                    }
                });
                return singleBindableStatusBarIconView;
            }
        };
    }

    @Override // com.android.systemui.statusbar.pipeline.icons.shared.model.BindableIcon
    public final ModernStatusBarViewCreator getInitializer() {
        return this.initializer;
    }

    @Override // com.android.systemui.statusbar.pipeline.icons.shared.model.BindableIcon
    public final boolean getShouldBindIcon() {
        return this.shouldBindIcon;
    }

    @Override // com.android.systemui.statusbar.pipeline.icons.shared.model.BindableIcon
    public final String getSlot() {
        return this.slot;
    }
}
