package com.android.systemui.biometrics.data.repository;

import com.android.systemui.biometrics.shared.model.AuthenticationReason;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class BiometricStatusRepositoryKt {
    public static final AuthenticationReason access$toAuthenticationReason(int i) {
        switch (i) {
            case 1:
                return new AuthenticationReason.SettingsAuthentication(AuthenticationReason.SettingsOperations.ENROLL_FIND_SENSOR);
            case 2:
                return new AuthenticationReason.SettingsAuthentication(AuthenticationReason.SettingsOperations.ENROLL_ENROLLING);
            case 3:
                return AuthenticationReason.BiometricPromptAuthentication.INSTANCE;
            case 4:
                return AuthenticationReason.DeviceEntryAuthentication.INSTANCE;
            case 5:
                return AuthenticationReason.OtherAuthentication.INSTANCE;
            case 6:
                return new AuthenticationReason.SettingsAuthentication(AuthenticationReason.SettingsOperations.OTHER);
            default:
                return AuthenticationReason.Unknown.INSTANCE;
        }
    }
}
