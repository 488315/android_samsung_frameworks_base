package com.android.systemui.p016qs;

import android.content.Context;
import android.util.AttributeSet;
import android.view.accessibility.AccessibilityNodeInfo;
import com.android.systemui.R;
import com.android.systemui.plugins.p013qs.QSTile;
import com.sec.ims.configuration.DATA;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class QuickQSPanel extends QSPanel {
    public QuickQSPanel(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        getResources().getInteger(R.integer.quick_qs_panel_max_tiles);
    }

    @Override // com.android.systemui.p016qs.QSPanel
    public final void drawTile(QSPanelControllerBase$TileRecord qSPanelControllerBase$TileRecord, QSTile.State state) {
        if (state instanceof QSTile.SignalState) {
            QSTile.SignalState signalState = new QSTile.SignalState();
            state.copyTo(signalState);
            signalState.activityIn = false;
            signalState.activityOut = false;
            state = signalState;
        }
        qSPanelControllerBase$TileRecord.tileView.onStateChanged(state);
    }

    @Override // com.android.systemui.p016qs.QSPanel, android.view.View
    public final void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.removeAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_COLLAPSE);
        accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_EXPAND);
    }

    @Override // com.android.systemui.p016qs.QSPanel, com.android.systemui.tuner.TunerService.Tunable
    public final void onTuningChanged(String str, String str2) {
        if ("qs_show_brightness".equals(str)) {
            super.onTuningChanged(str, DATA.DM_FIELD_INDEX.PCSCF_DOMAIN);
        }
    }

    @Override // android.view.View
    public final void setVisibility(int i) {
        super.setVisibility(i);
    }
}
