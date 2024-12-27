package com.android.systemui.util;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public class SafeMarqueeTextView extends TextView {
    public static final int $stable = 8;
    private boolean safelyIgnoreLayout;

    public SafeMarqueeTextView(Context context) {
        this(context, null, 0, 0, 14, null);
    }

    private final boolean getHasStableWidth() {
        return getLayoutParams().width != -2;
    }

    @Override // android.view.View
    public void requestLayout() {
        if (this.safelyIgnoreLayout) {
            return;
        }
        super.requestLayout();
    }

    public void startMarquee() {
        boolean z = this.safelyIgnoreLayout;
        this.safelyIgnoreLayout = getHasStableWidth();
        super.startMarquee();
        this.safelyIgnoreLayout = z;
    }

    public void stopMarquee() {
        boolean z = this.safelyIgnoreLayout;
        this.safelyIgnoreLayout = getHasStableWidth();
        super.stopMarquee();
        this.safelyIgnoreLayout = z;
    }

    public SafeMarqueeTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 0, 12, null);
    }

    public SafeMarqueeTextView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0, 8, null);
    }

    public /* synthetic */ SafeMarqueeTextView(Context context, AttributeSet attributeSet, int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i3 & 2) != 0 ? null : attributeSet, (i3 & 4) != 0 ? 0 : i, (i3 & 8) != 0 ? 0 : i2);
    }

    public SafeMarqueeTextView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }
}
