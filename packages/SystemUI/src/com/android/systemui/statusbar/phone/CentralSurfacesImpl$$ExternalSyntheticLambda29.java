package com.android.systemui.statusbar.phone;

import android.util.Log;
import android.view.WindowManagerGlobal;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import com.android.systemui.shade.ShadeController;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class CentralSurfacesImpl$$ExternalSyntheticLambda29 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ CentralSurfacesImpl$$ExternalSyntheticLambda29(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                ((ShadeController) obj).makeExpandedInvisible();
                break;
            case 1:
                CentralSurfacesImpl.AnonymousClass3.Callback callback = (CentralSurfacesImpl.AnonymousClass3.Callback) obj;
                CentralSurfacesImpl.AnonymousClass3 anonymousClass3 = CentralSurfacesImpl.AnonymousClass3.this;
                NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl = (NotificationShadeWindowControllerImpl) CentralSurfacesImpl.this.mNotificationShadeWindowController;
                notificationShadeWindowControllerImpl.mListener = new CentralSurfacesImpl$$ExternalSyntheticLambda0(callback);
                notificationShadeWindowControllerImpl.setForcePluginOpen(callback, anonymousClass3.mOverlays.size() != 0);
                break;
            default:
                CentralSurfacesImpl.AnonymousClass8 anonymousClass8 = (CentralSurfacesImpl.AnonymousClass8) obj;
                anonymousClass8.getClass();
                Log.d("CentralSurfaces", "Clear all notifications and count major view number from aecmonitor");
                MetricsLogger.action(CentralSurfacesImpl.this.mContext, 148);
                CentralSurfacesImpl.this.mStackScroller.clearNotifications(0, true);
                if (!((KeyguardStateControllerImpl) CentralSurfacesImpl.this.mKeyguardStateController).mShowing) {
                    WindowManagerGlobal.getInstance().trimMemory(80);
                    break;
                }
                break;
        }
    }
}
