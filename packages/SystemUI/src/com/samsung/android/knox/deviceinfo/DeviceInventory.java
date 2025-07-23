package com.samsung.android.knox.deviceinfo;

import android.content.Context;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.StatFs;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.util.Log;
import com.android.keyguard.EmergencyButton$$ExternalSyntheticOutline0;
import com.samsung.android.knox.AccessController;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.ExternalDependencyInjector;
import com.samsung.android.knox.IMiscPolicy;
import com.samsung.android.knox.deviceinfo.IDeviceInfo;
import com.samsung.android.knox.license.EnterpriseLicenseManager;
import java.util.Collections;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class DeviceInventory {
    public static final String ACTION_SIM_CARD_CHANGED = "com.samsung.android.knox.intent.action.SIM_CARD_CHANGED";
    public static final String EXTRA_SIM_CHANGE_INFO = "com.samsung.android.knox.intent.extra.SIM_CHANGE_INFO";
    public static String TAG = "DeviceInventory";
    public final Context mContext;
    public ContextInfo mContextInfo;
    public ExternalDependencyInjector mExternalDependencyInjector;
    public IMiscPolicy mMiscService;
    public IDeviceInfo mService;

    public DeviceInventory(ContextInfo contextInfo, Context context, ExternalDependencyInjector externalDependencyInjector) {
        this.mContextInfo = contextInfo;
        this.mContext = context;
        this.mExternalDependencyInjector = externalDependencyInjector;
    }

    public void dataUsageTimerActivation() {
        if (getService() != null) {
            try {
                this.mService.dataUsageTimerActivation(this.mContextInfo);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with device info policy", e);
            }
        }
    }

    public final boolean externalSdCardAvailable() {
        String volumeState;
        String externalSdCardDirectory = getExternalSdCardDirectory();
        if (externalSdCardDirectory == null || (volumeState = ((StorageManager) this.mContext.getSystemService("storage")).getVolumeState(externalSdCardDirectory)) == null) {
            return false;
        }
        return volumeState.equals("mounted");
    }

    public long getAvailableCapacityExternal() {
        String externalSdCardDirectory;
        EnterpriseLicenseManager.log(this.mContextInfo, "DeviceInventory.getAvailableCapacityExternal");
        try {
            if (!externalSdCardAvailable() || (externalSdCardDirectory = getExternalSdCardDirectory()) == null) {
                return -1L;
            }
            StatFs statFs = new StatFs(externalSdCardDirectory);
            return statFs.getBlockSizeLong() * statFs.getAvailableBlocksLong();
        } catch (Exception e) {
            EmergencyButton$$ExternalSyntheticOutline0.m("getAvailableCapacityExternal", e, TAG);
        }
        return -1L;
    }

    public long getAvailableCapacityInternal() {
        EnterpriseLicenseManager.log(this.mContextInfo, "DeviceInventory.getAvailableCapacityInternal");
        try {
            String internalSdCardPath = getInternalSdCardPath();
            if (internalSdCardPath == null) {
                return -1L;
            }
            StatFs statFs = new StatFs(internalSdCardPath);
            return statFs.getBlockSizeLong() * statFs.getAvailableBlocksLong();
        } catch (Exception e) {
            EmergencyButton$$ExternalSyntheticOutline0.m("getAvailableCapacityInternal", e, TAG);
            return -1L;
        }
    }

    public final long getAvailableMemorySize(StatFs statFs) {
        return statFs.getBlockSizeLong() * statFs.getAvailableBlocksLong();
    }

    public String getDeviceOS() {
        EnterpriseLicenseManager.log(this.mContextInfo, "DeviceInventory.getDeviceOS");
        if (getService() == null) {
            return null;
        }
        try {
            return this.mService.getDeviceOS(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with device info policy", e);
            return null;
        }
    }

    public String getDeviceOSVersion() {
        EnterpriseLicenseManager.log(this.mContextInfo, "DeviceInventory.getDeviceOSVersion");
        if (getService() == null) {
            return null;
        }
        try {
            return this.mService.getDeviceOSVersion(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with device info policy", e);
            return null;
        }
    }

    public int getDroppedCallsCount() {
        EnterpriseLicenseManager.log(this.mContextInfo, "DeviceInventory.getDroppedCallsCount");
        IDeviceInfo iDeviceInfo = this.mService;
        if (iDeviceInfo == null) {
            return -1;
        }
        try {
            return iDeviceInfo.getDroppedCallsCount(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with device info policy", e);
            return -1;
        }
    }

    public final String getExternalSdCardDirectory() {
        StorageVolume[] volumeList = ((StorageManager) this.mContext.getSystemService("storage")).getVolumeList();
        if (volumeList == null || volumeList.length <= 1 || volumeList[1].getPath() == null) {
            return null;
        }
        StorageVolume storageVolume = volumeList[1];
        if (this.mExternalDependencyInjector != null) {
            Log.d(TAG, "Subsystem : " + this.mExternalDependencyInjector.storageVolumeGetSubSystem(storageVolume));
        }
        Log.d(TAG, "Path : " + storageVolume.getPath());
        return storageVolume.getPath();
    }

    public final String getInternalSdCardPath() {
        StorageVolume[] volumeList = ((StorageManager) this.mContext.getSystemService("storage")).getVolumeList();
        if (volumeList.length > 0) {
            return volumeList[0].getPath();
        }
        return null;
    }

    public String getKnoxServiceId() {
        EnterpriseLicenseManager.log(this.mContextInfo, "DeviceInventory.getKnoxServiceId", true);
        if (getService() == null) {
            return "";
        }
        try {
            return this.mService.getKnoxServiceId(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with device info policy", e);
            return "";
        }
    }

    public List<String> getKnoxServicePackageList() {
        EnterpriseLicenseManager.log(this.mContextInfo, "DeviceInventory.getKnoxServicePackageList", true);
        if (getService() != null) {
            try {
                return this.mService.getKnoxServicePackageList(this.mContextInfo);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with device info policy", e);
            }
        }
        return Collections.EMPTY_LIST;
    }

    public SimChangeInfo getLastSimChangeInfo() {
        EnterpriseLicenseManager.log(this.mContextInfo, "DeviceInventory.getLastSimChangeInfo");
        SimChangeInfo simChangeInfo = new SimChangeInfo();
        if (getMiscService() != null) {
            try {
                return this.mMiscService.getLastSimChangeInfo(this.mContextInfo);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with misc policy", e);
            }
        }
        return simChangeInfo;
    }

    public final IMiscPolicy getMiscService() {
        if (this.mMiscService == null) {
            this.mMiscService = IMiscPolicy.Stub.asInterface(ServiceManager.getService("misc_policy"));
        }
        return this.mMiscService;
    }

    public int getMissedCallsCount() {
        EnterpriseLicenseManager.log(this.mContextInfo, "DeviceInventory.getMissedCallsCount");
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.getMissedCallsCount(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with device info policy", e);
            return -1;
        }
    }

    public String getSalesCode() {
        EnterpriseLicenseManager.log(this.mContextInfo, "DeviceInventory.getSalesCode");
        if (getService() == null) {
            return null;
        }
        try {
            return this.mService.getSalesCode(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with device info policy", e);
            return null;
        }
    }

    public String getSerialNumber() {
        EnterpriseLicenseManager.log(this.mContextInfo, "DeviceInventory.getSerialNumber");
        if (getService() == null) {
            return null;
        }
        try {
            return this.mService.getSerialNumber(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with device info policy", e);
            return null;
        }
    }

    public final IDeviceInfo getService() {
        if (this.mService == null) {
            this.mService = IDeviceInfo.Stub.asInterface(ServiceManager.getService("device_info"));
        }
        return this.mService;
    }

    public int getSuccessCallsCount() {
        EnterpriseLicenseManager.log(this.mContextInfo, "DeviceInventory.getSuccessCallsCount");
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.getSuccessCallsCount(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with device info policy", e);
            return -1;
        }
    }

    public long getTotalCapacityExternal() {
        String externalSdCardDirectory;
        EnterpriseLicenseManager.log(this.mContextInfo, "DeviceInventory.getTotalCapacityExternal");
        try {
            if (!externalSdCardAvailable() || (externalSdCardDirectory = getExternalSdCardDirectory()) == null) {
                return -1L;
            }
            StatFs statFs = new StatFs(externalSdCardDirectory);
            return statFs.getBlockSizeLong() * statFs.getBlockCountLong();
        } catch (Exception e) {
            EmergencyButton$$ExternalSyntheticOutline0.m("getTotalCapacityExternal", e, TAG);
        }
        return -1L;
    }

    public long getTotalCapacityInternal() {
        EnterpriseLicenseManager.log(this.mContextInfo, "DeviceInventory.getTotalCapacityInternal");
        try {
            String internalSdCardPath = getInternalSdCardPath();
            if (internalSdCardPath == null) {
                return -1L;
            }
            StatFs statFs = new StatFs(internalSdCardPath);
            return statFs.getBlockSizeLong() * statFs.getBlockCountLong();
        } catch (Exception e) {
            EmergencyButton$$ExternalSyntheticOutline0.m("getTotalCapacityInternal", e, TAG);
            return -1L;
        }
    }

    public final long getTotalMemorySize(StatFs statFs) {
        return statFs.getBlockSizeLong() * statFs.getBlockCountLong();
    }

    public boolean isDeviceLocked() {
        AccessController.throwIfParentInstance(this.mContextInfo, "isDeviceLocked");
        EnterpriseLicenseManager.log(this.mContextInfo, "DeviceInventory.isDeviceLocked");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.isDeviceLocked(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with device info policy", e);
            return false;
        }
    }

    public boolean isDeviceSecure() {
        AccessController.throwIfParentInstance(this.mContextInfo, "isDeviceSecure");
        EnterpriseLicenseManager.log(this.mContextInfo, "DeviceInventory.isDeviceSecure");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.isDeviceSecure(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with device info policy", e);
            return false;
        }
    }

    public boolean resetCallsCount() {
        EnterpriseLicenseManager.log(this.mContextInfo, "DeviceInventory.resetCallsCount");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.resetCallsCount(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with device info policy", e);
            return false;
        }
    }

    public boolean setKnoxServiceId(List<String> list, String str) {
        EnterpriseLicenseManager.log(this.mContextInfo, "DeviceInventory.setKnoxServiceID");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setKnoxServiceId(this.mContextInfo, list, str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with device info policy", e);
            return false;
        }
    }

    public boolean storeCalling(String str, String str2, String str3, String str4, boolean z) {
        if (getService() == null) {
            return false;
        }
        try {
            this.mService.storeCalling(str, str2, str3, str4, z);
            return true;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with device inventory policy", e);
            return false;
        }
    }

    public void storeMMS(String str, String str2, String str3, boolean z) {
        if (getService() != null) {
            try {
                this.mService.storeMMS(str, str2, str3, z);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with device info policy", e);
            }
        }
    }

    public void storeSMS(String str, String str2, String str3, boolean z) {
        if (getService() != null) {
            try {
                this.mService.storeSMS(str, str2, str3, z);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with device info policy", e);
            }
        }
    }
}
