package com.android.systemui.statusbar.phone;

import com.android.systemui.animation.ActivityTransitionAnimator;
import com.android.systemui.plugins.OverlayPlugin;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl.AnonymousClass3.Callback;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class CentralSurfacesImpl$3$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ CentralSurfacesImpl$3$$ExternalSyntheticLambda0(int i, Object obj, Object obj2) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                CentralSurfacesImpl.AnonymousClass3 anonymousClass3 = (CentralSurfacesImpl.AnonymousClass3) this.f$0;
                OverlayPlugin overlayPlugin = (OverlayPlugin) this.f$1;
                CentralSurfacesImpl centralSurfacesImpl = CentralSurfacesImpl.this;
                overlayPlugin.setup(((NotificationShadeWindowControllerImpl) centralSurfacesImpl.mNotificationShadeWindowController).mWindowRootView, centralSurfacesImpl.getNavigationBarView(), anonymousClass3.new Callback(overlayPlugin), centralSurfacesImpl.mDozeParameters);
                break;
            case 1:
                CentralSurfacesImpl.AnonymousClass3 anonymousClass32 = (CentralSurfacesImpl.AnonymousClass3) this.f$0;
                anonymousClass32.mOverlays.remove((OverlayPlugin) this.f$1);
                ((NotificationShadeWindowControllerImpl) CentralSurfacesImpl.this.mNotificationShadeWindowController).setForcePluginOpen(anonymousClass32, anonymousClass32.mOverlays.size() != 0);
                break;
            default:
                CentralSurfacesImpl.this.mKeyguardViewMediator.hideWithAnimation((ActivityTransitionAnimator.Runner) this.f$1);
                break;
        }
    }
}
