package com.android.keyguard;

import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.RemoteException;
import android.os.UserManager;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.util.Log;
import com.android.internal.widget.LockPatternUtils;
import com.android.systemui.R;
import com.android.systemui.keyguard.SecurityLog;
import com.samsung.android.knox.EnterpriseDeviceManager;

/* loaded from: classes.dex */
public class ResetDeviceUtils {
    public final Context mContext;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final LockPatternUtils mLockPatternUtils;
    public StorageManager mStorageManager = null;
    public ProgressDialog mProgressDialog = null;

    public ResetDeviceUtils(Context context, LockPatternUtils lockPatternUtils, KeyguardUpdateMonitor keyguardUpdateMonitor) {
        Log.d("ResetDeviceUtils", "ResetDeviceUtils()");
        this.mContext = context;
        this.mLockPatternUtils = lockPatternUtils;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
    }

    public final void removeSubUser(int i) {
        this.mKeyguardUpdateMonitor.clearFailedUnlockAttempts(true);
        this.mLockPatternUtils.reportSuccessfulPasswordAttempt(i);
        try {
            ActivityManager.getService().switchUser(0);
            ((UserManager) this.mContext.getSystemService("user")).removeUser(i);
        } catch (RemoteException unused) {
            Log.e("ResetDeviceUtils", "KeyguardHostView - exception in removeSubuser");
        }
    }

    public final void wipeOut(int i, int i2, int i3) {
        StorageVolume storageVolume;
        Intent intent;
        SecurityLog.d("ResetDeviceUtils", "wipeOut() attemptsCount = " + i + " userType = " + i3);
        UserManager userManager = (UserManager) this.mContext.getSystemService("user");
        if (i3 != 1) {
            SecurityLog.d("ResetDeviceUtils", "wipeOut() removeSubUser userType : " + i3);
            if (i3 == 2) {
                removeSubUser(this.mLockPatternUtils.getDevicePolicyManager().getProfileWithMinimumFailedPasswordsForWipe(i2));
                return;
            } else {
                removeSubUser(i2);
                return;
            }
        }
        EnterpriseDeviceManager enterpriseDeviceManager = EnterpriseDeviceManager.getInstance(this.mContext);
        if (enterpriseDeviceManager != null) {
            boolean zIsFactoryResetAllowed = enterpriseDeviceManager.getRestrictionPolicy().isFactoryResetAllowed();
            SecurityLog.d("ResetDeviceUtils", "isFactoryResetAllowed = " + zIsFactoryResetAllowed);
            if (!zIsFactoryResetAllowed) {
                SecurityLog.d("ResetDeviceUtils", "Factory Reset is not allowed");
                return;
            }
            if (userManager != null && userManager.hasUserRestriction("no_factory_reset")) {
                SecurityLog.d("ResetDeviceUtils", "Factory Reset is not allowed DISALLOW_FACTORY_RESET");
                return;
            }
            boolean zIsExternalStorageForFailedPasswordsWipeExcluded = enterpriseDeviceManager.getPasswordPolicy().isExternalStorageForFailedPasswordsWipeExcluded();
            SecurityLog.d("ResetDeviceUtils", "wipeExcludeExternalStorage = " + zIsExternalStorageForFailedPasswordsWipeExcluded);
            Log.d("ResetDeviceUtils", "findSDCard ()");
            if (this.mStorageManager == null) {
                this.mStorageManager = (StorageManager) this.mContext.getSystemService("storage");
            }
            StorageManager storageManager = this.mStorageManager;
            if (storageManager != null) {
                StorageVolume[] volumeList = storageManager.getVolumeList();
                int length = volumeList.length;
                for (int i4 = 0; i4 < length; i4++) {
                    if (volumeList[i4].isRemovable()) {
                        Log.d("ResetDeviceUtils", "findSDCard ( storageVolumes = " + volumeList[i4] + " )");
                        storageVolume = volumeList[i4];
                        break;
                    }
                }
                Log.d("ResetDeviceUtils", "findSDCard ( null )");
                storageVolume = null;
            } else {
                Log.d("ResetDeviceUtils", "findSDCard ( null )");
                storageVolume = null;
            }
            if (this.mProgressDialog == null) {
                ProgressDialog progressDialog = new ProgressDialog(this.mContext, 5);
                this.mProgressDialog = progressDialog;
                progressDialog.setIndeterminate(true);
                this.mProgressDialog.setCancelable(false);
                this.mProgressDialog.setMessage(this.mContext.getString(R.string.keyguard_progress_erasing_all));
                this.mProgressDialog.getWindow().setType(2009);
            }
            this.mProgressDialog.show();
            if (storageVolume == null || zIsExternalStorageForFailedPasswordsWipeExcluded) {
                SecurityLog.d("ResetDeviceUtils", "wipeOut ( send ACTION_FACTORY_RESET )");
                intent = new Intent("android.intent.action.FACTORY_RESET");
                intent.addFlags(268435456);
                intent.putExtra("android.intent.extra.REASON", "ResetDeviceUtils_FACTORY_RESET, attemptsCount = " + i);
            } else {
                SecurityLog.d("ResetDeviceUtils", "wipeOut ACTION_FACTORY_RESET/EXTRA_WIPE_EXTERNAL_STORAGE=true)");
                intent = new Intent("android.intent.action.FACTORY_RESET");
                intent.addFlags(268435456);
                intent.putExtra("android.intent.extra.REASON", "ResetDeviceUtils_EXTERNAL_STORAGE, attemptsCount = " + i);
                intent.putExtra("android.intent.extra.WIPE_EXTERNAL_STORAGE", true);
            }
            intent.addFlags(16777216);
            this.mContext.sendBroadcast(intent);
        }
    }
}
