package com.android.systemui.qs;

import android.util.Log;
import com.android.systemui.qs.bar.BarItemImpl;
import com.android.systemui.qs.bar.domain.interactor.BarOrderInteractor;
import com.android.systemui.qs.bar.repository.BarOrderRepository;
import com.android.systemui.qs.customize.viewcontroller.QSCMainViewController;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final /* synthetic */ class SecQSPanelController$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ SecQSPanelController$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                BarOrderInteractor barOrderInteractor = (BarOrderInteractor) obj;
                BarOrderRepository barOrderRepository = barOrderInteractor.repository;
                List split$default = StringsKt__StringsKt.split$default(barOrderRepository.getDefaultBarOrderList(), new String[]{","}, 0, 6);
                Log.d("BarOrderRepository", "resetBarOrder");
                barOrderRepository.setBarOrder(barOrderInteractor.toFilteredNonEditBars(split$default));
                barOrderInteractor.repository.setCollapsedBarRow(2);
                break;
            case 1:
                SecQSPanelController secQSPanelController = (SecQSPanelController) obj;
                QSCMainViewController qSCMainViewController = secQSPanelController.mQSCMainViewController;
                if (!qSCMainViewController.isShown) {
                    QSPanelHost qSPanelHost = secQSPanelController.mQsPanelHost;
                    Objects.requireNonNull(qSPanelHost);
                    qSCMainViewController.show(new SecQSPanelController$$ExternalSyntheticLambda0(qSPanelHost, 2));
                    break;
                }
                break;
            default:
                Iterator it = ((QSPanelHost) obj).mBarController.mExpandedBarItems.iterator();
                while (it.hasNext()) {
                    ((BarItemImpl) it.next()).makeCloneBar();
                }
                break;
        }
    }
}
