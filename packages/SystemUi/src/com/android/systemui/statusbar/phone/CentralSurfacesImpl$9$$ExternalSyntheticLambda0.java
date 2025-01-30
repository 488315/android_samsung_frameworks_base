package com.android.systemui.statusbar.phone;

import android.util.Log;
import android.view.WindowManagerGlobal;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class CentralSurfacesImpl$9$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ CentralSurfacesImpl$9$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                CentralSurfacesImpl.C30279 c30279 = (CentralSurfacesImpl.C30279) this.f$0;
                c30279.getClass();
                Log.d("CentralSurfaces", "Clear all notifications and count major view number from aecmonitor");
                MetricsLogger.action(CentralSurfacesImpl.this.mContext, 148);
                CentralSurfacesImpl.this.mStackScroller.clearNotifications(0, true);
                if (!((KeyguardStateControllerImpl) CentralSurfacesImpl.this.mKeyguardStateController).mShowing) {
                    WindowManagerGlobal.getInstance().trimMemory(80);
                    break;
                }
                break;
            default:
                CentralSurfacesImpl.C30224.Callback callback = (CentralSurfacesImpl.C30224.Callback) this.f$0;
                CentralSurfacesImpl.C30224 c30224 = CentralSurfacesImpl.C30224.this;
                NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl = (NotificationShadeWindowControllerImpl) CentralSurfacesImpl.this.mNotificationShadeWindowController;
                notificationShadeWindowControllerImpl.mListener = new CentralSurfacesImpl$$ExternalSyntheticLambda0(callback);
                notificationShadeWindowControllerImpl.setForcePluginOpen(callback, c30224.mOverlays.size() != 0);
                break;
        }
    }
}
