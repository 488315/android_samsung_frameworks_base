package com.android.systemui.shade.transition;

import android.content.Context;
import android.content.res.Configuration;
import com.android.systemui.animation.ShadeInterpolation;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.SplitShadeStateController;
import com.android.systemui.statusbar.policy.SplitShadeStateControllerImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
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

    @Override // com.android.systemui.shade.transition.LargeScreenShadeInterpolator
    public final float getQsAlpha(float f) {
        this.portraitShadeInterpolator.getClass();
        return ShadeInterpolation.getContentAlpha(f);
    }
}
