package com.samsung.android.security.mdf.MdfService;

import android.R;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.os.Binder;
import android.os.Process;
import android.os.SystemProperties;
import android.os.UserManager;
import android.sec.enterprise.auditlog.AuditLog;
import android.util.Log;

import com.android.internal.widget.LockPatternUtils;
import com.android.server.input.KeyboardMetricsCollector;

import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.EnterpriseKnoxManager;
import com.samsung.android.knox.devicesecurity.PasswordPolicy;
import com.samsung.android.knox.keystore.CertificatePolicy;
import com.samsung.android.knox.restriction.RestrictionPolicy;
import com.samsung.android.security.mdf.MdfUtils;

public final class MdfPolicy {
    public static MdfPolicy sInstance;
    public final CertificatePolicy mCertificatePolicy;
    public final Context mContext;
    public final DevicePolicyManager mDevicePolicyManager;
    public boolean mIsRevocationCheckEnabled;
    public boolean mIsSDEnabled;
    public final LockPatternUtils mLockPatternUtils;
    public final MdfUtils mMdfUtils;
    public final PasswordPolicy mPasswordPolicy;
    public final RestrictionPolicy mRestrictionPolicy;
    public final UserManager mUserManager;

    public MdfPolicy(Context context) {
        Log.i("MdfService", "v3.21.0");
        this.mContext = context;
        this.mDevicePolicyManager = (DevicePolicyManager) context.getSystemService("device_policy");
        this.mLockPatternUtils = new LockPatternUtils(context.getApplicationContext());
        this.mCertificatePolicy = EnterpriseKnoxManager.getInstance(context).getCertificatePolicy();
        this.mPasswordPolicy = EnterpriseDeviceManager.getInstance(context).getPasswordPolicy();
        this.mRestrictionPolicy =
                EnterpriseDeviceManager.getInstance(context).getRestrictionPolicy();
        this.mUserManager = (UserManager) context.getSystemService("user");
        this.mMdfUtils = new MdfUtils();
    }

    public static synchronized MdfPolicy getInstance(Context context) {
        MdfPolicy mdfPolicy;
        synchronized (MdfPolicy.class) {
            try {
                if (sInstance == null) {
                    sInstance = new MdfPolicy(context);
                }
                mdfPolicy = sInstance;
            } catch (Throwable th) {
                throw th;
            }
        }
        return mdfPolicy;
    }

    public static void logForAuditAndLogcat(int i, int i2, String str) {
        AuditLog.log(1, i, false, Process.myPid(), "MdfStatus", str);
        Log.println(i2, "MdfService", str);
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x0086  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0095  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00a0  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00bd  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00cc  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x010d  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0134  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0145  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0154  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x015a  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x014f  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x013a  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x012e  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0104  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x00c4  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x00a6  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x009a  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x008b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int checkDevicePolicy() {
        /*
            Method dump skipped, instructions count: 356
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.security.mdf.MdfService.MdfPolicy.checkDevicePolicy():int");
    }

    public final int enableCCMode(boolean z) {
        SystemProperties.set("security.mdf.result", KeyboardMetricsCollector.DEFAULT_LANGUAGE_TAG);
        if (!"Enabled".equals(SystemProperties.get("ro.security.mdf.ux"))) {
            Log.e("MdfService", "This model does not support CC mode.");
            SystemProperties.set("security.mdf.result", Integer.toString(-16));
            return -16;
        }
        int cCModeFlag = this.mMdfUtils.getCCModeFlag();
        int i = cCModeFlag == 1 ? 1 : cCModeFlag == 4 ? 4 : cCModeFlag == 8 ? 8 : 16;
        Log.d("MdfService", "the current mode : " + Integer.toString(i));
        int i2 = 0;
        if (!z) {
            if (i == 1) {
                int cCMode = setCCMode(4);
                if (cCMode != 0) {
                    MdfPolicy$$ExternalSyntheticOutline0.m(
                            cCMode, new StringBuilder("Failed. setCCMode. res = "), "MdfService");
                    return cCMode;
                }
                setNotification(
                        this.mContext.getResources().getString(R.string.permlab_hideOverlayWindows),
                        this.mContext
                                .getResources()
                                .getString(R.string.permlab_foregroundServiceHealth));
                SystemProperties.set("security.mdf.result", Integer.toString(cCMode));
                return cCMode;
            }
            if (i == 4) {
                Log.e("MdfService", "CCMode is already ready.");
                return 0;
            }
            if (i == 8) {
                int cCMode2 = setCCMode(8);
                if (cCMode2 != 0) {
                    MdfPolicy$$ExternalSyntheticOutline0.m(
                            cCMode2, new StringBuilder("Failed. setCCMode. res = "), "MdfService");
                    return cCMode2;
                }
                setNotification(
                        this.mContext
                                .getResources()
                                .getString(R.string.permlab_highSamplingRateSensors),
                        this.mContext.getResources().getString(R.string.permlab_handoverStatus));
                SystemProperties.set("security.mdf.result", Integer.toString(-11));
                return -11;
            }
            if (i == 16) {
                int cCMode3 = setCCMode(16);
                if (cCMode3 != 0) {
                    MdfPolicy$$ExternalSyntheticOutline0.m(
                            cCMode3, new StringBuilder("Failed. setCCMode. res = "), "MdfService");
                }
                SystemProperties.set("security.mdf.result", Integer.toString(-12));
                return -12;
            }
            int cCMode4 = setCCMode(16);
            if (cCMode4 != 0) {
                MdfPolicy$$ExternalSyntheticOutline0.m(
                        cCMode4, new StringBuilder("Failed. setCCMode. res = "), "MdfService");
            }
            SystemProperties.set("security.mdf.result", Integer.toString(-13));
            return -13;
        }
        if (i == 1) {
            Log.e("MdfService", "CCMode is already enabled.");
            return 0;
        }
        if (i != 4) {
            if (i == 16) {
                int cCMode5 = setCCMode(16);
                if (cCMode5 != 0) {
                    MdfPolicy$$ExternalSyntheticOutline0.m(
                            cCMode5, new StringBuilder("Failed. setCCMode. res = "), "MdfService");
                }
                SystemProperties.set("security.mdf.result", Integer.toString(-12));
                return -12;
            }
            if (i == 8) {
                int cCMode6 = setCCMode(8);
                if (cCMode6 != 0) {
                    MdfPolicy$$ExternalSyntheticOutline0.m(
                            cCMode6, new StringBuilder("Failed. setCCMode. res = "), "MdfService");
                }
                SystemProperties.set("security.mdf.result", Integer.toString(-11));
                return -11;
            }
            int cCMode7 = setCCMode(16);
            if (cCMode7 != 0) {
                MdfPolicy$$ExternalSyntheticOutline0.m(
                        cCMode7, new StringBuilder("Failed. setCCMode. res = "), "MdfService");
            }
            SystemProperties.set("security.mdf.result", Integer.toString(-13));
            return -13;
        }
        int checkDevicePolicy = checkDevicePolicy();
        SystemProperties.set("security.mdf.result", Integer.toString(checkDevicePolicy));
        if (checkDevicePolicy != 0) {
            MdfPolicy$$ExternalSyntheticOutline0.m(
                    checkDevicePolicy,
                    new StringBuilder("Prerequisite policies have yet to set. res = "),
                    "MdfService");
        }
        if (this.mMdfUtils.FIPS_Openssl_SelfTest() != 0) {
            logForAuditAndLogcat(1, 6, "FIPS self-test : FAILED");
            i2 = -20;
        } else {
            logForAuditAndLogcat(5, 4, "FIPS self-test : OK");
        }
        SystemProperties.set("security.mdf.result", Integer.toString(i2));
        if (i2 == 0) {
            int cCMode8 = setCCMode(1);
            if (cCMode8 != 0) {
                MdfPolicy$$ExternalSyntheticOutline0.m(
                        cCMode8, new StringBuilder("Failed. setCCMode. res = "), "MdfService");
                return cCMode8;
            }
            setNotification(
                    this.mContext.getResources().getString(R.string.permlab_hideOverlayWindows),
                    this.mContext
                            .getResources()
                            .getString(R.string.permlab_foregroundServiceDataSync));
            return cCMode8;
        }
        Log.e("MdfService", "Failed. check fips self test. res = " + Integer.toString(i2));
        int cCMode9 = setCCMode(8);
        if (cCMode9 != 0) {
            MdfPolicy$$ExternalSyntheticOutline0.m(
                    cCMode9, new StringBuilder("Failed. setCCMode. res = "), "MdfService");
            return cCMode9;
        }
        setNotification(
                this.mContext.getResources().getString(R.string.permlab_highSamplingRateSensors),
                this.mContext.getResources().getString(R.string.permlab_handoverStatus));
        return cCMode9;
    }

    public final int initCCMode() {
        int i;
        int i2;
        try {
            if (Binder.getCallingUid() != 1000) {
                throw new SecurityException(
                        "Security Exception Occurred. Only SYSTEM can use the MdfService.");
            }
            int cCModeFlag = this.mMdfUtils.getCCModeFlag();
            int i3 = cCModeFlag == 1 ? 1 : cCModeFlag == 4 ? 4 : cCModeFlag == 8 ? 8 : 16;
            Log.d("MdfService", "current mode : " + Integer.toString(i3));
            boolean z = false;
            if (i3 == 1) {
                if (this.mMdfUtils.FIPS_Openssl_SelfTest() != 0) {
                    logForAuditAndLogcat(1, 6, "FIPS self-test : FAILED");
                    i2 = -20;
                } else {
                    logForAuditAndLogcat(5, 4, "FIPS self-test : OK");
                    i2 = 0;
                }
                if (i2 != 0) {
                    MdfPolicy$$ExternalSyntheticOutline0.m(
                            i2,
                            new StringBuilder("Failed. check fips self test. res = "),
                            "MdfService");
                    setNotification(
                            this.mContext
                                    .getResources()
                                    .getString(R.string.permlab_highSamplingRateSensors),
                            this.mContext
                                    .getResources()
                                    .getString(R.string.permlab_handoverStatus));
                    i = setCCMode(8);
                    if (i != 0) {
                        MdfPolicy$$ExternalSyntheticOutline0.m(
                                i, new StringBuilder("Failed. setCCMode. res = "), "MdfService");
                    }
                } else {
                    i = checkDevicePolicy();
                    if (i != 0) {
                        MdfPolicy$$ExternalSyntheticOutline0.m(
                                i,
                                new StringBuilder("Prerequisite policies have yet to set. res = "),
                                "MdfService");
                    }
                }
            } else if (i3 == 4) {
                i = setCCMode(4);
                if (i != 0) {
                    MdfPolicy$$ExternalSyntheticOutline0.m(
                            i, new StringBuilder("Failed. setCCMode. res = "), "MdfService");
                }
            } else if (i3 == 8) {
                int cCMode = setCCMode(8);
                if (cCMode != 0) {
                    MdfPolicy$$ExternalSyntheticOutline0.m(
                            cCMode, new StringBuilder("Failed. setCCMode. res = "), "MdfService");
                }
                i = -11;
            } else if (i3 == 16) {
                int cCMode2 = setCCMode(16);
                if (cCMode2 != 0) {
                    MdfPolicy$$ExternalSyntheticOutline0.m(
                            cCMode2, new StringBuilder("Failed. setCCMode. res = "), "MdfService");
                }
                i = -12;
            } else {
                int cCMode3 = setCCMode(16);
                if (cCMode3 != 0) {
                    MdfPolicy$$ExternalSyntheticOutline0.m(
                            cCMode3, new StringBuilder("Failed. setCCMode. res = "), "MdfService");
                }
                i = -13;
            }
            StringBuilder sb = new StringBuilder("AE CommonCriteriaMode is ");
            DevicePolicyManager devicePolicyManager = this.mDevicePolicyManager;
            if (devicePolicyManager != null) {
                z = devicePolicyManager.isCommonCriteriaModeEnabled(null);
            } else {
                Log.e("MdfService", "Failed isCommonCriteriaMode(). mDevicePolicyManager is null");
            }
            sb.append(Boolean.toString(z));
            Log.i("MdfService", sb.toString());
            return i;
        } catch (SecurityException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:13:0x003a  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0089  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x00eb A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:22:0x00ec  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x009e  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00ca  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0060  */
    /* JADX WARN: Type inference failed for: r4v1, types: [int] */
    /* JADX WARN: Type inference failed for: r4v4 */
    /* JADX WARN: Type inference failed for: r4v5 */
    /* JADX WARN: Type inference failed for: r4v6 */
    /* JADX WARN: Type inference failed for: r4v8 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int setCCMode(int r15) {
        /*
            Method dump skipped, instructions count: 301
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.security.mdf.MdfService.MdfPolicy.setCCMode(int):int");
    }

    public final void setNotification(String str, String str2) {
        Log.d("MdfService", "Notice for applying security policy");
        NotificationManager notificationManager =
                (NotificationManager) this.mContext.getSystemService("notification");
        if (notificationManager == null) {
            Log.d("MdfService", "NotificationManager is null");
            return;
        }
        NotificationChannel notificationChannel =
                new NotificationChannel("mdf_channel_id", "Mdf Channel", 4);
        notificationChannel.enableVibration(true);
        notificationChannel.setVibrationPattern(new long[] {0, 500});
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
}
