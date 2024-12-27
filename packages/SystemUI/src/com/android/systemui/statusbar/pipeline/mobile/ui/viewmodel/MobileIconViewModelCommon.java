package com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel;

import android.view.View;
import com.android.systemui.statusbar.phone.DoubleShadowStatusBarIconDrawable;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
