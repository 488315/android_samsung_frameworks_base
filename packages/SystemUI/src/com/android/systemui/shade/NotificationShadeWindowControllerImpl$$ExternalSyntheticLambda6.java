package com.android.systemui.shade;

import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda6 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ NotificationShadeWindowControllerImpl f$0;

    public /* synthetic */ NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda6(NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl, int i) {
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
                notificationShadeWindowControllerImpl.onShadeOrQsExpanded((Boolean) obj);
                break;
            case 2:
                notificationShadeWindowControllerImpl.getClass();
                boolean booleanValue = ((Boolean) obj).booleanValue();
                NotificationShadeWindowState notificationShadeWindowState = notificationShadeWindowControllerImpl.mCurrentState;
                notificationShadeWindowState.qsExpanded = booleanValue;
                notificationShadeWindowControllerImpl.apply(notificationShadeWindowState);
                break;
            case 3:
                notificationShadeWindowControllerImpl.onCommunalVisibleChanged((Boolean) obj);
                break;
            case 4:
                boolean booleanValue2 = ((Boolean) obj).booleanValue();
                NotificationShadeWindowState notificationShadeWindowState2 = notificationShadeWindowControllerImpl.mCurrentState;
                notificationShadeWindowState2.keyguardOccluded = booleanValue2;
                notificationShadeWindowControllerImpl.apply(notificationShadeWindowState2);
                break;
            default:
                ((Boolean) obj).booleanValue();
                notificationShadeWindowControllerImpl.getClass();
                break;
        }
    }
}
