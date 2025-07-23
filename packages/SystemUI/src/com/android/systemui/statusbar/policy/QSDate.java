package com.android.systemui.statusbar.policy;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;
import com.android.systemui.Dependency;
import com.android.systemui.statusbar.policy.QSClockBellTower;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class QSDate extends TextView implements QSClockBellTower.TimeAudience {
    public String mLastText;

    public QSDate(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 0, 0);
    }

    @Override // com.android.systemui.statusbar.policy.QSClockBellTower.TimeAudience
    public final String getTicket() {
        return getTag().toString();
    }

    @Override // com.android.systemui.statusbar.policy.QSClockBellTower.TimeAudience
    public void notifyTimeChanged(QSClockBellSound qSClockBellSound) {
        if (qSClockBellSound.DateText.equals(this.mLastText)) {
            return;
        }
        String str = qSClockBellSound.DateText;
        setText(str);
        this.mLastText = str;
    }

    @Override // android.widget.TextView, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        ((QSClockBellTower) Dependency.sDependency.getDependencyInner(QSClockBellTower.class)).registerAudience(this);
    }

    @Override // android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ((QSClockBellTower) Dependency.sDependency.getDependencyInner(QSClockBellTower.class)).unregisterAudience(this);
    }
}
