package com.android.systemui.statusbar.notification.collection.inflation;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.render.NotifViewController;
import kotlin.jvm.internal.DefaultConstructorMarker;

public interface NotifInflater {

    public interface InflationCallback {
        void onInflationFinished(NotificationEntry notificationEntry, NotifViewController notifViewController);
    }

    public final class Params {
        public final boolean isMinimized;
        public final boolean needsRedaction;
        public final String reason;

        public Params(boolean z, String str, boolean z2, boolean z3, boolean z4, boolean z5) {
            this.isMinimized = z;
            this.reason = str;
            this.needsRedaction = z5;
        }

        public /* synthetic */ Params(boolean z, String str, boolean z2, boolean z3, boolean z4, boolean z5, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(z, str, z2, (i & 8) != 0 ? false : z3, (i & 16) != 0 ? false : z4, z5);
        }
    }
}
