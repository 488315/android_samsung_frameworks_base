package com.android.systemui.unfold;

import com.android.systemui.unfold.UnfoldTransitionProgressProvider;
import com.android.systemui.util.WallpaperController;

public final class UnfoldTransitionWallpaperController {
    public final UnfoldTransitionProgressProvider unfoldTransitionProgressProvider;
    public final WallpaperController wallpaperController;

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
