package com.android.systemui.shade;

import java.util.function.Consumer;

/* loaded from: classes3.dex */
public final /* synthetic */ class NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda7 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ NotificationShadeWindowControllerImpl f$0;

    public /* synthetic */ NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda7(NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl, int i) {
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
                boolean zBooleanValue = ((Boolean) obj).booleanValue();
                NotificationShadeWindowState notificationShadeWindowState = notificationShadeWindowControllerImpl.mCurrentState;
                notificationShadeWindowState.qsExpanded = zBooleanValue;
                notificationShadeWindowControllerImpl.apply(notificationShadeWindowState);
                break;
            case 4:
                notificationShadeWindowControllerImpl.onCommunalVisibleChanged((Boolean) obj);
                break;
            default:
                boolean zBooleanValue2 = ((Boolean) obj).booleanValue();
                NotificationShadeWindowState notificationShadeWindowState2 = notificationShadeWindowControllerImpl.mCurrentState;
                notificationShadeWindowState2.keyguardOccluded = zBooleanValue2;
                notificationShadeWindowControllerImpl.apply(notificationShadeWindowState2);
                break;
        }
    }
}
