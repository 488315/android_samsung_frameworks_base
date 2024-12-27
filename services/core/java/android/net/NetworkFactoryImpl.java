package android.net;

import android.content.Context;
import android.hardware.biometrics.face.V1_0.OptionalBool$$ExternalSyntheticOutline0;
import android.os.Looper;
import android.os.Message;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Executor;

public final class NetworkFactoryImpl extends NetworkFactoryLegacyImpl {
    public static final NetworkScore INVINCIBLE_SCORE =
            new NetworkScore.Builder().setLegacyInt(1000).build();
    public final NetworkFactoryImpl$$ExternalSyntheticLambda0 mExecutor;
    public final Map mNetworkRequests;
    public final AnonymousClass1 mRequestCallback;
    public NetworkScore mScore;

    public final class NetworkRequestInfo {
        public final NetworkRequest request;
        public boolean requested = false;

        public NetworkRequestInfo(NetworkRequest networkRequest) {
            this.request = networkRequest;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("{");
            sb.append(this.request);
            sb.append(", requested=");
            return OptionalBool$$ExternalSyntheticOutline0.m("}", sb, this.requested);
        }
    }

    public NetworkFactoryImpl(
            NetworkFactory networkFactory,
            Looper looper,
            Context context,
            NetworkCapabilities networkCapabilities) {
        super(
                networkFactory,
                looper,
                context,
                networkCapabilities == null
                        ? NetworkCapabilities.Builder.withoutDefaultCapabilities().build()
                        : networkCapabilities);
        this.mNetworkRequests = new LinkedHashMap();
        this.mScore = new NetworkScore.Builder().setLegacyInt(0).build();
        this.mRequestCallback =
                new NetworkProvider
                        .NetworkOfferCallback() { // from class: android.net.NetworkFactoryImpl.1
                    public final void onNetworkNeeded(NetworkRequest networkRequest) {
                        NetworkFactoryImpl.this.handleAddRequest(networkRequest);
                    }

                    public final void onNetworkUnneeded(NetworkRequest networkRequest) {
                        NetworkFactoryImpl.this.handleRemoveRequest(networkRequest);
                    }
                };
        this.mExecutor =
                new Executor() { // from class:
                                 // android.net.NetworkFactoryImpl$$ExternalSyntheticLambda0
                    @Override // java.util.concurrent.Executor
                    public final void execute(Runnable runnable) {
                        NetworkFactoryImpl.this.post(runnable);
                    }
                };
    }

    @Override // android.net.NetworkFactoryLegacyImpl, android.net.NetworkFactoryShim
    public final void dump(PrintWriter printWriter) {
        printWriter.println(toString());
        Iterator it = ((LinkedHashMap) this.mNetworkRequests).values().iterator();
        while (it.hasNext()) {
            printWriter.println("  " + ((NetworkRequestInfo) it.next()));
        }
    }

    @Override // android.net.NetworkFactoryLegacyImpl, android.net.NetworkFactoryShim
    public final int getRequestCount() {
        return this.mNetworkRequests.size();
    }

    public final void handleAddRequest(NetworkRequest networkRequest) {
        NetworkRequestInfo networkRequestInfo =
                (NetworkRequestInfo) ((LinkedHashMap) this.mNetworkRequests).get(networkRequest);
        NetworkFactory networkFactory = this.mParent;
        if (networkRequestInfo == null) {
            networkFactory.log("got request " + networkRequest);
            networkRequestInfo = new NetworkRequestInfo(networkRequest);
            this.mNetworkRequests.put(networkRequest, networkRequestInfo);
        }
        if (networkFactory.acceptRequest(networkRequest)) {
            networkRequestInfo.requested = true;
            networkFactory.needNetworkFor(networkRequest);
        }
    }

    @Override // android.net.NetworkFactoryLegacyImpl, android.os.Handler
    public final void handleMessage(Message message) {
        int i = message.what;
        AnonymousClass1 anonymousClass1 = this.mRequestCallback;
        NetworkFactoryImpl$$ExternalSyntheticLambda0 networkFactoryImpl$$ExternalSyntheticLambda0 =
                this.mExecutor;
        NetworkFactory networkFactory = this.mParent;
        switch (i) {
            case 1:
                handleAddRequest((NetworkRequest) message.obj);
                break;
            case 2:
                handleRemoveRequest((NetworkRequest) message.obj);
                break;
            case 3:
                NetworkScore networkScore = (NetworkScore) message.obj;
                if (!this.mScore.equals(networkScore)) {
                    this.mScore = networkScore;
                    networkFactory.reevaluateAllRequests();
                    break;
                }
                break;
            case 4:
                NetworkCapabilities networkCapabilities = (NetworkCapabilities) message.obj;
                if (!networkCapabilities.equals(this.mCapabilityFilter)) {
                    this.mCapabilityFilter = networkCapabilities;
                    networkFactory.reevaluateAllRequests();
                    break;
                }
                break;
            case 5:
                this.mProvider.registerNetworkOffer(
                        this.mScore,
                        this.mCapabilityFilter,
                        networkFactoryImpl$$ExternalSyntheticLambda0,
                        anonymousClass1);
                break;
            case 6:
                this.mProvider.registerNetworkOffer(
                        INVINCIBLE_SCORE,
                        this.mCapabilityFilter,
                        networkFactoryImpl$$ExternalSyntheticLambda0,
                        anonymousClass1);
                break;
        }
    }

    public final void handleRemoveRequest(NetworkRequest networkRequest) {
        NetworkRequestInfo networkRequestInfo =
                (NetworkRequestInfo) ((LinkedHashMap) this.mNetworkRequests).get(networkRequest);
        if (networkRequestInfo != null) {
            this.mNetworkRequests.remove(networkRequest);
            if (networkRequestInfo.requested) {
                this.mParent.releaseNetworkFor(networkRequestInfo.request);
            }
        }
    }

    @Override // android.net.NetworkFactoryLegacyImpl, android.net.NetworkFactoryShim
    public final void reevaluateAllRequests() {
        NetworkProvider networkProvider = this.mProvider;
        if (networkProvider == null) {
            return;
        }
        networkProvider.registerNetworkOffer(
                this.mScore, this.mCapabilityFilter, this.mExecutor, this.mRequestCallback);
    }

    @Override // android.net.NetworkFactoryLegacyImpl, android.net.NetworkFactoryShim
    public final void register(String str) {
        register(str, false);
    }

    public final void register(String str, boolean z) {
        if (this.mProvider != null) {
            throw new IllegalStateException("A NetworkFactory must only be registered once");
        }
        this.mParent.log("Registering NetworkFactory");
        this.mProvider =
                new NetworkProvider(
                        this.mContext,
                        getLooper(),
                        str) { // from class: android.net.NetworkFactoryImpl.2
                    public final void onNetworkRequestWithdrawn(NetworkRequest networkRequest) {
                        NetworkFactoryImpl.this.handleRemoveRequest(networkRequest);
                    }

                    public final void onNetworkRequested(
                            NetworkRequest networkRequest, int i, int i2) {
                        NetworkFactoryImpl.this.handleAddRequest(networkRequest);
                    }
                };
        ((ConnectivityManager) this.mContext.getSystemService("connectivity"))
                .registerNetworkProvider(this.mProvider);
        if (z) {
            sendMessage(obtainMessage(6));
        } else {
            sendMessage(obtainMessage(5));
        }
    }

    @Override // android.net.NetworkFactoryShim
    public final void registerIgnoringScore(String str) {
        register(str, true);
    }

    @Override // android.net.NetworkFactoryLegacyImpl, android.net.NetworkFactoryShim
    public final void setCapabilityFilter(NetworkCapabilities networkCapabilities) {
        sendMessage(obtainMessage(4, new NetworkCapabilities(networkCapabilities)));
    }

    @Override // android.net.NetworkFactoryLegacyImpl, android.net.NetworkFactoryShim
    public final void setScoreFilter(int i) {
        setScoreFilter(new NetworkScore.Builder().setLegacyInt(i).build());
    }

    @Override // android.net.NetworkFactoryLegacyImpl, android.net.NetworkFactoryShim
    public final void setScoreFilter(NetworkScore networkScore) {
        sendMessage(obtainMessage(3, networkScore));
    }

    @Override // android.net.NetworkFactoryLegacyImpl, android.os.Handler
    public final String toString() {
        StringBuilder sb = new StringBuilder("providerId=");
        NetworkProvider networkProvider = this.mProvider;
        sb.append(
                networkProvider != null
                        ? Integer.valueOf(networkProvider.getProviderId())
                        : "null");
        sb.append(", ScoreFilter=");
        sb.append(this.mScore);
        sb.append(", Filter=");
        sb.append(this.mCapabilityFilter);
        sb.append(", requests=");
        sb.append(this.mNetworkRequests.size());
        return sb.toString();
    }
}
