package com.android.keyguard;

import android.hardware.biometrics.BiometricSourceType;
import android.text.TextUtils;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.internal.logging.UiEventLogger;
import com.android.keyguard.KeyguardBiometricLockoutLogger;
import com.android.systemui.CoreStartable;
import com.android.systemui.keyguard.SecurityLog;
import com.android.systemui.log.SessionTracker;
import com.android.systemui.util.SystemUIAnalytics;
import com.sec.ims.configuration.DATA;
import java.io.PrintWriter;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
                KeyguardUpdateMonitor keyguardUpdateMonitor = keyguardBiometricLockoutLogger.keyguardUpdateMonitor;
                keyguardUpdateMonitor.getClass();
                boolean z = keyguardUpdateMonitor.mFaceLockedOutPermanent;
                if (z && !keyguardBiometricLockoutLogger.faceLockedOut) {
                    keyguardBiometricLockoutLogger.uiEventLogger.log(KeyguardBiometricLockoutLogger.PrimaryAuthRequiredEvent.PRIMARY_AUTH_REQUIRED_FACE_LOCKED_OUT, keyguardBiometricLockoutLogger.sessionTracker.getSessionId(1));
                } else if (!z && keyguardBiometricLockoutLogger.faceLockedOut) {
                    keyguardBiometricLockoutLogger.uiEventLogger.log(KeyguardBiometricLockoutLogger.PrimaryAuthRequiredEvent.PRIMARY_AUTH_REQUIRED_FACE_LOCKED_OUT_RESET, keyguardBiometricLockoutLogger.sessionTracker.getSessionId(1));
                }
                keyguardBiometricLockoutLogger.faceLockedOut = z;
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:52:0x00ea, code lost:
        
            if (((r1 & 128) != 0) != false) goto L66;
         */
        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void onStrongAuthStateChanged(int i) {
            String str;
            KeyguardBiometricLockoutLogger keyguardBiometricLockoutLogger = KeyguardBiometricLockoutLogger.this;
            int strongAuthForUser = keyguardBiometricLockoutLogger.keyguardUpdateMonitor.mStrongAuthTracker.getStrongAuthForUser(i);
            int bouncerPromptReason = keyguardBiometricLockoutLogger.viewMediatorCallback.getBouncerPromptReason();
            String hexString = Integer.toHexString(keyguardBiometricLockoutLogger.strongAuthFlags);
            String hexString2 = Integer.toHexString(strongAuthForUser);
            StringBuilder m61m = AbstractC0662xaf167275.m61m("onStrongAuthStateChanged() PromptReason ", bouncerPromptReason, " StrongAuth Flag 0x ", hexString, "  -> 0x");
            m61m.append(hexString2);
            String sb = m61m.toString();
            boolean z = false;
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
                str = DATA.DM_FIELD_INDEX.PUBLIC_USER_ID;
            } else if (i2 == 8) {
                str = "4";
            } else if (i2 == 16) {
                sb = AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(sb, ", 72hr timeout");
                str = DATA.DM_FIELD_INDEX.LBO_PCSCF_ADDRESS_TYPE;
            } else if (i2 == 32) {
                str = DATA.DM_FIELD_INDEX.AMR_AUDIO_BITRATE;
            } else if (i2 == 64) {
                str = DATA.DM_FIELD_INDEX.SMS_FORMAT;
            } else if (i2 == 128) {
                sb = AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(sb, ", 24hr timeout");
                str = DATA.DM_FIELD_INDEX.AMR_AUDIO_BITRATE_WB;
            } else if (keyguardUpdateMonitor.mStrongAuthTracker.isNonStrongBiometricAllowedAfterIdleTimeout(i)) {
                str = "";
            } else {
                sb = AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(sb, ", 4hr idle timeout");
                str = DATA.DM_FIELD_INDEX.SIP_SESSION_TIMER;
            }
            if (!TextUtils.isEmpty(str)) {
                SystemUIAnalytics.sendEventLog(DATA.DM_FIELD_INDEX.VOLTE_DOMAIN_UI_SHOW, "1068", str);
            }
            SecurityLog.m143d("KeyguardBiometricLockoutLogger", sb);
            if (i != KeyguardUpdateMonitor.getCurrentUser()) {
                return;
            }
            int strongAuthForUser2 = keyguardUpdateMonitor.mStrongAuthTracker.getStrongAuthForUser(i);
            boolean isEncryptedOrLockdown = keyguardUpdateMonitor.isEncryptedOrLockdown(i);
            if (isEncryptedOrLockdown && !keyguardBiometricLockoutLogger.encryptedOrLockdown) {
                keyguardBiometricLockoutLogger.uiEventLogger.log(KeyguardBiometricLockoutLogger.PrimaryAuthRequiredEvent.PRIMARY_AUTH_REQUIRED_ENCRYPTED_OR_LOCKDOWN, keyguardBiometricLockoutLogger.sessionTracker.getSessionId(1));
            }
            keyguardBiometricLockoutLogger.encryptedOrLockdown = isEncryptedOrLockdown;
            KeyguardBiometricLockoutLogger.Companion.getClass();
            boolean z2 = (strongAuthForUser2 & 64) != 0;
            if (z2 && !keyguardBiometricLockoutLogger.unattendedUpdate) {
                keyguardBiometricLockoutLogger.uiEventLogger.log(KeyguardBiometricLockoutLogger.PrimaryAuthRequiredEvent.PRIMARY_AUTH_REQUIRED_UNATTENDED_UPDATE, keyguardBiometricLockoutLogger.sessionTracker.getSessionId(1));
            }
            keyguardBiometricLockoutLogger.unattendedUpdate = z2;
            if (!((strongAuthForUser2 & 16) != 0)) {
            }
            z = true;
            if (z && !keyguardBiometricLockoutLogger.timeout) {
                keyguardBiometricLockoutLogger.uiEventLogger.log(KeyguardBiometricLockoutLogger.PrimaryAuthRequiredEvent.PRIMARY_AUTH_REQUIRED_TIMEOUT, keyguardBiometricLockoutLogger.sessionTracker.getSessionId(1));
            }
            keyguardBiometricLockoutLogger.timeout = z;
        }
    };
    public final SessionTracker sessionTracker;
    public int strongAuthFlags;
    public boolean timeout;
    public final UiEventLogger uiEventLogger;
    public boolean unattendedUpdate;
    public final ViewMediatorCallback viewMediatorCallback;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum PrimaryAuthRequiredEvent implements UiEventLogger.UiEventEnum {
        PRIMARY_AUTH_REQUIRED_FINGERPRINT_LOCKED_OUT(924),
        PRIMARY_AUTH_REQUIRED_FINGERPRINT_LOCKED_OUT_RESET(925),
        PRIMARY_AUTH_REQUIRED_FACE_LOCKED_OUT(926),
        PRIMARY_AUTH_REQUIRED_FACE_LOCKED_OUT_RESET(927),
        PRIMARY_AUTH_REQUIRED_ENCRYPTED_OR_LOCKDOWN(928),
        PRIMARY_AUTH_REQUIRED_TIMEOUT(929),
        PRIMARY_AUTH_REQUIRED_UNATTENDED_UPDATE(931);

        private final int mId;

        PrimaryAuthRequiredEvent(int i) {
            this.mId = i;
        }

        public final int getId() {
            return this.mId;
        }
    }

    public KeyguardBiometricLockoutLogger(ViewMediatorCallback viewMediatorCallback, UiEventLogger uiEventLogger, KeyguardUpdateMonitor keyguardUpdateMonitor, SessionTracker sessionTracker) {
        this.viewMediatorCallback = viewMediatorCallback;
        this.uiEventLogger = uiEventLogger;
        this.keyguardUpdateMonitor = keyguardUpdateMonitor;
        this.sessionTracker = sessionTracker;
    }

    @Override // com.android.systemui.CoreStartable, com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m53m("  mFingerprintLockedOut=", this.fingerprintLockedOut, printWriter);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m53m("  mFaceLockedOut=", this.faceLockedOut, printWriter);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m53m("  mIsEncryptedOrLockdown=", this.encryptedOrLockdown, printWriter);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m53m("  mIsUnattendedUpdate=", this.unattendedUpdate, printWriter);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m53m("  mIsTimeout=", this.timeout, printWriter);
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        this.mKeyguardUpdateMonitorCallback.onStrongAuthStateChanged(KeyguardUpdateMonitor.getCurrentUser());
        this.keyguardUpdateMonitor.registerCallback(this.mKeyguardUpdateMonitorCallback);
    }
}
