package com.android.systemui.statusbar.notification;

import com.android.systemui.Dependency;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class SubscreenNotificationDetailAdapter$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ SubscreenNotificationDetailAdapter$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ((SubscreenNotificationDetailAdapter) this.f$0).dismissReplyButtons(true);
                break;
            default:
                ((CentralSurfacesImpl) ((CentralSurfaces) Dependency.get(CentralSurfaces.class))).checkRemoteInputRequest((String) this.f$0, null);
                break;
        }
    }
}
