package com.android.wm.shell.freeform;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class FreeformContainerViewController$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ FreeformContainerViewController f$0;

    public /* synthetic */ FreeformContainerViewController$$ExternalSyntheticLambda1(FreeformContainerViewController freeformContainerViewController, int i) {
        this.$r8$classId = i;
        this.f$0 = freeformContainerViewController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                FreeformContainerView freeformContainerView = this.f$0.mContainerView;
                if (freeformContainerView != null) {
                    freeformContainerView.setVisibility(8);
                    break;
                }
                break;
            default:
                FreeformContainerViewController freeformContainerViewController = this.f$0;
                FreeformContainerDismissButtonView freeformContainerDismissButtonView = freeformContainerViewController.mDismissButtonView;
                if (freeformContainerDismissButtonView != null) {
                    freeformContainerDismissButtonView.mDismissButtonManager.cleanUpDismissTarget();
                    freeformContainerViewController.mDismissButtonView = null;
                    break;
                }
                break;
        }
    }
}
