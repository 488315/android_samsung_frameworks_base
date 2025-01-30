package com.android.systemui.statusbar;

import android.app.Notification;
import android.app.RemoteInput;
import android.content.Context;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import com.android.systemui.statusbar.NotificationListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class NotificationListener$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ NotificationListener f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ NotificationListenerService.RankingMap f$2;

    public /* synthetic */ NotificationListener$$ExternalSyntheticLambda2(NotificationListener notificationListener, Object obj, NotificationListenerService.RankingMap rankingMap, int i) {
        this.$r8$classId = i;
        this.f$0 = notificationListener;
        this.f$1 = obj;
        this.f$2 = rankingMap;
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x006d  */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void run() {
        Bundle bundle;
        Notification.Action[] actionArr;
        RemoteInput[] remoteInputs;
        switch (this.$r8$classId) {
            case 0:
                NotificationListener notificationListener = this.f$0;
                StatusBarNotification statusBarNotification = (StatusBarNotification) this.f$1;
                NotificationListenerService.RankingMap rankingMap = this.f$2;
                notificationListener.getClass();
                Notification notification2 = statusBarNotification.getNotification();
                Context context = notificationListener.mContext;
                if (RemoteInputController.ENABLE_REMOTE_INPUT && (bundle = notification2.extras) != null && bundle.containsKey("android.wearable.EXTENSIONS") && ((actionArr = notification2.actions) == null || actionArr.length == 0)) {
                    List<Notification.Action> actions = new Notification.WearableExtender(notification2).getActions();
                    int size = actions.size();
                    Notification.Action action = null;
                    for (int i = 0; i < size; i++) {
                        Notification.Action action2 = actions.get(i);
                        if (action2 != null && (remoteInputs = action2.getRemoteInputs()) != null) {
                            int length = remoteInputs.length;
                            int i2 = 0;
                            while (true) {
                                if (i2 < length) {
                                    if (remoteInputs[i2].getAllowFreeFormInput()) {
                                        action = action2;
                                    } else {
                                        i2++;
                                    }
                                }
                            }
                            if (action != null) {
                                if (action != null) {
                                    Notification.Builder recoverBuilder = Notification.Builder.recoverBuilder(context, notification2);
                                    recoverBuilder.setActions(action);
                                    recoverBuilder.build();
                                }
                            }
                        }
                    }
                    if (action != null) {
                    }
                }
                Iterator it = ((ArrayList) notificationListener.mNotificationHandlers).iterator();
                while (it.hasNext()) {
                    ((NotificationListener.NotificationHandler) it.next()).onNotificationPosted(statusBarNotification, rankingMap);
                }
                break;
            default:
                NotificationListener notificationListener2 = this.f$0;
                StatusBarNotification[] statusBarNotificationArr = (StatusBarNotification[]) this.f$1;
                NotificationListenerService.RankingMap rankingMap2 = this.f$2;
                notificationListener2.getClass();
                ArrayList arrayList = new ArrayList();
                for (StatusBarNotification statusBarNotification2 : statusBarNotificationArr) {
                    String key = statusBarNotification2.getKey();
                    NotificationListenerService.Ranking ranking = new NotificationListenerService.Ranking();
                    if (!rankingMap2.getRanking(key, ranking)) {
                        ranking.populate(key, 0, false, 0, 0, 0, null, null, null, new ArrayList(), new ArrayList(), false, 0, false, 0L, false, new ArrayList(), new ArrayList(), false, false, false, null, 0, false, 0, false);
                    }
                    arrayList.add(ranking);
                }
                NotificationListenerService.RankingMap rankingMap3 = new NotificationListenerService.RankingMap((NotificationListenerService.Ranking[]) arrayList.toArray(new NotificationListenerService.Ranking[0]));
                for (StatusBarNotification statusBarNotification3 : statusBarNotificationArr) {
                    Iterator it2 = ((ArrayList) notificationListener2.mNotificationHandlers).iterator();
                    while (it2.hasNext()) {
                        ((NotificationListener.NotificationHandler) it2.next()).onNotificationPosted(statusBarNotification3, rankingMap3);
                    }
                }
                Iterator it3 = ((ArrayList) notificationListener2.mNotificationHandlers).iterator();
                while (it3.hasNext()) {
                    ((NotificationListener.NotificationHandler) it3.next()).onNotificationsInitialized();
                }
                break;
        }
    }
}
