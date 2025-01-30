package com.android.systemui.statusbar.pipeline.wifi.ui;

import android.view.ViewGroup;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.statusbar.phone.StatusBarIconController;
import com.android.systemui.statusbar.phone.StatusBarLocation;
import com.android.systemui.statusbar.pipeline.StatusBarPipelineFlags;
import com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel.HomeWifiViewModel;
import com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel.KeyguardWifiViewModel;
import com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel.LocationBasedWifiViewModel;
import com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel.QsWifiViewModel;
import com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel.SubScreenQsWifiViewModel;
import com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel.WifiViewModel;
import kotlin.NoWhenBranchMatchedException;
import kotlin.coroutines.EmptyCoroutineContext;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WifiUiAdapter {
    public final StatusBarIconController iconController;
    public final StatusBarPipelineFlags statusBarPipelineFlags;
    public final WifiViewModel wifiViewModel;

    public WifiUiAdapter(StatusBarIconController statusBarIconController, WifiViewModel wifiViewModel, StatusBarPipelineFlags statusBarPipelineFlags) {
        this.iconController = statusBarIconController;
        this.wifiViewModel = wifiViewModel;
        this.statusBarPipelineFlags = statusBarPipelineFlags;
    }

    public final LocationBasedWifiViewModel bindGroup(ViewGroup viewGroup, StatusBarLocation statusBarLocation) {
        LocationBasedWifiViewModel homeWifiViewModel;
        LocationBasedWifiViewModel.Companion.getClass();
        int i = LocationBasedWifiViewModel.Companion.WhenMappings.$EnumSwitchMapping$0[statusBarLocation.ordinal()];
        WifiViewModel wifiViewModel = this.wifiViewModel;
        StatusBarPipelineFlags statusBarPipelineFlags = this.statusBarPipelineFlags;
        if (i == 1) {
            homeWifiViewModel = new HomeWifiViewModel(wifiViewModel, statusBarPipelineFlags, statusBarLocation);
        } else if (i == 2) {
            homeWifiViewModel = new KeyguardWifiViewModel(wifiViewModel, statusBarPipelineFlags, statusBarLocation);
        } else if (i == 3) {
            homeWifiViewModel = new QsWifiViewModel(wifiViewModel, statusBarPipelineFlags, statusBarLocation);
        } else {
            if (i != 4) {
                throw new NoWhenBranchMatchedException();
            }
            homeWifiViewModel = new SubScreenQsWifiViewModel(wifiViewModel, statusBarPipelineFlags, statusBarLocation);
        }
        RepeatWhenAttachedKt.repeatWhenAttached(viewGroup, EmptyCoroutineContext.INSTANCE, new WifiUiAdapter$bindGroup$1(homeWifiViewModel, this, null));
        return homeWifiViewModel;
    }
}
