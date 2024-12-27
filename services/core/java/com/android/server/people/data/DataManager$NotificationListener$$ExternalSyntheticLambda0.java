package com.android.server.people.data;

import android.service.notification.StatusBarNotification;
import android.util.ArrayMap;
import android.util.Pair;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

public final /* synthetic */ class DataManager$NotificationListener$$ExternalSyntheticLambda0
        implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DataManager.NotificationListener f$0;
    public final /* synthetic */ StatusBarNotification f$1;
    public final /* synthetic */ String f$2;

    public /* synthetic */ DataManager$NotificationListener$$ExternalSyntheticLambda0(
            DataManager.NotificationListener notificationListener,
            StatusBarNotification statusBarNotification,
            String str,
            int i) {
        this.$r8$classId = i;
        this.f$0 = notificationListener;
        this.f$1 = statusBarNotification;
        this.f$2 = str;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                DataManager.NotificationListener notificationListener = this.f$0;
                StatusBarNotification statusBarNotification = this.f$1;
                String str = this.f$2;
                notificationListener.getClass();
                Pair create = Pair.create(statusBarNotification.getPackageName(), str);
                synchronized (notificationListener) {
                    try {
                        final int i = 0;
                        Set set =
                                (Set)
                                        notificationListener.mActiveNotifKeys.computeIfAbsent(
                                                create,
                                                new Function() { // from class:
                                                                 // com.android.server.people.data.DataManager$NotificationListener$$ExternalSyntheticLambda2
                                                    @Override // java.util.function.Function
                                                    public final Object apply(Object obj2) {
                                                        switch (i) {
                                                        }
                                                        return new HashSet();
                                                    }
                                                });
                        set.remove(statusBarNotification.getKey());
                        if (set.isEmpty()) {
                            ((ArrayMap) notificationListener.mActiveNotifKeys).remove(create);
                            notificationListener.this$0.cleanupCachedShortcuts(
                                    notificationListener.mUserId);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                return;
            default:
                DataManager.NotificationListener notificationListener2 = this.f$0;
                StatusBarNotification statusBarNotification2 = this.f$1;
                String str2 = this.f$2;
                synchronized (notificationListener2) {
                    final int i2 = 1;
                    ((Set)
                                    notificationListener2.mActiveNotifKeys.computeIfAbsent(
                                            Pair.create(
                                                    statusBarNotification2.getPackageName(), str2),
                                            new Function() { // from class:
                                                             // com.android.server.people.data.DataManager$NotificationListener$$ExternalSyntheticLambda2
                                                @Override // java.util.function.Function
                                                public final Object apply(Object obj2) {
                                                    switch (i2) {
                                                    }
                                                    return new HashSet();
                                                }
                                            }))
                            .add(statusBarNotification2.getKey());
                }
                return;
        }
    }
}
