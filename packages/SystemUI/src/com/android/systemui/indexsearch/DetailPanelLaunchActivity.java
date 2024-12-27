package com.android.systemui.indexsearch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.android.systemui.Dependency;
import com.android.systemui.shade.BaseShadeControllerImpl;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class DetailPanelLaunchActivity extends Activity {
    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Intent intent = getIntent();
        String stringExtra = intent.getStringExtra("tileSpec");
        String stringExtra2 = intent.getStringExtra("requestFrom");
        CentralSurfaces centralSurfaces = (CentralSurfaces) Dependency.sDependency.getDependencyInner(CentralSurfaces.class);
        if (stringExtra == null) {
            CentralSurfacesImpl centralSurfacesImpl = (CentralSurfacesImpl) centralSurfaces;
            if (centralSurfacesImpl.mCommandQueue.panelsEnabled()) {
                if (((KeyguardStateControllerImpl) centralSurfacesImpl.mKeyguardStateController).mShowing) {
                    centralSurfacesImpl.mQSPanelController.mCollapseExpandAction.run();
                } else {
                    ((BaseShadeControllerImpl) centralSurfacesImpl.mShadeController).animateExpandQs();
                }
            }
        } else if ("search".equalsIgnoreCase(stringExtra2)) {
            CentralSurfacesImpl centralSurfacesImpl2 = (CentralSurfacesImpl) centralSurfaces;
            if (((KeyguardStateControllerImpl) centralSurfacesImpl2.mKeyguardStateController).mShowing) {
                centralSurfacesImpl2.mQSPanelController.mCollapseExpandAction.run();
            } else {
                ((BaseShadeControllerImpl) centralSurfacesImpl2.mShadeController).animateExpandQs();
                centralSurfacesImpl2.mQSPanelController.flipPageWithTile(stringExtra);
            }
        } else {
            ((CentralSurfacesImpl) centralSurfaces).openQSPanelWithDetail(stringExtra);
        }
        finish();
        overridePendingTransition(0, 0);
    }
}
