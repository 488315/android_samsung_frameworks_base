package com.android.systemui.statusbar.phone.fragment;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class StatusBarVisibilityModel {
    public static final Companion Companion = new Companion(null);
    public final boolean showClock;
    public final boolean showNotificationIcons;
    public final boolean showOngoingCallChip;
    public final boolean showSystemInfo;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static StatusBarVisibilityModel createModelFromFlags(int i, int i2) {
            return new StatusBarVisibilityModel((8388608 & i) == 0, (131072 & i) == 0, (67108864 & i) == 0, (i & QuickStepContract.SYSUI_STATE_IME_SWITCHER_SHOWING) == 0 && (i2 & 2) == 0);
        }
    }

    public StatusBarVisibilityModel(boolean z, boolean z2, boolean z3, boolean z4) {
        this.showClock = z;
        this.showNotificationIcons = z2;
        this.showOngoingCallChip = z3;
        this.showSystemInfo = z4;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof StatusBarVisibilityModel)) {
            return false;
        }
        StatusBarVisibilityModel statusBarVisibilityModel = (StatusBarVisibilityModel) obj;
        return this.showClock == statusBarVisibilityModel.showClock && this.showNotificationIcons == statusBarVisibilityModel.showNotificationIcons && this.showOngoingCallChip == statusBarVisibilityModel.showOngoingCallChip && this.showSystemInfo == statusBarVisibilityModel.showSystemInfo;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final int hashCode() {
        boolean z = this.showClock;
        int i = z;
        if (z != 0) {
            i = 1;
        }
        int i2 = i * 31;
        boolean z2 = this.showNotificationIcons;
        int i3 = z2;
        if (z2 != 0) {
            i3 = 1;
        }
        int i4 = (i2 + i3) * 31;
        boolean z3 = this.showOngoingCallChip;
        int i5 = z3;
        if (z3 != 0) {
            i5 = 1;
        }
        int i6 = (i4 + i5) * 31;
        boolean z4 = this.showSystemInfo;
        return i6 + (z4 ? 1 : z4 ? 1 : 0);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("StatusBarVisibilityModel(showClock=");
        sb.append(this.showClock);
        sb.append(", showNotificationIcons=");
        sb.append(this.showNotificationIcons);
        sb.append(", showOngoingCallChip=");
        sb.append(this.showOngoingCallChip);
        sb.append(", showSystemInfo=");
        return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m6m(sb, this.showSystemInfo, ")");
    }
}
