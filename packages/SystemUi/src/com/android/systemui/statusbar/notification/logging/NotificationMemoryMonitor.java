package com.android.systemui.statusbar.notification.logging;

import com.android.systemui.flags.FeatureFlags;
import dagger.Lazy;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NotificationMemoryMonitor {
    public final FeatureFlags featureFlags;
    public final NotificationMemoryDumper notificationMemoryDumper;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public NotificationMemoryMonitor(FeatureFlags featureFlags, NotificationMemoryDumper notificationMemoryDumper, Lazy lazy) {
        this.featureFlags = featureFlags;
        this.notificationMemoryDumper = notificationMemoryDumper;
    }
}
