package com.android.systemui.keyguard.data.repository;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public enum BiometricType {
    UNKNOWN(false),
    REAR_FINGERPRINT(true),
    UNDER_DISPLAY_FINGERPRINT(true),
    SIDE_FINGERPRINT(true),
    FACE(false);

    private final boolean isFingerprint;

    BiometricType(boolean z) {
        this.isFingerprint = z;
    }

    public final boolean isFingerprint() {
        return this.isFingerprint;
    }
}
