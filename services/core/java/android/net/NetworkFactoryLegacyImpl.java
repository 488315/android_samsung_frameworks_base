package android.net;

import android.content.Context;
import android.hardware.biometrics.face.V1_0.OptionalBool$$ExternalSyntheticOutline0;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public class NetworkFactoryLegacyImpl extends Handler implements NetworkFactoryShim {
    public NetworkCapabilities mCapabilityFilter;
    public final Context mContext;
    public final Map mNetworkRequests;
    public final NetworkFactory mParent;
    public NetworkProvider mProvider;
    public int mScore;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class NetworkRequestInfo {
        public int providerId;
        public final NetworkRequest request;
        public boolean requested = false;
        public int score;

        public NetworkRequestInfo(NetworkRequest networkRequest, int i, int i2) {
            this.request = networkRequest;
            this.score = i;
            this.providerId = i2;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("{");
            sb.append(this.request);
            sb.append(", score=");
            sb.append(this.score);
            sb.append(", requested=");
            return OptionalBool$$ExternalSyntheticOutline0.m("}", sb, this.requested);
        }
    }

    public NetworkFactoryLegacyImpl(
            NetworkFactory networkFactory,
            Looper looper,
            Context context,
            NetworkCapabilities networkCapabilities) {
        super(looper);
        this.mNetworkRequests = new LinkedHashMap();
        this.mProvider = null;
        this.mParent = networkFactory;
        this.mContext = context;
        this.mCapabilityFilter = networkCapabilities;
    }

    public void dump(PrintWriter printWriter) {
        printWriter.println(toString());
        Iterator it = ((LinkedHashMap) this.mNetworkRequests).values().iterator();
        while (it.hasNext()) {
            printWriter.println("  " + ((NetworkRequestInfo) it.next()));
        }
    }

    public final void evalRequest(NetworkRequestInfo networkRequestInfo) {
        boolean z = networkRequestInfo.requested;
        NetworkFactory networkFactory = this.mParent;
        if (!z
                && ((networkRequestInfo.score < this.mScore
                                || networkRequestInfo.providerId == this.mProvider.getProviderId())
                        && networkRequestInfo.request.canBeSatisfiedBy(this.mCapabilityFilter)
                        && networkFactory.acceptRequest(networkRequestInfo.request))) {
            networkFactory.needNetworkFor(networkRequestInfo.request);
            networkRequestInfo.requested = true;
        } else if (networkRequestInfo.requested) {
            if ((networkRequestInfo.score <= this.mScore
                            || networkRequestInfo.providerId == this.mProvider.getProviderId())
                    && networkRequestInfo.request.canBeSatisfiedBy(this.mCapabilityFilter)
                    && networkFactory.acceptRequest(networkRequestInfo.request)) {
                return;
            }
            networkFactory.releaseNetworkFor(networkRequestInfo.request);
            networkRequestInfo.requested = false;
        }
    }

    public final void evalRequests() {
        Iterator it = ((LinkedHashMap) this.mNetworkRequests).values().iterator();
        while (it.hasNext()) {
            evalRequest((NetworkRequestInfo) it.next());
        }
    }

    public int getRequestCount() {
        return this.mNetworkRequests.size();
    }

    public void handleAddRequest(NetworkRequest networkRequest, int i, int i2) {
        NetworkRequestInfo networkRequestInfo =
                (NetworkRequestInfo) ((LinkedHashMap) this.mNetworkRequests).get(networkRequest);
        if (networkRequestInfo == null) {
            this.mParent.log(
                    "got request " + networkRequest + " with score " + i + " and providerId " + i2);
            networkRequestInfo = new NetworkRequestInfo(networkRequest, i, i2);
            this.mNetworkRequests.put(networkRequest, networkRequestInfo);
        } else {
            networkRequestInfo.score = i;
            networkRequestInfo.providerId = i2;
        }
        evalRequest(networkRequestInfo);
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        int i = message.what;
        if (i == 1) {
            handleAddRequest((NetworkRequest) message.obj, message.arg1, message.arg2);
            return;
        }
        if (i == 2) {
            handleRemoveRequest$1((NetworkRequest) message.obj);
            return;
        }
        if (i == 3) {
            this.mScore = message.arg1;
            evalRequests();
        } else {
            if (i != 4) {
                return;
            }
            this.mCapabilityFilter = (NetworkCapabilities) message.obj;
            evalRequests();
        }
    }

    public final void handleRemoveRequest$1(NetworkRequest networkRequest) {
        NetworkRequestInfo networkRequestInfo =
                (NetworkRequestInfo) ((LinkedHashMap) this.mNetworkRequests).get(networkRequest);
        if (networkRequestInfo != null) {
            this.mNetworkRequests.remove(networkRequest);
            if (networkRequestInfo.requested) {
                this.mParent.releaseNetworkFor(networkRequestInfo.request);
            }
        }
    }

    public void reevaluateAllRequests() {
        post(
                new Runnable() { // from class:
                                 // android.net.NetworkFactoryLegacyImpl$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        NetworkFactoryLegacyImpl.this.evalRequests();
                    }
                });
    }

    public void register(String str) {
        if (this.mProvider != null) {
            throw new IllegalStateException("A NetworkFactory must only be registered once");
        }
        this.mParent.log("Registering NetworkFactory");
        this.mProvider =
                new NetworkProvider(
                        this.mContext,
                        getLooper(),
                        str) { // from class: android.net.NetworkFactoryLegacyImpl.1
                    public final void onNetworkRequestWithdrawn(NetworkRequest networkRequest) {
                        NetworkFactoryLegacyImpl.this.handleRemoveRequest$1(networkRequest);
                    }

                    public final void onNetworkRequested(
                            NetworkRequest networkRequest, int i, int i2) {
                        NetworkFactoryLegacyImpl.this.handleAddRequest(networkRequest, i, i2);
                    }
                };
        ((ConnectivityManager) this.mContext.getSystemService("connectivity"))
                .registerNetworkProvider(this.mProvider);
    }

    public void setCapabilityFilter(NetworkCapabilities networkCapabilities) {
        sendMessage(obtainMessage(4, new NetworkCapabilities(networkCapabilities)));
    }

    public void setScoreFilter(int i) {
        sendMessage(obtainMessage(3, i, 0));
    }

    public void setScoreFilter(NetworkScore networkScore) {
        setScoreFilter(networkScore.getLegacyInt());
    }

    @Override // android.os.Handler
    public String toString() {
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
