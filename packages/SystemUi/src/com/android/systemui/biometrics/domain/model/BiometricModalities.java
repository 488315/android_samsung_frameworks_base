package com.android.systemui.biometrics.domain.model;

import android.hardware.face.FaceSensorPropertiesInternal;
import android.hardware.fingerprint.FingerprintSensorPropertiesInternal;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class BiometricModalities {
    public final FaceSensorPropertiesInternal faceProperties;
    public final FingerprintSensorPropertiesInternal fingerprintProperties;

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public BiometricModalities() {
        this(r0, r0, 3, r0);
        FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal = null;
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
        if (this.fingerprintProperties != null) {
            if (this.faceProperties != null) {
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
