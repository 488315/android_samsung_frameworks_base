package android.net;

import android.content.Context;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.android.modules.utils.build.SdkLevel;

import java.io.FileDescriptor;
import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public class NetworkFactory {
    public static final int CMD_CANCEL_REQUEST = 2;
    public static final int CMD_REQUEST_NETWORK = 1;
    static final boolean DBG = true;
    static final boolean VDBG = false;
    private final String LOG_TAG;
    final NetworkFactoryShim mImpl;
    private int mRefCount = 0;

    public NetworkFactory(
            Looper looper, Context context, String str, NetworkCapabilities networkCapabilities) {
        this.LOG_TAG = str;
        if (SdkLevel.isAtLeastS()) {
            this.mImpl = new NetworkFactoryImpl(this, looper, context, networkCapabilities);
        } else {
            this.mImpl = new NetworkFactoryLegacyImpl(this, looper, context, networkCapabilities);
        }
    }

    public boolean acceptRequest(NetworkRequest networkRequest) {
        return true;
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        this.mImpl.dump(printWriter);
    }

    public final Looper getLooper() {
        return this.mImpl.getLooper();
    }

    public NetworkProvider getProvider() {
        return ((NetworkFactoryLegacyImpl) this.mImpl).mProvider;
    }

    public int getRequestCount() {
        return this.mImpl.getRequestCount();
    }

    public int getSerialNumber() {
        return ((NetworkFactoryLegacyImpl) this.mImpl).mProvider.getProviderId();
    }

    public void log(String str) {
        Log.d(this.LOG_TAG, str);
    }

    public void needNetworkFor(NetworkRequest networkRequest) {
        int i = this.mRefCount + 1;
        this.mRefCount = i;
        if (i == 1) {
            startNetwork();
        }
    }

    public Message obtainMessage(int i, int i2, int i3, Object obj) {
        return this.mImpl.obtainMessage(i, i2, i3, obj);
    }

    public final void reevaluateAllRequests() {
        this.mImpl.reevaluateAllRequests();
    }

    public void register() {
        this.mImpl.register(this.LOG_TAG);
    }

    public void registerIgnoringScore() {
        this.mImpl.registerIgnoringScore(this.LOG_TAG);
    }

    public void releaseNetworkFor(NetworkRequest networkRequest) {
        int i = this.mRefCount - 1;
        this.mRefCount = i;
        if (i == 0) {
            stopNetwork();
        }
    }

    public void releaseRequestAsUnfulfillableByAnyFactory(final NetworkRequest networkRequest) {
        final NetworkFactoryLegacyImpl networkFactoryLegacyImpl =
                (NetworkFactoryLegacyImpl) this.mImpl;
        networkFactoryLegacyImpl.getClass();
        networkFactoryLegacyImpl.post(
                new Runnable() { // from class:
                    // android.net.NetworkFactoryLegacyImpl$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        NetworkFactoryLegacyImpl networkFactoryLegacyImpl2 =
                                NetworkFactoryLegacyImpl.this;
                        NetworkRequest networkRequest2 = networkRequest;
                        networkFactoryLegacyImpl2.getClass();
                        NetworkFactory networkFactory = networkFactoryLegacyImpl2.mParent;
                        networkFactory.log(
                                "releaseRequestAsUnfulfillableByAnyFactory: " + networkRequest2);
                        NetworkProvider networkProvider = networkFactoryLegacyImpl2.mProvider;
                        if (networkProvider == null) {
                            networkFactory.log(
                                    "Ignoring attempt to release unregistered request as"
                                        + " unfulfillable");
                        } else {
                            networkProvider.declareNetworkRequestUnfulfillable(networkRequest2);
                        }
                    }
                });
    }

    public void setCapabilityFilter(NetworkCapabilities networkCapabilities) {
        this.mImpl.setCapabilityFilter(networkCapabilities);
    }

    @Deprecated
    public void setScoreFilter(int i) {
        this.mImpl.setScoreFilter(i);
    }

    public void setScoreFilter(NetworkScore networkScore) {
        this.mImpl.setScoreFilter(networkScore);
    }

    public void startNetwork() {}

    public void stopNetwork() {}

    public void terminate() {
        NetworkFactoryLegacyImpl networkFactoryLegacyImpl = (NetworkFactoryLegacyImpl) this.mImpl;
        if (networkFactoryLegacyImpl.mProvider == null) {
            throw new IllegalStateException("This NetworkFactory was never registered");
        }
        networkFactoryLegacyImpl.mParent.log("Unregistering NetworkFactory");
        ((ConnectivityManager) networkFactoryLegacyImpl.mContext.getSystemService("connectivity"))
                .unregisterNetworkProvider(networkFactoryLegacyImpl.mProvider);
        networkFactoryLegacyImpl.removeCallbacksAndMessages(null);
    }

    public String toString() {
        return "{" + this.LOG_TAG + " " + this.mImpl.toString() + "}";
    }
}
