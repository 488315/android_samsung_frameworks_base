package android.sec.enterprise;

import android.sec.enterprise.EnterpriseDeviceManager;
import android.util.Log;

/* loaded from: classes3.dex */
public class DeviceInventory {
    public static final String DEVICE_INFO_DROPPED_CALL = "dropped";
    public static final String DEVICE_INFO_MISSED_CALL = "missed";
    public static final String DEVICE_INFO_SUCCESS_CALL = "success";
    private static String TAG = "DeviceInfo";

    public void addCallsCount(String callType) {
        try {
            IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService != null) {
                lService.addCallsCount(callType);
            }
        } catch (Exception e) {
            Log.m94d(TAG, "PXY-addCallsCount returning default value");
        }
    }

    public boolean isCallingCaptureEnabled() {
        try {
            IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService != null) {
                return lService.isCallingCaptureEnabled();
            }
            return false;
        } catch (Exception e) {
            Log.m94d(TAG, "PXY-isCallingCaptureEnabled returning default value");
            return false;
        }
    }

    public boolean storeCalling(String address, String timeStamp, String duration, String status, boolean isIncoming) {
        try {
            IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService != null) {
                lService.storeCalling(address, timeStamp, duration, status, isIncoming);
                return true;
            }
            return false;
        } catch (Exception e) {
            Log.m94d(TAG, "PXY-storeCalling returning default value");
            return false;
        }
    }

    public boolean isSMSCaptureEnabled() {
        try {
            IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService != null) {
                return lService.isSMSCaptureEnabled();
            }
            return false;
        } catch (Exception e) {
            Log.m94d(TAG, "PXY-isSMSCaptureEnabled returning default value");
            return false;
        }
    }

    public void storeSMS(String address, String timeStamp, String message, boolean isInbound) {
        try {
            IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService != null) {
                lService.storeSMS(address, timeStamp, message, isInbound);
            }
        } catch (Exception e) {
            Log.m94d(TAG, "PXY-storeSMS returning default value");
        }
    }
}
