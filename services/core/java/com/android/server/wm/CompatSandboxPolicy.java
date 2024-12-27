package com.android.server.wm;

import android.content.res.Configuration;
import android.graphics.Rect;

import com.android.server.wm.utils.OptPropFactory;

import com.samsung.android.core.CompatSandbox;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class CompatSandboxPolicy {
    public final ActivityRecord mActivityRecord;
    public final OptPropFactory.OptProp mAllowSandboxingViewBoundsApisProp;
    public int mFlags = 0;
    public float mScale = -1.0f;
    public Rect mBounds = null;

    public CompatSandboxPolicy(ActivityRecord activityRecord) {
        this.mActivityRecord = activityRecord;
        this.mAllowSandboxingViewBoundsApisProp =
                new OptPropFactory(
                                activityRecord.mWmService.mContext.getPackageManager(),
                                activityRecord.packageName)
                        .create("android.window.PROPERTY_COMPAT_ALLOW_SANDBOXING_VIEW_BOUNDS_APIS");
    }

    public final void resolveCompatSandboxValues(Configuration configuration) {
        OptPropFactory.OptProp optProp = this.mAllowSandboxingViewBoundsApisProp;
        if ((optProp.mCondition.getAsBoolean() && optProp.getValue() == 1) || optProp.isFalse()) {
            this.mFlags &= -5;
        }
        if (this.mFlags == 0 && configuration.windowConfiguration.getCompatSandboxFlags() != 0) {
            this.mFlags = 1;
            this.mScale = 1.0f;
            this.mBounds = CompatSandbox.getEmptyRect();
        }
        this.mActivityRecord
                .getResolvedOverrideConfiguration()
                .windowConfiguration
                .setCompatSandboxValues(this.mFlags, this.mScale, this.mBounds);
        this.mFlags = 0;
        this.mScale = -1.0f;
        this.mBounds = null;
    }
}
