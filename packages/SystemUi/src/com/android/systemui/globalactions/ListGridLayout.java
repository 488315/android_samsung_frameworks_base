package com.android.systemui.globalactions;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class ListGridLayout extends LinearLayout {
    public final int[][] mConfigs;
    public int mCurrentCount;
    public int mExpectedCount;
    public boolean mReverseItems;
    public boolean mReverseSublists;
    public boolean mSwapRowsAndColumns;

    public ListGridLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mCurrentCount = 0;
        this.mConfigs = new int[][]{new int[]{0, 0}, new int[]{1, 1}, new int[]{1, 2}, new int[]{1, 3}, new int[]{2, 2}, new int[]{2, 3}, new int[]{2, 3}, new int[]{3, 3}, new int[]{3, 3}, new int[]{3, 3}};
    }

    public ViewGroup getParentView(int i, boolean z, boolean z2) {
        if (getRowCount() == 0 || i < 0) {
            return null;
        }
        int floor = z2 ? (int) Math.floor(r3 / r0) : Math.min(i, (this.mConfigs.length - 1) - 1) % getRowCount();
        if (z) {
            floor = getChildCount() - (floor + 1);
        }
        return getSublist(floor);
    }

    public final int getRowCount() {
        int[] iArr;
        int i = this.mExpectedCount;
        if (i < 0) {
            iArr = this.mConfigs[0];
        } else {
            iArr = this.mConfigs[Math.min(this.mConfigs.length - 1, i)];
        }
        return iArr[0];
    }

    public ViewGroup getSublist(int i) {
        return (ViewGroup) getChildAt(i);
    }
}
