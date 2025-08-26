package com.android.systemui.statusbar.notification.collection.inflation;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.render.NotifViewController;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public interface NotifInflater {

    public interface InflationCallback {
        void onInflationFinished(NotificationEntry notificationEntry, NotifViewController notifViewController);
    }

    public final class Params {
        public final boolean isChildInGroup;
        public final boolean isMinimized;
        public final String reason;
        public final int redactionType;

        public Params(boolean z, String str, boolean z2, boolean z3, boolean z4, int i) {
            this.isMinimized = z;
            this.reason = str;
            this.isChildInGroup = z3;
            this.redactionType = i;
        }

        public /* synthetic */ Params(boolean z, String str, boolean z2, boolean z3, boolean z4, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
            this(z, str, z2, (i2 & 8) != 0 ? false : z3, (i2 & 16) != 0 ? false : z4, i);
        }
    }
}
