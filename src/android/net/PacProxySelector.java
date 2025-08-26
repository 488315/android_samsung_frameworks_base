package android.net;

import android.content.IntentFilter;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.ServiceManager;
import android.util.Log;
import com.android.net.IProxyService;
import com.google.android.collect.Lists;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class PacProxySelector extends ProxySelector {
    private static final String PROXY = "PROXY ";
    public static final String PROXY_SERVICE = "com.android.net.IProxyService";
    private static final String SOCKS = "SOCKS ";
    private static final String TAG = "PacProxySelector";
    private final List<java.net.Proxy> mDefaultList;
    private IProxyService mProxyService;

    @Override // java.net.ProxySelector
    public void connectFailed(URI uri, SocketAddress socketAddress, IOException iOException) {
    }

    public PacProxySelector() {
        IProxyService iProxyServiceAsInterface = IProxyService.Stub.asInterface(ServiceManager.getService("com.android.net.IProxyService"));
        this.mProxyService = iProxyServiceAsInterface;
        if (iProxyServiceAsInterface == null) {
            Log.e(TAG, "PacProxyService: no proxy service");
        }
        this.mDefaultList = Lists.newArrayList(java.net.Proxy.NO_PROXY);
    }

    @Override // java.net.ProxySelector
    public List<java.net.Proxy> select(URI uri) {
        String host;
        String strResolvePacFile;
        if (this.mProxyService == null) {
            this.mProxyService = IProxyService.Stub.asInterface(ServiceManager.getService("com.android.net.IProxyService"));
        }
        if (this.mProxyService == null) {
            Log.e(TAG, "select: no proxy service return NO_PROXY");
            return Lists.newArrayList(java.net.Proxy.NO_PROXY);
        }
        try {
            if (!IntentFilter.SCHEME_HTTP.equalsIgnoreCase(uri.getScheme())) {
                uri = new URI(uri.getScheme(), null, uri.getHost(), uri.getPort(), "/", null, null);
            }
            host = uri.toURL().toString();
        } catch (MalformedURLException unused) {
            host = uri.getHost();
        } catch (URISyntaxException unused2) {
            host = uri.getHost();
        }
        try {
            strResolvePacFile = this.mProxyService.resolvePacFile(uri.getHost(), host);
        } catch (Exception e) {
            Log.e(TAG, "Error resolving PAC File", e);
            strResolvePacFile = null;
        }
        if (strResolvePacFile == null) {
            return this.mDefaultList;
        }
        return parseResponse(strResolvePacFile);
    }

    private static List<java.net.Proxy> parseResponse(String str) {
        java.net.Proxy proxyProxyFromHostPort;
        String[] strArrSplit = str.split(NavigationBarInflaterView.GRAVITY_SEPARATOR);
        ArrayList arrayListNewArrayList = Lists.newArrayList();
        for (String str2 : strArrSplit) {
            String strTrim = str2.trim();
            if (strTrim.equals("DIRECT")) {
                arrayListNewArrayList.add(java.net.Proxy.NO_PROXY);
            } else if (strTrim.startsWith(PROXY)) {
                java.net.Proxy proxyProxyFromHostPort2 = proxyFromHostPort(Proxy.Type.HTTP, strTrim.substring(6));
                if (proxyProxyFromHostPort2 != null) {
                    arrayListNewArrayList.add(proxyProxyFromHostPort2);
                }
            } else if (strTrim.startsWith(SOCKS) && (proxyProxyFromHostPort = proxyFromHostPort(Proxy.Type.SOCKS, strTrim.substring(6))) != null) {
                arrayListNewArrayList.add(proxyProxyFromHostPort);
            }
        }
        if (arrayListNewArrayList.size() == 0) {
            arrayListNewArrayList.add(java.net.Proxy.NO_PROXY);
        }
        return arrayListNewArrayList;
    }

    private static java.net.Proxy proxyFromHostPort(Proxy.Type type, String str) {
        try {
            String[] strArrSplit = str.split(":");
            return new java.net.Proxy(type, InetSocketAddress.createUnresolved(strArrSplit[0], Integer.parseInt(strArrSplit[1])));
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            Log.d(TAG, "Unable to parse proxy " + str + " " + e);
            return null;
        }
    }
}
