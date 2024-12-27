package com.android.systemui.qs;

import android.content.Context;
import android.util.AttributeSet;
import android.view.accessibility.AccessibilityNodeInfo;
import com.android.systemui.R;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public class QuickQSPanel extends QSPanel {
    public QuickQSPanel(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        getResources().getInteger(R.integer.quick_qs_panel_max_tiles);
    }

    @Override // com.android.systemui.qs.QSPanel, android.view.View
    public final void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.removeAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_COLLAPSE);
        accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_EXPAND);
    }

    @Override // com.android.systemui.qs.QSPanel, com.android.systemui.tuner.TunerService.Tunable
    public final void onTuningChanged(String str, String str2) {
        if ("qs_show_brightness".equals(str)) {
            super.onTuningChanged(str, "0");
        }
    }
}
