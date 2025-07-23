package com.android.systemui.qs.customize.viewcontroller;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class QSCMainViewController$onViewAttached$2 implements Runnable {
    public final /* synthetic */ QSCMainViewController this$0;

    public QSCMainViewController$onViewAttached$2(QSCMainViewController qSCMainViewController) {
        this.this$0 = qSCMainViewController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.this$0.getClass();
        if (QSCMainViewController.isLargeScreen$6()) {
            this.this$0.initResources();
            ViewControllerRepository viewControllerRepo = this.this$0.getViewControllerRepo();
            int cutoutTopMargin = this.this$0.getCutoutTopMargin();
            for (ViewControllerBase viewControllerBase : viewControllerRepo.viewControllers) {
                if (viewControllerBase != null) {
                    viewControllerBase.windowInsetChanged(cutoutTopMargin);
                }
            }
        }
    }
}
