package com.android.systemui.statusbar.phone;

import android.view.IRemoteAnimationRunner;
import com.android.systemui.qs.QSPanelHost$$ExternalSyntheticLambda17;
import com.android.systemui.qs.SecQSPanelController;
import com.android.systemui.qs.SecQSPanelControllerBase;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class CentralSurfacesImpl$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ CentralSurfacesImpl$$ExternalSyntheticLambda1(int i, Object obj, Object obj2) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                CentralSurfacesImpl centralSurfacesImpl = (CentralSurfacesImpl) this.f$0;
                String str = (String) this.f$1;
                SecQSPanelController secQSPanelController = centralSurfacesImpl.mQSPanelController;
                SecQSPanelControllerBase.TileRecord tileRecord = (SecQSPanelControllerBase.TileRecord) secQSPanelController.mQsPanelHost.mRecords.stream().filter(new QSPanelHost$$ExternalSyntheticLambda17(str, 0)).findFirst().orElse(null);
                if (tileRecord != null) {
                    secQSPanelController.mDetailController.showDetail(true, tileRecord);
                }
                centralSurfacesImpl.mQSPanelController.flipPageWithTile(str);
                break;
            default:
                CentralSurfacesImpl.AnonymousClass20 anonymousClass20 = (CentralSurfacesImpl.AnonymousClass20) this.f$0;
                CentralSurfacesImpl.this.mKeyguardViewMediator.hideWithAnimation((IRemoteAnimationRunner) this.f$1);
                break;
        }
    }
}
