package com.android.keyguard;

import android.content.Context;
import android.util.AttributeSet;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.LinearLayout;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.android.systemui.R;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class KeyguardUserSwitcherAnchor extends LinearLayout {
    public KeyguardUserSwitcherAnchor(Context context) {
        this(context, null, 2, 0 == true ? 1 : 0);
    }

    @Override // android.view.View
    public final AccessibilityNodeInfo createAccessibilityNodeInfo() {
        AccessibilityNodeInfo createAccessibilityNodeInfo = super.createAccessibilityNodeInfo();
        AccessibilityNodeInfoCompat wrap = AccessibilityNodeInfoCompat.wrap(createAccessibilityNodeInfo);
        wrap.mInfo.getExtras().putCharSequence("AccessibilityNodeInfo.roleDescription", getContext().getString(R.string.accessibility_multi_user_list_switcher));
        createAccessibilityNodeInfo.getClass();
        return createAccessibilityNodeInfo;
    }

    public /* synthetic */ KeyguardUserSwitcherAnchor(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : attributeSet);
    }

    public KeyguardUserSwitcherAnchor(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
