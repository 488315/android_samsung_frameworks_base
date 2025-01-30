package com.android.systemui.statusbar.policy;

import android.content.Context;
import android.util.AttributeSet;
import com.android.systemui.Dependency;
import com.android.systemui.shared.shadow.DoubleShadowTextView;
import com.android.systemui.statusbar.policy.QSClockBellTower;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class QSDate extends DoubleShadowTextView implements QSClockBellTower.TimeAudience {
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
        ((QSClockBellTower) Dependency.get(QSClockBellTower.class)).registerAudience(this);
    }

    @Override // android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ((QSClockBellTower) Dependency.get(QSClockBellTower.class)).unregisterAudience(this);
    }
}
