package com.android.systemui.statusbar.notification.collection.notifcollection;

import android.service.notification.NotificationListenerService;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
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
