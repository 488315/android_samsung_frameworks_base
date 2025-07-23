package com.android.systemui.qs.customize.viewcontroller;

import android.content.res.Configuration;
import com.android.systemui.qs.customize.view.QSCMainView;
import com.android.systemui.statusbar.policy.ConfigurationController;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class QSCMainViewController$onViewAttached$1 implements ConfigurationController.ConfigurationListener {
    public final /* synthetic */ QSCMainViewController this$0;

    public QSCMainViewController$onViewAttached$1(QSCMainViewController qSCMainViewController) {
        this.this$0 = qSCMainViewController;
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onConfigChanged(Configuration configuration) {
        ViewControllerType viewControllerType;
        QSCMainViewController qSCMainViewController = this.this$0;
        int i = qSCMainViewController.mCurrentOrientation;
        int i2 = configuration.orientation;
        if (i != i2) {
            qSCMainViewController.mCurrentOrientation = i2;
            if (qSCMainViewController.isShown) {
                if (i2 != 2 && (viewControllerType = qSCMainViewController.doShowingInRotation) != null) {
                    qSCMainViewController.doShowingInRotation = null;
                    QSCMainViewController.access$showView(qSCMainViewController, viewControllerType);
                }
                ((QSCMainView) qSCMainViewController.view).setTranslationX(QSCMainViewController.isLargeScreen$6() ? qSCMainViewController.resourcePicker.getQsFrameX() : 0.0f);
                qSCMainViewController.initResources();
                for (ViewControllerBase viewControllerBase : qSCMainViewController.getViewControllerRepo().viewControllers) {
                    if (viewControllerBase != null) {
                        viewControllerBase.configChanged();
                    }
                }
            }
        }
    }
}
