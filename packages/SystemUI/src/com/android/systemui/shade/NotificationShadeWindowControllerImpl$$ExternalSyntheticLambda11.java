package com.android.systemui.shade;

import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final /* synthetic */ class NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda11 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ NotificationShadeWindowControllerImpl f$0;

    public /* synthetic */ NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda11(NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl, int i) {
        this.$r8$classId = i;
        this.f$0 = notificationShadeWindowControllerImpl;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl = this.f$0;
        switch (i) {
            case 0:
                notificationShadeWindowControllerImpl.apply((NotificationShadeWindowState) obj);
                break;
            case 1:
                ((Boolean) obj).booleanValue();
                notificationShadeWindowControllerImpl.getClass();
                break;
            case 2:
                notificationShadeWindowControllerImpl.onShadeOrQsExpanded((Boolean) obj);
                break;
            case 3:
                notificationShadeWindowControllerImpl.getClass();
                boolean booleanValue = ((Boolean) obj).booleanValue();
                NotificationShadeWindowState notificationShadeWindowState = notificationShadeWindowControllerImpl.mCurrentState;
                notificationShadeWindowState.qsExpanded = booleanValue;
                notificationShadeWindowControllerImpl.apply(notificationShadeWindowState);
                break;
            default:
                notificationShadeWindowControllerImpl.onCommunalVisibleChanged((Boolean) obj);
                break;
        }
    }
}
