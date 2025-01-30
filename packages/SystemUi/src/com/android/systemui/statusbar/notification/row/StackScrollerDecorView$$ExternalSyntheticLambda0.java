package com.android.systemui.statusbar.notification.row;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class StackScrollerDecorView$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ StackScrollerDecorView f$0;

    public /* synthetic */ StackScrollerDecorView$$ExternalSyntheticLambda0(StackScrollerDecorView stackScrollerDecorView) {
        this.f$0 = stackScrollerDecorView;
    }

    @Override // java.lang.Runnable
    public final void run() {
        StackScrollerDecorView stackScrollerDecorView = this.f$0;
        stackScrollerDecorView.mContentAnimating = false;
        if (stackScrollerDecorView.getVisibility() == 8 || stackScrollerDecorView.mIsVisible) {
            return;
        }
        stackScrollerDecorView.setVisibility(8);
        stackScrollerDecorView.mWillBeGone = false;
        stackScrollerDecorView.notifyHeightChanged(false);
    }
}
