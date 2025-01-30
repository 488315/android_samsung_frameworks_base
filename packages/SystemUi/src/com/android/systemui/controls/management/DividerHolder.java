package com.android.systemui.controls.management;

import android.view.View;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class DividerHolder extends Holder {
    public final View divider;
    public final View frame;

    public DividerHolder(View view) {
        super(view, null);
        this.frame = this.itemView.requireViewById(R.id.frame);
        this.divider = this.itemView.requireViewById(R.id.divider);
    }

    @Override // com.android.systemui.controls.management.Holder
    public final void bindData(ElementWrapper elementWrapper) {
        DividerWrapper dividerWrapper = (DividerWrapper) elementWrapper;
        this.frame.setVisibility(dividerWrapper.showNone ? 0 : 8);
        this.divider.setVisibility(dividerWrapper.showDivider ? 0 : 8);
    }
}
