package com.android.systemui.statusbar.policy;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;
import com.android.systemui.Dependency;
import com.android.systemui.statusbar.policy.QSClockBellTower;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
