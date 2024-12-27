package android.sec.enterprise;

import android.sec.enterprise.EnterpriseDeviceManager;
import android.sec.enterprise.content.SecContentProviderURI;
import android.util.Log;

/* loaded from: classes3.dex */
public class BrowserPolicy {
    private static String TAG = SecContentProviderURI.BROWSERPOLICY;

    public class BrowserSetting {
        public static final int BROWSER_AUTOFILL_SETTING = 4;
        public static final int BROWSER_COOKIES_SETTING = 2;
        public static final int BROWSER_FORCEFRAUDWARNING_SETTING = 8;
        public static final int BROWSER_JAVASCRIPT_SETTING = 16;
        public static final int BROWSER_POPUP_SETTING = 1;

        public BrowserSetting() {
        }
    }

    public boolean getCookiesSetting() {
        try {
            IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService != null) {
                return lService.getBrowserSettingStatus(2);
            }
            return true;
        } catch (Exception e) {
            Log.d(TAG, "PXY-getCookiesSetting returning default value");
            return true;
        }
    }

    public boolean getAutoFillSetting() {
        try {
            IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService != null) {
                return lService.getBrowserSettingStatus(4);
            }
            return true;
        } catch (Exception e) {
            Log.d(TAG, "PXY-getAutoFillSetting returning default value");
            return true;
        }
    }

    public boolean getJavaScriptSetting() {
        try {
            IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService != null) {
                return lService.getBrowserSettingStatus(16);
            }
            return true;
        } catch (Exception e) {
            Log.d(TAG, "PXY-getJavaScriptSetting returning default value");
            return true;
        }
    }

    public boolean getPopupsSetting() {
        try {
            IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService != null) {
                return lService.getBrowserSettingStatus(1);
            }
        } catch (Exception e) {
            Log.d(TAG, "PXY-getPopupsSetting returning default value");
        }
        return true;
    }
}
