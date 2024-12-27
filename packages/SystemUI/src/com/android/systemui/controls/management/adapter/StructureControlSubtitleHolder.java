package com.android.systemui.controls.management.adapter;

import android.view.View;
import android.widget.TextView;
import com.android.systemui.R;
import com.android.systemui.controls.management.model.StructureElementWrapper;
import com.android.systemui.controls.management.model.SubtitleWrapper;

public final class StructureControlSubtitleHolder extends SecStructureViewHolder {
    public final TextView subtitleView;

    public StructureControlSubtitleHolder(View view) {
        super(view, null);
        this.subtitleView = (TextView) this.itemView.requireViewById(R.id.subtitle);
    }

    @Override // com.android.systemui.controls.management.adapter.SecStructureViewHolder
    public final void bindData(StructureElementWrapper structureElementWrapper) {
        this.subtitleView.setText(((SubtitleWrapper) structureElementWrapper).subtitle);
    }
}
