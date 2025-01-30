package com.android.systemui.statusbar.policy;

import android.content.Context;
import android.util.AttributeSet;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class QSClockPanelView extends QSClock {
    public QSClockPanelView(Context context) {
        super(context);
    }

    @Override // com.android.systemui.statusbar.policy.QSClock, com.android.systemui.statusbar.policy.QSClockBellTower.TimeAudience
    public final void notifyTimeChanged(QSClockBellSound qSClockBellSound) {
        if (!qSClockBellSound.ShowSecondsClock) {
            super.notifyTimeChanged(qSClockBellSound);
        } else {
            if (qSClockBellSound.Demo) {
                return;
            }
            setText(qSClockBellSound.TimeTextWithSeconds);
            setContentDescription(qSClockBellSound.TimeContentDescription);
        }
    }

    public QSClockPanelView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public QSClockPanelView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }
}
