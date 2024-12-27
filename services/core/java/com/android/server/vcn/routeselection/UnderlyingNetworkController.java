package com.android.server.vcn.routeselection;

import android.net.ConnectivityManager;
import android.net.IpSecTransform;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.net.TelephonyNetworkSpecifier;
import android.net.vcn.Flags;
import android.net.vcn.VcnCellUnderlyingNetworkTemplate;
import android.net.vcn.VcnGatewayConnectionConfig;
import android.net.vcn.VcnUnderlyingNetworkTemplate;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.ParcelUuid;
import android.telephony.TelephonyCallback;
import android.telephony.TelephonyManager;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.LocalLog;
import android.util.Slog;

import com.android.internal.util.HexDump;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.VcnManagementService;
import com.android.server.pm.PackageManagerShellCommandDataLoader;
import com.android.server.vcn.TelephonySubscriptionTracker;
import com.android.server.vcn.VcnContext;
import com.android.server.vcn.VcnGatewayConnection;
import com.android.server.vcn.routeselection.IpSecPacketLossDetector.PollIpSecStateRunnable;
import com.android.server.vcn.util.PersistableBundleUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

public final class UnderlyingNetworkController {
    public final VcnActiveDataSubscriptionIdListener mActiveDataSubIdListener;
    public PersistableBundleUtils.PersistableBundleWrapper mCarrierConfig;
    public final UnderlyingNetworkControllerCallback mCb;
    public final List mCellBringupCallbacks;
    public final VcnGatewayConnectionConfig mConnectionConfig;
    public final ConnectivityManager mConnectivityManager;
    public UnderlyingNetworkRecord mCurrentRecord;
    public final Dependencies mDeps;
    public final Handler mHandler;
    public boolean mIsQuitting;
    public TelephonySubscriptionTracker.TelephonySubscriptionSnapshot mLastSnapshot;
    public UnderlyingNetworkListener mRouteSelectionCallback;
    public final ParcelUuid mSubscriptionGroup;
    public final Map mUnderlyingNetworkRecords;
    public final VcnContext mVcnContext;
    public ConnectivityManager.NetworkCallback mWifiBringupCallback;
    public ConnectivityManager.NetworkCallback mWifiEntryRssiThresholdCallback;
    public ConnectivityManager.NetworkCallback mWifiExitRssiThresholdCallback;

    public final class CapabilityMatchCriteria {
        public final int capability;
        public final int matchCriteria;

        public CapabilityMatchCriteria(int i, int i2) {
            this.capability = i;
            this.matchCriteria = i2;
        }

        public final boolean equals(Object obj) {
            if (!(obj instanceof CapabilityMatchCriteria)) {
                return false;
            }
            CapabilityMatchCriteria capabilityMatchCriteria = (CapabilityMatchCriteria) obj;
            return this.capability == capabilityMatchCriteria.capability
                    && this.matchCriteria == capabilityMatchCriteria.matchCriteria;
        }

        public final int hashCode() {
            return Objects.hash(
                    Integer.valueOf(this.capability), Integer.valueOf(this.matchCriteria));
        }
    }

    public class Dependencies {}

    class NetworkBringupCallback extends ConnectivityManager.NetworkCallback {}

    class NetworkEvaluatorCallbackImpl
            implements UnderlyingNetworkEvaluator.NetworkEvaluatorCallback {
        public NetworkEvaluatorCallbackImpl() {}

        public final void onEvaluationResultChanged() {
            UnderlyingNetworkController underlyingNetworkController =
                    UnderlyingNetworkController.this;
            if (underlyingNetworkController.mVcnContext.isFlagNetworkMetricMonitorEnabled()) {
                VcnContext vcnContext = underlyingNetworkController.mVcnContext;
                vcnContext.getClass();
                if (VcnContext.isFlagIpSecTransformStateEnabled()) {
                    vcnContext.ensureRunningOnLooperThread();
                    underlyingNetworkController.reevaluateNetworks();
                    return;
                }
            }
            underlyingNetworkController.logWtf(
                    "#onEvaluationResultChanged: unexpected call; flags missing");
        }
    }

    public interface UnderlyingNetworkControllerCallback {}

    public class UnderlyingNetworkListener extends ConnectivityManager.NetworkCallback {
        public UnderlyingNetworkListener() {
            super(1);
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public final void onAvailable(Network network) {
            UnderlyingNetworkController underlyingNetworkController =
                    UnderlyingNetworkController.this;
            Map map = underlyingNetworkController.mUnderlyingNetworkRecords;
            Dependencies dependencies = underlyingNetworkController.mDeps;
            VcnContext vcnContext = underlyingNetworkController.mVcnContext;
            List<VcnUnderlyingNetworkTemplate> vcnUnderlyingNetworkPriorities =
                    underlyingNetworkController.mConnectionConfig
                            .getVcnUnderlyingNetworkPriorities();
            UnderlyingNetworkController underlyingNetworkController2 =
                    UnderlyingNetworkController.this;
            ParcelUuid parcelUuid = underlyingNetworkController2.mSubscriptionGroup;
            TelephonySubscriptionTracker.TelephonySubscriptionSnapshot
                    telephonySubscriptionSnapshot = underlyingNetworkController2.mLastSnapshot;
            PersistableBundleUtils.PersistableBundleWrapper persistableBundleWrapper =
                    underlyingNetworkController2.mCarrierConfig;
            NetworkEvaluatorCallbackImpl networkEvaluatorCallbackImpl =
                    underlyingNetworkController2.new NetworkEvaluatorCallbackImpl();
            dependencies.getClass();
            ((ArrayMap) map)
                    .put(
                            network,
                            new UnderlyingNetworkEvaluator(
                                    vcnContext,
                                    network,
                                    vcnUnderlyingNetworkPriorities,
                                    parcelUuid,
                                    telephonySubscriptionSnapshot,
                                    persistableBundleWrapper,
                                    networkEvaluatorCallbackImpl,
                                    new UnderlyingNetworkEvaluator.Dependencies()));
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public final void onBlockedStatusChanged(Network network, boolean z) {
            UnderlyingNetworkEvaluator underlyingNetworkEvaluator =
                    (UnderlyingNetworkEvaluator)
                            ((ArrayMap) UnderlyingNetworkController.this.mUnderlyingNetworkRecords)
                                    .get(network);
            if (underlyingNetworkEvaluator == null) {
                UnderlyingNetworkController.this.logWtf(
                        "Got blocked status change for unknown key: " + network);
                return;
            }
            List<VcnUnderlyingNetworkTemplate> vcnUnderlyingNetworkPriorities =
                    UnderlyingNetworkController.this.mConnectionConfig
                            .getVcnUnderlyingNetworkPriorities();
            UnderlyingNetworkController underlyingNetworkController =
                    UnderlyingNetworkController.this;
            ParcelUuid parcelUuid = underlyingNetworkController.mSubscriptionGroup;
            TelephonySubscriptionTracker.TelephonySubscriptionSnapshot
                    telephonySubscriptionSnapshot = underlyingNetworkController.mLastSnapshot;
            PersistableBundleUtils.PersistableBundleWrapper persistableBundleWrapper =
                    underlyingNetworkController.mCarrierConfig;
            UnderlyingNetworkRecord.Builder builder =
                    underlyingNetworkEvaluator.mNetworkRecordBuilder;
            builder.mIsBlocked = z;
            builder.mWasIsBlockedSet = true;
            underlyingNetworkEvaluator.updatePriorityClass(
                    vcnUnderlyingNetworkPriorities,
                    parcelUuid,
                    telephonySubscriptionSnapshot,
                    persistableBundleWrapper);
            if (builder.isValid()) {
                UnderlyingNetworkController.this.reevaluateNetworks();
            }
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public final void onCapabilitiesChanged(
                Network network, NetworkCapabilities networkCapabilities) {
            UnderlyingNetworkEvaluator underlyingNetworkEvaluator =
                    (UnderlyingNetworkEvaluator)
                            ((ArrayMap) UnderlyingNetworkController.this.mUnderlyingNetworkRecords)
                                    .get(network);
            if (underlyingNetworkEvaluator == null) {
                UnderlyingNetworkController.this.logWtf(
                        "Got capabilities change for unknown key: " + network);
                return;
            }
            List<VcnUnderlyingNetworkTemplate> vcnUnderlyingNetworkPriorities =
                    UnderlyingNetworkController.this.mConnectionConfig
                            .getVcnUnderlyingNetworkPriorities();
            UnderlyingNetworkController underlyingNetworkController =
                    UnderlyingNetworkController.this;
            ParcelUuid parcelUuid = underlyingNetworkController.mSubscriptionGroup;
            TelephonySubscriptionTracker.TelephonySubscriptionSnapshot
                    telephonySubscriptionSnapshot = underlyingNetworkController.mLastSnapshot;
            PersistableBundleUtils.PersistableBundleWrapper persistableBundleWrapper =
                    underlyingNetworkController.mCarrierConfig;
            UnderlyingNetworkRecord.Builder builder =
                    underlyingNetworkEvaluator.mNetworkRecordBuilder;
            builder.mNetworkCapabilities = networkCapabilities;
            underlyingNetworkEvaluator.updatePriorityClass(
                    vcnUnderlyingNetworkPriorities,
                    parcelUuid,
                    telephonySubscriptionSnapshot,
                    persistableBundleWrapper);
            if (Flags.evaluateIpsecLossOnLpNcChange()) {
                Iterator it = ((ArrayList) underlyingNetworkEvaluator.mMetricMonitors).iterator();
                while (it.hasNext()) {
                    IpSecPacketLossDetector ipSecPacketLossDetector =
                            (IpSecPacketLossDetector) ((NetworkMetricMonitor) it.next());
                    if (ipSecPacketLossDetector.mIsStarted) {
                        ipSecPacketLossDetector.mHandler.removeCallbacksAndEqualMessages(
                                ipSecPacketLossDetector.mCancellationToken);
                        ipSecPacketLossDetector.mHandler.postDelayed(
                                ipSecPacketLossDetector.new PollIpSecStateRunnable(),
                                ipSecPacketLossDetector.mCancellationToken,
                                0L);
                    }
                }
            }
            if (builder.isValid()) {
                UnderlyingNetworkController.this.reevaluateNetworks();
            }
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public final void onLinkPropertiesChanged(Network network, LinkProperties linkProperties) {
            UnderlyingNetworkEvaluator underlyingNetworkEvaluator =
                    (UnderlyingNetworkEvaluator)
                            ((ArrayMap) UnderlyingNetworkController.this.mUnderlyingNetworkRecords)
                                    .get(network);
            if (underlyingNetworkEvaluator == null) {
                UnderlyingNetworkController.this.logWtf(
                        "Got link properties change for unknown key: " + network);
                return;
            }
            List<VcnUnderlyingNetworkTemplate> vcnUnderlyingNetworkPriorities =
                    UnderlyingNetworkController.this.mConnectionConfig
                            .getVcnUnderlyingNetworkPriorities();
            UnderlyingNetworkController underlyingNetworkController =
                    UnderlyingNetworkController.this;
            ParcelUuid parcelUuid = underlyingNetworkController.mSubscriptionGroup;
            TelephonySubscriptionTracker.TelephonySubscriptionSnapshot
                    telephonySubscriptionSnapshot = underlyingNetworkController.mLastSnapshot;
            PersistableBundleUtils.PersistableBundleWrapper persistableBundleWrapper =
                    underlyingNetworkController.mCarrierConfig;
            UnderlyingNetworkRecord.Builder builder =
                    underlyingNetworkEvaluator.mNetworkRecordBuilder;
            builder.mLinkProperties = linkProperties;
            underlyingNetworkEvaluator.updatePriorityClass(
                    vcnUnderlyingNetworkPriorities,
                    parcelUuid,
                    telephonySubscriptionSnapshot,
                    persistableBundleWrapper);
            if (Flags.evaluateIpsecLossOnLpNcChange()) {
                Iterator it = ((ArrayList) underlyingNetworkEvaluator.mMetricMonitors).iterator();
                while (it.hasNext()) {
                    IpSecPacketLossDetector ipSecPacketLossDetector =
                            (IpSecPacketLossDetector) ((NetworkMetricMonitor) it.next());
                    if (ipSecPacketLossDetector.mIsStarted) {
                        ipSecPacketLossDetector.mHandler.removeCallbacksAndEqualMessages(
                                ipSecPacketLossDetector.mCancellationToken);
                        ipSecPacketLossDetector.mHandler.postDelayed(
                                ipSecPacketLossDetector.new PollIpSecStateRunnable(),
                                ipSecPacketLossDetector.mCancellationToken,
                                0L);
                    }
                }
            }
            if (builder.isValid()) {
                UnderlyingNetworkController.this.reevaluateNetworks();
            }
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public final void onLost(Network network) {
            if (UnderlyingNetworkController.this.mVcnContext.isFlagNetworkMetricMonitorEnabled()) {
                UnderlyingNetworkController.this.mVcnContext.getClass();
                if (VcnContext.isFlagIpSecTransformStateEnabled()) {
                    ((UnderlyingNetworkEvaluator)
                                    ((ArrayMap)
                                                    UnderlyingNetworkController.this
                                                            .mUnderlyingNetworkRecords)
                                            .get(network))
                            .close();
                }
            }
            ((ArrayMap) UnderlyingNetworkController.this.mUnderlyingNetworkRecords).remove(network);
            UnderlyingNetworkController.this.reevaluateNetworks();
        }
    }

    public final class VcnActiveDataSubscriptionIdListener extends TelephonyCallback
            implements TelephonyCallback.ActiveDataSubscriptionIdListener {
        public VcnActiveDataSubscriptionIdListener() {}

        @Override // android.telephony.TelephonyCallback.ActiveDataSubscriptionIdListener
        public final void onActiveDataSubscriptionIdChanged(int i) {
            UnderlyingNetworkController.this.reevaluateNetworks();
        }
    }

    public UnderlyingNetworkController(
            VcnContext vcnContext,
            VcnGatewayConnectionConfig vcnGatewayConnectionConfig,
            ParcelUuid parcelUuid,
            TelephonySubscriptionTracker.TelephonySubscriptionSnapshot
                    telephonySubscriptionSnapshot,
            UnderlyingNetworkControllerCallback underlyingNetworkControllerCallback,
            Dependencies dependencies) {
        VcnActiveDataSubscriptionIdListener vcnActiveDataSubscriptionIdListener =
                new VcnActiveDataSubscriptionIdListener();
        this.mActiveDataSubIdListener = vcnActiveDataSubscriptionIdListener;
        this.mUnderlyingNetworkRecords = new ArrayMap();
        this.mCellBringupCallbacks = new ArrayList();
        this.mIsQuitting = false;
        Objects.requireNonNull(vcnContext, "Missing vcnContext");
        this.mVcnContext = vcnContext;
        Objects.requireNonNull(vcnGatewayConnectionConfig, "Missing connectionConfig");
        this.mConnectionConfig = vcnGatewayConnectionConfig;
        Objects.requireNonNull(parcelUuid, "Missing subscriptionGroup");
        this.mSubscriptionGroup = parcelUuid;
        Objects.requireNonNull(telephonySubscriptionSnapshot, "Missing snapshot");
        this.mLastSnapshot = telephonySubscriptionSnapshot;
        Objects.requireNonNull(underlyingNetworkControllerCallback, "Missing cb");
        this.mCb = underlyingNetworkControllerCallback;
        Objects.requireNonNull(dependencies, "Missing deps");
        this.mDeps = dependencies;
        Handler handler = new Handler(vcnContext.mLooper);
        this.mHandler = handler;
        this.mConnectivityManager =
                (ConnectivityManager)
                        vcnContext.mContext.getSystemService(ConnectivityManager.class);
        ((TelephonyManager) vcnContext.mContext.getSystemService(TelephonyManager.class))
                .registerTelephonyCallback(
                        new HandlerExecutor(handler), vcnActiveDataSubscriptionIdListener);
        this.mCarrierConfig = this.mLastSnapshot.getCarrierConfigForSubGrp(parcelUuid);
        registerOrUpdateNetworkRequests();
    }

    public static NetworkRequest.Builder getBaseNetworkRequestBuilder() {
        return new NetworkRequest.Builder()
                .removeCapability(14)
                .removeCapability(13)
                .removeCapability(28);
    }

    public final NetworkRequest.Builder getBaseWifiNetworkRequestBuilder() {
        return getBaseNetworkRequestBuilder()
                .addTransportType(1)
                .addCapability(12)
                .setSubscriptionIds(
                        this.mLastSnapshot.getAllSubIdsInGroup(this.mSubscriptionGroup));
    }

    public final String getLogPrefix() {
        StringBuilder sb = new StringBuilder("(");
        ParcelUuid parcelUuid = this.mSubscriptionGroup;
        sb.append(parcelUuid == null ? null : HexDump.toHexString(parcelUuid.hashCode()));
        sb.append(PackageManagerShellCommandDataLoader.STDIN_PATH);
        sb.append(this.mConnectionConfig.getGatewayConnectionName());
        sb.append(PackageManagerShellCommandDataLoader.STDIN_PATH);
        sb.append(System.identityHashCode(this));
        sb.append(") ");
        return sb.toString();
    }

    public final TreeSet getSortedUnderlyingNetworks() {
        final VcnContext vcnContext = this.mVcnContext;
        TreeSet treeSet =
                new TreeSet(
                        new Comparator() { // from class:
                                           // com.android.server.vcn.routeselection.UnderlyingNetworkEvaluator$$ExternalSyntheticLambda0
                            @Override // java.util.Comparator
                            public final int compare(Object obj, Object obj2) {
                                boolean z;
                                VcnContext vcnContext2 = VcnContext.this;
                                UnderlyingNetworkEvaluator underlyingNetworkEvaluator =
                                        (UnderlyingNetworkEvaluator) obj;
                                UnderlyingNetworkEvaluator underlyingNetworkEvaluator2 =
                                        (UnderlyingNetworkEvaluator) obj2;
                                vcnContext2.getClass();
                                if (!(VcnContext.isFlagIpSecTransformStateEnabled()
                                                && vcnContext2.isFlagNetworkMetricMonitorEnabled())
                                        || (z = underlyingNetworkEvaluator.mIsPenalized)
                                                == underlyingNetworkEvaluator2.mIsPenalized) {
                                    int i = underlyingNetworkEvaluator.mPriorityClass;
                                    int i2 = underlyingNetworkEvaluator2.mPriorityClass;
                                    if (i == i2) {
                                        if (!underlyingNetworkEvaluator.mIsSelected) {
                                            if (underlyingNetworkEvaluator2.mIsSelected) {
                                                return 1;
                                            }
                                        }
                                    }
                                    return Integer.compare(i, i2);
                                }
                                if (z) {
                                    return 1;
                                }
                                return -1;
                            }
                        });
        for (UnderlyingNetworkEvaluator underlyingNetworkEvaluator :
                ((ArrayMap) this.mUnderlyingNetworkRecords).values()) {
            if (underlyingNetworkEvaluator.mPriorityClass != -1) {
                treeSet.add(underlyingNetworkEvaluator);
            }
        }
        return treeSet;
    }

    public final void logWtf(String str) {
        Slog.wtf("UnderlyingNetworkController", str);
        LocalLog localLog = VcnManagementService.LOCAL_LOG;
        StringBuilder sb = new StringBuilder("UnderlyingNetworkController[WTF ] ");
        sb.append("[ UnderlyingNetworkController " + getLogPrefix() + "]");
        sb.append(str);
        localLog.log(sb.toString());
    }

    public final void reevaluateNetworks() {
        if (this.mIsQuitting || this.mRouteSelectionCallback == null) {
            return;
        }
        TreeSet sortedUnderlyingNetworks = getSortedUnderlyingNetworks();
        UnderlyingNetworkEvaluator underlyingNetworkEvaluator =
                sortedUnderlyingNetworks.isEmpty()
                        ? null
                        : (UnderlyingNetworkEvaluator) sortedUnderlyingNetworks.first();
        UnderlyingNetworkRecord build =
                underlyingNetworkEvaluator == null
                        ? null
                        : underlyingNetworkEvaluator.mNetworkRecordBuilder.build();
        if (Objects.equals(this.mCurrentRecord, build)) {
            return;
        }
        Iterator it = sortedUnderlyingNetworks.iterator();
        String str = "";
        while (it.hasNext()) {
            UnderlyingNetworkEvaluator underlyingNetworkEvaluator2 =
                    (UnderlyingNetworkEvaluator) it.next();
            if (!str.isEmpty()) {
                str = str.concat(", ");
            }
            StringBuilder m = BootReceiver$$ExternalSyntheticOutline0.m(str);
            m.append(underlyingNetworkEvaluator2.mNetworkRecordBuilder.mNetwork);
            m.append(": ");
            m.append(underlyingNetworkEvaluator2.mPriorityClass);
            str = m.toString();
        }
        UnderlyingNetworkRecord underlyingNetworkRecord = this.mCurrentRecord;
        if (!Objects.equals(
                underlyingNetworkRecord == null ? null : underlyingNetworkRecord.network,
                build == null ? null : build.network)) {
            StringBuilder sb = new StringBuilder("Selected network changed to ");
            sb.append(build == null ? null : build.network);
            sb.append(", selected from list: ");
            sb.append(str);
            String sb2 = sb.toString();
            Slog.i("UnderlyingNetworkController", getLogPrefix() + sb2);
            LocalLog localLog = VcnManagementService.LOCAL_LOG;
            StringBuilder sb3 = new StringBuilder("[INFO] ");
            sb3.append("[ UnderlyingNetworkController " + getLogPrefix() + "]");
            sb3.append(sb2);
            localLog.log(sb3.toString());
        }
        this.mCurrentRecord = build;
        ((VcnGatewayConnection.VcnUnderlyingNetworkControllerCallback) this.mCb)
                .onSelectedUnderlyingNetworkChanged(build);
        for (UnderlyingNetworkEvaluator underlyingNetworkEvaluator3 :
                ((ArrayMap) this.mUnderlyingNetworkRecords).values()) {
            boolean z = underlyingNetworkEvaluator == underlyingNetworkEvaluator3;
            List<VcnUnderlyingNetworkTemplate> vcnUnderlyingNetworkPriorities =
                    this.mConnectionConfig.getVcnUnderlyingNetworkPriorities();
            ParcelUuid parcelUuid = this.mSubscriptionGroup;
            TelephonySubscriptionTracker.TelephonySubscriptionSnapshot
                    telephonySubscriptionSnapshot = this.mLastSnapshot;
            PersistableBundleUtils.PersistableBundleWrapper persistableBundleWrapper =
                    this.mCarrierConfig;
            underlyingNetworkEvaluator3.mIsSelected = z;
            underlyingNetworkEvaluator3.updatePriorityClass(
                    vcnUnderlyingNetworkPriorities,
                    parcelUuid,
                    telephonySubscriptionSnapshot,
                    persistableBundleWrapper);
            Iterator it2 = ((ArrayList) underlyingNetworkEvaluator3.mMetricMonitors).iterator();
            while (it2.hasNext()) {
                NetworkMetricMonitor networkMetricMonitor = (NetworkMetricMonitor) it2.next();
                if (networkMetricMonitor.mIsSelectedUnderlyingNetwork != z) {
                    networkMetricMonitor.mIsSelectedUnderlyingNetwork = z;
                    IpSecPacketLossDetector ipSecPacketLossDetector =
                            (IpSecPacketLossDetector) networkMetricMonitor;
                    if (!ipSecPacketLossDetector.mIsSelectedUnderlyingNetwork) {
                        ipSecPacketLossDetector.mInboundTransform = null;
                        ipSecPacketLossDetector.mIsValidationFailed = false;
                        ipSecPacketLossDetector.mIsStarted = false;
                        ipSecPacketLossDetector.mHandler.removeCallbacksAndEqualMessages(
                                ipSecPacketLossDetector.mCancellationToken);
                        ipSecPacketLossDetector.mLastIpSecTransformState = null;
                    }
                }
            }
        }
    }

    public final void registerOrUpdateNetworkRequests() {
        UnderlyingNetworkListener underlyingNetworkListener = this.mRouteSelectionCallback;
        ConnectivityManager.NetworkCallback networkCallback = this.mWifiBringupCallback;
        ConnectivityManager.NetworkCallback networkCallback2 = this.mWifiEntryRssiThresholdCallback;
        ConnectivityManager.NetworkCallback networkCallback3 = this.mWifiExitRssiThresholdCallback;
        ArrayList arrayList = new ArrayList(this.mCellBringupCallbacks);
        ((ArrayList) this.mCellBringupCallbacks).clear();
        VcnContext vcnContext = this.mVcnContext;
        if (vcnContext.isFlagNetworkMetricMonitorEnabled()
                && VcnContext.isFlagIpSecTransformStateEnabled()) {
            Iterator it = ((ArrayMap) this.mUnderlyingNetworkRecords).values().iterator();
            while (it.hasNext()) {
                ((UnderlyingNetworkEvaluator) it.next()).close();
            }
        }
        ((ArrayMap) this.mUnderlyingNetworkRecords).clear();
        if (this.mIsQuitting) {
            this.mRouteSelectionCallback = null;
            this.mWifiBringupCallback = null;
            this.mWifiEntryRssiThresholdCallback = null;
            this.mWifiExitRssiThresholdCallback = null;
        } else {
            this.mRouteSelectionCallback = new UnderlyingNetworkListener();
            ConnectivityManager connectivityManager = this.mConnectivityManager;
            NetworkRequest build =
                    vcnContext.mIsInTestMode
                            ? new NetworkRequest.Builder()
                                    .clearCapabilities()
                                    .addTransportType(7)
                                    .setSubscriptionIds(
                                            this.mLastSnapshot.getAllSubIdsInGroup(
                                                    this.mSubscriptionGroup))
                                    .build()
                            : getBaseNetworkRequestBuilder()
                                    .addCapability(16)
                                    .addCapability(21)
                                    .setSubscriptionIds(
                                            this.mLastSnapshot.getAllSubIdsInGroup(
                                                    this.mSubscriptionGroup))
                                    .build();
            UnderlyingNetworkListener underlyingNetworkListener2 = this.mRouteSelectionCallback;
            Handler handler = this.mHandler;
            connectivityManager.registerNetworkCallback(build, underlyingNetworkListener2, handler);
            this.mWifiEntryRssiThresholdCallback = new NetworkBringupCallback();
            ConnectivityManager connectivityManager2 = this.mConnectivityManager;
            NetworkRequest.Builder baseWifiNetworkRequestBuilder =
                    getBaseWifiNetworkRequestBuilder();
            PersistableBundleUtils.PersistableBundleWrapper persistableBundleWrapper =
                    this.mCarrierConfig;
            connectivityManager2.registerNetworkCallback(
                    baseWifiNetworkRequestBuilder
                            .setSignalStrength(
                                    persistableBundleWrapper != null
                                            ? persistableBundleWrapper.mBundle.getInt(
                                                    "vcn_network_selection_wifi_entry_rssi_threshold",
                                                    -70)
                                            : -70)
                            .build(),
                    this.mWifiEntryRssiThresholdCallback,
                    handler);
            this.mWifiExitRssiThresholdCallback = new NetworkBringupCallback();
            ConnectivityManager connectivityManager3 = this.mConnectivityManager;
            NetworkRequest.Builder baseWifiNetworkRequestBuilder2 =
                    getBaseWifiNetworkRequestBuilder();
            PersistableBundleUtils.PersistableBundleWrapper persistableBundleWrapper2 =
                    this.mCarrierConfig;
            connectivityManager3.registerNetworkCallback(
                    baseWifiNetworkRequestBuilder2
                            .setSignalStrength(
                                    persistableBundleWrapper2 != null
                                            ? persistableBundleWrapper2.mBundle.getInt(
                                                    "vcn_network_selection_wifi_exit_rssi_threshold",
                                                    -74)
                                            : -74)
                            .build(),
                    this.mWifiExitRssiThresholdCallback,
                    handler);
            this.mWifiBringupCallback = new NetworkBringupCallback();
            this.mConnectivityManager.requestBackgroundNetwork(
                    getBaseWifiNetworkRequestBuilder().build(), this.mWifiBringupCallback, handler);
            Iterator it2 =
                    ((ArraySet) this.mLastSnapshot.getAllSubIdsInGroup(this.mSubscriptionGroup))
                            .iterator();
            while (it2.hasNext()) {
                int intValue = ((Integer) it2.next()).intValue();
                VcnGatewayConnectionConfig vcnGatewayConnectionConfig = this.mConnectionConfig;
                ArraySet arraySet = new ArraySet();
                for (VcnUnderlyingNetworkTemplate vcnUnderlyingNetworkTemplate :
                        vcnGatewayConnectionConfig.getVcnUnderlyingNetworkPriorities()) {
                    if (vcnUnderlyingNetworkTemplate instanceof VcnCellUnderlyingNetworkTemplate) {
                        ArraySet arraySet2 = new ArraySet();
                        for (Map.Entry entry :
                                ((VcnCellUnderlyingNetworkTemplate) vcnUnderlyingNetworkTemplate)
                                        .getCapabilitiesMatchCriteria()
                                        .entrySet()) {
                            int intValue2 = ((Integer) entry.getKey()).intValue();
                            int intValue3 = ((Integer) entry.getValue()).intValue();
                            if (intValue3 != 0) {
                                arraySet2.add(new CapabilityMatchCriteria(intValue2, intValue3));
                            }
                        }
                        arraySet.add(arraySet2);
                    }
                }
                int i = 1;
                arraySet.add(Collections.singleton(new CapabilityMatchCriteria(12, 1)));
                Iterator it3 = arraySet.iterator();
                while (it3.hasNext()) {
                    Set<CapabilityMatchCriteria> set = (Set) it3.next();
                    NetworkBringupCallback networkBringupCallback = new NetworkBringupCallback();
                    ((ArrayList) this.mCellBringupCallbacks).add(networkBringupCallback);
                    ConnectivityManager connectivityManager4 = this.mConnectivityManager;
                    NetworkRequest.Builder networkSpecifier =
                            getBaseNetworkRequestBuilder()
                                    .addTransportType(0)
                                    .setNetworkSpecifier(new TelephonyNetworkSpecifier(intValue));
                    for (CapabilityMatchCriteria capabilityMatchCriteria : set) {
                        Iterator it4 = it2;
                        int i2 = capabilityMatchCriteria.capability;
                        int i3 = capabilityMatchCriteria.matchCriteria;
                        if (i3 == i) {
                            networkSpecifier.addCapability(i2);
                        } else if (i3 == 2) {
                            networkSpecifier.addForbiddenCapability(i2);
                        }
                        it2 = it4;
                        i = 1;
                    }
                    connectivityManager4.requestBackgroundNetwork(
                            networkSpecifier.build(), networkBringupCallback, handler);
                    it2 = it2;
                    i = 1;
                }
            }
        }
        if (underlyingNetworkListener != null) {
            this.mConnectivityManager.unregisterNetworkCallback(underlyingNetworkListener);
        }
        if (networkCallback != null) {
            this.mConnectivityManager.unregisterNetworkCallback(networkCallback);
        }
        if (networkCallback2 != null) {
            this.mConnectivityManager.unregisterNetworkCallback(networkCallback2);
        }
        if (networkCallback3 != null) {
            this.mConnectivityManager.unregisterNetworkCallback(networkCallback3);
        }
        Iterator it5 = arrayList.iterator();
        while (it5.hasNext()) {
            this.mConnectivityManager.unregisterNetworkCallback(
                    (ConnectivityManager.NetworkCallback) it5.next());
        }
    }

    public final void updateInboundTransform(
            UnderlyingNetworkRecord underlyingNetworkRecord, IpSecTransform ipSecTransform) {
        if (!this.mVcnContext.isFlagNetworkMetricMonitorEnabled()
                || !VcnContext.isFlagIpSecTransformStateEnabled()) {
            logWtf("#updateInboundTransform: unexpected call; flags missing");
            return;
        }
        Objects.requireNonNull(underlyingNetworkRecord, "currentNetwork is null");
        Objects.requireNonNull(ipSecTransform, "transform is null");
        UnderlyingNetworkRecord underlyingNetworkRecord2 = this.mCurrentRecord;
        if (underlyingNetworkRecord2 == null
                || this.mRouteSelectionCallback == null
                || !Objects.equals(
                        underlyingNetworkRecord.network, underlyingNetworkRecord2.network)) {
            return;
        }
        UnderlyingNetworkEvaluator underlyingNetworkEvaluator =
                (UnderlyingNetworkEvaluator)
                        ((ArrayMap) this.mUnderlyingNetworkRecords)
                                .get(this.mCurrentRecord.network);
        if (!underlyingNetworkEvaluator.mIsSelected) {
            underlyingNetworkEvaluator.logWtf("setInboundTransform on an unselected evaluator");
            return;
        }
        Iterator it = ((ArrayList) underlyingNetworkEvaluator.mMetricMonitors).iterator();
        while (it.hasNext()) {
            NetworkMetricMonitor networkMetricMonitor = (NetworkMetricMonitor) it.next();
            networkMetricMonitor.getClass();
            networkMetricMonitor.setInboundTransformInternal(
                    new NetworkMetricMonitor.IpSecTransformWrapper(ipSecTransform));
        }
    }
}
