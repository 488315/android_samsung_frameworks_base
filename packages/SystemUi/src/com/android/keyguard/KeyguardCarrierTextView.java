package com.android.keyguard;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.android.systemui.Dependency;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class KeyguardCarrierTextView extends FrameLayout {
    public KeyguardCarrierTextView(Context context) {
        this(context, null, 0);
    }

    public final void updateVisibility() {
        setVisibility(((KeyguardUpdateMonitor) Dependency.get(KeyguardUpdateMonitor.class)).isForcedLock() ? 0 : 8);
    }

    public KeyguardCarrierTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public KeyguardCarrierTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }
}
