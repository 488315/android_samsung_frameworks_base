package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.policy.BaseHeadsUpManager;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Pair;

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
            ((BaseHeadsUpManager) this.headsUpManager).removeNotification$1((String) pair.component1(), ((Boolean) pair.component2()).booleanValue());
        }
        this.deferred.clear();
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.HunMutator
    public void removeNotification(String str, boolean z) {
        this.deferred.add(new Pair<>(str, Boolean.valueOf(z)));
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.HunMutator
    public void updateNotification(String str, boolean z) {
        ((BaseHeadsUpManager) this.headsUpManager).updateNotification(str, z);
    }
}
