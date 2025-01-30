package android.sec.enterprise;

import android.sec.enterprise.EnterpriseDeviceManager;
import android.util.Log;

/* loaded from: classes3.dex */
public class ApplicationPolicy {
    public static final int NOTIFICATION_MODE_BLOCK_ALL = 2;
    public static final int NOTIFICATION_MODE_BLOCK_TEXT = 3;
    public static final int NOTIFICATION_MODE_BLOCK_TEXT_AND_SOUND = 4;
    private static String TAG = "ApplicationPolicy";

    public byte[] getApplicationIconFromDb(String packageName, int userId) {
        try {
            IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService == null) {
                return null;
            }
            byte[] imageData = lService.getApplicationIconFromDb(packageName, userId);
            return imageData;
        } catch (Exception e) {
            Log.m94d(TAG, "PXY-getApplicationIconFromDb returning default value");
            return null;
        }
    }

    public boolean getAddHomeShorcutRequested() {
        try {
            IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService != null) {
                return lService.getAddHomeShorcutRequested();
            }
            return false;
        } catch (Exception e) {
            Log.m94d(TAG, "PXY-getAddHomeShorcutRequested returning default value");
            return false;
        }
    }

    public String getApplicationNameFromDb(String packageName, int userId) {
        try {
            IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService == null) {
                return null;
            }
            String newName = lService.getApplicationNameFromDb(packageName, userId);
            return newName;
        } catch (Exception e) {
            Log.m94d(TAG, "PXY-getApplicationNameFromDb returning default value");
            return null;
        }
    }

    public String getApplicationNameForComponent(String componentName, String packageName, int userId) {
        try {
            IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService == null) {
                return null;
            }
            String newName = lService.getApplicationNameForComponent(componentName, packageName, userId);
            return newName;
        } catch (Exception e) {
            Log.m94d(TAG, "PXY-getApplicationNameForComponent returning default value");
            return null;
        }
    }

    public boolean isAnyApplicationNameChangedAsUser(int userId) {
        try {
            IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService == null) {
                return false;
            }
            boolean result = lService.isAnyApplicationNameChangedAsUser(userId);
            return result;
        } catch (Exception e) {
            Log.m94d(TAG, "PXY-isAnyApplicationNameChangedAsUser returning default value");
            return false;
        }
    }

    public boolean isPackageInAvrWhitelist(int uid) {
        try {
            IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService == null) {
                return false;
            }
            boolean result = lService.isPackageInAvrWhitelist(uid);
            return result;
        } catch (Exception e) {
            Log.m94d(TAG, "PXY-isPackageInAvrWhitelist returning default value");
            return false;
        }
    }
}
