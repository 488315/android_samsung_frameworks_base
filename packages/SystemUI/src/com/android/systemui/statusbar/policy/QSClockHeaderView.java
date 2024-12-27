package com.android.systemui.statusbar.policy;

import android.content.Context;
import android.util.AttributeSet;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class QSClockHeaderView extends QSClock {
    public QSClockHeaderView(Context context) {
        super(context);
    }

    @Override // com.android.systemui.statusbar.policy.QSClock, com.android.systemui.statusbar.policy.QSClockBellTower.TimeAudience
    public final void notifyTimeChanged(QSClockBellSound qSClockBellSound) {
        if (!qSClockBellSound.ShowSecondsClock) {
            super.notifyTimeChanged(qSClockBellSound);
            setText(getText());
        } else {
            if (qSClockBellSound.Demo) {
                return;
            }
            setText(qSClockBellSound.TimeTextWithSeconds);
            setContentDescription(qSClockBellSound.TimeContentDescription);
        }
    }

    public QSClockHeaderView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public QSClockHeaderView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }
}
