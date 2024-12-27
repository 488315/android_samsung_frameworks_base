package com.android.systemui.statusbar.notification.stack;

import android.view.View;
import com.android.internal.jank.InteractionJankMonitor;

public final /* synthetic */ class NotificationStackScrollLayout$$ExternalSyntheticLambda4 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ NotificationStackScrollLayout$$ExternalSyntheticLambda4(View view) {
        this.$r8$classId = 2;
        this.f$0 = view;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                boolean z = NotificationStackScrollLayout.DEBUG_DISABLE_SHOW_NEW_NOTIF_ONLY;
                ((NotificationStackScrollLayout) obj).animateScroll();
                break;
            case 1:
                NotificationStackScrollLayout notificationStackScrollLayout = (NotificationStackScrollLayout) obj;
                notificationStackScrollLayout.mFlingAfterUpEvent = false;
                InteractionJankMonitor.getInstance().end(2);
                notificationStackScrollLayout.mFinishScrollingCallback = null;
                break;
            default:
                boolean z2 = NotificationStackScrollLayout.DEBUG_DISABLE_SHOW_NEW_NOTIF_ONLY;
                ((View) obj).requestFocus();
                break;
        }
    }

    public /* synthetic */ NotificationStackScrollLayout$$ExternalSyntheticLambda4(NotificationStackScrollLayout notificationStackScrollLayout, int i) {
        this.$r8$classId = i;
        this.f$0 = notificationStackScrollLayout;
    }
}
