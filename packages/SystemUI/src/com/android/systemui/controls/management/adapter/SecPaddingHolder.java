package com.android.systemui.controls.management.adapter;

import android.view.View;
import com.android.systemui.controls.management.model.SecElementWrapper;
import com.android.systemui.controls.management.model.VerticalPaddingWrapper;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
