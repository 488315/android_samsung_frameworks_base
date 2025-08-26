package com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel;

import com.android.systemui.statusbar.phone.StatusBarLocation;
import com.android.systemui.statusbar.pipeline.mobile.ui.VerboseMobileViewLogger;

/* loaded from: classes3.dex */
public final class HomeMobileIconViewModel extends LocationBasedMobileViewModel {
    public HomeMobileIconViewModel(MobileIconViewModelCommon mobileIconViewModelCommon, VerboseMobileViewLogger verboseMobileViewLogger) {
        super(mobileIconViewModelCommon, StatusBarLocation.HOME, verboseMobileViewLogger);
    }
}
