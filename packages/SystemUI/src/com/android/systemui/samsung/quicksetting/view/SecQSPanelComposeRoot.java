package com.android.systemui.samsung.quicksetting.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class SecQSPanelComposeRoot extends FrameLayout {
    public SecQSPanelComposeRoot(Context context) {
        this(context, null, 0, 6, null);
    }

    public SecQSPanelComposeRoot(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
    }

    public /* synthetic */ SecQSPanelComposeRoot(Context context, AttributeSet attributeSet, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? null : attributeSet, (i2 & 4) != 0 ? 0 : i);
    }

    public SecQSPanelComposeRoot(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }
}
