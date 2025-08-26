package com.android.systemui.statusbar.policy;

import android.content.Context;
import android.util.AttributeSet;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public final class QSClockPopOverImmersiveView extends QSClock {
    public QSClockPopOverImmersiveView(Context context) {
        this(context, null, 0, 6, null);
    }

    public QSClockPopOverImmersiveView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
    }

    public /* synthetic */ QSClockPopOverImmersiveView(Context context, AttributeSet attributeSet, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? null : attributeSet, (i2 & 4) != 0 ? 0 : i);
    }

    public QSClockPopOverImmersiveView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }
}
