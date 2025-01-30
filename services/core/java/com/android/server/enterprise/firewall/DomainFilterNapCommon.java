package com.android.server.enterprise.firewall;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;
import android.os.Binder;
import android.os.SystemProperties;
import android.util.Log;
import com.android.internal.util.FunctionalUtils;
import com.android.server.enterprise.adapterlayer.DnsResolverAdapter;

/* loaded from: classes2.dex */
public class DomainFilterNapCommon {
    public static final String TAG = "DomainFilterNapCommon";
    public static DomainFilterNapCommon sInstance;
    public static final Object sLock = new Object();
    public volatile boolean mCallbackRegistered;
    public ConnectivityManager.NetworkCallback mConnectivityCallback = new ConnectivityManager.NetworkCallback() { // from class: com.android.server.enterprise.firewall.DomainFilterNapCommon.1
        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onAvailable(Network network) {
            Log.d(DomainFilterNapCommon.TAG, "onAvailable : " + network.getNetId());
            super.onAvailable(network);
            if (DomainFilterNapCommon.this.hasFeatureEnabled()) {
                DomainFilterNapCommon.this.updateEnforceDnsUidForNetwork(network.getNetId(), true);
            }
        }
    };
    public Context mContext;
    public volatile boolean mDomainFilterEnabled;
    public volatile boolean mDomainFilterOnIptablesEnabled;
    public volatile boolean mNapEnabled;
    public volatile boolean mNetworksUpdated;

    public DomainFilterNapCommon(Context context) {
        this.mContext = context;
    }

    public static DomainFilterNapCommon getInstance(Context context) {
        DomainFilterNapCommon domainFilterNapCommon = sInstance;
        if (domainFilterNapCommon == null) {
            synchronized (sLock) {
                domainFilterNapCommon = sInstance;
                if (domainFilterNapCommon == null) {
                    domainFilterNapCommon = new DomainFilterNapCommon(context);
                    sInstance = domainFilterNapCommon;
                }
            }
        }
        return domainFilterNapCommon;
    }

    public final void registerConnectivityCallback() {
        Log.d(TAG, "registerConnectivityCallback " + this.mCallbackRegistered);
        if (this.mCallbackRegistered) {
            return;
        }
        final NetworkRequest.Builder addTransportType = new NetworkRequest.Builder().addTransportType(1).addTransportType(0).addTransportType(4).addTransportType(3);
        final ConnectivityManager connectivityManager = (ConnectivityManager) this.mContext.getSystemService(ConnectivityManager.class);
        if (connectivityManager != null) {
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.enterprise.firewall.DomainFilterNapCommon$$ExternalSyntheticLambda0
                public final void runOrThrow() {
                    DomainFilterNapCommon.this.lambda$registerConnectivityCallback$0(connectivityManager, addTransportType);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$registerConnectivityCallback$0(ConnectivityManager connectivityManager, NetworkRequest.Builder builder) {
        connectivityManager.registerNetworkCallback(builder.build(), this.mConnectivityCallback);
        Log.d(TAG, "callback registered");
        this.mCallbackRegistered = true;
    }

    public final void unregisterConnectivityCallback() {
        final ConnectivityManager connectivityManager;
        Log.d(TAG, "unregisterConnectivityCallback " + this.mCallbackRegistered);
        if (this.mCallbackRegistered && (connectivityManager = (ConnectivityManager) this.mContext.getSystemService(ConnectivityManager.class)) != null) {
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.enterprise.firewall.DomainFilterNapCommon$$ExternalSyntheticLambda1
                public final void runOrThrow() {
                    DomainFilterNapCommon.this.lambda$unregisterConnectivityCallback$1(connectivityManager);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$unregisterConnectivityCallback$1(ConnectivityManager connectivityManager) {
        connectivityManager.unregisterNetworkCallback(this.mConnectivityCallback);
        this.mCallbackRegistered = false;
        Log.d(TAG, "callback unregistered");
    }

    public void setDomainFilterEnabled(boolean z) {
        Log.d(TAG, "setDomainFilterEnabled " + z);
        this.mDomainFilterEnabled = z;
        updateEnforceDnsUidForAllNetworks();
    }

    public void setDomainFilterOnIptablesEnabled(boolean z) {
        Log.d(TAG, "setDomainFilterOnIptablesEnabled " + z);
        this.mDomainFilterOnIptablesEnabled = z;
        setNapProperty(z);
    }

    public void setNapEnabled(boolean z) {
        Log.d(TAG, "setNapEnabled " + z);
        this.mNapEnabled = z;
        setNapProperty(z);
        updateEnforceDnsUidForAllNetworks();
    }

    public void setNapProperty(final boolean z) {
        Log.d(TAG, "setNapProperty " + z);
        if (z || shouldDisableNapProperty()) {
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.enterprise.firewall.DomainFilterNapCommon$$ExternalSyntheticLambda2
                public final void runOrThrow() {
                    DomainFilterNapCommon.lambda$setNapProperty$2(z);
                }
            });
        }
    }

    public static /* synthetic */ void lambda$setNapProperty$2(boolean z) {
        try {
            SystemProperties.set("net.knox.nap", z ? "1" : "0");
        } catch (Exception e) {
            Log.e(TAG, "Unable to set NPA feature property", e);
        }
    }

    public final boolean shouldDisableNapProperty() {
        return (this.mDomainFilterOnIptablesEnabled || this.mNapEnabled) ? false : true;
    }

    public final synchronized void updateEnforceDnsUidForAllNetworks() {
        if (this.mNetworksUpdated && hasFeatureEnabled()) {
            Log.d(TAG, "Already updated networks, leaving");
            return;
        }
        if (this.mNetworksUpdated && !hasFeatureEnabled()) {
            Log.d(TAG, "Features are disabled, reverting enforce dns uid");
            this.mNetworksUpdated = false;
            updateEnforceDnsUidForAllNetworks(false);
            unregisterConnectivityCallback();
            return;
        }
        Log.d(TAG, "Feature enabled, enforcing dns uid");
        this.mNetworksUpdated = true;
        updateEnforceDnsUidForAllNetworks(true);
        registerConnectivityCallback();
    }

    public final boolean hasFeatureEnabled() {
        return this.mNapEnabled || this.mDomainFilterEnabled;
    }

    public final void updateEnforceDnsUidForAllNetworks(boolean z) {
        String str = TAG;
        Log.d(str, "updateEnforceDnsUidForAllNetworks " + z);
        ConnectivityManager connectivityManager = (ConnectivityManager) this.mContext.getSystemService(ConnectivityManager.class);
        if (connectivityManager == null) {
            Log.e(str, "Failed to get connectivity manager instance");
            return;
        }
        Network[] allNetworks = connectivityManager.getAllNetworks();
        if (allNetworks != null) {
            for (Network network : allNetworks) {
                updateEnforceDnsUidForNetwork(network.getNetId(), z);
            }
        }
    }

    public final void updateEnforceDnsUidForNetwork(int i, boolean z) {
        DnsResolverAdapter.getInstance().updateEnforceDnsUidForNetwork(i, z);
    }
}
