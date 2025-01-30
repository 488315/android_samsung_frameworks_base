package com.android.wm.shell.pip;

import android.content.Context;
import android.graphics.Rect;
import com.android.wm.shell.common.DisplayLayout;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PipDisplayLayoutState {
    public final Context mContext;
    public int mDisplayId;
    public final DisplayLayout mDisplayLayout = new DisplayLayout();

    public PipDisplayLayoutState(Context context) {
        this.mContext = context;
    }

    public final Rect getDisplayBounds() {
        DisplayLayout displayLayout = this.mDisplayLayout;
        return new Rect(0, 0, displayLayout.mWidth, displayLayout.mHeight);
    }

    public final DisplayLayout getDisplayLayout() {
        return new DisplayLayout(this.mDisplayLayout);
    }

    public final void rotateTo(int i) {
        this.mDisplayLayout.rotateTo(i, this.mContext.getResources());
    }
}
