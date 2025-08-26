package com.android.systemui.statusbar.notification.row;

import android.view.View;
import com.android.systemui.blur.SecQpBlurController;
import com.android.systemui.blur.di.SecPanelBlurBinding;
import com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin;

/* loaded from: classes3.dex */
public final /* synthetic */ class NotificationGutsManager$$ExternalSyntheticLambda7 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ NotificationGutsManager f$0;
    public final /* synthetic */ View f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ int f$3;
    public final /* synthetic */ NotificationMenuRowPlugin.MenuItem f$4;

    public /* synthetic */ NotificationGutsManager$$ExternalSyntheticLambda7(NotificationGutsManager notificationGutsManager, View view, int i, int i2, NotificationMenuRowPlugin.MenuItem menuItem, int i3) {
        this.$r8$classId = i3;
        this.f$0 = notificationGutsManager;
        this.f$1 = view;
        this.f$2 = i;
        this.f$3 = i2;
        this.f$4 = menuItem;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                NotificationGutsManager notificationGutsManager = this.f$0;
                View view = this.f$1;
                int i = this.f$2;
                int i2 = this.f$3;
                NotificationMenuRowPlugin.MenuItem menuItem = this.f$4;
                int i3 = NotificationGutsManager.$r8$clinit;
                notificationGutsManager.getClass();
                notificationGutsManager.mMainHandler.post(new NotificationGutsManager$$ExternalSyntheticLambda7(notificationGutsManager, view, i, i2, menuItem, 1));
                break;
            default:
                NotificationGutsManager notificationGutsManager2 = this.f$0;
                View view2 = this.f$1;
                int i4 = this.f$2;
                int i5 = this.f$3;
                NotificationMenuRowPlugin.MenuItem menuItem2 = this.f$4;
                if (notificationGutsManager2.mIsGoingGutOpenedFromLock) {
                    SecQpBlurController secQpBlurController = notificationGutsManager2.mBlurController;
                    secQpBlurController.getClass();
                    secQpBlurController.doBlur(1.0f, SecPanelBlurBinding.BlurType.QUICK_PANEL);
                }
                notificationGutsManager2.openGutsInternal(view2, i4, i5, menuItem2);
                break;
        }
    }
}
