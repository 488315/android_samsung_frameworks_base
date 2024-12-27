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
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.keyguard.SecurityLog;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.samsung.android.knox.EnterpriseDeviceManager;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class ResetDeviceUtils {
    public static ResetDeviceUtils sResetDeviceUtils;
    public final Context mContext;
    public final LockPatternUtils mLockPatternUtils;
    public StorageManager mStorageManager = null;
    public ProgressDialog mProgressDialog = null;

    public ResetDeviceUtils(Context context) {
        Log.d("ResetDeviceUtils", "ResetDeviceUtils()");
        this.mContext = context;
        this.mLockPatternUtils = new LockPatternUtils(context);
    }

    public final void removeSubUser(int i) {
        ((KeyguardUpdateMonitor) Dependency.sDependency.getDependencyInner(KeyguardUpdateMonitor.class)).clearFailedUnlockAttempts(true);
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
            boolean isFactoryResetAllowed = enterpriseDeviceManager.getRestrictionPolicy().isFactoryResetAllowed();
            SecurityLog.d("ResetDeviceUtils", "isFactoryResetAllowed = " + isFactoryResetAllowed);
            if (!isFactoryResetAllowed) {
                SecurityLog.d("ResetDeviceUtils", "Factory Reset is not allowed");
                return;
            }
            if (userManager != null && userManager.hasUserRestriction("no_factory_reset")) {
                SecurityLog.d("ResetDeviceUtils", "Factory Reset is not allowed DISALLOW_FACTORY_RESET");
                return;
            }
            boolean isExternalStorageForFailedPasswordsWipeExcluded = enterpriseDeviceManager.getPasswordPolicy().isExternalStorageForFailedPasswordsWipeExcluded();
            SecurityLog.d("ResetDeviceUtils", "wipeExcludeExternalStorage = " + isExternalStorageForFailedPasswordsWipeExcluded);
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
            }
            Log.d("ResetDeviceUtils", "findSDCard ( null )");
            storageVolume = null;
            if (this.mProgressDialog == null) {
                ProgressDialog progressDialog = new ProgressDialog(this.mContext, 5);
                this.mProgressDialog = progressDialog;
                progressDialog.setIndeterminate(true);
                this.mProgressDialog.setCancelable(false);
                this.mProgressDialog.setMessage(this.mContext.getString(R.string.keyguard_progress_erasing_all));
                this.mProgressDialog.getWindow().setType(2009);
            }
            this.mProgressDialog.show();
            if (storageVolume == null || isExternalStorageForFailedPasswordsWipeExcluded) {
                SecurityLog.d("ResetDeviceUtils", "wipeOut ( send ACTION_FACTORY_RESET )");
                intent = new Intent("android.intent.action.FACTORY_RESET");
                intent.addFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
                intent.putExtra("android.intent.extra.REASON", "ResetDeviceUtils_FACTORY_RESET, attemptsCount = " + i);
            } else {
                SecurityLog.d("ResetDeviceUtils", "wipeOut ACTION_FACTORY_RESET/EXTRA_WIPE_EXTERNAL_STORAGE=true)");
                intent = new Intent("android.intent.action.FACTORY_RESET");
                intent.addFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
                intent.putExtra("android.intent.extra.REASON", "ResetDeviceUtils_EXTERNAL_STORAGE, attemptsCount = " + i);
                intent.putExtra("android.intent.extra.WIPE_EXTERNAL_STORAGE", true);
            }
            intent.addFlags(16777216);
            this.mContext.sendBroadcast(intent);
        }
    }
}
