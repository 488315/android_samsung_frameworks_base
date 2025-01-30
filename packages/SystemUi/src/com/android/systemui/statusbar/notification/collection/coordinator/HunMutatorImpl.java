package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.policy.HeadsUpManager;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class HunMutatorImpl {
    public final List deferred = new ArrayList();
    public final HeadsUpManager headsUpManager;

    public HunMutatorImpl(HeadsUpManager headsUpManager) {
        this.headsUpManager = headsUpManager;
    }
}
