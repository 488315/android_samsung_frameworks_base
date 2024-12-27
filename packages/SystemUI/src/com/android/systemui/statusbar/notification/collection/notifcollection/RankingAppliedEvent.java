package com.android.systemui.statusbar.notification.collection.notifcollection;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class RankingAppliedEvent extends NotifEvent {
    public RankingAppliedEvent() {
        super("onRankingApplied", null);
    }

    @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifEvent
    public final void dispatchToListener(NotifCollectionListener notifCollectionListener) {
        notifCollectionListener.onRankingApplied();
    }
}
