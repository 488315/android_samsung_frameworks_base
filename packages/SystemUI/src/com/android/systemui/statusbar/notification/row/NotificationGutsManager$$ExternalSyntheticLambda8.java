package com.android.systemui.statusbar.notification.row;

import com.android.systemui.Dumpable;
import com.android.systemui.blur.SecQpBlurController;
import com.android.systemui.blur.di.SecPanelBlurBinding;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class NotificationGutsManager$$ExternalSyntheticLambda8 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Dumpable f$0;

    public /* synthetic */ NotificationGutsManager$$ExternalSyntheticLambda8(Dumpable dumpable, int i) {
        this.$r8$classId = i;
        this.f$0 = dumpable;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Dumpable dumpable = this.f$0;
        switch (i) {
            case 0:
                NotificationGutsManager notificationGutsManager = (NotificationGutsManager) dumpable;
                int i2 = NotificationGutsManager.$r8$clinit;
                notificationGutsManager.getClass();
                notificationGutsManager.mMainHandler.post(new NotificationGutsManager$$ExternalSyntheticLambda8(notificationGutsManager, 1));
                break;
            case 1:
                SecQpBlurController secQpBlurController = ((NotificationGutsManager) dumpable).mBlurController;
                secQpBlurController.getClass();
                secQpBlurController.doBlur(0.0f, SecPanelBlurBinding.BlurType.QUICK_PANEL);
                break;
            default:
                ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) dumpable;
                expandableNotificationRow.resetTranslation();
                expandableNotificationRow.updateContentAccessibilityImportanceForGuts(false);
                break;
        }
    }
}
