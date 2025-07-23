package com.android.systemui.statusbar.notification.row;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class RowContentBindParams {
    public boolean mUseMinimized;
    public boolean mViewsNeedReinflation;
    public int mContentViews = 3;
    public int mRedactionType = 0;
    public int mDirtyContentViews = 3;

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
        return String.format("RowContentBindParams[mContentViews=%x mDirtyContentViews=%x mUseMinimized=%b mViewsNeedReinflation=%b]", Integer.valueOf(this.mContentViews), Integer.valueOf(this.mDirtyContentViews), Boolean.valueOf(this.mUseMinimized), Boolean.valueOf(this.mViewsNeedReinflation));
    }
}
