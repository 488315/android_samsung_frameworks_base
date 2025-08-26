package com.android.systemui.indexsearch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.android.systemui.Dependency;
import com.android.systemui.shade.BaseShadeControllerImpl;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda1;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;

/* loaded from: classes2.dex */
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
            CentralSurfacesImpl centralSurfacesImpl3 = (CentralSurfacesImpl) centralSurfaces;
            if (((KeyguardStateControllerImpl) centralSurfacesImpl3.mKeyguardStateController).mShowing) {
                centralSurfacesImpl3.mQSPanelController.mCollapseExpandAction.run();
            } else {
                centralSurfacesImpl3.mShadeSurface.expandQSForOpenDetail();
                centralSurfacesImpl3.mMainExecutor.executeDelayed(new CentralSurfacesImpl$$ExternalSyntheticLambda1(0, centralSurfacesImpl3, stringExtra), 500L);
            }
        }
        finish();
        overridePendingTransition(0, 0);
    }
}
