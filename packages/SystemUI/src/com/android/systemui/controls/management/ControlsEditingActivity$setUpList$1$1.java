package com.android.systemui.controls.management;

import android.content.Context;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ControlsEditingActivity$setUpList$1$1 extends GridLayoutManager {
    public ControlsEditingActivity$setUpList$1$1(int i, Context context) {
        super(context, i);
    }

    @Override // androidx.recyclerview.widget.GridLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final int getRowCountForAccessibility(RecyclerView.Recycler recycler, RecyclerView.State state) {
        int rowCountForAccessibility = super.getRowCountForAccessibility(recycler, state);
        return rowCountForAccessibility > 0 ? rowCountForAccessibility - 1 : rowCountForAccessibility;
    }
}
