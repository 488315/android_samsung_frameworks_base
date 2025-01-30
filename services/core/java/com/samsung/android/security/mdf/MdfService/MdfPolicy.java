package com.samsung.android.security.mdf.MdfService;

import android.R;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.Process;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.os.IInstalld;
import android.sec.enterprise.auditlog.AuditLog;
import android.util.Log;
import com.android.internal.widget.LockPatternUtils;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.EnterpriseKnoxManager;
import com.samsung.android.knox.custom.LauncherConfigurationInternal;
import com.samsung.android.knox.devicesecurity.PasswordPolicy;
import com.samsung.android.knox.keystore.CertificatePolicy;
import com.samsung.android.knox.restriction.RestrictionPolicy;
import com.samsung.android.security.SemSdCardEncryption;
import com.samsung.android.security.mdf.MdfUtils;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public class MdfPolicy {
    public static MdfPolicy sInstance;
    public CertificatePolicy mCertificatePolicy;
    public Context mContext;
    public DevicePolicyManager mDevicePolicyManager;
    public boolean mIsExternalSDRemovable;
    public boolean mIsFaceLockDisabled;
    public boolean mIsOcspCheckEnabled;
    public boolean mIsPasswordEnabled;
    public boolean mIsRevocationCheckEnabled;
    public boolean mIsSDEnabled;
    public boolean mIsSDEncrypted;
    public boolean mIsSmartLockDisabled;
    public LockPatternUtils mLockPatternUtils;
    public int mMaximumFailedPasswordsForWipe;
    public MdfUtils mMdfUtils;
    public PasswordPolicy mPasswordPolicy;
    public int mPasswordQuality;
    public RestrictionPolicy mRestrictionPolicy;
    public UserManager mUserManager;

    public MdfPolicy(Context context) {
        printVersion();
        this.mContext = context;
        this.mDevicePolicyManager = (DevicePolicyManager) context.getSystemService("device_policy");
        this.mLockPatternUtils = new LockPatternUtils(context.getApplicationContext());
        this.mCertificatePolicy = EnterpriseKnoxManager.getInstance(this.mContext).getCertificatePolicy();
        this.mPasswordPolicy = EnterpriseDeviceManager.getInstance(this.mContext).getPasswordPolicy();
        this.mRestrictionPolicy = EnterpriseDeviceManager.getInstance(this.mContext).getRestrictionPolicy();
        this.mUserManager = (UserManager) context.getSystemService("user");
        this.mMdfUtils = new MdfUtils();
    }

    public static synchronized MdfPolicy getInstance(Context context) {
        MdfPolicy mdfPolicy;
        synchronized (MdfPolicy.class) {
            if (sInstance == null) {
                sInstance = new MdfPolicy(context);
            }
            mdfPolicy = sInstance;
        }
        return mdfPolicy;
    }

    public final void setNotification(String str, String str2) {
        Log.d("MdfService", "Notice for applying security policy");
        NotificationManager notificationManager = (NotificationManager) this.mContext.getSystemService("notification");
        if (notificationManager == null) {
            Log.d("MdfService", "NotificationManager is null");
            return;
        }
        NotificationChannel notificationChannel = new NotificationChannel("mdf_channel_id", "Mdf Channel", 4);
        notificationChannel.enableVibration(true);
        notificationChannel.setVibrationPattern(new long[]{0, 500});
        notificationManager.createNotificationChannel(notificationChannel);
        Notification.Builder builder = new Notification.Builder(this.mContext);
        builder.setContentTitle(str);
        builder.setContentText(str2);
        builder.setSmallIcon(R.drawable.ic_dialog_alert);
        builder.setPriority(2);
        builder.setAutoCancel(false);
        builder.setChannelId(notificationChannel.getId());
        notificationManager.notify(this.mContext.getApplicationInfo().uid, builder.build());
    }

    public final void logForAudit(int i, String str) {
        AuditLog.log(1, i, false, Process.myPid(), "MdfStatus", str);
    }

    public final void logForAuditAndLogcat(int i, int i2, String str) {
        logForAudit(i, str);
        Log.println(i2, "MdfService", str);
    }

    public boolean isCCModeSupport() {
        return "Enabled".equals(SystemProperties.get("ro.security.mdf.ux"));
    }

    public final boolean isAECommonCriteriaModeEnabled() {
        DevicePolicyManager devicePolicyManager = this.mDevicePolicyManager;
        if (devicePolicyManager != null) {
            return devicePolicyManager.isCommonCriteriaModeEnabled(null);
        }
        Log.e("MdfService", "Failed isCommonCriteriaMode(). mDevicePolicyManager is null");
        return false;
    }

    public int initCCMode() {
        int i;
        try {
            checkSystemUid();
            int checkCCModeOnDevice = checkCCModeOnDevice();
            Log.d("MdfService", "current mode : " + Integer.toString(checkCCModeOnDevice));
            if (checkCCModeOnDevice == 1) {
                int checkFipsSelftest = checkFipsSelftest();
                if (checkFipsSelftest != 0) {
                    Log.e("MdfService", "Failed. check fips self test. res = " + Integer.toString(checkFipsSelftest));
                    setNotification(this.mContext.getResources().getString(R.string.permdesc_useFingerprint), this.mContext.getResources().getString(R.string.permdesc_useBiometric));
                    i = setCCMode(8);
                    if (i != 0) {
                        Log.e("MdfService", "Failed. setCCMode. res = " + Integer.toString(i));
                    }
                } else {
                    i = checkDevicePolicy();
                    if (i != 0) {
                        Log.e("MdfService", "Prerequisite policies have yet to set. res = " + Integer.toString(i));
                    }
                }
            } else if (checkCCModeOnDevice == 4) {
                i = setCCMode(4);
                if (i != 0) {
                    Log.e("MdfService", "Failed. setCCMode. res = " + Integer.toString(i));
                }
            } else if (checkCCModeOnDevice == 8) {
                int cCMode = setCCMode(8);
                if (cCMode != 0) {
                    Log.e("MdfService", "Failed. setCCMode. res = " + Integer.toString(cCMode));
                }
                i = -11;
            } else if (checkCCModeOnDevice == 16) {
                int cCMode2 = setCCMode(16);
                if (cCMode2 != 0) {
                    Log.e("MdfService", "Failed. setCCMode. res = " + Integer.toString(cCMode2));
                }
                i = -12;
            } else {
                int cCMode3 = setCCMode(16);
                if (cCMode3 != 0) {
                    Log.e("MdfService", "Failed. setCCMode. res = " + Integer.toString(cCMode3));
                }
                i = -13;
            }
            Log.i("MdfService", "AE CommonCriteriaMode is " + Boolean.toString(isAECommonCriteriaModeEnabled()));
            return i;
        } catch (SecurityException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public int enableCCMode(boolean z) {
        SystemProperties.set("security.mdf.result", "None");
        if (!isCCModeSupport()) {
            Log.e("MdfService", "This model does not support CC mode.");
            SystemProperties.set("security.mdf.result", Integer.toString(-16));
            return -16;
        }
        int checkCCModeOnDevice = checkCCModeOnDevice();
        Log.d("MdfService", "the current mode : " + Integer.toString(checkCCModeOnDevice));
        if (!z) {
            if (checkCCModeOnDevice == 1) {
                int cCMode = setCCMode(4);
                if (cCMode != 0) {
                    Log.e("MdfService", "Failed. setCCMode. res = " + Integer.toString(cCMode));
                    return cCMode;
                }
                setNotification(this.mContext.getResources().getString(R.string.permdesc_useDataInBackground), this.mContext.getResources().getString(R.string.permdesc_sim_communication));
                SystemProperties.set("security.mdf.result", Integer.toString(cCMode));
                return cCMode;
            }
            if (checkCCModeOnDevice == 4) {
                Log.e("MdfService", "CCMode is already ready.");
                return 0;
            }
            if (checkCCModeOnDevice == 8) {
                int cCMode2 = setCCMode(8);
                if (cCMode2 != 0) {
                    Log.e("MdfService", "Failed. setCCMode. res = " + Integer.toString(cCMode2));
                    return cCMode2;
                }
                setNotification(this.mContext.getResources().getString(R.string.permdesc_useFingerprint), this.mContext.getResources().getString(R.string.permdesc_useBiometric));
                SystemProperties.set("security.mdf.result", Integer.toString(-11));
                return -11;
            }
            if (checkCCModeOnDevice == 16) {
                int cCMode3 = setCCMode(16);
                if (cCMode3 != 0) {
                    Log.e("MdfService", "Failed. setCCMode. res = " + Integer.toString(cCMode3));
                }
                SystemProperties.set("security.mdf.result", Integer.toString(-12));
                return -12;
            }
            int cCMode4 = setCCMode(16);
            if (cCMode4 != 0) {
                Log.e("MdfService", "Failed. setCCMode. res = " + Integer.toString(cCMode4));
            }
            SystemProperties.set("security.mdf.result", Integer.toString(-13));
            return -13;
        }
        if (checkCCModeOnDevice == 1) {
            Log.e("MdfService", "CCMode is already enabled.");
            return 0;
        }
        if (checkCCModeOnDevice != 4) {
            if (checkCCModeOnDevice == 16) {
                int cCMode5 = setCCMode(16);
                if (cCMode5 != 0) {
                    Log.e("MdfService", "Failed. setCCMode. res = " + Integer.toString(cCMode5));
                }
                SystemProperties.set("security.mdf.result", Integer.toString(-12));
                return -12;
            }
            if (checkCCModeOnDevice == 8) {
                int cCMode6 = setCCMode(8);
                if (cCMode6 != 0) {
                    Log.e("MdfService", "Failed. setCCMode. res = " + Integer.toString(cCMode6));
                }
                SystemProperties.set("security.mdf.result", Integer.toString(-11));
                return -11;
            }
            int cCMode7 = setCCMode(16);
            if (cCMode7 != 0) {
                Log.e("MdfService", "Failed. setCCMode. res = " + Integer.toString(cCMode7));
            }
            SystemProperties.set("security.mdf.result", Integer.toString(-13));
            return -13;
        }
        int checkDevicePolicy = checkDevicePolicy();
        SystemProperties.set("security.mdf.result", Integer.toString(checkDevicePolicy));
        if (checkDevicePolicy != 0) {
            Log.e("MdfService", "Prerequisite policies have yet to set. res = " + Integer.toString(checkDevicePolicy));
        }
        int checkFipsSelftest = checkFipsSelftest();
        SystemProperties.set("security.mdf.result", Integer.toString(checkFipsSelftest));
        if (checkFipsSelftest != 0) {
            Log.e("MdfService", "Failed. check fips self test. res = " + Integer.toString(checkFipsSelftest));
            int cCMode8 = setCCMode(8);
            if (cCMode8 != 0) {
                Log.e("MdfService", "Failed. setCCMode. res = " + Integer.toString(cCMode8));
                return cCMode8;
            }
            setNotification(this.mContext.getResources().getString(R.string.permdesc_useFingerprint), this.mContext.getResources().getString(R.string.permdesc_useBiometric));
            return cCMode8;
        }
        int cCMode9 = setCCMode(1);
        if (cCMode9 != 0) {
            Log.e("MdfService", "Failed. setCCMode. res = " + Integer.toString(cCMode9));
            return cCMode9;
        }
        setNotification(this.mContext.getResources().getString(R.string.permdesc_useDataInBackground), this.mContext.getResources().getString(R.string.permdesc_setWallpaper));
        return cCMode9;
    }

    public final int checkCCModeOnDevice() {
        int cCModeFlag = this.mMdfUtils.getCCModeFlag();
        if (cCModeFlag == 1) {
            return 1;
        }
        if (cCModeFlag == 4) {
            return 4;
        }
        return cCModeFlag == 8 ? 8 : 16;
    }

    public final int checkDevicePolicy() {
        int i;
        int maximumFailedPasswordsForWipe = getMaximumFailedPasswordsForWipe();
        this.mMaximumFailedPasswordsForWipe = maximumFailedPasswordsForWipe;
        boolean z = false;
        if (maximumFailedPasswordsForWipe <= 0 || maximumFailedPasswordsForWipe > 30) {
            logForAuditAndLogcat(1, 3, String.format("Password attempts : setMaximumFailedPasswordsForWipe() should be set between 1 and 30. Current value = %d", Integer.valueOf(maximumFailedPasswordsForWipe)));
            i = 2;
        } else {
            logForAuditAndLogcat(5, 4, "Password attempts : OK");
            i = 0;
        }
        int passwordQuality = getPasswordQuality();
        this.mPasswordQuality = passwordQuality;
        if (passwordQuality < 327680 || passwordQuality > 393216) {
            logForAuditAndLogcat(1, 3, "Password quality : setPasswordQuality() should be set between alphanumeric and complex.");
            i |= IInstalld.FLAG_FORCE;
        } else {
            logForAuditAndLogcat(5, 4, "Password quality : OK");
        }
        boolean isPasswordEnabled = isPasswordEnabled();
        this.mIsPasswordEnabled = isPasswordEnabled;
        if (!isPasswordEnabled) {
            logForAuditAndLogcat(1, 3, "Screen lock : Screen lock should be set to Password above alphanumeric (Biometric lock is available)");
            i |= 4;
        } else {
            logForAuditAndLogcat(5, 4, "Screen lock : OK");
        }
        this.mIsRevocationCheckEnabled = isRevocationCheckEnabled();
        boolean isOcspCheckEnabled = isOcspCheckEnabled();
        this.mIsOcspCheckEnabled = isOcspCheckEnabled;
        if (isOcspCheckEnabled) {
            logForAuditAndLogcat(5, 4, "Certificate revocation : OK (OCSP/CRL)");
        } else if (this.mIsRevocationCheckEnabled) {
            logForAuditAndLogcat(5, 4, "Certificate revocation : OK (CRL)");
        } else {
            logForAuditAndLogcat(1, 3, "Certificate revocation : enableOcspCheck() or enableRevocationCheck() should be set on all packages.");
            i |= 32;
        }
        boolean isExternalSDRemovable = isExternalSDRemovable();
        this.mIsExternalSDRemovable = isExternalSDRemovable;
        if (isExternalSDRemovable) {
            if (this.mRestrictionPolicy.isSdCardEnabled() && !this.mUserManager.hasUserRestriction("no_physical_media")) {
                z = true;
            }
            this.mIsSDEnabled = z;
            boolean semGetRequireStorageCardEncryption = this.mDevicePolicyManager.semGetRequireStorageCardEncryption(null);
            this.mIsSDEncrypted = semGetRequireStorageCardEncryption;
            if (!this.mIsSDEnabled) {
                logForAuditAndLogcat(5, 4, "SD card status : OK (Blocked)");
            } else if (semGetRequireStorageCardEncryption) {
                logForAuditAndLogcat(5, 4, "SD card status : OK (Encrypted)");
            } else {
                logForAuditAndLogcat(5, 3, "SD card status : setRequireStorageCardEncryption() should be set to true, setSdCardState() should be set to false or DISALLOW_MOUNT_PHYSICAL_MEDIA should be set to addUserRestriction().");
                i |= 128;
            }
        } else {
            logForAuditAndLogcat(5, 4, "SD card status : OK (No slot)");
        }
        boolean isFaceLockDisabled = isFaceLockDisabled();
        this.mIsFaceLockDisabled = isFaceLockDisabled;
        if (isFaceLockDisabled) {
            logForAuditAndLogcat(5, 4, "Face lock : OK");
        } else {
            logForAuditAndLogcat(1, 3, "Face lock : BIOMETRIC_AUTHENTICATION_FACE should be set to false in the setBiometricAuthenticationEnabled() or KEYGUARD_DISABLE_FACE should be set to setKeyguardDisabledFeatures().");
            i |= 16384;
        }
        boolean isSmartLockDisabled = isSmartLockDisabled();
        this.mIsSmartLockDisabled = isSmartLockDisabled;
        if (isSmartLockDisabled) {
            logForAuditAndLogcat(5, 4, "Smart lock : OK");
            return i;
        }
        logForAuditAndLogcat(1, 3, "Smart lock : KEYGUARD_DISABLE_TRUST_AGENTS should be set to setKeyguardDisabledFeatures().");
        return i | 32768;
    }

    public final int checkFipsSelftest() {
        if (this.mMdfUtils.FIPS_Openssl_SelfTest() != 0) {
            logForAuditAndLogcat(1, 6, "FIPS self-test : FAILED");
            return -20;
        }
        logForAuditAndLogcat(5, 4, "FIPS self-test : OK");
        return 0;
    }

    public final int enforceSB(boolean z) {
        int sBFlagOff;
        if (z) {
            sBFlagOff = this.mMdfUtils.setSBFlagOn();
            if (sBFlagOff != 0) {
                Log.e("MdfService", "Failed. setSBFlagOn() res = " + Integer.toString(sBFlagOff));
                return -24;
            }
            int sBFlag = this.mMdfUtils.getSBFlag();
            if (sBFlag != 1) {
                Log.e("MdfService", "Failed. SBFlag has yet to set. current flag = " + Integer.toString(sBFlag));
                return -24;
            }
        } else {
            sBFlagOff = this.mMdfUtils.setSBFlagOff();
            if (sBFlagOff != 0) {
                Log.e("MdfService", "Failed. setSBFlagOff() res = " + Integer.toString(sBFlagOff));
                return -24;
            }
            int sBFlag2 = this.mMdfUtils.getSBFlag();
            if (sBFlag2 != 0) {
                Log.e("MdfService", "Failed. SBFlag has yet to set. current flag = " + Integer.toString(sBFlag2));
                return -24;
            }
        }
        return sBFlagOff;
    }

    public final int setCCModeFlags(int i) {
        int cCModeFlag = this.mMdfUtils.setCCModeFlag(i);
        if (cCModeFlag != 0) {
            Log.e("MdfService", "Failed. setCCModeFlag() res = " + Integer.toString(cCModeFlag));
            return -27;
        }
        int cCModeFlag2 = this.mMdfUtils.getCCModeFlag();
        if (cCModeFlag2 == i) {
            return cCModeFlag;
        }
        Log.e("MdfService", "Failed. CCMode Flag has yet to set. current flag = " + Integer.toString(cCModeFlag2));
        return -27;
    }

    public final int setFlags(boolean z, int i) {
        int enforceSB = enforceSB(z);
        if (enforceSB != 0) {
            Log.d("MdfService", "Failed. enforceSB : " + z + ", result = " + Integer.toString(enforceSB));
            return enforceSB;
        }
        int cCModeFlags = setCCModeFlags(i);
        if (cCModeFlags != 0) {
            Log.d("MdfService", "Failed. setCCModeFlag : " + Integer.toString(i) + ", result = " + Integer.toString(cCModeFlags));
        }
        return cCModeFlags;
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0043 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0044  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int setCCMode(int i) {
        String str;
        int i2;
        int flags;
        String str2;
        boolean z = true;
        if (i != 1) {
            i2 = 4;
            if (i != 4) {
                i2 = 8;
                if (i == 8) {
                    str = "Disabled";
                    flags = setFlags(z, i2);
                    if (flags != 0) {
                        return flags;
                    }
                    try {
                        if (!SystemProperties.get("security.mdf").equals(str)) {
                            SystemProperties.set("security.mdf", str);
                        }
                        logForAudit(5, String.format("CC Mode status : %s", str));
                        Log.d("MdfService", "security.mdf : " + SystemProperties.get("security.mdf"));
                        return flags;
                    } catch (RuntimeException unused) {
                        Log.e("MdfService", "SystemProperties RuntimeException Occurs");
                        return -3;
                    } catch (Exception unused2) {
                        Log.e("MdfService", "SystemProperties Exception Occurs");
                        return -3;
                    }
                }
                str2 = "None";
                i2 = 16;
                if (i != 16) {
                    Log.e("MdfService", "setCCMode default... status = " + Integer.toString(i));
                    str = "None";
                    z = false;
                }
            } else {
                str2 = "Ready";
            }
            str = str2;
            z = false;
            flags = setFlags(z, i2);
            if (flags != 0) {
            }
        } else {
            str = "Enabled";
        }
        i2 = z;
        flags = setFlags(z, i2);
        if (flags != 0) {
        }
    }

    public final int getMaximumFailedPasswordsForWipe() {
        DevicePolicyManager devicePolicyManager = this.mDevicePolicyManager;
        if (devicePolicyManager != null) {
            return devicePolicyManager.getMaximumFailedPasswordsForWipe(null);
        }
        Log.e("MdfService", "DevicePolicyManager is null");
        return -2;
    }

    public final boolean isPasswordEnabled() {
        LockPatternUtils lockPatternUtils = this.mLockPatternUtils;
        if (lockPatternUtils != null) {
            int activePasswordQuality = lockPatternUtils.getActivePasswordQuality(UserHandle.myUserId());
            return activePasswordQuality >= 262144 && activePasswordQuality <= 393216;
        }
        Log.e("MdfService", "LockPatternUtils is null");
        return false;
    }

    public final int getPasswordQuality() {
        DevicePolicyManager devicePolicyManager = this.mDevicePolicyManager;
        if (devicePolicyManager != null) {
            return devicePolicyManager.getPasswordQuality(null);
        }
        Log.e("MdfService", "DevicePolicyManager is null");
        return -2;
    }

    public final boolean isRevocationCheckEnabled() {
        CertificatePolicy certificatePolicy = this.mCertificatePolicy;
        if (certificatePolicy != null) {
            return certificatePolicy.isRevocationCheckEnabled("*");
        }
        Log.e("MdfService", "CertificatePolicy is null");
        return false;
    }

    public final boolean isOcspCheckEnabled() {
        CertificatePolicy certificatePolicy = this.mCertificatePolicy;
        if (certificatePolicy != null) {
            return certificatePolicy.isOcspCheckEnabled("*");
        }
        Log.e("MdfService", "CertificatePolicy is null");
        return false;
    }

    public final boolean isExternalSDRemovable() {
        if (SemSdCardEncryption.isEncryptionFeatureEnabled()) {
            Log.i("MdfService", "SDCARD SLOT Support");
            return true;
        }
        Log.i("MdfService", "SDCARD SLOT None");
        return false;
    }

    public final boolean isFaceLockDisabled() {
        DevicePolicyManager devicePolicyManager = this.mDevicePolicyManager;
        if (devicePolicyManager != null) {
            boolean z = (devicePolicyManager.getKeyguardDisabledFeatures(null) & 128) != 0;
            PasswordPolicy passwordPolicy = this.mPasswordPolicy;
            if (passwordPolicy != null) {
                return z || (passwordPolicy.isBiometricAuthenticationEnabled(4) ^ true);
            }
            Log.e("MdfService", "PasswordPolicy is null");
            return false;
        }
        Log.e("MdfService", "DevicePolicyManager is null");
        return false;
    }

    public final boolean isSmartLockDisabled() {
        DevicePolicyManager devicePolicyManager = this.mDevicePolicyManager;
        if (devicePolicyManager != null) {
            return (devicePolicyManager.getKeyguardDisabledFeatures(null) & 16) != 0;
        }
        Log.e("MdfService", "DevicePolicyManager is null");
        return false;
    }

    public final void printVersion() {
        Log.i("MdfService", "v3.21.0");
    }

    public final void checkSystemUid() {
        if (Binder.getCallingUid() != 1000) {
            throw new SecurityException("Security Exception Occurred. Only SYSTEM can use the MdfService.");
        }
    }

    public void sendSamsungAnalyticsMultiLog() {
        int checkCCModeOnDevice = checkCCModeOnDevice();
        Log.d("MdfService", "current mode : " + Integer.toString(checkCCModeOnDevice));
        if (checkCCModeOnDevice != 1) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        arrayList.add("MCME");
        arrayList2.add("Enabled");
        arrayList.add("MPPA");
        arrayList2.add(Integer.toString(this.mMaximumFailedPasswordsForWipe));
        arrayList.add("MPPQ");
        arrayList2.add(Integer.toString(this.mPasswordQuality));
        arrayList.add("MPSL");
        arrayList2.add(Boolean.toString(this.mIsPasswordEnabled));
        arrayList.add("MPCR");
        arrayList2.add(Boolean.toString(this.mIsRevocationCheckEnabled));
        arrayList.add("MPCO");
        arrayList2.add(Boolean.toString(this.mIsOcspCheckEnabled));
        if (this.mIsExternalSDRemovable) {
            arrayList.add("MPSE");
            arrayList2.add(Boolean.toString(this.mIsSDEncrypted));
            arrayList.add("MPSB");
            arrayList2.add(Boolean.toString(!this.mIsSDEnabled));
        }
        arrayList.add("MPFL");
        arrayList2.add(Boolean.toString(!this.mIsFaceLockDisabled));
        arrayList.add("MPKS");
        arrayList2.add(Boolean.toString(true ^ this.mIsSmartLockDisabled));
        Bundle bundle = new Bundle();
        bundle.putString("tracking_id", "4M1-399-979749");
        bundle.putStringArray(LauncherConfigurationInternal.KEY_FEATURE_INT, (String[]) arrayList.toArray(new String[arrayList.size()]));
        bundle.putStringArray("extra", (String[]) arrayList2.toArray(new String[arrayList2.size()]));
        bundle.putString("type", "ev");
        Intent intent = new Intent();
        intent.setAction("com.sec.android.diagmonagent.intent.USE_MULTI_APP_FEATURE_SURVEY");
        intent.putExtras(bundle);
        intent.setPackage("com.sec.android.diagmonagent");
        Context context = this.mContext;
        if (context != null) {
            context.sendBroadcast(intent);
        }
    }
}
