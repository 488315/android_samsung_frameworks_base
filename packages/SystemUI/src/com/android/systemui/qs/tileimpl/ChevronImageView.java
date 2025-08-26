package com.android.systemui.qs.tileimpl;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/* loaded from: classes2.dex */
public final class ChevronImageView extends ImageView {
    public ChevronImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public final boolean resolveLayoutDirection() {
        int layoutDirection = getLayoutDirection();
        boolean zResolveLayoutDirection = super.resolveLayoutDirection();
        if (zResolveLayoutDirection && getLayoutDirection() != layoutDirection) {
            onRtlPropertiesChanged(getLayoutDirection());
        }
        return zResolveLayoutDirection;
    }
}
