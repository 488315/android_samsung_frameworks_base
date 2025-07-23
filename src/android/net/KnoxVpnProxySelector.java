package android.net;

import android.content.Context;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.net.IVpnManager;
import android.os.Debug;
import android.os.Process;
import android.os.ServiceManager;
import android.util.Log;
import com.google.android.collect.Lists;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class KnoxVpnProxySelector extends ProxySelector {
    private static final boolean DBG = Debug.semIsProductDev();
    private static final String PROXY = "PROXY ";
    private static final String SOCKS = "SOCKS ";
    private static final String TAG = "KnoxVpnProxySelector";

    @Override // java.net.ProxySelector
    public void connectFailed(URI uri, SocketAddress socketAddress, IOException iOException) {
    }

    private IVpnManager getVpnManagerService() {
        return IVpnManager.Stub.asInterface(ServiceManager.getService(Context.VPN_MANAGEMENT_SERVICE));
    }

    @Override // java.net.ProxySelector
    public List<java.net.Proxy> select(URI uri) {
        Log.d(TAG, "selection of proxy is being reached for the caller " + Process.myUid());
        ArrayList newArrayList = Lists.newArrayList();
        try {
            String url = uri.toURL().toString();
            boolean z = DBG;
            if (z) {
                Log.d(TAG, "pac url being recieved is " + url + "for the caller " + Process.myUid());
            }
            String[] proxyInfoForUid = getVpnManagerService().getProxyInfoForUid(Process.myUid());
            String str = proxyInfoForUid[0];
            if (z) {
                Log.d(TAG, "host value is " + str + "for caller " + Process.myUid());
            }
            String str2 = proxyInfoForUid[1];
            if (z) {
                Log.d(TAG, "port value is " + str2 + "for caller " + Process.myUid());
            }
            if (str != null && str2 != null) {
                java.net.Proxy proxy = new java.net.Proxy(Proxy.Type.HTTP, InetSocketAddress.createUnresolved(str, Integer.parseInt(str2)));
                Log.d(TAG, "valid knox vpn proxy is added for caller" + Process.myUid());
                newArrayList.add(proxy);
            }
            if (newArrayList.size() == 0) {
                Log.d(TAG, "in-valid knox vpn proxy is added for caller" + Process.myUid());
                newArrayList.add(java.net.Proxy.NO_PROXY);
                return newArrayList;
            }
        } catch (RuntimeException unused) {
            Log.e(TAG, "RuntimeException occured for the caller " + Process.myUid());
            if (newArrayList.size() == 0) {
                Log.d(TAG, "in-valid knox vpn proxy is added for caller during exception " + Process.myUid());
                newArrayList.add(java.net.Proxy.NO_PROXY);
            }
        } catch (Exception unused2) {
            Log.e(TAG, "Exception occured for the caller " + Process.myUid());
            if (newArrayList.size() == 0) {
                Log.d(TAG, "in-valid knox vpn proxy is added for caller during exception " + Process.myUid());
                newArrayList.add(java.net.Proxy.NO_PROXY);
            }
        }
        return newArrayList;
    }

    private static List<java.net.Proxy> parseResponse(String str) {
        java.net.Proxy proxyFromHostPort;
        String[] split = str.split(NavigationBarInflaterView.GRAVITY_SEPARATOR);
        ArrayList newArrayList = Lists.newArrayList();
        for (String str2 : split) {
            Log.d(TAG, "s value is " + str2);
            String trim = str2.trim();
            if (trim.equals("DIRECT")) {
                newArrayList.add(java.net.Proxy.NO_PROXY);
            } else if (trim.startsWith(PROXY)) {
                java.net.Proxy proxyFromHostPort2 = proxyFromHostPort(Proxy.Type.HTTP, trim.substring(6));
                if (proxyFromHostPort2 != null) {
                    newArrayList.add(proxyFromHostPort2);
                }
            } else if (trim.startsWith(SOCKS) && (proxyFromHostPort = proxyFromHostPort(Proxy.Type.SOCKS, trim.substring(6))) != null) {
                newArrayList.add(proxyFromHostPort);
            }
        }
        if (newArrayList.size() == 0) {
            Log.d(TAG, "ret value is 0");
            newArrayList.add(java.net.Proxy.NO_PROXY);
        }
        return newArrayList;
    }

    private static java.net.Proxy proxyFromHostPort(Proxy.Type type, String str) {
        try {
            String[] split = str.split(":");
            String str2 = split[0];
            Log.d(TAG, "host value is " + str2);
            int parseInt = Integer.parseInt(split[1]);
            Log.d(TAG, "port value is " + parseInt);
            return new java.net.Proxy(type, InetSocketAddress.createUnresolved(str2, parseInt));
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            Log.d(TAG, "Unable to parse proxy " + str + " " + e);
            return null;
        }
    }
}
