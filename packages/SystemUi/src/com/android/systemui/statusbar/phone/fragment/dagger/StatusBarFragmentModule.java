package com.android.systemui.statusbar.phone.fragment.dagger;

import com.android.systemui.BasicRune;
import com.android.systemui.R;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.phone.IndicatorCutoutUtil;
import com.android.systemui.statusbar.phone.IndicatorScaleGardener;
import com.android.systemui.statusbar.phone.PhoneStatusBarView;
import com.android.systemui.statusbar.policy.NetspeedView;
import com.android.systemui.statusbar.policy.NetspeedViewController;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface StatusBarFragmentModule {
    static NetspeedViewController provideNetspeedViewController(PhoneStatusBarView phoneStatusBarView, IndicatorScaleGardener indicatorScaleGardener, IndicatorCutoutUtil indicatorCutoutUtil, UserTracker userTracker) {
        if (BasicRune.STATUS_REAL_TIME_NETWORK_SPEED) {
            return new NetspeedViewController((NetspeedView) phoneStatusBarView.findViewById(R.id.networkSpeed), indicatorScaleGardener, indicatorCutoutUtil, userTracker);
        }
        return null;
    }
}
