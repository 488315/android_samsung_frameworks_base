package com.google.android.material.appbar;

import android.view.View;
import androidx.core.view.ViewCompat;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ViewOffsetHelper {
    public int layoutLeft;
    public int layoutTop;
    public int offsetLeft;
    public int offsetTop;
    public final View view;
    public final boolean verticalOffsetEnabled = true;
    public final boolean horizontalOffsetEnabled = true;

    public ViewOffsetHelper(View view) {
        this.view = view;
    }

    public final void applyOffsets() {
        int i = this.offsetTop;
        View view = this.view;
        int top = i - (view.getTop() - this.layoutTop);
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        view.offsetTopAndBottom(top);
        view.offsetLeftAndRight(this.offsetLeft - (view.getLeft() - this.layoutLeft));
    }

    public final boolean setTopAndBottomOffset(int i) {
        if (!this.verticalOffsetEnabled || this.offsetTop == i) {
            return false;
        }
        this.offsetTop = i;
        applyOffsets();
        return true;
    }
}
