package com.android.systemui.p016qs.dagger;

import com.android.systemui.p016qs.QSContainerImplController;
import com.android.systemui.p016qs.QSFragment;
import com.android.systemui.p016qs.QSSquishinessController;
import com.android.systemui.p016qs.SecQSPanelController;
import com.android.systemui.p016qs.SecQuickQSPanelController;
import com.android.systemui.p016qs.animator.QsExpandAnimator;
import com.android.systemui.p016qs.animator.QsOpenAnimator;
import com.android.systemui.p016qs.animator.QsTransitionAnimator;
import com.android.systemui.p016qs.animator.SecQSFragmentAnimatorManager;
import com.android.systemui.p016qs.bar.BarController;
import com.android.systemui.p016qs.buttons.QSButtonsContainerController;
import com.android.systemui.p016qs.cinema.QSCinemaCompany;
import com.android.systemui.p016qs.customize.QSCustomizerController;

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
