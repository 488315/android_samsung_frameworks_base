package com.android.systemui.statusbar.notification.stack;

import android.view.View;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.systemui.shade.ShadeControllerImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class NotificationStackScrollLayout$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ View f$0;

    public /* synthetic */ NotificationStackScrollLayout$$ExternalSyntheticLambda2(View view, int i) {
        this.$r8$classId = i;
        this.f$0 = view;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ((ShadeControllerImpl) ((NotificationStackScrollLayout) this.f$0).mShadeController).animateCollapseShade(0);
                break;
            case 1:
                NotificationStackScrollLayout notificationStackScrollLayout = (NotificationStackScrollLayout) this.f$0;
                int i = NotificationStackScrollLayout.$r8$clinit;
                notificationStackScrollLayout.animateScroll();
                break;
            case 2:
                ((ShadeControllerImpl) ((NotificationStackScrollLayout) this.f$0).mShadeController).animateCollapseShade(0);
                break;
            case 3:
                NotificationStackScrollLayout notificationStackScrollLayout2 = (NotificationStackScrollLayout) this.f$0;
                notificationStackScrollLayout2.mFlingAfterUpEvent = false;
                InteractionJankMonitor.getInstance().end(2);
                notificationStackScrollLayout2.mFinishScrollingCallback = null;
                break;
            case 4:
                NotificationStackScrollLayout notificationStackScrollLayout3 = (NotificationStackScrollLayout) this.f$0;
                notificationStackScrollLayout3.mFlingAfterUpEvent = false;
                InteractionJankMonitor.getInstance().end(2);
                notificationStackScrollLayout3.mFinishScrollingCallback = null;
                break;
            default:
                View view = this.f$0;
                int i2 = NotificationStackScrollLayout.$r8$clinit;
                view.requestFocus();
                break;
        }
    }
}
