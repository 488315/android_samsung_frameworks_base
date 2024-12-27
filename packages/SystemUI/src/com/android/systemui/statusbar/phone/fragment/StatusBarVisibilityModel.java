package com.android.systemui.statusbar.phone.fragment;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class StatusBarVisibilityModel {
    public static final Companion Companion = new Companion(null);
    public final boolean showClock;
    public final boolean showNotificationIcons;
    public final boolean showOngoingActivityChip;
    public final boolean showOngoingCallChip;
    public final boolean showSystemInfo;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public static StatusBarVisibilityModel createModelFromFlags(int i, int i2) {
            int i3 = 67108864 & i;
            return new StatusBarVisibilityModel((8388608 & i) == 0, (131072 & i) == 0, i3 == 0, i3 == 0, (i & QuickStepContract.SYSUI_STATE_IME_SWITCHER_SHOWING) == 0 && (i2 & 2) == 0);
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public StatusBarVisibilityModel(boolean z, boolean z2, boolean z3, boolean z4, boolean z5) {
        this.showClock = z;
        this.showNotificationIcons = z2;
        this.showOngoingCallChip = z3;
        this.showOngoingActivityChip = z4;
        this.showSystemInfo = z5;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof StatusBarVisibilityModel)) {
            return false;
        }
        StatusBarVisibilityModel statusBarVisibilityModel = (StatusBarVisibilityModel) obj;
        return this.showClock == statusBarVisibilityModel.showClock && this.showNotificationIcons == statusBarVisibilityModel.showNotificationIcons && this.showOngoingCallChip == statusBarVisibilityModel.showOngoingCallChip && this.showOngoingActivityChip == statusBarVisibilityModel.showOngoingActivityChip && this.showSystemInfo == statusBarVisibilityModel.showSystemInfo;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.showSystemInfo) + TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(Boolean.hashCode(this.showClock) * 31, 31, this.showNotificationIcons), 31, this.showOngoingCallChip), 31, this.showOngoingActivityChip);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("StatusBarVisibilityModel(showClock=");
        sb.append(this.showClock);
        sb.append(", showNotificationIcons=");
        sb.append(this.showNotificationIcons);
        sb.append(", showOngoingCallChip=");
        sb.append(this.showOngoingCallChip);
        sb.append(", showOngoingActivityChip=");
        sb.append(this.showOngoingActivityChip);
        sb.append(", showSystemInfo=");
        return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(sb, this.showSystemInfo, ")");
    }
}
