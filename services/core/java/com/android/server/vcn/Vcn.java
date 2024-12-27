package com.android.server.vcn;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.net.vcn.Flags;
import android.net.vcn.VcnConfig;
import android.net.vcn.VcnGatewayConnectionConfig;
import android.net.vcn.VcnUnderlyingNetworkTemplate;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.Message;
import android.os.ParcelUuid;
import android.provider.Settings;
import android.telephony.TelephonyCallback;
import android.telephony.TelephonyManager;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Slog;
import com.android.internal.util.HexDump;
import com.android.internal.util.IndentingPrintWriter;
import com.android.server.VcnManagementService;
import com.android.server.attention.AttentionManagerService$AttentionCheck$$ExternalSyntheticOutline0;
import com.android.server.pm.PackageManagerShellCommandDataLoader;
import com.android.server.vcn.TelephonySubscriptionTracker;
import com.android.server.vcn.VcnGatewayConnection;
import com.android.server.vcn.VcnNetworkProvider;
import com.android.server.vcn.routeselection.IpSecPacketLossDetector;
import com.android.server.vcn.routeselection.IpSecPacketLossDetector.PollIpSecStateRunnable;
import com.android.server.vcn.routeselection.NetworkMetricMonitor;
import com.android.server.vcn.routeselection.UnderlyingNetworkController;
import com.android.server.vcn.routeselection.UnderlyingNetworkEvaluator;
import com.android.server.vcn.routeselection.UnderlyingNetworkRecord;
import com.android.server.vcn.util.PersistableBundleUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class Vcn extends Handler {
    public static final List CAPS_REQUIRING_MOBILE_DATA = Arrays.asList(12, 2);
    public VcnConfig mConfig;
    public volatile int mCurrentStatus;
    public final Dependencies mDeps;
    public boolean mIsMobileDataEnabled;
    public TelephonySubscriptionTracker.TelephonySubscriptionSnapshot mLastSnapshot;
    public final VcnMobileDataContentObserver mMobileDataSettingsObserver;
    public final Map mMobileDataStateListeners;
    public final VcnNetworkRequestListener mRequestListener;
    public final ParcelUuid mSubscriptionGroup;
    public final VcnManagementService.VcnCallback mVcnCallback;
    public final VcnContext mVcnContext;
    public final Map mVcnGatewayConnections;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public class Dependencies {
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public class VcnContentResolver {
        public final ContentResolver mImpl;

        public VcnContentResolver(VcnContext vcnContext) {
            this.mImpl = vcnContext.mContext.getContentResolver();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface VcnGatewayStatusCallback {
        void onGatewayConnectionError(int i, String str, String str2, String str3);

        void onQuit();

        void onSafeModeStatusChanged();
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class VcnGatewayStatusCallbackImpl implements VcnGatewayStatusCallback {
        public final VcnGatewayConnectionConfig mGatewayConnectionConfig;

        public VcnGatewayStatusCallbackImpl(VcnGatewayConnectionConfig vcnGatewayConnectionConfig) {
            this.mGatewayConnectionConfig = vcnGatewayConnectionConfig;
        }

        @Override // com.android.server.vcn.Vcn.VcnGatewayStatusCallback
        public final void onGatewayConnectionError(int i, String str, String str2, String str3) {
            ((VcnManagementService.VcnCallbackImpl) Vcn.this.mVcnCallback).onGatewayConnectionError(i, str, str2, str3);
        }

        @Override // com.android.server.vcn.Vcn.VcnGatewayStatusCallback
        public final void onQuit() {
            VcnGatewayConnectionConfig vcnGatewayConnectionConfig = this.mGatewayConnectionConfig;
            Vcn vcn = Vcn.this;
            vcn.sendMessage(vcn.obtainMessage(3, vcnGatewayConnectionConfig));
        }

        @Override // com.android.server.vcn.Vcn.VcnGatewayStatusCallback
        public final void onSafeModeStatusChanged() {
            Vcn vcn = Vcn.this;
            vcn.sendMessage(vcn.obtainMessage(4));
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class VcnMobileDataContentObserver extends ContentObserver {
        public VcnMobileDataContentObserver(Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            Vcn vcn = Vcn.this;
            vcn.sendMessage(vcn.obtainMessage(5));
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class VcnNetworkRequestListener implements VcnNetworkProvider.NetworkRequestListener {
        public VcnNetworkRequestListener() {
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    class VcnUserMobileDataStateListener extends TelephonyCallback implements TelephonyCallback.UserMobileDataStateListener {
        public VcnUserMobileDataStateListener() {
        }

        @Override // android.telephony.TelephonyCallback.UserMobileDataStateListener
        public final void onUserMobileDataStateChanged(boolean z) {
            Vcn vcn = Vcn.this;
            vcn.sendMessage(vcn.obtainMessage(5));
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Vcn(VcnContext vcnContext, ParcelUuid parcelUuid, VcnConfig vcnConfig, TelephonySubscriptionTracker.TelephonySubscriptionSnapshot telephonySubscriptionSnapshot, VcnManagementService.VcnCallback vcnCallback, Dependencies dependencies) {
        super(vcnContext.mLooper);
        Objects.requireNonNull(vcnContext, "Missing vcnContext");
        this.mMobileDataStateListeners = new ArrayMap();
        this.mVcnGatewayConnections = new HashMap();
        this.mCurrentStatus = 2;
        this.mIsMobileDataEnabled = false;
        this.mVcnContext = vcnContext;
        Objects.requireNonNull(parcelUuid, "Missing subscriptionGroup");
        this.mSubscriptionGroup = parcelUuid;
        Objects.requireNonNull(vcnCallback, "Missing vcnCallback");
        this.mVcnCallback = vcnCallback;
        Objects.requireNonNull(dependencies, "Missing deps");
        this.mDeps = dependencies;
        VcnNetworkRequestListener vcnNetworkRequestListener = new VcnNetworkRequestListener();
        this.mRequestListener = vcnNetworkRequestListener;
        VcnContentResolver vcnContentResolver = new VcnContentResolver(vcnContext);
        VcnMobileDataContentObserver vcnMobileDataContentObserver = new VcnMobileDataContentObserver(this);
        vcnContentResolver.mImpl.registerContentObserver(Settings.Global.getUriFor("mobile_data"), true, vcnMobileDataContentObserver);
        Objects.requireNonNull(vcnConfig, "Missing config");
        this.mConfig = vcnConfig;
        Objects.requireNonNull(telephonySubscriptionSnapshot, "Missing snapshot");
        this.mLastSnapshot = telephonySubscriptionSnapshot;
        this.mIsMobileDataEnabled = getMobileDataStatus();
        updateMobileDataStateListeners();
        vcnContext.mVcnNetworkProvider.registerListener(vcnNetworkRequestListener);
    }

    public final void dump(IndentingPrintWriter indentingPrintWriter) {
        this.mVcnContext.ensureRunningOnLooperThread();
        indentingPrintWriter.println("Vcn (" + this.mSubscriptionGroup + "):");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println("mCurrentStatus: " + this.mCurrentStatus);
        indentingPrintWriter.println("mIsMobileDataEnabled: " + this.mIsMobileDataEnabled);
        indentingPrintWriter.println();
        indentingPrintWriter.println("mVcnGatewayConnections:");
        indentingPrintWriter.increaseIndent();
        for (VcnGatewayConnection vcnGatewayConnection : ((HashMap) this.mVcnGatewayConnections).values()) {
            vcnGatewayConnection.mVcnContext.ensureRunningOnLooperThread();
            indentingPrintWriter.println("VcnGatewayConnection (" + vcnGatewayConnection.mConnectionConfig.getGatewayConnectionName() + "):");
            indentingPrintWriter.increaseIndent();
            StringBuilder sb = new StringBuilder("Current state: ");
            sb.append(vcnGatewayConnection.getCurrentState() == null ? null : vcnGatewayConnection.getCurrentState().getClass().getSimpleName());
            indentingPrintWriter.println(sb.toString());
            StringBuilder m = AttentionManagerService$AttentionCheck$$ExternalSyntheticOutline0.m(AttentionManagerService$AttentionCheck$$ExternalSyntheticOutline0.m(new StringBuilder("mIsQuitting: "), vcnGatewayConnection.mIsQuitting.mValue, indentingPrintWriter, "mIsInSafeMode: "), vcnGatewayConnection.mIsInSafeMode, indentingPrintWriter, "mCurrentToken: ");
            m.append(vcnGatewayConnection.mCurrentToken);
            indentingPrintWriter.println(m.toString());
            indentingPrintWriter.println("mFailedAttempts: " + vcnGatewayConnection.mFailedAttempts);
            StringBuilder sb2 = new StringBuilder("mNetworkAgent.getNetwork(): ");
            VcnGatewayConnection.VcnNetworkAgent vcnNetworkAgent = vcnGatewayConnection.mNetworkAgent;
            sb2.append(vcnNetworkAgent == null ? null : vcnNetworkAgent.mImpl.getNetwork());
            indentingPrintWriter.println(sb2.toString());
            indentingPrintWriter.println();
            UnderlyingNetworkController underlyingNetworkController = vcnGatewayConnection.mUnderlyingNetworkController;
            underlyingNetworkController.getClass();
            indentingPrintWriter.println("UnderlyingNetworkController:");
            indentingPrintWriter.increaseIndent();
            StringBuilder sb3 = new StringBuilder("Carrier WiFi Entry Threshold: ");
            PersistableBundleUtils.PersistableBundleWrapper persistableBundleWrapper = underlyingNetworkController.mCarrierConfig;
            sb3.append(persistableBundleWrapper != null ? persistableBundleWrapper.mBundle.getInt("vcn_network_selection_wifi_entry_rssi_threshold", -70) : -70);
            indentingPrintWriter.println(sb3.toString());
            StringBuilder sb4 = new StringBuilder("Carrier WiFi Exit Threshold: ");
            PersistableBundleUtils.PersistableBundleWrapper persistableBundleWrapper2 = underlyingNetworkController.mCarrierConfig;
            sb4.append(persistableBundleWrapper2 != null ? persistableBundleWrapper2.mBundle.getInt("vcn_network_selection_wifi_exit_rssi_threshold", -74) : -74);
            indentingPrintWriter.println(sb4.toString());
            StringBuilder sb5 = new StringBuilder("Currently selected: ");
            UnderlyingNetworkRecord underlyingNetworkRecord = underlyingNetworkController.mCurrentRecord;
            sb5.append(underlyingNetworkRecord != null ? underlyingNetworkRecord.network : null);
            indentingPrintWriter.println(sb5.toString());
            indentingPrintWriter.println("VcnUnderlyingNetworkTemplate list:");
            indentingPrintWriter.increaseIndent();
            int i = 0;
            for (VcnUnderlyingNetworkTemplate vcnUnderlyingNetworkTemplate : underlyingNetworkController.mConnectionConfig.getVcnUnderlyingNetworkPriorities()) {
                indentingPrintWriter.println("Priority index: " + i);
                vcnUnderlyingNetworkTemplate.dump(indentingPrintWriter);
                i++;
            }
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.println();
            indentingPrintWriter.println("Underlying networks:");
            indentingPrintWriter.increaseIndent();
            if (underlyingNetworkController.mRouteSelectionCallback != null) {
                Iterator it = underlyingNetworkController.getSortedUnderlyingNetworks().iterator();
                while (it.hasNext()) {
                    UnderlyingNetworkEvaluator underlyingNetworkEvaluator = (UnderlyingNetworkEvaluator) it.next();
                    underlyingNetworkEvaluator.getClass();
                    indentingPrintWriter.println("UnderlyingNetworkEvaluator:");
                    indentingPrintWriter.increaseIndent();
                    UnderlyingNetworkRecord.Builder builder = underlyingNetworkEvaluator.mNetworkRecordBuilder;
                    if (builder.isValid()) {
                        UnderlyingNetworkRecord build = builder.build();
                        indentingPrintWriter.println("UnderlyingNetworkRecord:");
                        indentingPrintWriter.increaseIndent();
                        indentingPrintWriter.println("mNetwork: " + build.network);
                        indentingPrintWriter.println("mNetworkCapabilities: " + build.networkCapabilities);
                        indentingPrintWriter.println("mLinkProperties: " + build.linkProperties);
                        indentingPrintWriter.decreaseIndent();
                    } else {
                        indentingPrintWriter.println("UnderlyingNetworkRecord incomplete: mNetwork: " + builder.mNetwork);
                    }
                    StringBuilder m2 = AttentionManagerService$AttentionCheck$$ExternalSyntheticOutline0.m(new StringBuilder("mIsSelected: "), underlyingNetworkEvaluator.mIsSelected, indentingPrintWriter, "mPriorityClass: ");
                    m2.append(underlyingNetworkEvaluator.mPriorityClass);
                    indentingPrintWriter.println(m2.toString());
                    indentingPrintWriter.println("mIsPenalized: " + underlyingNetworkEvaluator.mIsPenalized);
                    indentingPrintWriter.decreaseIndent();
                }
            }
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.println();
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.println();
            if (vcnGatewayConnection.mIkeSession == null) {
                indentingPrintWriter.println("mIkeSession: null");
            } else {
                indentingPrintWriter.println("mIkeSession:");
                try {
                    vcnGatewayConnection.mIkeSession.mImpl.dump(indentingPrintWriter);
                } catch (Exception e) {
                    Slog.wtf("VcnGatewayConnection", "Failed to dump IkeSession: " + e);
                }
            }
            indentingPrintWriter.decreaseIndent();
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println();
        indentingPrintWriter.decreaseIndent();
    }

    public final String getLogPrefix() {
        StringBuilder sb = new StringBuilder("(");
        ParcelUuid parcelUuid = this.mSubscriptionGroup;
        sb.append(parcelUuid == null ? null : HexDump.toHexString(parcelUuid.hashCode()));
        sb.append(PackageManagerShellCommandDataLoader.STDIN_PATH);
        sb.append(System.identityHashCode(this));
        sb.append(") ");
        return sb.toString();
    }

    public final boolean getMobileDataStatus() {
        Iterator it = ((ArraySet) this.mLastSnapshot.getAllSubIdsInGroup(this.mSubscriptionGroup)).iterator();
        while (it.hasNext()) {
            if (((TelephonyManager) this.mVcnContext.mContext.getSystemService(TelephonyManager.class)).createForSubscriptionId(((Integer) it.next()).intValue()).isDataEnabled()) {
                return true;
            }
        }
        return false;
    }

    public Map getVcnGatewayConnectionConfigMap() {
        return Collections.unmodifiableMap(new HashMap(this.mVcnGatewayConnections));
    }

    public Set getVcnGatewayConnections() {
        return Collections.unmodifiableSet(new HashSet(((HashMap) this.mVcnGatewayConnections).values()));
    }

    @Override // android.os.Handler
    public final void handleMessage(Message message) {
        Set set;
        int[] intArray;
        if (this.mCurrentStatus == 2 || this.mCurrentStatus == 3) {
            int i = message.what;
            if (i == 0) {
                VcnConfig vcnConfig = (VcnConfig) message.obj;
                Slog.d("Vcn", getLogPrefix() + ("Config updated: old = " + this.mConfig.hashCode() + "; new = " + vcnConfig.hashCode()));
                this.mConfig = vcnConfig;
                for (Map.Entry entry : ((HashMap) this.mVcnGatewayConnections).entrySet()) {
                    VcnGatewayConnectionConfig vcnGatewayConnectionConfig = (VcnGatewayConnectionConfig) entry.getKey();
                    VcnGatewayConnection vcnGatewayConnection = (VcnGatewayConnection) entry.getValue();
                    if (!this.mConfig.getGatewayConnectionConfigs().contains(vcnGatewayConnectionConfig)) {
                        if (vcnGatewayConnection == null) {
                            logWtf("Found gatewayConnectionConfig without GatewayConnection");
                        } else {
                            logInfo("Config updated, restarting gateway " + vcnGatewayConnection.getLogPrefix());
                            vcnGatewayConnection.teardownAsynchronously();
                        }
                    }
                }
                this.mVcnContext.mVcnNetworkProvider.resendAllRequests(this.mRequestListener);
                return;
            }
            boolean z = true;
            if (i == 1) {
                NetworkRequest networkRequest = (NetworkRequest) message.obj;
                Objects.toString(networkRequest);
                Iterator it = ((HashMap) this.mVcnGatewayConnections).keySet().iterator();
                while (it.hasNext()) {
                    if (isRequestSatisfiedByGatewayConnectionConfig(networkRequest, (VcnGatewayConnectionConfig) it.next())) {
                        networkRequest.toString();
                        return;
                    }
                }
                for (VcnGatewayConnectionConfig vcnGatewayConnectionConfig2 : this.mConfig.getGatewayConnectionConfigs()) {
                    if (isRequestSatisfiedByGatewayConnectionConfig(networkRequest, vcnGatewayConnectionConfig2)) {
                        if (this.mIsMobileDataEnabled) {
                            set = vcnGatewayConnectionConfig2.getAllExposedCapabilities();
                        } else {
                            ArraySet arraySet = new ArraySet(vcnGatewayConnectionConfig2.getAllExposedCapabilities());
                            arraySet.removeAll(CAPS_REQUIRING_MOBILE_DATA);
                            set = arraySet;
                        }
                        if (!set.isEmpty()) {
                            if (((HashMap) this.mVcnGatewayConnections).containsKey(vcnGatewayConnectionConfig2)) {
                                logWtf("Attempted to bring up VcnGatewayConnection for config with existing VcnGatewayConnection");
                                return;
                            }
                            logInfo("Bringing up new VcnGatewayConnection for request " + networkRequest);
                            ParcelUuid parcelUuid = this.mSubscriptionGroup;
                            TelephonySubscriptionTracker.TelephonySubscriptionSnapshot telephonySubscriptionSnapshot = this.mLastSnapshot;
                            VcnGatewayStatusCallbackImpl vcnGatewayStatusCallbackImpl = new VcnGatewayStatusCallbackImpl(vcnGatewayConnectionConfig2);
                            boolean z2 = this.mIsMobileDataEnabled;
                            this.mDeps.getClass();
                            ((HashMap) this.mVcnGatewayConnections).put(vcnGatewayConnectionConfig2, new VcnGatewayConnection(this.mVcnContext, parcelUuid, telephonySubscriptionSnapshot, vcnGatewayConnectionConfig2, vcnGatewayStatusCallbackImpl, z2, new VcnGatewayConnection.Dependencies()));
                            return;
                        }
                    }
                }
                Objects.toString(networkRequest);
                return;
            }
            if (i != 2) {
                if (i == 3) {
                    VcnGatewayConnectionConfig vcnGatewayConnectionConfig3 = (VcnGatewayConnectionConfig) message.obj;
                    logInfo("VcnGatewayConnection quit: " + vcnGatewayConnectionConfig3);
                    ((HashMap) this.mVcnGatewayConnections).remove(vcnGatewayConnectionConfig3);
                    this.mVcnContext.mVcnNetworkProvider.resendAllRequests(this.mRequestListener);
                    return;
                }
                if (i == 4) {
                    Iterator it2 = ((HashMap) this.mVcnGatewayConnections).values().iterator();
                    while (true) {
                        if (!it2.hasNext()) {
                            z = false;
                            break;
                        }
                        VcnGatewayConnection vcnGatewayConnection2 = (VcnGatewayConnection) it2.next();
                        vcnGatewayConnection2.mVcnContext.ensureRunningOnLooperThread();
                        if (vcnGatewayConnection2.mIsInSafeMode) {
                            break;
                        }
                    }
                    int i2 = this.mCurrentStatus;
                    this.mCurrentStatus = z ? 3 : 2;
                    if (i2 != this.mCurrentStatus) {
                        VcnManagementService.VcnCallbackImpl vcnCallbackImpl = (VcnManagementService.VcnCallbackImpl) this.mVcnCallback;
                        synchronized (VcnManagementService.this.mLock) {
                            try {
                                if (((ArrayMap) VcnManagementService.this.mVcns).containsKey(vcnCallbackImpl.mSubGroup)) {
                                    int i3 = z ? 3 : 2;
                                    VcnManagementService.this.notifyAllPolicyListenersLocked();
                                    VcnManagementService.this.notifyAllPermissionedStatusCallbacksLocked(vcnCallbackImpl.mSubGroup, i3);
                                }
                            } finally {
                            }
                        }
                        logInfo("Safe mode ".concat(this.mCurrentStatus == 3 ? "entered" : "exited"));
                        return;
                    }
                    return;
                }
                if (i == 5) {
                    handleMobileDataToggled();
                    return;
                }
                if (i != 100) {
                    logWtf("Unknown msg.what: " + message.what);
                    return;
                }
                Slog.d("Vcn", getLogPrefix() + "Tearing down");
                this.mVcnContext.mVcnNetworkProvider.unregisterListener(this.mRequestListener);
                Iterator it3 = ((HashMap) this.mVcnGatewayConnections).values().iterator();
                while (it3.hasNext()) {
                    ((VcnGatewayConnection) it3.next()).teardownAsynchronously();
                }
                Iterator it4 = ((ArrayMap) this.mMobileDataStateListeners).values().iterator();
                while (it4.hasNext()) {
                    ((TelephonyManager) this.mVcnContext.mContext.getSystemService(TelephonyManager.class)).unregisterTelephonyCallback((VcnUserMobileDataStateListener) it4.next());
                }
                ((ArrayMap) this.mMobileDataStateListeners).clear();
                this.mCurrentStatus = 1;
                return;
            }
            this.mLastSnapshot = (TelephonySubscriptionTracker.TelephonySubscriptionSnapshot) message.obj;
            for (VcnGatewayConnection vcnGatewayConnection3 : ((HashMap) this.mVcnGatewayConnections).values()) {
                TelephonySubscriptionTracker.TelephonySubscriptionSnapshot telephonySubscriptionSnapshot2 = this.mLastSnapshot;
                vcnGatewayConnection3.getClass();
                Objects.requireNonNull(telephonySubscriptionSnapshot2, "Missing snapshot");
                vcnGatewayConnection3.mVcnContext.ensureRunningOnLooperThread();
                vcnGatewayConnection3.mLastSnapshot = telephonySubscriptionSnapshot2;
                UnderlyingNetworkController underlyingNetworkController = vcnGatewayConnection3.mUnderlyingNetworkController;
                underlyingNetworkController.getClass();
                TelephonySubscriptionTracker.TelephonySubscriptionSnapshot telephonySubscriptionSnapshot3 = underlyingNetworkController.mLastSnapshot;
                underlyingNetworkController.mLastSnapshot = telephonySubscriptionSnapshot2;
                underlyingNetworkController.mCarrierConfig = telephonySubscriptionSnapshot2.getCarrierConfigForSubGrp(underlyingNetworkController.mSubscriptionGroup);
                for (UnderlyingNetworkEvaluator underlyingNetworkEvaluator : ((ArrayMap) underlyingNetworkController.mUnderlyingNetworkRecords).values()) {
                    List<VcnUnderlyingNetworkTemplate> vcnUnderlyingNetworkPriorities = underlyingNetworkController.mConnectionConfig.getVcnUnderlyingNetworkPriorities();
                    ParcelUuid parcelUuid2 = underlyingNetworkController.mSubscriptionGroup;
                    TelephonySubscriptionTracker.TelephonySubscriptionSnapshot telephonySubscriptionSnapshot4 = underlyingNetworkController.mLastSnapshot;
                    PersistableBundleUtils.PersistableBundleWrapper persistableBundleWrapper = underlyingNetworkController.mCarrierConfig;
                    underlyingNetworkEvaluator.updatePriorityClass(vcnUnderlyingNetworkPriorities, parcelUuid2, telephonySubscriptionSnapshot4, persistableBundleWrapper);
                    int[] iArr = UnderlyingNetworkEvaluator.PENALTY_TIMEOUT_MINUTES_DEFAULT;
                    if (persistableBundleWrapper != null && (intArray = persistableBundleWrapper.mBundle.getIntArray("vcn_network_selection_penalty_timeout_minutes_list")) != null) {
                        iArr = intArray;
                    }
                    underlyingNetworkEvaluator.mPenalizedTimeoutMs = TimeUnit.MINUTES.toMillis(iArr[0]);
                    Iterator it5 = ((ArrayList) underlyingNetworkEvaluator.mMetricMonitors).iterator();
                    while (it5.hasNext()) {
                        IpSecPacketLossDetector ipSecPacketLossDetector = (IpSecPacketLossDetector) ((NetworkMetricMonitor) it5.next());
                        ipSecPacketLossDetector.getClass();
                        ipSecPacketLossDetector.mPollIpSecStateIntervalMs = TimeUnit.SECONDS.toMillis(persistableBundleWrapper != null ? persistableBundleWrapper.mBundle.getInt("vcn_network_selection_poll_ipsec_state_interval_seconds", 20) : 20);
                        if (Flags.handleSeqNumLeap()) {
                            ipSecPacketLossDetector.mPacketLossRatePercentThreshold = persistableBundleWrapper != null ? persistableBundleWrapper.mBundle.getInt("vcn_network_selection_ipsec_packet_loss_percent_threshold", 12) : 12;
                            ipSecPacketLossDetector.mMaxSeqNumIncreasePerSecond = IpSecPacketLossDetector.getMaxSeqNumIncreasePerSecond(persistableBundleWrapper);
                        }
                        if (Flags.allowDisableIpsecLossDetector()) {
                            NetworkMetricMonitor.IpSecTransformWrapper ipSecTransformWrapper = ipSecPacketLossDetector.mInboundTransform;
                            if (((ipSecTransformWrapper == null || ipSecPacketLossDetector.mPacketLossRatePercentThreshold == -1) ? false : true) != ipSecPacketLossDetector.mIsStarted) {
                                if (ipSecTransformWrapper == null || ipSecPacketLossDetector.mPacketLossRatePercentThreshold == -1) {
                                    ipSecPacketLossDetector.mIsValidationFailed = false;
                                    ipSecPacketLossDetector.mIsStarted = false;
                                    ipSecPacketLossDetector.mHandler.removeCallbacksAndEqualMessages(ipSecPacketLossDetector.mCancellationToken);
                                    ipSecPacketLossDetector.mLastIpSecTransformState = null;
                                } else {
                                    ipSecPacketLossDetector.mIsStarted = true;
                                    ipSecPacketLossDetector.mHandler.removeCallbacksAndEqualMessages(ipSecPacketLossDetector.mCancellationToken);
                                    ipSecPacketLossDetector.mLastIpSecTransformState = null;
                                    ipSecPacketLossDetector.mHandler.postDelayed(ipSecPacketLossDetector.new PollIpSecStateRunnable(), ipSecPacketLossDetector.mCancellationToken, 0L);
                                }
                            }
                        }
                    }
                }
                if (!((ArraySet) telephonySubscriptionSnapshot3.getAllSubIdsInGroup(underlyingNetworkController.mSubscriptionGroup)).equals(telephonySubscriptionSnapshot2.getAllSubIdsInGroup(underlyingNetworkController.mSubscriptionGroup))) {
                    underlyingNetworkController.registerOrUpdateNetworkRequests();
                } else if (underlyingNetworkController.mVcnContext.isFlagNetworkMetricMonitorEnabled() && VcnContext.isFlagIpSecTransformStateEnabled()) {
                    underlyingNetworkController.reevaluateNetworks();
                }
                vcnGatewayConnection3.sendMessageAndAcquireWakeLock(9, Integer.MIN_VALUE);
            }
            updateMobileDataStateListeners();
            handleMobileDataToggled();
        }
    }

    public final void handleMobileDataToggled() {
        boolean z = this.mIsMobileDataEnabled;
        boolean mobileDataStatus = getMobileDataStatus();
        this.mIsMobileDataEnabled = mobileDataStatus;
        if (z != mobileDataStatus) {
            for (Map.Entry entry : ((HashMap) this.mVcnGatewayConnections).entrySet()) {
                VcnGatewayConnectionConfig vcnGatewayConnectionConfig = (VcnGatewayConnectionConfig) entry.getKey();
                VcnGatewayConnection vcnGatewayConnection = (VcnGatewayConnection) entry.getValue();
                Set allExposedCapabilities = vcnGatewayConnectionConfig.getAllExposedCapabilities();
                if (allExposedCapabilities.contains(12) || allExposedCapabilities.contains(2)) {
                    if (vcnGatewayConnection == null) {
                        logWtf("Found gatewayConnectionConfig without GatewayConnection");
                    } else {
                        vcnGatewayConnection.teardownAsynchronously();
                    }
                }
            }
            this.mVcnContext.mVcnNetworkProvider.resendAllRequests(this.mRequestListener);
            logInfo("Mobile data ".concat(this.mIsMobileDataEnabled ? "enabled" : "disabled"));
        }
    }

    public boolean isMobileDataEnabled() {
        return this.mIsMobileDataEnabled;
    }

    public final boolean isRequestSatisfiedByGatewayConnectionConfig(NetworkRequest networkRequest, VcnGatewayConnectionConfig vcnGatewayConnectionConfig) {
        Set set;
        NetworkCapabilities.Builder builder = new NetworkCapabilities.Builder();
        builder.addTransportType(0);
        builder.addCapability(28);
        if (this.mIsMobileDataEnabled) {
            set = vcnGatewayConnectionConfig.getAllExposedCapabilities();
        } else {
            ArraySet arraySet = new ArraySet(vcnGatewayConnectionConfig.getAllExposedCapabilities());
            arraySet.removeAll(CAPS_REQUIRING_MOBILE_DATA);
            set = arraySet;
        }
        Iterator it = set.iterator();
        while (it.hasNext()) {
            builder.addCapability(((Integer) it.next()).intValue());
        }
        return networkRequest.canBeSatisfiedBy(builder.build());
    }

    public final void logInfo(String str) {
        Slog.i("Vcn", getLogPrefix() + str);
        VcnManagementService.LOCAL_LOG.log(getLogPrefix() + "INFO: " + str);
    }

    public final void logWtf(String str) {
        Slog.wtf("Vcn", getLogPrefix() + str);
        VcnManagementService.LOCAL_LOG.log(getLogPrefix() + "WTF: " + str);
    }

    public void setMobileDataEnabled(boolean z) {
        this.mIsMobileDataEnabled = z;
    }

    public void setStatus(int i) {
        this.mCurrentStatus = i;
    }

    public final void updateMobileDataStateListeners() {
        VcnContext vcnContext;
        Set allSubIdsInGroup = this.mLastSnapshot.getAllSubIdsInGroup(this.mSubscriptionGroup);
        Executor handlerExecutor = new HandlerExecutor(this);
        ArraySet arraySet = (ArraySet) allSubIdsInGroup;
        Iterator it = arraySet.iterator();
        while (true) {
            boolean hasNext = it.hasNext();
            vcnContext = this.mVcnContext;
            if (!hasNext) {
                break;
            }
            Integer num = (Integer) it.next();
            int intValue = num.intValue();
            if (!((ArrayMap) this.mMobileDataStateListeners).containsKey(num)) {
                VcnUserMobileDataStateListener vcnUserMobileDataStateListener = new VcnUserMobileDataStateListener();
                ((TelephonyManager) vcnContext.mContext.getSystemService(TelephonyManager.class)).createForSubscriptionId(intValue).registerTelephonyCallback(handlerExecutor, vcnUserMobileDataStateListener);
                ((ArrayMap) this.mMobileDataStateListeners).put(num, vcnUserMobileDataStateListener);
            }
        }
        Iterator it2 = ((ArrayMap) this.mMobileDataStateListeners).entrySet().iterator();
        while (it2.hasNext()) {
            Map.Entry entry = (Map.Entry) it2.next();
            if (!arraySet.contains(entry.getKey())) {
                ((TelephonyManager) vcnContext.mContext.getSystemService(TelephonyManager.class)).unregisterTelephonyCallback((TelephonyCallback) entry.getValue());
                it2.remove();
            }
        }
    }
}
