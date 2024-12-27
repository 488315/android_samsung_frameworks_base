package com.android.server.wm;

import android.content.pm.ActivityInfo;
import android.graphics.Rect;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class AppCompatAspectRatioPolicy {
    public final ActivityRecord mActivityRecord;
    public final AppCompatAspectRatioState mAppCompatAspectRatioState;
    public final AppCompatOverrides mAppCompatOverrides;
    public final TransparentPolicy mTransparentPolicy;
    public float mUserOrSystemMinAspectRatio;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AppCompatAspectRatioState {
        public boolean mIsAspectRatioApplied;
        public Rect mLetterboxBoundsForAspectRatio;
        public Rect mLetterboxBoundsForFixedOrientationAndAspectRatio;
    }

    public AppCompatAspectRatioPolicy(ActivityRecord activityRecord, TransparentPolicy transparentPolicy, AppCompatOverrides appCompatOverrides) {
        this.mActivityRecord = activityRecord;
        this.mTransparentPolicy = transparentPolicy;
        this.mAppCompatOverrides = appCompatOverrides;
        AppCompatAspectRatioState appCompatAspectRatioState = new AppCompatAspectRatioState();
        appCompatAspectRatioState.mIsAspectRatioApplied = false;
        this.mAppCompatAspectRatioState = appCompatAspectRatioState;
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0078  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean applyAspectRatio(android.graphics.Rect r17, android.graphics.Rect r18, android.graphics.Rect r19, float r20) {
        /*
            Method dump skipped, instructions count: 352
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.AppCompatAspectRatioPolicy.applyAspectRatio(android.graphics.Rect, android.graphics.Rect, android.graphics.Rect, float):boolean");
    }

    public final float getMinAspectRatio() {
        WindowContainer parent;
        TransparentPolicy transparentPolicy = this.mTransparentPolicy;
        if (transparentPolicy.mTransparentPolicyState.isRunning()) {
            return transparentPolicy.mTransparentPolicyState.mInheritedMinAspectRatio;
        }
        ActivityRecord activityRecord = this.mActivityRecord;
        ActivityInfo activityInfo = activityRecord.info;
        if (activityInfo.applicationInfo == null) {
            return activityInfo.getMinAspectRatio();
        }
        AppCompatOverrides appCompatOverrides = this.mAppCompatOverrides;
        AppCompatAspectRatioOverrides appCompatAspectRatioOverrides = appCompatOverrides.mAppCompatAspectRatioOverrides;
        return appCompatAspectRatioOverrides.shouldApplyUserMinAspectRatioOverride() ? appCompatAspectRatioOverrides.getUserMinAspectRatio() : (appCompatAspectRatioOverrides.mAllowMinAspectRatioOverrideOptProp.shouldEnableWithOptInOverrideAndOptOutProperty(appCompatAspectRatioOverrides.mActivityRecord.info.isChangeEnabled(174042980L)) || appCompatOverrides.mAppCompatCameraOverrides.shouldOverrideMinAspectRatioForCamera()) ? (!activityInfo.isChangeEnabled(203647190L) || ActivityInfo.isFixedOrientationPortrait(activityRecord.getOverrideOrientation())) ? (activityInfo.isChangeEnabled(218959984L) && (parent = activityRecord.getParent()) != null && parent.getConfiguration().orientation == 1 && parent.getWindowConfiguration().getWindowingMode() == 1) ? activityInfo.getMinAspectRatio() : activityInfo.isChangeEnabled(208648326L) ? Math.max(appCompatAspectRatioOverrides.getSplitScreenAspectRatio(), activityInfo.getMinAspectRatio()) : activityInfo.isChangeEnabled(180326787L) ? Math.max(1.7777778f, activityInfo.getMinAspectRatio()) : activityInfo.isChangeEnabled(180326845L) ? Math.max(1.5f, activityInfo.getMinAspectRatio()) : activityInfo.isChangeEnabled(349045028L) ? Math.max(1.3333334f, activityInfo.getMinAspectRatio()) : activityInfo.getMinAspectRatio() : activityInfo.getMinAspectRatio() : activityInfo.getMinAspectRatio();
    }

    public final boolean isLetterboxedForFixedOrientationAndAspectRatio() {
        return this.mAppCompatAspectRatioState.mLetterboxBoundsForFixedOrientationAndAspectRatio != null;
    }

    public final boolean isUserOrSystemMinAspectRatioApplied() {
        return this.mAppCompatAspectRatioState.mIsAspectRatioApplied && this.mUserOrSystemMinAspectRatio != -1.0f;
    }
}
