package com.android.systemui.statusbar.policy;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;
import com.android.systemui.Dependency;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.policy.QSClockBellTower;
import com.android.systemui.util.DeviceType;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public abstract class QSClock extends TextView implements CommandQueue.Callbacks, QSClockBellTower.TimeAudience {
    public boolean mClockVisibleByPolicy;
    public String mViewTag;

    static {
        DeviceType.isEngOrUTBinary();
    }

    public QSClock(Context context) {
        this(context, null);
    }

    public boolean calculateVisibility() {
        return this.mClockVisibleByPolicy;
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void disable(int i, int i2, int i3, boolean z) {
        if (i != getDisplay().getDisplayId()) {
            return;
        }
        boolean z2 = (8388608 & i2) == 0;
        boolean z3 = this.mClockVisibleByPolicy;
        if (z2 == z3 || z3 == z2) {
            return;
        }
        this.mClockVisibleByPolicy = z2;
        setVisibility(calculateVisibility() ? 0 : 8);
    }

    @Override // com.android.systemui.statusbar.policy.QSClockBellTower.TimeAudience
    public final String getTicket() {
        return this.mViewTag;
    }

    @Override // com.android.systemui.statusbar.policy.QSClockBellTower.TimeAudience
    public void notifyTimeChanged(QSClockBellSound qSClockBellSound) {
        if (qSClockBellSound.Demo) {
            return;
        }
        setText(qSClockBellSound.TimeText);
        setContentDescription(qSClockBellSound.TimeContentDescription);
    }

    @Override // android.widget.TextView, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (getTag() != null) {
            this.mViewTag = getTag().toString();
        }
        ((CommandQueue) Dependency.sDependency.getDependencyInner(CommandQueue.class)).addCallback((CommandQueue.Callbacks) this);
        ((QSClockBellTower) Dependency.sDependency.getDependencyInner(QSClockBellTower.class)).registerAudience(this);
    }

    @Override // android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ((CommandQueue) Dependency.sDependency.getDependencyInner(CommandQueue.class)).removeCallback((CommandQueue.Callbacks) this);
        ((QSClockBellTower) Dependency.sDependency.getDependencyInner(QSClockBellTower.class)).unregisterAudience(this);
    }

    @Override // android.view.View
    public void setVisibility(int i) {
        if (!calculateVisibility()) {
            i = 8;
        }
        super.setVisibility(i);
    }

    public QSClock(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public QSClock(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i, 0);
        this.mClockVisibleByPolicy = true;
        this.mViewTag = "QSClock";
    }

    public QSClock(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mClockVisibleByPolicy = true;
        this.mViewTag = "QSClock";
    }
}
