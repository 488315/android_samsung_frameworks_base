package com.android.server.wm;

import com.samsung.android.cover.CoverState;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
