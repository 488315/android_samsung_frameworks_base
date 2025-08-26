package android.net;

import android.os.Process;
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
public class KnoxZtnaProxySelector extends ProxySelector {
    private static final String TAG = "knoxNwFilter-KnoxZtnaProxySelector";

    @Override // java.net.ProxySelector
    public List<java.net.Proxy> select(URI uri) {
        ArrayList arrayListNewArrayList = Lists.newArrayList();
        String property = System.getProperty("http.proxyPort");
        Log.d(TAG, "Proxy port configured is " + property + "for the caller " + Process.myUid());
        arrayListNewArrayList.add(new java.net.Proxy(Proxy.Type.HTTP, InetSocketAddress.createUnresolved("::1", Integer.parseInt(property))));
        return arrayListNewArrayList;
    }

    @Override // java.net.ProxySelector
    public void connectFailed(URI uri, SocketAddress socketAddress, IOException iOException) {
        Log.d(TAG, "connection failed happened");
    }
}
