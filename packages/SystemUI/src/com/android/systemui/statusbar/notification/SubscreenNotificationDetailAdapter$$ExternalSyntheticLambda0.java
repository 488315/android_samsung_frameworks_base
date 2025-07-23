package com.android.systemui.statusbar.notification;

import com.android.systemui.Dependency;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class SubscreenNotificationDetailAdapter$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ SubscreenNotificationDetailAdapter$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter = SubscreenNotificationDetailAdapter.sInstance;
                ((SubscreenNotificationDetailAdapter) obj).dismissReplyButtons(true);
                break;
            default:
                SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter2 = SubscreenNotificationDetailAdapter.sInstance;
                ((CentralSurfacesImpl) ((CentralSurfaces) Dependency.sDependency.getDependencyInner(CentralSurfaces.class))).checkRemoteInputRequest((String) obj, null);
                break;
        }
    }
}
