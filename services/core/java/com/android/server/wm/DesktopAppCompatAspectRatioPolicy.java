package com.android.server.wm;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class DesktopAppCompatAspectRatioPolicy {
    public final ActivityRecord mActivityRecord;
    public final AppCompatConfiguration mAppCompatConfiguration;
    public final AppCompatOverrides mAppCompatOverrides;
    public final TransparentPolicy mTransparentPolicy;

    public DesktopAppCompatAspectRatioPolicy(
            ActivityRecord activityRecord,
            AppCompatOverrides appCompatOverrides,
            TransparentPolicy transparentPolicy,
            AppCompatConfiguration appCompatConfiguration) {
        this.mActivityRecord = activityRecord;
        this.mAppCompatOverrides = appCompatOverrides;
        this.mTransparentPolicy = transparentPolicy;
        this.mAppCompatConfiguration = appCompatConfiguration;
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x00cc A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:21:0x00cd  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public float getDesiredAspectRatio(com.android.server.wm.Task r6) {
        /*
            r5 = this;
            com.android.server.wm.ActivityRecord r0 = r5.mActivityRecord
            boolean r1 = r0.shouldCreateAppCompatDisplayInsets()
            com.android.server.wm.AppCompatConfiguration r5 = r5.mAppCompatConfiguration
            r2 = 1065353216(0x3f800000, float:1.0)
            if (r1 == 0) goto L90
            boolean r1 = r5.mIsSplitScreenAspectRatioForUnresizableAppsEnabled
            if (r1 != 0) goto L37
            float r0 = r5.mDefaultMinAspectRatioForUnresizableApps
            int r1 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r1 <= 0) goto L18
            goto Lae
        L18:
            boolean r0 = r5.mIsDisplayAspectRatioEnabledForFixedOrientationLetterbox
            if (r0 != 0) goto L21
            float r5 = r5.mFixedOrientationLetterboxAspectRatio
        L1e:
            r0 = r5
            goto Lae
        L21:
            com.android.server.wm.TaskDisplayArea r5 = r6.getDisplayArea()
            android.graphics.Rect r0 = new android.graphics.Rect
            android.app.WindowConfiguration r5 = r5.getWindowConfiguration()
            android.graphics.Rect r5 = r5.getAppBounds()
            r0.<init>(r5)
            float r5 = com.android.server.wm.AppCompatUtils.computeAspectRatio(r0)
            goto L1e
        L37:
            com.android.server.wm.TaskDisplayArea r5 = r6.getDisplayArea()
            com.android.server.wm.WindowManagerService r1 = r0.mWmService
            android.content.Context r1 = r1.mContext
            android.content.res.Resources r1 = r1.getResources()
            r3 = 17105284(0x1050184, float:2.442933E-38)
            int r1 = r1.getDimensionPixelSize(r3)
            com.android.server.wm.WindowManagerService r0 = r0.mWmService
            android.content.Context r0 = r0.mContext
            android.content.res.Resources r0 = r0.getResources()
            r3 = 17105283(0x1050183, float:2.4429327E-38)
            int r0 = r0.getDimensionPixelSize(r3)
            int r0 = r0 * 2
            int r1 = r1 - r0
            android.graphics.Rect r0 = new android.graphics.Rect
            android.app.WindowConfiguration r5 = r5.getWindowConfiguration()
            android.graphics.Rect r5 = r5.getAppBounds()
            r0.<init>(r5)
            int r5 = r0.width()
            int r3 = r0.height()
            r4 = 0
            if (r5 < r3) goto L80
            int r1 = r1 / 2
            r0.inset(r1, r4)
            int r5 = r0.centerX()
            r0.right = r5
            goto L8b
        L80:
            int r1 = r1 / 2
            r0.inset(r4, r1)
            int r5 = r0.centerY()
            r0.bottom = r5
        L8b:
            float r0 = com.android.server.wm.AppCompatUtils.computeAspectRatio(r0)
            goto Lae
        L90:
            boolean r0 = r5.mIsDisplayAspectRatioEnabledForFixedOrientationLetterbox
            if (r0 != 0) goto L97
            float r5 = r5.mFixedOrientationLetterboxAspectRatio
            goto L1e
        L97:
            com.android.server.wm.TaskDisplayArea r5 = r6.getDisplayArea()
            android.graphics.Rect r0 = new android.graphics.Rect
            android.app.WindowConfiguration r5 = r5.getWindowConfiguration()
            android.graphics.Rect r5 = r5.getAppBounds()
            r0.<init>(r5)
            float r5 = com.android.server.wm.AppCompatUtils.computeAspectRatio(r0)
            goto L1e
        Lae:
            com.android.server.wm.DisplayContent r5 = r6.mDisplayContent
            com.android.server.wm.TaskDisplayArea r1 = r6.getDisplayArea()
            int r1 = r1.getWindowingMode()
            boolean r1 = android.app.WindowConfiguration.inMultiWindowMode(r1)
            if (r1 == 0) goto Lc8
            boolean r5 = r5.getIgnoreOrientationRequest()
            if (r5 != 0) goto Lc8
            r5 = 1065437102(0x3f8147ae, float:1.01)
            return r5
        Lc8:
            int r5 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r5 <= 0) goto Lcd
            return r0
        Lcd:
            com.android.server.wm.TaskDisplayArea r5 = r6.getDisplayArea()
            android.graphics.Rect r5 = r5.getBounds()
            float r5 = com.android.server.wm.AppCompatUtils.computeAspectRatio(r5)
            return r5
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.wm.DesktopAppCompatAspectRatioPolicy.getDesiredAspectRatio(com.android.server.wm.Task):float");
    }
}
