package com.android.systemui.controls.management.adapter;

import android.view.View;
import com.android.systemui.controls.management.model.PaddingWrapper;
import com.android.systemui.controls.management.model.StructureElementWrapper;

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
