package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.headsup.PinnedStatus;

/* loaded from: classes3.dex */
interface HunMutator {
    void removeNotification(String str, boolean z);

    void updateNotification(String str, PinnedStatus pinnedStatus);
}
