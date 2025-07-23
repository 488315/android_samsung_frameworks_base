package com.android.systemui.qs.dagger;

import android.view.View;
import com.android.systemui.qs.QSContainerImplController;
import com.android.systemui.qs.QSSquishinessController;
import com.android.systemui.qs.SecQSGradationDrawableController;
import com.android.systemui.qs.SecQSPanelController;
import com.android.systemui.qs.SecQuickQSPanelController;
import com.android.systemui.qs.animator.SecQSImplAnimatorManager;
import com.android.systemui.qs.bar.BarController;
import com.android.systemui.qs.buttons.QSButtonsContainerController;
import com.android.systemui.qs.customize.QSCustomizerController;
import com.android.systemui.qs.customize.viewcontroller.QSCMainViewController;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface QSComponent {
    BarController getBarController();

    QSContainerImplController getQSContainerImplController();

    QSCustomizerController getQSCustomizerController();

    SecQSPanelController getQSPanelController();

    QSSquishinessController getQSSquishinessController();

    QSButtonsContainerController getQsButtonsContainerController();

    SecQSGradationDrawableController getQsGradationDrawableController();

    QSCMainViewController getQscMainViewController();

    SecQuickQSPanelController getQuickQSPanelController();

    View getRootView();

    SecQSImplAnimatorManager getSecQSImplAnimatorManager();
}
