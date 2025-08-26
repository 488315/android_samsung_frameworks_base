package android.net.metrics;

import android.annotation.SystemApi;
import android.net.ConnectivityMetricsEvent;
import android.net.IIpConnectivityMetrics;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import com.android.internal.util.BitUtils;

@SystemApi
@Deprecated
/* loaded from: classes3.dex */
public class IpConnectivityLog {
    private static final boolean DBG = false;
    public static final String SERVICE_NAME = "connmetrics";
    private static final String TAG = "IpConnectivityLog";
    private IIpConnectivityMetrics mService;

    public interface Event extends Parcelable {
    }

    @SystemApi
    public IpConnectivityLog() {
    }

    public IpConnectivityLog(IIpConnectivityMetrics iIpConnectivityMetrics) {
        this.mService = iIpConnectivityMetrics;
    }

    private boolean checkLoggerService() {
        if (this.mService != null) {
            return true;
        }
        IIpConnectivityMetrics iIpConnectivityMetricsAsInterface = IIpConnectivityMetrics.Stub.asInterface(ServiceManager.getService(SERVICE_NAME));
        if (iIpConnectivityMetricsAsInterface == null) {
            return false;
        }
        this.mService = iIpConnectivityMetricsAsInterface;
        return true;
    }

    public boolean log(ConnectivityMetricsEvent connectivityMetricsEvent) {
        if (!checkLoggerService()) {
            return false;
        }
        if (connectivityMetricsEvent.timestamp == 0) {
            connectivityMetricsEvent.timestamp = System.currentTimeMillis();
        }
        try {
            return this.mService.logEvent(connectivityMetricsEvent) >= 0;
        } catch (RemoteException e) {
            Log.e(TAG, "Error logging event", e);
            return false;
        }
    }

    public boolean log(long j, Event event) {
        ConnectivityMetricsEvent connectivityMetricsEventMakeEv = makeEv(event);
        connectivityMetricsEventMakeEv.timestamp = j;
        return log(connectivityMetricsEventMakeEv);
    }

    public boolean log(String str, Event event) {
        ConnectivityMetricsEvent connectivityMetricsEventMakeEv = makeEv(event);
        connectivityMetricsEventMakeEv.ifname = str;
        return log(connectivityMetricsEventMakeEv);
    }

    public boolean log(Network network, int[] iArr, Event event) {
        return log(network.getNetId(), iArr, event);
    }

    public boolean log(int i, int[] iArr, Event event) {
        ConnectivityMetricsEvent connectivityMetricsEventMakeEv = makeEv(event);
        connectivityMetricsEventMakeEv.netId = i;
        connectivityMetricsEventMakeEv.transports = BitUtils.packBits(iArr);
        return log(connectivityMetricsEventMakeEv);
    }

    public boolean log(Event event) {
        return log(makeEv(event));
    }

    public boolean logDefaultNetworkValidity(boolean z) {
        if (!checkLoggerService()) {
            return false;
        }
        try {
            this.mService.logDefaultNetworkValidity(z);
            return true;
        } catch (RemoteException unused) {
            return true;
        }
    }

    public boolean logDefaultNetworkEvent(Network network, int i, boolean z, LinkProperties linkProperties, NetworkCapabilities networkCapabilities, Network network2, int i2, LinkProperties linkProperties2, NetworkCapabilities networkCapabilities2) {
        if (!checkLoggerService()) {
            return false;
        }
        try {
            this.mService.logDefaultNetworkEvent(network, i, z, linkProperties, networkCapabilities, network2, i2, linkProperties2, networkCapabilities2);
            return true;
        } catch (RemoteException unused) {
            return true;
        }
    }

    private static ConnectivityMetricsEvent makeEv(Event event) {
        ConnectivityMetricsEvent connectivityMetricsEvent = new ConnectivityMetricsEvent();
        connectivityMetricsEvent.data = event;
        return connectivityMetricsEvent;
    }
}
