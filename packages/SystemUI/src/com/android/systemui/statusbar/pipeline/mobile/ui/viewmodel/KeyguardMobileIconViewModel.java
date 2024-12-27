package com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel;

import com.android.systemui.statusbar.phone.StatusBarLocation;

public final class KeyguardMobileIconViewModel extends LocationBasedMobileViewModel {
    public KeyguardMobileIconViewModel(MobileIconViewModelCommon mobileIconViewModelCommon) {
        super(mobileIconViewModelCommon, StatusBarLocation.KEYGUARD, null);
    }
}
