package android.sec.enterprise.kioskmode;

import android.sec.enterprise.EnterpriseDeviceManager;
import android.sec.enterprise.IEDMProxy;
import android.util.Log;

/* loaded from: classes3.dex */
public class KioskMode {
    private static final String TAG = "KioskMode";
    public static String CONTROL_PANEL_PKGNAME = "com.sec.android.app.controlpanel";
    public static String TASK_MANAGER_PKGNAME = "com.sec.android.app.taskmanager";
    public static String MINI_TASK_MANAGER_PKGNAME = "com.sec.minimode.taskcloser";

    public boolean isTaskManagerAllowed(boolean showMsg) {
        try {
            IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService != null) {
                return EnterpriseDeviceManager.EDMProxyServiceHelper.getService().isTaskManagerAllowed(showMsg);
            }
            return true;
        } catch (Exception e) {
            Log.d("KioskMode", "PXY-isTaskManagerAllowed returning default value");
            return true;
        }
    }
}
