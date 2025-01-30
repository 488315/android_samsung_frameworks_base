package com.android.systemui.statusbar.phone;

import android.view.IRemoteAnimationRunner;
import com.android.systemui.p016qs.QSPanelHost$$ExternalSyntheticLambda5;
import com.android.systemui.p016qs.SecQSPanelController;
import com.android.systemui.p016qs.SecQSPanelControllerBase;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class CentralSurfacesImpl$$ExternalSyntheticLambda12 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ CentralSurfacesImpl$$ExternalSyntheticLambda12(int i, Object obj, Object obj2) {
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
                SecQSPanelControllerBase.TileRecord tileRecord = (SecQSPanelControllerBase.TileRecord) secQSPanelController.mQsPanelHost.mRecords.stream().filter(new QSPanelHost$$ExternalSyntheticLambda5(str, 0)).findFirst().orElse(null);
                if (tileRecord != null) {
                    secQSPanelController.showDetail(tileRecord, true);
                }
                centralSurfacesImpl.mQSPanelController.flipPageWithTile(str);
                break;
            case 1:
                CentralSurfacesImpl centralSurfacesImpl2 = (CentralSurfacesImpl) this.f$0;
                centralSurfacesImpl2.mQSPanelController.flipPageWithTile((String) this.f$1);
                break;
            default:
                CentralSurfacesImpl.C301724 c301724 = (CentralSurfacesImpl.C301724) this.f$0;
                CentralSurfacesImpl.this.mKeyguardViewMediator.hideWithAnimation((IRemoteAnimationRunner) this.f$1);
                break;
        }
    }
}
