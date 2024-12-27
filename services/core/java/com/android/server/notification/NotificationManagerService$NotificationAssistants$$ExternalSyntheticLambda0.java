package com.android.server.notification;

import android.os.RemoteException;
import android.service.notification.INotificationListener;
import android.util.Slog;

import java.util.function.BiConsumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */
class NotificationManagerService$NotificationAssistants$$ExternalSyntheticLambda0
        implements BiConsumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ NotificationManagerService.NotificationAssistants f$0;
    public final /* synthetic */ String f$1;

    public /* synthetic */
    NotificationManagerService$NotificationAssistants$$ExternalSyntheticLambda0(
            NotificationManagerService.NotificationAssistants notificationAssistants,
            String str,
            int i) {
        this.$r8$classId = i;
        this.f$0 = notificationAssistants;
        this.f$1 = str;
    }

    @Override // java.util.function.BiConsumer
    public final void accept(Object obj, Object obj2) {
        switch (this.$r8$classId) {
            case 0:
                NotificationManagerService.NotificationAssistants notificationAssistants = this.f$0;
                String str = this.f$1;
                INotificationListener iNotificationListener = (INotificationListener) obj;
                notificationAssistants.getClass();
                try {
                    iNotificationListener.onNotificationClicked(str);
                    break;
                } catch (RemoteException e) {
                    Slog.e(
                            notificationAssistants.TAG,
                            "unable to notify assistant (clicked): " + iNotificationListener,
                            e);
                    return;
                }
            case 1:
                NotificationManagerService.NotificationAssistants notificationAssistants2 =
                        this.f$0;
                String str2 = this.f$1;
                INotificationListener iNotificationListener2 = (INotificationListener) obj;
                NotificationManagerService.StatusBarNotificationHolder statusBarNotificationHolder =
                        (NotificationManagerService.StatusBarNotificationHolder) obj2;
                notificationAssistants2.getClass();
                try {
                    iNotificationListener2.onNotificationSnoozedUntilContext(
                            statusBarNotificationHolder, str2);
                    break;
                } catch (RemoteException e2) {
                    Slog.e(
                            notificationAssistants2.TAG,
                            "unable to notify assistant (snoozed): " + iNotificationListener2,
                            e2);
                    return;
                }
            default:
                NotificationManagerService.NotificationAssistants notificationAssistants3 =
                        this.f$0;
                String str3 = this.f$1;
                INotificationListener iNotificationListener3 = (INotificationListener) obj;
                notificationAssistants3.getClass();
                try {
                    iNotificationListener3.onNotificationDirectReply(str3);
                    break;
                } catch (RemoteException e3) {
                    Slog.e(
                            notificationAssistants3.TAG,
                            "unable to notify assistant (expanded): " + iNotificationListener3,
                            e3);
                }
        }
    }
}
