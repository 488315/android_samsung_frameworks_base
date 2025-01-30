package com.android.systemui.controls.management.adapter;

import android.view.View;
import android.widget.TextView;
import com.android.systemui.R;
import com.android.systemui.controls.management.model.StructureElementWrapper;
import com.android.systemui.controls.management.model.SubtitleWrapper;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class StructureControlSubtitleHolder extends CustomStructureHolder {
    public final TextView subtitleView;

    public StructureControlSubtitleHolder(View view) {
        super(view, null);
        this.subtitleView = (TextView) this.itemView.requireViewById(R.id.subtitle);
    }

    @Override // com.android.systemui.controls.management.adapter.CustomStructureHolder
    public final void bindData(StructureElementWrapper structureElementWrapper) {
        this.subtitleView.setText(((SubtitleWrapper) structureElementWrapper).subtitle);
    }
}
