package com.android.systemui.shade;

import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda12 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ NotificationShadeWindowControllerImpl f$0;

    public /* synthetic */ NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda12(NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl, int i) {
        this.$r8$classId = i;
        this.f$0 = notificationShadeWindowControllerImpl;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.apply((NotificationShadeWindowState) obj);
                break;
            default:
                NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl = this.f$0;
                ((Boolean) obj).booleanValue();
                notificationShadeWindowControllerImpl.getClass();
                break;
        }
    }
}
