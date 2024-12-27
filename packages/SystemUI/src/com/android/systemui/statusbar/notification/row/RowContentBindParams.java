package com.android.systemui.statusbar.notification.row;

public final class RowContentBindParams {
    public int mContentViews = 3;
    public int mDirtyContentViews = 3;
    public boolean mUseIncreasedHeadsUpHeight;
    public boolean mUseIncreasedHeight;
    public boolean mUseMinimized;
    public boolean mViewsNeedReinflation;

    public final void markContentViewsFreeable(int i) {
        int i2 = this.mContentViews;
        int i3 = i & i2;
        this.mContentViews = i2 & (~i3);
        this.mDirtyContentViews = i3 | this.mDirtyContentViews;
    }

    public final void requireContentViews(int i) {
        int i2 = this.mContentViews;
        int i3 = i & (~i2);
        this.mContentViews = i2 | i3;
        this.mDirtyContentViews = i3 | this.mDirtyContentViews;
    }

    public final String toString() {
        return String.format("RowContentBindParams[mContentViews=%x mDirtyContentViews=%x mUseMinimized=%b mUseIncreasedHeight=%b mUseIncreasedHeadsUpHeight=%b mViewsNeedReinflation=%b]", Integer.valueOf(this.mContentViews), Integer.valueOf(this.mDirtyContentViews), Boolean.valueOf(this.mUseMinimized), Boolean.valueOf(this.mUseIncreasedHeight), Boolean.valueOf(this.mUseIncreasedHeadsUpHeight), Boolean.valueOf(this.mViewsNeedReinflation));
    }
}
