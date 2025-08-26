package com.android.systemui.statusbar.phone.fragment;

import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public final class StatusBarVisibilityModel {
    public static final Companion Companion = new Companion(null);
    public final boolean showClock;
    public final boolean showNotificationIcons;
    public final boolean showOngoingCallChip;
    public final boolean showPrimaryOngoingActivityChip;
    public final boolean showSecondaryOngoingActivityChip;
    public final boolean showSystemInfo;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static StatusBarVisibilityModel createModelFromFlags(int i, int i2) {
            boolean z;
            boolean z2 = false;
            boolean z3 = (8388608 & i) == 0;
            if ((131072 & i) == 0) {
                z = false;
                z2 = true;
            } else {
                z = false;
            }
            int i3 = 67108864 & i;
            boolean z4 = true;
            boolean z5 = i3 != 0 ? z : true;
            boolean z6 = z;
            if (i3 == 0) {
                z = true;
            }
            boolean z7 = i3 == 0 ? true : z6;
            if ((i & 1048576) != 0 || (i2 & 2) != 0) {
                z4 = z6;
            }
            return new StatusBarVisibilityModel(z3, z2, z5, z, z7, z4);
        }

        private Companion() {
        }
    }

    public StatusBarVisibilityModel(boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6) {
        this.showClock = z;
        this.showNotificationIcons = z2;
        this.showOngoingCallChip = z3;
        this.showPrimaryOngoingActivityChip = z4;
        this.showSecondaryOngoingActivityChip = z5;
        this.showSystemInfo = z6;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof StatusBarVisibilityModel)) {
            return false;
        }
        StatusBarVisibilityModel statusBarVisibilityModel = (StatusBarVisibilityModel) obj;
        return this.showClock == statusBarVisibilityModel.showClock && this.showNotificationIcons == statusBarVisibilityModel.showNotificationIcons && this.showOngoingCallChip == statusBarVisibilityModel.showOngoingCallChip && this.showPrimaryOngoingActivityChip == statusBarVisibilityModel.showPrimaryOngoingActivityChip && this.showSecondaryOngoingActivityChip == statusBarVisibilityModel.showSecondaryOngoingActivityChip && this.showSystemInfo == statusBarVisibilityModel.showSystemInfo;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.showSystemInfo) + TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(Boolean.hashCode(this.showClock) * 31, 31, this.showNotificationIcons), 31, this.showOngoingCallChip), 31, this.showPrimaryOngoingActivityChip), 31, this.showSecondaryOngoingActivityChip);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("StatusBarVisibilityModel(showClock=");
        sb.append(this.showClock);
        sb.append(", showNotificationIcons=");
        sb.append(this.showNotificationIcons);
        sb.append(", showOngoingCallChip=");
        sb.append(this.showOngoingCallChip);
        sb.append(", showPrimaryOngoingActivityChip=");
        sb.append(this.showPrimaryOngoingActivityChip);
        sb.append(", showSecondaryOngoingActivityChip=");
        sb.append(this.showSecondaryOngoingActivityChip);
        sb.append(", showSystemInfo=");
        return MoveResult$$ExternalSyntheticOutline0.m(sb, this.showSystemInfo, ")");
    }
}
