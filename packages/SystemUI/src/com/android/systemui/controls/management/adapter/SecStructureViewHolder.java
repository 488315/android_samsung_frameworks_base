package com.android.systemui.controls.management.adapter;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.controls.management.model.StructureElementWrapper;
import kotlin.jvm.internal.DefaultConstructorMarker;

public abstract class SecStructureViewHolder extends RecyclerView.ViewHolder {
    public /* synthetic */ SecStructureViewHolder(View view, DefaultConstructorMarker defaultConstructorMarker) {
        this(view);
    }

    public abstract void bindData(StructureElementWrapper structureElementWrapper);

    private SecStructureViewHolder(View view) {
        super(view);
    }
}
