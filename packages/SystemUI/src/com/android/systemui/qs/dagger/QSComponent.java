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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
