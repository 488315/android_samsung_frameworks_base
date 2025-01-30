package com.android.systemui.util;

import android.app.WallpaperInfo;
import android.app.WallpaperManager;
import android.util.Log;
import android.view.View;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WallpaperController {
    public View rootView;
    public WallpaperInfo wallpaperInfo;
    public final WallpaperManager wallpaperManager;

    public WallpaperController(WallpaperManager wallpaperManager) {
        this.wallpaperManager = wallpaperManager;
    }

    public final void setUnfoldTransitionZoom(float f) {
        WallpaperInfo wallpaperInfo = this.wallpaperInfo;
        if (wallpaperInfo != null ? wallpaperInfo.shouldUseDefaultUnfoldTransition() : true) {
            float max = Math.max(0.0f, f);
            try {
                View view = this.rootView;
                if (view != null) {
                    if (!view.isAttachedToWindow() || view.getWindowToken() == null) {
                        Log.i("WallpaperController", "Won't set zoom. Window not attached " + view);
                    } else {
                        this.wallpaperManager.setWallpaperZoomOut(view.getWindowToken(), max);
                    }
                }
            } catch (IllegalArgumentException e) {
                View view2 = this.rootView;
                Log.w("WallpaperController", "Can't set zoom. Window is gone: " + (view2 != null ? view2.getWindowToken() : null), e);
            }
        }
    }
}
