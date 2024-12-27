package com.android.server.knox.zt.devicetrust;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.INetd;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.ServiceManager;
import com.android.internal.net.IOemNetd;
import com.android.server.ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.StringJoiner;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class OemNetdAdapterImpl implements OemNetdAdapter {
    public static final String TAG = "OemNetdAdapterImpl.ztd";
    public ConnectivityManager mConnectivityManager;
    public Context mContext;
    public EndpointMonitorNative mEndpointMonitor;
    public PacketTracingHandler mHandler;
    public HandlerThread mHandlerThread;
    public ConnectivityManager.NetworkCallback mNetworkCallback = new ConnectivityManager.NetworkCallback() { // from class: com.android.server.knox.zt.devicetrust.OemNetdAdapterImpl.1
        @Override // android.net.ConnectivityManager.NetworkCallback
        public final void onAvailable(Network network) {
            super.onAvailable(network);
            OemNetdAdapterImpl.this.sendMessageToHandler(1, OemNetdAdapterImpl.this.new NetworkInfo(network));
        }
    };

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class NetworkInfo {
        public String mInterfaceName;
        public Network mNetwork;
        public NetworkCapabilities mNetworkCapabilities;
        public int mNetworkIndex;

        public NetworkInfo(Network network) {
            NetworkInterface networkInterface;
            this.mNetwork = network;
            LinkProperties linkProperties = OemNetdAdapterImpl.this.getConnectivityManager().getLinkProperties(network);
            this.mInterfaceName = linkProperties == null ? "" : linkProperties.getInterfaceName();
            this.mNetworkCapabilities = OemNetdAdapterImpl.this.getConnectivityManager().getNetworkCapabilities(network);
            try {
                networkInterface = NetworkInterface.getByName(this.mInterfaceName);
            } catch (NullPointerException | SocketException unused) {
                networkInterface = null;
            }
            this.mNetworkIndex = networkInterface != null ? networkInterface.getIndex() : -1;
        }

        public final int getInterfaceIndex() {
            return this.mNetworkIndex;
        }

        public final String getInterfaceName() {
            return this.mInterfaceName;
        }

        public final boolean hasEthernetHeader() {
            return !isCellularNetwork();
        }

        public final boolean isCellularNetwork() {
            NetworkCapabilities networkCapabilities = this.mNetworkCapabilities;
            if (networkCapabilities == null) {
                return false;
            }
            return networkCapabilities.hasTransport(0);
        }

        public final String toString() {
            return new StringJoiner(", ", "[ ", " ]").add("id: " + this.mNetwork).add("name: " + this.mInterfaceName).add("index: " + this.mNetworkIndex).toString();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PacketTracingHandler extends Handler {
        public static final int DISABLE = 2;
        public static final int ENABLE = 1;

        public PacketTracingHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            Object obj = message.obj;
            if (obj instanceof NetworkInfo) {
                NetworkInfo networkInfo = (NetworkInfo) obj;
                int i = message.what;
                if (i == 1) {
                    OemNetdAdapterImpl.this.updateNetworkInterfaceData(networkInfo.mNetworkIndex, networkInfo.hasEthernetHeader());
                    OemNetdAdapterImpl.this.enablePacketTracing(networkInfo.mInterfaceName);
                } else {
                    if (i != 2) {
                        return;
                    }
                    OemNetdAdapterImpl.this.disablePacketTracing(networkInfo.mInterfaceName);
                }
            }
        }
    }

    public OemNetdAdapterImpl() {
    }

    public OemNetdAdapterImpl(Context context, EndpointMonitorNative endpointMonitorNative) {
        this.mContext = context;
        this.mEndpointMonitor = endpointMonitorNative;
        initializeHandlerThread();
    }

    @Override // com.android.server.knox.zt.devicetrust.OemNetdAdapter
    public final int attachProbes(int i) {
        if ((i & 64) <= 0) {
            return -2;
        }
        sendMessageToHandler(1, new NetworkInfo(getConnectivityManager().getActiveNetwork()));
        registerNetworkCallback();
        return 0;
    }

    @Override // com.android.server.knox.zt.devicetrust.OemNetdAdapter
    public final int detachProbes(int i) {
        if ((i & 64) <= 0) {
            return -2;
        }
        sendMessageToHandler(2, new NetworkInfo(getConnectivityManager().getActiveNetwork()));
        unregisterNetworkCallback();
        return 0;
    }

    /* JADX WARN: Removed duplicated region for block: B:5:0x0012  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int disablePacketTracing(java.lang.String r3) {
        /*
            r2 = this;
            com.android.internal.net.IOemNetd r2 = r2.getOemNetdService()
            if (r2 == 0) goto Lf
            int r2 = r2.disableTlsPacketTracing(r3)     // Catch: android.os.RemoteException -> Lb
            goto L10
        Lb:
            r2 = move-exception
            r2.printStackTrace()
        Lf:
            r2 = -6
        L10:
            if (r2 == 0) goto L1b
            java.lang.String r3 = com.android.server.knox.zt.devicetrust.OemNetdAdapterImpl.TAG
            java.lang.String r0 = "Failed to disable packet tracing("
            java.lang.String r1 = ")"
            com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticOutline0.m(r2, r0, r1, r3)
        L1b:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.knox.zt.devicetrust.OemNetdAdapterImpl.disablePacketTracing(java.lang.String):int");
    }

    /* JADX WARN: Removed duplicated region for block: B:5:0x0012  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int enablePacketTracing(java.lang.String r3) {
        /*
            r2 = this;
            com.android.internal.net.IOemNetd r2 = r2.getOemNetdService()
            if (r2 == 0) goto Lf
            int r2 = r2.enableTlsPacketTracing(r3)     // Catch: android.os.RemoteException -> Lb
            goto L10
        Lb:
            r2 = move-exception
            r2.printStackTrace()
        Lf:
            r2 = -6
        L10:
            if (r2 == 0) goto L1b
            java.lang.String r3 = com.android.server.knox.zt.devicetrust.OemNetdAdapterImpl.TAG
            java.lang.String r0 = "Failed to enable packet tracing("
            java.lang.String r1 = ")"
            com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticOutline0.m(r2, r0, r1, r3)
        L1b:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.knox.zt.devicetrust.OemNetdAdapterImpl.enablePacketTracing(java.lang.String):int");
    }

    public final ConnectivityManager getConnectivityManager() {
        Context context = this.mContext;
        if (context != null && this.mConnectivityManager == null) {
            this.mConnectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        }
        return this.mConnectivityManager;
    }

    public final IOemNetd getOemNetdService() {
        try {
            return IOemNetd.Stub.asInterface(INetd.Stub.asInterface(ServiceManager.getService("netd")).getOemNetd());
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }

    public final void initializeHandlerThread() {
        HandlerThread handlerThread = new HandlerThread("PacketTracingHandler", 10);
        this.mHandlerThread = handlerThread;
        handlerThread.start();
        this.mHandler = new PacketTracingHandler(this.mHandlerThread.getLooper());
    }

    public final boolean registerNetworkCallback() {
        if (getConnectivityManager() == null) {
            return false;
        }
        getConnectivityManager().registerNetworkCallback(new NetworkRequest.Builder().addTransportType(1).addTransportType(0).addCapability(12).build(), this.mNetworkCallback);
        return true;
    }

    public final void sendMessageToHandler(int i, NetworkInfo networkInfo) {
        PacketTracingHandler packetTracingHandler = this.mHandler;
        if (packetTracingHandler != null) {
            this.mHandler.sendMessage(Message.obtain(packetTracingHandler, i, networkInfo));
        }
    }

    public final void startTracingPackets(NetworkInfo networkInfo) {
        sendMessageToHandler(1, networkInfo);
    }

    public final void stopTracingPackets(NetworkInfo networkInfo) {
        sendMessageToHandler(2, networkInfo);
    }

    public final boolean unregisterNetworkCallback() {
        if (getConnectivityManager() == null) {
            return false;
        }
        getConnectivityManager().unregisterNetworkCallback(this.mNetworkCallback);
        return true;
    }

    public final void updateNetworkInterfaceData(int i, boolean z) {
        if (this.mEndpointMonitor.updateNetworkInterfaceData(i, z ? 1 : 0) != 0) {
            ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(i, "Failed to set ethernet status for interface index=", TAG);
        }
    }
}
