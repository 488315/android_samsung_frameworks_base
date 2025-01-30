package com.android.p038wm.shell.back;

import android.graphics.Color;
import android.graphics.Rect;
import android.view.SurfaceControl;
import com.android.internal.graphics.ColorUtils;
import com.android.internal.view.AppearanceRegion;
import com.android.p038wm.shell.RootTaskDisplayAreaOrganizer;
import com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler;
import com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler$$ExternalSyntheticLambda5;
import com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler$$ExternalSyntheticLambda7;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class BackAnimationBackground {
    public boolean mBackgroundIsDark;
    public SurfaceControl mBackgroundSurface;
    public EdgeBackGestureHandler$$ExternalSyntheticLambda5 mCustomizer;
    public boolean mIsRequestingStatusBarAppearance;
    public final RootTaskDisplayAreaOrganizer mRootTaskDisplayAreaOrganizer;
    public Rect mStartBounds;

    public BackAnimationBackground(RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer) {
        this.mRootTaskDisplayAreaOrganizer = rootTaskDisplayAreaOrganizer;
    }

    public final void ensureBackground(Rect rect, int i, SurfaceControl.Transaction transaction) {
        if (this.mBackgroundSurface != null) {
            return;
        }
        this.mBackgroundIsDark = ColorUtils.calculateLuminance(i) < 0.5d;
        float[] fArr = {Color.red(i) / 255.0f, Color.green(i) / 255.0f, Color.blue(i) / 255.0f};
        SurfaceControl.Builder colorLayer = new SurfaceControl.Builder().setName("back-animation-background").setCallsite("BackAnimationBackground").setColorLayer();
        colorLayer.setParent((SurfaceControl) this.mRootTaskDisplayAreaOrganizer.mLeashes.get(0));
        SurfaceControl build = colorLayer.build();
        this.mBackgroundSurface = build;
        transaction.setColor(build, fArr).setLayer(this.mBackgroundSurface, -1).show(this.mBackgroundSurface);
        this.mStartBounds = rect;
        this.mIsRequestingStatusBarAppearance = false;
    }

    public final void onBackProgressed(float f) {
        if (this.mCustomizer == null || this.mStartBounds.isEmpty()) {
            return;
        }
        boolean z = f > 0.2f;
        if (z == this.mIsRequestingStatusBarAppearance) {
            return;
        }
        this.mIsRequestingStatusBarAppearance = z;
        if (!z) {
            EdgeBackGestureHandler edgeBackGestureHandler = this.mCustomizer.f$0;
            edgeBackGestureHandler.getClass();
            edgeBackGestureHandler.mMainExecutor.execute(new EdgeBackGestureHandler$$ExternalSyntheticLambda7(edgeBackGestureHandler, null));
        } else {
            AppearanceRegion appearanceRegion = new AppearanceRegion(this.mBackgroundIsDark ? 0 : 8, this.mStartBounds);
            EdgeBackGestureHandler edgeBackGestureHandler2 = this.mCustomizer.f$0;
            edgeBackGestureHandler2.getClass();
            edgeBackGestureHandler2.mMainExecutor.execute(new EdgeBackGestureHandler$$ExternalSyntheticLambda7(edgeBackGestureHandler2, appearanceRegion));
        }
    }

    public final void removeBackground(SurfaceControl.Transaction transaction) {
        SurfaceControl surfaceControl = this.mBackgroundSurface;
        if (surfaceControl == null) {
            return;
        }
        if (surfaceControl.isValid()) {
            transaction.remove(this.mBackgroundSurface);
        }
        this.mBackgroundSurface = null;
        this.mIsRequestingStatusBarAppearance = false;
    }
}
