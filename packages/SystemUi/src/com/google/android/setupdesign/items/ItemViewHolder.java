package com.google.android.setupdesign.items;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.setupdesign.DividerItemDecoration;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ItemViewHolder extends RecyclerView.ViewHolder implements DividerItemDecoration.DividedViewHolder {
    public boolean isEnabled;
    public AbstractItem item;

    public ItemViewHolder(View view) {
        super(view);
    }

    @Override // com.google.android.setupdesign.DividerItemDecoration.DividedViewHolder
    public final boolean isDividerAllowedAbove() {
        return this.isEnabled;
    }

    @Override // com.google.android.setupdesign.DividerItemDecoration.DividedViewHolder
    public final boolean isDividerAllowedBelow() {
        return this.isEnabled;
    }
}
