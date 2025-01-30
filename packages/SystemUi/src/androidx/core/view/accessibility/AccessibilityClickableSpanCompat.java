package androidx.core.view.accessibility;

import android.os.Bundle;
import android.text.style.ClickableSpan;
import android.view.View;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class AccessibilityClickableSpanCompat extends ClickableSpan {
    public final int mClickableSpanActionId;
    public final AccessibilityNodeInfoCompat mNodeInfoCompat;
    public final int mOriginalClickableSpanId;

    public AccessibilityClickableSpanCompat(int i, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat, int i2) {
        this.mOriginalClickableSpanId = i;
        this.mNodeInfoCompat = accessibilityNodeInfoCompat;
        this.mClickableSpanActionId = i2;
    }

    @Override // android.text.style.ClickableSpan
    public final void onClick(View view) {
        Bundle bundle = new Bundle();
        bundle.putInt("ACCESSIBILITY_CLICKABLE_SPAN_ID", this.mOriginalClickableSpanId);
        AccessibilityNodeInfoCompat accessibilityNodeInfoCompat = this.mNodeInfoCompat;
        accessibilityNodeInfoCompat.mInfo.performAction(this.mClickableSpanActionId, bundle);
    }
}
