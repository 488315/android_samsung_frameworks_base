package com.android.server.enterprise.firewall;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;
import android.os.Binder;
import android.os.SystemProperties;
import android.util.Log;

import com.android.internal.util.FunctionalUtils;
import com.android.server.RCPManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.enterprise.adapterlayer.DnsResolverAdapter;
import com.android.server.enterprise.adapterlayer.DnsResolverAdapter$$ExternalSyntheticLambda0;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class DomainFilterNapCommon {
    public static DomainFilterNapCommon sInstance;
    public static final Object sLock = new Object();
    public volatile boolean mCallbackRegistered;
    public AnonymousClass1 mConnectivityCallback;
    public Context mContext;
    public volatile boolean mDomainFilterEnabled;
    public volatile boolean mDomainFilterOnIptablesEnabled;
    public volatile boolean mNapEnabled;
    public volatile boolean mNetworksUpdated;

    /* JADX WARN: Type inference failed for: r2v0, types: [com.android.server.enterprise.firewall.DomainFilterNapCommon$1] */
    public static DomainFilterNapCommon getInstance(Context context) {
        DomainFilterNapCommon domainFilterNapCommon = sInstance;
        if (domainFilterNapCommon == null) {
            synchronized (sLock) {
                try {
                    domainFilterNapCommon = sInstance;
                    if (domainFilterNapCommon == null) {
                        domainFilterNapCommon = new DomainFilterNapCommon();
                        domainFilterNapCommon.mConnectivityCallback =
                                new ConnectivityManager
                                        .NetworkCallback() { // from class:
                                                             // com.android.server.enterprise.firewall.DomainFilterNapCommon.1
                                    @Override // android.net.ConnectivityManager.NetworkCallback
                                    public final void onAvailable(Network network) {
                                        Log.d(
                                                "DomainFilterNapCommon",
                                                "onAvailable : " + network.getNetId());
                                        super.onAvailable(network);
                                        DomainFilterNapCommon domainFilterNapCommon2 =
                                                DomainFilterNapCommon.this;
                                        if (domainFilterNapCommon2.mNapEnabled
                                                || domainFilterNapCommon2.mDomainFilterEnabled) {
                                            DomainFilterNapCommon domainFilterNapCommon3 =
                                                    DomainFilterNapCommon.this;
                                            int netId = network.getNetId();
                                            domainFilterNapCommon3.getClass();
                                            DnsResolverAdapter dnsResolverAdapter =
                                                    DnsResolverAdapter.getInstance();
                                            Log.d(
                                                    "DnsResolverAdapter",
                                                    "updateEnforceDnsUidForNetwork - entered");
                                            dnsResolverAdapter.runWithExceptionHandling(
                                                    new DnsResolverAdapter$$ExternalSyntheticLambda0(
                                                            netId, true));
                                            Log.d(
                                                    "DnsResolverAdapter",
                                                    "updateEnforceDnsUidForNetwork - exited");
                                        }
                                    }
                                };
                        domainFilterNapCommon.mContext = context;
                        sInstance = domainFilterNapCommon;
                    }
                } finally {
                }
            }
        }
        return domainFilterNapCommon;
    }

    public final void registerConnectivityCallback() {
        RCPManagerService$$ExternalSyntheticOutline0.m(
                "DomainFilterNapCommon",
                new StringBuilder("registerConnectivityCallback "),
                this.mCallbackRegistered);
        if (this.mCallbackRegistered) {
            return;
        }
        final NetworkRequest.Builder addTransportType =
                new NetworkRequest.Builder()
                        .addTransportType(1)
                        .addTransportType(0)
                        .addTransportType(4)
                        .addTransportType(3);
        final ConnectivityManager connectivityManager =
                (ConnectivityManager) this.mContext.getSystemService(ConnectivityManager.class);
        if (connectivityManager != null) {
            Binder.withCleanCallingIdentity(
                    new FunctionalUtils
                            .ThrowingRunnable() { // from class:
                                                  // com.android.server.enterprise.firewall.DomainFilterNapCommon$$ExternalSyntheticLambda2
                        public final void runOrThrow() {
                            DomainFilterNapCommon domainFilterNapCommon =
                                    DomainFilterNapCommon.this;
                            ConnectivityManager connectivityManager2 = connectivityManager;
                            NetworkRequest.Builder builder = addTransportType;
                            domainFilterNapCommon.getClass();
                            connectivityManager2.registerNetworkCallback(
                                    builder.build(), domainFilterNapCommon.mConnectivityCallback);
                            Log.d("DomainFilterNapCommon", "callback registered");
                            domainFilterNapCommon.mCallbackRegistered = true;
                        }
                    });
        }
    }

    public final void setDomainFilterEnabled(boolean z) {
        AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                "setDomainFilterEnabled ", "DomainFilterNapCommon", z);
        this.mDomainFilterEnabled = z;
        updateEnforceDnsUidForAllNetworks();
    }

    public final void setDomainFilterOnIptablesEnabled(boolean z) {
        AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                "setDomainFilterOnIptablesEnabled ", "DomainFilterNapCommon", z);
        this.mDomainFilterOnIptablesEnabled = z;
        setNapProperty(z);
    }

    public final void setNapProperty(final boolean z) {
        AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                "setNapProperty ", "DomainFilterNapCommon", z);
        if (z || !(this.mDomainFilterOnIptablesEnabled || this.mNapEnabled)) {
            Binder.withCleanCallingIdentity(
                    new FunctionalUtils
                            .ThrowingRunnable() { // from class:
                                                  // com.android.server.enterprise.firewall.DomainFilterNapCommon$$ExternalSyntheticLambda1
                        public final void runOrThrow() {
                            try {
                                SystemProperties.set("net.knox.nap", z ? "1" : "0");
                            } catch (Exception e) {
                                Log.e(
                                        "DomainFilterNapCommon",
                                        "Unable to set NPA feature property",
                                        e);
                            }
                        }
                    });
        }
    }

    public final synchronized void updateEnforceDnsUidForAllNetworks() {
        final ConnectivityManager connectivityManager;
        if (this.mNetworksUpdated && (this.mNapEnabled || this.mDomainFilterEnabled)) {
            Log.d("DomainFilterNapCommon", "Already updated networks, leaving");
            return;
        }
        if (this.mNetworksUpdated && !this.mNapEnabled && !this.mDomainFilterEnabled) {
            Log.d("DomainFilterNapCommon", "Features are disabled, reverting enforce dns uid");
            this.mNetworksUpdated = false;
            updateEnforceDnsUidForAllNetworks(false);
            RCPManagerService$$ExternalSyntheticOutline0.m(
                    "DomainFilterNapCommon",
                    new StringBuilder("unregisterConnectivityCallback "),
                    this.mCallbackRegistered);
            if (this.mCallbackRegistered
                    && (connectivityManager =
                                    (ConnectivityManager)
                                            this.mContext.getSystemService(
                                                    ConnectivityManager.class))
                            != null) {
                Binder.withCleanCallingIdentity(
                        new FunctionalUtils
                                .ThrowingRunnable() { // from class:
                                                      // com.android.server.enterprise.firewall.DomainFilterNapCommon$$ExternalSyntheticLambda0
                            public final void runOrThrow() {
                                DomainFilterNapCommon domainFilterNapCommon =
                                        DomainFilterNapCommon.this;
                                connectivityManager.unregisterNetworkCallback(
                                        domainFilterNapCommon.mConnectivityCallback);
                                domainFilterNapCommon.mCallbackRegistered = false;
                                Log.d("DomainFilterNapCommon", "callback unregistered");
                            }
                        });
            }
            return;
        }
        Log.d("DomainFilterNapCommon", "Feature enabled, enforcing dns uid");
        this.mNetworksUpdated = true;
        updateEnforceDnsUidForAllNetworks(true);
        registerConnectivityCallback();
    }

    public final void updateEnforceDnsUidForAllNetworks(boolean z) {
        AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                "updateEnforceDnsUidForAllNetworks ", "DomainFilterNapCommon", z);
        ConnectivityManager connectivityManager =
                (ConnectivityManager) this.mContext.getSystemService(ConnectivityManager.class);
        if (connectivityManager == null) {
            Log.e("DomainFilterNapCommon", "Failed to get connectivity manager instance");
            return;
        }
        Network[] allNetworks = connectivityManager.getAllNetworks();
        if (allNetworks != null) {
            for (Network network : allNetworks) {
                int netId = network.getNetId();
                DnsResolverAdapter dnsResolverAdapter = DnsResolverAdapter.getInstance();
                Log.d("DnsResolverAdapter", "updateEnforceDnsUidForNetwork - entered");
                dnsResolverAdapter.runWithExceptionHandling(
                        new DnsResolverAdapter$$ExternalSyntheticLambda0(netId, z));
                Log.d("DnsResolverAdapter", "updateEnforceDnsUidForNetwork - exited");
            }
        }
    }
}
