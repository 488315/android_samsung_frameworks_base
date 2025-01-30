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
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.keyguard.SecurityLog;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.samsung.android.knox.EnterpriseDeviceManager;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
        ((KeyguardUpdateMonitor) Dependency.get(KeyguardUpdateMonitor.class)).clearFailedUnlockAttempts(true);
        this.mLockPatternUtils.reportSuccessfulPasswordAttempt(KeyguardUpdateMonitor.getCurrentUser());
        try {
            ActivityManager.getService().switchUser(0);
            ((UserManager) this.mContext.getSystemService("user")).removeUser(i);
        } catch (RemoteException unused) {
            Log.e("ResetDeviceUtils", "KeyguardHostView - exception in removeSubuser");
        }
    }

    public final void wipeOut(int i, int i2) {
        StorageVolume storageVolume;
        Intent intent;
        SecurityLog.m143d("ResetDeviceUtils", "wipeOut() attemptsCount = " + i + " userType = " + i2);
        Context context = this.mContext;
        UserManager userManager = (UserManager) context.getSystemService("user");
        if (i2 != 1) {
            SecurityLog.m143d("ResetDeviceUtils", "wipeOut() removeSubUser userType : " + i2);
            int currentUser = KeyguardUpdateMonitor.getCurrentUser();
            if (i2 == 2) {
                removeSubUser(this.mLockPatternUtils.getDevicePolicyManager().getProfileWithMinimumFailedPasswordsForWipe(currentUser));
                return;
            } else {
                removeSubUser(currentUser);
                return;
            }
        }
        EnterpriseDeviceManager enterpriseDeviceManager = EnterpriseDeviceManager.getInstance(context);
        if (enterpriseDeviceManager != null) {
            boolean isFactoryResetAllowed = enterpriseDeviceManager.getRestrictionPolicy().isFactoryResetAllowed();
            SecurityLog.m143d("ResetDeviceUtils", "isFactoryResetAllowed = " + isFactoryResetAllowed);
            if (!isFactoryResetAllowed) {
                SecurityLog.m143d("ResetDeviceUtils", "Factory Reset is not allowed");
                return;
            }
            if (userManager != null && userManager.hasUserRestriction("no_factory_reset")) {
                SecurityLog.m143d("ResetDeviceUtils", "Factory Reset is not allowed DISALLOW_FACTORY_RESET");
                return;
            }
            boolean isExternalStorageForFailedPasswordsWipeExcluded = enterpriseDeviceManager.getPasswordPolicy().isExternalStorageForFailedPasswordsWipeExcluded();
            SecurityLog.m143d("ResetDeviceUtils", "wipeExcludeExternalStorage = " + isExternalStorageForFailedPasswordsWipeExcluded);
            Log.d("ResetDeviceUtils", "findSDCard ()");
            if (this.mStorageManager == null) {
                this.mStorageManager = (StorageManager) context.getSystemService("storage");
            }
            StorageManager storageManager = this.mStorageManager;
            if (storageManager != null) {
                StorageVolume[] volumeList = storageManager.getVolumeList();
                int length = volumeList.length;
                for (int i3 = 0; i3 < length; i3++) {
                    if (volumeList[i3].isRemovable()) {
                        Log.d("ResetDeviceUtils", "findSDCard ( storageVolumes = " + volumeList[i3] + " )");
                        storageVolume = volumeList[i3];
                        break;
                    }
                }
            }
            Log.d("ResetDeviceUtils", "findSDCard ( null )");
            storageVolume = null;
            if (this.mProgressDialog == null) {
                ProgressDialog progressDialog = new ProgressDialog(context, 5);
                this.mProgressDialog = progressDialog;
                progressDialog.setIndeterminate(true);
                this.mProgressDialog.setCancelable(false);
                this.mProgressDialog.setMessage(context.getString(R.string.keyguard_progress_erasing_all));
                this.mProgressDialog.getWindow().setType(2009);
            }
            this.mProgressDialog.show();
            if (storageVolume != null && !isExternalStorageForFailedPasswordsWipeExcluded) {
                SecurityLog.m143d("ResetDeviceUtils", "wipeOut ACTION_FACTORY_RESET/EXTRA_WIPE_EXTERNAL_STORAGE=true)");
                intent = new Intent("android.intent.action.FACTORY_RESET");
                intent.addFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
                intent.putExtra("android.intent.extra.REASON", "ResetDeviceUtils_EXTERNAL_STORAGE, attemptsCount = " + i);
                intent.putExtra("android.intent.extra.WIPE_EXTERNAL_STORAGE", true);
            } else if (LsRune.SECURITY_FACTORY_RESET_WITHOUT_UI) {
                SecurityLog.m143d("ResetDeviceUtils", "wipeOut ( send SEC_FACTORY_RESET_WITHOUT_FACTORY_UI )");
                intent = new Intent("com.samsung.intent.action.SEC_FACTORY_RESET_WITHOUT_FACTORY_UI");
                intent.setFlags(16777216);
                intent.putExtra("android.intent.extra.REASON", "ResetDeviceUtils_WITHOUT_FACTORY_UI, attemptsCount = " + i);
            } else {
                SecurityLog.m143d("ResetDeviceUtils", "wipeOut ( send ACTION_FACTORY_RESET )");
                intent = new Intent("android.intent.action.FACTORY_RESET");
                intent.addFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
                intent.putExtra("android.intent.extra.REASON", "ResetDeviceUtils_FACTORY_RESET, attemptsCount = " + i);
            }
            intent.addFlags(16777216);
            context.sendBroadcast(intent);
        }
    }
}
