package com.android.systemui.wmshell;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.notifcollection.DismissedByUserStats;
import com.android.systemui.statusbar.notification.collection.provider.NotificationVisibilityProviderImpl;
import com.android.systemui.statusbar.notification.collection.render.NotificationVisibilityProvider;
import com.android.systemui.wmshell.BubblesManager;
import java.util.ArrayList;
import java.util.List;
import java.util.function.IntConsumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class BubblesManager$$ExternalSyntheticLambda3 implements IntConsumer {
    public final /* synthetic */ BubblesManager f$0;
    public final /* synthetic */ List f$1;
    public final /* synthetic */ NotificationEntry f$2;

    public /* synthetic */ BubblesManager$$ExternalSyntheticLambda3(BubblesManager bubblesManager, List list, NotificationEntry notificationEntry) {
        this.f$0 = bubblesManager;
        this.f$1 = list;
        this.f$2 = notificationEntry;
    }

    @Override // java.util.function.IntConsumer
    public final void accept(int i) {
        BubblesManager bubblesManager = this.f$0;
        List list = this.f$1;
        NotificationEntry notificationEntry = this.f$2;
        NotificationVisibilityProvider notificationVisibilityProvider = bubblesManager.mVisibilityProvider;
        int i2 = 0;
        if (i < 0) {
            ArrayList arrayList = (ArrayList) bubblesManager.mCallbacks;
            int size = arrayList.size();
            while (i2 < size) {
                Object obj = arrayList.get(i2);
                i2++;
                ((BubblesManager.NotifCallback) obj).removeNotification(notificationEntry, new DismissedByUserStats(4, 1, ((NotificationVisibilityProviderImpl) notificationVisibilityProvider).obtain(notificationEntry)), 12);
            }
            return;
        }
        ArrayList arrayList2 = (ArrayList) bubblesManager.mCallbacks;
        int size2 = arrayList2.size();
        while (i2 < size2) {
            Object obj2 = arrayList2.get(i2);
            i2++;
            ArrayList arrayList3 = (ArrayList) list;
            ((BubblesManager.NotifCallback) obj2).removeNotification((NotificationEntry) arrayList3.get(i), new DismissedByUserStats(4, 1, ((NotificationVisibilityProviderImpl) notificationVisibilityProvider).obtain((NotificationEntry) arrayList3.get(i))), 12);
        }
    }
}
