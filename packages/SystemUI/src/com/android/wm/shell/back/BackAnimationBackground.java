package com.android.wm.shell.back;

import android.graphics.Color;
import android.graphics.Rect;
import android.view.SurfaceControl;
import com.android.internal.graphics.ColorUtils;
import com.android.internal.view.AppearanceRegion;
import com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler$$ExternalSyntheticLambda10;
import com.android.wm.shell.RootTaskDisplayAreaOrganizer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class BackAnimationBackground {
    public boolean mBackgroundIsDark;
    public SurfaceControl mBackgroundSurface;
    public EdgeBackGestureHandler$$ExternalSyntheticLambda10 mCustomizer;
    public boolean mIsRequestingStatusBarAppearance;
    public final RootTaskDisplayAreaOrganizer mRootTaskDisplayAreaOrganizer;
    public Rect mStartBounds;
    public int mStatusbarHeight;

    public BackAnimationBackground(RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer) {
        this.mRootTaskDisplayAreaOrganizer = rootTaskDisplayAreaOrganizer;
    }

    public final void customizeStatusBarAppearance(int i) {
        if (this.mCustomizer == null || this.mStartBounds.isEmpty()) {
            return;
        }
        boolean z = i > this.mStatusbarHeight / 2;
        if (z == this.mIsRequestingStatusBarAppearance) {
            return;
        }
        this.mIsRequestingStatusBarAppearance = z;
        if (z) {
            this.mCustomizer.customizeStatusBarAppearance(new AppearanceRegion(this.mBackgroundIsDark ? 0 : 8, this.mStartBounds));
        } else {
            this.mCustomizer.customizeStatusBarAppearance(null);
        }
    }

    public final void ensureBackground(Rect rect, int i, SurfaceControl.Transaction transaction, int i2, Rect rect2, float f) {
        if (this.mBackgroundSurface != null) {
            return;
        }
        this.mBackgroundIsDark = ColorUtils.calculateLuminance(i) < 0.5d;
        float[] fArr = {Color.red(i) / 255.0f, Color.green(i) / 255.0f, Color.blue(i) / 255.0f};
        SurfaceControl.Builder colorLayer = new SurfaceControl.Builder().setName("back-animation-background").setCallsite("BackAnimationBackground").setColorLayer();
        this.mRootTaskDisplayAreaOrganizer.attachToDisplayArea(0, colorLayer);
        SurfaceControl build = colorLayer.build();
        this.mBackgroundSurface = build;
        transaction.setColor(build, fArr).setLayer(this.mBackgroundSurface, -1).show(this.mBackgroundSurface);
        if (rect2 != null && !rect2.isEmpty()) {
            transaction.setCrop(this.mBackgroundSurface, rect2).setCornerRadius(this.mBackgroundSurface, f);
        }
        this.mStartBounds = rect;
        this.mIsRequestingStatusBarAppearance = false;
        this.mStatusbarHeight = i2;
    }
}
