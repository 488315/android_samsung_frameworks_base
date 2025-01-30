package com.samsung.android.knox.internal;

import android.app.admin.IDevicePolicyManager;
import android.content.ComponentName;
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
import java.io.UnsupportedEncodingException;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class EDMNativeHelper {
    public static String TAG = "EDMNativeHelper";

    public static void enterpriseLogger(String str) {
        EnterpriseLicenseManager.log(new ContextInfo(Process.myUid()), str);
    }

    public static boolean isAVRCPProfileEnabled() {
        IBluetoothPolicy asInterface = IBluetoothPolicy.Stub.asInterface(ServiceManager.getService("bluetooth_policy"));
        if (asInterface != null) {
            try {
                return asInterface.isProfileEnabledInternal(16, true);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public static boolean isAudioRecordAllowed(int i) {
        IRestrictionPolicy asInterface = IRestrictionPolicy.Stub.asInterface(ServiceManager.getService("restriction_policy"));
        if (asInterface != null) {
            try {
                return asInterface.isAudioRecordAllowed(new ContextInfo(i), true);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public static boolean isAuditLogEnabled() {
        IAuditLog asInterface = IAuditLog.Stub.asInterface(ServiceManager.getService("auditlog"));
        if (asInterface == null) {
            return false;
        }
        try {
            return asInterface.isAuditServiceRunning();
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isBTOutgoingCallEnabled() {
        IBluetoothPolicy asInterface = IBluetoothPolicy.Stub.asInterface(ServiceManager.getService("bluetooth_policy"));
        if (asInterface == null) {
            return true;
        }
        try {
            return asInterface.isOutgoingCallsAllowed(new ContextInfo());
        } catch (RemoteException e) {
            e.printStackTrace();
            return true;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0041 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:8:0x005d A[ADDED_TO_REGION] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean isCameraEnabled(int i) {
        boolean isCameraEnabledNative;
        IDevicePolicyManager asInterface;
        boolean z;
        Log.d(TAG, "isCameraEnabled");
        IEnterpriseDeviceManager asInterface2 = IEnterpriseDeviceManager.Stub.asInterface(ServiceManager.getService("enterprise_policy"));
        if (asInterface2 != null) {
            try {
                Log.d(TAG, "checking for camera in EnterpriseDeviceManagerService");
                isCameraEnabledNative = asInterface2.isCameraEnabledNative(new ContextInfo(i));
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (SecurityException e2) {
                e2.printStackTrace();
            } catch (Exception e3) {
                e3.printStackTrace();
            }
            asInterface = IDevicePolicyManager.Stub.asInterface(ServiceManager.getService("device_policy"));
            if (asInterface != null) {
                try {
                    z = !asInterface.getCameraDisabled((ComponentName) null, (String) null, UserHandle.getUserId(i), false);
                } catch (RemoteException e4) {
                    e4.printStackTrace();
                } catch (SecurityException e5) {
                    e5.printStackTrace();
                } catch (Exception e6) {
                    e6.printStackTrace();
                }
                return !isCameraEnabledNative && z;
            }
            z = true;
            if (isCameraEnabledNative) {
            }
        }
        isCameraEnabledNative = true;
        asInterface = IDevicePolicyManager.Stub.asInterface(ServiceManager.getService("device_policy"));
        if (asInterface != null) {
        }
        z = true;
        if (isCameraEnabledNative) {
        }
    }

    public static boolean isFaceRecognitionAllowedEvenCameraBlocked(int i) {
        IRestrictionPolicy asInterface = IRestrictionPolicy.Stub.asInterface(ServiceManager.getService("restriction_policy"));
        if (asInterface == null) {
            return true;
        }
        try {
            return asInterface.isFaceRecognitionAllowedEvenCameraBlocked(new ContextInfo(i));
        } catch (RemoteException e) {
            e.printStackTrace();
            return true;
        }
    }

    public static boolean isHIDProfileEnabled() {
        IBluetoothPolicy asInterface = IBluetoothPolicy.Stub.asInterface(ServiceManager.getService("bluetooth_policy"));
        if (asInterface == null) {
            return true;
        }
        try {
            return asInterface.isBluetoothUUIDAllowed(new ContextInfo(), BluetoothPolicy.BluetoothUUID.HID_UUID);
        } catch (RemoteException e) {
            e.printStackTrace();
            return true;
        }
    }

    public static boolean isIrisCameraEnabled(int i) {
        IRestrictionPolicy asInterface = IRestrictionPolicy.Stub.asInterface(ServiceManager.getService("restriction_policy"));
        if (asInterface != null) {
            try {
                return asInterface.isIrisCameraEnabled(new ContextInfo(i), true);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public static boolean isMicrophoneEnabled(int i) {
        IRestrictionPolicy asInterface = IRestrictionPolicy.Stub.asInterface(ServiceManager.getService("restriction_policy"));
        if (asInterface != null) {
            try {
                return asInterface.isMicrophoneEnabled(new ContextInfo(i), true);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public static boolean isPackageInAvrWhitelist(int i) {
        IApplicationPolicy asInterface = IApplicationPolicy.Stub.asInterface(ServiceManager.getService("application_policy"));
        if (asInterface == null) {
            return false;
        }
        try {
            return asInterface.isPackageInWhitelistInternal(3, UserHandle.getCallingUserId(), i);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public static boolean isScreenCaptureEnabled() {
        IRestrictionPolicy asInterface = IRestrictionPolicy.Stub.asInterface(ServiceManager.getService("restriction_policy"));
        if (asInterface != null) {
            try {
                return asInterface.isScreenCaptureEnabledInternal(true);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public static boolean isVideoRecordAllowed(int i) {
        IRestrictionPolicy asInterface = IRestrictionPolicy.Stub.asInterface(ServiceManager.getService("restriction_policy"));
        if (asInterface != null) {
            try {
                return asInterface.isVideoRecordAllowed(new ContextInfo(i), true);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public static void nativeLogger(int i, int i2, boolean z, int i3, String str, String str2) {
        AuditLog.log(i, i2, z, i3, str, str2);
    }

    public static void nativeLoggerPrivilegedAsUser(int i, int i2, int i3, int i4, String str, byte[] bArr, byte[] bArr2, int i5) {
        try {
            String str2 = new String(bArr, "UTF-8");
            String str3 = new String(bArr2, "UTF-8");
            String[] split = str2.split("\\n");
            String[] split2 = str3.split("\\n");
            int i6 = 0;
            while (i6 < split.length) {
                AuditLog.logPrivilegedAsUser(i, i2, i3 != 0, i4, str, split[i6], split2.length > i6 ? split2[i6] : null, i5);
                i6++;
            }
        } catch (UnsupportedEncodingException unused) {
            Log.d(TAG, "Unsupported conversion");
        }
    }

    public static void sendIntent(int i) {
        IEnterpriseDeviceManager asInterface = IEnterpriseDeviceManager.Stub.asInterface(ServiceManager.getService("enterprise_policy"));
        if (asInterface != null) {
            try {
                asInterface.sendIntent(i);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public static void updateDexScreenDimensions(int i, int i2, int i3) {
        IRemoteInjection asInterface = IRemoteInjection.Stub.asInterface(ServiceManager.getService("remoteinjection"));
        if (asInterface != null) {
            try {
                asInterface.updateDexScreenDimensions(i, i2, i3);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public static void updateRemoteScreenDimensionsAndCallerUid(int i, int i2, int i3) {
        IRemoteInjection asInterface = IRemoteInjection.Stub.asInterface(ServiceManager.getService("remoteinjection"));
        if (asInterface != null) {
            try {
                asInterface.updateRemoteScreenDimensionsAndCallerUid(i, i2, i3);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public static void nativeLogger(int i, int i2, int i3, int i4, String str, byte[] bArr) {
        try {
            String[] split = new String(bArr, "UTF-8").split("\\n");
            for (String str2 : split) {
                AuditLog.log(i, i2, true, i4, str, str2);
            }
        } catch (UnsupportedEncodingException unused) {
            Log.d(TAG, "Unsupported conversion");
        }
    }
}
