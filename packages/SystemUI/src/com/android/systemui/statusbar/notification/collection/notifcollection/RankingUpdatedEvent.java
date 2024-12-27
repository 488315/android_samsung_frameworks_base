package com.android.systemui.statusbar.notification.collection.notifcollection;

import android.service.notification.NotificationListenerService;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class RankingUpdatedEvent extends NotifEvent {
    public final NotificationListenerService.RankingMap rankingMap;

    public RankingUpdatedEvent(NotificationListenerService.RankingMap rankingMap) {
        super("onRankingUpdate", null);
        this.rankingMap = rankingMap;
    }

    @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifEvent
    public final void dispatchToListener(NotifCollectionListener notifCollectionListener) {
        notifCollectionListener.onRankingUpdate(this.rankingMap);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof RankingUpdatedEvent) && Intrinsics.areEqual(this.rankingMap, ((RankingUpdatedEvent) obj).rankingMap);
    }

    public final int hashCode() {
        return this.rankingMap.hashCode();
    }

    public final String toString() {
        return "RankingUpdatedEvent(rankingMap=" + this.rankingMap + ")";
    }
}
