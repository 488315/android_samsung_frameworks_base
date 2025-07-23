package android.net;

import android.annotation.SystemApi;
import android.app.ActivityManager;
import android.content.Context;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.net.INetworkPolicyListener;
import android.net.NetworkPolicyManager;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.os.Process;
import android.os.RemoteException;
import android.security.keystore.KeyProperties;
import android.telephony.SubscriptionPlan;
import android.util.DebugUtils;
import android.util.Pair;
import android.util.Range;
import com.android.internal.hidden_from_bootclasspath.android.net.platform.flags.Flags;
import com.android.internal.util.function.TriConsumer;
import com.android.internal.util.function.pooled.PooledLambda;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.time.ZonedDateTime;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;

@SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
/* loaded from: classes3.dex */
public class NetworkPolicyManager {
    public static final int ALLOWED_METERED_REASON_FOREGROUND = 262144;
    public static final int ALLOWED_METERED_REASON_MASK = -65536;
    public static final int ALLOWED_METERED_REASON_SYSTEM = 131072;
    public static final int ALLOWED_METERED_REASON_USER_EXEMPTED = 65536;
    public static final int ALLOWED_REASON_FOREGROUND = 2;
    public static final int ALLOWED_REASON_LOW_POWER_STANDBY_ALLOWLIST = 64;
    public static final int ALLOWED_REASON_NONE = 0;
    public static final int ALLOWED_REASON_NOT_IN_BACKGROUND = 128;
    public static final int ALLOWED_REASON_POWER_SAVE_ALLOWLIST = 4;
    public static final int ALLOWED_REASON_POWER_SAVE_EXCEPT_IDLE_ALLOWLIST = 8;
    public static final int ALLOWED_REASON_RESTRICTED_MODE_PERMISSIONS = 16;
    public static final int ALLOWED_REASON_SYSTEM = 1;
    public static final int ALLOWED_REASON_TOP = 32;
    private static final boolean ALLOW_PLATFORM_APP_POLICY = true;
    public static final int BACKGROUND_THRESHOLD_STATE = 12;
    public static final String EXTRA_NETWORK_TEMPLATE = "android.net.NETWORK_TEMPLATE";
    public static final String FIREWALL_CHAIN_NAME_BACKGROUND = "background";
    public static final String FIREWALL_CHAIN_NAME_DOZABLE = "dozable";
    public static final String FIREWALL_CHAIN_NAME_LOW_POWER_STANDBY = "low_power_standby";
    public static final String FIREWALL_CHAIN_NAME_METERED_ALLOW = "metered_allow";
    public static final String FIREWALL_CHAIN_NAME_METERED_DENY_ADMIN = "metered_deny_admin";
    public static final String FIREWALL_CHAIN_NAME_METERED_DENY_USER = "metered_deny_user";
    public static final String FIREWALL_CHAIN_NAME_NONE = "none";
    public static final String FIREWALL_CHAIN_NAME_OEM_DENY_1 = "fw_oem_deny_1";
    public static final String FIREWALL_CHAIN_NAME_POWERSAVE = "powersave";
    public static final String FIREWALL_CHAIN_NAME_RESTRICTED = "restricted";
    public static final String FIREWALL_CHAIN_NAME_STANDBY = "standby";
    public static final int FIREWALL_POLICY_NONE = 0;
    public static final int FIREWALL_POLICY_REJECT_DATA_WIFI = 3;
    public static final int FIREWALL_POLICY_REJECT_MOBILE_DATA = 1;
    public static final int FIREWALL_POLICY_REJECT_WIFI = 2;
    public static final int FIREWALL_RULE_DEFAULT = 0;
    public static final int FOREGROUND_THRESHOLD_STATE = 5;
    public static final int MASK_ALL_NETWORKS = 240;
    public static final int MASK_METERED_NETWORKS = 15;
    public static final int MASK_RESTRICTED_MODE_NETWORKS = 3840;
    public static final int POLICY_ALLOW_METERED_BACKGROUND = 4;
    public static final int POLICY_NONE = 0;
    public static final int POLICY_REJECT_METERED_BACKGROUND = 1;
    public static final int RULE_ALLOW_ALL = 32;
    public static final int RULE_ALLOW_METERED = 1;
    public static final int RULE_NONE = 0;
    public static final int RULE_REJECT_ALL = 64;
    public static final int RULE_REJECT_METERED = 4;
    public static final int RULE_REJECT_RESTRICTED_MODE = 1024;
    public static final int RULE_TEMPORARY_ALLOW_METERED = 2;
    public static final int SUBSCRIPTION_OVERRIDE_CONGESTED = 2;
    public static final int SUBSCRIPTION_OVERRIDE_UNMETERED = 1;
    public static final int TOP_THRESHOLD_STATE = 3;
    private final Context mContext;
    private INetworkPolicyManager mService;
    private final Map<SubscriptionCallback, SubscriptionCallbackProxy> mSubscriptionCallbackMap = new ConcurrentHashMap();
    private final Map<NetworkPolicyCallback, NetworkPolicyCallbackProxy> mNetworkPolicyCallbackMap = new ConcurrentHashMap();

    public static class Listener extends INetworkPolicyListener.Stub {
        @Override // android.net.INetworkPolicyListener
        public void onBlockedReasonChanged(int i, int i2, int i3) {
        }

        @Override // android.net.INetworkPolicyListener
        public void onMeteredIfacesChanged(String[] strArr) {
        }

        @Override // android.net.INetworkPolicyListener
        public void onRestrictBackgroundChanged(boolean z) {
        }

        @Override // android.net.INetworkPolicyListener
        public void onSubscriptionOverride(int i, int i2, int i3, int[] iArr) {
        }

        @Override // android.net.INetworkPolicyListener
        public void onSubscriptionPlansChanged(int i, SubscriptionPlan[] subscriptionPlanArr) {
        }

        @Override // android.net.INetworkPolicyListener
        public void onUidPoliciesChanged(int i, int i2) {
        }

        @Override // android.net.INetworkPolicyListener
        public void onUidRulesChanged(int i, int i2) {
        }
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    @Deprecated
    public interface NetworkPolicyCallback {
        @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
        default void onUidBlockedReasonChanged(int i, int i2) {
        }
    }

    public static class SubscriptionCallback {
        public void onSubscriptionOverride(int i, int i2, int i3, int[] iArr) {
        }

        public void onSubscriptionPlansChanged(int i, SubscriptionPlan[] subscriptionPlanArr) {
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SubscriptionOverrideMask {
    }

    public static int getDefaultProcessNetworkCapabilities(int i) {
        return (i == 0 || i == 1 || i == 2 || i == 3 || i == 4 || i == 5) ? 40 : 0;
    }

    public static boolean isProcStateAllowedWhileIdleOrPowerSaveMode(int i, int i2) {
        if (i == -1) {
            return false;
        }
        return i <= 5 || (i2 & 8) != 0;
    }

    public static boolean isProcStateAllowedWhileOnRestrictBackground(int i, int i2) {
        if (i == -1) {
            return false;
        }
        if (i > 5) {
            return i <= 6 && (i2 & 32) != 0;
        }
        return true;
    }

    public NetworkPolicyManager(Context context, INetworkPolicyManager iNetworkPolicyManager) {
        if (iNetworkPolicyManager == null) {
            throw new IllegalArgumentException("missing INetworkPolicyManager");
        }
        this.mContext = context;
        this.mService = iNetworkPolicyManager;
    }

    public static NetworkPolicyManager from(Context context) {
        return (NetworkPolicyManager) context.getSystemService(Context.NETWORK_POLICY_SERVICE);
    }

    public void setUidPolicy(int i, int i2) {
        try {
            this.mService.setUidPolicy(i, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void addUidPolicy(int i, int i2) {
        try {
            this.mService.addUidPolicy(i, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void removeUidPolicy(int i, int i2) {
        try {
            this.mService.removeUidPolicy(i, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getUidPolicy(int i) {
        try {
            return this.mService.getUidPolicy(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int[] getUidsWithPolicy(int i) {
        try {
            return this.mService.getUidsWithPolicy(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void registerListener(INetworkPolicyListener iNetworkPolicyListener) {
        try {
            this.mService.registerListener(iNetworkPolicyListener);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void unregisterListener(INetworkPolicyListener iNetworkPolicyListener) {
        try {
            this.mService.unregisterListener(iNetworkPolicyListener);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void registerSubscriptionCallback(SubscriptionCallback subscriptionCallback) {
        if (subscriptionCallback == null) {
            throw new NullPointerException("Callback cannot be null.");
        }
        SubscriptionCallbackProxy subscriptionCallbackProxy = new SubscriptionCallbackProxy(this, subscriptionCallback);
        if (this.mSubscriptionCallbackMap.putIfAbsent(subscriptionCallback, subscriptionCallbackProxy) != null) {
            throw new IllegalArgumentException("Callback is already registered.");
        }
        registerListener(subscriptionCallbackProxy);
    }

    public void unregisterSubscriptionCallback(SubscriptionCallback subscriptionCallback) {
        if (subscriptionCallback == null) {
            throw new NullPointerException("Callback cannot be null.");
        }
        SubscriptionCallbackProxy remove = this.mSubscriptionCallbackMap.remove(subscriptionCallback);
        if (remove == null) {
            return;
        }
        unregisterListener(remove);
    }

    public void setNetworkPolicies(NetworkPolicy[] networkPolicyArr) {
        try {
            this.mService.setNetworkPolicies(networkPolicyArr);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public NetworkPolicy[] getNetworkPolicies() {
        try {
            return this.mService.getNetworkPolicies(this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setRestrictBackground(boolean z) {
        try {
            this.mService.setRestrictBackground(z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean getRestrictBackground() {
        try {
            return this.mService.getRestrictBackground();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public int getRestrictBackgroundStatus(int i) {
        try {
            return this.mService.getRestrictBackgroundStatus(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setSubscriptionOverride(int i, int i2, int i3, int[] iArr, long j, String str) {
        try {
            this.mService.setSubscriptionOverride(i, i2, i3, iArr, j, str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setSubscriptionPlans(int i, SubscriptionPlan[] subscriptionPlanArr, long j, String str) {
        try {
            this.mService.setSubscriptionPlans(i, subscriptionPlanArr, j, str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public SubscriptionPlan[] getSubscriptionPlans(int i, String str) {
        try {
            return this.mService.getSubscriptionPlans(i, str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public SubscriptionPlan getSubscriptionPlan(NetworkTemplate networkTemplate) {
        try {
            return this.mService.getSubscriptionPlan(networkTemplate);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public void notifyStatsProviderWarningReached() {
        try {
            this.mService.notifyStatsProviderWarningOrLimitReached();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public void notifyStatsProviderLimitReached() {
        try {
            this.mService.notifyStatsProviderWarningOrLimitReached();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void factoryReset(String str) {
        try {
            this.mService.factoryReset(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isUidNetworkingBlocked(int i, boolean z) {
        try {
            return this.mService.isUidNetworkingBlocked(i, z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isUidRestrictedOnMeteredNetworks(int i) {
        try {
            return this.mService.isUidRestrictedOnMeteredNetworks(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public int getMultipathPreference(Network network) {
        try {
            return this.mService.getMultipathPreference(network);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public static Iterator<Pair<ZonedDateTime, ZonedDateTime>> cycleIterator(NetworkPolicy networkPolicy) {
        final Iterator<Range<ZonedDateTime>> cycleIterator = networkPolicy.cycleIterator();
        return new Iterator<Pair<ZonedDateTime, ZonedDateTime>>() { // from class: android.net.NetworkPolicyManager.1
            @Override // java.util.Iterator
            public boolean hasNext() {
                return cycleIterator.hasNext();
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.Iterator
            public Pair<ZonedDateTime, ZonedDateTime> next() {
                if (hasNext()) {
                    Range range = (Range) cycleIterator.next();
                    return Pair.create((ZonedDateTime) range.getLower(), (ZonedDateTime) range.getUpper());
                }
                return Pair.create(null, null);
            }
        };
    }

    @Deprecated
    public static boolean isUidValidForPolicy(Context context, int i) {
        return Process.isApplicationUid(i);
    }

    public static String uidRulesToString(int i) {
        StringBuilder sb = new StringBuilder();
        sb.append(i);
        sb.append(" (");
        if (i == 0) {
            sb.append(KeyProperties.DIGEST_NONE);
        } else {
            sb.append(DebugUtils.flagsToString(NetworkPolicyManager.class, "RULE_", i));
        }
        sb.append(NavigationBarInflaterView.KEY_CODE_END);
        return sb.toString();
    }

    public static String uidPoliciesToString(int i) {
        StringBuilder sb = new StringBuilder();
        sb.append(i);
        sb.append(" (");
        if (i == 0) {
            sb.append(KeyProperties.DIGEST_NONE);
        } else {
            sb.append(DebugUtils.flagsToString(NetworkPolicyManager.class, "POLICY_", i));
        }
        sb.append(NavigationBarInflaterView.KEY_CODE_END);
        return sb.toString();
    }

    public static boolean isProcStateAllowedWhileIdleOrPowerSaveMode(UidState uidState) {
        if (uidState == null) {
            return false;
        }
        return isProcStateAllowedWhileIdleOrPowerSaveMode(uidState.procState, uidState.capability);
    }

    public static boolean isProcStateAllowedWhileInLowPowerStandby(UidState uidState) {
        return uidState != null && uidState.procState <= 3;
    }

    public static boolean isProcStateAllowedNetworkWhileBackground(UidState uidState) {
        if (uidState == null) {
            return false;
        }
        return uidState.procState < 12 || (uidState.capability & 8) != 0;
    }

    public static boolean isProcStateAllowedWhileOnRestrictBackground(UidState uidState) {
        if (uidState == null) {
            return false;
        }
        return isProcStateAllowedWhileOnRestrictBackground(uidState.procState, uidState.capability);
    }

    public static final class UidState {
        public int capability;
        public int procState;
        public long procStateSeq;
        public int uid;

        public UidState(int i, int i2, long j, int i3) {
            this.uid = i;
            this.procState = i2;
            this.procStateSeq = j;
            this.capability = i3;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("{procState=");
            sb.append(ActivityManager.procStateToString(this.procState));
            sb.append(",seq=");
            sb.append(this.procStateSeq);
            sb.append(",cap=");
            ActivityManager.printCapabilitiesSummary(sb, this.capability);
            sb.append("}");
            return sb.toString();
        }
    }

    public static String resolveNetworkId(WifiConfiguration wifiConfiguration) {
        return WifiInfo.sanitizeSsid(wifiConfiguration.isPasspoint() ? wifiConfiguration.providerFriendlyName : wifiConfiguration.SSID);
    }

    public static String resolveNetworkId(String str) {
        return WifiInfo.sanitizeSsid(str);
    }

    public static String blockedReasonsToString(int i) {
        return DebugUtils.flagsToString(ConnectivityManager.class, "BLOCKED_", i);
    }

    public static String allowedReasonsToString(int i) {
        return DebugUtils.flagsToString(NetworkPolicyManager.class, "ALLOWED_", i);
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    @Deprecated
    public void registerNetworkPolicyCallback(Executor executor, NetworkPolicyCallback networkPolicyCallback) {
        if (Flags.deprecateNetworkPolicyCallback()) {
            throw new UnsupportedOperationException("NetworkPolicyCallback is no longer supported. Please use ConnectivityManager APIs instead");
        }
        if (networkPolicyCallback == null) {
            throw new NullPointerException("Callback cannot be null.");
        }
        NetworkPolicyCallbackProxy networkPolicyCallbackProxy = new NetworkPolicyCallbackProxy(executor, networkPolicyCallback);
        registerListener(networkPolicyCallbackProxy);
        this.mNetworkPolicyCallbackMap.put(networkPolicyCallback, networkPolicyCallbackProxy);
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    @Deprecated
    public void unregisterNetworkPolicyCallback(NetworkPolicyCallback networkPolicyCallback) {
        if (Flags.deprecateNetworkPolicyCallback()) {
            throw new UnsupportedOperationException("NetworkPolicyCallback is no longer supported. Please use ConnectivityManager APIs instead");
        }
        if (networkPolicyCallback == null) {
            throw new NullPointerException("Callback cannot be null.");
        }
        NetworkPolicyCallbackProxy remove = this.mNetworkPolicyCallbackMap.remove(networkPolicyCallback);
        if (remove == null) {
            return;
        }
        unregisterListener(remove);
    }

    public static class NetworkPolicyCallbackProxy extends Listener {
        private final NetworkPolicyCallback mCallback;
        private final Executor mExecutor;

        NetworkPolicyCallbackProxy(Executor executor, NetworkPolicyCallback networkPolicyCallback) {
            this.mExecutor = executor;
            this.mCallback = networkPolicyCallback;
        }

        @Override // android.net.NetworkPolicyManager.Listener, android.net.INetworkPolicyListener
        public void onBlockedReasonChanged(int i, int i2, int i3) {
            if (i2 != i3) {
                NetworkPolicyManager.dispatchOnUidBlockedReasonChanged(this.mExecutor, this.mCallback, i, i3);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void dispatchOnUidBlockedReasonChanged(Executor executor, NetworkPolicyCallback networkPolicyCallback, int i, int i2) {
        if (executor == null) {
            networkPolicyCallback.onUidBlockedReasonChanged(i, i2);
        } else {
            executor.execute(PooledLambda.obtainRunnable(new TriConsumer() { // from class: android.net.NetworkPolicyManager$$ExternalSyntheticLambda0
                @Override // com.android.internal.util.function.TriConsumer
                public final void accept(Object obj, Object obj2, Object obj3) {
                    ((NetworkPolicyManager.NetworkPolicyCallback) obj).onUidBlockedReasonChanged(((Integer) obj2).intValue(), ((Integer) obj3).intValue());
                }
            }, networkPolicyCallback, Integer.valueOf(i), Integer.valueOf(i2)).recycleOnUse());
        }
    }

    public class SubscriptionCallbackProxy extends Listener {
        private final SubscriptionCallback mCallback;

        SubscriptionCallbackProxy(NetworkPolicyManager networkPolicyManager, SubscriptionCallback subscriptionCallback) {
            this.mCallback = subscriptionCallback;
        }

        @Override // android.net.NetworkPolicyManager.Listener, android.net.INetworkPolicyListener
        public void onSubscriptionOverride(int i, int i2, int i3, int[] iArr) {
            this.mCallback.onSubscriptionOverride(i, i2, i3, iArr);
        }

        @Override // android.net.NetworkPolicyManager.Listener, android.net.INetworkPolicyListener
        public void onSubscriptionPlansChanged(int i, SubscriptionPlan[] subscriptionPlanArr) {
            this.mCallback.onSubscriptionPlansChanged(i, subscriptionPlanArr);
        }
    }

    public int[] getAllFirewallRuleMobileData() {
        try {
            return this.mService.getAllFirewallRuleMobileData();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setFirewallRuleMobileData(int i, boolean z) {
        try {
            this.mService.setFirewallRuleMobileData(i, z);
        } catch (RemoteException unused) {
        }
    }

    public void setFirewallRuleWifi(int i, boolean z) {
        try {
            this.mService.setFirewallRuleWifi(i, z);
        } catch (RemoteException unused) {
        }
    }

    public void setFirewallRuleMobileDataMap(Map<Integer, Boolean> map) {
        try {
            this.mService.setFirewallRuleMobileDataMap(map);
        } catch (RemoteException unused) {
        }
    }

    public void setFirewallRuleWifiMap(Map<Integer, Boolean> map) {
        try {
            this.mService.setFirewallRuleWifiMap(map);
        } catch (RemoteException unused) {
        }
    }

    public boolean getFirewallRuleMobileData(int i) {
        try {
            return this.mService.getFirewallRuleMobileData(i);
        } catch (RemoteException unused) {
            return true;
        }
    }

    public boolean getFirewallRuleWifi(int i) {
        try {
            return this.mService.getFirewallRuleWifi(i);
        } catch (RemoteException unused) {
            return true;
        }
    }
}
