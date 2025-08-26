package com.android.systemui.statusbar.notification.collection.coordinator;

import android.app.NotificationChannel;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.PipelineEntry;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public final class BundleUtil {
    public static final int $stable = 0;
    public static final Companion Companion = new Companion(null);

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final boolean isClassified(PipelineEntry pipelineEntry) {
            if (!(pipelineEntry instanceof NotificationEntry)) {
                return false;
            }
            NotificationEntry notificationEntry = (NotificationEntry) pipelineEntry;
            notificationEntry.getClass();
            if (notificationEntry.mRanking.getChannel() == null) {
                return false;
            }
            notificationEntry.getClass();
            return NotificationChannel.SYSTEM_RESERVED_IDS.contains(notificationEntry.mRanking.getChannel().getId());
        }

        private Companion() {
        }
    }
}
