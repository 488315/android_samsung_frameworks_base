package com.android.systemui.qs.tileimpl;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ChevronImageView extends ImageView {
    public ChevronImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public final boolean resolveLayoutDirection() {
        int layoutDirection = getLayoutDirection();
        boolean resolveLayoutDirection = super.resolveLayoutDirection();
        if (resolveLayoutDirection && getLayoutDirection() != layoutDirection) {
            onRtlPropertiesChanged(getLayoutDirection());
        }
        return resolveLayoutDirection;
    }
}
