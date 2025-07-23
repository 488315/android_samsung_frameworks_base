package com.android.systemui.controls.management.adapter;

import android.view.View;
import android.widget.TextView;
import com.android.systemui.R;
import com.android.systemui.controls.management.model.LoadingWrapper;
import com.android.systemui.controls.management.model.StructureElementWrapper;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
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
