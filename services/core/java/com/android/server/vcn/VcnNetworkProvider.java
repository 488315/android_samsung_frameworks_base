package com.android.server.vcn;

import android.content.Context;
import android.net.NetworkProvider;
import android.net.NetworkRequest;
import android.os.Handler;
import android.os.Looper;
import android.util.ArraySet;

import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

public final class VcnNetworkProvider extends NetworkProvider {
    public final Context mContext;
    public final Dependencies mDeps;
    public final Handler mHandler;
    public final Set mListeners;
    public final Set mRequests;

    public class Dependencies {}

    public interface NetworkRequestListener {}

    public VcnNetworkProvider(Context context, Looper looper, Dependencies dependencies) {
        super(context, looper, "VcnNetworkProvider");
        Objects.requireNonNull(context, "Missing context");
        Objects.requireNonNull(looper, "Missing looper");
        this.mListeners = new ArraySet();
        this.mRequests = new ArraySet();
        this.mContext = context;
        this.mHandler = new Handler(looper);
        Objects.requireNonNull(dependencies, "Missing dependencies");
        this.mDeps = dependencies;
    }

    public void registerListener(NetworkRequestListener networkRequestListener) {
        ((ArraySet) this.mListeners).add(networkRequestListener);
        resendAllRequests(networkRequestListener);
    }

    public void resendAllRequests(NetworkRequestListener networkRequestListener) {
        Iterator it = ((ArraySet) this.mRequests).iterator();
        while (it.hasNext()) {
            NetworkRequest networkRequest = (NetworkRequest) it.next();
            Vcn.VcnNetworkRequestListener vcnNetworkRequestListener =
                    (Vcn.VcnNetworkRequestListener) networkRequestListener;
            vcnNetworkRequestListener.getClass();
            Objects.requireNonNull(networkRequest, "Missing request");
            Vcn vcn = Vcn.this;
            vcn.sendMessage(vcn.obtainMessage(1, networkRequest));
        }
    }

    public void unregisterListener(NetworkRequestListener networkRequestListener) {
        ((ArraySet) this.mListeners).remove(networkRequestListener);
    }
}
