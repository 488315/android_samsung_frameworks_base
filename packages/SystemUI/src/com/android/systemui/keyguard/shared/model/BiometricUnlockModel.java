package com.android.systemui.keyguard.shared.model;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        int hashCode = this.mode.hashCode() * 31;
        BiometricUnlockSource biometricUnlockSource = this.source;
        return hashCode + (biometricUnlockSource == null ? 0 : biometricUnlockSource.hashCode());
    }

    public final String toString() {
        return "BiometricUnlockModel(mode=" + this.mode + ", source=" + this.source + ")";
    }
}
