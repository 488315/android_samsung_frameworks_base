package com.android.systemui.controls.management.adapter;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.controls.management.model.StructureElementWrapper;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public abstract class SecStructureViewHolder extends RecyclerView.ViewHolder {
    public /* synthetic */ SecStructureViewHolder(View view, DefaultConstructorMarker defaultConstructorMarker) {
        this(view);
    }

    public abstract void bindData(StructureElementWrapper structureElementWrapper);

    private SecStructureViewHolder(View view) {
        super(view);
    }
}
