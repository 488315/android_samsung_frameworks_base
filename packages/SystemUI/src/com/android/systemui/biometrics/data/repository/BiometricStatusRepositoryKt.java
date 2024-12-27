package com.android.systemui.biometrics.data.repository;

import com.android.systemui.biometrics.shared.model.AuthenticationReason;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
