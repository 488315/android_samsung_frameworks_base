package com.android.systemui.qs;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import com.android.systemui.Dependency;
import com.android.systemui.util.SettingsHelper;
import java.util.function.DoubleSupplier;

public class SecQuickQSPanel extends SecQSPanel {
    public boolean mDisabledByPolicy;
    public DoubleSupplier mMeasuredHeightSupplier;
    private final SettingsHelper mSettingHelper;

    public SecQuickQSPanel(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mSettingHelper = (SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class);
    }

    @Override // com.android.systemui.qs.SecQSPanel
    public final QSEvent closePanelEvent() {
        return QSEvent.QQS_PANEL_COLLAPSED;
    }

    @Override // com.android.systemui.qs.SecQSPanel
    public final String getDumpableTag() {
        return "SecQuickQSPanel";
    }

    @Override // com.android.systemui.qs.SecQSPanel, android.view.View
    public final void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.removeAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_COLLAPSE);
        accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_EXPAND);
    }

    @Override // android.widget.LinearLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (this.mMeasuredHeightSupplier != null) {
            setMeasuredDimension(View.MeasureSpec.getSize(i), View.MeasureSpec.getSize((int) this.mMeasuredHeightSupplier.getAsDouble()));
        }
    }

    @Override // com.android.systemui.qs.SecQSPanel
    public final QSEvent openPanelEvent() {
        return QSEvent.QQS_PANEL_EXPANDED;
    }

    @Override // com.android.systemui.qs.SecQSPanel, android.view.View
    public final void setVisibility(int i) {
        if (this.mDisabledByPolicy) {
            if (getVisibility() == 8) {
                return;
            } else {
                i = 8;
            }
        }
        super.setVisibility(i);
    }
}
