package com.android.systemui.keyguard.shared.model;

/* loaded from: classes2.dex */
public final class BiometricUnlockModel {
    public final BiometricUnlockMode mode;
    public final BiometricUnlockSource source;

    public BiometricUnlockModel(BiometricUnlockMode biometricUnlockMode, BiometricUnlockSource biometricUnlockSource) {
        this.mode = biometricUnlockMode;
        this.source = biometricUnlockSource;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof BiometricUnlockModel)) {
            return false;
        }
        BiometricUnlockModel biometricUnlockModel = (BiometricUnlockModel) obj;
        return this.mode == biometricUnlockModel.mode && this.source == biometricUnlockModel.source;
    }

    public final int hashCode() {
        int iHashCode = this.mode.hashCode() * 31;
        BiometricUnlockSource biometricUnlockSource = this.source;
        return iHashCode + (biometricUnlockSource == null ? 0 : biometricUnlockSource.hashCode());
    }

    public final String toString() {
        return "BiometricUnlockModel(mode=" + this.mode + ", source=" + this.source + ")";
    }
}
