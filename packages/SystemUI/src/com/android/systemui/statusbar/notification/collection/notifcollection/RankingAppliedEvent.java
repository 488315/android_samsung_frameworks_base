package com.android.systemui.statusbar.notification.collection.notifcollection;

public final class RankingAppliedEvent extends NotifEvent {
    public RankingAppliedEvent() {
        super("onRankingApplied", null);
    }

    @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifEvent
    public final void dispatchToListener(NotifCollectionListener notifCollectionListener) {
        notifCollectionListener.onRankingApplied();
    }
}
