package android.sec.enterprise.kioskmode;

import android.sec.enterprise.EnterpriseDeviceManager;
import android.util.Log;

/* loaded from: classes3.dex */
public class KioskMode {
    public static String CONTROL_PANEL_PKGNAME = "com.sec.android.app.controlpanel";
    public static String MINI_TASK_MANAGER_PKGNAME = "com.sec.minimode.taskcloser";
    private static final String TAG = "KioskMode";
    public static String TASK_MANAGER_PKGNAME = "com.sec.android.app.taskmanager";

    public boolean isTaskManagerAllowed(boolean z) {
        try {
            if (EnterpriseDeviceManager.EDMProxyServiceHelper.getService() != null) {
                return EnterpriseDeviceManager.EDMProxyServiceHelper.getService().isTaskManagerAllowed(z);
            }
            return true;
        } catch (Exception unused) {
            Log.d("KioskMode", "PXY-isTaskManagerAllowed returning default value");
            return true;
        }
    }
}
