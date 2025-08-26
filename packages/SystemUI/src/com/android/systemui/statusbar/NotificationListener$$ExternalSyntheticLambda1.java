package com.android.systemui.statusbar;

import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import com.android.systemui.statusbar.NotificationListener;
import java.util.ArrayList;

/* loaded from: classes3.dex */
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
        int i = 0;
        switch (this.$r8$classId) {
            case 0:
                NotificationListener notificationListener = this.f$0;
                StatusBarNotification[] statusBarNotificationArr = (StatusBarNotification[]) this.f$1;
                NotificationListenerService.RankingMap rankingMap = this.f$2;
                int i2 = NotificationListener.$r8$clinit;
                ArrayList arrayList = new ArrayList();
                for (StatusBarNotification statusBarNotification : statusBarNotificationArr) {
                    String key = statusBarNotification.getKey();
                    NotificationListenerService.Ranking ranking = new NotificationListenerService.Ranking();
                    if (!rankingMap.getRanking(key, ranking)) {
                        ranking.populate(key, 0, false, 0, 0, 0, null, null, null, new ArrayList(), new ArrayList(), false, 0, false, 0L, false, new ArrayList(), new ArrayList(), false, false, false, null, 0, false, 0, false, null);
                    }
                    arrayList.add(ranking);
                }
                NotificationListenerService.RankingMap rankingMap2 = new NotificationListenerService.RankingMap((NotificationListenerService.Ranking[]) arrayList.toArray(new NotificationListenerService.Ranking[0]));
                for (StatusBarNotification statusBarNotification2 : statusBarNotificationArr) {
                    ArrayList arrayList2 = (ArrayList) notificationListener.mNotificationHandlers;
                    int size = arrayList2.size();
                    int i3 = 0;
                    while (i3 < size) {
                        Object obj = arrayList2.get(i3);
                        i3++;
                        ((NotificationListener.NotificationHandler) obj).onNotificationPosted(statusBarNotification2, rankingMap2);
                    }
                }
                ArrayList arrayList3 = (ArrayList) notificationListener.mNotificationHandlers;
                int size2 = arrayList3.size();
                while (i < size2) {
                    Object obj2 = arrayList3.get(i);
                    i++;
                    ((NotificationListener.NotificationHandler) obj2).onNotificationsInitialized();
                }
                break;
            default:
                NotificationListener notificationListener2 = this.f$0;
                StatusBarNotification statusBarNotification3 = (StatusBarNotification) this.f$1;
                NotificationListenerService.RankingMap rankingMap3 = this.f$2;
                ArrayList arrayList4 = (ArrayList) notificationListener2.mNotificationHandlers;
                int size3 = arrayList4.size();
                while (i < size3) {
                    Object obj3 = arrayList4.get(i);
                    i++;
                    ((NotificationListener.NotificationHandler) obj3).onNotificationPosted(statusBarNotification3, rankingMap3);
                }
                break;
        }
    }
}
