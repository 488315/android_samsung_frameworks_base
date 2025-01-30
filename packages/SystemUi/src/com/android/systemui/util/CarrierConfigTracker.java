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
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragment;
import com.android.systemui.statusbar.policy.CallbackController;
import java.util.Iterator;
import java.util.Set;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class CarrierConfigTracker extends BroadcastReceiver implements CallbackController {
    public final CarrierConfigManager mCarrierConfigManager;
    public boolean mDefaultAlwaysShowPrimarySignalBarInOpportunisticNetworkConfig;

    /* renamed from: mDefaultAlwaysShowPrimarySignalBarInOpportunisticNetworkConfigLoaded */
    public boolean f390x7ceaf5fa;
    public boolean mDefaultCarrierProvisionsWifiMergedNetworks;
    public boolean mDefaultCarrierProvisionsWifiMergedNetworksLoaded;
    public final SparseBooleanArray mCallStrengthConfigs = new SparseBooleanArray();
    public final SparseBooleanArray mNoCallingConfigs = new SparseBooleanArray();
    public final SparseBooleanArray mCarrierProvisionsWifiMergedNetworks = new SparseBooleanArray();
    public final SparseBooleanArray mShowOperatorNameConfigs = new SparseBooleanArray();
    public final Set mListeners = new ArraySet();
    public final Set mDataListeners = new ArraySet();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void addCallback(Object obj) {
        ((ArraySet) this.mListeners).add((CollapsedStatusBarFragment.C31812) obj);
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        PersistableBundle configForSubId;
        String action = intent.getAction();
        if (!"android.telephony.action.CARRIER_CONFIG_CHANGED".equals(action)) {
            if ("android.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED".equals(action)) {
                int intExtra = intent.getIntExtra("android.telephony.extra.SUBSCRIPTION_INDEX", -1);
                Iterator it = ((ArraySet) this.mDataListeners).iterator();
                while (it.hasNext()) {
                    ((DefaultDataSubscriptionChangedListener) it.next()).onDefaultSubscriptionChanged(intExtra);
                }
                return;
            }
            return;
        }
        int intExtra2 = intent.getIntExtra("android.telephony.extra.SUBSCRIPTION_INDEX", -1);
        if (SubscriptionManager.isValidSubscriptionId(intExtra2) && (configForSubId = this.mCarrierConfigManager.getConfigForSubId(intExtra2)) != null) {
            synchronized (this.mCallStrengthConfigs) {
                this.mCallStrengthConfigs.put(intExtra2, configForSubId.getBoolean("display_call_strength_indicator_bool"));
            }
            synchronized (this.mNoCallingConfigs) {
                this.mNoCallingConfigs.put(intExtra2, configForSubId.getBoolean("use_ip_for_calling_indicator_bool"));
            }
            synchronized (this.mCarrierProvisionsWifiMergedNetworks) {
                this.mCarrierProvisionsWifiMergedNetworks.put(intExtra2, configForSubId.getBoolean("carrier_provisions_wifi_merged_networks_bool"));
            }
            synchronized (this.mShowOperatorNameConfigs) {
                this.mShowOperatorNameConfigs.put(intExtra2, configForSubId.getBoolean("show_operator_name_in_statusbar_bool"));
            }
            Iterator it2 = ((ArraySet) this.mListeners).iterator();
            while (it2.hasNext()) {
                CollapsedStatusBarFragment collapsedStatusBarFragment = CollapsedStatusBarFragment.this;
                if (collapsedStatusBarFragment.mCarrierHomeLogoViewController == null) {
                    collapsedStatusBarFragment.initOperatorName();
                }
            }
        }
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void removeCallback(Object obj) {
        ((ArraySet) this.mListeners).remove((CollapsedStatusBarFragment.C31812) obj);
    }
}
