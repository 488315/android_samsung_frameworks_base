package com.android.systemui.statusbar.notification.stack;

import com.android.systemui.statusbar.notification.Roundable;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import kotlin.jvm.internal.Intrinsics;

public final class RoundableTargets {
    public final Roundable after;
    public final Roundable before;
    public final ExpandableNotificationRow swiped;

    public RoundableTargets(Roundable roundable, ExpandableNotificationRow expandableNotificationRow, Roundable roundable2) {
        this.before = roundable;
        this.swiped = expandableNotificationRow;
        this.after = roundable2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof RoundableTargets)) {
            return false;
        }
        RoundableTargets roundableTargets = (RoundableTargets) obj;
        return Intrinsics.areEqual(this.before, roundableTargets.before) && Intrinsics.areEqual(this.swiped, roundableTargets.swiped) && Intrinsics.areEqual(this.after, roundableTargets.after);
    }

    public final int hashCode() {
        Roundable roundable = this.before;
        int hashCode = (roundable == null ? 0 : roundable.hashCode()) * 31;
        ExpandableNotificationRow expandableNotificationRow = this.swiped;
        int hashCode2 = (hashCode + (expandableNotificationRow == null ? 0 : expandableNotificationRow.hashCode())) * 31;
        Roundable roundable2 = this.after;
        return hashCode2 + (roundable2 != null ? roundable2.hashCode() : 0);
    }

    public final String toString() {
        return "RoundableTargets(before=" + this.before + ", swiped=" + this.swiped + ", after=" + this.after + ")";
    }
}
