package com.android.systemui.statusbar.notification.stack;

import com.android.systemui.statusbar.notification.row.ExpandableView;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class StackStateAnimator$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ExpandableView f$0;

    public /* synthetic */ StackStateAnimator$$ExternalSyntheticLambda1(ExpandableView expandableView, int i) {
        this.$r8$classId = i;
        this.f$0 = expandableView;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        ExpandableView expandableView = this.f$0;
        switch (i) {
            case 0:
                expandableView.mInRemovalAnimation = true;
                break;
            case 1:
                expandableView.removeFromTransientContainer();
                break;
            case 2:
                expandableView.mInRemovalAnimation = true;
                break;
            default:
                expandableView.mInRemovalAnimation = true;
                break;
        }
    }
}
