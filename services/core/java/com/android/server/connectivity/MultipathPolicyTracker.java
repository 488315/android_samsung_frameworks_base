package com.android.server.connectivity;

import android.R;
import android.app.usage.NetworkStats;
import android.app.usage.NetworkStatsManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkIdentity;
import android.net.NetworkPolicy;
import android.net.NetworkPolicyManager;
import android.net.NetworkRequest;
import android.net.NetworkSpecifier;
import android.net.NetworkTemplate;
import android.net.TelephonyNetworkSpecifier;
import android.net.Uri;
import android.os.BestClock;
import android.os.Handler;
import android.os.SystemClock;
import android.os.UserHandle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DebugUtils;
import android.util.Log;
import android.util.Range;
import com.android.internal.util.IndentingPrintWriter;
import com.android.server.LocalServices;
import com.android.server.net.NetworkPolicyManagerService;
import java.time.Clock;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class MultipathPolicyTracker {
    public ConnectivityManager mCM;
    public final Context mContext;
    public final Handler mHandler;
    public AnonymousClass1 mMobileNetworkCallback;
    public NetworkPolicyManager mNPM;
    public AnonymousClass2 mPolicyListener;
    public final ContentResolver mResolver;
    final ContentObserver mSettingsObserver;
    public final Context mUserAllContext;
    public final ConcurrentHashMap mMultipathTrackers = new ConcurrentHashMap();
    public final Clock mClock = new BestClock(ZoneOffset.UTC, new Clock[]{SystemClock.currentNetworkTimeClock(), Clock.systemUTC()});
    public final ConfigChangeReceiver mConfigChangeReceiver = new ConfigChangeReceiver();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.connectivity.MultipathPolicyTracker$2, reason: invalid class name */
    public final class AnonymousClass2 extends NetworkPolicyManager.Listener {
        public AnonymousClass2() {
        }

        public final void onMeteredIfacesChanged(String[] strArr) {
            MultipathPolicyTracker.this.mHandler.post(new Runnable() { // from class: com.android.server.connectivity.MultipathPolicyTracker$2$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    MultipathPolicyTracker.m368$$Nest$mupdateAllMultipathBudgets(MultipathPolicyTracker.this);
                }
            });
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ConfigChangeReceiver extends BroadcastReceiver {
        public ConfigChangeReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            MultipathPolicyTracker.m368$$Nest$mupdateAllMultipathBudgets(MultipathPolicyTracker.this);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class MultipathTracker {
        public volatile long mMultipathBudget;
        public NetworkCapabilities mNetworkCapabilities;
        public final NetworkTemplate mNetworkTemplate;
        public long mQuota;
        public final NetworkStatsManager mStatsManager;
        public final int mSubId;
        public final AnonymousClass1 mUsageCallback;
        public boolean mUsageCallbackRegistered = false;
        public final Network network;
        public final String subscriberId;

        /* JADX WARN: Type inference failed for: r5v9, types: [com.android.server.connectivity.MultipathPolicyTracker$MultipathTracker$1] */
        public MultipathTracker(Network network, NetworkCapabilities networkCapabilities) {
            this.network = network;
            this.mNetworkCapabilities = new NetworkCapabilities(networkCapabilities);
            NetworkSpecifier networkSpecifier = networkCapabilities.getNetworkSpecifier();
            if (!(networkSpecifier instanceof TelephonyNetworkSpecifier)) {
                throw new IllegalStateException(String.format("Can't get subId from mobile network %s (%s)", network, networkCapabilities));
            }
            int subscriptionId = ((TelephonyNetworkSpecifier) networkSpecifier).getSubscriptionId();
            this.mSubId = subscriptionId;
            TelephonyManager telephonyManager = (TelephonyManager) MultipathPolicyTracker.this.mContext.getSystemService(TelephonyManager.class);
            if (telephonyManager == null) {
                throw new IllegalStateException("Missing TelephonyManager");
            }
            TelephonyManager createForSubscriptionId = telephonyManager.createForSubscriptionId(subscriptionId);
            if (createForSubscriptionId == null) {
                throw new IllegalStateException(String.format("Can't get TelephonyManager for subId %d", Integer.valueOf(subscriptionId)));
            }
            String subscriberId = createForSubscriptionId.getSubscriberId();
            this.subscriberId = subscriberId;
            if (subscriberId == null) {
                throw new IllegalStateException(VibrationParam$1$$ExternalSyntheticOutline0.m(subscriptionId, "Null subscriber Id for subId "));
            }
            this.mNetworkTemplate = new NetworkTemplate.Builder(1).setSubscriberIds(Set.of(subscriberId)).setMeteredness(1).setDefaultNetworkStatus(0).build();
            this.mUsageCallback = new NetworkStatsManager.UsageCallback() { // from class: com.android.server.connectivity.MultipathPolicyTracker.MultipathTracker.1
                @Override // android.app.usage.NetworkStatsManager.UsageCallback
                public final void onThresholdReached(int i, String str) {
                    MultipathTracker.this.updateMultipathBudget();
                }
            };
            NetworkStatsManager networkStatsManager = (NetworkStatsManager) MultipathPolicyTracker.this.mContext.getSystemService(NetworkStatsManager.class);
            this.mStatsManager = networkStatsManager;
            networkStatsManager.setPollOnOpen(false);
            updateMultipathBudget();
        }

        public final long getNetworkTotalBytes(long j, long j2) {
            try {
                NetworkStats.Bucket querySummaryForDevice = this.mStatsManager.querySummaryForDevice(this.mNetworkTemplate, j, j2);
                return querySummaryForDevice.getRxBytes() + querySummaryForDevice.getTxBytes();
            } catch (RuntimeException e) {
                Log.w("MultipathPolicyTracker", "Failed to get data usage: " + e);
                return -1L;
            }
        }

        public final void updateMultipathBudget() {
            NetworkPolicy[] networkPolicyArr;
            long subscriptionOpportunisticQuota = ((NetworkPolicyManagerService.NetworkPolicyManagerInternalImpl) LocalServices.getService(NetworkPolicyManagerService.NetworkPolicyManagerInternalImpl.class)).getSubscriptionOpportunisticQuota(this.network, 2);
            long j = -1;
            if (subscriptionOpportunisticQuota == -1) {
                NetworkCapabilities networkCapabilities = this.mNetworkCapabilities;
                NetworkIdentity build = new NetworkIdentity.Builder().setType(0).setSubscriberId(this.subscriberId).setRoaming(!networkCapabilities.hasCapability(18)).setMetered(!networkCapabilities.hasCapability(11)).setSubId(this.mSubId).build();
                MultipathPolicyTracker multipathPolicyTracker = MultipathPolicyTracker.this;
                NetworkPolicy[] networkPolicies = multipathPolicyTracker.mNPM.getNetworkPolicies();
                int length = networkPolicies.length;
                int i = 0;
                long j2 = Long.MAX_VALUE;
                while (i < length) {
                    NetworkPolicy networkPolicy = networkPolicies[i];
                    if (networkPolicy.hasCycle() && networkPolicy.template.matches(build)) {
                        long epochMilli = ((ZonedDateTime) ((Range) networkPolicy.cycleIterator().next()).getLower()).toInstant().toEpochMilli();
                        long j3 = networkPolicy.lastWarningSnooze < epochMilli ? networkPolicy.warningBytes : j;
                        if (j3 == j) {
                            j3 = networkPolicy.lastLimitSnooze < epochMilli ? networkPolicy.limitBytes : j;
                        }
                        if (j3 != j && j3 != j) {
                            Range range = (Range) networkPolicy.cycleIterator().next();
                            long epochMilli2 = ((ZonedDateTime) range.getLower()).toInstant().toEpochMilli();
                            networkPolicyArr = networkPolicies;
                            long epochMilli3 = ((ZonedDateTime) range.getUpper()).toInstant().toEpochMilli();
                            long networkTotalBytes = getNetworkTotalBytes(epochMilli2, epochMilli3);
                            j2 = Math.min(j2, (networkTotalBytes == j ? 0L : Math.max(0L, j3 - networkTotalBytes)) / Math.max(1L, (((epochMilli3 - multipathPolicyTracker.mClock.millis()) - 1) / TimeUnit.DAYS.toMillis(1L)) + 1));
                            i++;
                            networkPolicies = networkPolicyArr;
                            j = -1;
                        }
                    }
                    networkPolicyArr = networkPolicies;
                    i++;
                    networkPolicies = networkPolicyArr;
                    j = -1;
                }
                subscriptionOpportunisticQuota = j2 == Long.MAX_VALUE ? -1L : j2 / 20;
                j = -1;
            }
            if (subscriptionOpportunisticQuota == j) {
                MultipathPolicyTracker multipathPolicyTracker2 = MultipathPolicyTracker.this;
                String string = Settings.Global.getString(multipathPolicyTracker2.mContext.getContentResolver(), "network_default_daily_multipath_quota_bytes");
                if (string != null) {
                    try {
                        subscriptionOpportunisticQuota = Long.parseLong(string);
                    } catch (NumberFormatException unused) {
                    }
                }
                subscriptionOpportunisticQuota = multipathPolicyTracker2.mContext.getResources().getInteger(R.integer.config_previousVibrationsDumpSizeLimit);
            }
            if (this.mMultipathBudget <= 0 || subscriptionOpportunisticQuota != this.mQuota) {
                this.mQuota = subscriptionOpportunisticQuota;
                ZonedDateTime ofInstant = ZonedDateTime.ofInstant(MultipathPolicyTracker.this.mClock.instant(), ZoneId.systemDefault());
                long networkTotalBytes2 = getNetworkTotalBytes(ofInstant.truncatedTo(ChronoUnit.DAYS).toInstant().toEpochMilli(), ofInstant.toInstant().toEpochMilli());
                long max = networkTotalBytes2 == -1 ? 0L : Math.max(0L, subscriptionOpportunisticQuota - networkTotalBytes2);
                if (max <= 2097152) {
                    if (this.mUsageCallbackRegistered) {
                        this.mStatsManager.unregisterUsageCallback(this.mUsageCallback);
                        this.mUsageCallbackRegistered = false;
                    }
                    this.mMultipathBudget = 0L;
                    return;
                }
                if (this.mUsageCallbackRegistered) {
                    this.mStatsManager.unregisterUsageCallback(this.mUsageCallback);
                    this.mUsageCallbackRegistered = false;
                }
                this.mStatsManager.registerUsageCallback(this.mNetworkTemplate, max, new Executor() { // from class: com.android.server.connectivity.MultipathPolicyTracker$MultipathTracker$$ExternalSyntheticLambda0
                    @Override // java.util.concurrent.Executor
                    public final void execute(Runnable runnable) {
                        MultipathPolicyTracker.this.mHandler.post(runnable);
                    }
                }, this.mUsageCallback);
                this.mUsageCallbackRegistered = true;
                this.mMultipathBudget = max;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SettingsObserver extends ContentObserver {
        public SettingsObserver(Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            Log.wtf("MultipathPolicyTracker", "Should never be reached.");
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            if (!Settings.Global.getUriFor("network_default_daily_multipath_quota_bytes").equals(uri)) {
                Log.wtf("MultipathPolicyTracker", "Unexpected settings observation: " + uri);
            }
            MultipathPolicyTracker.m368$$Nest$mupdateAllMultipathBudgets(MultipathPolicyTracker.this);
        }
    }

    /* renamed from: -$$Nest$mupdateAllMultipathBudgets, reason: not valid java name */
    public static void m368$$Nest$mupdateAllMultipathBudgets(MultipathPolicyTracker multipathPolicyTracker) {
        Iterator it = multipathPolicyTracker.mMultipathTrackers.values().iterator();
        while (it.hasNext()) {
            ((MultipathTracker) it.next()).updateMultipathBudget();
        }
    }

    public MultipathPolicyTracker(Context context, Handler handler) {
        this.mContext = context;
        this.mUserAllContext = context.createContextAsUser(UserHandle.ALL, 0);
        this.mHandler = handler;
        this.mResolver = context.getContentResolver();
        this.mSettingsObserver = new SettingsObserver(handler);
    }

    public final void dump(IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("MultipathPolicyTracker:");
        indentingPrintWriter.increaseIndent();
        for (MultipathTracker multipathTracker : this.mMultipathTrackers.values()) {
            indentingPrintWriter.println(String.format("Network %s: quota %d, budget %d. Preference: %s", multipathTracker.network, Long.valueOf(multipathTracker.mQuota), Long.valueOf(multipathTracker.mMultipathBudget), DebugUtils.flagsToString(ConnectivityManager.class, "MULTIPATH_PREFERENCE_", multipathTracker.mMultipathBudget > 0 ? 3 : 0)));
        }
        indentingPrintWriter.decreaseIndent();
    }

    public final void start() {
        this.mCM = (ConnectivityManager) this.mContext.getSystemService(ConnectivityManager.class);
        this.mNPM = (NetworkPolicyManager) this.mContext.getSystemService(NetworkPolicyManager.class);
        NetworkRequest build = new NetworkRequest.Builder().addCapability(12).addTransportType(0).build();
        ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback() { // from class: com.android.server.connectivity.MultipathPolicyTracker.1
            @Override // android.net.ConnectivityManager.NetworkCallback
            public final void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
                MultipathTracker multipathTracker = (MultipathTracker) MultipathPolicyTracker.this.mMultipathTrackers.get(network);
                if (multipathTracker != null) {
                    multipathTracker.mNetworkCapabilities = new NetworkCapabilities(networkCapabilities);
                    multipathTracker.updateMultipathBudget();
                    return;
                }
                try {
                    MultipathPolicyTracker multipathPolicyTracker = MultipathPolicyTracker.this;
                    multipathPolicyTracker.mMultipathTrackers.put(network, multipathPolicyTracker.new MultipathTracker(network, networkCapabilities));
                } catch (IllegalStateException e) {
                    Log.e("MultipathPolicyTracker", "Can't track mobile network " + network + ": " + e.getMessage());
                }
            }

            @Override // android.net.ConnectivityManager.NetworkCallback
            public final void onLost(Network network) {
                MultipathTracker multipathTracker = (MultipathTracker) MultipathPolicyTracker.this.mMultipathTrackers.get(network);
                if (multipathTracker != null) {
                    if (multipathTracker.mUsageCallbackRegistered) {
                        multipathTracker.mStatsManager.unregisterUsageCallback(multipathTracker.mUsageCallback);
                        multipathTracker.mUsageCallbackRegistered = false;
                    }
                    multipathTracker.mMultipathBudget = 0L;
                    MultipathPolicyTracker.this.mMultipathTrackers.remove(network);
                }
            }
        };
        ConnectivityManager connectivityManager = this.mCM;
        Handler handler = this.mHandler;
        connectivityManager.registerNetworkCallback(build, networkCallback, handler);
        this.mNPM.registerListener(new AnonymousClass2());
        this.mResolver.registerContentObserver(Settings.Global.getUriFor("network_default_daily_multipath_quota_bytes"), false, this.mSettingsObserver);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.CONFIGURATION_CHANGED");
        this.mUserAllContext.registerReceiver(this.mConfigChangeReceiver, intentFilter, null, handler);
    }
}
