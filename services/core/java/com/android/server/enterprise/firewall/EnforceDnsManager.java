package com.android.server.enterprise.firewall;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.LinkProperties;
import android.net.ResolverOptionsParcel;
import android.net.ResolverParamsParcel;
import android.os.Binder;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import com.android.server.enterprise.adapter.IDnsResolverAdapter;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes2.dex */
public class EnforceDnsManager {
  public static final String TAG = "EnforceDnsManager";
  public final ContentResolver mContentResolver;
  public final Context mContext;
  public final IDnsResolverAdapter mDnsResolverAdapter;
  public int mMaxSamples;
  public int mMinSamples;
  public int mSampleValidity;
  public int mSuccessThreshold;
  public boolean mEnforceDnsUid = true;
  public final Map mLinkPropertiesMap = new HashMap();
  public final Map mTransportsMap = new HashMap();

  public EnforceDnsManager(Context context, IDnsResolverAdapter iDnsResolverAdapter) {
    this.mContext = context;
    this.mContentResolver = context.getContentResolver();
    this.mDnsResolverAdapter = iDnsResolverAdapter;
  }

  public void updateTransportsForNetwork(int i, int[] iArr) {
    this.mTransportsMap.put(Integer.valueOf(i), iArr);
    sendDnsConfigurationForNetwork(i);
  }

  public void noteDnsServersForNetwork(int i, LinkProperties linkProperties) {
    this.mLinkPropertiesMap.put(Integer.valueOf(i), linkProperties);
    sendDnsConfigurationForNetwork(i);
  }

  public void updateDnsUidForNetwork(int i, boolean z) {
    this.mEnforceDnsUid = z;
    sendDnsConfigurationForNetwork(i);
  }

  public void sendDnsConfigurationForNetwork(int i) {
    LinkProperties linkProperties =
        (LinkProperties) this.mLinkPropertiesMap.get(Integer.valueOf(i));
    int[] iArr = (int[]) this.mTransportsMap.get(Integer.valueOf(i));
    if (linkProperties == null || iArr == null) {
      return;
    }
    updateParametersSettings();
    ResolverParamsParcel resolverParamsParcel = new ResolverParamsParcel();
    resolverParamsParcel.netId = i;
    resolverParamsParcel.sampleValiditySeconds = this.mSampleValidity;
    resolverParamsParcel.successThreshold = this.mSuccessThreshold;
    resolverParamsParcel.minSamples = this.mMinSamples;
    resolverParamsParcel.maxSamples = this.mMaxSamples;
    resolverParamsParcel.servers = makeStrings(linkProperties.getDnsServers());
    resolverParamsParcel.domains = getDomainStrings(linkProperties.getDomains());
    resolverParamsParcel.tlsName = "";
    resolverParamsParcel.tlsServers = new String[0];
    ResolverOptionsParcel resolverOptionsParcel = new ResolverOptionsParcel();
    resolverParamsParcel.resolverOptions = resolverOptionsParcel;
    resolverOptionsParcel.enforceDnsUid = this.mEnforceDnsUid;
    resolverParamsParcel.transportTypes = iArr;
    Log.d(
        TAG,
        String.format(
            "sendDnsConfigurationForNetwork(%d, %s, %s, %d, %d, %d, %d, %d, %d, %s, %s)",
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
    this.mDnsResolverAdapter.setResolverConfiguration(resolverParamsParcel);
  }

  public void flushVmDnsCache() {
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

  public final void updateParametersSettings() {
    int intSetting = getIntSetting("dns_resolver_sample_validity_seconds", 1800);
    this.mSampleValidity = intSetting;
    if (intSetting < 0 || intSetting > 65535) {
      Log.w(TAG, "Invalid sampleValidity=" + this.mSampleValidity + ", using default=1800");
      this.mSampleValidity = 1800;
    }
    int intSetting2 = getIntSetting("dns_resolver_success_threshold_percent", 25);
    this.mSuccessThreshold = intSetting2;
    if (intSetting2 < 0 || intSetting2 > 100) {
      Log.w(TAG, "Invalid successThreshold=" + this.mSuccessThreshold + ", using default=25");
      this.mSuccessThreshold = 25;
    }
    this.mMinSamples = getIntSetting("dns_resolver_min_samples", 8);
    int intSetting3 = getIntSetting("dns_resolver_max_samples", 64);
    this.mMaxSamples = intSetting3;
    int i = this.mMinSamples;
    if (i < 0 || i > intSetting3 || intSetting3 > 64) {
      Log.w(
          TAG,
          "Invalid sample count (min, max)=("
              + this.mMinSamples
              + ", "
              + this.mMaxSamples
              + "), using default=(8, 64)");
      this.mMinSamples = 8;
      this.mMaxSamples = 64;
    }
  }

  public final int getIntSetting(String str, int i) {
    return Settings.Global.getInt(this.mContentResolver, str, i);
  }

  public final String[] makeStrings(Collection collection) {
    String[] strArr = new String[collection.size()];
    Iterator it = collection.iterator();
    int i = 0;
    while (it.hasNext()) {
      strArr[i] = ((InetAddress) it.next()).getHostAddress();
      i++;
    }
    return strArr;
  }

  public static String[] getDomainStrings(String str) {
    return TextUtils.isEmpty(str) ? new String[0] : str.split(" ");
  }
}
