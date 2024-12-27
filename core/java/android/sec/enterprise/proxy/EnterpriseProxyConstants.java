package android.sec.enterprise.proxy;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class EnterpriseProxyConstants {
    public static final int ANDROID_PROXY_CONFIG = 0;
    public static final String BUNDLE_CREDENTIALS = "credentials";
    public static final String BUNDLE_NEW_PROXY = "newProxy";
    public static final String BUNDLE_OLD_PROXY = "oldProxy";
    public static final String BUNDLE_PACKAGE_NAME = "packageName";
    public static final String BUNDLE_PROXY = "proxy";
    public static final String BUNDLE_RESULT = "result";
    public static final String ENTERPRISE_AUTH_DIALOG_CLASS_NAME =
            "com.samsung.android.mdm.EnterpriseProxyAuthDialog";
    public static final String ENTERPRISE_AUTH_DIALOG_PACKAGE = "com.samsung.android.mdm";
    public static final int ENTERPRISE_AUTH_NOTIFICATION_ID = 993;
    public static final int ENTERPRISE_PROXY_CONFIG = 2;
    public static final int ERROR_BACK_CODE = -3;
    public static final int ERROR_CANCEL_CODE = -2;
    public static final int ERROR_INVALID_CRED_CODE = -4;
    public static final int ERROR_RETURN_CODE = -1;
    public static final int KNOX_VPN_PROXY_CONFIG = 1;
    public static final List<String> LOCAL_ENTERPRISE_PROXY_WHITELIST =
            new ArrayList<
                    String>() { // from class:
                                // android.sec.enterprise.proxy.EnterpriseProxyConstants.1
                {
                    add("com.android.chrome");
                    add("com.sec.android.app.sbrowser");
                }
            };
    public static final int NO_ERROR = 0;
    public static final String PERMISSION_KNOX_SET_PROXY_CREDENTIAL_INTERNAL =
            "com.samsung.android.knox.permission.KNOX_SET_PROXY_CREDENTIAL_INTERNAL";
    public static final String PROXYHANDLER_PACKAGE = "com.android.proxyhandler";
    public static final String PROXY_CLEAR_SERVER_CACHE =
            "com.samsung.android.knox.intent.action.PROXY_CLEAR_SERVER_CACHE";
    public static final String PROXY_REFRESH_CREDENTIALS_DIALOG =
            "com.samsung.android.knox.intent.action.PROXY_REFRESH_CREDENTIALS_DIALOG_INTERNAL";
    public static final String PROXY_SERVICE = "com.android.proxyhandler.ProxyService";
}
