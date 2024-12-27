package com.android.systemui.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.PersistableBundle;
import android.telephony.CarrierConfigManager;
import android.telephony.SubscriptionManager;
import android.util.ArraySet;
import android.util.SparseBooleanArray;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.statusbar.policy.CallbackController;
import java.util.Iterator;
import java.util.Set;

public class CarrierConfigTracker extends BroadcastReceiver implements CallbackController {
    private final CarrierConfigManager mCarrierConfigManager;
    private boolean mDefaultAlwaysShowPrimarySignalBarInOpportunisticNetworkConfig;
    private boolean mDefaultAlwaysShowPrimarySignalBarInOpportunisticNetworkConfigLoaded;
    private boolean mDefaultCallStrengthConfig;
    private boolean mDefaultCallStrengthConfigLoaded;
    private boolean mDefaultCarrierProvisionsWifiMergedNetworks;
    private boolean mDefaultCarrierProvisionsWifiMergedNetworksLoaded;
    private boolean mDefaultNoCallingConfig;
    private boolean mDefaultNoCallingConfigLoaded;
    private boolean mDefaultShowOperatorNameConfig;
    private boolean mDefaultShowOperatorNameConfigLoaded;
    private final SparseBooleanArray mCallStrengthConfigs = new SparseBooleanArray();
    private final SparseBooleanArray mNoCallingConfigs = new SparseBooleanArray();
    private final SparseBooleanArray mCarrierProvisionsWifiMergedNetworks = new SparseBooleanArray();
    private final SparseBooleanArray mShowOperatorNameConfigs = new SparseBooleanArray();
    private final Set<CarrierConfigChangedListener> mListeners = new ArraySet();
    private final Set<DefaultDataSubscriptionChangedListener> mDataListeners = new ArraySet();

    public interface CarrierConfigChangedListener {
        void onCarrierConfigChanged();
    }

    public interface DefaultDataSubscriptionChangedListener {
        void onDefaultSubscriptionChanged(int i);
    }

    public CarrierConfigTracker(CarrierConfigManager carrierConfigManager, BroadcastDispatcher broadcastDispatcher) {
        this.mCarrierConfigManager = carrierConfigManager;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.telephony.action.CARRIER_CONFIG_CHANGED");
        intentFilter.addAction("android.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED");
        broadcastDispatcher.registerReceiver(intentFilter, this);
    }

    private void notifyCarrierConfigChanged() {
        Iterator<CarrierConfigChangedListener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onCarrierConfigChanged();
        }
    }

    private void notifyDefaultDataSubscriptionChanged(int i) {
        Iterator<DefaultDataSubscriptionChangedListener> it = this.mDataListeners.iterator();
        while (it.hasNext()) {
            it.next().onDefaultSubscriptionChanged(i);
        }
    }

    private void updateDefaultDataSubscription(Intent intent) {
        notifyDefaultDataSubscriptionChanged(intent.getIntExtra("android.telephony.extra.SUBSCRIPTION_INDEX", -1));
    }

    private void updateFromNewCarrierConfig(Intent intent) {
        PersistableBundle configForSubId;
        int intExtra = intent.getIntExtra("android.telephony.extra.SUBSCRIPTION_INDEX", -1);
        if (this.mCarrierConfigManager == null || !SubscriptionManager.isValidSubscriptionId(intExtra) || (configForSubId = this.mCarrierConfigManager.getConfigForSubId(intExtra)) == null) {
            return;
        }
        synchronized (this.mCallStrengthConfigs) {
            this.mCallStrengthConfigs.put(intExtra, configForSubId.getBoolean("display_call_strength_indicator_bool"));
        }
        synchronized (this.mNoCallingConfigs) {
            this.mNoCallingConfigs.put(intExtra, configForSubId.getBoolean("use_ip_for_calling_indicator_bool"));
        }
        synchronized (this.mCarrierProvisionsWifiMergedNetworks) {
            this.mCarrierProvisionsWifiMergedNetworks.put(intExtra, configForSubId.getBoolean("carrier_provisions_wifi_merged_networks_bool"));
        }
        synchronized (this.mShowOperatorNameConfigs) {
            this.mShowOperatorNameConfigs.put(intExtra, configForSubId.getBoolean("show_operator_name_in_statusbar_bool"));
        }
        notifyCarrierConfigChanged();
    }

    public void addDefaultDataSubscriptionChangedListener(DefaultDataSubscriptionChangedListener defaultDataSubscriptionChangedListener) {
        this.mDataListeners.add(defaultDataSubscriptionChangedListener);
    }

    public boolean getAlwaysShowPrimarySignalBarInOpportunisticNetworkDefault() {
        if (!this.mDefaultAlwaysShowPrimarySignalBarInOpportunisticNetworkConfigLoaded) {
            this.mDefaultAlwaysShowPrimarySignalBarInOpportunisticNetworkConfig = CarrierConfigManager.getDefaultConfig().getBoolean("always_show_primary_signal_bar_in_opportunistic_network_boolean");
            this.mDefaultAlwaysShowPrimarySignalBarInOpportunisticNetworkConfigLoaded = true;
        }
        return this.mDefaultAlwaysShowPrimarySignalBarInOpportunisticNetworkConfig;
    }

    public boolean getCallStrengthConfig(int i) {
        synchronized (this.mCallStrengthConfigs) {
            try {
                if (this.mCallStrengthConfigs.indexOfKey(i) >= 0) {
                    return this.mCallStrengthConfigs.get(i);
                }
                if (!this.mDefaultCallStrengthConfigLoaded) {
                    this.mDefaultCallStrengthConfig = CarrierConfigManager.getDefaultConfig().getBoolean("display_call_strength_indicator_bool");
                    this.mDefaultCallStrengthConfigLoaded = true;
                }
                return this.mDefaultCallStrengthConfig;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public boolean getCarrierProvisionsWifiMergedNetworksBool(int i) {
        synchronized (this.mCarrierProvisionsWifiMergedNetworks) {
            try {
                if (this.mCarrierProvisionsWifiMergedNetworks.indexOfKey(i) >= 0) {
                    return this.mCarrierProvisionsWifiMergedNetworks.get(i);
                }
                if (!this.mDefaultCarrierProvisionsWifiMergedNetworksLoaded) {
                    this.mDefaultCarrierProvisionsWifiMergedNetworks = CarrierConfigManager.getDefaultConfig().getBoolean("carrier_provisions_wifi_merged_networks_bool");
                    this.mDefaultCarrierProvisionsWifiMergedNetworksLoaded = true;
                }
                return this.mDefaultCarrierProvisionsWifiMergedNetworks;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public boolean getNoCallingConfig(int i) {
        synchronized (this.mNoCallingConfigs) {
            try {
                if (this.mNoCallingConfigs.indexOfKey(i) >= 0) {
                    return this.mNoCallingConfigs.get(i);
                }
                if (!this.mDefaultNoCallingConfigLoaded) {
                    this.mDefaultNoCallingConfig = CarrierConfigManager.getDefaultConfig().getBoolean("use_ip_for_calling_indicator_bool");
                    this.mDefaultNoCallingConfigLoaded = true;
                }
                return this.mDefaultNoCallingConfig;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public boolean getShowOperatorNameInStatusBarConfig(int i) {
        return this.mShowOperatorNameConfigs.indexOfKey(i) >= 0 ? this.mShowOperatorNameConfigs.get(i) : getShowOperatorNameInStatusBarConfigDefault();
    }

    public boolean getShowOperatorNameInStatusBarConfigDefault() {
        if (!this.mDefaultShowOperatorNameConfigLoaded) {
            this.mDefaultShowOperatorNameConfig = CarrierConfigManager.getDefaultConfig().getBoolean("show_operator_name_in_statusbar_bool");
            this.mDefaultShowOperatorNameConfigLoaded = true;
        }
        return this.mDefaultShowOperatorNameConfig;
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public /* bridge */ /* synthetic */ Object observe(Lifecycle lifecycle, Object obj) {
        super.observe(lifecycle, obj);
        return obj;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if ("android.telephony.action.CARRIER_CONFIG_CHANGED".equals(action)) {
            updateFromNewCarrierConfig(intent);
        } else if ("android.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED".equals(action)) {
            updateDefaultDataSubscription(intent);
        }
    }

    public void removeDataSubscriptionChangedListener(DefaultDataSubscriptionChangedListener defaultDataSubscriptionChangedListener) {
        this.mDataListeners.remove(defaultDataSubscriptionChangedListener);
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public void addCallback(CarrierConfigChangedListener carrierConfigChangedListener) {
        this.mListeners.add(carrierConfigChangedListener);
    }

    public Object observe(LifecycleOwner lifecycleOwner, Object obj) {
        return observe(lifecycleOwner.getLifecycle(), obj);
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public void removeCallback(CarrierConfigChangedListener carrierConfigChangedListener) {
        this.mListeners.remove(carrierConfigChangedListener);
    }
}
