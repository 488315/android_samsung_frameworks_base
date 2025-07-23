package com.android.systemui.statusbar.policy;

import android.content.Context;
import android.util.AttributeSet;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
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
