package com.android.server.enterprise.adapterlayer;

import android.net.IDnsResolver;
import android.net.ResolverOptionsParcel;
import android.net.ResolverParamsParcel;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.ServiceSpecificException;
import android.util.Log;
import com.android.server.enterprise.adapter.IDnsResolverAdapter;

/* loaded from: classes2.dex */
public class DnsResolverAdapter implements IDnsResolverAdapter {
    public static final String TAG = "DnsResolverAdapter";
    public static DnsResolverAdapter sInstance;
    public static final Object sLock = new Object();
    public volatile IDnsResolver mDnsResolver;

    public interface CheckedRemoteRequest {
        void execute(IDnsResolver iDnsResolver);
    }

    public static DnsResolverAdapter getInstance() {
        DnsResolverAdapter dnsResolverAdapter = sInstance;
        if (dnsResolverAdapter == null) {
            synchronized (sLock) {
                dnsResolverAdapter = sInstance;
                if (dnsResolverAdapter == null) {
                    dnsResolverAdapter = new DnsResolverAdapter();
                    sInstance = dnsResolverAdapter;
                }
            }
        }
        return dnsResolverAdapter;
    }

    public void updateEnforceDnsUidForNetwork(final int i, final boolean z) {
        String str = TAG;
        Log.d(str, "updateEnforceDnsUidForNetwork - entered");
        runWithExceptionHandling(new CheckedRemoteRequest() { // from class: com.android.server.enterprise.adapterlayer.DnsResolverAdapter$$ExternalSyntheticLambda0
            @Override // com.android.server.enterprise.adapterlayer.DnsResolverAdapter.CheckedRemoteRequest
            public final void execute(IDnsResolver iDnsResolver) {
                DnsResolverAdapter.lambda$updateEnforceDnsUidForNetwork$0(z, i, iDnsResolver);
            }
        });
        Log.d(str, "updateEnforceDnsUidForNetwork - exited");
    }

    public static /* synthetic */ void lambda$updateEnforceDnsUidForNetwork$0(boolean z, int i, IDnsResolver iDnsResolver) {
        ResolverOptionsParcel resolverOptionsParcel = new ResolverOptionsParcel();
        resolverOptionsParcel.enforceDnsUid = z;
        iDnsResolver.setResolverOptions(i, resolverOptionsParcel);
    }

    @Override // com.android.server.enterprise.adapter.IDnsResolverAdapter
    public void setResolverConfiguration(final ResolverParamsParcel resolverParamsParcel) {
        String str = TAG;
        Log.d(str, "setResolverConfiguration - entered");
        runWithExceptionHandling(new CheckedRemoteRequest() { // from class: com.android.server.enterprise.adapterlayer.DnsResolverAdapter$$ExternalSyntheticLambda1
            @Override // com.android.server.enterprise.adapterlayer.DnsResolverAdapter.CheckedRemoteRequest
            public final void execute(IDnsResolver iDnsResolver) {
                iDnsResolver.setResolverConfiguration(ResolverParamsParcel.this);
            }
        });
        Log.d(str, "setResolverConfiguration - exited");
    }

    @Override // com.android.server.enterprise.adapter.IDnsResolverAdapter
    public void createNetworkCache(final int i) {
        String str = TAG;
        Log.d(str, "createNetworkCache - entered");
        runWithExceptionHandling(new CheckedRemoteRequest() { // from class: com.android.server.enterprise.adapterlayer.DnsResolverAdapter$$ExternalSyntheticLambda2
            @Override // com.android.server.enterprise.adapterlayer.DnsResolverAdapter.CheckedRemoteRequest
            public final void execute(IDnsResolver iDnsResolver) {
                iDnsResolver.createNetworkCache(i);
            }
        });
        Log.d(str, "createNetworkCache - exited");
    }

    @Override // com.android.server.enterprise.adapter.IDnsResolverAdapter
    public void flushNetworkCache(final int i) {
        String str = TAG;
        Log.d(str, "flushNetworkCache - entered");
        runWithExceptionHandling(new CheckedRemoteRequest() { // from class: com.android.server.enterprise.adapterlayer.DnsResolverAdapter$$ExternalSyntheticLambda3
            @Override // com.android.server.enterprise.adapterlayer.DnsResolverAdapter.CheckedRemoteRequest
            public final void execute(IDnsResolver iDnsResolver) {
                iDnsResolver.flushNetworkCache(i);
            }
        });
        Log.d(str, "flushNetworkCache - exited");
    }

    public final void runWithExceptionHandling(CheckedRemoteRequest checkedRemoteRequest) {
        if (getDnsResolver() == null) {
            Log.e(TAG, "Failed to get dns resolver service");
            return;
        }
        try {
            checkedRemoteRequest.execute(this.mDnsResolver);
            Log.d(TAG, "dnsresolver called successfully");
        } catch (RemoteException | ServiceSpecificException e) {
            Log.e(TAG, "Error calling dnsresolver service: " + e);
        }
    }

    public final IDnsResolver getDnsResolver() {
        if (this.mDnsResolver == null) {
            if (ServiceManager.getService("dnsresolver") == null) {
                Log.e(TAG, "updateEnforceDnsUidForNetwork: Failed to get binder for dns resolver service");
                return null;
            }
            this.mDnsResolver = IDnsResolver.Stub.asInterface(ServiceManager.getService("dnsresolver"));
        }
        return this.mDnsResolver;
    }
}
