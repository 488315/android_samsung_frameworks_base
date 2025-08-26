package com.android.systemui.statusbar.disableflags.shared.model;

import defpackage.MoveResult$$ExternalSyntheticOutline0;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public final class DisableFlagsModel {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final boolean animate;
    public final boolean areNotificationIconsEnabled;
    public final int disable1;
    public final int disable2;
    public final boolean isClockEnabled;
    public final boolean isSystemInfoEnabled;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public DisableFlagsModel() {
        this(0, 0, false, 7, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DisableFlagsModel)) {
            return false;
        }
        DisableFlagsModel disableFlagsModel = (DisableFlagsModel) obj;
        return this.disable1 == disableFlagsModel.disable1 && this.disable2 == disableFlagsModel.disable2 && this.animate == disableFlagsModel.animate;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.animate) + ReorderTile$$ExternalSyntheticOutline0.m(this.disable2, Integer.hashCode(this.disable1) * 31, 31);
    }

    public final boolean isQuickSettingsEnabled() {
        return (this.disable2 & 1) == 0;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("DisableFlagsModel(disable1=");
        sb.append(this.disable1);
        sb.append(", disable2=");
        sb.append(this.disable2);
        sb.append(", animate=");
        return MoveResult$$ExternalSyntheticOutline0.m(sb, this.animate, ")");
    }

    public DisableFlagsModel(int i, int i2, boolean z) {
        this.disable1 = i;
        this.disable2 = i2;
        this.animate = z;
        boolean z2 = false;
        this.isClockEnabled = (8388608 & i) == 0;
        this.areNotificationIconsEnabled = (131072 & i) == 0;
        if ((i & 1048576) == 0 && (i2 & 2) == 0) {
            z2 = true;
        }
        this.isSystemInfoEnabled = z2;
    }

    public /* synthetic */ DisableFlagsModel(int i, int i2, boolean z, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this((i3 & 1) != 0 ? 0 : i, (i3 & 2) != 0 ? 0 : i2, (i3 & 4) != 0 ? false : z);
    }
}
