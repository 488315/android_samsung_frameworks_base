package com.android.systemui.statusbar.phone;

import com.android.systemui.shade.NotificationShadeWindowControllerImpl;

/* loaded from: classes3.dex */
public final /* synthetic */ class CentralSurfacesImpl$$ExternalSyntheticLambda37 implements Runnable {
    public final /* synthetic */ CentralSurfacesImpl f$0;
    public final /* synthetic */ boolean f$1;

    public /* synthetic */ CentralSurfacesImpl$$ExternalSyntheticLambda37(CentralSurfacesImpl centralSurfacesImpl, boolean z) {
        this.f$0 = centralSurfacesImpl;
        this.f$1 = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        CentralSurfacesImpl centralSurfacesImpl = this.f$0;
        ((NotificationShadeWindowControllerImpl) centralSurfacesImpl.mNotificationShadeWindowController).setRequestTopUi("ShellStartingWindow", this.f$1);
    }
}
