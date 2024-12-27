package android.net;

import android.content.Context;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
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
import java.util.List;

public class KnoxVpnProxySelector extends ProxySelector {
    private static final boolean DBG = Debug.semIsProductDev();
    private static final String PROXY = "PROXY ";
    private static final String SOCKS = "SOCKS ";
    private static final String TAG = "KnoxVpnProxySelector";

    private IVpnManager getVpnManagerService() {
        return IVpnManager.Stub.asInterface(
                ServiceManager.getService(Context.VPN_MANAGEMENT_SERVICE));
    }

    @Override // java.net.ProxySelector
    public List<java.net.Proxy> select(URI uri) {
        Log.d(TAG, "selection of proxy is being reached for the caller " + Process.myUid());
        List<java.net.Proxy> ret = Lists.newArrayList();
        try {
            String urlString = uri.toURL().toString();
            if (DBG) {
                Log.d(
                        TAG,
                        "pac url being recieved is "
                                + urlString
                                + "for the caller "
                                + Process.myUid());
            }
            String[] proxyInfo = getVpnManagerService().getProxyInfoForUid(Process.myUid());
            String host = proxyInfo[0];
            if (DBG) {
                Log.d(TAG, "host value is " + host + "for caller " + Process.myUid());
            }
            String port = proxyInfo[1];
            if (DBG) {
                Log.d(TAG, "port value is " + port + "for caller " + Process.myUid());
            }
            if (host != null && port != null) {
                int intPort = Integer.parseInt(port);
                java.net.Proxy proxy =
                        new java.net.Proxy(
                                Proxy.Type.HTTP, InetSocketAddress.createUnresolved(host, intPort));
                Log.d(TAG, "valid knox vpn proxy is added for caller" + Process.myUid());
                ret.add(proxy);
            }
            int intPort2 = ret.size();
            if (intPort2 == 0) {
                Log.d(TAG, "in-valid knox vpn proxy is added for caller" + Process.myUid());
                ret.add(java.net.Proxy.NO_PROXY);
            }
        } catch (RuntimeException e) {
            Log.e(TAG, "RuntimeException occured for the caller " + Process.myUid());
            if (ret.size() == 0) {
                Log.d(
                        TAG,
                        "in-valid knox vpn proxy is added for caller during exception "
                                + Process.myUid());
                ret.add(java.net.Proxy.NO_PROXY);
            }
        } catch (Exception e2) {
            Log.e(TAG, "Exception occured for the caller " + Process.myUid());
            if (ret.size() == 0) {
                Log.d(
                        TAG,
                        "in-valid knox vpn proxy is added for caller during exception "
                                + Process.myUid());
                ret.add(java.net.Proxy.NO_PROXY);
            }
        }
        return ret;
    }

    private static List<java.net.Proxy> parseResponse(String response) {
        java.net.Proxy proxy;
        String[] split = response.split(NavigationBarInflaterView.GRAVITY_SEPARATOR);
        List<java.net.Proxy> ret = Lists.newArrayList();
        for (String s : split) {
            Log.d(TAG, "s value is " + s);
            String trimmed = s.trim();
            if (trimmed.equals("DIRECT")) {
                ret.add(java.net.Proxy.NO_PROXY);
            } else if (trimmed.startsWith(PROXY)) {
                java.net.Proxy proxy2 =
                        proxyFromHostPort(Proxy.Type.HTTP, trimmed.substring(PROXY.length()));
                if (proxy2 != null) {
                    ret.add(proxy2);
                }
            } else if (trimmed.startsWith(SOCKS)
                    && (proxy =
                                    proxyFromHostPort(
                                            Proxy.Type.SOCKS, trimmed.substring(SOCKS.length())))
                            != null) {
                ret.add(proxy);
            }
        }
        if (ret.size() == 0) {
            Log.d(TAG, "ret value is 0");
            ret.add(java.net.Proxy.NO_PROXY);
        }
        return ret;
    }

    private static java.net.Proxy proxyFromHostPort(Proxy.Type type, String hostPortString) {
        try {
            String[] hostPort = hostPortString.split(":");
            String host = hostPort[0];
            Log.d(TAG, "host value is " + host);
            int port = Integer.parseInt(hostPort[1]);
            Log.d(TAG, "port value is " + port);
            return new java.net.Proxy(type, InetSocketAddress.createUnresolved(host, port));
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            Log.d(TAG, "Unable to parse proxy " + hostPortString + " " + e);
            return null;
        }
    }

    @Override // java.net.ProxySelector
    public void connectFailed(URI uri, SocketAddress address, IOException failure) {}
}
