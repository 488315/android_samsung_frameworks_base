package com.google.android.setupdesign.items;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.setupdesign.DividerItemDecoration;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class ItemViewHolder extends RecyclerView.ViewHolder implements DividerItemDecoration.DividedViewHolder {
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
