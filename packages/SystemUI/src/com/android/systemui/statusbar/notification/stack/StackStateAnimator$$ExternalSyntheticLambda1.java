package com.android.systemui.statusbar.notification.stack;

import com.android.systemui.statusbar.notification.row.ExpandableView;

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
