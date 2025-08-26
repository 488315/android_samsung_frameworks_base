package com.android.wm.shell.freeform;

/* loaded from: classes3.dex */
public final /* synthetic */ class FreeformContainerViewController$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ FreeformContainerViewController f$0;

    public /* synthetic */ FreeformContainerViewController$$ExternalSyntheticLambda1(FreeformContainerViewController freeformContainerViewController, int i) {
        this.$r8$classId = i;
        this.f$0 = freeformContainerViewController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        FreeformContainerViewController freeformContainerViewController = this.f$0;
        switch (i) {
            case 0:
                FreeformContainerView freeformContainerView = freeformContainerViewController.mContainerView;
                if (freeformContainerView != null) {
                    freeformContainerView.setVisibility(8);
                    break;
                }
                break;
            default:
                FreeformContainerDismissButtonView freeformContainerDismissButtonView = freeformContainerViewController.mDismissButtonView;
                if (freeformContainerDismissButtonView != null) {
                    freeformContainerDismissButtonView.mDismissViewManager.cleanUpDismissTarget();
                    freeformContainerViewController.mDismissButtonView = null;
                    break;
                }
                break;
        }
    }
}
