package com.android.server.enterprise.hdm;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Binder;
import android.os.IBinder;
import android.p005os.IInstalld;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.android.server.enterprise.EnterpriseServiceCallback;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.hdm.IHdmManager;
import com.samsung.android.service.DeviceRootKeyService.DeviceRootKeyServiceManager;
import com.samsung.android.service.DeviceRootKeyService.Tlv;
import com.samsung.android.wifi.SemWifiManager;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

/* loaded from: classes2.dex */
public class HdmService extends IHdmManager.Stub implements EnterpriseServiceCallback {
    public static final String TAG = "HDM - " + HdmService.class.getSimpleName();
    public final HdmSoftwareBlock hdmSoftwareBlock;
    public final Context mContext;
    public EnterpriseDeviceManager mEDM = null;
    public byte[] mSerialNumber = null;
    public String mImei0 = "";
    public byte[] mHashImei = null;
    public String mMacAddress = "";
    public final HdmVendorController vendorController = new HdmVendorController();

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void notifyToAddSystemService(String str, IBinder iBinder) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void onAdminAdded(int i) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void onAdminRemoved(int i) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void onPreAdminRemoval(int i) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void systemReady() {
    }

    public HdmService(Context context) {
        this.mContext = context;
        this.hdmSoftwareBlock = new HdmSoftwareBlock(context);
        Log.d(TAG, "HdmService.java instantiated");
    }

    public final boolean isDeviceRootKeyValid(DeviceRootKeyServiceManager deviceRootKeyServiceManager) {
        if (!deviceRootKeyServiceManager.isAliveDeviceRootKeyService()) {
            Log.e(TAG, "DRK failed in isAliveDeviceRootKeyService()");
            return false;
        }
        if (deviceRootKeyServiceManager.isExistDeviceRootKey(1)) {
            return true;
        }
        Log.e(TAG, "DRK failed in isExistDeviceRootKey()");
        return false;
    }

    public final void setImeis() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        TelephonyManager telephonyManager = (TelephonyManager) this.mContext.getSystemService("phone");
        if (telephonyManager != null) {
            try {
                if (telephonyManager.getPrimaryImei() != null) {
                    this.mImei0 = telephonyManager.getPrimaryImei();
                }
            } catch (Exception e) {
                Log.i(TAG, "failed to get info: " + e);
            }
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    public final void setHashImeiAndSerial() {
        DeviceRootKeyServiceManager.DeviceInfo deviceInfo;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        if (HdmSakManager.isSupported(this.mContext)) {
            Log.i(TAG, "calculate hashed info");
            this.mHashImei = HdmSakManager.getHashedImei(this.mContext);
            this.mSerialNumber = HdmSakManager.getHashedUniqueNumber();
        } else {
            String str = TAG;
            Log.i(str, "get hashed info from DRK");
            DeviceRootKeyServiceManager deviceRootKeyServiceManager = new DeviceRootKeyServiceManager(this.mContext);
            if (isDeviceRootKeyValid(deviceRootKeyServiceManager)) {
                deviceInfo = deviceRootKeyServiceManager.getDeviceInfo(12);
            } else {
                Log.e(str, "setHashImeiAndSerial(): DRK is not valid");
                deviceInfo = null;
            }
            if (deviceInfo != null) {
                this.mHashImei = deviceInfo.getImei();
                this.mSerialNumber = deviceInfo.getSerial();
                Log.e(str, "mSerialNumber calculated");
            }
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    public final void setMacAddress() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        SemWifiManager semWifiManager = (SemWifiManager) this.mContext.getSystemService("sem_wifi");
        if (semWifiManager != null) {
            this.mMacAddress = semWifiManager.getFactoryMacAddress();
        } else {
            Log.e(TAG, "setMacAddress is failed on SemWifiManager");
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    public final boolean setNwdInfo() {
        setImeis();
        setHashImeiAndSerial();
        if (this.mSerialNumber == null) {
            return false;
        }
        if ("".equals(this.mImei0)) {
            setMacAddress();
            if ("".equals(this.mMacAddress)) {
                return false;
            }
        }
        return this.mHashImei != null;
    }

    public final byte[] generateHdmKey() {
        byte[] bArr;
        if (HdmSakManager.isSupported(this.mContext)) {
            return HdmSakManager.generateHdmKey();
        }
        String str = TAG;
        Log.d(str, "Generate HDM key");
        DeviceRootKeyServiceManager deviceRootKeyServiceManager = new DeviceRootKeyServiceManager(this.mContext);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        if (isDeviceRootKeyValid(deviceRootKeyServiceManager)) {
            bArr = deviceRootKeyServiceManager.createServiceKeySession("HDM", 1, (Tlv) null);
        } else {
            Log.e(str, "generateHdmKey(): DRK is not valid");
            bArr = null;
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        if (bArr == null || bArr.length == 0) {
            return null;
        }
        return bArr;
    }

    public final boolean deleteHdmKey() {
        return this.vendorController.hdmDeleteKeys() == 0;
    }

    public final void releaseDrk() {
        if (HdmSakManager.isSupported(this.mContext)) {
            Log.i(TAG, "ignore releaseDrk for sak model");
            return;
        }
        DeviceRootKeyServiceManager deviceRootKeyServiceManager = new DeviceRootKeyServiceManager(this.mContext);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        int releaseServiceKeySession = deviceRootKeyServiceManager.releaseServiceKeySession();
        Binder.restoreCallingIdentity(clearCallingIdentity);
        if (releaseServiceKeySession != 0) {
            Log.e(TAG, "failure releasing drk service: " + releaseServiceKeySession);
        }
    }

    public final EnterpriseDeviceManager getEDM() {
        if (this.mEDM == null) {
            this.mEDM = EnterpriseDeviceManager.getInstance(this.mContext);
        }
        return this.mEDM;
    }

    public final ContextInfo enforceOwnerOnlyHDMPermission(ContextInfo contextInfo) {
        return getEDM().enforceOwnerOnlyAndActiveAdminPermission(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_HDM")));
    }

    public final ContextInfo enforceHDMPermission(ContextInfo contextInfo) {
        return getEDM().enforcePermissionByContext(contextInfo, "com.samsung.android.knox.permission.KNOX_HDM");
    }

    public final boolean validateParameter(String str, int i) {
        return (str == null || str.isEmpty() || str.length() >= i) ? false : true;
    }

    public synchronized String setHdmPolicy(ContextInfo contextInfo, String str) {
        byte[] bArr;
        byte[] hdmApplyPolicy;
        String str2 = TAG;
        Log.d(str2, "setHdmPolicy() on HdmService.java");
        enforceOwnerOnlyHDMPermission(contextInfo);
        if (!validateParameter(str, IInstalld.FLAG_FORCE)) {
            Log.d(str2, "parameter check failed");
            return null;
        }
        if (!setNwdInfo()) {
            Log.e(str2, "setNwdInfo failed");
        }
        if (this.vendorController.hdmLoad() != 0) {
            Log.d(str2, "hdm_load failure");
            return null;
        }
        Log.d(str2, "checking whether key is saved");
        boolean z = false;
        boolean z2 = this.vendorController.hdmGetKey() == 0;
        if (!z2) {
            Log.d(str2, "no key, generate");
            bArr = generateHdmKey();
            if (bArr != null) {
                if (this.vendorController.hdmSetKey(bArr) != 0) {
                    Log.e(str2, "Failed to write DRK");
                }
            } else {
                Log.e(str2, "generateHdmKey returned null");
            }
        } else {
            Log.d(str2, "found key, no need to generate");
            bArr = null;
            z = true;
        }
        if (!z2 && bArr == null) {
            Log.d(str2, "wrappedKey is null, delete HDM key for a new try");
            if (!deleteHdmKey()) {
                Log.e(str2, "deleteHdmKey failure");
            }
            hdmApplyPolicy = null;
        } else {
            Log.d(str2, "HDM TLC call!");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            Charset defaultCharset = Charset.defaultCharset();
            String str3 = this.mImei0;
            if (str3 == null) {
                str3 = "";
            }
            byte[] bytes = str3.getBytes(defaultCharset);
            String str4 = this.mMacAddress;
            if (str4 == null) {
                str4 = "";
            }
            byte[] bytes2 = str4.getBytes(defaultCharset);
            Log.i(str2, "beforeSoftwareBlockResult: " + this.hdmSoftwareBlock.handleSoftwareBlockBefore(str.getBytes(defaultCharset)));
            hdmApplyPolicy = this.vendorController.hdmApplyPolicy(str.getBytes(defaultCharset), this.mSerialNumber, bytes, this.mHashImei, bytes2, z);
            Log.i(str2, "afterSoftwareBlockResult: " + this.hdmSoftwareBlock.handleSoftwareBlockAfter(hdmApplyPolicy));
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
        if (this.vendorController.hdmUnload() != 0) {
            Log.d(str2, "hdm_unload failure");
        }
        if (!z) {
            releaseDrk();
        }
        if (hdmApplyPolicy == null) {
            Log.d(str2, "hdm_apply_policy Fail");
            if (!deleteHdmKey()) {
                Log.d(str2, "deleteHdmKey failure");
            }
        }
        if (hdmApplyPolicy == null || hdmApplyPolicy.length <= 1) {
            return null;
        }
        return new String(hdmApplyPolicy, Charset.defaultCharset());
    }

    public synchronized String getHdmId(ContextInfo contextInfo, String str) {
        String str2;
        byte[] bArr;
        byte[] hdmGetId;
        String str3 = TAG;
        Log.d(str3, "getHdmId() on HdmService.java");
        enforceOwnerOnlyHDMPermission(contextInfo);
        if (!validateParameter(str, 95)) {
            Log.d(str3, "parameter check failed");
            return null;
        }
        setImeis();
        setMacAddress();
        String str4 = this.mImei0;
        if (str4 != null && !str4.isEmpty()) {
            str2 = this.mImei0;
        } else {
            String str5 = this.mMacAddress;
            str2 = (str5 == null || str5.isEmpty()) ? null : this.mMacAddress;
        }
        if (str2 != null && !str2.isEmpty()) {
            if (this.vendorController.hdmLoad() != 0) {
                Log.d(str3, "hdm_load failure");
                return null;
            }
            Log.d(str3, "checking whether key is saved");
            boolean z = false;
            boolean z2 = this.vendorController.hdmGetKey() == 0;
            if (!z2) {
                Log.d(str3, "no key, generate");
                bArr = generateHdmKey();
                if (bArr != null) {
                    if (this.vendorController.hdmSetKey(bArr) != 0) {
                        Log.e(str3, "Failed to write DRK");
                    }
                } else {
                    Log.e(str3, "generateHdmKey returned null");
                }
            } else {
                Log.d(str3, "found key, no need to generate");
                bArr = null;
                z = true;
            }
            if (!z2 && bArr == null) {
                Log.d(str3, "wrappedKey is null, delete HDM key for a new try");
                if (!deleteHdmKey()) {
                    Log.e(str3, "deleteHdmKey failure");
                }
                hdmGetId = null;
            } else {
                Log.d(str3, "HDM TLC call!");
                long clearCallingIdentity = Binder.clearCallingIdentity();
                hdmGetId = this.vendorController.hdmGetId(str2.getBytes(Charset.defaultCharset()), str.getBytes(Charset.defaultCharset()), z);
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
            if (this.vendorController.hdmUnload() != 0) {
                Log.d(str3, "hdm_unload failure");
            }
            if (!z) {
                releaseDrk();
            }
            if (hdmGetId == null) {
                Log.d(str3, "hdm_get_id Fail");
                if (!deleteHdmKey()) {
                    Log.d(str3, "deleteHdmKey failure");
                }
            }
            if (hdmGetId == null || hdmGetId.length <= 1) {
                return null;
            }
            return new String(hdmGetId, Charset.defaultCharset());
        }
        Log.d(str3, "getHdmId Fail to get device ID");
        return null;
    }

    public synchronized String getHdmPolicy(ContextInfo contextInfo, String str, String str2) {
        Log.d(TAG, "getHdmPolicy() on HdmService.java");
        enforceOwnerOnlyHDMPermission(contextInfo);
        return getHdmPolicy(str, str2);
    }

    public final synchronized String getHdmPolicy(String str, String str2) {
        try {
        } catch (Exception e) {
            Log.e(TAG, "getPolicy failed: " + e);
        }
        if (!validateParameter(str, 95)) {
            Log.d(TAG, "parameter check failed for requestId");
            return null;
        }
        if (!validateParameter(str2, 40)) {
            Log.d(TAG, "parameter check failed for serviceName");
            return null;
        }
        if (this.vendorController.hdmLoad() != 0) {
            Log.d(TAG, "hdm_load failure");
            return null;
        }
        String str3 = TAG;
        Log.d(str3, "checking whether key is saved");
        boolean z = false;
        if (this.vendorController.hdmGetKey() == 0) {
            z = true;
        } else {
            Log.d(str3, "no key, generate");
            byte[] generateHdmKey = generateHdmKey();
            if (generateHdmKey != null) {
                if (this.vendorController.hdmSetKey(generateHdmKey) != 0) {
                    Log.e(str3, "Failed to write DRK");
                }
            } else {
                this.vendorController.hdmUnload();
                return null;
            }
        }
        byte[] hdmGetPolicy = this.vendorController.hdmGetPolicy(z, str.getBytes(Charset.defaultCharset()), str2.getBytes(Charset.defaultCharset()));
        if (this.vendorController.hdmUnload() != 0) {
            Log.d(str3, "hdm_unload failure");
        }
        if (!z) {
            releaseDrk();
        }
        if (hdmGetPolicy != null && hdmGetPolicy.length > 1) {
            return new String(hdmGetPolicy, Charset.defaultCharset());
        }
        Log.e(str3, "response == null or len <= 1");
        return null;
    }

    public synchronized int setHdmTaCmd(ContextInfo contextInfo, int i) {
        String str = TAG;
        Log.d(str, "setHdmTaCmd() on HdmService.java");
        enforceHDMPermission(contextInfo);
        if (this.vendorController.hdmLoad() != 0) {
            Log.d(str, "hdm_load failure");
            return -1;
        }
        Log.d(str, "HDM TLC call!");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        int hdmKgCmd = this.vendorController.hdmKgCmd(i);
        Binder.restoreCallingIdentity(clearCallingIdentity);
        if (this.vendorController.hdmUnload() != 0) {
            Log.d(str, "hdm_unload failure");
        }
        return hdmKgCmd;
    }

    public synchronized boolean isSwBlockEnabled(ContextInfo contextInfo) {
        Log.d(TAG, "isSwBlockEnabled() on HdmService.java");
        enforceHDMPermission(contextInfo);
        return this.hdmSoftwareBlock.isSwBlockEnabled();
    }

    public synchronized boolean setSwBlock(ContextInfo contextInfo, boolean z) {
        Log.d(TAG, "setSwBlock() on HdmService.java: " + z);
        enforceHDMPermission(contextInfo);
        return this.hdmSoftwareBlock.setSwBlock(z);
    }

    public synchronized int syncSwBlockFromBoot() {
        String str = TAG;
        Log.d(str, "syncSwBlockFromBoot() on HdmService.java: ");
        if (Binder.getCallingUid() != 1000) {
            Log.i(str, "invalid caller uid: " + Binder.getCallingUid());
            return -1;
        }
        String callerPackageName = getCallerPackageName(Binder.getCallingPid());
        if (!"com.samsung.android.hdmapp".equalsIgnoreCase(callerPackageName)) {
            Log.i(str, "invalid caller: " + callerPackageName);
            return -2;
        }
        return this.hdmSoftwareBlock.syncSwBlockFromBoot(getHdmPolicy(UUID.randomUUID().toString(), "HDMFW"));
    }

    public synchronized boolean isNFCBlockedByHDM(ContextInfo contextInfo) {
        Log.d(TAG, "isNFCBlockedByHDM() on HdmService.java: ");
        enforceHDMPermission(contextInfo);
        return this.hdmSoftwareBlock.isNFCBlockedByHDM(getHdmPolicy(UUID.randomUUID().toString(), "HDMFW"));
    }

    public final String getCallerPackageName(int i) {
        Log.d(TAG, "getCallerPackageName");
        try {
            return ((ActivityManager) this.mContext.getSystemService("activity")).getPackageFromAppProcesses(i);
        } catch (Exception unused) {
            return null;
        }
    }
}
