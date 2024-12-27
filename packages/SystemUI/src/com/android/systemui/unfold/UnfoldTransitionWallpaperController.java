package com.android.systemui.unfold;

import com.android.systemui.unfold.UnfoldTransitionProgressProvider;
import com.android.systemui.util.WallpaperController;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class UnfoldTransitionWallpaperController {
    public final UnfoldTransitionProgressProvider unfoldTransitionProgressProvider;
    public final WallpaperController wallpaperController;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
