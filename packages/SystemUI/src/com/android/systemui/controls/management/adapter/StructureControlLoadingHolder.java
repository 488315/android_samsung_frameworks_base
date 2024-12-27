package com.android.systemui.controls.management.adapter;

import android.view.View;
import android.widget.TextView;
import com.android.systemui.R;
import com.android.systemui.controls.management.model.LoadingWrapper;
import com.android.systemui.controls.management.model.StructureElementWrapper;

public final class StructureControlLoadingHolder extends SecStructureViewHolder {
    public final TextView subtitleView;

    public StructureControlLoadingHolder(View view) {
        super(view, null);
        this.subtitleView = (TextView) this.itemView.requireViewById(R.id.subtitle);
    }

    @Override // com.android.systemui.controls.management.adapter.SecStructureViewHolder
    public final void bindData(StructureElementWrapper structureElementWrapper) {
        this.subtitleView.setText(((LoadingWrapper) structureElementWrapper).subtitle);
    }
}
