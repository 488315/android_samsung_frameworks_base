package com.android.server.wm;

import com.samsung.android.cover.CoverState;

public final class WmCoverState extends CoverState {
    public static boolean sIsEnabled;
    public static WmCoverState sWmCoverState;

    public static WmCoverState getInstance() {
        if (sIsEnabled) {
            return sWmCoverState;
        }
        return null;
    }

    public final int getWindowLayerFromTypeLw(int i) {
        if (i != 2099 && i != 2411) {
            return -1;
        }
        int i2 = ((CoverState) this).type;
        if (i2 == 15 || i2 == 16 || i2 == 17) {
            return (i == 2099 || (((CoverState) this).switchState ^ true)) ? 26 : -1;
        }
        return -1;
    }

    public final boolean isFlipTypeCoverClosed() {
        if (!(!((CoverState) this).switchState)) {
            return false;
        }
        int i = ((CoverState) this).type;
        return i == 0 || i == 7 || i == 14;
    }

    public final boolean isViewCoverClosed() {
        if (!(!((CoverState) this).switchState)) {
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
}
