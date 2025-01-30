package com.android.systemui.statusbar.notification.stack;

import com.android.systemui.dump.DumpManager;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
