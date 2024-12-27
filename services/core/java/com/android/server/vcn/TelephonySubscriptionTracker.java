package com.android.server.vcn;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.vcn.VcnManager;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.ParcelUuid;
import android.os.PersistableBundle;
import android.telephony.CarrierConfigManager;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyCallback;
import android.telephony.TelephonyManager;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Slog;

import com.android.internal.telephony.flags.Flags;
import com.android.internal.util.IndentingPrintWriter;
import com.android.server.VcnManagementService;
import com.android.server.vcn.util.PersistableBundleUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executor;

public final class TelephonySubscriptionTracker extends BroadcastReceiver {
    public final ActiveDataSubscriptionIdListener mActiveDataSubIdListener;
    public final TelephonySubscriptionTrackerCallback mCallback;
    public final CarrierConfigManager mCarrierConfigManager;
    public final Context mContext;
    public TelephonySubscriptionSnapshot mCurrentSnapshot;
    public final Dependencies mDeps;
    public final Handler mHandler;
    public final AnonymousClass1 mSubscriptionChangedListener;
    public final SubscriptionManager mSubscriptionManager;
    public final TelephonyManager mTelephonyManager;
    public final Map mReadySubIdsBySlotId = new HashMap();
    public final Map mSubIdToCarrierConfigMap = new HashMap();
    public final List mCarrierPrivilegesCallbacks = new ArrayList();
    public final TelephonySubscriptionTracker$$ExternalSyntheticLambda1
            mCarrierConfigChangeListener =
                    new CarrierConfigManager
                            .CarrierConfigChangeListener() { // from class:
                                                             // com.android.server.vcn.TelephonySubscriptionTracker$$ExternalSyntheticLambda1
                        @Override // android.telephony.CarrierConfigManager.CarrierConfigChangeListener
                        public final void onCarrierConfigChanged(int i, int i2, int i3, int i4) {
                            TelephonySubscriptionTracker telephonySubscriptionTracker =
                                    TelephonySubscriptionTracker.this;
                            telephonySubscriptionTracker.getClass();
                            if (i == -1) {
                                return;
                            }
                            if (!SubscriptionManager.isValidSubscriptionId(i2)) {
                                Integer num =
                                        (Integer)
                                                ((HashMap)
                                                                telephonySubscriptionTracker
                                                                        .mReadySubIdsBySlotId)
                                                        .remove(Integer.valueOf(i));
                                if (num != null) {
                                    ((HashMap)
                                                    telephonySubscriptionTracker
                                                            .mSubIdToCarrierConfigMap)
                                            .remove(num);
                                }
                                telephonySubscriptionTracker.handleSubscriptionsChanged();
                                return;
                            }
                            PersistableBundle carrierConfigSubset =
                                    Flags.fixCrashOnGettingConfigWhenPhoneIsGone()
                                            ? CarrierConfigManager.getCarrierConfigSubset(
                                                    telephonySubscriptionTracker.mContext,
                                                    i2,
                                                    VcnManager.VCN_RELATED_CARRIER_CONFIG_KEYS)
                                            : telephonySubscriptionTracker.mCarrierConfigManager
                                                    .getConfigForSubId(
                                                            i2,
                                                            VcnManager
                                                                    .VCN_RELATED_CARRIER_CONFIG_KEYS);
                            telephonySubscriptionTracker.mDeps.getClass();
                            if (CarrierConfigManager.isConfigForIdentifiedCarrier(
                                    carrierConfigSubset)) {
                                ((HashMap) telephonySubscriptionTracker.mReadySubIdsBySlotId)
                                        .put(Integer.valueOf(i), Integer.valueOf(i2));
                                if (!carrierConfigSubset.isEmpty()) {
                                    ((HashMap)
                                                    telephonySubscriptionTracker
                                                            .mSubIdToCarrierConfigMap)
                                            .put(
                                                    Integer.valueOf(i2),
                                                    new PersistableBundleUtils
                                                            .PersistableBundleWrapper(
                                                            carrierConfigSubset));
                                }
                                telephonySubscriptionTracker.handleSubscriptionsChanged();
                            }
                        }
                    };

    public final class ActiveDataSubscriptionIdListener extends TelephonyCallback
            implements TelephonyCallback.ActiveDataSubscriptionIdListener {
        public ActiveDataSubscriptionIdListener() {}

        @Override // android.telephony.TelephonyCallback.ActiveDataSubscriptionIdListener
        public final void onActiveDataSubscriptionIdChanged(int i) {
            TelephonySubscriptionTracker.this.handleSubscriptionsChanged();
        }
    }

    public class Dependencies {}

    public final class TelephonySubscriptionSnapshot {
        public static final TelephonySubscriptionSnapshot EMPTY_SNAPSHOT =
                new TelephonySubscriptionSnapshot(
                        -1, Collections.emptyMap(), Collections.emptyMap(), Collections.emptyMap());
        public final int mActiveDataSubId;
        public final Map mPrivilegedPackages;
        public final Map mSubIdToCarrierConfigMap;
        public final Map mSubIdToInfoMap;

        public TelephonySubscriptionSnapshot(int i, Map map, Map map2, Map map3) {
            this.mActiveDataSubId = i;
            Objects.requireNonNull(map, "subIdToInfoMap was null");
            Objects.requireNonNull(map3, "privilegedPackages was null");
            Objects.requireNonNull(map2, "subIdToCarrierConfigMap was null");
            this.mSubIdToInfoMap = Collections.unmodifiableMap(new HashMap(map));
            this.mSubIdToCarrierConfigMap = Collections.unmodifiableMap(new HashMap(map2));
            ArrayMap arrayMap = new ArrayMap();
            for (Map.Entry entry : map3.entrySet()) {
                arrayMap.put(
                        (ParcelUuid) entry.getKey(),
                        Collections.unmodifiableSet((Set) entry.getValue()));
            }
            this.mPrivilegedPackages = Collections.unmodifiableMap(arrayMap);
        }

        public final void dump(IndentingPrintWriter indentingPrintWriter) {
            indentingPrintWriter.println("TelephonySubscriptionSnapshot:");
            indentingPrintWriter.increaseIndent();
            indentingPrintWriter.println("mActiveDataSubId: " + this.mActiveDataSubId);
            indentingPrintWriter.println("mSubIdToInfoMap: " + this.mSubIdToInfoMap);
            indentingPrintWriter.println(
                    "mSubIdToCarrierConfigMap: " + this.mSubIdToCarrierConfigMap);
            indentingPrintWriter.println("mPrivilegedPackages: " + this.mPrivilegedPackages);
            indentingPrintWriter.decreaseIndent();
        }

        public final boolean equals(Object obj) {
            if (!(obj instanceof TelephonySubscriptionSnapshot)) {
                return false;
            }
            TelephonySubscriptionSnapshot telephonySubscriptionSnapshot =
                    (TelephonySubscriptionSnapshot) obj;
            return this.mActiveDataSubId == telephonySubscriptionSnapshot.mActiveDataSubId
                    && this.mSubIdToInfoMap.equals(telephonySubscriptionSnapshot.mSubIdToInfoMap)
                    && this.mSubIdToCarrierConfigMap.equals(
                            telephonySubscriptionSnapshot.mSubIdToCarrierConfigMap)
                    && this.mPrivilegedPackages.equals(
                            telephonySubscriptionSnapshot.mPrivilegedPackages);
        }

        public final Set getAllSubIdsInGroup(ParcelUuid parcelUuid) {
            ArraySet arraySet = new ArraySet();
            for (Map.Entry entry : this.mSubIdToInfoMap.entrySet()) {
                if (parcelUuid.equals(((SubscriptionInfo) entry.getValue()).getGroupUuid())) {
                    arraySet.add((Integer) entry.getKey());
                }
            }
            return arraySet;
        }

        public final PersistableBundleUtils.PersistableBundleWrapper getCarrierConfigForSubGrp(
                ParcelUuid parcelUuid) {
            Iterator it = ((ArraySet) getAllSubIdsInGroup(parcelUuid)).iterator();
            PersistableBundleUtils.PersistableBundleWrapper persistableBundleWrapper = null;
            while (it.hasNext()) {
                Integer num = (Integer) it.next();
                num.intValue();
                PersistableBundleUtils.PersistableBundleWrapper persistableBundleWrapper2 =
                        (PersistableBundleUtils.PersistableBundleWrapper)
                                this.mSubIdToCarrierConfigMap.get(num);
                if (persistableBundleWrapper2 != null) {
                    if (!(this.mSubIdToInfoMap.containsKey(num)
                            ? ((SubscriptionInfo) this.mSubIdToInfoMap.get(num)).isOpportunistic()
                            : false)) {
                        return persistableBundleWrapper2;
                    }
                    persistableBundleWrapper = persistableBundleWrapper2;
                }
            }
            return persistableBundleWrapper;
        }

        public final int hashCode() {
            return Objects.hash(
                    Integer.valueOf(this.mActiveDataSubId),
                    this.mSubIdToInfoMap,
                    this.mSubIdToCarrierConfigMap,
                    this.mPrivilegedPackages);
        }

        public final boolean packageHasPermissionsForSubscriptionGroup(
                ParcelUuid parcelUuid, String str) {
            Set set = (Set) this.mPrivilegedPackages.get(parcelUuid);
            return set != null && set.contains(str);
        }

        public final String toString() {
            return "TelephonySubscriptionSnapshot{ mActiveDataSubId="
                    + this.mActiveDataSubId
                    + ", mSubIdToInfoMap="
                    + this.mSubIdToInfoMap
                    + ", mSubIdToCarrierConfigMap="
                    + this.mSubIdToCarrierConfigMap
                    + ", mPrivilegedPackages="
                    + this.mPrivilegedPackages
                    + " }";
        }
    }

    public interface TelephonySubscriptionTrackerCallback {}

    public TelephonySubscriptionTracker(
            Context context,
            Handler handler,
            TelephonySubscriptionTrackerCallback telephonySubscriptionTrackerCallback,
            Dependencies dependencies) {
        Objects.requireNonNull(context, "Missing context");
        this.mContext = context;
        Objects.requireNonNull(handler, "Missing handler");
        this.mHandler = handler;
        Objects.requireNonNull(telephonySubscriptionTrackerCallback, "Missing callback");
        this.mCallback = telephonySubscriptionTrackerCallback;
        Objects.requireNonNull(dependencies, "Missing deps");
        this.mDeps = dependencies;
        this.mTelephonyManager =
                (TelephonyManager) context.getSystemService(TelephonyManager.class);
        this.mSubscriptionManager =
                (SubscriptionManager) context.getSystemService(SubscriptionManager.class);
        this.mCarrierConfigManager =
                (CarrierConfigManager) context.getSystemService(CarrierConfigManager.class);
        this.mActiveDataSubIdListener = new ActiveDataSubscriptionIdListener();
        this.mSubscriptionChangedListener =
                new SubscriptionManager
                        .OnSubscriptionsChangedListener() { // from class:
                                                            // com.android.server.vcn.TelephonySubscriptionTracker.1
                    @Override // android.telephony.SubscriptionManager.OnSubscriptionsChangedListener
                    public final void onSubscriptionsChanged() {
                        TelephonySubscriptionTracker.this.handleSubscriptionsChanged();
                    }
                };
    }

    public Map getReadySubIdsBySlotId() {
        return Collections.unmodifiableMap(this.mReadySubIdsBySlotId);
    }

    public Map getSubIdToCarrierConfigMap() {
        return Collections.unmodifiableMap(this.mSubIdToCarrierConfigMap);
    }

    public final void handleSubscriptionsChanged() {
        HashMap hashMap = new HashMap();
        HashMap hashMap2 = new HashMap();
        List<SubscriptionInfo> allSubscriptionInfoList =
                this.mSubscriptionManager.getAllSubscriptionInfoList();
        if (allSubscriptionInfoList == null) {
            return;
        }
        for (SubscriptionInfo subscriptionInfo : allSubscriptionInfoList) {
            if (subscriptionInfo.getGroupUuid() != null) {
                hashMap2.put(
                        Integer.valueOf(subscriptionInfo.getSubscriptionId()), subscriptionInfo);
                if (subscriptionInfo.getSimSlotIndex() != -1
                        && ((HashMap) this.mReadySubIdsBySlotId)
                                .values()
                                .contains(Integer.valueOf(subscriptionInfo.getSubscriptionId()))) {
                    TelephonyManager createForSubscriptionId =
                            this.mTelephonyManager.createForSubscriptionId(
                                    subscriptionInfo.getSubscriptionId());
                    ParcelUuid groupUuid = subscriptionInfo.getGroupUuid();
                    Set set = (Set) hashMap.getOrDefault(groupUuid, new ArraySet());
                    set.addAll(createForSubscriptionId.getPackagesWithCarrierPrivileges());
                    hashMap.put(groupUuid, set);
                }
            }
        }
        this.mDeps.getClass();
        final TelephonySubscriptionSnapshot telephonySubscriptionSnapshot =
                new TelephonySubscriptionSnapshot(
                        SubscriptionManager.getActiveDataSubscriptionId(),
                        hashMap2,
                        this.mSubIdToCarrierConfigMap,
                        hashMap);
        if (telephonySubscriptionSnapshot.equals(this.mCurrentSnapshot)) {
            return;
        }
        this.mCurrentSnapshot = telephonySubscriptionSnapshot;
        this.mHandler.post(
                new Runnable() { // from class:
                                 // com.android.server.vcn.TelephonySubscriptionTracker$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        TelephonySubscriptionTracker telephonySubscriptionTracker =
                                TelephonySubscriptionTracker.this;
                        ((VcnManagementService.VcnSubscriptionTrackerCallback)
                                        telephonySubscriptionTracker.mCallback)
                                .onNewSnapshot(telephonySubscriptionSnapshot);
                    }
                });
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        action.getClass();
        if (!action.equals("android.telephony.action.MULTI_SIM_CONFIG_CHANGED")) {
            Slog.v(
                    "TelephonySubscriptionTracker",
                    "Unknown intent received with action: " + intent.getAction());
            return;
        }
        Iterator it = ((ArrayList) this.mCarrierPrivilegesCallbacks).iterator();
        while (it.hasNext()) {
            this.mTelephonyManager.unregisterCarrierPrivilegesCallback(
                    (TelephonyManager.CarrierPrivilegesCallback) it.next());
        }
        ((ArrayList) this.mCarrierPrivilegesCallbacks).clear();
        int activeModemCount = this.mTelephonyManager.getActiveModemCount();
        Iterator it2 = ((HashMap) this.mReadySubIdsBySlotId).keySet().iterator();
        while (it2.hasNext()) {
            if (((Integer) it2.next()).intValue() >= activeModemCount) {
                it2.remove();
            }
        }
        registerCarrierPrivilegesCallbacks();
        handleSubscriptionsChanged();
    }

    public final void registerCarrierPrivilegesCallbacks() {
        Executor handlerExecutor = new HandlerExecutor(this.mHandler);
        int activeModemCount = this.mTelephonyManager.getActiveModemCount();
        for (int i = 0; i < activeModemCount; i++) {
            try {
                TelephonyManager.CarrierPrivilegesCallback carrierPrivilegesCallback =
                        new TelephonyManager
                                .CarrierPrivilegesCallback() { // from class:
                                                               // com.android.server.vcn.TelephonySubscriptionTracker.2
                            public final void onCarrierPrivilegesChanged(Set set, Set set2) {
                                TelephonySubscriptionTracker.this.handleSubscriptionsChanged();
                            }
                        };
                this.mTelephonyManager.registerCarrierPrivilegesCallback(
                        i, handlerExecutor, carrierPrivilegesCallback);
                ((ArrayList) this.mCarrierPrivilegesCallbacks).add(carrierPrivilegesCallback);
            } catch (IllegalArgumentException e) {
                Slog.wtf(
                        "TelephonySubscriptionTracker",
                        "Encounted exception registering carrier privileges listeners",
                        e);
                return;
            }
        }
    }

    public void setReadySubIdsBySlotId(Map map) {
        ((HashMap) this.mReadySubIdsBySlotId).clear();
        ((HashMap) this.mReadySubIdsBySlotId).putAll(map);
    }

    public void setSubIdToCarrierConfigMap(Map map) {
        ((HashMap) this.mSubIdToCarrierConfigMap).clear();
        ((HashMap) this.mSubIdToCarrierConfigMap).putAll(map);
    }
}
