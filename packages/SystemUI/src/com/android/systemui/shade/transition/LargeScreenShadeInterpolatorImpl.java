package com.android.systemui.shade.transition;

import android.content.Context;
import android.content.res.Configuration;
import com.android.systemui.animation.ShadeInterpolation;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.SplitShadeStateController;
import com.android.systemui.statusbar.policy.SplitShadeStateControllerImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class LargeScreenShadeInterpolatorImpl implements LargeScreenShadeInterpolator {
    public final Context context;
    public final LargeScreenPortraitShadeInterpolator portraitShadeInterpolator;
    public final SplitShadeStateController splitShadeStateController;

    public LargeScreenShadeInterpolatorImpl(ConfigurationController configurationController, Context context, SplitShadeInterpolator splitShadeInterpolator, LargeScreenPortraitShadeInterpolator largeScreenPortraitShadeInterpolator, SplitShadeStateController splitShadeStateController) {
        this.context = context;
        this.portraitShadeInterpolator = largeScreenPortraitShadeInterpolator;
        this.splitShadeStateController = splitShadeStateController;
        ((ConfigurationControllerImpl) configurationController).addCallback(new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.shade.transition.LargeScreenShadeInterpolatorImpl.1
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onConfigChanged(Configuration configuration) {
                LargeScreenShadeInterpolatorImpl largeScreenShadeInterpolatorImpl = LargeScreenShadeInterpolatorImpl.this;
                largeScreenShadeInterpolatorImpl.context.getResources();
                ((SplitShadeStateControllerImpl) largeScreenShadeInterpolatorImpl.splitShadeStateController).shouldUseSplitNotificationShade();
            }
        });
        context.getResources();
        ((SplitShadeStateControllerImpl) splitShadeStateController).shouldUseSplitNotificationShade();
    }

    @Override // com.android.systemui.shade.transition.LargeScreenShadeInterpolator
    public final float getBehindScrimAlpha(float f) {
        return this.portraitShadeInterpolator.getBehindScrimAlpha(f);
    }

    @Override // com.android.systemui.shade.transition.LargeScreenShadeInterpolator
    public final float getNotificationContentAlpha(float f) {
        this.portraitShadeInterpolator.getClass();
        return ShadeInterpolation.getContentAlpha(f);
    }

    @Override // com.android.systemui.shade.transition.LargeScreenShadeInterpolator
    public final float getNotificationScrimAlpha(float f) {
        return this.portraitShadeInterpolator.getNotificationScrimAlpha(f);
    }
}
