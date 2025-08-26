package com.samsung.android.knox.internal;

import android.app.admin.IDevicePolicyManager;
import android.content.ComponentName;
import android.os.Binder;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.sec.enterprise.auditlog.AuditLog;
import android.util.Log;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.IEnterpriseDeviceManager;
import com.samsung.android.knox.application.IApplicationPolicy;
import com.samsung.android.knox.bluetooth.BluetoothPolicy;
import com.samsung.android.knox.bluetooth.IBluetoothPolicy;
import com.samsung.android.knox.license.EnterpriseLicenseManager;
import com.samsung.android.knox.log.IAuditLog;
import com.samsung.android.knox.remotecontrol.IRemoteInjection;
import com.samsung.android.knox.restriction.IRestrictionPolicy;

/* loaded from: classes4.dex */
public final class EDMNativeHelper {
    public static String TAG = "EDMNativeHelper";

    public static boolean enforceDoPoOrAuthorizedApp(int i, String str) {
        Log.d(TAG, "enforceDoPoOrAuthorizedApp(" + i + ")");
        IEnterpriseDeviceManager iEnterpriseDeviceManagerAsInterface = IEnterpriseDeviceManager.Stub.asInterface(ServiceManager.getService("enterprise_policy"));
        if (iEnterpriseDeviceManagerAsInterface == null) {
            Log.d(TAG, "EDM Service is null!");
            return false;
        }
        try {
            iEnterpriseDeviceManagerAsInterface.enforceCaller(null, str);
            Log.d(TAG, "Caller is able!");
            return true;
        } catch (RemoteException | SecurityException e) {
            Log.d(TAG, "Caller not authorized " + e.getMessage());
            return false;
        }
    }

    public static void enterpriseLogger(String str) {
        EnterpriseLicenseManager.log(new ContextInfo(Process.myUid()), str);
    }

    public static boolean isAVRCPProfileEnabled() {
        IBluetoothPolicy iBluetoothPolicyAsInterface = IBluetoothPolicy.Stub.asInterface(ServiceManager.getService("bluetooth_policy"));
        if (iBluetoothPolicyAsInterface != null) {
            try {
                return iBluetoothPolicyAsInterface.isProfileEnabledInternal(16, true);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public static boolean isAudioRecordAllowed(int i) {
        IRestrictionPolicy iRestrictionPolicyAsInterface = IRestrictionPolicy.Stub.asInterface(ServiceManager.getService("restriction_policy"));
        if (iRestrictionPolicyAsInterface != null) {
            try {
                return iRestrictionPolicyAsInterface.isAudioRecordAllowed(new ContextInfo(i), true);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public static boolean isAuditLogEnabled() {
        IAuditLog iAuditLogAsInterface = IAuditLog.Stub.asInterface(ServiceManager.getService("auditlog"));
        if (iAuditLogAsInterface == null) {
            return false;
        }
        try {
            return iAuditLogAsInterface.isAuditServiceRunning();
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isBTOutgoingCallEnabled() {
        IBluetoothPolicy iBluetoothPolicyAsInterface = IBluetoothPolicy.Stub.asInterface(ServiceManager.getService("bluetooth_policy"));
        if (iBluetoothPolicyAsInterface == null) {
            return true;
        }
        try {
            return iBluetoothPolicyAsInterface.isOutgoingCallsAllowed(new ContextInfo());
        } catch (RemoteException e) {
            e.printStackTrace();
            return true;
        }
    }

    public static boolean isCameraEnabled(int i) {
        boolean zIsCameraEnabledNative;
        boolean z;
        Log.d(TAG, "isCameraEnabled");
        IEnterpriseDeviceManager iEnterpriseDeviceManagerAsInterface = IEnterpriseDeviceManager.Stub.asInterface(ServiceManager.getService("enterprise_policy"));
        if (iEnterpriseDeviceManagerAsInterface != null) {
            try {
                Log.d(TAG, "checking for camera in EnterpriseDeviceManagerService");
                zIsCameraEnabledNative = iEnterpriseDeviceManagerAsInterface.isCameraEnabledNative(new ContextInfo(i));
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (SecurityException e2) {
                e2.printStackTrace();
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        } else {
            zIsCameraEnabledNative = true;
        }
        IDevicePolicyManager iDevicePolicyManagerAsInterface = IDevicePolicyManager.Stub.asInterface(ServiceManager.getService("device_policy"));
        if (iDevicePolicyManagerAsInterface != null) {
            try {
                z = !iDevicePolicyManagerAsInterface.getCameraDisabled((ComponentName) null, (String) null, UserHandle.getUserId(i), false);
            } catch (RemoteException e4) {
                e4.printStackTrace();
            } catch (SecurityException e5) {
                e5.printStackTrace();
            } catch (Exception e6) {
                e6.printStackTrace();
            }
        } else {
            z = true;
        }
        return zIsCameraEnabledNative && z;
    }

    public static boolean isFaceRecognitionAllowedEvenCameraBlocked(int i) {
        IRestrictionPolicy iRestrictionPolicyAsInterface = IRestrictionPolicy.Stub.asInterface(ServiceManager.getService("restriction_policy"));
        if (iRestrictionPolicyAsInterface == null) {
            return true;
        }
        try {
            return iRestrictionPolicyAsInterface.isFaceRecognitionAllowedEvenCameraBlocked(new ContextInfo(i));
        } catch (RemoteException e) {
            e.printStackTrace();
            return true;
        }
    }

    public static boolean isHIDProfileEnabled() {
        IBluetoothPolicy iBluetoothPolicyAsInterface = IBluetoothPolicy.Stub.asInterface(ServiceManager.getService("bluetooth_policy"));
        if (iBluetoothPolicyAsInterface == null) {
            return true;
        }
        try {
            return iBluetoothPolicyAsInterface.isBluetoothUUIDAllowed(new ContextInfo(), BluetoothPolicy.BluetoothUUID.HID_UUID);
        } catch (RemoteException e) {
            e.printStackTrace();
            return true;
        }
    }

    public static boolean isIrisCameraEnabled(int i) {
        IRestrictionPolicy iRestrictionPolicyAsInterface = IRestrictionPolicy.Stub.asInterface(ServiceManager.getService("restriction_policy"));
        if (iRestrictionPolicyAsInterface != null) {
            try {
                return iRestrictionPolicyAsInterface.isIrisCameraEnabled(new ContextInfo(i), true);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public static boolean isMicrophoneEnabled(int i) {
        IRestrictionPolicy iRestrictionPolicyAsInterface = IRestrictionPolicy.Stub.asInterface(ServiceManager.getService("restriction_policy"));
        if (iRestrictionPolicyAsInterface != null) {
            try {
                return iRestrictionPolicyAsInterface.isMicrophoneEnabled(new ContextInfo(i), true);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public static boolean isPackageInAvrWhitelist(int i) {
        IApplicationPolicy iApplicationPolicyAsInterface = IApplicationPolicy.Stub.asInterface(ServiceManager.getService("application_policy"));
        if (iApplicationPolicyAsInterface == null) {
            return false;
        }
        try {
            return iApplicationPolicyAsInterface.isPackageInWhitelistInternal(3, UserHandle.getCallingUserId(), i);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public static boolean isScreenCaptureEnabled() {
        IRestrictionPolicy iRestrictionPolicyAsInterface = IRestrictionPolicy.Stub.asInterface(ServiceManager.getService("restriction_policy"));
        if (iRestrictionPolicyAsInterface != null) {
            try {
                return iRestrictionPolicyAsInterface.isScreenCaptureEnabledInternal(true);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public static boolean isVideoRecordAllowed(int i) {
        IRestrictionPolicy iRestrictionPolicyAsInterface = IRestrictionPolicy.Stub.asInterface(ServiceManager.getService("restriction_policy"));
        if (iRestrictionPolicyAsInterface != null) {
            try {
                return iRestrictionPolicyAsInterface.isVideoRecordAllowed(new ContextInfo(i), true);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public static void nativeLoggerEvent(int i) {
        long jClearCallingIdentity = Binder.clearCallingIdentity();
        try {
            AuditLog.logEvent(i, new Object[0]);
        } catch (Exception e) {
            Log.e(TAG, "Failed to log audit event", e);
        } finally {
            Binder.restoreCallingIdentity(jClearCallingIdentity);
        }
    }

    public static void nativeLoggerEventAsUser(int i, int i2, Object... objArr) {
        long jClearCallingIdentity = Binder.clearCallingIdentity();
        try {
            AuditLog.logEventAsUser(i, i2, objArr);
        } catch (Exception e) {
            Log.e(TAG, "Failed to log audit event", e);
        } finally {
            Binder.restoreCallingIdentity(jClearCallingIdentity);
        }
    }

    public static void sendIntent(int i) {
        IEnterpriseDeviceManager iEnterpriseDeviceManagerAsInterface = IEnterpriseDeviceManager.Stub.asInterface(ServiceManager.getService("enterprise_policy"));
        if (iEnterpriseDeviceManagerAsInterface != null) {
            try {
                iEnterpriseDeviceManagerAsInterface.sendIntent(i);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public static void updateRemoteScreenDimensionsAndCallerUid(int i, int i2, int i3) {
        IRemoteInjection iRemoteInjectionAsInterface = IRemoteInjection.Stub.asInterface(ServiceManager.getService("remoteinjection"));
        if (iRemoteInjectionAsInterface != null) {
            try {
                iRemoteInjectionAsInterface.updateRemoteScreenDimensionsAndCallerUid(i, i2, i3);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
