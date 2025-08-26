package com.android.systemui.statusbar.policy.domain.model;

import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public final class ActiveZenModes {
    public final ZenModeInfo mainMode;
    public final List modeNames;

    public ActiveZenModes(List<String> list, ZenModeInfo zenModeInfo) {
        this.modeNames = list;
        this.mainMode = zenModeInfo;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ActiveZenModes)) {
            return false;
        }
        ActiveZenModes activeZenModes = (ActiveZenModes) obj;
        return Intrinsics.areEqual(this.modeNames, activeZenModes.modeNames) && Intrinsics.areEqual(this.mainMode, activeZenModes.mainMode);
    }

    public final int hashCode() {
        int iHashCode = this.modeNames.hashCode() * 31;
        ZenModeInfo zenModeInfo = this.mainMode;
        return iHashCode + (zenModeInfo == null ? 0 : zenModeInfo.hashCode());
    }

    public final String toString() {
        return "ActiveZenModes(modeNames=" + this.modeNames + ", mainMode=" + this.mainMode + ")";
    }
}
