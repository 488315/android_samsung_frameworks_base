package com.android.systemui.unfold;

import com.android.systemui.unfold.UnfoldTransitionProgressProvider;
import com.android.systemui.util.WallpaperController;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class UnfoldTransitionWallpaperController {
    public final UnfoldTransitionProgressProvider unfoldTransitionProgressProvider;
    public final WallpaperController wallpaperController;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class TransitionListener implements UnfoldTransitionProgressProvider.TransitionProgressListener {
        public TransitionListener() {
        }

        @Override // com.android.systemui.unfold.UnfoldTransitionProgressProvider.TransitionProgressListener
        public final void onTransitionFinished() {
            UnfoldTransitionWallpaperController.this.wallpaperController.setUnfoldTransitionZoom(0.0f);
        }

        @Override // com.android.systemui.unfold.UnfoldTransitionProgressProvider.TransitionProgressListener
        public final void onTransitionProgress(float f) {
            UnfoldTransitionWallpaperController.this.wallpaperController.setUnfoldTransitionZoom(1 - f);
        }
    }

    public UnfoldTransitionWallpaperController(UnfoldTransitionProgressProvider unfoldTransitionProgressProvider, WallpaperController wallpaperController) {
        this.unfoldTransitionProgressProvider = unfoldTransitionProgressProvider;
        this.wallpaperController = wallpaperController;
    }
}
