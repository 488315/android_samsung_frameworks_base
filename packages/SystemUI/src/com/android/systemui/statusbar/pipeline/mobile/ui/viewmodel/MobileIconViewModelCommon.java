package com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel;

import android.view.View;
import com.android.systemui.statusbar.phone.DoubleShadowStatusBarIconDrawable;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
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
