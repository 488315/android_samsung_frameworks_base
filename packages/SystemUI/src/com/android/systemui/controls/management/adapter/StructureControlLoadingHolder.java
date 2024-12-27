package com.android.systemui.controls.management.adapter;

import android.view.View;
import android.widget.TextView;
import com.android.systemui.R;
import com.android.systemui.controls.management.model.LoadingWrapper;
import com.android.systemui.controls.management.model.StructureElementWrapper;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
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
