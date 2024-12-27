package com.android.server.enterprise.hdm;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Binder;
import android.os.IBinder;
import android.os.SystemProperties;
import android.security.KeyStore2;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0;
import com.android.server.enterprise.EnterpriseServiceCallback;

import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.hdm.IHdmManager;
import com.samsung.android.service.DeviceRootKeyService.DeviceRootKeyServiceManager;
import com.samsung.android.service.DeviceRootKeyService.Tlv;
import com.samsung.android.wifi.SemWifiManager;
import com.samsung.security.securekeyblob.SecureKeyGenerator;
import com.samsung.security.securekeyblob.SecureKeyResult;

import vendor.samsung.hardware.khdm.ISehKhdm;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public final class HdmService extends IHdmManager.Stub implements EnterpriseServiceCallback {
    public final HdmSoftwareBlock hdmSoftwareBlock;
    public final Context mContext;
    public final HdmVendorController vendorController;
    public EnterpriseDeviceManager mEDM = null;
    public byte[] mSerialNumber = null;
    public String mImei0 = "";
    public byte[] mHashImei = null;
    public String mMacAddress = "";

    public HdmService(Context context) {
        this.mContext = context;
        HdmSoftwareBlock hdmSoftwareBlock = new HdmSoftwareBlock();
        hdmSoftwareBlock.deferredServiceStarted = false;
        try {
            File file = new File("/data/misc/hdm");
            if (!file.exists()) {
                file.mkdir();
            }
            EnterpriseDeviceManager enterpriseDeviceManager =
                    EnterpriseDeviceManager.getInstance(context);
            hdmSoftwareBlock.edm = enterpriseDeviceManager;
            hdmSoftwareBlock.restrictionPolicy = enterpriseDeviceManager.getRestrictionPolicy();
            hdmSoftwareBlock.supportedFunctionMap = hdmSoftwareBlock.initControlMap();
            int initTargetSubSystems = hdmSoftwareBlock.initTargetSubSystems();
            hdmSoftwareBlock.targetSubSystems = initTargetSubSystems;
            hdmSoftwareBlock.swBlockEnabled = hdmSoftwareBlock.isSwBlockEnabled();
            SystemProperties.set(
                    "sys.hdm.double.protection.subsystem", Integer.toString(initTargetSubSystems));
        } catch (Exception e) {
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                    e, "HdmSoftwareBlock failed: ", "HDM - HdmSoftwareBlock");
        }
        this.hdmSoftwareBlock = hdmSoftwareBlock;
        this.vendorController = new HdmVendorController();
        Log.d("HDM - HdmService", "HdmService.java instantiated");
    }

    public static boolean validateParameter(int i, String str) {
        return (str == null || str.isEmpty() || str.length() >= i) ? false : true;
    }

    public final boolean deleteHdmKey() {
        int i;
        HdmVendorController hdmVendorController = this.vendorController;
        hdmVendorController.getClass();
        try {
            if (hdmVendorController.hidlSupport) {
                i = HdmVendorController.hdm_delete_keys();
            } else {
                i = ((ISehKhdm.Stub.Proxy) HdmVendorController.getService()).deleteKey();
                Log.i("Hdm - VendorInterface", "hdmDeleteKeys using ISehKhdm: " + i);
            }
        } catch (Exception e) {
            Log.e("Hdm - VendorInterface", "hdmDeleteKeys failed: " + e, e);
            i = -1;
        }
        return i == 0;
    }

    public final void enforceOwnerOnlyHDMPermission(ContextInfo contextInfo) {
        if (this.mEDM == null) {
            this.mEDM = EnterpriseDeviceManager.getInstance(this.mContext);
        }
        this.mEDM.enforceOwnerOnlyAndActiveAdminPermission(
                contextInfo,
                new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_HDM")));
    }

    public final byte[] generateHdmKey() {
        byte[] bArr;
        if (HdmSakManager.isSupported(this.mContext)) {
            try {
                SecureKeyGenerator secureKeyGenerator = new SecureKeyGenerator();
                secureKeyGenerator.mSamsungSecurekeyGeneratorBinder = null;
                KeyStore2.getInstance();
                SecureKeyResult generateKeyPair =
                        secureKeyGenerator.generateKeyPair(HdmSakManager.genKeySpec());
                byte[] bArr2 = generateKeyPair.mServiceKey;
                X509Certificate[] x509CertificateArr = generateKeyPair.mCertificates;
                Log.i(
                        "HDM - HdmSakManager",
                        "serviceId: "
                                + new String(generateKeyPair.mServiceID)
                                + ", certLen: "
                                + x509CertificateArr.length
                                + ", keyLen: "
                                + bArr2.length);
                return HdmSakManager.constructTLV(x509CertificateArr, bArr2);
            } catch (Exception e) {
                Log.e("HDM - HdmSakManager", "generateHdmKey failed: " + e, e);
                return null;
            }
        }
        Log.d("HDM - HdmService", "Generate HDM key");
        DeviceRootKeyServiceManager deviceRootKeyServiceManager =
                new DeviceRootKeyServiceManager(this.mContext);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            boolean z = false;
            if (!deviceRootKeyServiceManager.isAliveDeviceRootKeyService()) {
                Log.e("HDM - HdmService", "DRK failed in isAliveDeviceRootKeyService()");
            } else if (deviceRootKeyServiceManager.isExistDeviceRootKey(1)) {
                z = true;
            } else {
                Log.e("HDM - HdmService", "DRK failed in isExistDeviceRootKey()");
            }
            if (z) {
                bArr = deviceRootKeyServiceManager.createServiceKeySession("HDM", 1, (Tlv) null);
            } else {
                Log.e("HDM - HdmService", "generateHdmKey(): DRK is not valid but try to generate");
                bArr = deviceRootKeyServiceManager.createServiceKeySession("HDM", 1, (Tlv) null);
            }
        } catch (Exception e2) {
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                    e2, "drk request failed: ", "HDM - HdmService");
            bArr = null;
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        if (bArr == null || bArr.length == 0) {
            return null;
        }
        return bArr;
    }

    public final synchronized String getHdmId(ContextInfo contextInfo, String str) {
        String str2;
        byte[] bArr;
        byte[] hdmGetId;
        Log.d("HDM - HdmService", "getHdmId() on HdmService.java");
        enforceOwnerOnlyHDMPermission(contextInfo);
        if (!validateParameter(95, str)) {
            Log.d("HDM - HdmService", "parameter check failed");
            return null;
        }
        setImeis();
        setMacAddress();
        String str3 = this.mImei0;
        if (str3 == null || str3.isEmpty()) {
            String str4 = this.mMacAddress;
            str2 = (str4 == null || str4.isEmpty()) ? null : this.mMacAddress;
        } else {
            str2 = this.mImei0;
        }
        if (str2 != null && !str2.isEmpty()) {
            if (this.vendorController.hdmLoad() != 0) {
                Log.d("HDM - HdmService", "hdm_load failure");
                return null;
            }
            Log.d("HDM - HdmService", "checking whether key is saved");
            boolean z = false;
            boolean z2 = this.vendorController.hdmGetKey() == 0;
            if (z2) {
                Log.d("HDM - HdmService", "found key, no need to generate");
                bArr = null;
                z = true;
            } else {
                Log.d("HDM - HdmService", "no key, generate");
                bArr = generateHdmKey();
                if (bArr == null) {
                    Log.e("HDM - HdmService", "generateHdmKey returned null");
                } else if (this.vendorController.hdmSetKey(bArr) != 0) {
                    Log.e("HDM - HdmService", "Failed to write DRK");
                }
            }
            if (z2 || bArr != null) {
                Log.d("HDM - HdmService", "HDM TLC call!");
                long clearCallingIdentity = Binder.clearCallingIdentity();
                hdmGetId =
                        this.vendorController.hdmGetId(
                                z,
                                str2.getBytes(Charset.defaultCharset()),
                                str.getBytes(Charset.defaultCharset()));
                Binder.restoreCallingIdentity(clearCallingIdentity);
            } else {
                Log.d("HDM - HdmService", "wrappedKey is null, delete HDM key for a new try");
                if (!deleteHdmKey()) {
                    Log.e("HDM - HdmService", "deleteHdmKey failure");
                }
                hdmGetId = null;
            }
            if (this.vendorController.hdmUnload() != 0) {
                Log.d("HDM - HdmService", "hdm_unload failure");
            }
            if (!z) {
                releaseDrk();
            }
            if (hdmGetId == null) {
                Log.d("HDM - HdmService", "hdm_get_id Fail");
                if (!deleteHdmKey()) {
                    Log.d("HDM - HdmService", "deleteHdmKey failure");
                }
            }
            if (hdmGetId == null || hdmGetId.length <= 1) {
                return null;
            }
            return new String(hdmGetId, Charset.defaultCharset());
        }
        Log.d("HDM - HdmService", "getHdmId Fail to get device ID");
        return null;
    }

    public final synchronized String getHdmPolicy(
            ContextInfo contextInfo, String str, String str2) {
        Log.d("HDM - HdmService", "getHdmPolicy() on HdmService.java");
        enforceOwnerOnlyHDMPermission(contextInfo);
        return getHdmPolicy(str, str2);
    }

    public final synchronized String getHdmPolicy(String str, String str2) {
        boolean z;
        try {
        } catch (Exception e) {
            Log.e("HDM - HdmService", "getPolicy failed: " + e);
        }
        if (!validateParameter(95, str)) {
            Log.d("HDM - HdmService", "parameter check failed for requestId");
            return null;
        }
        if (!validateParameter(40, str2)) {
            Log.d("HDM - HdmService", "parameter check failed for serviceName");
            return null;
        }
        if (this.vendorController.hdmLoad() != 0) {
            Log.d("HDM - HdmService", "hdm_load failure");
            return null;
        }
        Log.d("HDM - HdmService", "checking whether key is saved");
        if (this.vendorController.hdmGetKey() == 0) {
            z = true;
        } else {
            Log.d("HDM - HdmService", "no key, generate");
            byte[] generateHdmKey = generateHdmKey();
            if (generateHdmKey == null) {
                this.vendorController.hdmUnload();
                return null;
            }
            if (this.vendorController.hdmSetKey(generateHdmKey) != 0) {
                Log.e("HDM - HdmService", "Failed to write DRK");
            }
            z = false;
        }
        byte[] hdmGetPolicy =
                this.vendorController.hdmGetPolicy(
                        z,
                        str.getBytes(Charset.defaultCharset()),
                        str2.getBytes(Charset.defaultCharset()));
        if (this.vendorController.hdmUnload() != 0) {
            Log.d("HDM - HdmService", "hdm_unload failure");
        }
        if (!z) {
            releaseDrk();
        }
        if (hdmGetPolicy == null || hdmGetPolicy.length <= 1) {
            Log.e("HDM - HdmService", "response == null or len <= 1");
            return null;
        }
        return new String(hdmGetPolicy, Charset.defaultCharset());
    }

    public final synchronized boolean isNFCBlockedByHDM(ContextInfo contextInfo) {
        boolean z;
        int appliedHdmPolicy;
        Log.d("HDM - HdmService", "isNFCBlockedByHDM() on HdmService.java: ");
        if (this.mEDM == null) {
            this.mEDM = EnterpriseDeviceManager.getInstance(this.mContext);
        }
        this.mEDM.enforcePermissionByContext(
                contextInfo, "com.samsung.android.knox.permission.KNOX_HDM");
        HdmSoftwareBlock hdmSoftwareBlock = this.hdmSoftwareBlock;
        String hdmPolicy = getHdmPolicy(UUID.randomUUID().toString(), "HDMFW");
        hdmSoftwareBlock.getClass();
        try {
            appliedHdmPolicy =
                    hdmSoftwareBlock.getAppliedHdmPolicy(
                            hdmPolicy.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                    e, "isNFCBlockedByHDM failed: ", "HDM - HdmSoftwareBlock");
        }
        if ((appliedHdmPolicy & 64) != 0 && (hdmSoftwareBlock.targetSubSystems & 64) != 0) {
            Log.i("HDM - HdmSoftwareBlock", "nfc is blocked: " + appliedHdmPolicy);
            z = true;
        }
        z = false;
        return z;
    }

    public final synchronized boolean isSwBlockEnabled(ContextInfo contextInfo) {
        Log.d("HDM - HdmService", "isSwBlockEnabled() on HdmService.java");
        if (this.mEDM == null) {
            this.mEDM = EnterpriseDeviceManager.getInstance(this.mContext);
        }
        this.mEDM.enforcePermissionByContext(
                contextInfo, "com.samsung.android.knox.permission.KNOX_HDM");
        return this.hdmSoftwareBlock.isSwBlockEnabled();
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void notifyToAddSystemService(String str, IBinder iBinder) {}

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onAdminAdded(int i) {}

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onAdminRemoved(int i) {}

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onPreAdminRemoval(int i) {}

    public final void releaseDrk() {
        if (HdmSakManager.isSupported(this.mContext)) {
            Log.i("HDM - HdmService", "ignore releaseDrk for sak model");
            return;
        }
        DeviceRootKeyServiceManager deviceRootKeyServiceManager =
                new DeviceRootKeyServiceManager(this.mContext);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        int releaseServiceKeySession = deviceRootKeyServiceManager.releaseServiceKeySession();
        Binder.restoreCallingIdentity(clearCallingIdentity);
        if (releaseServiceKeySession != 0) {
            ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                    releaseServiceKeySession,
                    "failure releasing drk service: ",
                    "HDM - HdmService");
        }
    }

    public final synchronized String setHdmPolicy(ContextInfo contextInfo, String str) {
        byte[] bArr;
        byte[] hdmApplyPolicy;
        int i;
        Log.d("HDM - HdmService", "setHdmPolicy() on HdmService.java");
        enforceOwnerOnlyHDMPermission(contextInfo);
        if (!validateParameter(8192, str)) {
            Log.d("HDM - HdmService", "parameter check failed");
            return null;
        }
        if (!setNwdInfo()) {
            Log.e("HDM - HdmService", "setNwdInfo failed");
        }
        if (this.vendorController.hdmLoad() != 0) {
            Log.d("HDM - HdmService", "hdm_load failure");
            return null;
        }
        Log.d("HDM - HdmService", "checking whether key is saved");
        boolean z = false;
        boolean z2 = this.vendorController.hdmGetKey() == 0;
        if (z2) {
            Log.d("HDM - HdmService", "found key, no need to generate");
            bArr = null;
            z = true;
        } else {
            Log.d("HDM - HdmService", "no key, generate");
            bArr = generateHdmKey();
            if (bArr == null) {
                Log.e("HDM - HdmService", "generateHdmKey returned null");
            } else if (this.vendorController.hdmSetKey(bArr) != 0) {
                Log.e("HDM - HdmService", "Failed to write DRK");
            }
        }
        if (z2 || bArr != null) {
            Log.d("HDM - HdmService", "HDM TLC call!");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            Charset defaultCharset = Charset.defaultCharset();
            String str2 = this.mImei0;
            if (str2 == null) {
                str2 = "";
            }
            byte[] bytes = str2.getBytes(defaultCharset);
            String str3 = this.mMacAddress;
            if (str3 == null) {
                str3 = "";
            }
            byte[] bytes2 = str3.getBytes(defaultCharset);
            Log.i(
                    "HDM - HdmService",
                    "beforeSoftwareBlockResult: "
                            + this.hdmSoftwareBlock.handleSoftwareBlockBefore(
                                    str.getBytes(defaultCharset)));
            hdmApplyPolicy =
                    this.vendorController.hdmApplyPolicy(
                            str.getBytes(defaultCharset),
                            this.mSerialNumber,
                            bytes,
                            this.mHashImei,
                            bytes2,
                            z);
            HdmSoftwareBlock hdmSoftwareBlock = this.hdmSoftwareBlock;
            hdmSoftwareBlock.getClass();
            try {
                i =
                        hdmSoftwareBlock.applySwBlock(
                                hdmSoftwareBlock.getAppliedHdmPolicy(hdmApplyPolicy), true);
            } catch (Exception e) {
                Log.e("HDM - HdmSoftwareBlock", "handleSoftwareBlockAfter failed: " + e);
                i = -1;
            }
            Log.i("HDM - HdmService", "afterSoftwareBlockResult: " + i);
            Binder.restoreCallingIdentity(clearCallingIdentity);
        } else {
            Log.d("HDM - HdmService", "wrappedKey is null, delete HDM key for a new try");
            if (!deleteHdmKey()) {
                Log.e("HDM - HdmService", "deleteHdmKey failure");
            }
            hdmApplyPolicy = null;
        }
        if (this.vendorController.hdmUnload() != 0) {
            Log.d("HDM - HdmService", "hdm_unload failure");
        }
        if (!z) {
            releaseDrk();
        }
        if (hdmApplyPolicy == null) {
            Log.d("HDM - HdmService", "hdm_apply_policy Fail");
            if (!deleteHdmKey()) {
                Log.d("HDM - HdmService", "deleteHdmKey failure");
            }
        }
        if (hdmApplyPolicy == null || hdmApplyPolicy.length <= 1) {
            return null;
        }
        return new String(hdmApplyPolicy, Charset.defaultCharset());
    }

    public final synchronized int setHdmTaCmd(ContextInfo contextInfo, int i) {
        Log.d("HDM - HdmService", "setHdmTaCmd() on HdmService.java");
        if (this.mEDM == null) {
            this.mEDM = EnterpriseDeviceManager.getInstance(this.mContext);
        }
        this.mEDM.enforcePermissionByContext(
                contextInfo, "com.samsung.android.knox.permission.KNOX_HDM");
        if (this.vendorController.hdmLoad() != 0) {
            Log.d("HDM - HdmService", "hdm_load failure");
            return -1;
        }
        Log.d("HDM - HdmService", "HDM TLC call!");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        int hdmKgCmd = this.vendorController.hdmKgCmd(i);
        Binder.restoreCallingIdentity(clearCallingIdentity);
        if (this.vendorController.hdmUnload() != 0) {
            Log.d("HDM - HdmService", "hdm_unload failure");
        }
        return hdmKgCmd;
    }

    public final void setImeis() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        TelephonyManager telephonyManager =
                (TelephonyManager) this.mContext.getSystemService("phone");
        if (telephonyManager != null) {
            try {
                if (telephonyManager.getPrimaryImei() != null) {
                    this.mImei0 = telephonyManager.getPrimaryImei();
                }
            } catch (Exception e) {
                Log.i("HDM - HdmService", "failed to get info: " + e);
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
            Log.e("HDM - HdmService", "setMacAddress is failed on SemWifiManager");
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    /* JADX WARN: Code restructure failed: missing block: B:36:0x0081, code lost:

       r3 = move-exception;
    */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0089, code lost:

       com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(r3, "getHashedUniqueNumber failed: ", "HDM - HdmSakManager");
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean setNwdInfo() {
        /*
            Method dump skipped, instructions count: 268
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.hdm.HdmService.setNwdInfo():boolean");
    }

    public final synchronized boolean setSwBlock(ContextInfo contextInfo, boolean z) {
        Log.d("HDM - HdmService", "setSwBlock() on HdmService.java: " + z);
        if (this.mEDM == null) {
            this.mEDM = EnterpriseDeviceManager.getInstance(this.mContext);
        }
        this.mEDM.enforcePermissionByContext(
                contextInfo, "com.samsung.android.knox.permission.KNOX_HDM");
        return this.hdmSoftwareBlock.setSwBlock(z);
    }

    public final synchronized int syncSwBlockFromBoot() {
        String str;
        Log.d("HDM - HdmService", "syncSwBlockFromBoot() on HdmService.java: ");
        int i = -1;
        if (Binder.getCallingUid() != 1000) {
            Log.i("HDM - HdmService", "invalid caller uid: " + Binder.getCallingUid());
            return -1;
        }
        int callingPid = Binder.getCallingPid();
        Log.d("HDM - HdmService", "getCallerPackageName");
        try {
            str =
                    ((ActivityManager) this.mContext.getSystemService("activity"))
                            .getPackageFromAppProcesses(callingPid);
        } catch (Exception unused) {
            str = null;
        }
        if (!"com.samsung.android.hdmapp".equalsIgnoreCase(str)) {
            Log.i("HDM - HdmService", "invalid caller: " + str);
            return -2;
        }
        HdmSoftwareBlock hdmSoftwareBlock = this.hdmSoftwareBlock;
        String hdmPolicy = getHdmPolicy(UUID.randomUUID().toString(), "HDMFW");
        hdmSoftwareBlock.getClass();
        try {
            i =
                    hdmSoftwareBlock.applySwBlock(
                            hdmSoftwareBlock.getAppliedHdmPolicy(
                                    hdmPolicy.getBytes(StandardCharsets.UTF_8)),
                            false);
        } catch (Exception e) {
            Log.e("HDM - HdmSoftwareBlock", "syncSwBlockFromBoot failed: " + e);
        }
        return i;
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void systemReady() {}
}
