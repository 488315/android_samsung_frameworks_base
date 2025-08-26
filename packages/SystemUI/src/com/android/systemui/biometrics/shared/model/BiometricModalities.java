package com.android.systemui.biometrics.shared.model;

import android.hardware.face.FaceSensorPropertiesInternal;
import android.hardware.fingerprint.FingerprintSensorPropertiesInternal;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class BiometricModalities {
    public final FaceSensorPropertiesInternal faceProperties;
    public final FingerprintSensorPropertiesInternal fingerprintProperties;

    /* JADX WARN: Illegal instructions before constructor call */
    public BiometricModalities() {
        FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal = null;
        this(fingerprintSensorPropertiesInternal, fingerprintSensorPropertiesInternal, 3, fingerprintSensorPropertiesInternal);
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
        if (!getHasFingerprint()) {
            return false;
        }
        FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal = this.fingerprintProperties;
        fingerprintSensorPropertiesInternal.getClass();
        return fingerprintSensorPropertiesInternal.isAnySidefpsType();
    }

    public final boolean getHasUdfps() {
        if (!getHasFingerprint()) {
            return false;
        }
        FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal = this.fingerprintProperties;
        fingerprintSensorPropertiesInternal.getClass();
        return fingerprintSensorPropertiesInternal.isAnyUdfpsType();
    }

    public final int hashCode() {
        FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal = this.fingerprintProperties;
        int iHashCode = (fingerprintSensorPropertiesInternal == null ? 0 : fingerprintSensorPropertiesInternal.hashCode()) * 31;
        FaceSensorPropertiesInternal faceSensorPropertiesInternal = this.faceProperties;
        return iHashCode + (faceSensorPropertiesInternal != null ? faceSensorPropertiesInternal.hashCode() : 0);
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
