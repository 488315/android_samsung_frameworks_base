package com.android.systemui.qs.panels.ui.viewmodel;

import androidx.recyclerview.widget.DiffUtil;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class DiffCallback extends DiffUtil.Callback {
    public final List currentList;
    public final List newList;

    public DiffCallback(List<? extends TileSpec> list, List<? extends TileSpec> list2) {
        this.currentList = list;
        this.newList = list2;
    }

    @Override // androidx.recyclerview.widget.DiffUtil.Callback
    public final boolean areContentsTheSame(int i, int i2) {
        return areItemsTheSame(i, i2);
    }

    @Override // androidx.recyclerview.widget.DiffUtil.Callback
    public final boolean areItemsTheSame(int i, int i2) {
        return Intrinsics.areEqual(this.currentList.get(i), this.newList.get(i2));
    }

    @Override // androidx.recyclerview.widget.DiffUtil.Callback
    public final int getNewListSize() {
        return this.newList.size();
    }

    @Override // androidx.recyclerview.widget.DiffUtil.Callback
    public final int getOldListSize() {
        return this.currentList.size();
    }
}
