package com.android.systemui.statusbar.phone;

import android.util.ArraySet;
import com.android.systemui.plugins.OverlayPlugin;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl.C30224.Callback;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class CentralSurfacesImpl$4$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ CentralSurfacesImpl.C30224 f$0;
    public final /* synthetic */ OverlayPlugin f$1;

    public /* synthetic */ CentralSurfacesImpl$4$$ExternalSyntheticLambda0(CentralSurfacesImpl.C30224 c30224, OverlayPlugin overlayPlugin, int i) {
        this.$r8$classId = i;
        this.f$0 = c30224;
        this.f$1 = overlayPlugin;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                CentralSurfacesImpl.C30224 c30224 = this.f$0;
                OverlayPlugin overlayPlugin = this.f$1;
                ArraySet arraySet = c30224.mOverlays;
                arraySet.remove(overlayPlugin);
                ((NotificationShadeWindowControllerImpl) CentralSurfacesImpl.this.mNotificationShadeWindowController).setForcePluginOpen(c30224, arraySet.size() != 0);
                break;
            default:
                CentralSurfacesImpl.C30224 c302242 = this.f$0;
                OverlayPlugin overlayPlugin2 = this.f$1;
                CentralSurfacesImpl centralSurfacesImpl = CentralSurfacesImpl.this;
                overlayPlugin2.setup(centralSurfacesImpl.mNotificationShadeWindowView, centralSurfacesImpl.getNavigationBarView(), c302242.new Callback(overlayPlugin2), centralSurfacesImpl.mDozeParameters);
                break;
        }
    }
}
