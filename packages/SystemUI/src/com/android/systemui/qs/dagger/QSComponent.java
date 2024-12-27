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
