package com.android.keyguard;

import android.hardware.biometrics.BiometricSourceType;
import android.text.TextUtils;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.internal.logging.UiEventLogger;
import com.android.keyguard.KeyguardBiometricLockoutLogger;
import com.android.systemui.CoreStartable;
import com.android.systemui.keyguard.SecurityLog;
import com.android.systemui.log.SessionTracker;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.util.SystemUIAnalytics;
import java.io.PrintWriter;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class KeyguardBiometricLockoutLogger implements CoreStartable {
    public static final Companion Companion = new Companion(null);
    public boolean encryptedOrLockdown;
    public boolean faceLockedOut;
    public boolean fingerprintLockedOut;
    public final KeyguardUpdateMonitor keyguardUpdateMonitor;
    public final KeyguardUpdateMonitorCallback mKeyguardUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.keyguard.KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1
        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onLockedOutStateChanged(BiometricSourceType biometricSourceType) {
            BiometricSourceType biometricSourceType2 = BiometricSourceType.FINGERPRINT;
            KeyguardBiometricLockoutLogger keyguardBiometricLockoutLogger = KeyguardBiometricLockoutLogger.this;
            if (biometricSourceType == biometricSourceType2) {
                boolean isFingerprintLockedOut = keyguardBiometricLockoutLogger.keyguardUpdateMonitor.isFingerprintLockedOut();
                if (isFingerprintLockedOut && !keyguardBiometricLockoutLogger.fingerprintLockedOut) {
                    keyguardBiometricLockoutLogger.uiEventLogger.log(KeyguardBiometricLockoutLogger.PrimaryAuthRequiredEvent.PRIMARY_AUTH_REQUIRED_FINGERPRINT_LOCKED_OUT, keyguardBiometricLockoutLogger.sessionTracker.getSessionId(1));
                } else if (!isFingerprintLockedOut && keyguardBiometricLockoutLogger.fingerprintLockedOut) {
                    keyguardBiometricLockoutLogger.uiEventLogger.log(KeyguardBiometricLockoutLogger.PrimaryAuthRequiredEvent.PRIMARY_AUTH_REQUIRED_FINGERPRINT_LOCKED_OUT_RESET, keyguardBiometricLockoutLogger.sessionTracker.getSessionId(1));
                }
                keyguardBiometricLockoutLogger.fingerprintLockedOut = isFingerprintLockedOut;
                return;
            }
            if (biometricSourceType == BiometricSourceType.FACE) {
                boolean z = keyguardBiometricLockoutLogger.keyguardUpdateMonitor.mFaceLockedOutPermanent;
                if (z && !keyguardBiometricLockoutLogger.faceLockedOut) {
                    keyguardBiometricLockoutLogger.uiEventLogger.log(KeyguardBiometricLockoutLogger.PrimaryAuthRequiredEvent.PRIMARY_AUTH_REQUIRED_FACE_LOCKED_OUT, keyguardBiometricLockoutLogger.sessionTracker.getSessionId(1));
                } else if (!z && keyguardBiometricLockoutLogger.faceLockedOut) {
                    keyguardBiometricLockoutLogger.uiEventLogger.log(KeyguardBiometricLockoutLogger.PrimaryAuthRequiredEvent.PRIMARY_AUTH_REQUIRED_FACE_LOCKED_OUT_RESET, keyguardBiometricLockoutLogger.sessionTracker.getSessionId(1));
                }
                keyguardBiometricLockoutLogger.faceLockedOut = z;
            }
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onStrongAuthStateChanged(int i) {
            String str;
            KeyguardBiometricLockoutLogger keyguardBiometricLockoutLogger = KeyguardBiometricLockoutLogger.this;
            int strongAuthForUser = keyguardBiometricLockoutLogger.keyguardUpdateMonitor.mStrongAuthTracker.getStrongAuthForUser(i);
            int bouncerPromptReason = keyguardBiometricLockoutLogger.viewMediatorCallback.getBouncerPromptReason();
            String hexString = Integer.toHexString(keyguardBiometricLockoutLogger.strongAuthFlags);
            String hexString2 = Integer.toHexString(strongAuthForUser);
            StringBuilder m = KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0.m(bouncerPromptReason, "onStrongAuthStateChanged() PromptReason ", " StrongAuth Flag 0x ", hexString, "  -> 0x");
            m.append(hexString2);
            String sb = m.toString();
            int i2 = strongAuthForUser != 0 ? keyguardBiometricLockoutLogger.strongAuthFlags ^ strongAuthForUser : 0;
            if (i2 == 0 && bouncerPromptReason == 1) {
                i2 = 1;
            }
            keyguardBiometricLockoutLogger.strongAuthFlags = strongAuthForUser;
            KeyguardUpdateMonitor keyguardUpdateMonitor = keyguardBiometricLockoutLogger.keyguardUpdateMonitor;
            if (i2 == 1) {
                str = "1";
            } else if (i2 == 2) {
                str = "2";
            } else if (i2 == 4) {
                str = "3";
            } else if (i2 == 8) {
                str = "4";
            } else if (i2 == 16) {
                sb = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(sb, ", 72hr timeout");
                str = "5";
            } else if (i2 == 32) {
                str = "6";
            } else if (i2 == 64) {
                str = "9";
            } else if (i2 == 128) {
                sb = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(sb, ", 24hr timeout");
                str = "7";
            } else if (keyguardUpdateMonitor.mStrongAuthTracker.isNonStrongBiometricAllowedAfterIdleTimeout(i)) {
                str = "";
            } else {
                sb = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(sb, ", 4hr idle timeout");
                str = "8";
            }
            if (!TextUtils.isEmpty(str)) {
                SystemUIAnalytics.sendEventLog("102", SystemUIAnalytics.EID_STRONG_AUTH, str);
            }
            SecurityLog.d("KeyguardBiometricLockoutLogger", sb);
            if (i != keyguardBiometricLockoutLogger.selectedUserInteractor.getSelectedUserId(false)) {
                return;
            }
            int strongAuthForUser2 = keyguardUpdateMonitor.mStrongAuthTracker.getStrongAuthForUser(i);
            boolean isEncryptedOrLockdown = keyguardUpdateMonitor.isEncryptedOrLockdown(i);
            if (isEncryptedOrLockdown && !keyguardBiometricLockoutLogger.encryptedOrLockdown) {
                keyguardBiometricLockoutLogger.uiEventLogger.log(KeyguardBiometricLockoutLogger.PrimaryAuthRequiredEvent.PRIMARY_AUTH_REQUIRED_ENCRYPTED_OR_LOCKDOWN, keyguardBiometricLockoutLogger.sessionTracker.getSessionId(1));
            }
            keyguardBiometricLockoutLogger.encryptedOrLockdown = isEncryptedOrLockdown;
            KeyguardBiometricLockoutLogger.Companion.getClass();
            boolean z = (strongAuthForUser2 & 64) != 0;
            if (z && !keyguardBiometricLockoutLogger.unattendedUpdate) {
                keyguardBiometricLockoutLogger.uiEventLogger.log(KeyguardBiometricLockoutLogger.PrimaryAuthRequiredEvent.PRIMARY_AUTH_REQUIRED_UNATTENDED_UPDATE, keyguardBiometricLockoutLogger.sessionTracker.getSessionId(1));
            }
            keyguardBiometricLockoutLogger.unattendedUpdate = z;
            boolean z2 = ((strongAuthForUser2 & 16) == 0 && (strongAuthForUser2 & 128) == 0) ? false : true;
            if (z2 && !keyguardBiometricLockoutLogger.timeout) {
                keyguardBiometricLockoutLogger.uiEventLogger.log(KeyguardBiometricLockoutLogger.PrimaryAuthRequiredEvent.PRIMARY_AUTH_REQUIRED_TIMEOUT, keyguardBiometricLockoutLogger.sessionTracker.getSessionId(1));
            }
            keyguardBiometricLockoutLogger.timeout = z2;
        }
    };
    public final SelectedUserInteractor selectedUserInteractor;
    public final SessionTracker sessionTracker;
    public int strongAuthFlags;
    public boolean timeout;
    public final UiEventLogger uiEventLogger;
    public boolean unattendedUpdate;
    public final ViewMediatorCallback viewMediatorCallback;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class PrimaryAuthRequiredEvent implements UiEventLogger.UiEventEnum {
        public static final /* synthetic */ PrimaryAuthRequiredEvent[] $VALUES;
        public static final PrimaryAuthRequiredEvent PRIMARY_AUTH_REQUIRED_ENCRYPTED_OR_LOCKDOWN;
        public static final PrimaryAuthRequiredEvent PRIMARY_AUTH_REQUIRED_FACE_LOCKED_OUT;
        public static final PrimaryAuthRequiredEvent PRIMARY_AUTH_REQUIRED_FACE_LOCKED_OUT_RESET;
        public static final PrimaryAuthRequiredEvent PRIMARY_AUTH_REQUIRED_FINGERPRINT_LOCKED_OUT;
        public static final PrimaryAuthRequiredEvent PRIMARY_AUTH_REQUIRED_FINGERPRINT_LOCKED_OUT_RESET;
        public static final PrimaryAuthRequiredEvent PRIMARY_AUTH_REQUIRED_TIMEOUT;
        public static final PrimaryAuthRequiredEvent PRIMARY_AUTH_REQUIRED_UNATTENDED_UPDATE;
        private final int mId;

        static {
            PrimaryAuthRequiredEvent primaryAuthRequiredEvent = new PrimaryAuthRequiredEvent("PRIMARY_AUTH_REQUIRED_FINGERPRINT_LOCKED_OUT", 0, 924);
            PRIMARY_AUTH_REQUIRED_FINGERPRINT_LOCKED_OUT = primaryAuthRequiredEvent;
            PrimaryAuthRequiredEvent primaryAuthRequiredEvent2 = new PrimaryAuthRequiredEvent("PRIMARY_AUTH_REQUIRED_FINGERPRINT_LOCKED_OUT_RESET", 1, 925);
            PRIMARY_AUTH_REQUIRED_FINGERPRINT_LOCKED_OUT_RESET = primaryAuthRequiredEvent2;
            PrimaryAuthRequiredEvent primaryAuthRequiredEvent3 = new PrimaryAuthRequiredEvent("PRIMARY_AUTH_REQUIRED_FACE_LOCKED_OUT", 2, 926);
            PRIMARY_AUTH_REQUIRED_FACE_LOCKED_OUT = primaryAuthRequiredEvent3;
            PrimaryAuthRequiredEvent primaryAuthRequiredEvent4 = new PrimaryAuthRequiredEvent("PRIMARY_AUTH_REQUIRED_FACE_LOCKED_OUT_RESET", 3, 927);
            PRIMARY_AUTH_REQUIRED_FACE_LOCKED_OUT_RESET = primaryAuthRequiredEvent4;
            PrimaryAuthRequiredEvent primaryAuthRequiredEvent5 = new PrimaryAuthRequiredEvent("PRIMARY_AUTH_REQUIRED_ENCRYPTED_OR_LOCKDOWN", 4, 928);
            PRIMARY_AUTH_REQUIRED_ENCRYPTED_OR_LOCKDOWN = primaryAuthRequiredEvent5;
            PrimaryAuthRequiredEvent primaryAuthRequiredEvent6 = new PrimaryAuthRequiredEvent("PRIMARY_AUTH_REQUIRED_TIMEOUT", 5, 929);
            PRIMARY_AUTH_REQUIRED_TIMEOUT = primaryAuthRequiredEvent6;
            PrimaryAuthRequiredEvent primaryAuthRequiredEvent7 = new PrimaryAuthRequiredEvent("PRIMARY_AUTH_REQUIRED_UNATTENDED_UPDATE", 6, 931);
            PRIMARY_AUTH_REQUIRED_UNATTENDED_UPDATE = primaryAuthRequiredEvent7;
            PrimaryAuthRequiredEvent[] primaryAuthRequiredEventArr = {primaryAuthRequiredEvent, primaryAuthRequiredEvent2, primaryAuthRequiredEvent3, primaryAuthRequiredEvent4, primaryAuthRequiredEvent5, primaryAuthRequiredEvent6, primaryAuthRequiredEvent7};
            $VALUES = primaryAuthRequiredEventArr;
            EnumEntriesKt.enumEntries(primaryAuthRequiredEventArr);
        }

        private PrimaryAuthRequiredEvent(String str, int i, int i2) {
            this.mId = i2;
        }

        public static PrimaryAuthRequiredEvent valueOf(String str) {
            return (PrimaryAuthRequiredEvent) Enum.valueOf(PrimaryAuthRequiredEvent.class, str);
        }

        public static PrimaryAuthRequiredEvent[] values() {
            return (PrimaryAuthRequiredEvent[]) $VALUES.clone();
        }

        public final int getId() {
            return this.mId;
        }
    }

    public KeyguardBiometricLockoutLogger(ViewMediatorCallback viewMediatorCallback, UiEventLogger uiEventLogger, KeyguardUpdateMonitor keyguardUpdateMonitor, SessionTracker sessionTracker, SelectedUserInteractor selectedUserInteractor) {
        this.viewMediatorCallback = viewMediatorCallback;
        this.uiEventLogger = uiEventLogger;
        this.keyguardUpdateMonitor = keyguardUpdateMonitor;
        this.sessionTracker = sessionTracker;
        this.selectedUserInteractor = selectedUserInteractor;
    }

    @Override // com.android.systemui.CoreStartable, com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m("  mFingerprintLockedOut=", this.fingerprintLockedOut, printWriter);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m("  mFaceLockedOut=", this.faceLockedOut, printWriter);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m("  mIsEncryptedOrLockdown=", this.encryptedOrLockdown, printWriter);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m("  mIsUnattendedUpdate=", this.unattendedUpdate, printWriter);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m("  mIsTimeout=", this.timeout, printWriter);
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        this.mKeyguardUpdateMonitorCallback.onStrongAuthStateChanged(this.selectedUserInteractor.getSelectedUserId(false));
        this.keyguardUpdateMonitor.registerCallback(this.mKeyguardUpdateMonitorCallback);
    }
}
