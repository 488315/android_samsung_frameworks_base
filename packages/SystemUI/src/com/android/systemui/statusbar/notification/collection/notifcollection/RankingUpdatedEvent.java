package com.android.systemui.statusbar.notification.collection.notifcollection;

import android.service.notification.NotificationListenerService;
import kotlin.jvm.internal.Intrinsics;

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
