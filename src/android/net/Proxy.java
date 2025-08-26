package android.net;

import android.annotation.SystemApi;
import android.app.ActivityThread;
import android.content.Context;
import android.net.IVpnManager;
import android.os.Debug;
import android.os.Process;
import android.os.ServiceManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.NtpTrustedTime;
import com.android.net.module.util.ProxyUtils;
import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.net.URI;
import java.util.List;

/* loaded from: classes3.dex */
public final class Proxy {
    private static final String ENTERPRISE_PROXY_PROPERTY = "enterprise.proxy.auth";

    @Deprecated
    public static final String EXTRA_PROXY_INFO = "android.intent.extra.PROXY_INFO";
    public static final String PROXY_CHANGE_ACTION = "android.intent.action.PROXY_CHANGE";
    private static final String TAG = "Proxy";
    private static ConnectivityManager sConnectivityManager;
    private static final ProxySelector sDefaultProxySelector = ProxySelector.getDefault();
    private static final boolean DBG = Debug.semIsProductDev();

    public static final java.net.Proxy getProxy(Context context, String str) {
        if (str != null && !isLocalHost("")) {
            List<java.net.Proxy> listSelect = ProxySelector.getDefault().select(URI.create(str));
            if (listSelect.size() > 0) {
                return listSelect.get(0);
            }
        }
        return java.net.Proxy.NO_PROXY;
    }

    @Deprecated
    public static final String getHost(Context context) {
        java.net.Proxy proxy = getProxy(context, null);
        if (proxy == java.net.Proxy.NO_PROXY) {
            return null;
        }
        try {
            return ((InetSocketAddress) proxy.address()).getHostName();
        } catch (Exception unused) {
            return null;
        }
    }

    @Deprecated
    public static final int getPort(Context context) {
        java.net.Proxy proxy = getProxy(context, null);
        if (proxy == java.net.Proxy.NO_PROXY) {
            return -1;
        }
        try {
            return ((InetSocketAddress) proxy.address()).getPort();
        } catch (Exception unused) {
            return -1;
        }
    }

    @Deprecated
    public static final String getDefaultHost() {
        String property = System.getProperty("http.proxyHost");
        if (TextUtils.isEmpty(property)) {
            return null;
        }
        return property;
    }

    @Deprecated
    public static final int getDefaultPort() {
        if (getDefaultHost() == null) {
            return -1;
        }
        try {
            return Integer.parseInt(System.getProperty("http.proxyPort"));
        } catch (NumberFormatException unused) {
            return -1;
        }
    }

    private static final boolean isLocalHost(String str) {
        if (str != null && str != null) {
            try {
                if (str.equalsIgnoreCase("localhost")) {
                    return true;
                }
                if (InetAddresses.parseNumericAddress(str).isLoopbackAddress()) {
                    return true;
                }
            } catch (IllegalArgumentException unused) {
            }
        }
        return false;
    }

    @Deprecated
    public static void setHttpProxySystemProperty(ProxyInfo proxyInfo) {
        setHttpProxyConfiguration(proxyInfo);
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static void setHttpProxyConfiguration(ProxyInfo proxyInfo) {
        String string;
        String strExclusionListAsString;
        Uri pacFileUrl;
        String host;
        Uri uri = Uri.EMPTY;
        if (proxyInfo != null) {
            host = proxyInfo.getHost();
            string = Integer.toString(proxyInfo.getPort());
            strExclusionListAsString = ProxyUtils.exclusionListAsString(proxyInfo.getExclusionList());
            pacFileUrl = proxyInfo.getPacFileUrl();
        } else {
            string = null;
            strExclusionListAsString = null;
            pacFileUrl = uri;
            host = null;
        }
        setHttpProxyConfiguration(host, string, strExclusionListAsString, pacFileUrl);
    }

    public static void setHttpProxyConfiguration(String str, String str2, String str3, Uri uri) {
        int[] knoxVpnZtnaProxyInfo = getKnoxVpnZtnaProxyInfo();
        int i = knoxVpnZtnaProxyInfo[0];
        if (i > 0) {
            str2 = Integer.toString(i);
            str = "localhost";
        }
        int i2 = knoxVpnZtnaProxyInfo[1];
        if (i2 > 0) {
            str2 = Integer.toString(i2);
            str = "127.0.0.1";
        }
        boolean z = DBG;
        if (z) {
            Log.d(TAG, "setHttpProxySystemPropertyInternal for uid " + Process.myUid() + " The host value is " + str + " the port value is " + str2);
        }
        if (str3 != null) {
            str3 = str3.replace(",", NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER);
        }
        if (z) {
            Log.d(TAG, "setHttpProxySystemProperty :" + str + ":" + str2 + " - " + str3);
        }
        if (str != null) {
            System.setProperty("http.proxyHost", str);
            System.setProperty("https.proxyHost", str);
        } else {
            System.clearProperty("http.proxyHost");
            System.clearProperty("https.proxyHost");
        }
        if (str2 != null) {
            System.setProperty("http.proxyPort", str2);
            System.setProperty("https.proxyPort", str2);
        } else {
            System.clearProperty("http.proxyPort");
            System.clearProperty("https.proxyPort");
        }
        if (str3 != null) {
            System.setProperty("http.nonProxyHosts", str3);
            System.setProperty("https.nonProxyHosts", str3);
        } else {
            System.clearProperty("http.nonProxyHosts");
            System.clearProperty("https.nonProxyHosts");
        }
        if (knoxVpnZtnaProxyInfo[0] > 0) {
            if (Uri.EMPTY.equals(uri)) {
                return;
            }
            ProxySelector.setDefault(new KnoxVpnProxySelector());
        } else if (knoxVpnZtnaProxyInfo[1] > 0) {
            ProxySelector.setDefault(new KnoxZtnaProxySelector());
        } else if (!Uri.EMPTY.equals(uri)) {
            ProxySelector.setDefault(new PacProxySelector());
        } else {
            ProxySelector.setDefault(sDefaultProxySelector);
        }
    }

    private static IVpnManager getVpnManagerService() {
        return IVpnManager.Stub.asInterface(ServiceManager.getService(Context.VPN_MANAGEMENT_SERVICE));
    }

    private static int[] getKnoxVpnZtnaProxyInfo() {
        int[] iArr = new int[2];
        try {
            return getVpnManagerService().getKnoxVpnZtnaProxyInfoForUid(Process.myUid(), ActivityThread.currentPackageName());
        } catch (Exception e) {
            if (DBG) {
                Log.e(TAG, "getProxyInfo " + Process.myUid() + " error occured " + Log.getStackTraceString(e));
            }
            return iArr;
        }
    }
}
