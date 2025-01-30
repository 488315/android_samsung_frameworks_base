package com.android.keyguard;

import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import com.android.keyguard.ActiveUnlockConfig;
import com.android.systemui.util.settings.SecureSettings;
import com.android.systemui.util.settings.SecureSettingsImpl;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import kotlin.collections.EmptySet;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ActiveUnlockConfig$settingsObserver$1 extends ContentObserver {
    public final Uri bioFailUri;
    public final Uri faceAcquireInfoUri;
    public final Uri faceErrorsUri;
    public final /* synthetic */ ActiveUnlockConfig this$0;
    public final Uri unlockIntentUri;
    public final Uri unlockIntentWhenBiometricEnrolledUri;
    public final Uri wakeUri;
    public final Uri wakeupsConsideredUnlockIntentsUri;
    public final Uri wakeupsToForceDismissKeyguardUri;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ActiveUnlockConfig$settingsObserver$1(ActiveUnlockConfig activeUnlockConfig, Handler handler) {
        super(handler);
        this.this$0 = activeUnlockConfig;
        ((SecureSettingsImpl) activeUnlockConfig.secureSettings).getClass();
        this.wakeUri = Settings.Secure.getUriFor("active_unlock_on_wake");
        SecureSettings secureSettings = activeUnlockConfig.secureSettings;
        ((SecureSettingsImpl) secureSettings).getClass();
        this.unlockIntentUri = Settings.Secure.getUriFor("active_unlock_on_unlock_intent");
        ((SecureSettingsImpl) secureSettings).getClass();
        this.bioFailUri = Settings.Secure.getUriFor("active_unlock_on_biometric_fail");
        ((SecureSettingsImpl) secureSettings).getClass();
        this.faceErrorsUri = Settings.Secure.getUriFor("active_unlock_on_face_errors");
        ((SecureSettingsImpl) secureSettings).getClass();
        this.faceAcquireInfoUri = Settings.Secure.getUriFor("active_unlock_on_face_acquire_info");
        ((SecureSettingsImpl) secureSettings).getClass();
        this.unlockIntentWhenBiometricEnrolledUri = Settings.Secure.getUriFor("active_unlock_on_unlock_intent_when_biometric_enrolled");
        ((SecureSettingsImpl) secureSettings).getClass();
        this.wakeupsConsideredUnlockIntentsUri = Settings.Secure.getUriFor("active_unlock_wakeups_considered_unlock_intents");
        ((SecureSettingsImpl) secureSettings).getClass();
        this.wakeupsToForceDismissKeyguardUri = Settings.Secure.getUriFor("active_unlock_wakeups_to_force_dismiss_keyguard");
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
        if (KeyguardUpdateMonitor.getCurrentUser() != i2) {
            return;
        }
        if (z || collection.contains(this.wakeUri)) {
            ActiveUnlockConfig activeUnlockConfig = this.this$0;
            activeUnlockConfig.requestActiveUnlockOnWakeup = activeUnlockConfig.secureSettings.getIntForUser(0, KeyguardUpdateMonitor.getCurrentUser(), "active_unlock_on_wake") == 1;
        }
        if (z || collection.contains(this.unlockIntentUri)) {
            ActiveUnlockConfig activeUnlockConfig2 = this.this$0;
            activeUnlockConfig2.requestActiveUnlockOnUnlockIntent = activeUnlockConfig2.secureSettings.getIntForUser(0, KeyguardUpdateMonitor.getCurrentUser(), "active_unlock_on_unlock_intent") == 1;
        }
        if (z || collection.contains(this.bioFailUri)) {
            ActiveUnlockConfig activeUnlockConfig3 = this.this$0;
            activeUnlockConfig3.requestActiveUnlockOnBioFail = activeUnlockConfig3.secureSettings.getIntForUser(0, KeyguardUpdateMonitor.getCurrentUser(), "active_unlock_on_biometric_fail") == 1;
        }
        if (z || collection.contains(this.faceErrorsUri)) {
            processStringArray(((SecureSettingsImpl) this.this$0.secureSettings).getStringForUser(KeyguardUpdateMonitor.getCurrentUser(), "active_unlock_on_face_errors"), this.this$0.faceErrorsToTriggerBiometricFailOn, Collections.singleton(3));
        }
        if (z || collection.contains(this.faceAcquireInfoUri)) {
            processStringArray(((SecureSettingsImpl) this.this$0.secureSettings).getStringForUser(KeyguardUpdateMonitor.getCurrentUser(), "active_unlock_on_face_acquire_info"), this.this$0.faceAcquireInfoToTriggerBiometricFailOn, EmptySet.INSTANCE);
        }
        if (z || collection.contains(this.unlockIntentWhenBiometricEnrolledUri)) {
            processStringArray(((SecureSettingsImpl) this.this$0.secureSettings).getStringForUser(KeyguardUpdateMonitor.getCurrentUser(), "active_unlock_on_unlock_intent_when_biometric_enrolled"), this.this$0.onUnlockIntentWhenBiometricEnrolled, Collections.singleton(Integer.valueOf(ActiveUnlockConfig.BiometricType.NONE.getIntValue())));
        }
        if (z || collection.contains(this.wakeupsConsideredUnlockIntentsUri)) {
            processStringArray(((SecureSettingsImpl) this.this$0.secureSettings).getStringForUser(KeyguardUpdateMonitor.getCurrentUser(), "active_unlock_wakeups_considered_unlock_intents"), this.this$0.wakeupsConsideredUnlockIntents, Collections.singleton(12));
        }
        if (z || collection.contains(this.wakeupsToForceDismissKeyguardUri)) {
            processStringArray(((SecureSettingsImpl) this.this$0.secureSettings).getStringForUser(KeyguardUpdateMonitor.getCurrentUser(), "active_unlock_wakeups_to_force_dismiss_keyguard"), this.this$0.wakeupsToForceDismissKeyguard, Collections.singleton(12));
        }
    }
}
