package com.google.android.material.appbar;

import android.view.View;
import androidx.core.view.ViewCompat;
import java.util.WeakHashMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class ViewOffsetHelper {
    public int layoutLeft;
    public int layoutTop;
    public int offsetTop;
    public final View view;

    public ViewOffsetHelper(View view) {
        this.view = view;
    }

    public final void applyOffsets() {
        View view = this.view;
        int top = this.offsetTop - (view.getTop() - this.layoutTop);
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        view.offsetTopAndBottom(top);
        View view2 = this.view;
        view2.offsetLeftAndRight(0 - (view2.getLeft() - this.layoutLeft));
    }

    public final boolean setTopAndBottomOffset(int i) {
        if (this.offsetTop == i) {
            return false;
        }
        this.offsetTop = i;
        applyOffsets();
        return true;
    }
}
