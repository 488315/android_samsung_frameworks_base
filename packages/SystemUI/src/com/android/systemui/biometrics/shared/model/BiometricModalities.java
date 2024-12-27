package com.android.systemui.biometrics.shared.model;

import android.hardware.face.FaceSensorPropertiesInternal;
import android.hardware.fingerprint.FingerprintSensorPropertiesInternal;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class BiometricModalities {
    public final FaceSensorPropertiesInternal faceProperties;
    public final FingerprintSensorPropertiesInternal fingerprintProperties;

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public BiometricModalities() {
        /*
            r2 = this;
            r0 = 0
            r1 = 3
            r2.<init>(r0, r0, r1, r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.biometrics.shared.model.BiometricModalities.<init>():void");
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof BiometricModalities)) {
            return false;
        }
        BiometricModalities biometricModalities = (BiometricModalities) obj;
        return Intrinsics.areEqual(this.fingerprintProperties, biometricModalities.fingerprintProperties) && Intrinsics.areEqual(this.faceProperties, biometricModalities.faceProperties);
    }

    public final boolean getHasFaceAndFingerprint() {
        return getHasFingerprint() && this.faceProperties != null;
    }

    public final boolean getHasFaceOnly() {
        return (this.faceProperties == null || getHasFingerprint()) ? false : true;
    }

    public final boolean getHasFingerprint() {
        return this.fingerprintProperties != null;
    }

    public final boolean getHasSfps() {
        if (getHasFingerprint()) {
            FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal = this.fingerprintProperties;
            Intrinsics.checkNotNull(fingerprintSensorPropertiesInternal);
            if (fingerprintSensorPropertiesInternal.isAnySidefpsType()) {
                return true;
            }
        }
        return false;
    }

    public final boolean getHasUdfps() {
        if (getHasFingerprint()) {
            FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal = this.fingerprintProperties;
            Intrinsics.checkNotNull(fingerprintSensorPropertiesInternal);
            if (fingerprintSensorPropertiesInternal.isAnyUdfpsType()) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal = this.fingerprintProperties;
        int hashCode = (fingerprintSensorPropertiesInternal == null ? 0 : fingerprintSensorPropertiesInternal.hashCode()) * 31;
        FaceSensorPropertiesInternal faceSensorPropertiesInternal = this.faceProperties;
        return hashCode + (faceSensorPropertiesInternal != null ? faceSensorPropertiesInternal.hashCode() : 0);
    }

    public final String toString() {
        return "BiometricModalities(fingerprintProperties=" + this.fingerprintProperties + ", faceProperties=" + this.faceProperties + ")";
    }

    public BiometricModalities(FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal, FaceSensorPropertiesInternal faceSensorPropertiesInternal) {
        this.fingerprintProperties = fingerprintSensorPropertiesInternal;
        this.faceProperties = faceSensorPropertiesInternal;
    }

    public /* synthetic */ BiometricModalities(FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal, FaceSensorPropertiesInternal faceSensorPropertiesInternal, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : fingerprintSensorPropertiesInternal, (i & 2) != 0 ? null : faceSensorPropertiesInternal);
    }
}
