package com.android.systemui.statusbar.phone;

import android.os.RemoteException;
import com.android.systemui.shade.ShadeController;
import com.android.systemui.shade.ShadeControllerImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class StatusBarNotificationActivityStarter$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ StatusBarNotificationActivityStarter$$ExternalSyntheticLambda1(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                StatusBarNotificationActivityStarter statusBarNotificationActivityStarter = (StatusBarNotificationActivityStarter) this.f$0;
                statusBarNotificationActivityStarter.getClass();
                try {
                    statusBarNotificationActivityStarter.mDreamManager.awaken();
                    break;
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            default:
                ((ShadeControllerImpl) ((ShadeController) this.f$0)).collapseShade();
                break;
        }
    }
}
