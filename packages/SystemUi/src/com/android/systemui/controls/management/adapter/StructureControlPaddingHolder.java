package com.android.systemui.controls.management.adapter;

import android.view.View;
import com.android.systemui.controls.management.model.PaddingWrapper;
import com.android.systemui.controls.management.model.StructureElementWrapper;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class StructureControlPaddingHolder extends CustomStructureHolder {
    public final View paddingView;

    public StructureControlPaddingHolder(View view) {
        super(view, null);
        this.paddingView = this.itemView;
    }

    @Override // com.android.systemui.controls.management.adapter.CustomStructureHolder
    public final void bindData(StructureElementWrapper structureElementWrapper) {
        this.paddingView.getLayoutParams().height = ((PaddingWrapper) structureElementWrapper).padding;
    }
}
