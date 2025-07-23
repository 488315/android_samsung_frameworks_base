package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.headsup.PinnedStatus;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
interface HunMutator {
    void removeNotification(String str, boolean z);

    void updateNotification(String str, PinnedStatus pinnedStatus);
}
