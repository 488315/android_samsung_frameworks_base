package com.android.systemui.statusbar.notification.collection.render;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NotifStats {
    public static final Companion Companion = new Companion(null);
    public static final NotifStats empty = new NotifStats(0, false, false, false, false);
    public final boolean hasClearableAlertingNotifs;
    public final boolean hasClearableSilentNotifs;
    public final boolean hasNonClearableAlertingNotifs;
    public final boolean hasNonClearableSilentNotifs;
    public final int numActiveNotifs;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public NotifStats(int i, boolean z, boolean z2, boolean z3, boolean z4) {
        this.numActiveNotifs = i;
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
        return this.numActiveNotifs == notifStats.numActiveNotifs && this.hasNonClearableAlertingNotifs == notifStats.hasNonClearableAlertingNotifs && this.hasClearableAlertingNotifs == notifStats.hasClearableAlertingNotifs && this.hasNonClearableSilentNotifs == notifStats.hasNonClearableSilentNotifs && this.hasClearableSilentNotifs == notifStats.hasClearableSilentNotifs;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final int hashCode() {
        int hashCode = Integer.hashCode(this.numActiveNotifs) * 31;
        boolean z = this.hasNonClearableAlertingNotifs;
        int i = z;
        if (z != 0) {
            i = 1;
        }
        int i2 = (hashCode + i) * 31;
        boolean z2 = this.hasClearableAlertingNotifs;
        int i3 = z2;
        if (z2 != 0) {
            i3 = 1;
        }
        int i4 = (i2 + i3) * 31;
        boolean z3 = this.hasNonClearableSilentNotifs;
        int i5 = z3;
        if (z3 != 0) {
            i5 = 1;
        }
        int i6 = (i4 + i5) * 31;
        boolean z4 = this.hasClearableSilentNotifs;
        return i6 + (z4 ? 1 : z4 ? 1 : 0);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("NotifStats(numActiveNotifs=");
        sb.append(this.numActiveNotifs);
        sb.append(", hasNonClearableAlertingNotifs=");
        sb.append(this.hasNonClearableAlertingNotifs);
        sb.append(", hasClearableAlertingNotifs=");
        sb.append(this.hasClearableAlertingNotifs);
        sb.append(", hasNonClearableSilentNotifs=");
        sb.append(this.hasNonClearableSilentNotifs);
        sb.append(", hasClearableSilentNotifs=");
        return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m6m(sb, this.hasClearableSilentNotifs, ")");
    }
}
