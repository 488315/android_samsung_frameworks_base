package android.sec.enterprise;

import android.util.Log;

/* loaded from: classes3.dex */
public class DeviceAccountPolicy {
  private static String TAG = "DeviceAccountPolicy";

  public boolean isAccountRemovalAllowed(String type, String account, boolean showMsg) {
    try {
      IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
      if (lService != null) {
        return lService.isAccountRemovalAllowed(type, account, showMsg);
      }
      return true;
    } catch (Exception e) {
      Log.m94d(TAG, "PXY-isAccountRemovalAllowed returning default value");
      return true;
    }
  }
}
