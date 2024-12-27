package com.android.systemui.statusbar.notification.collection.inflation;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.render.NotifViewController;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface NotifInflater {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface InflationCallback {
        void onInflationFinished(NotificationEntry notificationEntry, NotifViewController notifViewController);
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
