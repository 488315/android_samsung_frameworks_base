package com.android.keyguard;

import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.knox.EdmMonitor;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import com.samsung.android.knox.accounts.HostAuth;
import com.samsung.android.knox.zt.config.securelog.SignalSeverity;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class KeyguardTextBuilder {
    public static KeyguardTextBuilder sInstance;
    public int mAddRemainingAttempt;
    public String mBiometricType;
    public final Context mContext;
    public final String mDeviceType;
    public String mDismissActionType;
    public boolean mIsFace;
    public boolean mIsFingerprint;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final KnoxStateMonitor mKnoxStateMonitor;
    public String mPromptReasonType;
    public String mSecurityType;
    public StrongAuthPopup mStrongAuthPopup;
    public int mUserId;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.keyguard.KeyguardTextBuilder$3, reason: invalid class name */
    public abstract /* synthetic */ class AnonymousClass3 {
        public static final /* synthetic */ int[] $SwitchMap$com$android$keyguard$KeyguardConstants$KeyguardDismissActionType;
        public static final /* synthetic */ int[] $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode;

        static {
            int[] iArr = new int[KeyguardConstants$KeyguardDismissActionType.values().length];
            $SwitchMap$com$android$keyguard$KeyguardConstants$KeyguardDismissActionType = iArr;
            try {
                iArr[KeyguardConstants$KeyguardDismissActionType.KEYGUARD_DISMISS_ACTION_SHUTDOWN.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$android$keyguard$KeyguardConstants$KeyguardDismissActionType[KeyguardConstants$KeyguardDismissActionType.KEYGUARD_DISMISS_ACTION_REBOOT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$android$keyguard$KeyguardConstants$KeyguardDismissActionType[KeyguardConstants$KeyguardDismissActionType.KEYGUARD_DISMISS_ACTION_FINGERPRINT_ERROR.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$android$keyguard$KeyguardConstants$KeyguardDismissActionType[KeyguardConstants$KeyguardDismissActionType.KEYGUARD_DISMISS_ACTION_DEFAULT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            int[] iArr2 = new int[KeyguardSecurityModel.SecurityMode.values().length];
            $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode = iArr2;
            try {
                iArr2[KeyguardSecurityModel.SecurityMode.PIN.ordinal()] = 1;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode[KeyguardSecurityModel.SecurityMode.Pattern.ordinal()] = 2;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode[KeyguardSecurityModel.SecurityMode.Password.ordinal()] = 3;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    enum Biometric {
        Fingerprint("fingerprint"),
        Face("face"),
        MultiBiometrics("multi"),
        FaceFingerprint("facefingerprint");

        private final String mBiometric;

        Biometric(String str) {
            this.mBiometric = str;
        }

        public final String getType() {
            return this.mBiometric;
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    enum BiometricSecurityLevel {
        Strong("strong"),
        Weak("weak"),
        IdleTimeout("idletimeout");

        private final String mBiometricSecurityLevel;

        BiometricSecurityLevel(String str) {
            this.mBiometricSecurityLevel = str;
        }

        public final String getType() {
            return this.mBiometricSecurityLevel;
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    enum DismissActionType {
        /* JADX INFO: Fake field, exist only in values array */
        Active(SystemUIAnalytics.QPBSE_KEY_ACTIVE),
        ShutDown("shutdown"),
        Reboot("reboot"),
        /* JADX INFO: Fake field, exist only in values array */
        Dex("dex"),
        FingerPrintError("fingerprinterror");

        private final String mDismissType;

        DismissActionType(String str) {
            this.mDismissType = str;
        }

        public final String getType() {
            return this.mDismissType;
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    enum PromptReason {
        Restart("restart"),
        Timeout("timeout"),
        NonStrongBiometricTimeout("nonstrongbiometrictimeout"),
        DeviceAdmin("deviceadmin"),
        ItPolicy("itpolicy");

        private final String mPromptReason;

        PromptReason(String str) {
            this.mPromptReason = str;
        }

        public final String getType() {
            return this.mPromptReason;
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    enum Security {
        PIN("pin"),
        Pattern("pattern"),
        Password(HostAuth.PASSWORD);

        private final String mSecurity;

        Security(String str) {
            this.mSecurity = str;
        }

        public final String getType() {
            return this.mSecurity;
        }
    }

    public KeyguardTextBuilder(Context context) {
        KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.keyguard.KeyguardTextBuilder.1
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onUserSwitchComplete(int i) {
                KeyguardTextBuilder keyguardTextBuilder = KeyguardTextBuilder.this;
                if (keyguardTextBuilder.mUserId != i) {
                    keyguardTextBuilder.mUserId = i;
                }
            }
        };
        this.mUserId = 0;
        this.mContext = context;
        this.mUserId = ActivityManager.getCurrentUser();
        KeyguardUpdateMonitor keyguardUpdateMonitor = (KeyguardUpdateMonitor) Dependency.sDependency.getDependencyInner(KeyguardUpdateMonitor.class);
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        keyguardUpdateMonitor.registerCallback(keyguardUpdateMonitorCallback);
        this.mKnoxStateMonitor = (KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class);
        this.mDeviceType = DeviceType.isTablet() ? "tablet" : SignalSeverity.NONE;
    }

    public static KeyguardTextBuilder getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new KeyguardTextBuilder(context);
        }
        return sInstance;
    }

    public final String getAddRemainingAttemptIndication(int i) {
        if (this.mAddRemainingAttempt <= 0) {
            return this.mContext.getString(i);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(this.mContext.getString(i));
        sb.append(" (");
        Resources resources = this.mContext.getResources();
        int i2 = this.mAddRemainingAttempt;
        return ComponentActivity$1$$ExternalSyntheticOutline0.m(sb, resources.getQuantityString(R.plurals.kg_attempt_left, i2, Integer.valueOf(i2)), ")");
    }

    public final String getDefaultSecurityMessage(KeyguardSecurityModel.SecurityMode securityMode) {
        String str;
        String format;
        str = "";
        KnoxStateMonitor knoxStateMonitor = this.mKnoxStateMonitor;
        if (knoxStateMonitor != null) {
            KnoxStateMonitorImpl knoxStateMonitorImpl = (KnoxStateMonitorImpl) knoxStateMonitor;
            if (knoxStateMonitorImpl.isDeviceDisabledForMaxFailedAttempt()) {
                EdmMonitor edmMonitor = knoxStateMonitorImpl.mEdmMonitor;
                String str2 = edmMonitor == null ? null : edmMonitor.mPkgNameForMaxAttemptDisable;
                StringBuilder sb = new StringBuilder();
                sb.append(this.mContext.getString(android.R.string.grant_credentials_permission_message_footer));
                sb.append(TextUtils.isEmpty(str2) ? "" : ContentInViewNode$Request$$ExternalSyntheticOutline0.m("(", str2, ")"));
                return sb.toString();
            }
        }
        updateSecurityMode(securityMode);
        updateCurrentState(false);
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateMonitor;
        boolean is2StepVerification = keyguardUpdateMonitor.is2StepVerification();
        String str3 = SignalSeverity.NONE;
        if (is2StepVerification) {
            if (keyguardUpdateMonitor.getUserUnlockedWithBiometric(this.mUserId)) {
                this.mBiometricType = SignalSeverity.NONE;
            } else {
                if (this.mBiometricType.equals(Biometric.MultiBiometrics.getType())) {
                    return this.mContext.getResources().getString(R.string.kg_use_biometrics_first);
                }
                if (this.mBiometricType.equals(Biometric.Fingerprint.getType())) {
                    return this.mContext.getResources().getString(R.string.kg_use_fingerprint_first);
                }
            }
        }
        boolean z = this.mIsFace;
        String str4 = this.mDeviceType;
        if (z && (keyguardUpdateMonitor.isCameraDisabledByPolicy() || keyguardUpdateMonitor.isFaceDisabled(this.mUserId))) {
            format = this.mIsFingerprint ? (DismissActionType.ShutDown.getType().equals(this.mDismissActionType) || DismissActionType.Reboot.getType().equals(this.mDismissActionType)) ? String.format(this.mContext.getResources().getString(R.string.kg_device_biometric_security_active_instructions), str4, Biometric.Fingerprint.getType(), this.mSecurityType, this.mDismissActionType) : String.format(this.mContext.getResources().getString(R.string.kg_device_biometric_security_reason_instructions), SignalSeverity.NONE, Biometric.FaceFingerprint.getType(), this.mSecurityType, PromptReason.ItPolicy.getType()) : (DismissActionType.ShutDown.getType().equals(this.mDismissActionType) || DismissActionType.Reboot.getType().equals(this.mDismissActionType)) ? String.format(this.mContext.getResources().getString(R.string.kg_device_biometric_security_active_instructions), str4, SignalSeverity.NONE, this.mSecurityType, this.mDismissActionType) : String.format(this.mContext.getResources().getString(R.string.kg_device_biometric_security_reason_instructions), SignalSeverity.NONE, this.mBiometricType, this.mSecurityType, PromptReason.DeviceAdmin.getType());
        } else if (DismissActionType.ShutDown.getType().equals(this.mDismissActionType) || DismissActionType.Reboot.getType().equals(this.mDismissActionType)) {
            format = String.format(this.mContext.getResources().getString(R.string.kg_device_biometric_security_active_instructions), str4, this.mBiometricType, this.mSecurityType, this.mDismissActionType);
        } else {
            if (DismissActionType.FingerPrintError.getType().equals(this.mDismissActionType)) {
                return this.mContext.getResources().getString(R.string.kg_finger_print_template_changed_error_message);
            }
            String type = (LsRune.SECURITY_FINGERPRINT_IN_DISPLAY || !this.mIsFingerprint) ? SignalSeverity.NONE : Biometric.Fingerprint.getType();
            if (!keyguardUpdateMonitor.isForgotPasswordView()) {
                str3 = type;
            }
            format = String.format(this.mContext.getResources().getString(R.string.kg_biometric_security_active_instructions), str3, this.mSecurityType, this.mDismissActionType);
        }
        int identifier = this.mContext.getResources().getIdentifier(format, "string", this.mContext.getPackageName());
        if (identifier != 0) {
            str = getAddRemainingAttemptIndication(identifier);
        } else {
            ListPopupWindow$$ExternalSyntheticOutline0.m(identifier, "Can't find default string id=", "KeyguardTextBuilder");
        }
        return str;
    }

    /* JADX WARN: Removed duplicated region for block: B:36:0x00f9  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00fe  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.String getPromptSecurityMessage(com.android.keyguard.KeyguardSecurityModel.SecurityMode r5, int r6) {
        /*
            Method dump skipped, instructions count: 262
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.keyguard.KeyguardTextBuilder.getPromptSecurityMessage(com.android.keyguard.KeyguardSecurityModel$SecurityMode, int):java.lang.String");
    }

    public final String getStrongAuthTimeOutMessage(KeyguardSecurityModel.SecurityMode securityMode) {
        String type;
        Resources resources = this.mContext.getResources();
        updateSecurityMode(securityMode);
        updateCurrentState(true);
        String string = resources.getString(R.string.kg_device_biometric_security_level_instructions);
        String str = this.mBiometricType;
        String str2 = this.mSecurityType;
        if (PromptReason.NonStrongBiometricTimeout.getType().equals(this.mPromptReasonType)) {
            type = (!this.mKeyguardUpdateMonitor.mStrongAuthTracker.isNonStrongBiometricAllowedAfterIdleTimeout(this.mUserId) ? BiometricSecurityLevel.IdleTimeout : BiometricSecurityLevel.Weak).getType();
        } else {
            type = BiometricSecurityLevel.Strong.getType();
        }
        int identifier = resources.getIdentifier(String.format(string, this.mDeviceType, str, str2, type), "string", this.mContext.getPackageName());
        if (identifier != 0) {
            return this.mContext.getString(identifier);
        }
        ListPopupWindow$$ExternalSyntheticOutline0.m(identifier, "Can't find strong auth timeout string id=", "KeyguardTextBuilder");
        return "";
    }

    public final String getWarningAutoWipeMessage(int i, int i2) {
        Object obj = i == 1 ? "1" : SignalSeverity.NONE;
        Object obj2 = i2 == 1 ? "1" : SignalSeverity.NONE;
        int identifier = this.mContext.getResources().getIdentifier(String.format(this.mContext.getResources().getString(R.string.kg_warning_device_attempt_remaining_auto_wipe), this.mDeviceType, obj, obj2), "string", this.mContext.getPackageName());
        if (identifier != 0) {
            return (SignalSeverity.NONE.equals(obj) && SignalSeverity.NONE.equals(obj2)) ? this.mContext.getString(identifier, Integer.valueOf(i), Integer.valueOf(i2)) : "1".equals(obj) ? this.mContext.getString(identifier, Integer.valueOf(i2)) : this.mContext.getString(identifier, Integer.valueOf(i));
        }
        ListPopupWindow$$ExternalSyntheticOutline0.m(identifier, "Can't find the warning on auto wipe string id=", "KeyguardTextBuilder");
        return "";
    }

    public final void updateCurrentState(boolean z) {
        int i = this.mUserId;
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateMonitor;
        boolean z2 = false;
        boolean z3 = keyguardUpdateMonitor.isFingerprintDisabled(i) || keyguardUpdateMonitor.isBiometricErrorLockoutPermanent();
        this.mAddRemainingAttempt = keyguardUpdateMonitor.getRemainingAttempt(0);
        int biometricType = keyguardUpdateMonitor.getBiometricType(this.mUserId);
        boolean z4 = !z3 && (biometricType & 1) == 1;
        this.mIsFingerprint = z4;
        this.mIsFace = !z3 && (biometricType & 256) == 256;
        if (!z) {
            boolean z5 = z4 && keyguardUpdateMonitor.isUnlockingWithBiometricAllowed(true);
            this.mIsFingerprint = z5;
            boolean z6 = LsRune.SECURITY_FINGERPRINT_IN_DISPLAY;
            if (z6) {
                this.mIsFingerprint = z5 && keyguardUpdateMonitor.isFingerprintDetectionRunning();
            }
            if (keyguardUpdateMonitor.is2StepVerification() && keyguardUpdateMonitor.getUserUnlockedWithBiometric(this.mUserId)) {
                this.mIsFingerprint = false;
            }
            if (keyguardUpdateMonitor.isCameraDisabledByPolicy() || keyguardUpdateMonitor.isFaceDisabled(this.mUserId)) {
                if (this.mIsFace && keyguardUpdateMonitor.isUnlockingWithBiometricAllowed(keyguardUpdateMonitor.getFaceStrongBiometric())) {
                    z2 = true;
                }
                this.mIsFace = z2;
            } else {
                if ((this.mIsFace || (z6 && ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isOneHandModeRunning())) && keyguardUpdateMonitor.isFaceDetectionRunning()) {
                    z2 = true;
                }
                this.mIsFace = z2;
            }
        }
        boolean z7 = this.mIsFingerprint;
        String str = SignalSeverity.NONE;
        if (z7 && this.mIsFace) {
            this.mBiometricType = Biometric.MultiBiometrics.getType();
        } else if (z7) {
            this.mBiometricType = Biometric.Fingerprint.getType();
        } else if (this.mIsFace) {
            this.mBiometricType = Biometric.Face.getType();
        } else {
            this.mBiometricType = SignalSeverity.NONE;
        }
        if (!keyguardUpdateMonitor.isDismissActionExist()) {
            if (keyguardUpdateMonitor.isForgotPasswordView()) {
                str = "prev";
            }
            this.mDismissActionType = str;
            return;
        }
        int i2 = AnonymousClass3.$SwitchMap$com$android$keyguard$KeyguardConstants$KeyguardDismissActionType[keyguardUpdateMonitor.getDismissActionType().ordinal()];
        if (i2 == 1) {
            this.mDismissActionType = DismissActionType.ShutDown.getType();
            return;
        }
        if (i2 == 2) {
            this.mDismissActionType = DismissActionType.Reboot.getType();
        } else if (i2 != 3) {
            this.mDismissActionType = SignalSeverity.NONE;
        } else {
            this.mDismissActionType = DismissActionType.FingerPrintError.getType();
        }
    }

    public final void updateSecurityMode(KeyguardSecurityModel.SecurityMode securityMode) {
        int i = AnonymousClass3.$SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode[securityMode.ordinal()];
        if (i == 1) {
            this.mSecurityType = Security.PIN.getType();
        } else if (i != 2) {
            this.mSecurityType = Security.Password.getType();
        } else {
            this.mSecurityType = Security.Pattern.getType();
        }
    }
}
