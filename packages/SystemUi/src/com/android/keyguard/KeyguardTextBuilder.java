package com.android.keyguard;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.core.graphics.PathParser$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.knox.EdmMonitor;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.knox.accounts.HostAuth;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor = (KeyguardUpdateMonitor) Dependency.get(KeyguardUpdateMonitor.class);
    public final KnoxStateMonitor mKnoxStateMonitor = (KnoxStateMonitor) Dependency.get(KnoxStateMonitor.class);
    public String mPromptReasonType;
    public String mSecurityType;
    public StrongAuthPopup mStrongAuthPopup;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.keyguard.KeyguardTextBuilder$2 */
    public abstract /* synthetic */ class AbstractC07892 {

        /* renamed from: $SwitchMap$com$android$keyguard$KeyguardConstants$KeyguardDismissActionType */
        public static final /* synthetic */ int[] f213xd2b52713;

        /* renamed from: $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode */
        public static final /* synthetic */ int[] f214xdc0e830a;

        static {
            int[] iArr = new int[KeyguardConstants$KeyguardDismissActionType.values().length];
            f213xd2b52713 = iArr;
            try {
                iArr[KeyguardConstants$KeyguardDismissActionType.KEYGUARD_DISMISS_ACTION_SHUTDOWN.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f213xd2b52713[KeyguardConstants$KeyguardDismissActionType.KEYGUARD_DISMISS_ACTION_REBOOT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f213xd2b52713[KeyguardConstants$KeyguardDismissActionType.KEYGUARD_DISMISS_ACTION_FINGERPRINT_ERROR.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f213xd2b52713[KeyguardConstants$KeyguardDismissActionType.KEYGUARD_DISMISS_ACTION_DEFAULT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            int[] iArr2 = new int[KeyguardSecurityModel.SecurityMode.values().length];
            f214xdc0e830a = iArr2;
            try {
                iArr2[KeyguardSecurityModel.SecurityMode.PIN.ordinal()] = 1;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f214xdc0e830a[KeyguardSecurityModel.SecurityMode.Pattern.ordinal()] = 2;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f214xdc0e830a[KeyguardSecurityModel.SecurityMode.Password.ordinal()] = 3;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    enum DismissActionType {
        /* JADX INFO: Fake field, exist only in values array */
        Active("active"),
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
        this.mContext = context;
        this.mDeviceType = DeviceType.isTablet() ? "tablet" : "none";
    }

    public static KeyguardTextBuilder getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new KeyguardTextBuilder(context);
        }
        return sInstance;
    }

    public final String getAddRemainingAttemptIndication(int i) {
        int i2 = this.mAddRemainingAttempt;
        Context context = this.mContext;
        if (i2 <= 0) {
            return context.getString(i);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(context.getString(i));
        sb.append(" (");
        Resources resources = context.getResources();
        int i3 = this.mAddRemainingAttempt;
        return AbstractResolvableFuture$$ExternalSyntheticOutline0.m16m(sb, resources.getQuantityString(R.plurals.kg_attempt_left, i3, Integer.valueOf(i3)), ")");
    }

    public final String getDefaultSecurityMessage(KeyguardSecurityModel.SecurityMode securityMode) {
        String str;
        String format;
        str = "";
        Context context = this.mContext;
        KnoxStateMonitor knoxStateMonitor = this.mKnoxStateMonitor;
        if (knoxStateMonitor != null) {
            KnoxStateMonitorImpl knoxStateMonitorImpl = (KnoxStateMonitorImpl) knoxStateMonitor;
            if (knoxStateMonitorImpl.isDeviceDisabledForMaxFailedAttempt()) {
                EdmMonitor edmMonitor = knoxStateMonitorImpl.mEdmMonitor;
                String str2 = edmMonitor == null ? null : edmMonitor.mPkgNameForMaxAttemptDisable;
                StringBuilder sb = new StringBuilder();
                sb.append(context.getString(android.R.string.font_family_menu_material));
                sb.append(TextUtils.isEmpty(str2) ? "" : PathParser$$ExternalSyntheticOutline0.m29m("(", str2, ")"));
                return sb.toString();
            }
        }
        int currentUser = KeyguardUpdateMonitor.getCurrentUser();
        updateSecurityMode(securityMode);
        updateCurrentState(false);
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateMonitor;
        if (keyguardUpdateMonitor.is2StepVerification()) {
            if (keyguardUpdateMonitor.getUserUnlockedWithBiometric(currentUser)) {
                this.mBiometricType = "none";
            } else {
                if (this.mBiometricType.equals(Biometric.MultiBiometrics.getType())) {
                    return context.getResources().getString(R.string.kg_use_biometrics_first);
                }
                if (this.mBiometricType.equals(Biometric.Fingerprint.getType())) {
                    return context.getResources().getString(R.string.kg_use_fingerprint_first);
                }
            }
        }
        boolean z = this.mIsFace;
        String str3 = this.mDeviceType;
        if (z && (keyguardUpdateMonitor.isCameraDisabledByPolicy() || keyguardUpdateMonitor.isFaceDisabled(currentUser))) {
            format = this.mIsFingerprint ? (DismissActionType.ShutDown.getType().equals(this.mDismissActionType) || DismissActionType.Reboot.getType().equals(this.mDismissActionType)) ? String.format(context.getResources().getString(R.string.kg_device_biometric_security_active_instructions), str3, Biometric.Fingerprint.getType(), this.mSecurityType, this.mDismissActionType) : String.format(context.getResources().getString(R.string.kg_device_biometric_security_reason_instructions), "none", Biometric.FaceFingerprint.getType(), this.mSecurityType, PromptReason.ItPolicy.getType()) : (DismissActionType.ShutDown.getType().equals(this.mDismissActionType) || DismissActionType.Reboot.getType().equals(this.mDismissActionType)) ? String.format(context.getResources().getString(R.string.kg_device_biometric_security_active_instructions), str3, "none", this.mSecurityType, this.mDismissActionType) : String.format(context.getResources().getString(R.string.kg_device_biometric_security_reason_instructions), "none", this.mBiometricType, this.mSecurityType, PromptReason.DeviceAdmin.getType());
        } else if (DismissActionType.ShutDown.getType().equals(this.mDismissActionType) || DismissActionType.Reboot.getType().equals(this.mDismissActionType)) {
            format = String.format(context.getResources().getString(R.string.kg_device_biometric_security_active_instructions), str3, this.mBiometricType, this.mSecurityType, this.mDismissActionType);
        } else {
            if (DismissActionType.FingerPrintError.getType().equals(this.mDismissActionType)) {
                return context.getResources().getString(R.string.kg_finger_print_template_changed_error_message);
            }
            format = String.format(context.getResources().getString(R.string.kg_biometric_security_active_instructions), keyguardUpdateMonitor.isForgotPasswordView() ? "none" : (LsRune.SECURITY_FINGERPRINT_IN_DISPLAY || !this.mIsFingerprint) ? "none" : Biometric.Fingerprint.getType(), this.mSecurityType, this.mDismissActionType);
        }
        int identifier = context.getResources().getIdentifier(format, "string", context.getPackageName());
        if (identifier != 0) {
            str = getAddRemainingAttemptIndication(identifier);
        } else {
            ListPopupWindow$$ExternalSyntheticOutline0.m10m("Can't find default string id=", identifier, "KeyguardTextBuilder");
        }
        return str;
    }

    /* JADX WARN: Removed duplicated region for block: B:36:0x00f0  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00f5  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final String getPromptSecurityMessage(KeyguardSecurityModel.SecurityMode securityMode, int i) {
        int identifier;
        Context context = this.mContext;
        KnoxStateMonitor knoxStateMonitor = this.mKnoxStateMonitor;
        if (knoxStateMonitor != null) {
            KnoxStateMonitorImpl knoxStateMonitorImpl = (KnoxStateMonitorImpl) knoxStateMonitor;
            if (knoxStateMonitorImpl.isDeviceDisabledForMaxFailedAttempt()) {
                EdmMonitor edmMonitor = knoxStateMonitorImpl.mEdmMonitor;
                String str = edmMonitor == null ? null : edmMonitor.mPkgNameForMaxAttemptDisable;
                StringBuilder sb = new StringBuilder();
                sb.append(context.getString(android.R.string.font_family_menu_material));
                sb.append(TextUtils.isEmpty(str) ? "" : PathParser$$ExternalSyntheticOutline0.m29m("(", str, ")"));
                return sb.toString();
            }
        }
        updateSecurityMode(securityMode);
        updateCurrentState(true);
        String str2 = this.mBiometricType;
        if (i == 1) {
            this.mPromptReasonType = PromptReason.Restart.getType();
        } else {
            if (i != 2) {
                if (i == 3) {
                    this.mPromptReasonType = PromptReason.DeviceAdmin.getType();
                } else if (i == 7) {
                    this.mPromptReasonType = PromptReason.NonStrongBiometricTimeout.getType();
                } else {
                    if (i != 17) {
                        return "";
                    }
                    this.mPromptReasonType = PromptReason.NonStrongBiometricTimeout.getType();
                    if (this.mIsFingerprint) {
                        str2 = Biometric.MultiBiometrics.getType();
                    }
                }
                identifier = context.getResources().getIdentifier((!DismissActionType.ShutDown.getType().equals(this.mDismissActionType) || DismissActionType.Reboot.getType().equals(this.mDismissActionType)) ? String.format(context.getResources().getString(R.string.kg_device_biometric_security_active_instructions), this.mDeviceType, "none", this.mSecurityType, this.mDismissActionType) : String.format(context.getResources().getString(R.string.kg_device_biometric_security_reason_instructions), "none", str2, this.mSecurityType, this.mPromptReasonType), "string", context.getPackageName());
                if (identifier == 0) {
                    return getAddRemainingAttemptIndication(identifier);
                }
                ListPopupWindow$$ExternalSyntheticOutline0.m10m("Can't find prompt string id=", identifier, "KeyguardTextBuilder");
                return "";
            }
            this.mPromptReasonType = PromptReason.Timeout.getType();
        }
        str2 = "none";
        identifier = context.getResources().getIdentifier((!DismissActionType.ShutDown.getType().equals(this.mDismissActionType) || DismissActionType.Reboot.getType().equals(this.mDismissActionType)) ? String.format(context.getResources().getString(R.string.kg_device_biometric_security_active_instructions), this.mDeviceType, "none", this.mSecurityType, this.mDismissActionType) : String.format(context.getResources().getString(R.string.kg_device_biometric_security_reason_instructions), "none", str2, this.mSecurityType, this.mPromptReasonType), "string", context.getPackageName());
        if (identifier == 0) {
        }
    }

    public final String getStrongAuthTimeOutMessage(KeyguardSecurityModel.SecurityMode securityMode) {
        String type;
        Context context = this.mContext;
        Resources resources = context.getResources();
        updateSecurityMode(securityMode);
        updateCurrentState(true);
        String string = resources.getString(R.string.kg_device_biometric_security_level_instructions);
        Object[] objArr = new Object[4];
        objArr[0] = this.mDeviceType;
        objArr[1] = this.mBiometricType;
        objArr[2] = this.mSecurityType;
        if (PromptReason.NonStrongBiometricTimeout.getType().equals(this.mPromptReasonType)) {
            type = (!this.mKeyguardUpdateMonitor.mStrongAuthTracker.isNonStrongBiometricAllowedAfterIdleTimeout(KeyguardUpdateMonitor.getCurrentUser()) ? BiometricSecurityLevel.IdleTimeout : BiometricSecurityLevel.Weak).getType();
        } else {
            type = BiometricSecurityLevel.Strong.getType();
        }
        objArr[3] = type;
        int identifier = resources.getIdentifier(String.format(string, objArr), "string", context.getPackageName());
        if (identifier != 0) {
            return context.getString(identifier);
        }
        ListPopupWindow$$ExternalSyntheticOutline0.m10m("Can't find strong auth timeout string id=", identifier, "KeyguardTextBuilder");
        return "";
    }

    public final String getWarningAutoWipeMessage(int i, int i2) {
        String str = i == 1 ? "1" : "none";
        String str2 = i2 == 1 ? "1" : "none";
        Context context = this.mContext;
        int identifier = context.getResources().getIdentifier(String.format(context.getResources().getString(R.string.kg_warning_device_attempt_remaining_auto_wipe), this.mDeviceType, str, str2), "string", context.getPackageName());
        if (identifier != 0) {
            return ("none".equals(str) && "none".equals(str2)) ? context.getString(identifier, Integer.valueOf(i), Integer.valueOf(i2)) : "1".equals(str) ? context.getString(identifier, Integer.valueOf(i2)) : context.getString(identifier, Integer.valueOf(i));
        }
        ListPopupWindow$$ExternalSyntheticOutline0.m10m("Can't find the warning on auto wipe string id=", identifier, "KeyguardTextBuilder");
        return "";
    }

    public final void updateCurrentState(boolean z) {
        int currentUser = KeyguardUpdateMonitor.getCurrentUser();
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateMonitor;
        boolean z2 = false;
        boolean z3 = keyguardUpdateMonitor.isFingerprintDisabled(currentUser) || keyguardUpdateMonitor.isBiometricErrorLockoutPermanent();
        this.mAddRemainingAttempt = keyguardUpdateMonitor.getRemainingAttempt(0);
        int biometricType = keyguardUpdateMonitor.getBiometricType(currentUser);
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
            if (keyguardUpdateMonitor.is2StepVerification() && keyguardUpdateMonitor.getUserUnlockedWithBiometric(currentUser)) {
                this.mIsFingerprint = false;
            }
            if (keyguardUpdateMonitor.isCameraDisabledByPolicy() || keyguardUpdateMonitor.isFaceDisabled(currentUser)) {
                if (this.mIsFace && keyguardUpdateMonitor.isUnlockingWithBiometricAllowed(keyguardUpdateMonitor.getFaceStrongBiometric())) {
                    z2 = true;
                }
                this.mIsFace = z2;
            } else {
                if ((this.mIsFace || (z6 && ((SettingsHelper) Dependency.get(SettingsHelper.class)).isOneHandModeRunning())) && keyguardUpdateMonitor.isFaceDetectionRunning()) {
                    z2 = true;
                }
                this.mIsFace = z2;
            }
        }
        boolean z7 = this.mIsFingerprint;
        if (z7 && this.mIsFace) {
            this.mBiometricType = Biometric.MultiBiometrics.getType();
        } else if (z7) {
            this.mBiometricType = Biometric.Fingerprint.getType();
        } else if (this.mIsFace) {
            this.mBiometricType = Biometric.Face.getType();
        } else {
            this.mBiometricType = "none";
        }
        if (!keyguardUpdateMonitor.isDismissActionExist()) {
            this.mDismissActionType = keyguardUpdateMonitor.isForgotPasswordView() ? "prev" : "none";
            return;
        }
        int i = AbstractC07892.f213xd2b52713[keyguardUpdateMonitor.getDismissActionType().ordinal()];
        if (i == 1) {
            this.mDismissActionType = DismissActionType.ShutDown.getType();
            return;
        }
        if (i == 2) {
            this.mDismissActionType = DismissActionType.Reboot.getType();
        } else if (i != 3) {
            this.mDismissActionType = "none";
        } else {
            this.mDismissActionType = DismissActionType.FingerPrintError.getType();
        }
    }

    public final void updateSecurityMode(KeyguardSecurityModel.SecurityMode securityMode) {
        int i = AbstractC07892.f214xdc0e830a[securityMode.ordinal()];
        if (i == 1) {
            this.mSecurityType = Security.PIN.getType();
        } else if (i != 2) {
            this.mSecurityType = Security.Password.getType();
        } else {
            this.mSecurityType = Security.Pattern.getType();
        }
    }
}
