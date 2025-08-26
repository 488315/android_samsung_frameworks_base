package com.android.systemui.statusbar.policy.domain.model;

import com.android.settingslib.notification.modes.ZenIcon;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public final class ZenModeInfo {
    public final ZenIcon icon;
    public final String name;

    public ZenModeInfo(String str, ZenIcon zenIcon) {
        this.name = str;
        this.icon = zenIcon;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ZenModeInfo)) {
            return false;
        }
        ZenModeInfo zenModeInfo = (ZenModeInfo) obj;
        return Intrinsics.areEqual(this.name, zenModeInfo.name) && Intrinsics.areEqual(this.icon, zenModeInfo.icon);
    }

    public final int hashCode() {
        return this.icon.hashCode() + (this.name.hashCode() * 31);
    }

    public final String toString() {
        return "ZenModeInfo(name=" + this.name + ", icon=" + this.icon + ")";
    }
}
