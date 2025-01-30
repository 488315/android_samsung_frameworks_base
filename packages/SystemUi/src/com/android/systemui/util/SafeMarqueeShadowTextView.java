package com.android.systemui.util;

import android.content.Context;
import android.util.AttributeSet;
import com.android.systemui.shared.shadow.DoubleShadowTextView;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class SafeMarqueeShadowTextView extends DoubleShadowTextView {
    public boolean safelyIgnoreLayout;

    public SafeMarqueeShadowTextView(Context context) {
        this(context, null, 0, 0, 14, null);
    }

    @Override // android.view.View
    public final void requestLayout() {
        if (this.safelyIgnoreLayout) {
            return;
        }
        super.requestLayout();
    }

    public final void startMarquee() {
        boolean z = this.safelyIgnoreLayout;
        this.safelyIgnoreLayout = getLayoutParams().width != -2;
        super.startMarquee();
        this.safelyIgnoreLayout = z;
    }

    public final void stopMarquee() {
        boolean z = this.safelyIgnoreLayout;
        this.safelyIgnoreLayout = getLayoutParams().width != -2;
        super.stopMarquee();
        this.safelyIgnoreLayout = z;
    }

    public SafeMarqueeShadowTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 0, 12, null);
    }

    public SafeMarqueeShadowTextView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0, 8, null);
    }

    public /* synthetic */ SafeMarqueeShadowTextView(Context context, AttributeSet attributeSet, int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i3 & 2) != 0 ? null : attributeSet, (i3 & 4) != 0 ? 0 : i, (i3 & 8) != 0 ? 0 : i2);
    }

    public SafeMarqueeShadowTextView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }
}
