package com.android.systemui.statusbar.notification.logging;

import android.os.RemoteException;
import android.util.Log;
import com.android.internal.statusbar.NotificationVisibility;
import com.android.systemui.statusbar.notification.logging.NotificationLogger;

public final /* synthetic */ class NotificationLogger$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ Object f$2;

    public /* synthetic */ NotificationLogger$$ExternalSyntheticLambda0(NotificationLogger.ExpansionStateLogger expansionStateLogger, String str, NotificationLogger.ExpansionStateLogger.State state) {
        this.f$0 = expansionStateLogger;
        this.f$1 = str;
        this.f$2 = state;
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
                }
        }
    }

    public /* synthetic */ NotificationLogger$$ExternalSyntheticLambda0(NotificationLogger notificationLogger, NotificationVisibility[] notificationVisibilityArr, NotificationVisibility[] notificationVisibilityArr2) {
        this.f$0 = notificationLogger;
        this.f$1 = notificationVisibilityArr;
        this.f$2 = notificationVisibilityArr2;
    }
}
