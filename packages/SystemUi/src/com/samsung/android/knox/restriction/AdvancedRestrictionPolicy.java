package com.samsung.android.knox.restriction;

import android.content.Context;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import androidx.picker.adapter.AbsAdapter$1$$ExternalSyntheticOutline0;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.bluetooth.IBluetoothPolicy;
import com.samsung.android.knox.license.EnterpriseLicenseManager;
import com.samsung.android.knox.multiuser.MultiUserManager$$ExternalSyntheticOutline0;
import com.samsung.android.knox.net.vpn.IVpnInfoPolicy;
import com.samsung.android.knox.net.wifi.IWifiPolicy;
import com.samsung.android.knox.remotecontrol.IRemoteInjection;
import com.samsung.android.knox.restriction.IRestrictionPolicy;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class AdvancedRestrictionPolicy {
    public static final int CCMODE_STATE_DISABLED = 1;
    public static final int CCMODE_STATE_ENABLED = 4;
    public static final int CCMODE_STATE_ENFORCING = 3;
    public static final int CCMODE_STATE_NONE = 0;
    public static final int CCMODE_STATE_NOT_SUPPORTED = -1;
    public static final int CCMODE_STATE_READY = 2;
    public static final int CONSTRAINED_STATE_DISABLED = 0;
    public static final int CONSTRAINED_STATE_ENABLED_AND_DEVICE_CONSTRAINED = 2;
    public static final int CONSTRAINED_STATE_ENABLED_BUT_DEVICE_NOT_CONSTRAINED = 1;
    public static final int CONSTRAINED_STATE_RESTRICT_BLUETOOTH = 8;
    public static final int CONSTRAINED_STATE_RESTRICT_CAMERA = 1;
    public static final int CONSTRAINED_STATE_RESTRICT_EXTERNAL_SDCARD = 2;
    public static final int CONSTRAINED_STATE_RESTRICT_MTP = 4;
    public static final int CONSTRAINED_STATE_RESTRICT_SCREEN_CAPTURE = 64;
    public static final int CONSTRAINED_STATE_RESTRICT_TETHERING = 16;
    public static final int CONSTRAINED_STATE_RESTRICT_USB_DEBUGGING = 32;
    public static String TAG = "AdvancedRestrictionPolicy";
    public IVpnInfoPolicy lVpnService;
    public IBluetoothPolicy mBluetoothPolicyService;
    public Context mContext;
    public ContextInfo mContextInfo;
    public IRemoteInjection mRemoteControlService;
    public IRestrictionPolicy mService;
    public IWifiPolicy mWifiPolicyService;

    public AdvancedRestrictionPolicy(ContextInfo contextInfo, Context context) {
        this.mContextInfo = contextInfo;
        this.mContext = context;
    }

    public final boolean allowBLE(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "AdvancedRestrictionPolicy.allowBLE");
        if (getBluetoothPolicyService() == null) {
            return false;
        }
        try {
            return getBluetoothPolicyService().allowBLE(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with bluetooth policy", e);
            return false;
        }
    }

    public final boolean allowFaceRecognitionEvenCameraBlocked(boolean z) {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.allowFaceRecognitionEvenCameraBlocked(this.mContextInfo, z);
        } catch (Exception e) {
            Log.w(TAG, "Failed talking with restriction policy", e);
            return false;
        }
    }

    public final boolean allowFirmwareAutoUpdate(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "AdvancedRestrictionPolicy.allowFirmwareAutoUpdate");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.allowFirmwareAutoUpdate(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with restriction policy", e);
            return false;
        }
    }

    public final boolean allowIntelligenceOnlineProcessing(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "AdvancedRestrictionPolicy.allowIntelligenceOnlineProcessing");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.allowIntelligenceOnlineProcessing(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with restriction policy", e);
            return false;
        }
    }

    public final boolean allowLocalContactStorage(boolean z) {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.allowLocalContactStorage(this.mContextInfo, z);
        } catch (Exception e) {
            Log.w(TAG, "Failed talking with restriction policy", e);
            return false;
        }
    }

    public final boolean allowOnlySecureConnections(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "AdvancedRestrictionPolicy.allowOnlySecureConnections");
        try {
            if (getVpnService() != null) {
                return this.lVpnService.allowOnlySecureConnections(this.mContextInfo, z);
            }
            return false;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at advanced restriction policy API allowOnlySecureConnections ", e);
            return false;
        }
    }

    public final boolean allowRemoteControl(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "AdvancedRestrictionPolicy.allowRemoteControl");
        IRemoteInjection remoteControlService = getRemoteControlService();
        if (remoteControlService == null) {
            Log.e(TAG, "Remote Control Service is not yet ready");
            return false;
        }
        try {
            return remoteControlService.allowRemoteControl(this.mContextInfo, z, true);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at AdvancedRestrictionPolicy API allowRemoteControl ", e);
            return false;
        }
    }

    public final boolean allowUserSetAlwaysOn(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "AdvancedRestrictionPolicy.allowUserSetAlwaysOn", true);
        try {
            if (getVpnService() != null) {
                return this.lVpnService.allowUserSetAlwaysOn(this.mContextInfo, z);
            }
            return false;
        } catch (RemoteException e) {
            MultiUserManager$$ExternalSyntheticOutline0.m259m(e, new StringBuilder("Failed to communicate with advanced restriction policy "), TAG);
            return false;
        }
    }

    public final boolean allowWifiScanning(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "AdvancedRestrictionPolicy.allowWifiScanning");
        if (getWifiPolicyService() == null) {
            return false;
        }
        try {
            return getWifiPolicyService().allowWifiScanning(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with wifi policy", e);
            return false;
        }
    }

    public final boolean disableConstrainedState() {
        EnterpriseLicenseManager.log(this.mContextInfo, "AdvancedRestrictionPolicy.disableConstrainedState");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.disableConstrainedState(this.mContextInfo);
        } catch (Exception e) {
            AbsAdapter$1$$ExternalSyntheticOutline0.m39m("exception occured! ", e, TAG);
            return false;
        }
    }

    public final boolean enableConstrainedState(String str, int i) {
        return enableConstrainedState(str, null, null, null, i);
    }

    public final boolean enableODETrustedBootVerification(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "AdvancedRestrictionPolicy.enableODETrustedBootVerification");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.enableODETrustedBootVerification(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with restriction policy", e);
            return false;
        }
    }

    public final IBluetoothPolicy getBluetoothPolicyService() {
        if (this.mBluetoothPolicyService == null) {
            this.mBluetoothPolicyService = IBluetoothPolicy.Stub.asInterface(ServiceManager.getService("bluetooth_policy"));
        }
        return this.mBluetoothPolicyService;
    }

    public final int getCCModeState() {
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.getCCModeState(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with restriction policy", e);
            return -1;
        }
    }

    public final int getConstrainedState() {
        if (getService() == null) {
            return 0;
        }
        try {
            return this.mService.getConstrainedState();
        } catch (Exception e) {
            AbsAdapter$1$$ExternalSyntheticOutline0.m39m("exception occured! ", e, TAG);
            return 0;
        }
    }

    public final IRemoteInjection getRemoteControlService() {
        if (this.mRemoteControlService == null) {
            this.mRemoteControlService = IRemoteInjection.Stub.asInterface(ServiceManager.getService("remoteinjection"));
        }
        return this.mRemoteControlService;
    }

    public final IRestrictionPolicy getService() {
        if (this.mService == null) {
            this.mService = IRestrictionPolicy.Stub.asInterface(ServiceManager.getService("restriction_policy"));
        }
        return this.mService;
    }

    public final IVpnInfoPolicy getVpnService() {
        if (this.lVpnService == null) {
            this.lVpnService = IVpnInfoPolicy.Stub.asInterface(ServiceManager.getService("vpn_policy"));
        }
        return this.lVpnService;
    }

    public final IWifiPolicy getWifiPolicyService() {
        if (this.mWifiPolicyService == null) {
            this.mWifiPolicyService = IWifiPolicy.Stub.asInterface(ServiceManager.getService("wifi_policy"));
        }
        return this.mWifiPolicyService;
    }

    public final boolean isBLEAllowed() {
        if (getBluetoothPolicyService() == null) {
            return true;
        }
        try {
            return getBluetoothPolicyService().isBLEAllowed(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with bluetooth policy", e);
            return true;
        }
    }

    public final boolean isFaceRecognitionAllowedEvenCameraBlocked() {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.isFaceRecognitionAllowedEvenCameraBlocked(this.mContextInfo);
        } catch (Exception e) {
            Log.w(TAG, "Failed talking with restriction policy", e);
            return false;
        }
    }

    public final boolean isFirmwareAutoUpdateAllowed(boolean z) {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.isFirmwareAutoUpdateAllowed(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with restriction policy", e);
            return false;
        }
    }

    public final boolean isIntelligenceOnlineProcessingAllowed() {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isIntelligenceOnlineProcessingAllowed(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with restriction policy", e);
            return true;
        }
    }

    public final boolean isKnoxDelegationEnabled() {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.isKnoxDelegationEnabled(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with misc policy", e);
            return false;
        }
    }

    public final boolean isLocalContactStorageAllowed() {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.isLocalContactStorageAllowed(this.mContextInfo);
        } catch (Exception e) {
            Log.w(TAG, "Failed talking with restriction policy", e);
            return false;
        }
    }

    public final boolean isODETrustedBootVerificationEnabled() {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.isODETrustedBootVerificationEnabled(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with restriction policy", e);
            return false;
        }
    }

    public final boolean isOnlySecureConnectionsAllowed() {
        try {
            if (getVpnService() != null) {
                return this.lVpnService.isOnlySecureConnectionsAllowed(this.mContextInfo);
            }
            return false;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at advanced restriction policy API isOnlySecureConnectionsAllowed ", e);
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
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at AdvancedRestrictionPolicy API isRemoteControlAllowed ", e);
            return false;
        }
    }

    public final boolean isUserSetAlwaysOnAllowed() {
        try {
            if (getVpnService() != null) {
                return this.lVpnService.isUserSetAlwaysOnAllowed(this.mContextInfo, false);
            }
            return true;
        } catch (RemoteException e) {
            MultiUserManager$$ExternalSyntheticOutline0.m259m(e, new StringBuilder("Failed to communicate with advanced restriction policy "), TAG);
            return true;
        }
    }

    public final boolean isWifiScanningAllowed() {
        if (getWifiPolicyService() == null) {
            return true;
        }
        try {
            return getWifiPolicyService().isWifiScanningAllowed(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with wifi policy", e);
            return true;
        }
    }

    public final boolean setCCMode(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "AdvancedRestrictionPolicy.setCCMode");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setCCMode(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with restriction policy", e);
            return false;
        }
    }

    public final boolean setKnoxDelegationEnabled(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "AdvancedRestrictionPolicy.setKnoxDelegationEnabled");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setKnoxDelegationEnabled(this.mContextInfo, z);
        } catch (Exception e) {
            Log.w(TAG, "Failed talking with restriction policy", e);
            return false;
        }
    }

    public final boolean enableConstrainedState(String str, String str2, int i) {
        EnterpriseLicenseManager.log(this.mContextInfo, "AdvancedRestrictionPolicy.enableConstrainedState");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.enableConstrainedState(this.mContextInfo, str, str2, null, null, i);
        } catch (Exception e) {
            AbsAdapter$1$$ExternalSyntheticOutline0.m39m("exception occured! ", e, TAG);
            return false;
        }
    }

    public final boolean enableConstrainedState(String str, String str2, String str3, String str4, int i) {
        EnterpriseLicenseManager.log(this.mContextInfo, "AdvancedRestrictionPolicy.enableConstrainedState");
        if ((str3 == null || str3.isEmpty() || (str4 != null && !str4.isEmpty())) && getService() != null) {
            try {
                return this.mService.enableConstrainedState(this.mContextInfo, str, str2, str3, str4, i);
            } catch (Exception e) {
                AbsAdapter$1$$ExternalSyntheticOutline0.m39m("exception occured! ", e, TAG);
            }
        }
        return false;
    }
}
