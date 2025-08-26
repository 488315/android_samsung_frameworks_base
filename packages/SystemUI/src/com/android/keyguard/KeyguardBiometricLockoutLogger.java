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

/* loaded from: classes.dex */
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
            KeyguardBiometricLockoutLogger keyguardBiometricLockoutLogger = this.this$0;
            if (biometricSourceType == biometricSourceType2) {
                boolean zIsFingerprintLockedOut = keyguardBiometricLockoutLogger.keyguardUpdateMonitor.isFingerprintLockedOut();
                if (zIsFingerprintLockedOut && !keyguardBiometricLockoutLogger.fingerprintLockedOut) {
                    keyguardBiometricLockoutLogger.uiEventLogger.log(KeyguardBiometricLockoutLogger.PrimaryAuthRequiredEvent.PRIMARY_AUTH_REQUIRED_FINGERPRINT_LOCKED_OUT, keyguardBiometricLockoutLogger.sessionTracker.getSessionId(1));
                } else if (!zIsFingerprintLockedOut && keyguardBiometricLockoutLogger.fingerprintLockedOut) {
                    keyguardBiometricLockoutLogger.uiEventLogger.log(KeyguardBiometricLockoutLogger.PrimaryAuthRequiredEvent.PRIMARY_AUTH_REQUIRED_FINGERPRINT_LOCKED_OUT_RESET, keyguardBiometricLockoutLogger.sessionTracker.getSessionId(1));
                }
                keyguardBiometricLockoutLogger.fingerprintLockedOut = zIsFingerprintLockedOut;
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
            KeyguardBiometricLockoutLogger keyguardBiometricLockoutLogger = this.this$0;
            int strongAuthForUser = keyguardBiometricLockoutLogger.keyguardUpdateMonitor.mStrongAuthTracker.getStrongAuthForUser(i);
            int bouncerPromptReason = keyguardBiometricLockoutLogger.viewMediatorCallback.getBouncerPromptReason();
            String hexString = Integer.toHexString(keyguardBiometricLockoutLogger.strongAuthFlags);
            String hexString2 = Integer.toHexString(strongAuthForUser);
            StringBuilder sbM = KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0.m(bouncerPromptReason, "onStrongAuthStateChanged() PromptReason ", " StrongAuth Flag 0x ", hexString, "  -> 0x");
            sbM.append(hexString2);
            String string = sbM.toString();
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
                string = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(string, ", 72hr timeout");
                str = "5";
            } else if (i2 == 32) {
                str = "6";
            } else if (i2 == 64) {
                str = "9";
            } else if (i2 == 128) {
                string = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(string, ", 24hr timeout");
                str = "7";
            } else if (keyguardUpdateMonitor.mStrongAuthTracker.isNonStrongBiometricAllowedAfterIdleTimeout(i)) {
                str = "";
            } else {
                string = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(string, ", 4hr idle timeout");
                str = "8";
            }
            if (!TextUtils.isEmpty(str)) {
                SystemUIAnalytics.sendEventLog("102", SystemUIAnalytics.EID_STRONG_AUTH, str);
            }
            SecurityLog.d("KeyguardBiometricLockoutLogger", string);
            if (i != keyguardBiometricLockoutLogger.selectedUserInteractor.getSelectedUserId()) {
                return;
            }
            int strongAuthForUser2 = keyguardUpdateMonitor.mStrongAuthTracker.getStrongAuthForUser(i);
            boolean zIsEncryptedOrLockdown = keyguardUpdateMonitor.isEncryptedOrLockdown(i);
            if (zIsEncryptedOrLockdown && !keyguardBiometricLockoutLogger.encryptedOrLockdown) {
                keyguardBiometricLockoutLogger.uiEventLogger.log(KeyguardBiometricLockoutLogger.PrimaryAuthRequiredEvent.PRIMARY_AUTH_REQUIRED_ENCRYPTED_OR_LOCKDOWN, keyguardBiometricLockoutLogger.sessionTracker.getSessionId(1));
            }
            keyguardBiometricLockoutLogger.encryptedOrLockdown = zIsEncryptedOrLockdown;
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

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
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
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m(printWriter, "  mFingerprintLockedOut=", this.fingerprintLockedOut);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m(printWriter, "  mFaceLockedOut=", this.faceLockedOut);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m(printWriter, "  mIsEncryptedOrLockdown=", this.encryptedOrLockdown);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m(printWriter, "  mIsUnattendedUpdate=", this.unattendedUpdate);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m(printWriter, "  mIsTimeout=", this.timeout);
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        this.mKeyguardUpdateMonitorCallback.onStrongAuthStateChanged(this.selectedUserInteractor.getSelectedUserId());
        this.keyguardUpdateMonitor.registerCallback(this.mKeyguardUpdateMonitorCallback);
    }
}
