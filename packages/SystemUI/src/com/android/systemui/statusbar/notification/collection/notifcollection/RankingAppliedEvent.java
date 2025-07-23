package com.android.systemui.statusbar.notification.collection.notifcollection;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class RankingAppliedEvent extends NotifEvent {
    public RankingAppliedEvent() {
        super("onRankingApplied", null);
    }

    @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifEvent
    public final void dispatchToListener(NotifCollectionListener notifCollectionListener) {
        notifCollectionListener.onRankingApplied();
    }
}
