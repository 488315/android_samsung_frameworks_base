package com.android.systemui.statusbar.pipeline.wifi.ui;

import android.view.ViewGroup;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.statusbar.phone.StatusBarLocation;
import com.android.systemui.statusbar.phone.ui.StatusBarIconController;
import com.android.systemui.statusbar.pipeline.StatusBarPipelineFlags;
import com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel.HomeWifiViewModel;
import com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel.KeyguardWifiViewModel;
import com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel.LocationBasedWifiViewModel;
import com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel.QsWifiViewModel;
import com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel.SubScreenQsWifiViewModel;
import com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel.WifiViewModel;
import kotlin.NoWhenBranchMatchedException;
import kotlin.coroutines.EmptyCoroutineContext;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
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
        switch (i) {
            case 1:
                homeWifiViewModel = new HomeWifiViewModel(wifiViewModel, statusBarPipelineFlags, statusBarLocation);
                break;
            case 2:
                homeWifiViewModel = new KeyguardWifiViewModel(wifiViewModel, statusBarPipelineFlags, statusBarLocation);
                break;
            case 3:
                homeWifiViewModel = new QsWifiViewModel(wifiViewModel, statusBarPipelineFlags, statusBarLocation);
                break;
            case 4:
                homeWifiViewModel = new SubScreenQsWifiViewModel(wifiViewModel, statusBarPipelineFlags, statusBarLocation);
                break;
            case 5:
                throw new IllegalArgumentException("invalid location for WifiViewModel: " + statusBarLocation);
            case 6:
                throw new IllegalArgumentException("invalid location for WifiViewModel: " + statusBarLocation);
            default:
                throw new NoWhenBranchMatchedException();
        }
        RepeatWhenAttachedKt.repeatWhenAttached(viewGroup, EmptyCoroutineContext.INSTANCE, new WifiUiAdapter$bindGroup$1(homeWifiViewModel, this, null));
        return homeWifiViewModel;
    }
}
