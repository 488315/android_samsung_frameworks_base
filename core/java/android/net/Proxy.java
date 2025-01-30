package android.net;

import android.annotation.SystemApi;
import android.app.ActivityThread;
import android.content.Context;
import android.net.IVpnManager;
import android.p009os.Debug;
import android.p009os.Process;
import android.p009os.ServiceManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.NtpTrustedTime;
import com.android.net.module.util.ProxyUtils;
import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.net.URI;
import java.util.List;

/* loaded from: classes2.dex */
public final class Proxy {
    private static final String ENTERPRISE_PROXY_PROPERTY = "enterprise.proxy.auth";

    @Deprecated
    public static final String EXTRA_PROXY_INFO = "android.intent.extra.PROXY_INFO";
    public static final String PROXY_CHANGE_ACTION = "android.intent.action.PROXY_CHANGE";
    private static final String TAG = "Proxy";
    private static ConnectivityManager sConnectivityManager = null;
    private static final ProxySelector sDefaultProxySelector = ProxySelector.getDefault();
    private static final boolean DBG = Debug.semIsProductDev();

    public static final java.net.Proxy getProxy(Context ctx, String url) {
        if (url != null && !isLocalHost("")) {
            URI uri = URI.create(url);
            ProxySelector proxySelector = ProxySelector.getDefault();
            List<java.net.Proxy> proxyList = proxySelector.select(uri);
            if (proxyList.size() > 0) {
                return proxyList.get(0);
            }
        }
        return java.net.Proxy.NO_PROXY;
    }

    @Deprecated
    public static final String getHost(Context ctx) {
        java.net.Proxy proxy = getProxy(ctx, null);
        if (proxy == java.net.Proxy.NO_PROXY) {
            return null;
        }
        try {
            return ((InetSocketAddress) proxy.address()).getHostName();
        } catch (Exception e) {
            return null;
        }
    }

    @Deprecated
    public static final int getPort(Context ctx) {
        java.net.Proxy proxy = getProxy(ctx, null);
        if (proxy == java.net.Proxy.NO_PROXY) {
            return -1;
        }
        try {
            return ((InetSocketAddress) proxy.address()).getPort();
        } catch (Exception e) {
            return -1;
        }
    }

    @Deprecated
    public static final String getDefaultHost() {
        String host = System.getProperty("http.proxyHost");
        if (TextUtils.isEmpty(host)) {
            return null;
        }
        return host;
    }

    @Deprecated
    public static final int getDefaultPort() {
        if (getDefaultHost() == null) {
            return -1;
        }
        try {
            return Integer.parseInt(System.getProperty("http.proxyPort"));
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static final boolean isLocalHost(String host) {
        if (host != null && host != null) {
            try {
                if (host.equalsIgnoreCase("localhost")) {
                    return true;
                }
                if (InetAddresses.parseNumericAddress(host).isLoopbackAddress()) {
                    return true;
                }
            } catch (IllegalArgumentException e) {
            }
        }
        return false;
    }

    @Deprecated
    public static void setHttpProxySystemProperty(ProxyInfo p) {
        setHttpProxyConfiguration(p);
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static void setHttpProxyConfiguration(ProxyInfo p) {
        String host = null;
        String port = null;
        String exclList = null;
        Uri pacFileUrl = Uri.EMPTY;
        if (p != null) {
            host = p.getHost();
            port = Integer.toString(p.getPort());
            exclList = ProxyUtils.exclusionListAsString(p.getExclusionList());
            pacFileUrl = p.getPacFileUrl();
        }
        setHttpProxyConfiguration(host, port, exclList, pacFileUrl);
    }

    public static void setHttpProxyConfiguration(String host, String port, String exclList, Uri pacFileUrl) {
        int[] knoxVpnZtnaProxyInfo = new int[2];
        if (host == null && port == null) {
            knoxVpnZtnaProxyInfo = getKnoxVpnZtnaProxyInfo();
            if (knoxVpnZtnaProxyInfo[0] > 0) {
                host = "localhost";
                port = Integer.toString(knoxVpnZtnaProxyInfo[0]);
            }
            if (knoxVpnZtnaProxyInfo[1] > 0) {
                host = "127.0.0.1";
                port = Integer.toString(knoxVpnZtnaProxyInfo[1]);
            }
        }
        if (DBG) {
            Log.m94d(TAG, "setHttpProxySystemPropertyInternal for uid " + Process.myUid() + " The host value is " + host + " the port value is " + port);
        }
        if (exclList != null) {
            exclList = exclList.replace(",", NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER);
        }
        if (host != null) {
            System.setProperty("http.proxyHost", host);
            System.setProperty("https.proxyHost", host);
        } else {
            System.clearProperty("http.proxyHost");
            System.clearProperty("https.proxyHost");
        }
        if (port != null) {
            System.setProperty("http.proxyPort", port);
            System.setProperty("https.proxyPort", port);
        } else {
            System.clearProperty("http.proxyPort");
            System.clearProperty("https.proxyPort");
        }
        if (exclList != null) {
            System.setProperty("http.nonProxyHosts", exclList);
            System.setProperty("https.nonProxyHosts", exclList);
        } else {
            System.clearProperty("http.nonProxyHosts");
            System.clearProperty("https.nonProxyHosts");
        }
        if (knoxVpnZtnaProxyInfo[0] > 0) {
            if (!Uri.EMPTY.equals(pacFileUrl)) {
                ProxySelector.setDefault(new KnoxVpnProxySelector());
            }
        } else if (knoxVpnZtnaProxyInfo[1] > 0) {
            ProxySelector.setDefault(new KnoxZtnaProxySelector());
        } else if (!Uri.EMPTY.equals(pacFileUrl)) {
            ProxySelector.setDefault(new PacProxySelector());
        } else {
            ProxySelector.setDefault(sDefaultProxySelector);
        }
    }

    private static IVpnManager getVpnManagerService() {
        return IVpnManager.Stub.asInterface(ServiceManager.getService(Context.VPN_MANAGEMENT_SERVICE));
    }

    private static int[] getKnoxVpnZtnaProxyInfo() {
        int[] knoxVpnZtnaProxyInfo = new int[2];
        try {
            String packageName = ActivityThread.currentPackageName();
            return getVpnManagerService().getKnoxVpnZtnaProxyInfoForUid(Process.myUid(), packageName);
        } catch (Exception e) {
            if (DBG) {
                Log.m96e(TAG, "getProxyInfo " + Process.myUid() + " error occured " + Log.getStackTraceString(e));
                return knoxVpnZtnaProxyInfo;
            }
            return knoxVpnZtnaProxyInfo;
        }
    }
}
