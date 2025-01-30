package com.android.systemui.controls.management.adapter;

import android.view.View;
import com.android.systemui.controls.management.model.CustomElementWrapper;
import com.android.systemui.controls.management.model.VerticalPaddingWrapper;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class PaddingCustomHolder extends CustomHolder {
    public final View paddingView;

    public PaddingCustomHolder(View view) {
        super(view, null);
        this.paddingView = this.itemView;
    }

    @Override // com.android.systemui.controls.management.adapter.CustomHolder
    public final void bindData(CustomElementWrapper customElementWrapper) {
        this.paddingView.getLayoutParams().height = ((VerticalPaddingWrapper) customElementWrapper).padding;
    }
}
