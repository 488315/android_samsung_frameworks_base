package com.android.systemui.facewidget.plugin;

import android.content.Context;
import android.content.res.Resources;
import com.android.systemui.R;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.plugins.keyguardstatusview.PluginSecKeyguardClockPositionAlgorithm;
import com.android.systemui.statusbar.phone.KeyguardClockPositionAlgorithm;
import java.util.ArrayList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class FaceWidgetPositionAlgorithmWrapper extends KeyguardClockPositionAlgorithm {
    public int mLockscreenNotifPadding;
    public PluginSecKeyguardClockPositionAlgorithm mPositionAlgorithm;
    public int mSplitShadeTargetTopMargin;

    public FaceWidgetPositionAlgorithmWrapper(LogBuffer logBuffer) {
        super(logBuffer);
    }

    @Override // com.android.systemui.statusbar.phone.KeyguardClockPositionAlgorithm
    public final int getLockscreenNotifPadding() {
        PluginSecKeyguardClockPositionAlgorithm pluginSecKeyguardClockPositionAlgorithm = this.mPositionAlgorithm;
        if (pluginSecKeyguardClockPositionAlgorithm == null) {
            int i = this.mLockscreenNotifPadding;
            return i != 0 ? i : this.mSplitShadeTargetTopMargin;
        }
        int minStackScrollerPadding = (int) (pluginSecKeyguardClockPositionAlgorithm.getMinStackScrollerPadding() - 0.0f);
        this.mLockscreenNotifPadding = minStackScrollerPadding;
        return minStackScrollerPadding;
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
        if (resources != null) {
            this.mSplitShadeTargetTopMargin = resources.getDimensionPixelSize(R.dimen.keyguard_split_shade_top_margin);
        }
    }

    @Override // com.android.systemui.statusbar.phone.KeyguardClockPositionAlgorithm
    public final void run(KeyguardClockPositionAlgorithm.Result result) {
        super.run(result);
        if (this.mPositionAlgorithm != null) {
            ArrayList<Object> arrayList = new ArrayList<>();
            try {
                this.mPositionAlgorithm.run(arrayList);
            } catch (Throwable th) {
                th.printStackTrace();
            }
            if (arrayList.isEmpty()) {
                return;
            }
            result.stackScrollerPadding = ((Integer) arrayList.getFirst()).intValue();
        }
    }

    @Override // com.android.systemui.statusbar.phone.KeyguardClockPositionAlgorithm
    public final void setup(float f, float f2, int i, boolean z) {
        super.setup(f, f2, i, z);
        PluginSecKeyguardClockPositionAlgorithm pluginSecKeyguardClockPositionAlgorithm = this.mPositionAlgorithm;
        if (pluginSecKeyguardClockPositionAlgorithm != null) {
            pluginSecKeyguardClockPositionAlgorithm.setup(f, f2, z, i, false);
        }
    }
}
