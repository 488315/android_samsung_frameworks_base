package com.android.systemui.dreams;

import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import com.android.systemui.statusbar.NotificationListener;
import com.android.systemui.statusbar.policy.CallbackController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class DreamOverlayNotificationCountProvider implements CallbackController {
    public final C12741 mNotificationHandler;
    public final Set mNotificationKeys = new HashSet();
    public final List mCallbacks = new ArrayList();

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.systemui.dreams.DreamOverlayNotificationCountProvider$1, com.android.systemui.statusbar.NotificationListener$NotificationHandler] */
    public DreamOverlayNotificationCountProvider(final NotificationListener notificationListener, Executor executor) {
        ?? r0 = new NotificationListener.NotificationHandler() { // from class: com.android.systemui.dreams.DreamOverlayNotificationCountProvider.1
            @Override // com.android.systemui.statusbar.NotificationListener.NotificationHandler
            public final void onNotificationPosted(StatusBarNotification statusBarNotification, NotificationListenerService.RankingMap rankingMap) {
                if (statusBarNotification.isOngoing()) {
                    return;
                }
                DreamOverlayNotificationCountProvider dreamOverlayNotificationCountProvider = DreamOverlayNotificationCountProvider.this;
                ((HashSet) dreamOverlayNotificationCountProvider.mNotificationKeys).add(statusBarNotification.getKey());
                dreamOverlayNotificationCountProvider.reportNotificationCountChanged();
            }

            @Override // com.android.systemui.statusbar.NotificationListener.NotificationHandler
            public final void onNotificationRemoved(StatusBarNotification statusBarNotification, NotificationListenerService.RankingMap rankingMap, int i) {
                DreamOverlayNotificationCountProvider dreamOverlayNotificationCountProvider = DreamOverlayNotificationCountProvider.this;
                ((HashSet) dreamOverlayNotificationCountProvider.mNotificationKeys).remove(statusBarNotification.getKey());
                dreamOverlayNotificationCountProvider.reportNotificationCountChanged();
            }

            @Override // com.android.systemui.statusbar.NotificationListener.NotificationHandler
            public final void onNotificationRankingUpdate(NotificationListenerService.RankingMap rankingMap) {
            }

            @Override // com.android.systemui.statusbar.NotificationListener.NotificationHandler
            public final void onNotificationsInitialized() {
            }
        };
        this.mNotificationHandler = r0;
        notificationListener.addNotificationHandler(r0);
        executor.execute(new Runnable() { // from class: com.android.systemui.dreams.DreamOverlayNotificationCountProvider$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                final DreamOverlayNotificationCountProvider dreamOverlayNotificationCountProvider = DreamOverlayNotificationCountProvider.this;
                NotificationListener notificationListener2 = notificationListener;
                dreamOverlayNotificationCountProvider.getClass();
                Arrays.stream(notificationListener2.getActiveNotifications()).forEach(new Consumer() { // from class: com.android.systemui.dreams.DreamOverlayNotificationCountProvider$$ExternalSyntheticLambda1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ((HashSet) DreamOverlayNotificationCountProvider.this.mNotificationKeys).add(((StatusBarNotification) obj).getKey());
                    }
                });
                dreamOverlayNotificationCountProvider.reportNotificationCountChanged();
            }
        });
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void addCallback(Object obj) {
        DreamOverlayStatusBarViewController$$ExternalSyntheticLambda3 dreamOverlayStatusBarViewController$$ExternalSyntheticLambda3 = (DreamOverlayStatusBarViewController$$ExternalSyntheticLambda3) obj;
        ArrayList arrayList = (ArrayList) this.mCallbacks;
        if (arrayList.contains(dreamOverlayStatusBarViewController$$ExternalSyntheticLambda3)) {
            return;
        }
        arrayList.add(dreamOverlayStatusBarViewController$$ExternalSyntheticLambda3);
        dreamOverlayStatusBarViewController$$ExternalSyntheticLambda3.onNotificationCountChanged(((HashSet) this.mNotificationKeys).size());
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void removeCallback(Object obj) {
        ((ArrayList) this.mCallbacks).remove((DreamOverlayStatusBarViewController$$ExternalSyntheticLambda3) obj);
    }

    public final void reportNotificationCountChanged() {
        final int size = ((HashSet) this.mNotificationKeys).size();
        ((ArrayList) this.mCallbacks).forEach(new Consumer() { // from class: com.android.systemui.dreams.DreamOverlayNotificationCountProvider$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((DreamOverlayStatusBarViewController$$ExternalSyntheticLambda3) obj).onNotificationCountChanged(size);
            }
        });
    }
}
