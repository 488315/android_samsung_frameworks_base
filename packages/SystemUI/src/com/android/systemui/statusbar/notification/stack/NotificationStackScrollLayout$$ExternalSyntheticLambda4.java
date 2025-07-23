package com.android.systemui.statusbar.notification.stack;

import com.android.internal.jank.InteractionJankMonitor;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class NotificationStackScrollLayout$$ExternalSyntheticLambda4 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ NotificationStackScrollLayout f$0;

    public /* synthetic */ NotificationStackScrollLayout$$ExternalSyntheticLambda4(NotificationStackScrollLayout notificationStackScrollLayout, int i) {
        this.$r8$classId = i;
        this.f$0 = notificationStackScrollLayout;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        NotificationStackScrollLayout notificationStackScrollLayout = this.f$0;
        switch (i) {
            case 0:
                boolean z = NotificationStackScrollLayout.DEBUG_DISABLE_SHOW_NEW_NOTIF_ONLY;
                notificationStackScrollLayout.animateScroll();
                break;
            default:
                notificationStackScrollLayout.mFlingAfterUpEvent = false;
                InteractionJankMonitor.getInstance().end(2);
                int i2 = SceneContainerFlag.$r8$clinit;
                RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                notificationStackScrollLayout.mFinishScrollingCallback = null;
                break;
        }
    }
}
