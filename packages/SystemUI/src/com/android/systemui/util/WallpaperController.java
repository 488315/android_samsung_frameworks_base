package com.android.systemui.util;

import android.app.WallpaperInfo;
import android.app.WallpaperManager;
import android.util.Log;
import android.view.View;
import com.android.systemui.wallpapers.data.repository.WallpaperRepository;
import com.android.systemui.wallpapers.data.repository.WallpaperRepositoryImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class WallpaperController {
    public static final int $stable = 8;
    private float notificationShadeZoomOut;
    private View rootView;
    private float unfoldTransitionZoomOut;
    private final WallpaperManager wallpaperManager;
    private final WallpaperRepository wallpaperRepository;

    public WallpaperController(WallpaperManager wallpaperManager, WallpaperRepository wallpaperRepository) {
        this.wallpaperManager = wallpaperManager;
        this.wallpaperRepository = wallpaperRepository;
    }

    private final boolean getShouldUseDefaultUnfoldTransition() {
        WallpaperInfo wallpaperInfo = (WallpaperInfo) ((WallpaperRepositoryImpl) this.wallpaperRepository).wallpaperInfo.$$delegate_0.getValue();
        if (wallpaperInfo != null) {
            return wallpaperInfo.shouldUseDefaultUnfoldTransition();
        }
        return true;
    }

    private final void setWallpaperZoom(float f) {
        try {
            View view = this.rootView;
            if (view != null) {
                if (!view.isAttachedToWindow() || view.getWindowToken() == null) {
                    Log.i("WallpaperController", "Won't set zoom. Window not attached " + view);
                } else {
                    this.wallpaperManager.setWallpaperZoomOut(view.getWindowToken(), f);
                }
            }
        } catch (IllegalArgumentException e) {
            View view2 = this.rootView;
            Log.w("WallpaperController", "Can't set zoom. Window is gone: " + (view2 != null ? view2.getWindowToken() : null), e);
        }
    }

    private final void updateZoom() {
        setWallpaperZoom(Math.max(this.notificationShadeZoomOut, this.unfoldTransitionZoomOut));
    }

    public final View getRootView() {
        return this.rootView;
    }

    public final void setNotificationShadeZoom(float f) {
        this.notificationShadeZoomOut = f;
        updateZoom();
    }

    public final void setRootView(View view) {
        this.rootView = view;
    }

    public final void setUnfoldTransitionZoom(float f) {
        if (getShouldUseDefaultUnfoldTransition()) {
            this.unfoldTransitionZoomOut = f;
            updateZoom();
        }
    }
}
