package com.android.systemui.controls.management.adapter;

import android.view.View;
import com.android.systemui.controls.management.model.PaddingWrapper;
import com.android.systemui.controls.management.model.StructureElementWrapper;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class StructureControlPaddingHolder extends SecStructureViewHolder {
    public final View paddingView;

    public StructureControlPaddingHolder(View view) {
        super(view, null);
        this.paddingView = this.itemView;
    }

    @Override // com.android.systemui.controls.management.adapter.SecStructureViewHolder
    public final void bindData(StructureElementWrapper structureElementWrapper) {
        this.paddingView.getLayoutParams().height = ((PaddingWrapper) structureElementWrapper).padding;
    }
}
