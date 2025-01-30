package com.samsung.android.knox.container;

import android.app.admin.IDevicePolicyManager;
import android.content.ComponentName;
import android.content.IntentFilter;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.text.TextUtils;
import android.util.Log;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.samsung.android.knox.AppIdentity;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.EdmConstants;
import com.samsung.android.knox.EdmUtils;
import com.samsung.android.knox.KnoxInternalFeature;
import com.samsung.android.knox.application.IApplicationPolicy;
import com.samsung.android.knox.container.IKnoxContainerManager;
import com.samsung.android.knox.license.EnterpriseLicenseManager;
import com.samsung.android.knox.remotecontrol.IRemoteInjection;
import com.samsung.android.knox.restriction.IRestrictionPolicy;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class ContainerConfigurationPolicy {
    public static final int ERROR_INTERNAL_ERROR = -2;
    public static final int ERROR_INVALID_KEY = -1;
    public static final int ERROR_NONE = 0;
    public static final String FIDO_REQUEST_URI = "fido_request_uri";
    public static final String FIDO_RESPONSE_URI = "fido_response_uri";
    public static final String KEY_IMAGE = "key-image";
    public static final String KEY_NAME = "key-name";
    public static final String OPTION_CALLER_INFO = "option_callerinfo";
    public static final int RES_TYPE_BADGE = 1;
    public static final int RES_TYPE_NAME_ICON = 3;
    public static final int RES_TYPE_PERSONAL_MODE_NAME = 5;
    public static final int RES_TYPE_PROFILE_NAME = 4;
    public static final int RES_TYPE_PROFILE_SWITCH_ICON = 2;
    public static String TAG = "ContainerConfigurationPolicy";
    public static IRestrictionPolicy gRestrictionService;
    public static IKnoxContainerManager mMUMContainerService;
    public IApplicationPolicy mAppService;
    public final ContextInfo mContextInfo;
    public IDevicePolicyManager mDPMService;
    public IRemoteInjection mRemoteControlService;

    public ContainerConfigurationPolicy(ContextInfo contextInfo) {
        this.mContextInfo = contextInfo;
    }

    public static synchronized IKnoxContainerManager getMUMContainerService() {
        IKnoxContainerManager iKnoxContainerManager;
        synchronized (ContainerConfigurationPolicy.class) {
            if (mMUMContainerService == null) {
                mMUMContainerService = IKnoxContainerManager.Stub.asInterface(ServiceManager.getService("mum_container_policy"));
            }
            iKnoxContainerManager = mMUMContainerService;
        }
        return iKnoxContainerManager;
    }

    public static synchronized IRestrictionPolicy getRestrictionService() {
        IRestrictionPolicy iRestrictionPolicy;
        synchronized (ContainerConfigurationPolicy.class) {
            if (gRestrictionService == null) {
                gRestrictionService = IRestrictionPolicy.Stub.asInterface(ServiceManager.getService("restriction_policy"));
            }
            iRestrictionPolicy = gRestrictionService;
        }
        return iRestrictionPolicy;
    }

    public final void addCrossProfileIntentFilter(ComponentName componentName, IntentFilter intentFilter, int i) {
        EnterpriseLicenseManager.log(this.mContextInfo, "ContainerConfigurationPolicy.addCrossProfileIntentFilter");
        if (KnoxInternalFeature.KNOX_CONFIG_VERSION < 15 || getDPMService() == null) {
            return;
        }
        DependencyWrapper.addCrossProfileIntentFilterMDM(this.mDPMService, componentName, intentFilter, i, EdmUtils.getCallingUserId(this.mContextInfo));
    }

    public final boolean addHomeShortcutToPersonal(String str, String str2) {
        EnterpriseLicenseManager.log(this.mContextInfo, "ContainerConfigurationPolicy.addHomeShortcutToPersonal");
        IKnoxContainerManager mUMContainerService = getMUMContainerService();
        if (mUMContainerService == null) {
            Log.e(TAG, "ContainerApplication PolicyService is not yet ready!!!");
            return false;
        }
        try {
            return mUMContainerService.addHomeShortcutToPersonal(this.mContextInfo, str, str2);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at Application PolicyService API addHomeShortcutToPersonal ", e);
            return false;
        }
    }

    public final boolean addNetworkSSID(String str) {
        EnterpriseLicenseManager.log(this.mContextInfo, "ContainerConfigurationPolicy.addNetworkSSID");
        IKnoxContainerManager mUMContainerService = getMUMContainerService();
        if (str != null && !str.isEmpty()) {
            if (mUMContainerService == null) {
                Log.e(TAG, "ContainerPolicy Service is not yet ready!!!");
                return false;
            }
            try {
                return mUMContainerService.addNetworkSSID(this.mContextInfo, str);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed at ContainerConfigurationPolicy API addNetworkSSID", e);
            }
        }
        return false;
    }

    public final boolean addPackageToExternalStorageSBABlackList(String str) {
        EnterpriseLicenseManager.log(this.mContextInfo, "ContainerConfigurationPolicy.addPackageToExternalStorageSBABlackList");
        if (!checkExternalSDCardAPICallerPermission()) {
            return false;
        }
        IKnoxContainerManager mUMContainerService = getMUMContainerService();
        if (mUMContainerService == null) {
            Log.e(TAG, "KnoxMumContainer PolicyService is not yet ready!!!");
            return false;
        }
        try {
            return mUMContainerService.addPackageToExternalStorageBlackList(this.mContextInfo, new AppIdentity(str, (String) null)) == 0;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at ContainerConfigurationPolicy API addPackageToExternalStorageSBABlackList", e);
            return false;
        }
    }

    public final boolean addPackageToExternalStorageWhiteList(String str, Signature[] signatureArr) {
        EnterpriseLicenseManager.log(this.mContextInfo, "ContainerConfigurationPolicy.addPackageToExternalStorageWhiteList");
        if (!checkExternalSDCardAPICallerPermission()) {
            return false;
        }
        IKnoxContainerManager mUMContainerService = getMUMContainerService();
        if (mUMContainerService == null) {
            Log.e(TAG, "KnoxMumContainer PolicyService is not yet ready!!!");
            return false;
        }
        String str2 = "";
        if (signatureArr != null) {
            try {
                if (signatureArr.length > 0) {
                    String[] strArr = new String[signatureArr.length];
                    for (int i = 0; i < signatureArr.length; i++) {
                        strArr[i] = signatureArr[i].toCharsString();
                    }
                    str2 = TextUtils.join(",", strArr);
                }
            } catch (RemoteException e) {
                Log.w(TAG, "Failed at ContainerConfigurationPolicy API addPackageToExternalStorageWhiteList", e);
                return false;
            }
        }
        return mUMContainerService.addPackageToExternalStorageWhiteList(this.mContextInfo, new AppIdentity(str, str2)) == 0;
    }

    public final boolean addPackageToInstallWhiteList(String str) {
        EnterpriseLicenseManager.log(this.mContextInfo, "ContainerConfigurationPolicy.addPackageToInstallWhiteList");
        IKnoxContainerManager mUMContainerService = getMUMContainerService();
        if (mUMContainerService == null) {
            Log.e(TAG, "KnoxMumContainer PolicyService is not yet ready!!!");
            return false;
        }
        try {
            return mUMContainerService.addPackageToInstallWhiteList(this.mContextInfo, new AppIdentity(str, (String) null)) == 0;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at ContainerConfigurationPolicy API addPackageToInstallWhiteList ", e);
            return false;
        }
    }

    public final boolean allowLayoutSwitching(boolean z) {
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("allowLayoutSwitching: allowSwitch ", z, TAG);
        EnterpriseLicenseManager.log(this.mContextInfo, "ContainerConfigurationPolicy.allowLayoutSwitching");
        IKnoxContainerManager mUMContainerService = getMUMContainerService();
        if (mUMContainerService == null) {
            Log.e(TAG, "PolicyService is not yet ready!!!");
            return false;
        }
        try {
            return mUMContainerService.allowLayoutSwitching(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "PolicyService API allowLayoutSwitching ", e);
            return false;
        }
    }

    public final boolean allowRemoteControl(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "ContainerConfigurationPolicy.allowRemoteControl");
        IRemoteInjection remoteControlService = getRemoteControlService();
        if (remoteControlService == null) {
            Log.e(TAG, "Remote Control Service is not yet ready");
            return false;
        }
        try {
            return remoteControlService.allowRemoteControl(this.mContextInfo, z, false);
        } catch (Exception e) {
            Log.w(TAG, "Failed at ContainerConfigurationPolicy API allowRemoteControl ", e);
            return false;
        }
    }

    public final boolean checkBluetoothAndNFCAPICallerPermission() {
        Log.i(TAG, "Bluetooth And NFC caller permission check");
        EdmConstants.EnterpriseKnoxSdkVersion enterpriseKnoxSdkVersion = EdmConstants.getEnterpriseKnoxSdkVersion();
        String str = TAG;
        StringBuilder sb = new StringBuilder("current version : ");
        sb.append(enterpriseKnoxSdkVersion);
        sb.append(", Required version : ");
        EdmConstants.EnterpriseKnoxSdkVersion enterpriseKnoxSdkVersion2 = EdmConstants.EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_2_4;
        sb.append(enterpriseKnoxSdkVersion2);
        Log.d(str, sb.toString());
        return enterpriseKnoxSdkVersion.compareTo(enterpriseKnoxSdkVersion2) >= 0;
    }

    public final boolean checkContactsSharingAPICallerPermission() {
        Log.i(TAG, "Knox Phone Book Access Profile permission check");
        EdmConstants.EnterpriseKnoxSdkVersion enterpriseKnoxSdkVersion = EdmConstants.getEnterpriseKnoxSdkVersion();
        String str = TAG;
        StringBuilder sb = new StringBuilder("current version : ");
        sb.append(enterpriseKnoxSdkVersion);
        sb.append(", Required version : ");
        EdmConstants.EnterpriseKnoxSdkVersion enterpriseKnoxSdkVersion2 = EdmConstants.EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_2_7;
        sb.append(enterpriseKnoxSdkVersion2);
        Log.d(str, sb.toString());
        return enterpriseKnoxSdkVersion.compareTo(enterpriseKnoxSdkVersion2) >= 0;
    }

    public final boolean checkExternalSDCardAPICallerPermission() {
        Log.i(TAG, "External SDCard API caller permission check");
        EdmConstants.EnterpriseKnoxSdkVersion enterpriseKnoxSdkVersion = EdmConstants.getEnterpriseKnoxSdkVersion();
        String str = TAG;
        StringBuilder sb = new StringBuilder("current version : ");
        sb.append(enterpriseKnoxSdkVersion);
        sb.append(", Required version : ");
        EdmConstants.EnterpriseKnoxSdkVersion enterpriseKnoxSdkVersion2 = EdmConstants.EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_2_2;
        sb.append(enterpriseKnoxSdkVersion2);
        Log.d(str, sb.toString());
        return enterpriseKnoxSdkVersion.compareTo(enterpriseKnoxSdkVersion2) >= 0;
    }

    public final boolean checkUsbHostModeAPICallerPermission() {
        Log.i(TAG, "Usb Host Mode permission check");
        EdmConstants.EnterpriseKnoxSdkVersion enterpriseKnoxSdkVersion = EdmConstants.getEnterpriseKnoxSdkVersion();
        String str = TAG;
        StringBuilder sb = new StringBuilder("current version : ");
        sb.append(enterpriseKnoxSdkVersion);
        sb.append(", Required version : ");
        EdmConstants.EnterpriseKnoxSdkVersion enterpriseKnoxSdkVersion2 = EdmConstants.EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_2_5;
        sb.append(enterpriseKnoxSdkVersion2);
        Log.d(str, sb.toString());
        return enterpriseKnoxSdkVersion.compareTo(enterpriseKnoxSdkVersion2) >= 0;
    }

    public final void clearCrossProfileIntentFilters(ComponentName componentName) {
        EnterpriseLicenseManager.log(this.mContextInfo, "ContainerConfigurationPolicy.clearCrossProfileIntentFilters");
        if (KnoxInternalFeature.KNOX_CONFIG_VERSION < 15 || getDPMService() == null) {
            return;
        }
        DependencyWrapper.clearCrossProfileIntentFiltersMDM(this.mDPMService, componentName, EdmUtils.getCallingUserId(this.mContextInfo));
    }

    public final boolean clearNetworkSSID() {
        EnterpriseLicenseManager.log(this.mContextInfo, "ContainerConfigurationPolicy.clearNetworkSSID");
        IKnoxContainerManager mUMContainerService = getMUMContainerService();
        if (mUMContainerService == null) {
            Log.e(TAG, "ContainerPolicy Service is not yet ready!!!");
            return false;
        }
        try {
            return mUMContainerService.clearNetworkSSID(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at ContainerConfigurationPolicy API clearNetworkSSID", e);
            return false;
        }
    }

    public final boolean clearPackagesFromExternalStorageSBABlackList() {
        EnterpriseLicenseManager.log(this.mContextInfo, "ContainerConfigurationPolicy.clearPackagesFromExternalStorageSBABlackList");
        if (!checkExternalSDCardAPICallerPermission()) {
            return false;
        }
        IKnoxContainerManager mUMContainerService = getMUMContainerService();
        if (mUMContainerService == null) {
            Log.e(TAG, "KnoxMumContainer PolicyService is not yet ready!!!");
            return false;
        }
        try {
            return mUMContainerService.clearPackagesFromExternalStorageBlackList(this.mContextInfo) == 0;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at ContainerConfigurationPolicy API clearPackagesFromExternalStorageSBABlackList", e);
            return false;
        }
    }

    public final boolean clearPackagesFromExternalStorageWhiteList() {
        EnterpriseLicenseManager.log(this.mContextInfo, "ContainerConfigurationPolicy.clearPackagesFromExternalStorageWhiteList");
        if (!checkExternalSDCardAPICallerPermission()) {
            return false;
        }
        IKnoxContainerManager mUMContainerService = getMUMContainerService();
        if (mUMContainerService == null) {
            Log.e(TAG, "KnoxMumContainer PolicyService is not yet ready!!!");
            return false;
        }
        try {
            return mUMContainerService.clearPackagesFromExternalStorageWhiteList(this.mContextInfo) == 0;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at ContainerConfigurationPolicy API clearPackagesFromExternalStorageWhiteList", e);
            return false;
        }
    }

    public final boolean deleteHomeShortcutFromPersonal(String str, String str2) {
        EnterpriseLicenseManager.log(this.mContextInfo, "ContainerConfigurationPolicy.deleteHomeShortcutFromPersonal");
        IKnoxContainerManager mUMContainerService = getMUMContainerService();
        if (mUMContainerService == null) {
            Log.e(TAG, "ContainerApplication PolicyService is not yet ready!!!");
            return false;
        }
        try {
            return mUMContainerService.deleteHomeShortcutFromPersonal(this.mContextInfo, str, str2);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at Application PolicyService API deleteHomeShortcutFromPersonal ", e);
            return false;
        }
    }

    public final boolean enableBluetooth(boolean z, Bundle bundle) {
        EnterpriseLicenseManager.log(this.mContextInfo, "ContainerConfigurationPolicy.enableBluetooth");
        if (!checkBluetoothAndNFCAPICallerPermission()) {
            return false;
        }
        IKnoxContainerManager mUMContainerService = getMUMContainerService();
        if (mUMContainerService == null) {
            Log.e(TAG, "ContainerPolicy Service is not yet ready!!!");
            return false;
        }
        try {
            return mUMContainerService.enableBluetooth(this.mContextInfo, z, bundle);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at ContainerConfigurationPolicy API enableBluetooth", e);
            return false;
        }
    }

    public final boolean enableExternalStorage(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "ContainerConfigurationPolicy.enableExternalStorage");
        if (!checkExternalSDCardAPICallerPermission()) {
            return false;
        }
        IKnoxContainerManager mUMContainerService = getMUMContainerService();
        if (mUMContainerService == null) {
            Log.e(TAG, "ContainerPolicy Service is not yet ready!!!");
            return false;
        }
        try {
            return mUMContainerService.enableExternalStorage(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at ContainerConfigurationPolicy API enableExternalStorage", e);
            return false;
        }
    }

    public final boolean enableNFC(boolean z, Bundle bundle) {
        EnterpriseLicenseManager.log(this.mContextInfo, "ContainerConfigurationPolicy.enableNFC");
        if (!checkBluetoothAndNFCAPICallerPermission()) {
            return false;
        }
        IKnoxContainerManager mUMContainerService = getMUMContainerService();
        if (mUMContainerService == null) {
            Log.e(TAG, "ContainerPolicy Service is not yet ready!!!");
            return false;
        }
        try {
            return mUMContainerService.enableNFC(this.mContextInfo, z, bundle);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at ContainerConfigurationPolicy API enableNFC", e);
            return false;
        }
    }

    public final boolean enableUsbAccess(boolean z, Bundle bundle) {
        EnterpriseLicenseManager.log(this.mContextInfo, "ContainerConfigurationPolicy.enableUsbHostMode");
        if (!checkUsbHostModeAPICallerPermission()) {
            return false;
        }
        IKnoxContainerManager mUMContainerService = getMUMContainerService();
        if (mUMContainerService == null) {
            Log.e(TAG, "ContainerPolicy Service is not yet ready!!!");
            return false;
        }
        try {
            return mUMContainerService.enableUsbAccess(this.mContextInfo, z, bundle);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at ContainerConfigurationPolicy API enableUsbAccess", e);
            return false;
        }
    }

    public final void enforceMultifactorAuthentication(boolean z) {
        boolean z2;
        EnterpriseLicenseManager.log(this.mContextInfo, "ContainerConfigurationPolicy.enforceMultifactorAuthentication");
        IKnoxContainerManager mUMContainerService = getMUMContainerService();
        if (mUMContainerService == null) {
            Log.e(TAG, "ContainerPolicy Service is not yet ready!!!");
            return;
        }
        try {
            z2 = mUMContainerService.enforceMultifactorAuthentication(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at KnoxContainerManager API unlock ", e);
            z2 = false;
        }
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("enforceMultifactorAuthentication result = ", z2, TAG);
    }

    public final IApplicationPolicy getAppService() {
        if (this.mAppService == null) {
            this.mAppService = IApplicationPolicy.Stub.asInterface(ServiceManager.getService("application_policy"));
        }
        return this.mAppService;
    }

    public final IDevicePolicyManager getDPMService() {
        if (this.mDPMService == null) {
            this.mDPMService = IDevicePolicyManager.Stub.asInterface(ServiceManager.getService("device_policy"));
        }
        return this.mDPMService;
    }

    public final boolean getEnforceAuthForContainer() {
        IKnoxContainerManager mUMContainerService = getMUMContainerService();
        if (mUMContainerService == null) {
            Log.e(TAG, "ContainerPolicy Service is not yet ready!!!");
            return false;
        }
        try {
            return mUMContainerService.getEnforceAuthForContainer(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at KnoxContainerManager API unlock ", e);
            return false;
        }
    }

    public final Bundle getFIDOInfo() {
        IKnoxContainerManager mUMContainerService = getMUMContainerService();
        if (mUMContainerService == null) {
            Log.e(TAG, "PolicyService is not yet ready!!!");
            return null;
        }
        try {
            return mUMContainerService.getFIDOInfo(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "PolicyService API getFIDOInfo ", e);
            Log.w(TAG, "PolicyService API getFIDOInfo ", e);
            return null;
        }
    }

    public final long getHibernationTimeout() {
        IKnoxContainerManager mUMContainerService = getMUMContainerService();
        if (mUMContainerService == null) {
            Log.e(TAG, "ContainerPolicy Service is not yet ready!!!");
            return 0L;
        }
        try {
            return mUMContainerService.getHibernationTimeout(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with ContainerPolicy ", e);
            return 0L;
        }
    }

    public final List<String> getNetworkSSID() {
        IKnoxContainerManager mUMContainerService = getMUMContainerService();
        if (mUMContainerService == null) {
            Log.e(TAG, "ContainerPolicy Service is not yet ready!!!");
            return new ArrayList(0);
        }
        try {
            return mUMContainerService.getNetworkSSID(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at ContainerConfigurationPolicy API getNetworkSSID", e);
            return new ArrayList(0);
        }
    }

    public final Signature[] getPackageSignaturesFromExternalStorageWhiteList(String str) {
        if (!checkExternalSDCardAPICallerPermission()) {
            return null;
        }
        IKnoxContainerManager mUMContainerService = getMUMContainerService();
        if (mUMContainerService == null) {
            Log.e(TAG, "KnoxMumContainer PolicyService is not yet ready!!!");
            return null;
        }
        try {
            return mUMContainerService.getPackageSignaturesFromExternalStorageWhiteList(this.mContextInfo, str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at ContainerConfigurationPolicy API getPackageSignaturesFromExternalStorageWhiteList", e);
            return null;
        }
    }

    public final List<String> getPackagesFromExternalStorageSBABlackList() {
        if (!checkExternalSDCardAPICallerPermission()) {
            return null;
        }
        IKnoxContainerManager mUMContainerService = getMUMContainerService();
        if (mUMContainerService == null) {
            Log.e(TAG, "KnoxMumContainer PolicyService is not yet ready!!!");
            return null;
        }
        try {
            return mUMContainerService.getPackagesFromExternalStorageBlackList(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at ContainerConfigurationPolicy API getPackagesFromExternalStorageSBABlackList", e);
            return null;
        }
    }

    public final List<String> getPackagesFromExternalStorageWhiteList() {
        if (!checkExternalSDCardAPICallerPermission()) {
            return null;
        }
        IKnoxContainerManager mUMContainerService = getMUMContainerService();
        if (mUMContainerService == null) {
            Log.e(TAG, "KnoxMumContainer PolicyService is not yet ready!!!");
            return null;
        }
        try {
            return mUMContainerService.getPackagesFromExternalStorageWhiteList(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at ContainerConfigurationPolicy API getPackagesFromExternalStorageWhiteList", e);
            return null;
        }
    }

    public final List<String> getPackagesFromInstallWhiteList() {
        IKnoxContainerManager mUMContainerService = getMUMContainerService();
        if (mUMContainerService == null) {
            Log.e(TAG, "KnoxMumContainer PolicyService is not yet ready!!!");
            return null;
        }
        try {
            return mUMContainerService.getPackagesFromInstallWhiteList(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at ContainerConfigurationPolicy API getPackagesFromInstallWhiteList ", e);
            return null;
        }
    }

    public final IRemoteInjection getRemoteControlService() {
        if (this.mRemoteControlService == null) {
            this.mRemoteControlService = IRemoteInjection.Stub.asInterface(ServiceManager.getService("remoteinjection"));
        }
        return this.mRemoteControlService;
    }

    public final boolean isBluetoothEnabled() {
        if (!checkBluetoothAndNFCAPICallerPermission()) {
            return false;
        }
        IKnoxContainerManager mUMContainerService = getMUMContainerService();
        if (mUMContainerService == null) {
            Log.e(TAG, "ContainerPolicy Service is not yet ready!!!");
            return false;
        }
        try {
            return mUMContainerService.isBluetoothEnabled(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at ContainerConfigurationPolicy API isBluetoothEnabled", e);
            return false;
        }
    }

    public final boolean isBluetoothEnabledBeforeFOTA() {
        if (!checkBluetoothAndNFCAPICallerPermission()) {
            return false;
        }
        IKnoxContainerManager mUMContainerService = getMUMContainerService();
        if (mUMContainerService == null) {
            Log.e(TAG, "ContainerPolicy Service is not yet ready!!!");
            return false;
        }
        try {
            return mUMContainerService.isBluetoothEnabledBeforeFOTA(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at ContainerConfigurationPolicy API isBluetoothEnabled", e);
            return false;
        }
    }

    public final boolean isContactsSharingEnabled() {
        if (!checkContactsSharingAPICallerPermission()) {
            return false;
        }
        IKnoxContainerManager mUMContainerService = getMUMContainerService();
        if (mUMContainerService == null) {
            Log.e(TAG, "ContainerPolicy Service is not yet ready!!!");
            return false;
        }
        try {
            return mUMContainerService.isContactsSharingEnabled(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at ContainerConfigurationPolicy API isContactsSharingEnabled", e);
            return false;
        }
    }

    public final boolean isExternalStorageEnabled() {
        if (!checkExternalSDCardAPICallerPermission()) {
            return false;
        }
        IKnoxContainerManager mUMContainerService = getMUMContainerService();
        if (mUMContainerService == null) {
            Log.e(TAG, "ContainerPolicy Service is not yet ready!!!");
            return false;
        }
        try {
            return mUMContainerService.isExternalStorageEnabled(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at ContainerConfigurationPolicy API enableExternalStorage", e);
            return false;
        }
    }

    public final boolean isLayoutSwitchingAllowed() {
        Log.d(TAG, "isLayoutSwitchingAllowed is called...");
        IKnoxContainerManager mUMContainerService = getMUMContainerService();
        if (mUMContainerService == null) {
            Log.e(TAG, "PolicyService is not yet ready!!!");
            return false;
        }
        try {
            return mUMContainerService.isLayoutSwitchingAllowed(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "PolicyService API isLayoutSwitchingAllowed ", e);
            return false;
        }
    }

    public final boolean isMultifactorAuthenticationEnforced() {
        IKnoxContainerManager mUMContainerService = getMUMContainerService();
        if (mUMContainerService == null) {
            Log.e(TAG, "ContainerPolicy Service is not yet ready!!!");
            return false;
        }
        try {
            return mUMContainerService.isMultifactorAuthenticationEnforced(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at KnoxContainerManager API unlock ", e);
            return false;
        }
    }

    public final boolean isNFCEnabled() {
        if (!checkBluetoothAndNFCAPICallerPermission()) {
            return false;
        }
        IKnoxContainerManager mUMContainerService = getMUMContainerService();
        if (mUMContainerService == null) {
            Log.e(TAG, "ContainerPolicy Service is not yet ready!!!");
            return false;
        }
        try {
            return mUMContainerService.isNFCEnabled(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at ContainerConfigurationPolicy API isNFCEnabled", e);
            return false;
        }
    }

    public final boolean isPackageInInstallWhiteList(String str) {
        IKnoxContainerManager mUMContainerService = getMUMContainerService();
        if (mUMContainerService == null) {
            Log.e(TAG, "KnoxMumContainer PolicyService is not yet ready!!!");
            return false;
        }
        try {
            return mUMContainerService.isPackageInInstallWhiteList(this.mContextInfo, str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at ContainerConfigurationPolicy API isPackageInInstallWhiteList ", e);
            return false;
        }
    }

    public final boolean isRemoteControlAllowed() {
        IRemoteInjection remoteControlService = getRemoteControlService();
        if (remoteControlService == null) {
            Log.e(TAG, "Remote Control Service is not yet ready");
            return false;
        }
        try {
            return remoteControlService.isRemoteControlAllowed(this.mContextInfo);
        } catch (Exception e) {
            Log.w(TAG, "Failed at ContainerConfigurationPolicy API isRemoteControlAllowed ", e);
            return false;
        }
    }

    public final boolean isResetContainerOnRebootEnabled() {
        IKnoxContainerManager mUMContainerService = getMUMContainerService();
        if (mUMContainerService == null) {
            Log.e(TAG, "ContainerPolicy Service is not yet ready!!!");
            return false;
        }
        try {
            return mUMContainerService.isResetContainerOnRebootEnabled(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with ContainerConfigurationPolicy ", e);
            return false;
        }
    }

    public final boolean isSettingsOptionEnabled(String str) {
        IKnoxContainerManager mUMContainerService = getMUMContainerService();
        if (mUMContainerService == null) {
            Log.e(TAG, "ContainerPolicy Service is not yet ready!!!");
            return false;
        }
        try {
            return mUMContainerService.isSettingsOptionEnabled(this.mContextInfo, str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at ContainerConfigurationPolicy API isSettingsOptionEnabled", e);
            return false;
        }
    }

    public final boolean isUsbAccessEnabled() {
        if (!checkUsbHostModeAPICallerPermission()) {
            return false;
        }
        IKnoxContainerManager mUMContainerService = getMUMContainerService();
        if (mUMContainerService == null) {
            Log.e(TAG, "ContainerPolicy Service is not yet ready!!!");
            return false;
        }
        try {
            return mUMContainerService.isUsbAccessEnabled(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at ContainerConfigurationPolicy API isUsbAccessEnabled", e);
            return false;
        }
    }

    public final boolean isUseSecureKeypadEnabled() {
        IRestrictionPolicy restrictionService = getRestrictionService();
        if (restrictionService == null) {
            Log.e(TAG, "ContainerRestriction PolicyService is not yet ready!!!");
            return false;
        }
        try {
            return restrictionService.isUseSecureKeypadEnabled(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with misc policy", e);
            return false;
        }
    }

    public final boolean removeNetworkSSID(String str) {
        EnterpriseLicenseManager.log(this.mContextInfo, "ContainerConfigurationPolicy.removeNetworkSSID");
        IKnoxContainerManager mUMContainerService = getMUMContainerService();
        if (str != null && !str.isEmpty()) {
            if (mUMContainerService == null) {
                Log.e(TAG, "ContainerPolicy Service is not yet ready!!!");
                return false;
            }
            try {
                return mUMContainerService.removeNetworkSSID(this.mContextInfo, str);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed at ContainerConfigurationPolicy API removeNetworkSSID", e);
            }
        }
        return false;
    }

    public final boolean removePackageFromExternalStorageSBABlackList(String str) {
        EnterpriseLicenseManager.log(this.mContextInfo, "ContainerConfigurationPolicy.removePackageFromExternalStorageSBABlackList");
        if (!checkExternalSDCardAPICallerPermission()) {
            return false;
        }
        IKnoxContainerManager mUMContainerService = getMUMContainerService();
        if (mUMContainerService == null) {
            Log.e(TAG, "KnoxMumContainer PolicyService is not yet ready!!!");
            return false;
        }
        try {
            return mUMContainerService.removePackageFromExternalStorageBlackList(this.mContextInfo, new AppIdentity(str, (String) null)) == 0;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at ContainerConfigurationPolicy API removePackageFromExternalStorageSBABlackList", e);
            return false;
        }
    }

    public final boolean removePackageFromExternalStorageWhiteList(String str) {
        EnterpriseLicenseManager.log(this.mContextInfo, "ContainerConfigurationPolicy.removePackageFromExternalStorageWhiteList");
        if (!checkExternalSDCardAPICallerPermission()) {
            return false;
        }
        IKnoxContainerManager mUMContainerService = getMUMContainerService();
        if (mUMContainerService == null) {
            Log.e(TAG, "KnoxMumContainer PolicyService is not yet ready!!!");
            return false;
        }
        try {
            return mUMContainerService.removePackageFromExternalStorageWhiteList(this.mContextInfo, new AppIdentity(str, (String) null)) == 0;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at ContainerConfigurationPolicy API removePackageFromExternalStorageWhiteList", e);
            return false;
        }
    }

    public final boolean removePackageFromInstallWhiteList(String str) {
        EnterpriseLicenseManager.log(this.mContextInfo, "ContainerConfigurationPolicy.removePackageFromInstallWhiteList");
        IKnoxContainerManager mUMContainerService = getMUMContainerService();
        if (mUMContainerService == null) {
            Log.e(TAG, "KnoxMumContainer PolicyService is not yet ready!!!");
            return false;
        }
        try {
            return mUMContainerService.removePackageFromInstallWhiteList(this.mContextInfo, new AppIdentity(str, (String) null)) == 0;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at ContainerConfigurationPolicy API removePackageFromInstallWhiteList ", e);
            return false;
        }
    }

    public final boolean resetContainerOnReboot(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "ContainerConfigurationPolicy.resetContainerOnReboot");
        IKnoxContainerManager mUMContainerService = getMUMContainerService();
        if (mUMContainerService == null) {
            Log.e(TAG, "ContainerPolicy Service is not yet ready!!!");
            return false;
        }
        try {
            return mUMContainerService.resetContainerOnReboot(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with ContainerConfigurationPolicy ", e);
            return false;
        }
    }

    public final boolean resetContainerPassword() {
        int i;
        EnterpriseLicenseManager.log(this.mContextInfo, "ContainerConfigurationPolicy.resetPassword");
        IKnoxContainerManager mUMContainerService = getMUMContainerService();
        if (mUMContainerService == null) {
            Log.e(TAG, "ContainerPolicy Service is not yet ready!!!");
            return false;
        }
        try {
            i = mUMContainerService.forceResetPassword(this.mContextInfo, null, 0);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with ContainerConfigurationPolicy ", e);
            i = -2;
        }
        return i >= 0;
    }

    public final boolean setContactsSharingEnabled(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "ContainerConfigurationPolicy.setContactsSharingEnabled");
        if (!checkContactsSharingAPICallerPermission()) {
            return false;
        }
        IKnoxContainerManager mUMContainerService = getMUMContainerService();
        if (mUMContainerService == null) {
            Log.e(TAG, "ContainerPolicy Service is not yet ready!!!");
            return false;
        }
        try {
            return mUMContainerService.setContactsSharingEnabled(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at ContainerConfigurationPolicy API setContactsSharingEnabled", e);
            return false;
        }
    }

    public final int setCustomResource(int i, Bundle bundle) {
        Log.d(TAG, "setCustomResource is called...");
        EnterpriseLicenseManager.log(this.mContextInfo, "ContainerConfigurationPolicy.setCustomResource");
        IKnoxContainerManager mUMContainerService = getMUMContainerService();
        if (mUMContainerService == null) {
            Log.e(TAG, "PolicyService is not yet ready!!!");
            return -2;
        }
        try {
            return mUMContainerService.setCustomResource(i, this.mContextInfo, bundle);
        } catch (RemoteException e) {
            Log.w(TAG, "PolicyService API setCustomResource ", e);
            return -2;
        }
    }

    public final boolean setEnforceAuthForContainer(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "ContainerConfigurationPolicy.setEnforceAuthForContainer");
        IKnoxContainerManager mUMContainerService = getMUMContainerService();
        if (mUMContainerService == null) {
            Log.e(TAG, "ContainerPolicy Service is not yet ready!!!");
            return false;
        }
        try {
            return mUMContainerService.setEnforceAuthForContainer(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at KnoxContainerManager API unlock ", e);
            return false;
        }
    }

    public final boolean setFIDOInfo(Bundle bundle) {
        EnterpriseLicenseManager.log(this.mContextInfo, "ContainerConfigurationPolicy.setFIDOInfo");
        IKnoxContainerManager mUMContainerService = getMUMContainerService();
        if (mUMContainerService == null) {
            Log.e(TAG, "PolicyService is not yet ready!!!");
            return false;
        }
        try {
            return mUMContainerService.setFIDOInfo(this.mContextInfo, bundle);
        } catch (RemoteException e) {
            Log.w(TAG, "PolicyService API setFIDOSinfo ", e);
            return false;
        }
    }

    public final boolean setHibernationTimeout(long j) {
        EnterpriseLicenseManager.log(this.mContextInfo, "ContainerConfigurationPolicy.setHibernationTimeout");
        if (j <= 0 || j > 86400000) {
            return false;
        }
        IKnoxContainerManager mUMContainerService = getMUMContainerService();
        if (mUMContainerService == null) {
            Log.e(TAG, "ContainerPolicy Service is not yet ready!!!");
            return false;
        }
        try {
            return mUMContainerService.setHibernationTimeout(this.mContextInfo, j);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with ContainerPolicy ", e);
            return false;
        }
    }

    public final boolean setSettingsOptionEnabled(String str, boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "ContainerConfigurationPolicy.setSettingsOptionEnabled");
        IKnoxContainerManager mUMContainerService = getMUMContainerService();
        if (mUMContainerService == null) {
            Log.e(TAG, "ContainerPolicy Service is not yet ready!!!");
            return false;
        }
        try {
            return mUMContainerService.setSettingsOptionEnabled(this.mContextInfo, str, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at ContainerConfigurationPolicy API setSettingsOptionEnabled", e);
            return false;
        }
    }

    public final boolean setUseSecureKeypad(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "ContainerConfigurationPolicy.setUseSecureKeypad");
        IRestrictionPolicy restrictionService = getRestrictionService();
        if (restrictionService == null) {
            Log.e(TAG, "ContainerRestriction PolicyService is not yet ready!!!");
            return false;
        }
        try {
            return restrictionService.setUseSecureKeypad(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with misc policy", e);
            return false;
        }
    }

    public final int resetContainerPassword(String str, int i) {
        EnterpriseLicenseManager.log(this.mContextInfo, "ContainerConfigurationPolicy.resetPassword");
        IKnoxContainerManager mUMContainerService = getMUMContainerService();
        if (mUMContainerService == null) {
            Log.e(TAG, "ContainerPolicy Service is not yet ready!!!");
            return -2;
        }
        try {
            return mUMContainerService.forceResetPassword(this.mContextInfo, str, i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with ContainerConfigurationPolicy ", e);
            return -2;
        }
    }
}
