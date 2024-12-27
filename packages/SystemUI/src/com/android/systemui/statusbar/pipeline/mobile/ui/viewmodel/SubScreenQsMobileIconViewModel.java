package com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel;

import com.android.systemui.statusbar.phone.StatusBarLocation;

public final class SubScreenQsMobileIconViewModel extends LocationBasedMobileViewModel {
    public SubScreenQsMobileIconViewModel(MobileIconViewModelCommon mobileIconViewModelCommon) {
        super(mobileIconViewModelCommon, StatusBarLocation.SUB_SCREEN_QUICK_PANEL, null);
    }
}
