package com.android.systemui.wallpaper.view;

import android.graphics.Bitmap;
import android.view.MotionEvent;
import com.android.systemui.wallpaper.KeyguardWallpaperController;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface SystemUIWallpaperBase {
    void cleanUp();

    Bitmap getCapturedWallpaper();

    Bitmap getCapturedWallpaperForBlur();

    default int getCurrentPosition() {
        return 0;
    }

    Bitmap getWallpaperBitmap();

    void handleTouchEvent(MotionEvent motionEvent);

    void onBackDropLayoutChange();

    void onFaceAuthError();

    void onFingerprintAuthSuccess(boolean z);

    void onKeyguardBouncerFullyShowingChanged(boolean z);

    void onKeyguardShowing(boolean z);

    void onOccluded(boolean z);

    void onPause();

    void onResume();

    void onUnlock();

    void reset();

    void update();

    void updateBlurState(boolean z);

    default void dispatchWallpaperCommand(String str) {
    }

    default void setStartPosition(int i) {
    }

    default void setThumbnailVisibility(int i) {
    }

    default void setTransitionAnimationListener(KeyguardWallpaperController.C36634 c36634) {
    }

    default void updateDrawState(boolean z) {
    }

    default void updateThumbnail() {
    }
}
