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

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class VcnNetworkProvider extends NetworkProvider {
    public final Context mContext;
    public final Dependencies mDeps;
    public final Handler mHandler;
    public final Set mListeners;
    public final Set mRequests;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public class Dependencies {}

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface NetworkRequestListener {}

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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
