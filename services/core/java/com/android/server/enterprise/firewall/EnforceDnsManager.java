package com.android.server.enterprise.firewall;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.IDnsResolver;
import android.net.LinkProperties;
import android.net.ResolverOptionsParcel;
import android.net.ResolverParamsParcel;
import android.os.Binder;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import com.android.server.enterprise.adapterlayer.DnsResolverAdapter;

import java.net.InetAddress;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class EnforceDnsManager {
    public final ContentResolver mContentResolver;
    public final Context mContext;
    public final DnsResolverAdapter mDnsResolverAdapter;
    public int mMaxSamples;
    public int mMinSamples;
    public int mSampleValidity;
    public int mSuccessThreshold;
    public boolean mEnforceDnsUid = true;
    public final Map mLinkPropertiesMap = new HashMap();
    public final Map mTransportsMap = new HashMap();

    public EnforceDnsManager(Context context, DnsResolverAdapter dnsResolverAdapter) {
        this.mContext = context;
        this.mContentResolver = context.getContentResolver();
        this.mDnsResolverAdapter = dnsResolverAdapter;
    }

    public final void flushVmDnsCache() {
        Intent intent = new Intent("android.net.action.CLEAR_DNS_CACHE");
        intent.addFlags(536870912);
        intent.addFlags(67108864);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mContext.sendBroadcastAsUser(intent, UserHandle.ALL);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void sendDnsConfigurationForNetwork(int i) {
        LinkProperties linkProperties =
                (LinkProperties) ((HashMap) this.mLinkPropertiesMap).get(Integer.valueOf(i));
        int[] iArr = (int[]) ((HashMap) this.mTransportsMap).get(Integer.valueOf(i));
        if (linkProperties == null || iArr == null) {
            return;
        }
        int i2 =
                Settings.Global.getInt(
                        this.mContentResolver, "dns_resolver_sample_validity_seconds", 1800);
        this.mSampleValidity = i2;
        if (i2 < 0 || i2 > 65535) {
            Log.w(
                    "EnforceDnsManager",
                    "Invalid sampleValidity=" + this.mSampleValidity + ", using default=1800");
            this.mSampleValidity = 1800;
        }
        int i3 =
                Settings.Global.getInt(
                        this.mContentResolver, "dns_resolver_success_threshold_percent", 25);
        this.mSuccessThreshold = i3;
        if (i3 < 0 || i3 > 100) {
            Log.w(
                    "EnforceDnsManager",
                    "Invalid successThreshold=" + this.mSuccessThreshold + ", using default=25");
            this.mSuccessThreshold = 25;
        }
        this.mMinSamples =
                Settings.Global.getInt(this.mContentResolver, "dns_resolver_min_samples", 8);
        int i4 = Settings.Global.getInt(this.mContentResolver, "dns_resolver_max_samples", 64);
        this.mMaxSamples = i4;
        int i5 = this.mMinSamples;
        if (i5 < 0 || i5 > i4 || i4 > 64) {
            Log.w(
                    "EnforceDnsManager",
                    "Invalid sample count (min, max)=("
                            + this.mMinSamples
                            + ", "
                            + this.mMaxSamples
                            + "), using default=(8, 64)");
            this.mMinSamples = 8;
            this.mMaxSamples = 64;
        }
        final ResolverParamsParcel resolverParamsParcel = new ResolverParamsParcel();
        resolverParamsParcel.netId = i;
        resolverParamsParcel.sampleValiditySeconds = this.mSampleValidity;
        resolverParamsParcel.successThreshold = this.mSuccessThreshold;
        resolverParamsParcel.minSamples = this.mMinSamples;
        resolverParamsParcel.maxSamples = this.mMaxSamples;
        List<InetAddress> dnsServers = linkProperties.getDnsServers();
        String[] strArr = new String[dnsServers.size()];
        Iterator<InetAddress> it = dnsServers.iterator();
        int i6 = 0;
        while (it.hasNext()) {
            strArr[i6] = it.next().getHostAddress();
            i6++;
        }
        resolverParamsParcel.servers = strArr;
        String domains = linkProperties.getDomains();
        resolverParamsParcel.domains =
                TextUtils.isEmpty(domains) ? new String[0] : domains.split(" ");
        resolverParamsParcel.tlsName = "";
        resolverParamsParcel.tlsServers = new String[0];
        ResolverOptionsParcel resolverOptionsParcel = new ResolverOptionsParcel();
        resolverParamsParcel.resolverOptions = resolverOptionsParcel;
        resolverOptionsParcel.enforceDnsUid = this.mEnforceDnsUid;
        resolverParamsParcel.transportTypes = iArr;
        Log.d(
                "EnforceDnsManager",
                String.format(
                        "sendDnsConfigurationForNetwork(%d, %s, %s, %d, %d, %d, %d, %d, %d, %s,"
                            + " %s)",
                        Integer.valueOf(resolverParamsParcel.netId),
                        Arrays.toString(resolverParamsParcel.servers),
                        Arrays.toString(resolverParamsParcel.domains),
                        Integer.valueOf(resolverParamsParcel.sampleValiditySeconds),
                        Integer.valueOf(resolverParamsParcel.successThreshold),
                        Integer.valueOf(resolverParamsParcel.minSamples),
                        Integer.valueOf(resolverParamsParcel.maxSamples),
                        Integer.valueOf(resolverParamsParcel.baseTimeoutMsec),
                        Integer.valueOf(resolverParamsParcel.retryCount),
                        resolverParamsParcel.tlsName,
                        Arrays.toString(resolverParamsParcel.tlsServers)));
        DnsResolverAdapter dnsResolverAdapter = this.mDnsResolverAdapter;
        dnsResolverAdapter.getClass();
        Log.d("DnsResolverAdapter", "setResolverConfiguration - entered");
        dnsResolverAdapter.runWithExceptionHandling(
                new DnsResolverAdapter
                        .CheckedRemoteRequest() { // from class:
                                                  // com.android.server.enterprise.adapterlayer.DnsResolverAdapter$$ExternalSyntheticLambda1
                    @Override // com.android.server.enterprise.adapterlayer.DnsResolverAdapter.CheckedRemoteRequest
                    public final void execute(IDnsResolver iDnsResolver) {
                        iDnsResolver.setResolverConfiguration(ResolverParamsParcel.this);
                    }
                });
        Log.d("DnsResolverAdapter", "setResolverConfiguration - exited");
    }
}
