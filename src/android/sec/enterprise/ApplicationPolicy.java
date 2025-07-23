package android.sec.enterprise;

import android.sec.enterprise.EnterpriseDeviceManager;
import android.util.Log;

/* loaded from: classes3.dex */
public class ApplicationPolicy {
    public static final int NOTIFICATION_MODE_BLOCK_ALL = 2;
    public static final int NOTIFICATION_MODE_BLOCK_TEXT = 3;
    public static final int NOTIFICATION_MODE_BLOCK_TEXT_AND_SOUND = 4;
    private static String TAG = "ApplicationPolicy";

    public byte[] getApplicationIconFromDb(String str, int i) {
        try {
            IEDMProxy service = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (service != null) {
                return service.getApplicationIconFromDb(str, i);
            }
            return null;
        } catch (Exception unused) {
            Log.d(TAG, "PXY-getApplicationIconFromDb returning default value");
            return null;
        }
    }

    public boolean getAddHomeShorcutRequested() {
        try {
            IEDMProxy service = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (service != null) {
                return service.getAddHomeShorcutRequested();
            }
            return false;
        } catch (Exception unused) {
            Log.d(TAG, "PXY-getAddHomeShorcutRequested returning default value");
            return false;
        }
    }

    public String getApplicationNameFromDb(String str, int i) {
        try {
            IEDMProxy service = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (service != null) {
                return service.getApplicationNameFromDb(str, i);
            }
            return null;
        } catch (Exception unused) {
            Log.d(TAG, "PXY-getApplicationNameFromDb returning default value");
            return null;
        }
    }

    public String getApplicationNameForComponent(String str, String str2, int i) {
        try {
            IEDMProxy service = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (service != null) {
                return service.getApplicationNameForComponent(str, str2, i);
            }
            return null;
        } catch (Exception unused) {
            Log.d(TAG, "PXY-getApplicationNameForComponent returning default value");
            return null;
        }
    }

    public boolean isAnyApplicationNameChangedAsUser(int i) {
        try {
            IEDMProxy service = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (service != null) {
                return service.isAnyApplicationNameChangedAsUser(i);
            }
            return false;
        } catch (Exception unused) {
            Log.d(TAG, "PXY-isAnyApplicationNameChangedAsUser returning default value");
            return false;
        }
    }

    public boolean isPackageInAvrWhitelist(int i) {
        try {
            IEDMProxy service = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (service != null) {
                return service.isPackageInAvrWhitelist(i);
            }
            return false;
        } catch (Exception unused) {
            Log.d(TAG, "PXY-isPackageInAvrWhitelist returning default value");
            return false;
        }
    }
}
