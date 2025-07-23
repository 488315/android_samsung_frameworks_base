package com.android.systemui.controls.management.adapter;

import android.view.View;
import com.android.systemui.controls.management.model.SecElementWrapper;
import com.android.systemui.controls.management.model.VerticalPaddingWrapper;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
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
