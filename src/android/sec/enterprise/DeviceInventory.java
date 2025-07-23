package android.sec.enterprise;

import android.sec.enterprise.EnterpriseDeviceManager;
import android.util.Log;

/* loaded from: classes3.dex */
public class DeviceInventory {
    public static final String DEVICE_INFO_DROPPED_CALL = "dropped";
    public static final String DEVICE_INFO_MISSED_CALL = "missed";
    public static final String DEVICE_INFO_SUCCESS_CALL = "success";
    private static String TAG = "DeviceInfo";

    public void addCallsCount(String str) {
        try {
            IEDMProxy service = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (service != null) {
                service.addCallsCount(str);
            }
        } catch (Exception unused) {
            Log.d(TAG, "PXY-addCallsCount returning default value");
        }
    }

    public boolean isCallingCaptureEnabled() {
        try {
            IEDMProxy service = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (service != null) {
                return service.isCallingCaptureEnabled();
            }
            return false;
        } catch (Exception unused) {
            Log.d(TAG, "PXY-isCallingCaptureEnabled returning default value");
            return false;
        }
    }

    public boolean storeCalling(String str, String str2, String str3, String str4, boolean z) {
        try {
            IEDMProxy service = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (service == null) {
                return false;
            }
            service.storeCalling(str, str2, str3, str4, z);
            return true;
        } catch (Exception unused) {
            Log.d(TAG, "PXY-storeCalling returning default value");
            return false;
        }
    }

    public boolean isSMSCaptureEnabled() {
        try {
            IEDMProxy service = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (service != null) {
                return service.isSMSCaptureEnabled();
            }
            return false;
        } catch (Exception unused) {
            Log.d(TAG, "PXY-isSMSCaptureEnabled returning default value");
            return false;
        }
    }

    public void storeSMS(String str, String str2, String str3, boolean z) {
        try {
            IEDMProxy service = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (service != null) {
                service.storeSMS(str, str2, str3, z);
            }
        } catch (Exception unused) {
            Log.d(TAG, "PXY-storeSMS returning default value");
        }
    }
}
