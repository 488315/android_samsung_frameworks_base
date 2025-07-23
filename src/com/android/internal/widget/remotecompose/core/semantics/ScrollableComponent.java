package com.android.internal.widget.remotecompose.core.semantics;

import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.operations.layout.Component;

/* loaded from: classes6.dex */
public interface ScrollableComponent extends AccessibilitySemantics {
    public static final int SCROLL_HORIZONTAL = 1;
    public static final int SCROLL_NONE = 0;
    public static final int SCROLL_VERTICAL = 2;

    public enum ScrollDirection {
        FORWARD,
        BACKWARD,
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    default ScrollAxisRange getScrollAxisRange() {
        return null;
    }

    default int scrollByOffset(RemoteContext remoteContext, int i) {
        return i;
    }

    int scrollDirection();

    default boolean scrollDirection(RemoteContext remoteContext, ScrollDirection scrollDirection) {
        return false;
    }

    default boolean showOnScreen(RemoteContext remoteContext, Component component) {
        return false;
    }

    default boolean supportsScrollByOffset() {
        return true;
    }

    public static class ScrollAxisRange {
        private boolean mCanScrollBackwards;
        private boolean mCanScrollForward;
        private float mMaxValue;
        private float mValue;

        public ScrollAxisRange(float f, float f2, boolean z, boolean z2) {
            this.mValue = f;
            this.mMaxValue = f2;
            this.mCanScrollForward = z;
            this.mCanScrollBackwards = z2;
        }

        public float getmValue() {
            return this.mValue;
        }

        public float getMaxValue() {
            return this.mMaxValue;
        }

        public boolean canScrollForward() {
            return this.mCanScrollForward;
        }

        public boolean canScrollBackwards() {
            return this.mCanScrollBackwards;
        }
    }
}
