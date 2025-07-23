package android.sec.enterprise;

import android.sec.enterprise.EnterpriseDeviceManager;
import android.util.Log;

/* loaded from: classes3.dex */
public class BrowserPolicy {
    private static String TAG = "BrowserPolicy";

    public class BrowserSetting {
        public static final int BROWSER_AUTOFILL_SETTING = 4;
        public static final int BROWSER_COOKIES_SETTING = 2;
        public static final int BROWSER_FORCEFRAUDWARNING_SETTING = 8;
        public static final int BROWSER_JAVASCRIPT_SETTING = 16;
        public static final int BROWSER_POPUP_SETTING = 1;

        public BrowserSetting(BrowserPolicy browserPolicy) {
        }
    }

    public boolean getCookiesSetting() {
        try {
            IEDMProxy service = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (service != null) {
                return service.getBrowserSettingStatus(2);
            }
            return true;
        } catch (Exception unused) {
            Log.d(TAG, "PXY-getCookiesSetting returning default value");
            return true;
        }
    }

    public boolean getAutoFillSetting() {
        try {
            IEDMProxy service = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (service != null) {
                return service.getBrowserSettingStatus(4);
            }
            return true;
        } catch (Exception unused) {
            Log.d(TAG, "PXY-getAutoFillSetting returning default value");
            return true;
        }
    }

    public boolean getJavaScriptSetting() {
        try {
            IEDMProxy service = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (service != null) {
                return service.getBrowserSettingStatus(16);
            }
            return true;
        } catch (Exception unused) {
            Log.d(TAG, "PXY-getJavaScriptSetting returning default value");
            return true;
        }
    }

    public boolean getPopupsSetting() {
        try {
            IEDMProxy service = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (service != null) {
                return service.getBrowserSettingStatus(1);
            }
        } catch (Exception unused) {
            Log.d(TAG, "PXY-getPopupsSetting returning default value");
        }
        return true;
    }
}
