package com.android.systemui.screenshot.sep;

import android.graphics.Rect;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class ScreenCaptureHelperForB5CoverScreen extends ScreenCaptureHelper {
    @Override // com.android.systemui.screenshot.sep.ScreenCaptureHelper
    public final Rect getScreenshotEffectRect() {
        int i = this.screenWidth;
        int i2 = this.screenHeight;
        if (!isB5ScreenEffect()) {
            i2 -= this.safeInsetTop + this.safeInsetBottom;
        }
        Rect rect = new Rect();
        rect.set(0, 0, i, i2);
        return rect;
    }

    @Override // com.android.systemui.screenshot.sep.ScreenCaptureHelper
    public final Rect getScreenshotRectToCapture() {
        boolean isExcludeSystemUI = ScreenshotUtils.isExcludeSystemUI(this.displayContext);
        if (!isB5ScreenEffect()) {
            this.screenNativeHeight -= this.safeInsetTop + this.safeInsetBottom;
        } else if (ScreenshotUtils.isExcludeSystemUI(this.displayContext) && this.isNavigationBarVisible) {
            this.screenNativeHeight -= this.navigationBarHeight;
        }
        Rect rect = this.rectToCapture;
        if (isB5CoverScreenInReverseMode() && isExcludeSystemUI) {
            rect.set(0, this.navigationBarHeight, (int) this.screenNativeWidth, (int) this.screenNativeHeight);
        } else {
            rect.set(0, 0, (int) this.screenNativeWidth, (int) this.screenNativeHeight);
        }
        return rect;
    }

    @Override // com.android.systemui.screenshot.sep.ScreenCaptureHelper
    public final boolean isB5CoverScreenInReverseMode() {
        int navBarPosition = ScreenshotUtils.getNavBarPosition(this.displayContext, this.navigationBarHeight, true);
        if (this.isNavigationBarVisible) {
            if (ScreenCaptureHelper.getDegreesForRotation(ScreenshotUtils.getDisplay(this.capturedDisplayId, this.displayContext)) == 180.0f && navBarPosition == 4) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.systemui.screenshot.sep.ScreenCaptureHelper
    public final boolean isB5ScreenEffect() {
        return (!isLetterBoxHide() || this.isNavigationBarVisible) && !isB5CoverScreenInReverseMode();
    }

    @Override // com.android.systemui.screenshot.sep.ScreenCaptureHelper
    public final boolean isLetterBoxHide() {
        return this.safeInsetTop > 0 || this.safeInsetLeft > 0 || this.safeInsetRight > 0 || this.safeInsetBottom > 0;
    }
}
