package com.android.systemui.indexsearch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.android.systemui.Dependency;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda12;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class DetailPanelLaunchActivity extends Activity {
    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Intent intent = getIntent();
        String stringExtra = intent.getStringExtra("tileSpec");
        String stringExtra2 = intent.getStringExtra("requestFrom");
        CentralSurfaces centralSurfaces = (CentralSurfaces) Dependency.get(CentralSurfaces.class);
        if (stringExtra == null) {
            CentralSurfacesImpl centralSurfacesImpl = (CentralSurfacesImpl) centralSurfaces;
            if (centralSurfacesImpl.mCommandQueue.panelsEnabled() && centralSurfacesImpl.mNotificationPanelViewController != null) {
                if (centralSurfacesImpl.isKeyguardShowing()) {
                    centralSurfacesImpl.mQSPanelController.mCollapseExpandAction.run();
                } else {
                    centralSurfacesImpl.mNotificationPanelViewController.expandToQs();
                }
            }
        } else if ("search".equalsIgnoreCase(stringExtra2)) {
            CentralSurfacesImpl centralSurfacesImpl2 = (CentralSurfacesImpl) centralSurfaces;
            if (centralSurfacesImpl2.isKeyguardShowing()) {
                centralSurfacesImpl2.mQSPanelController.mCollapseExpandAction.run();
            } else {
                centralSurfacesImpl2.mNotificationPanelViewController.expandToQs();
                centralSurfacesImpl2.mMainHandler.postDelayed(new CentralSurfacesImpl$$ExternalSyntheticLambda12(1, centralSurfacesImpl2, stringExtra), 200L);
            }
        } else {
            ((CentralSurfacesImpl) centralSurfaces).openQSPanelWithDetail(stringExtra);
        }
        finish();
        overridePendingTransition(0, 0);
    }
}
