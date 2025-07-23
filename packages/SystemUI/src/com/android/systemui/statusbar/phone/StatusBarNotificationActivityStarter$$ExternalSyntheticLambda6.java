package com.android.systemui.statusbar.phone;

import com.android.systemui.shade.BaseShadeControllerImpl;
import com.android.systemui.statusbar.notification.collection.NotifCollection;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class StatusBarNotificationActivityStarter$$ExternalSyntheticLambda6 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ StatusBarNotificationActivityStarter f$0;
    public final /* synthetic */ NotifCollection.FutureDismissal f$1;

    public /* synthetic */ StatusBarNotificationActivityStarter$$ExternalSyntheticLambda6(StatusBarNotificationActivityStarter statusBarNotificationActivityStarter, NotifCollection.FutureDismissal futureDismissal, int i) {
        this.$r8$classId = i;
        this.f$0 = statusBarNotificationActivityStarter;
        this.f$1 = futureDismissal;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                StatusBarNotificationActivityStarter statusBarNotificationActivityStarter = this.f$0;
                NotifCollection.FutureDismissal futureDismissal = this.f$1;
                if (!((StatusBarNotificationPresenter) statusBarNotificationActivityStarter.mPresenter).isCollapsing()) {
                    futureDismissal.run();
                    break;
                } else {
                    ((BaseShadeControllerImpl) statusBarNotificationActivityStarter.mShadeController).postCollapseActions.add(futureDismissal);
                    break;
                }
            default:
                StatusBarNotificationActivityStarter statusBarNotificationActivityStarter2 = this.f$0;
                NotifCollection.FutureDismissal futureDismissal2 = this.f$1;
                if (!((StatusBarNotificationPresenter) statusBarNotificationActivityStarter2.mPresenter).isCollapsing()) {
                    futureDismissal2.run();
                    break;
                } else {
                    ((BaseShadeControllerImpl) statusBarNotificationActivityStarter2.mShadeController).postCollapseActions.add(futureDismissal2);
                    break;
                }
        }
    }
}
