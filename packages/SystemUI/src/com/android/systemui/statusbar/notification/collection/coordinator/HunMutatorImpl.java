package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.headsup.HeadsUpManager;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManagerImpl;
import com.android.systemui.statusbar.notification.headsup.PinnedStatus;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Pair;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class HunMutatorImpl implements HunMutator {
    private final List<Pair<String, Boolean>> deferred = new ArrayList();
    private final HeadsUpManager headsUpManager;

    public HunMutatorImpl(HeadsUpManager headsUpManager) {
        this.headsUpManager = headsUpManager;
    }

    public final void commitModifications() {
        Iterator<T> it = this.deferred.iterator();
        while (it.hasNext()) {
            Pair pair = (Pair) it.next();
            ((HeadsUpManagerImpl) this.headsUpManager).removeNotification((String) pair.component1(), "commitModifications", ((Boolean) pair.component2()).booleanValue());
        }
        this.deferred.clear();
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.HunMutator
    public void removeNotification(String str, boolean z) {
        this.deferred.add(new Pair<>(str, Boolean.valueOf(z)));
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.HunMutator
    public void updateNotification(String str, PinnedStatus pinnedStatus) {
        ((HeadsUpManagerImpl) this.headsUpManager).updateNotification(str, pinnedStatus);
    }
}
