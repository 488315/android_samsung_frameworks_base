package com.android.systemui.statusbar.phone;

import com.android.systemui.shade.NotificationShadeWindowControllerImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class CentralSurfacesImpl$$ExternalSyntheticLambda36 implements Runnable {
    public final /* synthetic */ CentralSurfacesImpl f$0;
    public final /* synthetic */ boolean f$1;

    public /* synthetic */ CentralSurfacesImpl$$ExternalSyntheticLambda36(CentralSurfacesImpl centralSurfacesImpl, boolean z) {
        this.f$0 = centralSurfacesImpl;
        this.f$1 = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        CentralSurfacesImpl centralSurfacesImpl = this.f$0;
        ((NotificationShadeWindowControllerImpl) centralSurfacesImpl.mNotificationShadeWindowController).setRequestTopUi("ShellStartingWindow", this.f$1);
    }
}
