package com.android.systemui.qs.tiles.impl.modes.domain.model;

import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final class ModesDndTileModel {
    public final String extraStatus;
    public final boolean isActivated;

    public ModesDndTileModel(boolean z, String str) {
        this.isActivated = z;
        this.extraStatus = str;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ModesDndTileModel)) {
            return false;
        }
        ModesDndTileModel modesDndTileModel = (ModesDndTileModel) obj;
        return this.isActivated == modesDndTileModel.isActivated && Intrinsics.areEqual(this.extraStatus, modesDndTileModel.extraStatus);
    }

    public final int hashCode() {
        int iHashCode = Boolean.hashCode(this.isActivated) * 31;
        String str = this.extraStatus;
        return iHashCode + (str == null ? 0 : str.hashCode());
    }

    public final String toString() {
        return "ModesDndTileModel(isActivated=" + this.isActivated + ", extraStatus=" + this.extraStatus + ")";
    }
}
