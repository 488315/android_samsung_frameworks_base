package com.android.keyguard;

import android.content.Context;
import android.util.AttributeSet;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.LinearLayout;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.android.systemui.R;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class KeyguardUserSwitcherAnchor extends LinearLayout {
    public KeyguardUserSwitcherAnchor(Context context) {
        this(context, null, 2, 0 == true ? 1 : 0);
    }

    @Override // android.view.View
    public final AccessibilityNodeInfo createAccessibilityNodeInfo() {
        AccessibilityNodeInfo createAccessibilityNodeInfo = super.createAccessibilityNodeInfo();
        AccessibilityNodeInfoCompat wrap = AccessibilityNodeInfoCompat.wrap(createAccessibilityNodeInfo);
        wrap.mInfo.getExtras().putCharSequence("AccessibilityNodeInfo.roleDescription", getContext().getString(R.string.accessibility_multi_user_list_switcher));
        Intrinsics.checkNotNull(createAccessibilityNodeInfo);
        return createAccessibilityNodeInfo;
    }

    public /* synthetic */ KeyguardUserSwitcherAnchor(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : attributeSet);
    }

    public KeyguardUserSwitcherAnchor(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
