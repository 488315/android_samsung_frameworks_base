package com.android.systemui.statusbar.notification.stack;

import com.android.systemui.statusbar.notification.row.ExpandableView;

public final /* synthetic */ class StackStateAnimator$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ StackStateAnimator f$0;
    public final /* synthetic */ String f$1;
    public final /* synthetic */ Object f$2;
    public final /* synthetic */ boolean f$3;
    public final /* synthetic */ ExpandableView f$4;

    public /* synthetic */ StackStateAnimator$$ExternalSyntheticLambda3(StackStateAnimator stackStateAnimator, String str, String str2, boolean z, ExpandableView expandableView) {
        this.f$0 = stackStateAnimator;
        this.f$1 = str;
        this.f$2 = str2;
        this.f$3 = z;
        this.f$4 = expandableView;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                StackStateAnimator stackStateAnimator = this.f$0;
                String str = this.f$1;
                String str2 = (String) this.f$2;
                boolean z = this.f$3;
                ExpandableView expandableView = this.f$4;
                stackStateAnimator.mLogger.animationStart(str, str2, z);
                expandableView.mInRemovalAnimation = true;
                break;
            default:
                StackStateAnimator stackStateAnimator2 = this.f$0;
                String str3 = this.f$1;
                boolean z2 = this.f$3;
                ExpandableView expandableView2 = this.f$4;
                Runnable runnable = (Runnable) this.f$2;
                stackStateAnimator2.mLogger.animationEnd(str3, "ANIMATION_TYPE_HEADS_UP_CYCLING_OUT", z2);
                expandableView2.mInRemovalAnimation = false;
                if (runnable != null) {
                    runnable.run();
                    break;
                }
                break;
        }
    }

    public /* synthetic */ StackStateAnimator$$ExternalSyntheticLambda3(StackStateAnimator stackStateAnimator, String str, boolean z, ExpandableView expandableView, StackStateAnimator$$ExternalSyntheticLambda1 stackStateAnimator$$ExternalSyntheticLambda1) {
        this.f$0 = stackStateAnimator;
        this.f$1 = str;
        this.f$3 = z;
        this.f$4 = expandableView;
        this.f$2 = stackStateAnimator$$ExternalSyntheticLambda1;
    }
}
