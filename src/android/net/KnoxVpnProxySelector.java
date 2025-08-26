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
    public List<java.net.Proxy> select(URI uri) throws NumberFormatException {
        Log.d(TAG, "selection of proxy is being reached for the caller " + Process.myUid());
        ArrayList arrayListNewArrayList = Lists.newArrayList();
        try {
            String string = uri.toURL().toString();
            boolean z = DBG;
            if (z) {
                Log.d(TAG, "pac url being recieved is " + string + "for the caller " + Process.myUid());
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
                arrayListNewArrayList.add(proxy);
            }
            if (arrayListNewArrayList.size() == 0) {
                Log.d(TAG, "in-valid knox vpn proxy is added for caller" + Process.myUid());
                arrayListNewArrayList.add(java.net.Proxy.NO_PROXY);
                return arrayListNewArrayList;
            }
        } catch (RuntimeException unused) {
            Log.e(TAG, "RuntimeException occured for the caller " + Process.myUid());
            if (arrayListNewArrayList.size() == 0) {
                Log.d(TAG, "in-valid knox vpn proxy is added for caller during exception " + Process.myUid());
                arrayListNewArrayList.add(java.net.Proxy.NO_PROXY);
            }
        } catch (Exception unused2) {
            Log.e(TAG, "Exception occured for the caller " + Process.myUid());
            if (arrayListNewArrayList.size() == 0) {
                Log.d(TAG, "in-valid knox vpn proxy is added for caller during exception " + Process.myUid());
                arrayListNewArrayList.add(java.net.Proxy.NO_PROXY);
            }
        }
        return arrayListNewArrayList;
    }

    private static List<java.net.Proxy> parseResponse(String str) throws NumberFormatException {
        java.net.Proxy proxyProxyFromHostPort;
        String[] strArrSplit = str.split(NavigationBarInflaterView.GRAVITY_SEPARATOR);
        ArrayList arrayListNewArrayList = Lists.newArrayList();
        for (String str2 : strArrSplit) {
            Log.d(TAG, "s value is " + str2);
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
            Log.d(TAG, "ret value is 0");
            arrayListNewArrayList.add(java.net.Proxy.NO_PROXY);
        }
        return arrayListNewArrayList;
    }

    private static java.net.Proxy proxyFromHostPort(Proxy.Type type, String str) throws NumberFormatException {
        try {
            String[] strArrSplit = str.split(":");
            String str2 = strArrSplit[0];
            Log.d(TAG, "host value is " + str2);
            int i = Integer.parseInt(strArrSplit[1]);
            Log.d(TAG, "port value is " + i);
            return new java.net.Proxy(type, InetSocketAddress.createUnresolved(str2, i));
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            Log.d(TAG, "Unable to parse proxy " + str + " " + e);
            return null;
        }
    }
}
