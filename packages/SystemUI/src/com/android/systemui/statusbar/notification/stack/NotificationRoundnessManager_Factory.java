package com.android.systemui.statusbar.notification.stack;

import com.android.systemui.dump.DumpManager;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class NotificationRoundnessManager_Factory implements Provider {
    public final Provider dumpManagerProvider;

    public NotificationRoundnessManager_Factory(Provider provider) {
        this.dumpManagerProvider = provider;
    }

    public static NotificationRoundnessManager newInstance(DumpManager dumpManager) {
        return new NotificationRoundnessManager(dumpManager);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new NotificationRoundnessManager((DumpManager) this.dumpManagerProvider.get());
    }
}
