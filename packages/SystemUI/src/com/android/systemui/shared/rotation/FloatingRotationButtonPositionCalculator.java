package com.android.systemui.shared.rotation;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;
import com.android.systemui.navigationbar.BasicRuneWrapper;

public final class FloatingRotationButtonPositionCalculator {
    public final int defaultMargin;
    public final boolean floatingRotationButtonPositionLeft;
    public final int taskbarMarginBottom;
    public final int taskbarMarginLeft;

    public final class Position {
        public final int gravity;
        public final int translationX;
        public final int translationY;

        public Position(int i, int i2, int i3) {
            this.gravity = i;
            this.translationX = i2;
            this.translationY = i3;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Position)) {
                return false;
            }
            Position position = (Position) obj;
            return this.gravity == position.gravity && this.translationX == position.translationX && this.translationY == position.translationY;
        }

        public final int hashCode() {
            return Integer.hashCode(this.translationY) + KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.translationX, Integer.hashCode(this.gravity) * 31, 31);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("Position(gravity=");
            sb.append(this.gravity);
            sb.append(", translationX=");
            sb.append(this.translationX);
            sb.append(", translationY=");
            return Anchor$$ExternalSyntheticOutline0.m(this.translationY, ")", sb);
        }
    }

    public FloatingRotationButtonPositionCalculator(int i, int i2, int i3, boolean z) {
        this.defaultMargin = i;
        this.taskbarMarginLeft = i2;
        this.taskbarMarginBottom = i3;
        this.floatingRotationButtonPositionLeft = z;
    }

    public final Position calculatePosition(int i, boolean z, boolean z2) {
        int i2;
        boolean z3 = (i == 0 || i == 1) && z && !z2;
        if (BasicRuneWrapper.NAVBAR_ENABLED) {
            RotationUtil.Companion.getClass();
            i2 = RotationUtil.floatingButtonPosition;
        } else if (this.floatingRotationButtonPositionLeft) {
            if (i != 0) {
                if (i != 1) {
                    if (i != 2) {
                        if (i != 3) {
                            throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Invalid rotation "));
                        }
                        i2 = 51;
                    }
                    i2 = 53;
                }
                i2 = 85;
            }
            i2 = 83;
        } else {
            if (i != 0) {
                if (i != 1) {
                    if (i != 2) {
                        if (i != 3) {
                            throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Invalid rotation "));
                        }
                        i2 = 83;
                    }
                    i2 = 51;
                }
                i2 = 53;
            }
            i2 = 85;
        }
        int i3 = this.defaultMargin;
        int i4 = z3 ? this.taskbarMarginLeft : i3;
        if (z3) {
            i3 = this.taskbarMarginBottom;
        }
        if ((i2 & 5) == 5) {
            i4 = -i4;
        }
        if ((i2 & 80) == 80) {
            i3 = -i3;
        }
        return new Position(i2, i4, i3);
    }
}
