package com.android.wm.shell.windowdecor;

import android.R;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public final class MarqueedTextView extends TextView {
    public MarqueedTextView(Context context) {
        this(context, null, 0, 6, null);
    }

    @Override // android.view.View
    public final boolean isSelected() {
        return true;
    }

    public final void startMarquee() {
        super.startMarquee();
    }

    public MarqueedTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
    }

    public /* synthetic */ MarqueedTextView(Context context, AttributeSet attributeSet, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? null : attributeSet, (i2 & 4) != 0 ? R.attr.textViewStyle : i);
    }

    public MarqueedTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }
}
