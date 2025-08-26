package com.android.systemui.statusbar.notification.headsup;

import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public final class HeadsUpAnimationEvent {
    public final boolean hasStatusBarChip;
    public final boolean isHeadsUpAppearance;
    public final ExpandableNotificationRow row;

    public HeadsUpAnimationEvent(ExpandableNotificationRow expandableNotificationRow, boolean z, boolean z2) {
        this.row = expandableNotificationRow;
        this.isHeadsUpAppearance = z;
        this.hasStatusBarChip = z2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof HeadsUpAnimationEvent)) {
            return false;
        }
        HeadsUpAnimationEvent headsUpAnimationEvent = (HeadsUpAnimationEvent) obj;
        return Intrinsics.areEqual(this.row, headsUpAnimationEvent.row) && this.isHeadsUpAppearance == headsUpAnimationEvent.isHeadsUpAppearance && this.hasStatusBarChip == headsUpAnimationEvent.hasStatusBarChip;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.hasStatusBarChip) + TransitionData$$ExternalSyntheticOutline0.m(this.row.hashCode() * 31, 31, this.isHeadsUpAppearance);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("HeadsUpAnimationEvent(row=");
        sb.append(this.row);
        sb.append(", isHeadsUpAppearance=");
        sb.append(this.isHeadsUpAppearance);
        sb.append(", hasStatusBarChip=");
        return MoveResult$$ExternalSyntheticOutline0.m(sb, this.hasStatusBarChip, ")");
    }
}
