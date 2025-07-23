package com.android.systemui.statusbar.notification.collection.inflation;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.render.NotifViewController;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface NotifInflater {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface InflationCallback {
        void onInflationFinished(NotificationEntry notificationEntry, NotifViewController notifViewController);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
