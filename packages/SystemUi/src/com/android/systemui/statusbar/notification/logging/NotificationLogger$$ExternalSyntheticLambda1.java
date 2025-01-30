package com.android.systemui.statusbar.notification.logging;

import android.os.RemoteException;
import android.util.Log;
import com.android.internal.statusbar.NotificationVisibility;
import com.android.systemui.statusbar.notification.logging.NotificationLogger;
import java.io.Serializable;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class NotificationLogger$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Serializable f$1;
    public final /* synthetic */ Object f$2;

    public /* synthetic */ NotificationLogger$$ExternalSyntheticLambda1(Object obj, Serializable serializable, Object obj2, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = serializable;
        this.f$2 = obj2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                NotificationLogger notificationLogger = (NotificationLogger) this.f$0;
                NotificationVisibility[] notificationVisibilityArr = (NotificationVisibility[]) this.f$1;
                NotificationVisibility[] notificationVisibilityArr2 = (NotificationVisibility[]) this.f$2;
                notificationLogger.getClass();
                try {
                    notificationLogger.mBarService.onNotificationVisibilityChanged(notificationVisibilityArr, notificationVisibilityArr2);
                } catch (RemoteException unused) {
                }
                int length = notificationVisibilityArr.length;
                if (length > 0) {
                    String[] strArr = new String[length];
                    for (int i = 0; i < length; i++) {
                        strArr[i] = notificationVisibilityArr[i].key;
                    }
                    try {
                        notificationLogger.mNotificationListener.setNotificationsShown(strArr);
                    } catch (RuntimeException e) {
                        Log.d("NotificationLogger", "failed setNotificationsShown: ", e);
                    }
                }
                for (NotificationVisibility notificationVisibility : notificationVisibilityArr) {
                    if (notificationVisibility != null) {
                        notificationVisibility.recycle();
                    }
                }
                for (NotificationVisibility notificationVisibility2 : notificationVisibilityArr2) {
                    if (notificationVisibility2 != null) {
                        notificationVisibility2.recycle();
                    }
                }
                break;
            default:
                NotificationLogger.ExpansionStateLogger expansionStateLogger = (NotificationLogger.ExpansionStateLogger) this.f$0;
                String str = (String) this.f$1;
                NotificationLogger.ExpansionStateLogger.State state = (NotificationLogger.ExpansionStateLogger.State) this.f$2;
                expansionStateLogger.getClass();
                try {
                    expansionStateLogger.mBarService.onNotificationExpansionChanged(str, state.mIsUserAction.booleanValue(), state.mIsExpanded.booleanValue(), state.mLocation.ordinal());
                    break;
                } catch (RemoteException e2) {
                    Log.e("NotificationLogger", "Failed to call onNotificationExpansionChanged: ", e2);
                    return;
                }
        }
    }
}
