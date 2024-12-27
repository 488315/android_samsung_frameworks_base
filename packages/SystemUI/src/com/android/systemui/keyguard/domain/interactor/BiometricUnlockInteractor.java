package com.android.systemui.keyguard.domain.interactor;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import com.android.systemui.keyguard.data.repository.KeyguardRepository;
import com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl;
import com.android.systemui.keyguard.shared.model.BiometricUnlockMode;
import com.android.systemui.keyguard.shared.model.BiometricUnlockModel;
import com.android.systemui.keyguard.shared.model.BiometricUnlockSource;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class BiometricUnlockInteractor {
    public final KeyguardRepository keyguardRepository;
    public final StateFlow unlockState;

    public BiometricUnlockInteractor(KeyguardRepository keyguardRepository) {
        this.keyguardRepository = keyguardRepository;
        this.unlockState = ((KeyguardRepositoryImpl) keyguardRepository).biometricUnlockState;
    }

    public final void setBiometricUnlockState(int i, BiometricUnlockSource biometricUnlockSource) {
        BiometricUnlockMode biometricUnlockMode;
        switch (i) {
            case 0:
                biometricUnlockMode = BiometricUnlockMode.NONE;
                break;
            case 1:
                biometricUnlockMode = BiometricUnlockMode.WAKE_AND_UNLOCK;
                break;
            case 2:
                biometricUnlockMode = BiometricUnlockMode.WAKE_AND_UNLOCK_PULSING;
                break;
            case 3:
                biometricUnlockMode = BiometricUnlockMode.SHOW_BOUNCER;
                break;
            case 4:
                biometricUnlockMode = BiometricUnlockMode.ONLY_WAKE;
                break;
            case 5:
                biometricUnlockMode = BiometricUnlockMode.UNLOCK_COLLAPSING;
                break;
            case 6:
                biometricUnlockMode = BiometricUnlockMode.WAKE_AND_UNLOCK_FROM_DREAM;
                break;
            case 7:
                biometricUnlockMode = BiometricUnlockMode.DISMISS_BOUNCER;
                break;
            default:
                throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Invalid BiometricUnlockModel value: "));
        }
        KeyguardRepositoryImpl keyguardRepositoryImpl = (KeyguardRepositoryImpl) this.keyguardRepository;
        keyguardRepositoryImpl.getClass();
        keyguardRepositoryImpl._biometricUnlockState.updateState(null, new BiometricUnlockModel(biometricUnlockMode, biometricUnlockSource));
    }
}
