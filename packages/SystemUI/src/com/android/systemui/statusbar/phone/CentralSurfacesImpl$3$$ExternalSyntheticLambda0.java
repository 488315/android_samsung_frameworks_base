package com.android.systemui.statusbar.phone;

import com.android.systemui.plugins.OverlayPlugin;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl.AnonymousClass3.Callback;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final /* synthetic */ class CentralSurfacesImpl$3$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ CentralSurfacesImpl.AnonymousClass3 f$0;
    public final /* synthetic */ OverlayPlugin f$1;

    public /* synthetic */ CentralSurfacesImpl$3$$ExternalSyntheticLambda0(CentralSurfacesImpl.AnonymousClass3 anonymousClass3, OverlayPlugin overlayPlugin, int i) {
        this.$r8$classId = i;
        this.f$0 = anonymousClass3;
        this.f$1 = overlayPlugin;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                CentralSurfacesImpl.AnonymousClass3 anonymousClass3 = this.f$0;
                OverlayPlugin overlayPlugin = this.f$1;
                CentralSurfacesImpl centralSurfacesImpl = CentralSurfacesImpl.this;
                overlayPlugin.setup(((NotificationShadeWindowControllerImpl) centralSurfacesImpl.mNotificationShadeWindowController).mWindowRootView, centralSurfacesImpl.getNavigationBarView(), anonymousClass3.new Callback(overlayPlugin), centralSurfacesImpl.mDozeParameters);
                break;
            default:
                CentralSurfacesImpl.AnonymousClass3 anonymousClass32 = this.f$0;
                anonymousClass32.mOverlays.remove(this.f$1);
                ((NotificationShadeWindowControllerImpl) CentralSurfacesImpl.this.mNotificationShadeWindowController).setForcePluginOpen(anonymousClass32, anonymousClass32.mOverlays.size() != 0);
                break;
        }
    }
}
