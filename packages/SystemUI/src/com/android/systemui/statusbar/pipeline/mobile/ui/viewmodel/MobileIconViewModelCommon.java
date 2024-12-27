package com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel;

import android.view.View;
import com.android.systemui.statusbar.phone.DoubleShadowStatusBarIconDrawable;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.StateFlow;

public interface MobileIconViewModelCommon {
    Flow getActivityContainerVisible();

    Flow getActivityIcon();

    Flow getActivityInVisible();

    Flow getActivityOutVisible();

    Flow getAnyChanges();

    Flow getContentDescription();

    Flow getDexStatusBarIcon();

    Flow getIcon();

    StateFlow getNetworkTypeBackground();

    Flow getNetworkTypeIcon();

    Flow getRoaming();

    Flow getRoamingIcon();

    DoubleShadowStatusBarIconDrawable getShadowDrawable(View view, int i);

    int getSubscriptionId();

    StateFlow getUpdateDeXStatusBarIconModel();

    StateFlow getVoiceNoServiceIcon();

    StateFlow isVisible();
}
