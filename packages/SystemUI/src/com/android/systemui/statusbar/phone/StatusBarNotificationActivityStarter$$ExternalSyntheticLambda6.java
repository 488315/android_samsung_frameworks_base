package com.android.systemui.statusbar.phone;

import com.android.systemui.shade.BaseShadeControllerImpl;
import com.android.systemui.statusbar.notification.collection.NotifCollection;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final /* synthetic */ class StatusBarNotificationActivityStarter$$ExternalSyntheticLambda6 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ StatusBarNotificationActivityStarter f$0;
    public final /* synthetic */ Runnable f$1;

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
                Runnable runnable = this.f$1;
                if (!((StatusBarNotificationPresenter) statusBarNotificationActivityStarter.mPresenter).isCollapsing()) {
                    runnable.run();
                    break;
                } else {
                    ((BaseShadeControllerImpl) statusBarNotificationActivityStarter.mShadeController).postCollapseActions.add(runnable);
                    break;
                }
            default:
                StatusBarNotificationActivityStarter statusBarNotificationActivityStarter2 = this.f$0;
                Runnable runnable2 = this.f$1;
                if (!((StatusBarNotificationPresenter) statusBarNotificationActivityStarter2.mPresenter).isCollapsing()) {
                    runnable2.run();
                    break;
                } else {
                    ((BaseShadeControllerImpl) statusBarNotificationActivityStarter2.mShadeController).postCollapseActions.add(runnable2);
                    break;
                }
        }
    }
}
