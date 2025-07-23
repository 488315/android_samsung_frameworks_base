package com.android.systemui.qs.tiles.impl.modes.domain.model;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        int hashCode = Boolean.hashCode(this.isActivated) * 31;
        String str = this.extraStatus;
        return hashCode + (str == null ? 0 : str.hashCode());
    }

    public final String toString() {
        return "ModesDndTileModel(isActivated=" + this.isActivated + ", extraStatus=" + this.extraStatus + ")";
    }
}
