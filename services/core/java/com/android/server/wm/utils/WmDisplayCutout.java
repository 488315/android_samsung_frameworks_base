package com.android.server.wm.utils;

import android.util.Size;
import android.view.DisplayCutout;

import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class WmDisplayCutout {
    public static final WmDisplayCutout NO_CUTOUT =
            new WmDisplayCutout(DisplayCutout.NO_CUTOUT, null);
    public final Size mFrameSize;
    public final DisplayCutout mInner;

    public WmDisplayCutout(DisplayCutout displayCutout, Size size) {
        this.mInner = displayCutout;
        this.mFrameSize = size;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof WmDisplayCutout)) {
            return false;
        }
        WmDisplayCutout wmDisplayCutout = (WmDisplayCutout) obj;
        return Objects.equals(this.mInner, wmDisplayCutout.mInner)
                && Objects.equals(this.mFrameSize, wmDisplayCutout.mFrameSize);
    }

    public final int hashCode() {
        return Objects.hash(this.mInner, this.mFrameSize);
    }

    public final String toString() {
        return "WmDisplayCutout{" + this.mInner + ", mFrameSize=" + this.mFrameSize + '}';
    }
}
