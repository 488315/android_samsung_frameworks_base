package com.android.systemui.controls.management.adapter;

import android.view.View;
import com.android.systemui.controls.management.model.PaddingWrapper;
import com.android.systemui.controls.management.model.StructureElementWrapper;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
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
