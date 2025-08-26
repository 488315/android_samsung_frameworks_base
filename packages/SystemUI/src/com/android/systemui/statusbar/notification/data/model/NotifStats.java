package com.android.systemui.statusbar.notification.data.model;

import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public final class NotifStats {
    public static final Companion Companion = new Companion(null);
    public static final NotifStats empty = new NotifStats(false, false, false, false);
    public final boolean hasClearableAlertingNotifs;
    public final boolean hasClearableSilentNotifs;
    public final boolean hasNonClearableAlertingNotifs;
    public final boolean hasNonClearableSilentNotifs;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public NotifStats(boolean z, boolean z2, boolean z3, boolean z4) {
        this.hasNonClearableAlertingNotifs = z;
        this.hasClearableAlertingNotifs = z2;
        this.hasNonClearableSilentNotifs = z3;
        this.hasClearableSilentNotifs = z4;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof NotifStats)) {
            return false;
        }
        NotifStats notifStats = (NotifStats) obj;
        return this.hasNonClearableAlertingNotifs == notifStats.hasNonClearableAlertingNotifs && this.hasClearableAlertingNotifs == notifStats.hasClearableAlertingNotifs && this.hasNonClearableSilentNotifs == notifStats.hasNonClearableSilentNotifs && this.hasClearableSilentNotifs == notifStats.hasClearableSilentNotifs;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.hasClearableSilentNotifs) + TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(Boolean.hashCode(this.hasNonClearableAlertingNotifs) * 31, 31, this.hasClearableAlertingNotifs), 31, this.hasNonClearableSilentNotifs);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("NotifStats(hasNonClearableAlertingNotifs=");
        sb.append(this.hasNonClearableAlertingNotifs);
        sb.append(", hasClearableAlertingNotifs=");
        sb.append(this.hasClearableAlertingNotifs);
        sb.append(", hasNonClearableSilentNotifs=");
        sb.append(this.hasNonClearableSilentNotifs);
        sb.append(", hasClearableSilentNotifs=");
        return MoveResult$$ExternalSyntheticOutline0.m(sb, this.hasClearableSilentNotifs, ")");
    }
}
