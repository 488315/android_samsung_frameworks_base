package com.android.systemui.knox;

import android.database.Cursor;
import android.net.Uri;
import android.os.UserManager;
import android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback;
import android.util.Log;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.util.DeviceState;
import java.io.PrintWriter;
import java.util.HashMap;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class EdmMonitor extends ISystemUIAdapterCallback.Stub implements Dumpable {
    public final KnoxStateMonitorImpl knoxStateMonitor;
    public String[] mLockedIccIdList;
    public final UserManager mUserManager;
    public boolean mIsStatusBarHidden = false;
    public boolean mIsNavigationBarHidden = false;
    public boolean mIsMDMKioskMode = false;
    public boolean mWipeExcludeExternalStorage = false;
    public boolean mIsDeviceDisableForMaxFailedAttempt = false;
    public boolean mIsProfileDisableForMaxFailedAttempt = false;
    public boolean mIsLockscreenInvisibleOverlayConfigured = false;
    public boolean mIsLockscreenWallpaperConfigured = false;
    public boolean mSettingsChangesAllowed = true;
    public boolean mStatusBarExpandAllowed = true;
    public boolean mAirplaneModeAllowed = true;
    public boolean mCellularDataAllowed = true;
    public final boolean mNFCAllowed = true;
    public boolean mWifiTetheringAllowed = true;
    public boolean mBluetoothAllowed = true;
    public boolean mNFCStateChangeAllowed = true;
    public boolean mRoamingAllowed = true;
    public boolean mWifiStateChangeAllowed = true;
    public boolean mWifiAllowed = true;
    public boolean mGPSStateChangeAllowed = true;
    public boolean mMultiFactorAuthEnabled = false;
    public boolean mPasswordVisibilityEnabled = true;
    public boolean mIsCameraDisabledByMdm = false;
    public boolean mIsFaceRecognitionAllowedEvenCameraBlocked = false;
    public boolean mEnableAdminLock = false;
    public boolean mLicenseExpired = false;
    public int mPwdChangeRequest = 0;
    public int mLockDelay = -1;
    public int mMaxNumFailedAttemptForDisable = 0;
    public int mMaxNumFailedAttemptForProfileDisable = 0;
    public String mPkgNameForMaxAttemptDisable = null;
    public final HashMap mLocationProviderAllowed = new HashMap();

    public EdmMonitor(KnoxStateMonitorImpl knoxStateMonitorImpl) {
        this.knoxStateMonitor = knoxStateMonitorImpl;
        if (DeviceState.isTesting()) {
            return;
        }
        this.mUserManager = (UserManager) knoxStateMonitorImpl.mContext.getSystemService("user");
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("EdmMonitor state:");
        printWriter.print("-mIsStatusBarHidden=");
        printWriter.println(this.mIsStatusBarHidden);
        printWriter.print("-mIsNavigationBarHidden=");
        printWriter.println(this.mIsNavigationBarHidden);
        printWriter.print("-mIsMDMKioskMode=");
        printWriter.println(this.mIsMDMKioskMode);
        printWriter.print("-mWipeExcludeExternalStorage=");
        printWriter.println(this.mWipeExcludeExternalStorage);
        printWriter.print("-mLockDelay=");
        printWriter.println(this.mLockDelay);
        printWriter.print("-mMaxNumFailedAttemptForDisable=");
        printWriter.println(this.mMaxNumFailedAttemptForDisable);
        printWriter.print("-mPkgNameForMaxFailedAttemptDisable=");
        printWriter.println(this.mPkgNameForMaxAttemptDisable);
        printWriter.print("-mIsDeviceDisableForMaxFailedAttempt=");
        printWriter.println(this.mIsDeviceDisableForMaxFailedAttempt);
        printWriter.print("-mPwdChangeRequest=");
        printWriter.println(this.mPwdChangeRequest);
        printWriter.print("-mSettingsChangesAllowed=");
        printWriter.println(this.mSettingsChangesAllowed);
        printWriter.print("-mStatusBarExpandAllowed=");
        printWriter.println(this.mStatusBarExpandAllowed);
        printWriter.print("-mAirplaneModeAllowed=");
        printWriter.println(this.mAirplaneModeAllowed);
        printWriter.print("-mCellularDataAllowed=");
        printWriter.println(this.mCellularDataAllowed);
        printWriter.print("-mNFCAllowed=");
        printWriter.println(this.mNFCAllowed);
        printWriter.print("-mWifiTetheringAllowed=");
        printWriter.println(this.mWifiTetheringAllowed);
        printWriter.print("-mBluetoothAllowed=");
        printWriter.println(this.mBluetoothAllowed);
        printWriter.print("-mNFCStateChangeAllowed=");
        printWriter.println(this.mNFCStateChangeAllowed);
        printWriter.print("-mRoamingAllowed=");
        printWriter.println(this.mRoamingAllowed);
        printWriter.print("-mWifiStateChangeAllowed=");
        printWriter.println(this.mWifiStateChangeAllowed);
        printWriter.print("-mWifiAllowed=");
        printWriter.println(this.mWifiAllowed);
        printWriter.print("-mLocationProviderAllowed=");
        printWriter.println(this.mLocationProviderAllowed);
        printWriter.print("-mGPSStateChangeAllowed=");
        printWriter.println(this.mGPSStateChangeAllowed);
        printWriter.print("-mIsLockscreenInvisibleOverlayConfigured=");
        printWriter.println(this.mIsLockscreenInvisibleOverlayConfigured);
        printWriter.print("-mIsLockscreenWallpaperConfigured=");
        printWriter.println(this.mIsLockscreenWallpaperConfigured);
        printWriter.print("-mMultiFactorAuthEnabled=");
        printWriter.println(this.mMultiFactorAuthEnabled);
        printWriter.print("-mIsCameraDisabledByMdm=");
        printWriter.println(this.mIsCameraDisabledByMdm);
        printWriter.print("-mIsFaceRecognitionAllowedEvenCameraBlocked=");
        printWriter.println(this.mIsFaceRecognitionAllowedEvenCameraBlocked);
        if (this.mLockedIccIdList != null) {
            printWriter.print("-mLockedIccIdList=");
            for (String str : this.mLockedIccIdList) {
                printWriter.print(" " + str);
            }
            printWriter.println("");
        }
    }

    public final void excludeExternalStorageForFailedPasswordsWipe(boolean z) {
        this.mWipeExcludeExternalStorage = z;
    }

    public final int getCurrentFailedAttempts() {
        Cursor query = this.knoxStateMonitor.mContext.getContentResolver().query(Uri.parse("content://com.sec.knox.provider/PasswordPolicy2"), null, "getCurrentFailedPasswordAttempts", null, null);
        if (query != null) {
            try {
                query.moveToFirst();
                return query.getInt(query.getColumnIndex("getCurrentFailedPasswordAttempts"));
            } catch (Exception unused) {
            } finally {
                query.close();
            }
        }
        return 0;
    }

    public final void setAdminLock(boolean z, boolean z2) {
        EmergencyButtonController$$ExternalSyntheticOutline0.m("setAdminLock enabled:", "  licenseExpired:", "EdmMonitor", z, z2);
        if (this.mEnableAdminLock == z && this.mLicenseExpired == z2) {
            return;
        }
        this.mEnableAdminLock = z;
        this.mLicenseExpired = z2;
        this.knoxStateMonitor.mHandler.removeMessages(5026);
        this.knoxStateMonitor.mHandler.sendEmptyMessage(5026);
    }

    public final void setAirplaneModeAllowed(boolean z) {
        this.mAirplaneModeAllowed = z;
    }

    public final void setBluetoothAllowed(boolean z) {
        this.mBluetoothAllowed = z;
    }

    public final void setCameraAllowed(boolean z) {
        Log.d("EdmMonitor", "setCameraAllowed( " + z + " )");
        this.mIsCameraDisabledByMdm = z ^ true;
    }

    public final void setCellularDataAllowed(boolean z) {
        this.mCellularDataAllowed = z;
    }

    public final void setFaceRecognitionEvenCameraBlockedAllowed(boolean z) {
        Log.d("EdmMonitor", "setFaceRecognitionEvenCameraBlockedAllowed( " + z + " )");
        this.mIsFaceRecognitionAllowedEvenCameraBlocked = z;
    }

    public final void setGPSStateChangeAllowed(boolean z) {
        this.mGPSStateChangeAllowed = z;
    }

    public final void setKioskModeEnabled(boolean z) {
        this.mIsMDMKioskMode = z;
    }

    public final void setLocationProviderAllowed(String str, boolean z) {
        this.mLocationProviderAllowed.put(str, Boolean.valueOf(z));
    }

    public final void setLockedIccIds(String[] strArr) {
        this.mLockedIccIdList = strArr;
    }

    public final void setLockscreenInvisibleOverlay(boolean z) {
        this.mIsLockscreenInvisibleOverlayConfigured = z;
    }

    public final void setLockscreenWallpaper(boolean z) {
        if (this.mIsLockscreenWallpaperConfigured != z) {
            this.mIsLockscreenWallpaperConfigured = z;
            this.knoxStateMonitor.mHandler.removeMessages(5024);
            this.knoxStateMonitor.mHandler.sendEmptyMessage(5024);
        } else if (z) {
            this.knoxStateMonitor.mHandler.removeMessages(5025);
            this.knoxStateMonitor.mHandler.sendEmptyMessage(5025);
        }
    }

    public final void setMaximumFailedPasswordsForDisable(int i, String str) {
        this.mMaxNumFailedAttemptForDisable = i;
        this.mPkgNameForMaxAttemptDisable = str;
        updateFailedUnlockAttemptForDeviceDisabled();
    }

    public final void setMaximumFailedPasswordsForProfileDisable(int i) {
        this.mMaxNumFailedAttemptForProfileDisable = i;
        updateFailedUnlockAttemptForProfileDisabled();
    }

    public final void setMultifactorAuthEnabled(boolean z) {
        Log.d("EdmMonitor", "setMultifactorAuthEnabled( " + z + " )");
        this.mMultiFactorAuthEnabled = z;
    }

    public final void setNFCStateChangeAllowed(boolean z) {
        this.mNFCStateChangeAllowed = z;
    }

    public final void setNavigationBarHidden(boolean z) {
        if (this.mIsNavigationBarHidden != z) {
            this.mIsNavigationBarHidden = z;
            this.knoxStateMonitor.mHandler.removeMessages(5020);
            this.knoxStateMonitor.mHandler.sendEmptyMessage(5020);
        }
    }

    public final void setPasswordLockDelay(int i) {
        this.mLockDelay = i;
    }

    public final void setPasswordVisibilityEnabled(boolean z) {
        Log.d("EdmMonitor", "setPasswordVisibilityEnabled( " + z + " )");
        this.mPasswordVisibilityEnabled = z;
    }

    public final void setPwdChangeRequested(int i) {
        this.mPwdChangeRequest = i;
    }

    public final void setRoamingAllowed(boolean z) {
        this.mRoamingAllowed = z;
    }

    public final void setSettingsChangeAllowed(boolean z) {
        this.mSettingsChangesAllowed = z;
    }

    public final void setStatusBarExpansionAllowed(boolean z) {
        this.mStatusBarExpandAllowed = z;
    }

    public final void setStatusBarHidden(boolean z) {
        if (this.mIsStatusBarHidden != z) {
            this.mIsStatusBarHidden = z;
            this.knoxStateMonitor.mHandler.removeMessages(5019);
            this.knoxStateMonitor.mHandler.sendEmptyMessage(5019);
        }
    }

    public final void setWifiAllowed(boolean z) {
        this.mWifiAllowed = z;
    }

    public final void setWifiStateChangeAllowed(boolean z) {
        this.mWifiStateChangeAllowed = z;
    }

    public final void setWifiTetheringAllowed(boolean z) {
        this.mWifiTetheringAllowed = z;
    }

    public final void updateFailedUnlockAttemptForDeviceDisabled() {
        if (!(this.mMaxNumFailedAttemptForDisable > 0)) {
            Log.d("EdmMonitor", "MDM is not enabled...");
            return;
        }
        int currentFailedAttempts = getCurrentFailedAttempts();
        this.mIsDeviceDisableForMaxFailedAttempt = currentFailedAttempts >= this.mMaxNumFailedAttemptForDisable;
        Log.d("EdmMonitor", "reportFailedUnlockAttempt : maxNumFailedAttemptForDisable=" + this.mMaxNumFailedAttemptForDisable + " , curNumFailedAttempts=" + currentFailedAttempts);
        ActionBarContextView$$ExternalSyntheticOutline0.m(new StringBuilder("isDeviceDisabledForMaxFailedAttempt="), this.mIsDeviceDisableForMaxFailedAttempt, "EdmMonitor");
        if (this.mIsDeviceDisableForMaxFailedAttempt) {
            this.knoxStateMonitor.mHandler.removeMessages(5022);
            this.knoxStateMonitor.mHandler.sendEmptyMessage(5022);
        }
    }

    public final void updateFailedUnlockAttemptForProfileDisabled() {
        if (this.mMaxNumFailedAttemptForProfileDisable <= 0) {
            Log.d("EdmMonitor", "MDM is not enabled...");
            return;
        }
        int currentFailedAttempts = getCurrentFailedAttempts();
        this.mIsProfileDisableForMaxFailedAttempt = currentFailedAttempts >= this.mMaxNumFailedAttemptForProfileDisable;
        Log.d("EdmMonitor", "reportFailedUnlockAttempt : maxNumFailedAttemptForProfileDisable=" + this.mMaxNumFailedAttemptForProfileDisable + " , curNumFailedAttempts=" + currentFailedAttempts);
        ActionBarContextView$$ExternalSyntheticOutline0.m(new StringBuilder("isProfileDisableForMaxFailedAttempt="), this.mIsProfileDisableForMaxFailedAttempt, "EdmMonitor");
        if (this.mIsProfileDisableForMaxFailedAttempt) {
            this.knoxStateMonitor.mHandler.removeMessages(5029);
            this.knoxStateMonitor.mHandler.sendEmptyMessage(5029);
        }
    }

    public final void setApplicationNameControlEnabled(boolean z) {
    }
}
