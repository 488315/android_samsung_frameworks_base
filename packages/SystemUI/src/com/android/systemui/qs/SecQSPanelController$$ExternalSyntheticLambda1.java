package com.android.systemui.qs;

import com.android.systemui.qs.customize.viewcontroller.QSCMainViewController;
import java.util.Objects;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class SecQSPanelController$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SecQSPanelController f$0;

    public /* synthetic */ SecQSPanelController$$ExternalSyntheticLambda1(SecQSPanelController secQSPanelController, int i) {
        this.$r8$classId = i;
        this.f$0 = secQSPanelController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        SecQSPanelController secQSPanelController = this.f$0;
        switch (i) {
            case 0:
                secQSPanelController.mBarOrderInteractor.initValuesAndApply(secQSPanelController.getContext(), false);
                break;
            default:
                QSCMainViewController qSCMainViewController = secQSPanelController.mQSCMainViewController;
                if (!qSCMainViewController.isShown) {
                    QSPanelHost qSPanelHost = secQSPanelController.mQsPanelHost;
                    Objects.requireNonNull(qSPanelHost);
                    qSCMainViewController.show(new SecQSPanelController$$ExternalSyntheticLambda0(qSPanelHost, 1));
                    break;
                }
                break;
        }
    }
}
