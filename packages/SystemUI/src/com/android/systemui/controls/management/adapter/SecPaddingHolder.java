package com.android.systemui.controls.management.adapter;

import android.view.View;
import com.android.systemui.controls.management.model.SecElementWrapper;
import com.android.systemui.controls.management.model.VerticalPaddingWrapper;

public final class SecPaddingHolder extends SecHolder {
    public final View paddingView;

    public SecPaddingHolder(View view) {
        super(view, null);
        this.paddingView = this.itemView;
    }

    @Override // com.android.systemui.controls.management.adapter.SecHolder
    public final void bindData(SecElementWrapper secElementWrapper) {
        this.paddingView.getLayoutParams().height = ((VerticalPaddingWrapper) secElementWrapper).padding;
    }
}
