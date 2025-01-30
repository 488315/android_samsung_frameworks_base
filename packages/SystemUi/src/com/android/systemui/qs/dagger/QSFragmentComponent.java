package com.android.systemui.qs.dagger;

import com.android.systemui.qs.QSContainerImplController;
import com.android.systemui.qs.QSFragment;
import com.android.systemui.qs.QSSquishinessController;
import com.android.systemui.qs.SecQSPanelController;
import com.android.systemui.qs.SecQuickQSPanelController;
import com.android.systemui.qs.animator.QsExpandAnimator;
import com.android.systemui.qs.animator.QsOpenAnimator;
import com.android.systemui.qs.animator.QsTransitionAnimator;
import com.android.systemui.qs.animator.SecQSFragmentAnimatorManager;
import com.android.systemui.qs.bar.BarController;
import com.android.systemui.qs.buttons.QSButtonsContainerController;
import com.android.systemui.qs.cinema.QSCinemaCompany;
import com.android.systemui.qs.customize.QSCustomizerController;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface QSFragmentComponent {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Factory {
        QSFragmentComponent create(QSFragment qSFragment);
    }

    BarController getBarController();

    QSButtonsContainerController getQSButtonsContainerController();

    QSCinemaCompany getQSCinemaCompany();

    QSContainerImplController getQSContainerImplController();

    QSCustomizerController getQSCustomizerController();

    QSSquishinessController getQSSquishinessController();

    QsExpandAnimator getQsExpandAnimator();

    QsOpenAnimator getQsOpenAnimator();

    QsTransitionAnimator getQsTransitionAnimator();

    SecQSFragmentAnimatorManager getSecQSFragmentAnimatorManager();

    SecQSPanelController getSecQSPanelController();

    SecQuickQSPanelController getSecQuickQSPanelController();
}
