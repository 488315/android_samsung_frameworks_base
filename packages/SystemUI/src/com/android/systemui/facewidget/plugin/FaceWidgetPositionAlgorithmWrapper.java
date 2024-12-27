package com.android.systemui.facewidget.plugin;

import android.content.Context;
import android.content.res.Resources;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.plugins.keyguardstatusview.PluginSecKeyguardClockPositionAlgorithm;
import com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.phone.KeyguardClockPositionAlgorithm;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class FaceWidgetPositionAlgorithmWrapper extends KeyguardClockPositionAlgorithm {
    public PluginSecKeyguardClockPositionAlgorithm mPositionAlgorithm;

    public FaceWidgetPositionAlgorithmWrapper(LogBuffer logBuffer) {
        super(logBuffer);
    }

    @Override // com.android.systemui.statusbar.phone.KeyguardClockPositionAlgorithm
    public final int getBottomMarginY() {
        PluginSecKeyguardClockPositionAlgorithm pluginSecKeyguardClockPositionAlgorithm = this.mPositionAlgorithm;
        if (pluginSecKeyguardClockPositionAlgorithm != null) {
            return pluginSecKeyguardClockPositionAlgorithm.getBottomMarginY();
        }
        return 0;
    }

    @Override // com.android.systemui.statusbar.phone.KeyguardClockPositionAlgorithm
    public final float getLockscreenNotifPadding(float f) {
        PluginSecKeyguardClockPositionAlgorithm pluginSecKeyguardClockPositionAlgorithm = this.mPositionAlgorithm;
        if (pluginSecKeyguardClockPositionAlgorithm != null) {
            return pluginSecKeyguardClockPositionAlgorithm.getMinStackScrollerPadding() - f;
        }
        return 0.0f;
    }

    @Override // com.android.systemui.statusbar.phone.KeyguardClockPositionAlgorithm
    public final boolean isPanelExpanded() {
        PluginSecKeyguardClockPositionAlgorithm pluginSecKeyguardClockPositionAlgorithm = this.mPositionAlgorithm;
        if (pluginSecKeyguardClockPositionAlgorithm != null) {
            return pluginSecKeyguardClockPositionAlgorithm.isPanelExpanded();
        }
        return false;
    }

    @Override // com.android.systemui.statusbar.phone.KeyguardClockPositionAlgorithm
    public final void loadDimens(Context context, Resources resources) {
        PluginSecKeyguardClockPositionAlgorithm pluginSecKeyguardClockPositionAlgorithm = this.mPositionAlgorithm;
        if (pluginSecKeyguardClockPositionAlgorithm != null) {
            pluginSecKeyguardClockPositionAlgorithm.loadDimens();
        }
    }

    @Override // com.android.systemui.statusbar.phone.KeyguardClockPositionAlgorithm
    public final void run(KeyguardClockPositionAlgorithm.Result result) {
        if (this.mPositionAlgorithm != null) {
            ArrayList<Object> arrayList = new ArrayList<>();
            this.mPositionAlgorithm.run(arrayList);
            if (arrayList.size() >= 6) {
                result.clockX = ((Integer) arrayList.get(0)).intValue();
                result.clockY = ((Integer) arrayList.get(1)).intValue();
                result.clockAlpha = ((Float) arrayList.get(2)).floatValue();
                result.stackScrollerPadding = ((Integer) arrayList.get(3)).intValue();
                ((Integer) arrayList.get(5)).intValue();
                result.contentsContainerPosition = (List) arrayList.get(6);
            }
        }
    }

    @Override // com.android.systemui.statusbar.phone.KeyguardClockPositionAlgorithm
    public final void setup(int i, float f, int i2, int i3, int i4, float f2, int i5, int i6, int i7, NotificationPanelViewController$$ExternalSyntheticLambda0 notificationPanelViewController$$ExternalSyntheticLambda0) {
        PluginSecKeyguardClockPositionAlgorithm pluginSecKeyguardClockPositionAlgorithm = this.mPositionAlgorithm;
        if (pluginSecKeyguardClockPositionAlgorithm != null) {
            pluginSecKeyguardClockPositionAlgorithm.setup(i, i3, i4, f, i5, i2, i4, false, false, f2, 0.0f, false, i5, i6, i7, notificationPanelViewController$$ExternalSyntheticLambda0);
        }
    }
}
