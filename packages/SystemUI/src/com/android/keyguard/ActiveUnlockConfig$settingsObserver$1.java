package com.android.keyguard;

import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import com.android.keyguard.ActiveUnlockConfig;
import com.android.systemui.util.settings.SecureSettings;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import kotlin.collections.EmptySet;
import kotlin.text.StringsKt__StringsKt;

public final class ActiveUnlockConfig$settingsObserver$1 extends ContentObserver {
    public final Uri bioFailUri;
    public final Uri faceAcquireInfoUri;
    public final Uri faceErrorsUri;
    public final /* synthetic */ ActiveUnlockConfig this$0;
    public final Uri unlockIntentLegacyUri;
    public final Uri unlockIntentUri;
    public final Uri unlockIntentWhenBiometricEnrolledUri;
    public final Uri wakeUri;
    public final Uri wakeupsConsideredUnlockIntentsUri;
    public final Uri wakeupsToForceDismissKeyguardUri;

    public ActiveUnlockConfig$settingsObserver$1(ActiveUnlockConfig activeUnlockConfig, Handler handler) {
        super(handler);
        this.this$0 = activeUnlockConfig;
        this.wakeUri = activeUnlockConfig.secureSettings.getUriFor("active_unlock_on_wake");
        SecureSettings secureSettings = activeUnlockConfig.secureSettings;
        this.unlockIntentLegacyUri = secureSettings.getUriFor("active_unlock_on_unlock_intent_legacy");
        this.unlockIntentUri = secureSettings.getUriFor("active_unlock_on_unlock_intent");
        this.bioFailUri = secureSettings.getUriFor("active_unlock_on_biometric_fail");
        this.faceErrorsUri = secureSettings.getUriFor("active_unlock_on_face_errors");
        this.faceAcquireInfoUri = secureSettings.getUriFor("active_unlock_on_face_acquire_info");
        this.unlockIntentWhenBiometricEnrolledUri = secureSettings.getUriFor("active_unlock_on_unlock_intent_when_biometric_enrolled");
        this.wakeupsConsideredUnlockIntentsUri = secureSettings.getUriFor("active_unlock_wakeups_considered_unlock_intents");
        this.wakeupsToForceDismissKeyguardUri = secureSettings.getUriFor("active_unlock_wakeups_to_force_dismiss_keyguard");
    }

    public static void processStringArray(String str, Set set, Set set2) {
        set.clear();
        if (str == null) {
            set.addAll(set2);
            return;
        }
        for (String str2 : StringsKt__StringsKt.split$default(str, new String[]{"|"}, 0, 6)) {
            if (str2.length() > 0) {
                try {
                    set.add(Integer.valueOf(Integer.parseInt(str2)));
                } catch (NumberFormatException unused) {
                    Log.e("ActiveUnlockConfig", "Passed an invalid setting=".concat(str2));
                }
            }
        }
    }

    public final void onChange(boolean z, Collection collection, int i, int i2) {
        if (this.this$0.selectedUserInteractor.getSelectedUserId(false) != i2) {
            return;
        }
        if (z || collection.contains(this.wakeUri)) {
            ActiveUnlockConfig activeUnlockConfig = this.this$0;
            activeUnlockConfig.requestActiveUnlockOnWakeup = activeUnlockConfig.secureSettings.getIntForUser("active_unlock_on_wake", 0, activeUnlockConfig.selectedUserInteractor.getSelectedUserId(false)) == 1;
        }
        if (z || collection.contains(this.unlockIntentLegacyUri)) {
            ActiveUnlockConfig activeUnlockConfig2 = this.this$0;
            activeUnlockConfig2.requestActiveUnlockOnUnlockIntentLegacy = activeUnlockConfig2.secureSettings.getIntForUser("active_unlock_on_unlock_intent_legacy", 0, activeUnlockConfig2.selectedUserInteractor.getSelectedUserId(false)) == 1;
        }
        if (z || collection.contains(this.unlockIntentUri)) {
            ActiveUnlockConfig activeUnlockConfig3 = this.this$0;
            activeUnlockConfig3.requestActiveUnlockOnUnlockIntent = activeUnlockConfig3.secureSettings.getIntForUser("active_unlock_on_unlock_intent", 0, activeUnlockConfig3.selectedUserInteractor.getSelectedUserId(false)) == 1;
        }
        if (z || collection.contains(this.bioFailUri)) {
            ActiveUnlockConfig activeUnlockConfig4 = this.this$0;
            activeUnlockConfig4.requestActiveUnlockOnBioFail = activeUnlockConfig4.secureSettings.getIntForUser("active_unlock_on_biometric_fail", 0, activeUnlockConfig4.selectedUserInteractor.getSelectedUserId(false)) == 1;
        }
        if (z || collection.contains(this.faceErrorsUri)) {
            ActiveUnlockConfig activeUnlockConfig5 = this.this$0;
            processStringArray(activeUnlockConfig5.secureSettings.getStringForUser("active_unlock_on_face_errors", activeUnlockConfig5.selectedUserInteractor.getSelectedUserId(false)), this.this$0.faceErrorsToTriggerBiometricFailOn, Collections.singleton(3));
        }
        if (z || collection.contains(this.faceAcquireInfoUri)) {
            ActiveUnlockConfig activeUnlockConfig6 = this.this$0;
            processStringArray(activeUnlockConfig6.secureSettings.getStringForUser("active_unlock_on_face_acquire_info", activeUnlockConfig6.selectedUserInteractor.getSelectedUserId(false)), this.this$0.faceAcquireInfoToTriggerBiometricFailOn, EmptySet.INSTANCE);
        }
        if (z || collection.contains(this.unlockIntentWhenBiometricEnrolledUri)) {
            ActiveUnlockConfig activeUnlockConfig7 = this.this$0;
            processStringArray(activeUnlockConfig7.secureSettings.getStringForUser("active_unlock_on_unlock_intent_when_biometric_enrolled", activeUnlockConfig7.selectedUserInteractor.getSelectedUserId(false)), this.this$0.onUnlockIntentWhenBiometricEnrolled, Collections.singleton(Integer.valueOf(ActiveUnlockConfig.BiometricType.NONE.getIntValue())));
        }
        if (z || collection.contains(this.wakeupsConsideredUnlockIntentsUri)) {
            ActiveUnlockConfig activeUnlockConfig8 = this.this$0;
            processStringArray(activeUnlockConfig8.secureSettings.getStringForUser("active_unlock_wakeups_considered_unlock_intents", activeUnlockConfig8.selectedUserInteractor.getSelectedUserId(false)), this.this$0.wakeupsConsideredUnlockIntents, Collections.singleton(12));
        }
        if (z || collection.contains(this.wakeupsToForceDismissKeyguardUri)) {
            ActiveUnlockConfig activeUnlockConfig9 = this.this$0;
            processStringArray(activeUnlockConfig9.secureSettings.getStringForUser("active_unlock_wakeups_to_force_dismiss_keyguard", activeUnlockConfig9.selectedUserInteractor.getSelectedUserId(false)), this.this$0.wakeupsToForceDismissKeyguard, Collections.singleton(12));
        }
    }
}
