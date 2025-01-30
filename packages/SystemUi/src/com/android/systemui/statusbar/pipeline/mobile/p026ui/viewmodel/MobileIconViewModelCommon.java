package com.android.systemui.statusbar.pipeline.mobile.p026ui.viewmodel;

import android.view.View;
import com.android.systemui.statusbar.phone.DoubleShadowStatusBarIconDrawable;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface MobileIconViewModelCommon {
    Flow getActivityContainerVisible();

    Flow getActivityIcon();

    Flow getActivityInVisible();

    Flow getActivityOutVisible();

    Flow getAnyChanges();

    Flow getContentDescription();

    Flow getDexStatusBarIcon();

    Flow getIcon();

    Flow getNetworkTypeIcon();

    Flow getRoaming();

    Flow getRoamingIcon();

    DoubleShadowStatusBarIconDrawable getShadowDrawable(View view, int i);

    int getSubscriptionId();

    StateFlow getUpdateDeXStatusBarIconModel();

    StateFlow getVoiceNoServiceIcon();

    StateFlow isVisible();
}
