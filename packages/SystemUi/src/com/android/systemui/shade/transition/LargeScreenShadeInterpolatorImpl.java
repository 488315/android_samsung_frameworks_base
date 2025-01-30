package com.android.systemui.shade.transition;

import android.content.Context;
import android.content.res.Configuration;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.LargeScreenUtils;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class LargeScreenShadeInterpolatorImpl implements LargeScreenShadeInterpolator {
    public final Context context;
    public boolean inSplitShade;
    public final LargeScreenPortraitShadeInterpolator portraitShadeInterpolator;
    public final SplitShadeInterpolator splitShadeInterpolator;

    public LargeScreenShadeInterpolatorImpl(ConfigurationController configurationController, Context context, SplitShadeInterpolator splitShadeInterpolator, LargeScreenPortraitShadeInterpolator largeScreenPortraitShadeInterpolator) {
        this.context = context;
        this.splitShadeInterpolator = splitShadeInterpolator;
        this.portraitShadeInterpolator = largeScreenPortraitShadeInterpolator;
        ((ConfigurationControllerImpl) configurationController).addCallback(new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.shade.transition.LargeScreenShadeInterpolatorImpl.1
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onConfigChanged(Configuration configuration) {
                LargeScreenShadeInterpolatorImpl largeScreenShadeInterpolatorImpl = LargeScreenShadeInterpolatorImpl.this;
                largeScreenShadeInterpolatorImpl.inSplitShade = LargeScreenUtils.shouldUseSplitNotificationShade(largeScreenShadeInterpolatorImpl.context.getResources());
            }
        });
        this.inSplitShade = LargeScreenUtils.shouldUseSplitNotificationShade(context.getResources());
    }

    @Override // com.android.systemui.shade.transition.LargeScreenShadeInterpolator
    public final float getBehindScrimAlpha(float f) {
        return (this.inSplitShade ? this.splitShadeInterpolator : this.portraitShadeInterpolator).getBehindScrimAlpha(f);
    }

    @Override // com.android.systemui.shade.transition.LargeScreenShadeInterpolator
    public final float getNotificationContentAlpha(float f) {
        return (this.inSplitShade ? this.splitShadeInterpolator : this.portraitShadeInterpolator).getNotificationContentAlpha(f);
    }

    @Override // com.android.systemui.shade.transition.LargeScreenShadeInterpolator
    public final float getNotificationScrimAlpha(float f) {
        return (this.inSplitShade ? this.splitShadeInterpolator : this.portraitShadeInterpolator).getNotificationScrimAlpha(f);
    }
}
