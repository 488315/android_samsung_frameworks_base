package com.android.systemui.statusbar;

import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import com.android.systemui.statusbar.NotificationListener;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final /* synthetic */ class NotificationListener$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ NotificationListener f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ NotificationListenerService.RankingMap f$2;

    public /* synthetic */ NotificationListener$$ExternalSyntheticLambda1(NotificationListener notificationListener, Object obj, NotificationListenerService.RankingMap rankingMap, int i) {
        this.$r8$classId = i;
        this.f$0 = notificationListener;
        this.f$1 = obj;
        this.f$2 = rankingMap;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                NotificationListener notificationListener = this.f$0;
                StatusBarNotification[] statusBarNotificationArr = (StatusBarNotification[]) this.f$1;
                NotificationListenerService.RankingMap rankingMap = this.f$2;
                notificationListener.getClass();
                ArrayList arrayList = new ArrayList();
                for (StatusBarNotification statusBarNotification : statusBarNotificationArr) {
                    String key = statusBarNotification.getKey();
                    NotificationListenerService.Ranking ranking = new NotificationListenerService.Ranking();
                    if (!rankingMap.getRanking(key, ranking)) {
                        ranking.populate(key, 0, false, 0, 0, 0, null, null, null, new ArrayList(), new ArrayList(), false, 0, false, 0L, false, new ArrayList(), new ArrayList(), false, false, false, null, 0, false, 0, false);
                    }
                    arrayList.add(ranking);
                }
                NotificationListenerService.RankingMap rankingMap2 = new NotificationListenerService.RankingMap((NotificationListenerService.Ranking[]) arrayList.toArray(new NotificationListenerService.Ranking[0]));
                for (StatusBarNotification statusBarNotification2 : statusBarNotificationArr) {
                    Iterator it = ((ArrayList) notificationListener.mNotificationHandlers).iterator();
                    while (it.hasNext()) {
                        ((NotificationListener.NotificationHandler) it.next()).onNotificationPosted(statusBarNotification2, rankingMap2);
                    }
                }
                Iterator it2 = ((ArrayList) notificationListener.mNotificationHandlers).iterator();
                while (it2.hasNext()) {
                    ((NotificationListener.NotificationHandler) it2.next()).onNotificationsInitialized();
                }
                break;
            default:
                NotificationListener notificationListener2 = this.f$0;
                StatusBarNotification statusBarNotification3 = (StatusBarNotification) this.f$1;
                NotificationListenerService.RankingMap rankingMap3 = this.f$2;
                Iterator it3 = ((ArrayList) notificationListener2.mNotificationHandlers).iterator();
                while (it3.hasNext()) {
                    ((NotificationListener.NotificationHandler) it3.next()).onNotificationPosted(statusBarNotification3, rankingMap3);
                }
                break;
        }
    }
}
