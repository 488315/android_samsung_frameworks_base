package com.android.server.wm;

import android.view.WindowManager;
import com.samsung.android.cover.CoverState;

/* loaded from: classes3.dex */
public final class WmCoverState extends CoverState {
    public static boolean sIsEnabled = false;
    public static WmCoverState sWmCoverState;

    public static WmCoverState getInstance() {
        if (sIsEnabled) {
            return sWmCoverState;
        }
        return null;
    }

    public static void enable() {
        sIsEnabled = true;
        if (sWmCoverState == null) {
            sWmCoverState = new WmCoverState();
        }
    }

    public static boolean isEnabled() {
        return sIsEnabled;
    }

    public boolean isCoverClosed() {
        return !((CoverState) this).switchState;
    }

    public boolean isCoverAppSupported() {
        switch (((CoverState) this).type) {
            case 15:
            case 16:
            case 17:
                return true;
            default:
                return false;
        }
    }

    public boolean isViewCoverClosed() {
        if (!isCoverClosed()) {
            return false;
        }
        switch (((CoverState) this).type) {
            case 15:
            case 16:
            case 17:
                return true;
            default:
                return false;
        }
    }

    public boolean isFlipTypeCoverClosed() {
        if (!isCoverClosed()) {
            return false;
        }
        int i = ((CoverState) this).type;
        return i == 0 || i == 7 || i == 14;
    }

    public boolean isClearTypeCover() {
        switch (((CoverState) this).type) {
            case 15:
            case 16:
            case 17:
                return true;
            default:
                return false;
        }
    }

    public boolean isClearTypeCoverClosed() {
        return isCoverClosed() && isClearTypeCover();
    }

    public boolean shouldHideStatusBarForCover() {
        return isClearTypeCover();
    }

    public boolean updateCoverState(CoverState coverState) {
        if (coverState.type == 2 && coverState.switchState == ((CoverState) this).switchState) {
            return false;
        }
        copyFrom(coverState);
        return true;
    }

    public int getWindowLayerFromTypeLw(int i) {
        if (i != 2099 && i != 2411) {
            return -1;
        }
        int i2 = ((CoverState) this).type;
        if (i2 == 15 || i2 == 16 || i2 == 17) {
            return (i == 2099 || isCoverClosed()) ? 26 : -1;
        }
        return -1;
    }

    public boolean windowAttrsHasShowWallpaperOrShowWhenLocked(WindowManager.LayoutParams layoutParams) {
        int i = layoutParams.flags;
        return ((1048576 & i) == 0 && (i & 524288) == 0) ? false : true;
    }
}
