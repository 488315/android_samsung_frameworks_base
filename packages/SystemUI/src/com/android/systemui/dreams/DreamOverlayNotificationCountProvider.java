package com.android.systemui.dreams;

import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import com.android.systemui.ambient.statusbar.ui.AmbientStatusBarViewController$$ExternalSyntheticLambda2;
import com.android.systemui.statusbar.NotificationListener;
import com.android.systemui.statusbar.policy.CallbackController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class DreamOverlayNotificationCountProvider implements CallbackController {
    public final AnonymousClass1 mNotificationHandler;
    public final Set mNotificationKeys = new HashSet();
    public final List mCallbacks = new ArrayList();

    public DreamOverlayNotificationCountProvider(final NotificationListener notificationListener, Executor executor) {
        notificationListener.addNotificationHandler(new NotificationListener.NotificationHandler() { // from class: com.android.systemui.dreams.DreamOverlayNotificationCountProvider.1
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
            public final void onNotificationsInitialized() {
            }

            @Override // com.android.systemui.statusbar.NotificationListener.NotificationHandler
            public final void onNotificationRankingUpdate(NotificationListenerService.RankingMap rankingMap) {
            }
        });
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
        AmbientStatusBarViewController$$ExternalSyntheticLambda2 ambientStatusBarViewController$$ExternalSyntheticLambda2 = (AmbientStatusBarViewController$$ExternalSyntheticLambda2) obj;
        if (((ArrayList) this.mCallbacks).contains(ambientStatusBarViewController$$ExternalSyntheticLambda2)) {
            return;
        }
        ((ArrayList) this.mCallbacks).add(ambientStatusBarViewController$$ExternalSyntheticLambda2);
        ambientStatusBarViewController$$ExternalSyntheticLambda2.onNotificationCountChanged(((HashSet) this.mNotificationKeys).size());
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void removeCallback(Object obj) {
        ((ArrayList) this.mCallbacks).remove((AmbientStatusBarViewController$$ExternalSyntheticLambda2) obj);
    }

    public final void reportNotificationCountChanged() {
        final int size = ((HashSet) this.mNotificationKeys).size();
        ((ArrayList) this.mCallbacks).forEach(new Consumer() { // from class: com.android.systemui.dreams.DreamOverlayNotificationCountProvider$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((AmbientStatusBarViewController$$ExternalSyntheticLambda2) obj).onNotificationCountChanged(size);
            }
        });
    }
}
