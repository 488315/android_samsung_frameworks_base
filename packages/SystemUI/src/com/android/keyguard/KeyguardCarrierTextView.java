package com.android.keyguard;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.android.systemui.Dependency;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class KeyguardCarrierTextView extends FrameLayout {
    public KeyguardCarrierTextView(Context context) {
        this(context, null, 0);
    }

    public final void updateVisibility() {
        setVisibility(((KeyguardUpdateMonitor) Dependency.sDependency.getDependencyInner(KeyguardUpdateMonitor.class)).isForcedLock() ? 0 : 8);
    }

    public KeyguardCarrierTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public KeyguardCarrierTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }
}
