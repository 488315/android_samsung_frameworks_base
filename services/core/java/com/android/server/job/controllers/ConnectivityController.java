package com.android.server.job.controllers;

import android.hardware.audio.common.V2_0.AudioConfig$$ExternalSyntheticOutline0;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkPolicyManager;
import android.net.NetworkRequest;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.os.UserHandle;
import android.provider.DeviceConfig;
import android.telephony.CellSignalStrength;
import android.telephony.SignalStrength;
import android.telephony.TelephonyCallback;
import android.telephony.TelephonyManager;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.IndentingPrintWriter;
import android.util.Log;
import android.util.Pools;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.util.TimeUtils;
import android.util.proto.ProtoOutputStream;

import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.AppSchedulingModuleThread;
import com.android.server.HermesService$3$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.accessibility.ProxyManager$$ExternalSyntheticOutline0;
import com.android.server.alarm.GmsAlarmManager$$ExternalSyntheticOutline0;
import com.android.server.job.Flags;
import com.android.server.job.JobSchedulerService;
import com.android.server.job.JobSchedulerService$$ExternalSyntheticLambda5;
import com.android.server.job.JobSchedulerService$Constants$$ExternalSyntheticOutline0;
import com.android.server.job.JobServiceContext;
import com.android.server.net.NetworkPolicyManagerService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public final class ConnectivityController extends RestrictingController
        implements ConnectivityManager.OnNetworkActiveListener {
    public static final boolean DEBUG;
    static final int TRANSPORT_AFFINITY_AVOID = 2;
    static final int TRANSPORT_AFFINITY_PREFER = 1;
    static final int TRANSPORT_AFFINITY_UNDEFINED = 0;
    static final SparseIntArray sNetworkTransportAffinities;
    public final ArrayMap mAvailableNetworks;
    public final SparseBooleanArray mBackgroundMeteredAllowed;
    public final CcConfig mCcConfig;
    public final ConnectivityManager mConnManager;
    public final SparseArray mCurrentDefaultNetworkCallbacks;
    public final AnonymousClass2 mDefaultNetworkCallback;
    public final Pools.Pool mDefaultNetworkCallbackPool;
    public final FlexibilityController mFlexibilityController;
    public final CcHandler mHandler;
    public long mLastAllJobUpdateTimeElapsed;
    public long mLastCallbackAdjustmentTimeElapsed;
    public final AnonymousClass4 mNetPolicyListener;
    public final NetworkPolicyManager mNetPolicyManager;
    public final NetworkPolicyManagerService.NetworkPolicyManagerInternalImpl
            mNetPolicyManagerInternal;
    public final AnonymousClass2 mNetworkCallback;
    public final SparseArray mRequestedWhitelistJobs;
    public final SparseArray mSignalStrengths;
    public final List mSortedStats;
    public Network mSystemDefaultNetwork;
    public final SparseArray mTrackedJobs;
    public final SparseArray mUidStats;
    public final AnonymousClass1 mUidStatsComparator;

    /* renamed from: com.android.server.job.controllers.ConnectivityController$1, reason: invalid class name */
    public final class AnonymousClass1 implements Comparator {
        public static int prioritizeExistenceOver(int i, int i2, int i3) {
            if (i2 > i && i3 > i) {
                return 0;
            }
            if (i2 > i || i3 > i) {
                return i2 > i ? -1 : 1;
            }
            return 0;
        }

        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            UidStats uidStats = (UidStats) obj;
            UidStats uidStats2 = (UidStats) obj2;
            int prioritizeExistenceOver =
                    prioritizeExistenceOver(
                            0, uidStats.runningJobs.size(), uidStats2.runningJobs.size());
            if (prioritizeExistenceOver != 0) {
                return prioritizeExistenceOver;
            }
            int prioritizeExistenceOver2 =
                    prioritizeExistenceOver(
                            0,
                            uidStats.numReadyWithConnectivity,
                            uidStats2.numReadyWithConnectivity);
            if (prioritizeExistenceOver2 != 0) {
                return prioritizeExistenceOver2;
            }
            int prioritizeExistenceOver3 =
                    prioritizeExistenceOver(
                            0,
                            uidStats.numRequestedNetworkAvailable,
                            uidStats2.numRequestedNetworkAvailable);
            if (prioritizeExistenceOver3 != 0) {
                return prioritizeExistenceOver3;
            }
            int prioritizeExistenceOver4 =
                    prioritizeExistenceOver(39, uidStats.baseBias, uidStats2.baseBias);
            if (prioritizeExistenceOver4 != 0) {
                return prioritizeExistenceOver4;
            }
            int prioritizeExistenceOver5 =
                    prioritizeExistenceOver(0, uidStats.numUIJs, uidStats2.numUIJs);
            if (prioritizeExistenceOver5 != 0) {
                return prioritizeExistenceOver5;
            }
            int prioritizeExistenceOver6 =
                    prioritizeExistenceOver(0, uidStats.numEJs, uidStats2.numEJs);
            if (prioritizeExistenceOver6 != 0) {
                return prioritizeExistenceOver6;
            }
            int prioritizeExistenceOver7 =
                    prioritizeExistenceOver(34, uidStats.baseBias, uidStats2.baseBias);
            if (prioritizeExistenceOver7 != 0) {
                return prioritizeExistenceOver7;
            }
            long j = uidStats.earliestUIJEnqueueTime;
            long j2 = uidStats2.earliestUIJEnqueueTime;
            if (j >= j2) {
                if (j <= j2) {
                    long j3 = uidStats.earliestEJEnqueueTime;
                    long j4 = uidStats2.earliestEJEnqueueTime;
                    if (j3 >= j4) {
                        if (j3 <= j4) {
                            int i = uidStats.baseBias;
                            int i2 = uidStats2.baseBias;
                            if (i != i2) {
                                return i2 - i;
                            }
                            long j5 = uidStats.earliestEnqueueTime;
                            long j6 = uidStats2.earliestEnqueueTime;
                            if (j5 >= j6) {
                                return j5 > j6 ? 1 : 0;
                            }
                        }
                    }
                }
                return 1;
            }
            return -1;
        }
    }

    public final class CachedNetworkMetadata {
        public long capabilitiesFirstAcquiredTimeElapsed;
        public long defaultNetworkActivationLastCheckTimeElapsed;
        public long defaultNetworkActivationLastConfirmedTimeElapsed;
        public NetworkCapabilities networkCapabilities;
        public boolean satisfiesTransportAffinities;

        public final String toString() {
            StringBuilder sb = new StringBuilder("CNM{");
            sb.append(this.networkCapabilities.toString());
            sb.append(", satisfiesTransportAffinities=");
            sb.append(this.satisfiesTransportAffinities);
            sb.append(", capabilitiesFirstAcquiredTimeElapsed=");
            sb.append(this.capabilitiesFirstAcquiredTimeElapsed);
            sb.append(", defaultNetworkActivationLastCheckTimeElapsed=");
            sb.append(this.defaultNetworkActivationLastCheckTimeElapsed);
            sb.append(", defaultNetworkActivationLastConfirmedTimeElapsed=");
            return AudioConfig$$ExternalSyntheticOutline0.m(
                    sb, this.defaultNetworkActivationLastConfirmedTimeElapsed, "}");
        }
    }

    class CcConfig {
        static final String KEY_AVOID_UNDEFINED_TRANSPORT_AFFINITY =
                "conn_avoid_undefined_transport_affinity";
        public boolean AVOID_UNDEFINED_TRANSPORT_AFFINITY;
        public long NETWORK_ACTIVATION_EXPIRATION_MS;
        public long NETWORK_ACTIVATION_MAX_WAIT_TIME_MS;
        public boolean mFlexIsEnabled;
        public boolean mShouldReprocessNetworkCapabilities;
    }

    public final class CcHandler extends Handler {
        public CcHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            synchronized (ConnectivityController.this.mLock) {
                try {
                    int i = message.what;
                    if (i == 0) {
                        synchronized (ConnectivityController.this.mLock) {
                            ConnectivityController.m624$$Nest$mmaybeAdjustRegisteredCallbacksLocked(
                                    ConnectivityController.this);
                        }
                    } else if (i == 1) {
                        synchronized (ConnectivityController.this.mLock) {
                            ConnectivityController.this.updateAllTrackedJobsLocked(
                                    message.arg1 == 1);
                        }
                    } else if (i == 2) {
                        removeMessages(2);
                        synchronized (ConnectivityController.this.mLock) {
                            ConnectivityController.this.mBackgroundMeteredAllowed.clear();
                            ConnectivityController.this.updateTrackedJobsLocked((Network) null, -1);
                        }
                    } else if (i == 3) {
                        int i2 = message.arg1;
                        boolean z = message.arg2 != 3;
                        synchronized (ConnectivityController.this.mLock) {
                            try {
                                if (ConnectivityController.this.mBackgroundMeteredAllowed.get(i2)
                                        != z) {
                                    ConnectivityController.this.mBackgroundMeteredAllowed.put(
                                            i2, z);
                                    ConnectivityController.this.updateTrackedJobsLocked(
                                            (Network) null, i2);
                                }
                            } finally {
                            }
                        }
                    } else if (i == 4) {
                        removeMessages(4);
                        synchronized (ConnectivityController.this.mLock) {
                            try {
                                ConnectivityController connectivityController =
                                        ConnectivityController.this;
                                Network network = connectivityController.mSystemDefaultNetwork;
                                if (network != null) {
                                    if (connectivityController.isNetworkInStateForJobRunLocked(
                                            network)) {
                                        ArrayMap arrayMap = new ArrayMap();
                                        for (int size =
                                                        ConnectivityController.this.mTrackedJobs
                                                                        .size()
                                                                - 1;
                                                size >= 0;
                                                size--) {
                                            ArraySet arraySet =
                                                    (ArraySet)
                                                            ConnectivityController.this.mTrackedJobs
                                                                    .valueAt(size);
                                            for (int size2 = arraySet.size() - 1;
                                                    size2 >= 0;
                                                    size2--) {
                                                JobStatus jobStatus =
                                                        (JobStatus) arraySet.valueAt(size2);
                                                if (!ConnectivityController.this
                                                        .mSystemDefaultNetwork.equals(
                                                        jobStatus.network)) {
                                                    CachedNetworkMetadata networkMetadata =
                                                            ConnectivityController.this
                                                                    .getNetworkMetadata(
                                                                            jobStatus.network);
                                                    NetworkCapabilities networkCapabilities =
                                                            networkMetadata == null
                                                                    ? null
                                                                    : networkMetadata
                                                                            .networkCapabilities;
                                                    if (networkCapabilities != null
                                                            && networkCapabilities.hasTransport(
                                                                    4)) {
                                                        if (!arrayMap.containsKey(
                                                                jobStatus.network)) {
                                                            List underlyingNetworks =
                                                                    networkCapabilities
                                                                            .getUnderlyingNetworks();
                                                            boolean z2 =
                                                                    underlyingNetworks != null
                                                                            && underlyingNetworks
                                                                                    .contains(
                                                                                            ConnectivityController
                                                                                                    .this
                                                                                                    .mSystemDefaultNetwork);
                                                            arrayMap.put(
                                                                    jobStatus.network,
                                                                    Boolean.valueOf(z2));
                                                            if (!z2) {}
                                                        } else if (!((Boolean)
                                                                        arrayMap.get(
                                                                                jobStatus.network))
                                                                .booleanValue()) {
                                                        }
                                                    }
                                                    arrayMap.put(jobStatus.network, Boolean.FALSE);
                                                }
                                                if (jobStatus.isReady(
                                                        jobStatus
                                                                .mSatisfiedConstraintsOfInterest)) {
                                                    if (ConnectivityController.DEBUG) {
                                                        Slog.d(
                                                                "JobScheduler.Connectivity",
                                                                "Potentially running "
                                                                        + jobStatus
                                                                        + " due to network"
                                                                        + " activity");
                                                    }
                                                    ConnectivityController.this
                                                            .mStateChangedListener.onRunJobNow(
                                                            jobStatus);
                                                }
                                            }
                                        }
                                    }
                                }
                            } finally {
                            }
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final class CellSignalStrengthCallback extends TelephonyCallback
            implements TelephonyCallback.SignalStrengthsListener {
        public int signalStrength = 4;

        public CellSignalStrengthCallback() {}

        @Override // android.telephony.TelephonyCallback.SignalStrengthsListener
        public final void onSignalStrengthsChanged(SignalStrength signalStrength) {
            synchronized (ConnectivityController.this.mLock) {
                try {
                    int level = signalStrength.getLevel();
                    if (ConnectivityController.DEBUG) {
                        Slog.d(
                                "JobScheduler.Connectivity",
                                "Signal strength changing from "
                                        + this.signalStrength
                                        + " to "
                                        + level);
                        for (CellSignalStrength cellSignalStrength :
                                signalStrength.getCellSignalStrengths()) {
                            Slog.d(
                                    "JobScheduler.Connectivity",
                                    "CSS: "
                                            + cellSignalStrength.getLevel()
                                            + " "
                                            + cellSignalStrength);
                        }
                    }
                    if (this.signalStrength == level) {
                        return;
                    }
                    this.signalStrength = level;
                    ConnectivityController.this.mHandler.obtainMessage(1, 1, 0).sendToTarget();
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final class UidDefaultNetworkCallback extends ConnectivityManager.NetworkCallback {
        public static final /* synthetic */ int $r8$clinit = 0;
        public int mBlockedReasons;
        public Network mDefaultNetwork;
        public int mUid;

        public UidDefaultNetworkCallback() {}

        @Override // android.net.ConnectivityManager.NetworkCallback
        public final void onAvailable(Network network) {
            if (ConnectivityController.DEBUG) {
                Slog.v(
                        "JobScheduler.Connectivity",
                        "default-onAvailable(" + this.mUid + "): " + network);
            }
        }

        public final void onBlockedStatusChanged(Network network, int i) {
            if (ConnectivityController.DEBUG) {
                StringBuilder sb = new StringBuilder("default-onBlockedStatusChanged(");
                sb.append(this.mUid);
                sb.append("): ");
                sb.append(network);
                sb.append(" -> ");
                GmsAlarmManager$$ExternalSyntheticOutline0.m(sb, i, "JobScheduler.Connectivity");
            }
            if (this.mUid == -10000) {
                return;
            }
            synchronized (ConnectivityController.this.mLock) {
                this.mDefaultNetwork = network;
                this.mBlockedReasons = i;
                ConnectivityController.this.updateTrackedJobsLocked(network, this.mUid);
            }
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public final void onLost(Network network) {
            if (ConnectivityController.DEBUG) {
                Slog.v(
                        "JobScheduler.Connectivity",
                        "default-onLost(" + this.mUid + "): " + network);
            }
            if (this.mUid == -10000) {
                return;
            }
            synchronized (ConnectivityController.this.mLock) {
                try {
                    if (Objects.equals(this.mDefaultNetwork, network)) {
                        this.mDefaultNetwork = null;
                        ConnectivityController.this.updateTrackedJobsLocked(network, this.mUid);
                        ConnectivityController.this.mHandler.sendEmptyMessageDelayed(0, 1000L);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final class UidStats {
        public int baseBias;
        public long earliestEJEnqueueTime;
        public long earliestEnqueueTime;
        public long earliestUIJEnqueueTime;
        public long lastUpdatedElapsed;
        public int numEJs;
        public int numReadyWithConnectivity;
        public int numRegular;
        public int numRequestedNetworkAvailable;
        public int numUIJs;
        public final ArraySet runningJobs = new ArraySet();
        public final int uid;

        public UidStats(int i) {
            this.uid = i;
        }
    }

    /* renamed from: -$$Nest$mmaybeAdjustRegisteredCallbacksLocked, reason: not valid java name */
    public static void m624$$Nest$mmaybeAdjustRegisteredCallbacksLocked(
            ConnectivityController connectivityController) {
        connectivityController.mHandler.removeMessages(0);
        if (connectivityController.mUidStats.size()
                == connectivityController.mCurrentDefaultNetworkCallbacks.size()) {
            return;
        }
        JobSchedulerService.sElapsedRealtimeClock.getClass();
        long elapsedRealtime = SystemClock.elapsedRealtime();
        if (elapsedRealtime - connectivityController.mLastCallbackAdjustmentTimeElapsed < 1000) {
            connectivityController.mHandler.sendEmptyMessageDelayed(0, 1000L);
            return;
        }
        connectivityController.mLastCallbackAdjustmentTimeElapsed = elapsedRealtime;
        ((ArrayList) connectivityController.mSortedStats).clear();
        for (int i = 0; i < connectivityController.mUidStats.size(); i++) {
            UidStats uidStats = (UidStats) connectivityController.mUidStats.valueAt(i);
            ArraySet arraySet = (ArraySet) connectivityController.mTrackedJobs.get(uidStats.uid);
            if (arraySet == null || arraySet.size() == 0) {
                connectivityController.unregisterDefaultNetworkCallbackLocked(
                        uidStats.uid, elapsedRealtime);
            } else {
                if (uidStats.lastUpdatedElapsed + 30000 < elapsedRealtime) {
                    uidStats.earliestEnqueueTime = Long.MAX_VALUE;
                    uidStats.earliestEJEnqueueTime = Long.MAX_VALUE;
                    uidStats.earliestUIJEnqueueTime = Long.MAX_VALUE;
                    uidStats.numReadyWithConnectivity = 0;
                    uidStats.numRequestedNetworkAvailable = 0;
                    uidStats.numRegular = 0;
                    uidStats.numEJs = 0;
                    uidStats.numUIJs = 0;
                    for (int i2 = 0; i2 < arraySet.size(); i2++) {
                        JobStatus jobStatus = (JobStatus) arraySet.valueAt(i2);
                        if (connectivityController.wouldBeReadyWithConstraintLocked(
                                jobStatus, 268435456)) {
                            uidStats.numReadyWithConnectivity++;
                            if (connectivityController.isNetworkAvailable(jobStatus)) {
                                uidStats.numRequestedNetworkAvailable++;
                            }
                            uidStats.earliestEnqueueTime =
                                    Math.min(uidStats.earliestEnqueueTime, jobStatus.enqueueTime);
                            if (jobStatus.shouldTreatAsExpeditedJob()
                                    || jobStatus.startedAsExpeditedJob) {
                                uidStats.earliestEJEnqueueTime =
                                        Math.min(
                                                uidStats.earliestEJEnqueueTime,
                                                jobStatus.enqueueTime);
                            } else if (jobStatus.shouldTreatAsUserInitiatedJob()) {
                                uidStats.earliestUIJEnqueueTime =
                                        Math.min(
                                                uidStats.earliestUIJEnqueueTime,
                                                jobStatus.enqueueTime);
                            }
                        }
                        if (jobStatus.shouldTreatAsExpeditedJob()
                                || jobStatus.startedAsExpeditedJob) {
                            uidStats.numEJs++;
                        } else if (jobStatus.shouldTreatAsUserInitiatedJob()) {
                            uidStats.numUIJs++;
                        } else {
                            uidStats.numRegular++;
                        }
                    }
                    uidStats.lastUpdatedElapsed = elapsedRealtime;
                }
                ((ArrayList) connectivityController.mSortedStats).add(uidStats);
            }
        }
        ((ArrayList) connectivityController.mSortedStats)
                .sort(connectivityController.mUidStatsComparator);
        ArraySet arraySet2 = new ArraySet();
        for (int size = ((ArrayList) connectivityController.mSortedStats).size() - 1;
                size >= 0;
                size--) {
            UidStats uidStats2 =
                    (UidStats) ((ArrayList) connectivityController.mSortedStats).get(size);
            if (size >= 125) {
                if (connectivityController.unregisterDefaultNetworkCallbackLocked(
                        uidStats2.uid, elapsedRealtime)) {
                    arraySet2.addAll(
                            (ArraySet) connectivityController.mTrackedJobs.get(uidStats2.uid));
                }
            } else if (((UidDefaultNetworkCallback)
                            connectivityController.mCurrentDefaultNetworkCallbacks.get(
                                    uidStats2.uid))
                    == null) {
                UidDefaultNetworkCallback uidDefaultNetworkCallback =
                        (UidDefaultNetworkCallback)
                                connectivityController.mDefaultNetworkCallbackPool.acquire();
                if (uidDefaultNetworkCallback == null) {
                    uidDefaultNetworkCallback =
                            connectivityController.new UidDefaultNetworkCallback();
                }
                int i3 = uidStats2.uid;
                uidDefaultNetworkCallback.mUid = i3;
                uidDefaultNetworkCallback.mDefaultNetwork = null;
                connectivityController.mCurrentDefaultNetworkCallbacks.append(
                        i3, uidDefaultNetworkCallback);
                connectivityController.mConnManager.registerDefaultNetworkCallbackForUid(
                        i3, uidDefaultNetworkCallback, connectivityController.mHandler);
            }
        }
        if (arraySet2.size() > 0) {
            connectivityController.mStateChangedListener.onControllerStateChanged(arraySet2);
        }
    }

    static {
        DEBUG = JobSchedulerService.DEBUG || Log.isLoggable("JobScheduler.Connectivity", 3);
        SparseIntArray sparseIntArray = new SparseIntArray();
        sNetworkTransportAffinities = sparseIntArray;
        sparseIntArray.put(0, 2);
        sparseIntArray.put(3, 1);
        sparseIntArray.put(10, 2);
        sparseIntArray.put(1, 1);
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.server.job.controllers.ConnectivityController$2] */
    public ConnectivityController(
            JobSchedulerService jobSchedulerService, FlexibilityController flexibilityController) {
        super(jobSchedulerService);
        this.mTrackedJobs = new SparseArray();
        this.mRequestedWhitelistJobs = new SparseArray();
        this.mAvailableNetworks = new ArrayMap();
        this.mCurrentDefaultNetworkCallbacks = new SparseArray();
        this.mUidStatsComparator = new AnonymousClass1();
        this.mUidStats = new SparseArray();
        this.mDefaultNetworkCallbackPool = new Pools.SimplePool(125);
        this.mSortedStats = new ArrayList();
        this.mBackgroundMeteredAllowed = new SparseBooleanArray();
        this.mSignalStrengths = new SparseArray();
        final int i = 0;
        ConnectivityManager.NetworkCallback networkCallback =
                new ConnectivityManager.NetworkCallback(
                        this) { // from class:
                                // com.android.server.job.controllers.ConnectivityController.2
                    public final /* synthetic */ ConnectivityController this$0;

                    {
                        this.this$0 = this;
                    }

                    public void maybeRegisterSignalStrengthCallbackLocked(
                            NetworkCapabilities networkCapabilities) {
                        if (networkCapabilities.hasTransport(0)) {
                            TelephonyManager telephonyManager =
                                    (TelephonyManager)
                                            this.this$0.mContext.getSystemService(
                                                    TelephonyManager.class);
                            Iterator it = networkCapabilities.getSubscriptionIds().iterator();
                            while (it.hasNext()) {
                                int intValue = ((Integer) it.next()).intValue();
                                if (this.this$0.mSignalStrengths.indexOfKey(intValue) < 0) {
                                    TelephonyManager createForSubscriptionId =
                                            telephonyManager.createForSubscriptionId(intValue);
                                    CellSignalStrengthCallback cellSignalStrengthCallback =
                                            this.this$0.new CellSignalStrengthCallback();
                                    createForSubscriptionId.registerTelephonyCallback(
                                            AppSchedulingModuleThread.getExecutor(),
                                            cellSignalStrengthCallback);
                                    this.this$0.mSignalStrengths.put(
                                            intValue, cellSignalStrengthCallback);
                                    SignalStrength signalStrength =
                                            createForSubscriptionId.getSignalStrength();
                                    if (signalStrength != null) {
                                        cellSignalStrengthCallback.signalStrength =
                                                signalStrength.getLevel();
                                    }
                                }
                            }
                        }
                    }

                    public void maybeUnregisterSignalStrengthCallbackLocked(
                            NetworkCapabilities networkCapabilities) {
                        NetworkCapabilities networkCapabilities2;
                        if (networkCapabilities.hasTransport(0)) {
                            ArraySet arraySet = new ArraySet();
                            int size = this.this$0.mAvailableNetworks.size();
                            for (int i2 = 0; i2 < size; i2++) {
                                CachedNetworkMetadata cachedNetworkMetadata =
                                        (CachedNetworkMetadata)
                                                this.this$0.mAvailableNetworks.valueAt(i2);
                                if (cachedNetworkMetadata != null
                                        && (networkCapabilities2 =
                                                        cachedNetworkMetadata.networkCapabilities)
                                                != null
                                        && networkCapabilities2.hasTransport(0)) {
                                    arraySet.addAll(
                                            cachedNetworkMetadata.networkCapabilities
                                                    .getSubscriptionIds());
                                }
                            }
                            if (ConnectivityController.DEBUG) {
                                Slog.d(
                                        "JobScheduler.Connectivity",
                                        "Active subscription IDs: " + arraySet);
                            }
                            TelephonyManager telephonyManager =
                                    (TelephonyManager)
                                            this.this$0.mContext.getSystemService(
                                                    TelephonyManager.class);
                            for (Integer num : networkCapabilities.getSubscriptionIds()) {
                                int intValue = num.intValue();
                                if (!arraySet.contains(num)) {
                                    TelephonyManager createForSubscriptionId =
                                            telephonyManager.createForSubscriptionId(intValue);
                                    CellSignalStrengthCallback cellSignalStrengthCallback =
                                            (CellSignalStrengthCallback)
                                                    this.this$0.mSignalStrengths.removeReturnOld(
                                                            intValue);
                                    if (cellSignalStrengthCallback != null) {
                                        createForSubscriptionId.unregisterTelephonyCallback(
                                                cellSignalStrengthCallback);
                                    } else {
                                        Slog.wtf(
                                                "JobScheduler.Connectivity",
                                                "Callback for sub "
                                                        + intValue
                                                        + " didn't exist?!?!");
                                    }
                                }
                            }
                        }
                    }

                    public void maybeUpdateFlexConstraintLocked(
                            CachedNetworkMetadata cachedNetworkMetadata) {
                        if (cachedNetworkMetadata != null
                                && cachedNetworkMetadata.satisfiesTransportAffinities) {
                            FlexibilityController flexibilityController2 =
                                    this.this$0.mFlexibilityController;
                            JobSchedulerService.sElapsedRealtimeClock.getClass();
                            flexibilityController2.setConstraintSatisfied(
                                    268435456, SystemClock.elapsedRealtime(), true);
                            return;
                        }
                        for (int size = this.this$0.mAvailableNetworks.size() - 1;
                                size >= 0;
                                size--) {
                            CachedNetworkMetadata cachedNetworkMetadata2 =
                                    (CachedNetworkMetadata)
                                            this.this$0.mAvailableNetworks.valueAt(size);
                            if (cachedNetworkMetadata2 != null
                                    && cachedNetworkMetadata2.satisfiesTransportAffinities) {
                                return;
                            }
                        }
                        FlexibilityController flexibilityController3 =
                                this.this$0.mFlexibilityController;
                        JobSchedulerService.sElapsedRealtimeClock.getClass();
                        flexibilityController3.setConstraintSatisfied(
                                268435456, SystemClock.elapsedRealtime(), false);
                    }

                    @Override // android.net.ConnectivityManager.NetworkCallback
                    public final void onAvailable(Network network) {
                        switch (i) {
                            case 0:
                                if (ConnectivityController.DEBUG) {
                                    Slog.v("JobScheduler.Connectivity", "onAvailable: " + network);
                                    return;
                                }
                                return;
                            default:
                                if (ConnectivityController.DEBUG) {
                                    Slog.v(
                                            "JobScheduler.Connectivity",
                                            "systemDefault-onAvailable: " + network);
                                }
                                synchronized (this.this$0.mLock) {
                                    this.this$0.mSystemDefaultNetwork = network;
                                }
                                return;
                        }
                    }

                    @Override // android.net.ConnectivityManager.NetworkCallback
                    public void onCapabilitiesChanged(
                            Network network, NetworkCapabilities networkCapabilities) {
                        switch (i) {
                            case 0:
                                if (ConnectivityController.DEBUG) {
                                    Slog.v(
                                            "JobScheduler.Connectivity",
                                            "onCapabilitiesChanged: " + network);
                                }
                                synchronized (this.this$0.mLock) {
                                    try {
                                        CachedNetworkMetadata cachedNetworkMetadata =
                                                (CachedNetworkMetadata)
                                                        this.this$0.mAvailableNetworks.get(network);
                                        if (cachedNetworkMetadata == null) {
                                            cachedNetworkMetadata = new CachedNetworkMetadata();
                                            JobSchedulerService.sElapsedRealtimeClock.getClass();
                                            cachedNetworkMetadata
                                                            .capabilitiesFirstAcquiredTimeElapsed =
                                                    SystemClock.elapsedRealtime();
                                            this.this$0.mAvailableNetworks.put(
                                                    network, cachedNetworkMetadata);
                                        } else {
                                            NetworkCapabilities networkCapabilities2 =
                                                    cachedNetworkMetadata.networkCapabilities;
                                            if (networkCapabilities2 != null) {
                                                maybeUnregisterSignalStrengthCallbackLocked(
                                                        networkCapabilities2);
                                            }
                                        }
                                        cachedNetworkMetadata.networkCapabilities =
                                                networkCapabilities;
                                        if (this.this$0.updateTransportAffinitySatisfaction(
                                                cachedNetworkMetadata)) {
                                            maybeUpdateFlexConstraintLocked(cachedNetworkMetadata);
                                        }
                                        maybeRegisterSignalStrengthCallbackLocked(
                                                networkCapabilities);
                                        this.this$0.updateTrackedJobsLocked(network, -1);
                                        this.this$0.postAdjustCallbacks();
                                    } catch (Throwable th) {
                                        throw th;
                                    }
                                }
                                return;
                            default:
                                super.onCapabilitiesChanged(network, networkCapabilities);
                                return;
                        }
                    }

                    @Override // android.net.ConnectivityManager.NetworkCallback
                    public final void onLost(Network network) {
                        switch (i) {
                            case 0:
                                if (ConnectivityController.DEBUG) {
                                    Slog.v("JobScheduler.Connectivity", "onLost: " + network);
                                }
                                synchronized (this.this$0.mLock) {
                                    try {
                                        CachedNetworkMetadata cachedNetworkMetadata =
                                                (CachedNetworkMetadata)
                                                        this.this$0.mAvailableNetworks.remove(
                                                                network);
                                        if (cachedNetworkMetadata != null) {
                                            NetworkCapabilities networkCapabilities =
                                                    cachedNetworkMetadata.networkCapabilities;
                                            if (networkCapabilities != null) {
                                                maybeUnregisterSignalStrengthCallbackLocked(
                                                        networkCapabilities);
                                            }
                                            if (cachedNetworkMetadata
                                                    .satisfiesTransportAffinities) {
                                                maybeUpdateFlexConstraintLocked(null);
                                            }
                                        }
                                        for (int i2 = 0;
                                                i2
                                                        < this.this$0
                                                                .mCurrentDefaultNetworkCallbacks
                                                                .size();
                                                i2++) {
                                            UidDefaultNetworkCallback uidDefaultNetworkCallback =
                                                    (UidDefaultNetworkCallback)
                                                            this.this$0
                                                                    .mCurrentDefaultNetworkCallbacks
                                                                    .valueAt(i2);
                                            if (Objects.equals(
                                                    uidDefaultNetworkCallback.mDefaultNetwork,
                                                    network)) {
                                                uidDefaultNetworkCallback.mDefaultNetwork = null;
                                            }
                                        }
                                        this.this$0.updateTrackedJobsLocked(network, -1);
                                        this.this$0.postAdjustCallbacks();
                                    } finally {
                                    }
                                }
                                return;
                            default:
                                if (ConnectivityController.DEBUG) {
                                    Slog.v(
                                            "JobScheduler.Connectivity",
                                            "systemDefault-onLost: " + network);
                                }
                                synchronized (this.this$0.mLock) {
                                    try {
                                        if (network.equals(this.this$0.mSystemDefaultNetwork)) {
                                            this.this$0.mSystemDefaultNetwork = null;
                                        }
                                    } finally {
                                    }
                                }
                                return;
                        }
                    }
                };
        final int i2 = 1;
        this.mDefaultNetworkCallback =
                new ConnectivityManager.NetworkCallback(
                        this) { // from class:
                                // com.android.server.job.controllers.ConnectivityController.2
                    public final /* synthetic */ ConnectivityController this$0;

                    {
                        this.this$0 = this;
                    }

                    public void maybeRegisterSignalStrengthCallbackLocked(
                            NetworkCapabilities networkCapabilities) {
                        if (networkCapabilities.hasTransport(0)) {
                            TelephonyManager telephonyManager =
                                    (TelephonyManager)
                                            this.this$0.mContext.getSystemService(
                                                    TelephonyManager.class);
                            Iterator it = networkCapabilities.getSubscriptionIds().iterator();
                            while (it.hasNext()) {
                                int intValue = ((Integer) it.next()).intValue();
                                if (this.this$0.mSignalStrengths.indexOfKey(intValue) < 0) {
                                    TelephonyManager createForSubscriptionId =
                                            telephonyManager.createForSubscriptionId(intValue);
                                    CellSignalStrengthCallback cellSignalStrengthCallback =
                                            this.this$0.new CellSignalStrengthCallback();
                                    createForSubscriptionId.registerTelephonyCallback(
                                            AppSchedulingModuleThread.getExecutor(),
                                            cellSignalStrengthCallback);
                                    this.this$0.mSignalStrengths.put(
                                            intValue, cellSignalStrengthCallback);
                                    SignalStrength signalStrength =
                                            createForSubscriptionId.getSignalStrength();
                                    if (signalStrength != null) {
                                        cellSignalStrengthCallback.signalStrength =
                                                signalStrength.getLevel();
                                    }
                                }
                            }
                        }
                    }

                    public void maybeUnregisterSignalStrengthCallbackLocked(
                            NetworkCapabilities networkCapabilities) {
                        NetworkCapabilities networkCapabilities2;
                        if (networkCapabilities.hasTransport(0)) {
                            ArraySet arraySet = new ArraySet();
                            int size = this.this$0.mAvailableNetworks.size();
                            for (int i22 = 0; i22 < size; i22++) {
                                CachedNetworkMetadata cachedNetworkMetadata =
                                        (CachedNetworkMetadata)
                                                this.this$0.mAvailableNetworks.valueAt(i22);
                                if (cachedNetworkMetadata != null
                                        && (networkCapabilities2 =
                                                        cachedNetworkMetadata.networkCapabilities)
                                                != null
                                        && networkCapabilities2.hasTransport(0)) {
                                    arraySet.addAll(
                                            cachedNetworkMetadata.networkCapabilities
                                                    .getSubscriptionIds());
                                }
                            }
                            if (ConnectivityController.DEBUG) {
                                Slog.d(
                                        "JobScheduler.Connectivity",
                                        "Active subscription IDs: " + arraySet);
                            }
                            TelephonyManager telephonyManager =
                                    (TelephonyManager)
                                            this.this$0.mContext.getSystemService(
                                                    TelephonyManager.class);
                            for (Integer num : networkCapabilities.getSubscriptionIds()) {
                                int intValue = num.intValue();
                                if (!arraySet.contains(num)) {
                                    TelephonyManager createForSubscriptionId =
                                            telephonyManager.createForSubscriptionId(intValue);
                                    CellSignalStrengthCallback cellSignalStrengthCallback =
                                            (CellSignalStrengthCallback)
                                                    this.this$0.mSignalStrengths.removeReturnOld(
                                                            intValue);
                                    if (cellSignalStrengthCallback != null) {
                                        createForSubscriptionId.unregisterTelephonyCallback(
                                                cellSignalStrengthCallback);
                                    } else {
                                        Slog.wtf(
                                                "JobScheduler.Connectivity",
                                                "Callback for sub "
                                                        + intValue
                                                        + " didn't exist?!?!");
                                    }
                                }
                            }
                        }
                    }

                    public void maybeUpdateFlexConstraintLocked(
                            CachedNetworkMetadata cachedNetworkMetadata) {
                        if (cachedNetworkMetadata != null
                                && cachedNetworkMetadata.satisfiesTransportAffinities) {
                            FlexibilityController flexibilityController2 =
                                    this.this$0.mFlexibilityController;
                            JobSchedulerService.sElapsedRealtimeClock.getClass();
                            flexibilityController2.setConstraintSatisfied(
                                    268435456, SystemClock.elapsedRealtime(), true);
                            return;
                        }
                        for (int size = this.this$0.mAvailableNetworks.size() - 1;
                                size >= 0;
                                size--) {
                            CachedNetworkMetadata cachedNetworkMetadata2 =
                                    (CachedNetworkMetadata)
                                            this.this$0.mAvailableNetworks.valueAt(size);
                            if (cachedNetworkMetadata2 != null
                                    && cachedNetworkMetadata2.satisfiesTransportAffinities) {
                                return;
                            }
                        }
                        FlexibilityController flexibilityController3 =
                                this.this$0.mFlexibilityController;
                        JobSchedulerService.sElapsedRealtimeClock.getClass();
                        flexibilityController3.setConstraintSatisfied(
                                268435456, SystemClock.elapsedRealtime(), false);
                    }

                    @Override // android.net.ConnectivityManager.NetworkCallback
                    public final void onAvailable(Network network) {
                        switch (i2) {
                            case 0:
                                if (ConnectivityController.DEBUG) {
                                    Slog.v("JobScheduler.Connectivity", "onAvailable: " + network);
                                    return;
                                }
                                return;
                            default:
                                if (ConnectivityController.DEBUG) {
                                    Slog.v(
                                            "JobScheduler.Connectivity",
                                            "systemDefault-onAvailable: " + network);
                                }
                                synchronized (this.this$0.mLock) {
                                    this.this$0.mSystemDefaultNetwork = network;
                                }
                                return;
                        }
                    }

                    @Override // android.net.ConnectivityManager.NetworkCallback
                    public void onCapabilitiesChanged(
                            Network network, NetworkCapabilities networkCapabilities) {
                        switch (i2) {
                            case 0:
                                if (ConnectivityController.DEBUG) {
                                    Slog.v(
                                            "JobScheduler.Connectivity",
                                            "onCapabilitiesChanged: " + network);
                                }
                                synchronized (this.this$0.mLock) {
                                    try {
                                        CachedNetworkMetadata cachedNetworkMetadata =
                                                (CachedNetworkMetadata)
                                                        this.this$0.mAvailableNetworks.get(network);
                                        if (cachedNetworkMetadata == null) {
                                            cachedNetworkMetadata = new CachedNetworkMetadata();
                                            JobSchedulerService.sElapsedRealtimeClock.getClass();
                                            cachedNetworkMetadata
                                                            .capabilitiesFirstAcquiredTimeElapsed =
                                                    SystemClock.elapsedRealtime();
                                            this.this$0.mAvailableNetworks.put(
                                                    network, cachedNetworkMetadata);
                                        } else {
                                            NetworkCapabilities networkCapabilities2 =
                                                    cachedNetworkMetadata.networkCapabilities;
                                            if (networkCapabilities2 != null) {
                                                maybeUnregisterSignalStrengthCallbackLocked(
                                                        networkCapabilities2);
                                            }
                                        }
                                        cachedNetworkMetadata.networkCapabilities =
                                                networkCapabilities;
                                        if (this.this$0.updateTransportAffinitySatisfaction(
                                                cachedNetworkMetadata)) {
                                            maybeUpdateFlexConstraintLocked(cachedNetworkMetadata);
                                        }
                                        maybeRegisterSignalStrengthCallbackLocked(
                                                networkCapabilities);
                                        this.this$0.updateTrackedJobsLocked(network, -1);
                                        this.this$0.postAdjustCallbacks();
                                    } catch (Throwable th) {
                                        throw th;
                                    }
                                }
                                return;
                            default:
                                super.onCapabilitiesChanged(network, networkCapabilities);
                                return;
                        }
                    }

                    @Override // android.net.ConnectivityManager.NetworkCallback
                    public final void onLost(Network network) {
                        switch (i2) {
                            case 0:
                                if (ConnectivityController.DEBUG) {
                                    Slog.v("JobScheduler.Connectivity", "onLost: " + network);
                                }
                                synchronized (this.this$0.mLock) {
                                    try {
                                        CachedNetworkMetadata cachedNetworkMetadata =
                                                (CachedNetworkMetadata)
                                                        this.this$0.mAvailableNetworks.remove(
                                                                network);
                                        if (cachedNetworkMetadata != null) {
                                            NetworkCapabilities networkCapabilities =
                                                    cachedNetworkMetadata.networkCapabilities;
                                            if (networkCapabilities != null) {
                                                maybeUnregisterSignalStrengthCallbackLocked(
                                                        networkCapabilities);
                                            }
                                            if (cachedNetworkMetadata
                                                    .satisfiesTransportAffinities) {
                                                maybeUpdateFlexConstraintLocked(null);
                                            }
                                        }
                                        for (int i22 = 0;
                                                i22
                                                        < this.this$0
                                                                .mCurrentDefaultNetworkCallbacks
                                                                .size();
                                                i22++) {
                                            UidDefaultNetworkCallback uidDefaultNetworkCallback =
                                                    (UidDefaultNetworkCallback)
                                                            this.this$0
                                                                    .mCurrentDefaultNetworkCallbacks
                                                                    .valueAt(i22);
                                            if (Objects.equals(
                                                    uidDefaultNetworkCallback.mDefaultNetwork,
                                                    network)) {
                                                uidDefaultNetworkCallback.mDefaultNetwork = null;
                                            }
                                        }
                                        this.this$0.updateTrackedJobsLocked(network, -1);
                                        this.this$0.postAdjustCallbacks();
                                    } finally {
                                    }
                                }
                                return;
                            default:
                                if (ConnectivityController.DEBUG) {
                                    Slog.v(
                                            "JobScheduler.Connectivity",
                                            "systemDefault-onLost: " + network);
                                }
                                synchronized (this.this$0.mLock) {
                                    try {
                                        if (network.equals(this.this$0.mSystemDefaultNetwork)) {
                                            this.this$0.mSystemDefaultNetwork = null;
                                        }
                                    } finally {
                                    }
                                }
                                return;
                        }
                    }
                };
        NetworkPolicyManager.Listener listener =
                new NetworkPolicyManager
                        .Listener() { // from class:
                                      // com.android.server.job.controllers.ConnectivityController.4
                    public final void onRestrictBackgroundChanged(boolean z) {
                        if (ConnectivityController.DEBUG) {
                            Slog.v(
                                    "JobScheduler.Connectivity",
                                    "onRestrictBackgroundChanged: " + z);
                        }
                        ConnectivityController.this.mHandler.obtainMessage(2).sendToTarget();
                    }

                    public final void onUidPoliciesChanged(int i3, int i4) {
                        if (ConnectivityController.DEBUG) {
                            ProxyManager$$ExternalSyntheticOutline0.m(
                                    i3, "onUidPoliciesChanged: ", "JobScheduler.Connectivity");
                        }
                        ConnectivityController connectivityController = ConnectivityController.this;
                        connectivityController
                                .mHandler
                                .obtainMessage(
                                        3,
                                        i3,
                                        connectivityController.mNetPolicyManager
                                                .getRestrictBackgroundStatus(i3))
                                .sendToTarget();
                    }
                };
        this.mHandler = new CcHandler(AppSchedulingModuleThread.get().getLooper());
        CcConfig ccConfig = new CcConfig();
        ccConfig.mFlexIsEnabled = false;
        ccConfig.mShouldReprocessNetworkCapabilities = false;
        ccConfig.AVOID_UNDEFINED_TRANSPORT_AFFINITY = false;
        ccConfig.NETWORK_ACTIVATION_EXPIRATION_MS = 10000L;
        ccConfig.NETWORK_ACTIVATION_MAX_WAIT_TIME_MS = 1860000L;
        this.mCcConfig = ccConfig;
        ConnectivityManager connectivityManager =
                (ConnectivityManager) this.mContext.getSystemService(ConnectivityManager.class);
        this.mConnManager = connectivityManager;
        NetworkPolicyManager networkPolicyManager =
                (NetworkPolicyManager) this.mContext.getSystemService(NetworkPolicyManager.class);
        this.mNetPolicyManager = networkPolicyManager;
        this.mNetPolicyManagerInternal =
                (NetworkPolicyManagerService.NetworkPolicyManagerInternalImpl)
                        LocalServices.getService(
                                NetworkPolicyManagerService.NetworkPolicyManagerInternalImpl.class);
        this.mFlexibilityController = flexibilityController;
        connectivityManager.registerNetworkCallback(
                new NetworkRequest.Builder().clearCapabilities().build(), networkCallback);
        networkPolicyManager.registerListener(listener);
        if (this.mContext.getPackageManager().hasSystemFeature("android.hardware.type.watch")) {
            sNetworkTransportAffinities.clear();
        }
    }

    public static long calculateTransferTimeMs(long j, long j2) {
        if (j == -1 || j2 <= 0) {
            return -1L;
        }
        return (j * 1000) / ((j2 * 1000) / 8);
    }

    public static NetworkCapabilities.Builder copyCapabilities(NetworkRequest networkRequest) {
        NetworkCapabilities.Builder builder = new NetworkCapabilities.Builder();
        for (int i : networkRequest.getTransportTypes()) {
            builder.addTransportType(i);
        }
        for (int i2 : networkRequest.getCapabilities()) {
            builder.addCapability(i2);
        }
        return builder;
    }

    @Override // com.android.server.job.controllers.StateController
    public final void dumpConstants(IndentingPrintWriter indentingPrintWriter) {
        CcConfig ccConfig = this.mCcConfig;
        ccConfig.getClass();
        indentingPrintWriter.println();
        indentingPrintWriter.print("ConnectivityController");
        indentingPrintWriter.println(":");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter
                .print(
                        "conn_avoid_undefined_transport_affinity",
                        Boolean.valueOf(ccConfig.AVOID_UNDEFINED_TRANSPORT_AFFINITY))
                .println();
        JobSchedulerService$Constants$$ExternalSyntheticOutline0.m(
                ccConfig.NETWORK_ACTIVATION_EXPIRATION_MS,
                indentingPrintWriter,
                "conn_network_activation_expiration_ms");
        indentingPrintWriter
                .print(
                        "conn_network_activation_max_wait_time_ms",
                        Long.valueOf(ccConfig.NETWORK_ACTIVATION_MAX_WAIT_TIME_MS))
                .println();
        indentingPrintWriter.decreaseIndent();
    }

    @Override // com.android.server.job.controllers.StateController
    public final void dumpControllerStateLocked(
            IndentingPrintWriter indentingPrintWriter,
            JobSchedulerService$$ExternalSyntheticLambda5
                    jobSchedulerService$$ExternalSyntheticLambda5) {
        JobSchedulerService.sElapsedRealtimeClock.getClass();
        long elapsedRealtime = SystemClock.elapsedRealtime();
        indentingPrintWriter.println("Aconfig flags:");
        indentingPrintWriter.increaseIndent();
        Flags.relaxPrefetchConnectivityConstraintOnlyOnCharger();
        indentingPrintWriter.print(
                "com.android.server.job.relax_prefetch_connectivity_constraint_only_on_charger",
                Boolean.TRUE);
        indentingPrintWriter.println();
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println();
        if (this.mRequestedWhitelistJobs.size() > 0) {
            indentingPrintWriter.print("Requested standby exceptions:");
            for (int i = 0; i < this.mRequestedWhitelistJobs.size(); i++) {
                indentingPrintWriter.print(" ");
                indentingPrintWriter.print(this.mRequestedWhitelistJobs.keyAt(i));
                indentingPrintWriter.print(" (");
                indentingPrintWriter.print(
                        ((ArraySet) this.mRequestedWhitelistJobs.valueAt(i)).size());
                indentingPrintWriter.print(" jobs)");
            }
            indentingPrintWriter.println();
        }
        if (this.mAvailableNetworks.size() > 0) {
            indentingPrintWriter.println("Available networks:");
            indentingPrintWriter.increaseIndent();
            for (int i2 = 0; i2 < this.mAvailableNetworks.size(); i2++) {
                indentingPrintWriter.print(this.mAvailableNetworks.keyAt(i2));
                indentingPrintWriter.print(": ");
                indentingPrintWriter.println(this.mAvailableNetworks.valueAt(i2));
            }
            indentingPrintWriter.decreaseIndent();
        } else {
            indentingPrintWriter.println("No available networks");
        }
        indentingPrintWriter.println();
        if (this.mSignalStrengths.size() > 0) {
            indentingPrintWriter.println("Subscription ID signal strengths:");
            indentingPrintWriter.increaseIndent();
            for (int i3 = 0; i3 < this.mSignalStrengths.size(); i3++) {
                indentingPrintWriter.print(this.mSignalStrengths.keyAt(i3));
                indentingPrintWriter.print(": ");
                indentingPrintWriter.println(
                        ((CellSignalStrengthCallback) this.mSignalStrengths.valueAt(i3))
                                .signalStrength);
            }
            indentingPrintWriter.decreaseIndent();
        } else {
            indentingPrintWriter.println("No cached signal strengths");
        }
        indentingPrintWriter.println();
        if (this.mBackgroundMeteredAllowed.size() > 0) {
            indentingPrintWriter.print("Background metered allowed: ");
            indentingPrintWriter.println(this.mBackgroundMeteredAllowed);
            indentingPrintWriter.println();
        }
        indentingPrintWriter.println("Current default network callbacks:");
        indentingPrintWriter.increaseIndent();
        for (int i4 = 0; i4 < this.mCurrentDefaultNetworkCallbacks.size(); i4++) {
            UidDefaultNetworkCallback uidDefaultNetworkCallback =
                    (UidDefaultNetworkCallback) this.mCurrentDefaultNetworkCallbacks.valueAt(i4);
            int i5 = UidDefaultNetworkCallback.$r8$clinit;
            uidDefaultNetworkCallback.getClass();
            indentingPrintWriter.print("UID: ");
            indentingPrintWriter.print(uidDefaultNetworkCallback.mUid);
            indentingPrintWriter.print("; ");
            if (uidDefaultNetworkCallback.mDefaultNetwork == null) {
                indentingPrintWriter.print("No network");
            } else {
                indentingPrintWriter.print("Network: ");
                indentingPrintWriter.print(uidDefaultNetworkCallback.mDefaultNetwork);
                indentingPrintWriter.print(" (blocked=");
                indentingPrintWriter.print(
                        NetworkPolicyManager.blockedReasonsToString(
                                uidDefaultNetworkCallback.mBlockedReasons));
                indentingPrintWriter.print(")");
            }
            indentingPrintWriter.println();
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println();
        indentingPrintWriter.println("UID Pecking Order:");
        indentingPrintWriter.increaseIndent();
        for (int i6 = 0; i6 < ((ArrayList) this.mSortedStats).size(); i6++) {
            indentingPrintWriter.print(i6);
            indentingPrintWriter.print(": ");
            UidStats uidStats = (UidStats) ((ArrayList) this.mSortedStats).get(i6);
            uidStats.getClass();
            indentingPrintWriter.print("UidStats{");
            indentingPrintWriter.print("uid", Integer.valueOf(uidStats.uid));
            indentingPrintWriter.print("pri", Integer.valueOf(uidStats.baseBias));
            indentingPrintWriter.print("#run", Integer.valueOf(uidStats.runningJobs.size()));
            indentingPrintWriter.print(
                    "#readyWithConn", Integer.valueOf(uidStats.numReadyWithConnectivity));
            indentingPrintWriter.print(
                    "#netAvail", Integer.valueOf(uidStats.numRequestedNetworkAvailable));
            indentingPrintWriter.print("#EJs", Integer.valueOf(uidStats.numEJs));
            indentingPrintWriter.print("#reg", Integer.valueOf(uidStats.numRegular));
            indentingPrintWriter.print(
                    "earliestEnqueue", Long.valueOf(uidStats.earliestEnqueueTime));
            indentingPrintWriter.print(
                    "earliestEJEnqueue", Long.valueOf(uidStats.earliestEJEnqueueTime));
            indentingPrintWriter.print(
                    "earliestUIJEnqueue", Long.valueOf(uidStats.earliestUIJEnqueueTime));
            indentingPrintWriter.print("updated=");
            TimeUtils.formatDuration(
                    uidStats.lastUpdatedElapsed - elapsedRealtime, indentingPrintWriter);
            indentingPrintWriter.println("}");
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println();
        for (int i7 = 0; i7 < this.mTrackedJobs.size(); i7++) {
            ArraySet arraySet = (ArraySet) this.mTrackedJobs.valueAt(i7);
            for (int i8 = 0; i8 < arraySet.size(); i8++) {
                JobStatus jobStatus = (JobStatus) arraySet.valueAt(i8);
                if (jobSchedulerService$$ExternalSyntheticLambda5.test(jobStatus)) {
                    indentingPrintWriter.print("#");
                    jobStatus.printUniqueId(indentingPrintWriter);
                    indentingPrintWriter.print(" from ");
                    UserHandle.formatUid(indentingPrintWriter, jobStatus.sourceUid);
                    indentingPrintWriter.print(": ");
                    indentingPrintWriter.print(jobStatus.job.getRequiredNetwork());
                    indentingPrintWriter.println();
                }
            }
        }
    }

    @Override // com.android.server.job.controllers.StateController
    public final void dumpControllerStateLocked(
            ProtoOutputStream protoOutputStream,
            JobSchedulerService$$ExternalSyntheticLambda5
                    jobSchedulerService$$ExternalSyntheticLambda5) {
        long start = protoOutputStream.start(2246267895812L);
        long start2 = protoOutputStream.start(1146756268035L);
        for (int i = 0; i < this.mRequestedWhitelistJobs.size(); i++) {
            protoOutputStream.write(2220498092035L, this.mRequestedWhitelistJobs.keyAt(i));
        }
        for (int i2 = 0; i2 < this.mTrackedJobs.size(); i2++) {
            ArraySet arraySet = (ArraySet) this.mTrackedJobs.valueAt(i2);
            for (int i3 = 0; i3 < arraySet.size(); i3++) {
                JobStatus jobStatus = (JobStatus) arraySet.valueAt(i3);
                if (jobSchedulerService$$ExternalSyntheticLambda5.test(jobStatus)) {
                    long start3 = protoOutputStream.start(2246267895810L);
                    jobStatus.writeToShortProto(protoOutputStream, 1146756268033L);
                    protoOutputStream.write(1120986464258L, jobStatus.sourceUid);
                    protoOutputStream.end(start3);
                }
            }
        }
        protoOutputStream.end(start2);
        protoOutputStream.end(start);
    }

    @Override // com.android.server.job.controllers.StateController
    public final void evaluateStateLocked(JobStatus jobStatus) {
        if (jobStatus.hasConnectivityConstraint()) {
            UidStats uidStats = getUidStats(jobStatus.sourceUid, jobStatus.sourcePackageName, true);
            if (jobStatus.shouldTreatAsExpeditedJob()
                    || jobStatus.shouldTreatAsUserInitiatedJob()) {
                if (!jobStatus.isConstraintSatisfied(268435456)) {
                    updateConstraintsSatisfied(jobStatus);
                }
            } else if (((jobStatus.isRequestedExpeditedJob()
                                    && !jobStatus.shouldTreatAsExpeditedJob())
                            || (jobStatus.job.isUserInitiated()
                                    && !jobStatus.shouldTreatAsUserInitiatedJob()))
                    && jobStatus.isConstraintSatisfied(268435456)) {
                updateConstraintsSatisfied(jobStatus);
            }
            if (!wouldBeReadyWithConstraintLocked(jobStatus, 268435456)
                    || !isNetworkAvailable(jobStatus)) {
                if (DEBUG) {
                    Slog.i(
                            "JobScheduler.Connectivity",
                            "evaluateStateLocked finds job " + jobStatus + " would not be ready.");
                }
                maybeRevokeStandbyExceptionLocked(jobStatus);
                return;
            }
            if (DEBUG) {
                Slog.i(
                        "JobScheduler.Connectivity",
                        "evaluateStateLocked finds job " + jobStatus + " would be ready.");
            }
            uidStats.numReadyWithConnectivity++;
            requestStandbyExceptionLocked(jobStatus);
        }
    }

    public CcConfig getCcConfig() {
        return this.mCcConfig;
    }

    public final Network getNetworkLocked(JobStatus jobStatus) {
        UidDefaultNetworkCallback uidDefaultNetworkCallback =
                (UidDefaultNetworkCallback)
                        this.mCurrentDefaultNetworkCallbacks.get(jobStatus.sourceUid);
        if (uidDefaultNetworkCallback == null) {
            return null;
        }
        SparseArray sparseArray = this.mUidStats;
        int i = jobStatus.sourceUid;
        int i2 = -196680;
        if (((UidStats) sparseArray.get(i)).baseBias >= 30 || (jobStatus.job.getFlags() & 1) != 0) {
            if (DEBUG) {
                AnyMotionDetector$$ExternalSyntheticOutline0.m(
                        i, "Using FG bypass for ", "JobScheduler.Connectivity");
            }
        } else if (jobStatus.shouldTreatAsUserInitiatedJob()) {
            if (DEBUG) {
                AnyMotionDetector$$ExternalSyntheticOutline0.m(
                        i, "Using UI bypass for ", "JobScheduler.Connectivity");
            }
        } else if (jobStatus.shouldTreatAsExpeditedJob() || jobStatus.startedAsExpeditedJob) {
            if (DEBUG) {
                AnyMotionDetector$$ExternalSyntheticOutline0.m(
                        i, "Using EJ bypass for ", "JobScheduler.Connectivity");
            }
            i2 = -72;
        } else {
            if (DEBUG) {
                AnyMotionDetector$$ExternalSyntheticOutline0.m(
                        i, "Using BG bypass for ", "JobScheduler.Connectivity");
            }
            i2 = -65;
        }
        if ((uidDefaultNetworkCallback.mBlockedReasons & i2) == 0) {
            return uidDefaultNetworkCallback.mDefaultNetwork;
        }
        return null;
    }

    public final CachedNetworkMetadata getNetworkMetadata(Network network) {
        CachedNetworkMetadata cachedNetworkMetadata;
        if (network == null) {
            return null;
        }
        synchronized (this.mLock) {
            cachedNetworkMetadata = (CachedNetworkMetadata) this.mAvailableNetworks.get(network);
        }
        return cachedNetworkMetadata;
    }

    public final UidStats getUidStats(int i, String str, boolean z) {
        UidStats uidStats = (UidStats) this.mUidStats.get(i);
        if (uidStats != null) {
            return uidStats;
        }
        if (z) {
            Slog.wtfStack(
                    "JobScheduler.Connectivity",
                    "UidStats was null after job for " + str + " was registered");
        }
        UidStats uidStats2 = new UidStats(i);
        this.mUidStats.append(i, uidStats2);
        return uidStats2;
    }

    public final boolean isNetworkAvailable(JobStatus jobStatus) {
        synchronized (this.mLock) {
            for (int i = 0; i < this.mAvailableNetworks.size(); i++) {
                try {
                    Network network = (Network) this.mAvailableNetworks.keyAt(i);
                    CachedNetworkMetadata cachedNetworkMetadata =
                            (CachedNetworkMetadata) this.mAvailableNetworks.valueAt(i);
                    NetworkCapabilities networkCapabilities =
                            cachedNetworkMetadata == null
                                    ? null
                                    : cachedNetworkMetadata.networkCapabilities;
                    boolean isSatisfied =
                            isSatisfied(jobStatus, network, networkCapabilities, this.mConstants);
                    if (DEBUG) {
                        Slog.v(
                                "JobScheduler.Connectivity",
                                "isNetworkAvailable("
                                        + jobStatus
                                        + ") with network "
                                        + network
                                        + " and capabilities "
                                        + networkCapabilities
                                        + ". Satisfied="
                                        + isSatisfied);
                    }
                    if (isSatisfied) {
                        return true;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            return false;
        }
    }

    public boolean isNetworkInStateForJobRunLocked(Network network) {
        List underlyingNetworks;
        CachedNetworkMetadata cachedNetworkMetadata =
                (CachedNetworkMetadata) this.mAvailableNetworks.get(network);
        if (cachedNetworkMetadata == null) {
            return false;
        }
        JobSchedulerService.sElapsedRealtimeClock.getClass();
        long elapsedRealtime = SystemClock.elapsedRealtime();
        long j = cachedNetworkMetadata.defaultNetworkActivationLastConfirmedTimeElapsed;
        CcConfig ccConfig = this.mCcConfig;
        if (ccConfig.NETWORK_ACTIVATION_EXPIRATION_MS + j > elapsedRealtime) {
            return true;
        }
        long j2 = cachedNetworkMetadata.capabilitiesFirstAcquiredTimeElapsed;
        long j3 = elapsedRealtime - ccConfig.NETWORK_ACTIVATION_MAX_WAIT_TIME_MS;
        boolean z = j2 < j3 && j < j3;
        Network network2 = this.mSystemDefaultNetwork;
        if (network2 == null) {
            return z;
        }
        if (network2.equals(network)) {
            long j4 = cachedNetworkMetadata.defaultNetworkActivationLastCheckTimeElapsed;
            if (this.mCcConfig.NETWORK_ACTIVATION_EXPIRATION_MS + j4 >= elapsedRealtime) {
                return false;
            }
            if (j4 > cachedNetworkMetadata.defaultNetworkActivationLastConfirmedTimeElapsed) {
                return z;
            }
            cachedNetworkMetadata.defaultNetworkActivationLastCheckTimeElapsed = elapsedRealtime;
            if (!this.mConnManager.isDefaultNetworkActive()) {
                return z;
            }
            cachedNetworkMetadata.defaultNetworkActivationLastConfirmedTimeElapsed =
                    elapsedRealtime;
            return true;
        }
        NetworkCapabilities networkCapabilities = cachedNetworkMetadata.networkCapabilities;
        if (networkCapabilities == null
                || !networkCapabilities.hasTransport(4)
                || (underlyingNetworks = networkCapabilities.getUnderlyingNetworks()) == null) {
            return z;
        }
        if (!underlyingNetworks.contains(this.mSystemDefaultNetwork)) {
            for (int size = underlyingNetworks.size() - 1; size >= 0; size--) {
                if (isNetworkInStateForJobRunLocked((Network) underlyingNetworks.get(size))) {
                    return true;
                }
            }
            return z;
        }
        if (DEBUG) {
            Slog.i(
                    "JobScheduler.Connectivity",
                    "Substituting system default network "
                            + this.mSystemDefaultNetwork
                            + " for VPN "
                            + network);
        }
        return isNetworkInStateForJobRunLocked(this.mSystemDefaultNetwork);
    }

    /* JADX WARN: Code restructure failed: missing block: B:139:0x01bc, code lost:

       if (r8 != false) goto L63;
    */
    /* JADX WARN: Code restructure failed: missing block: B:145:0x01ee, code lost:

       if (r7 != false) goto L63;
    */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0235 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0237  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0252 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0254  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x030b A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:57:0x030d  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x03a7 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:86:0x03a8 A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean isSatisfied(
            com.android.server.job.controllers.JobStatus r19,
            android.net.Network r20,
            android.net.NetworkCapabilities r21,
            com.android.server.job.JobSchedulerService.Constants r22) {
        /*
            Method dump skipped, instructions count: 940
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.job.controllers.ConnectivityController.isSatisfied(com.android.server.job.controllers.JobStatus,"
                    + " android.net.Network, android.net.NetworkCapabilities,"
                    + " com.android.server.job.JobSchedulerService$Constants):boolean");
    }

    public boolean isStandbyExceptionRequestedLocked(int i) {
        ArraySet arraySet = (ArraySet) this.mRequestedWhitelistJobs.get(i);
        return arraySet != null && arraySet.size() > 0;
    }

    public void maybeRevokeStandbyExceptionLocked(JobStatus jobStatus) {
        int i = jobStatus.sourceUid;
        if (isStandbyExceptionRequestedLocked(i)) {
            ArraySet arraySet = (ArraySet) this.mRequestedWhitelistJobs.get(i);
            if (arraySet == null) {
                Slog.wtf(
                        "JobScheduler.Connectivity",
                        "maybeRevokeStandbyExceptionLocked found null jobs array even though a"
                            + " standby exception has been requested.");
                return;
            }
            if (arraySet.remove(jobStatus) && arraySet.size() <= 0) {
                if (DEBUG) {
                    HermesService$3$$ExternalSyntheticOutline0.m(
                            i, "Revoking standby exception for UID: ", "JobScheduler.Connectivity");
                }
                NetworkPolicyManagerService.this.setAppIdleWhitelist(i, false);
                this.mRequestedWhitelistJobs.remove(i);
                return;
            }
            if (DEBUG) {
                Slog.i(
                        "JobScheduler.Connectivity",
                        "maybeRevokeStandbyExceptionLocked not revoking because there are still "
                                + arraySet.size()
                                + " jobs left.");
            }
        }
    }

    @Override // com.android.server.job.controllers.StateController
    public final void maybeStartTrackingJobLocked(JobStatus jobStatus, JobStatus jobStatus2) {
        if (jobStatus.hasConnectivityConstraint()) {
            String str = jobStatus.sourcePackageName;
            int i = jobStatus.sourceUid;
            UidStats uidStats = getUidStats(i, str, false);
            if (wouldBeReadyWithConstraintLocked(jobStatus, 268435456)) {
                uidStats.numReadyWithConnectivity++;
            }
            ArraySet arraySet = (ArraySet) this.mTrackedJobs.get(i);
            if (arraySet == null) {
                arraySet = new ArraySet();
                this.mTrackedJobs.put(i, arraySet);
            }
            arraySet.add(jobStatus);
            jobStatus.setTrackingController(2);
            updateConstraintsSatisfied(jobStatus);
        }
    }

    @Override // com.android.server.job.controllers.StateController
    public final void maybeStopTrackingJobLocked(JobStatus jobStatus, JobStatus jobStatus2) {
        if (jobStatus.clearTrackingController(2)) {
            SparseArray sparseArray = this.mTrackedJobs;
            int i = jobStatus.sourceUid;
            ArraySet arraySet = (ArraySet) sparseArray.get(i);
            if (arraySet != null) {
                arraySet.remove(jobStatus);
            }
            UidStats uidStats = getUidStats(i, jobStatus.sourcePackageName, true);
            uidStats.numReadyWithConnectivity--;
            uidStats.runningJobs.remove(jobStatus);
            maybeRevokeStandbyExceptionLocked(jobStatus);
            postAdjustCallbacks();
        }
    }

    @Override // com.android.server.job.controllers.StateController
    public final void onAppRemovedLocked(int i, String str) {
        if (this.mService.getPackagesForUidLocked(i) == null) {
            this.mTrackedJobs.delete(i);
            this.mBackgroundMeteredAllowed.delete(i);
            UidStats uidStats = (UidStats) this.mUidStats.removeReturnOld(i);
            JobSchedulerService.sElapsedRealtimeClock.getClass();
            unregisterDefaultNetworkCallbackLocked(i, SystemClock.elapsedRealtime());
            ((ArrayList) this.mSortedStats).remove(uidStats);
            registerPendingUidCallbacksLocked();
        }
    }

    @Override // com.android.server.job.controllers.StateController
    public final void onBatteryStateChangedLocked() {
        this.mHandler.sendEmptyMessage(1);
    }

    @Override // com.android.server.job.controllers.StateController
    public final void onConstantsUpdatedLocked() {
        boolean z;
        if (!this.mCcConfig.mShouldReprocessNetworkCapabilities) {
            FlexibilityController flexibilityController = this.mFlexibilityController;
            synchronized (flexibilityController.mLock) {
                z = flexibilityController.mFlexibilityEnabled;
            }
            if (z == this.mCcConfig.mFlexIsEnabled) {
                return;
            }
        }
        AppSchedulingModuleThread.getHandler()
                .post(
                        new Runnable() { // from class:
                                         // com.android.server.job.controllers.ConnectivityController$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                ConnectivityController connectivityController =
                                        ConnectivityController.this;
                                synchronized (connectivityController.mLock) {
                                    boolean z2 = false;
                                    boolean z3 = false;
                                    for (int i = 0;
                                            i < connectivityController.mAvailableNetworks.size();
                                            i++) {
                                        try {
                                            ConnectivityController.CachedNetworkMetadata
                                                    cachedNetworkMetadata =
                                                            (ConnectivityController
                                                                            .CachedNetworkMetadata)
                                                                    connectivityController
                                                                            .mAvailableNetworks
                                                                            .valueAt(i);
                                            if (cachedNetworkMetadata != null) {
                                                if (connectivityController
                                                        .updateTransportAffinitySatisfaction(
                                                                cachedNetworkMetadata)) {
                                                    z2 = true;
                                                }
                                                z3 |=
                                                        cachedNetworkMetadata
                                                                .satisfiesTransportAffinities;
                                            }
                                        } catch (Throwable th) {
                                            throw th;
                                        }
                                    }
                                    if (z2) {
                                        FlexibilityController flexibilityController2 =
                                                connectivityController.mFlexibilityController;
                                        JobSchedulerService.sElapsedRealtimeClock.getClass();
                                        flexibilityController2.setConstraintSatisfied(
                                                268435456, SystemClock.elapsedRealtime(), z3);
                                        connectivityController.updateAllTrackedJobsLocked(false);
                                    }
                                }
                            }
                        });
    }

    @Override // android.net.ConnectivityManager.OnNetworkActiveListener
    public final void onNetworkActive() {
        synchronized (this.mLock) {
            try {
                Network network = this.mSystemDefaultNetwork;
                if (network == null) {
                    Slog.wtf(
                            "JobScheduler.Connectivity",
                            "System default network is unknown but active");
                    return;
                }
                CachedNetworkMetadata cachedNetworkMetadata =
                        (CachedNetworkMetadata) this.mAvailableNetworks.get(network);
                if (cachedNetworkMetadata == null) {
                    Slog.wtf(
                            "JobScheduler.Connectivity",
                            "System default network capabilities are unknown but active");
                    return;
                }
                JobSchedulerService.sElapsedRealtimeClock.getClass();
                long elapsedRealtime = SystemClock.elapsedRealtime();
                cachedNetworkMetadata.defaultNetworkActivationLastCheckTimeElapsed =
                        elapsedRealtime;
                cachedNetworkMetadata.defaultNetworkActivationLastConfirmedTimeElapsed =
                        elapsedRealtime;
                this.mHandler.sendEmptyMessage(4);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.job.controllers.StateController
    public final void onUidBiasChangedLocked(int i, int i2, int i3) {
        UidStats uidStats = (UidStats) this.mUidStats.get(i);
        if (uidStats == null || uidStats.baseBias == i3) {
            return;
        }
        uidStats.baseBias = i3;
        postAdjustCallbacks();
    }

    @Override // com.android.server.job.controllers.StateController
    public final void onUserRemovedLocked(int i) {
        JobSchedulerService.sElapsedRealtimeClock.getClass();
        long elapsedRealtime = SystemClock.elapsedRealtime();
        for (int size = this.mUidStats.size() - 1; size >= 0; size--) {
            UidStats uidStats = (UidStats) this.mUidStats.valueAt(size);
            if (UserHandle.getUserId(uidStats.uid) == i) {
                unregisterDefaultNetworkCallbackLocked(uidStats.uid, elapsedRealtime);
                ((ArrayList) this.mSortedStats).remove(uidStats);
                this.mUidStats.removeAt(size);
            }
        }
        for (int size2 = this.mBackgroundMeteredAllowed.size() - 1; size2 >= 0; size2--) {
            if (UserHandle.getUserId(this.mBackgroundMeteredAllowed.keyAt(size2)) == i) {
                this.mBackgroundMeteredAllowed.removeAt(size2);
            }
        }
        postAdjustCallbacks();
    }

    public final void postAdjustCallbacks() {
        this.mHandler.sendEmptyMessageDelayed(0, 0L);
    }

    @Override // com.android.server.job.controllers.StateController
    public final void prepareForExecutionLocked(JobStatus jobStatus) {
        if (jobStatus.hasConnectivityConstraint()) {
            getUidStats(jobStatus.sourceUid, jobStatus.sourcePackageName, true)
                    .runningJobs
                    .add(jobStatus);
        }
    }

    @Override // com.android.server.job.controllers.StateController
    public final void prepareForUpdatedConstantsLocked() {
        boolean z;
        CcConfig ccConfig = this.mCcConfig;
        ccConfig.mShouldReprocessNetworkCapabilities = false;
        FlexibilityController flexibilityController = this.mFlexibilityController;
        synchronized (flexibilityController.mLock) {
            z = flexibilityController.mFlexibilityEnabled;
        }
        ccConfig.mFlexIsEnabled = z;
    }

    @Override // com.android.server.job.controllers.StateController
    public final void processConstantLocked(DeviceConfig.Properties properties, String str) {
        CcConfig ccConfig;
        ccConfig = this.mCcConfig;
        ccConfig.getClass();
        switch (str) {
            case "conn_network_activation_expiration_ms":
                long j = properties.getLong(str, 10000L);
                if (ccConfig.NETWORK_ACTIVATION_EXPIRATION_MS != j) {
                    ccConfig.NETWORK_ACTIVATION_EXPIRATION_MS = j;
                    break;
                }
                break;
            case "conn_network_activation_max_wait_time_ms":
                long j2 = properties.getLong(str, 1860000L);
                if (ccConfig.NETWORK_ACTIVATION_MAX_WAIT_TIME_MS != j2) {
                    ccConfig.NETWORK_ACTIVATION_MAX_WAIT_TIME_MS = j2;
                    ccConfig.mShouldReprocessNetworkCapabilities = true;
                    break;
                }
                break;
            case "conn_avoid_undefined_transport_affinity":
                boolean z = properties.getBoolean(str, false);
                if (ccConfig.AVOID_UNDEFINED_TRANSPORT_AFFINITY != z) {
                    ccConfig.AVOID_UNDEFINED_TRANSPORT_AFFINITY = z;
                    ccConfig.mShouldReprocessNetworkCapabilities = true;
                    break;
                }
                break;
        }
    }

    @Override // com.android.server.job.controllers.StateController
    public final void reevaluateStateLocked(int i) {
        ArraySet arraySet = (ArraySet) this.mTrackedJobs.get(i);
        if (arraySet == null) {
            return;
        }
        for (int size = arraySet.size() - 1; size >= 0; size--) {
            evaluateStateLocked((JobStatus) arraySet.valueAt(size));
        }
    }

    public final void registerPendingUidCallbacksLocked() {
        int size = this.mCurrentDefaultNetworkCallbacks.size();
        int size2 = ((ArrayList) this.mSortedStats).size();
        if (size2 < size) {
            Slog.wtf(
                    "JobScheduler.Connectivity",
                    "There are more registered callbacks than sorted UIDs: "
                            + size
                            + " vs "
                            + size2);
        }
        while (size < size2 && size < 125) {
            UidStats uidStats = (UidStats) ((ArrayList) this.mSortedStats).get(size);
            UidDefaultNetworkCallback uidDefaultNetworkCallback =
                    (UidDefaultNetworkCallback) this.mDefaultNetworkCallbackPool.acquire();
            if (uidDefaultNetworkCallback == null) {
                uidDefaultNetworkCallback = new UidDefaultNetworkCallback();
            }
            int i = uidStats.uid;
            uidDefaultNetworkCallback.mUid = i;
            uidDefaultNetworkCallback.mDefaultNetwork = null;
            this.mCurrentDefaultNetworkCallbacks.append(i, uidDefaultNetworkCallback);
            this.mConnManager.registerDefaultNetworkCallbackForUid(
                    uidStats.uid, uidDefaultNetworkCallback, this.mHandler);
            size++;
        }
    }

    public void requestStandbyExceptionLocked(JobStatus jobStatus) {
        int i = jobStatus.sourceUid;
        boolean isStandbyExceptionRequestedLocked = isStandbyExceptionRequestedLocked(i);
        ArraySet arraySet = (ArraySet) this.mRequestedWhitelistJobs.get(i);
        if (arraySet == null) {
            arraySet = new ArraySet();
            this.mRequestedWhitelistJobs.put(i, arraySet);
        }
        if (!arraySet.add(jobStatus) || isStandbyExceptionRequestedLocked) {
            if (DEBUG) {
                Slog.i(
                        "JobScheduler.Connectivity",
                        "requestStandbyExceptionLocked found exception already requested.");
            }
        } else {
            if (DEBUG) {
                HermesService$3$$ExternalSyntheticOutline0.m(
                        i, "Requesting standby exception for UID: ", "JobScheduler.Connectivity");
            }
            NetworkPolicyManagerService.this.setAppIdleWhitelist(i, true);
        }
    }

    @Override // com.android.server.job.controllers.StateController
    public final void startTrackingLocked() {
        this.mConnManager.registerSystemDefaultNetworkCallback(
                this.mDefaultNetworkCallback, this.mHandler);
        this.mConnManager.addDefaultNetworkActiveListener(this);
    }

    @Override // com.android.server.job.controllers.RestrictingController
    public final void startTrackingRestrictedJobLocked(JobStatus jobStatus) {
        if (jobStatus.hasConnectivityConstraint()) {
            updateConstraintsSatisfied(jobStatus);
        }
    }

    @Override // com.android.server.job.controllers.RestrictingController
    public final void stopTrackingRestrictedJobLocked(JobStatus jobStatus) {
        if (jobStatus.hasConnectivityConstraint()) {
            updateConstraintsSatisfied(jobStatus);
        }
    }

    @Override // com.android.server.job.controllers.StateController
    public final void unprepareFromExecutionLocked(JobStatus jobStatus) {
        if (jobStatus.hasConnectivityConstraint()) {
            getUidStats(jobStatus.sourceUid, jobStatus.sourcePackageName, true)
                    .runningJobs
                    .remove(jobStatus);
            postAdjustCallbacks();
        }
    }

    public final boolean unregisterDefaultNetworkCallbackLocked(int i, long j) {
        UidDefaultNetworkCallback uidDefaultNetworkCallback =
                (UidDefaultNetworkCallback) this.mCurrentDefaultNetworkCallbacks.get(i);
        boolean z = false;
        if (uidDefaultNetworkCallback == null) {
            return false;
        }
        this.mCurrentDefaultNetworkCallbacks.remove(i);
        this.mConnManager.unregisterNetworkCallback(uidDefaultNetworkCallback);
        this.mDefaultNetworkCallbackPool.release(uidDefaultNetworkCallback);
        uidDefaultNetworkCallback.mDefaultNetwork = null;
        uidDefaultNetworkCallback.mUid = -10000;
        ArraySet arraySet = (ArraySet) this.mTrackedJobs.get(i);
        if (arraySet != null) {
            for (int size = arraySet.size() - 1; size >= 0; size--) {
                z |= updateConstraintsSatisfied((JobStatus) arraySet.valueAt(size), j, null, null);
            }
        }
        return z;
    }

    public final void updateAllTrackedJobsLocked(boolean z) {
        if (z) {
            long j =
                    this.mLastAllJobUpdateTimeElapsed
                            + this.mConstants.CONN_UPDATE_ALL_JOBS_MIN_INTERVAL_MS;
            JobSchedulerService.sElapsedRealtimeClock.getClass();
            long elapsedRealtime = j - SystemClock.elapsedRealtime();
            if (elapsedRealtime > 0) {
                this.mHandler.sendMessageDelayed(
                        this.mHandler.obtainMessage(1, 1, 0), elapsedRealtime);
                return;
            }
        }
        this.mHandler.removeMessages(1);
        updateTrackedJobsLocked((Network) null, -1);
        JobSchedulerService.sElapsedRealtimeClock.getClass();
        this.mLastAllJobUpdateTimeElapsed = SystemClock.elapsedRealtime();
    }

    public final void updateConstraintsSatisfied(JobStatus jobStatus) {
        JobSchedulerService.sElapsedRealtimeClock.getClass();
        long elapsedRealtime = SystemClock.elapsedRealtime();
        if (((UidDefaultNetworkCallback)
                        this.mCurrentDefaultNetworkCallbacks.get(jobStatus.sourceUid))
                != null) {
            Network networkLocked = getNetworkLocked(jobStatus);
            updateConstraintsSatisfied(
                    jobStatus, elapsedRealtime, networkLocked, getNetworkMetadata(networkLocked));
            return;
        }
        SparseArray sparseArray = this.mCurrentDefaultNetworkCallbacks;
        int i = jobStatus.sourceUid;
        if (!sparseArray.contains(i)) {
            UidStats uidStats = getUidStats(i, jobStatus.sourcePackageName, true);
            if (!((ArrayList) this.mSortedStats).contains(uidStats)) {
                ((ArrayList) this.mSortedStats).add(uidStats);
            }
            if (this.mCurrentDefaultNetworkCallbacks.size() >= 125) {
                postAdjustCallbacks();
            } else {
                registerPendingUidCallbacksLocked();
            }
        }
        updateConstraintsSatisfied(jobStatus, elapsedRealtime, null, null);
    }

    public final boolean updateConstraintsSatisfied(
            JobStatus jobStatus,
            long j,
            Network network,
            CachedNetworkMetadata cachedNetworkMetadata) {
        NetworkCapabilities networkCapabilities =
                cachedNetworkMetadata == null ? null : cachedNetworkMetadata.networkCapabilities;
        boolean isSatisfied = isSatisfied(jobStatus, network, networkCapabilities, this.mConstants);
        boolean z = false;
        if (!isSatisfied
                && jobStatus.network != null
                && this.mService.isCurrentlyRunningLocked(jobStatus)) {
            Network network2 = jobStatus.network;
            CachedNetworkMetadata networkMetadata = getNetworkMetadata(network2);
            if (isSatisfied(
                    jobStatus,
                    network2,
                    networkMetadata != null ? networkMetadata.networkCapabilities : null,
                    this.mConstants)) {
                if (DEBUG) {
                    Slog.i(
                            "JobScheduler.Connectivity",
                            "Not reassigning network from "
                                    + jobStatus.network
                                    + " to "
                                    + network
                                    + " for running job "
                                    + jobStatus);
                }
                return false;
            }
        }
        boolean constraintSatisfied = jobStatus.setConstraintSatisfied(268435456, j, isSatisfied);
        jobStatus.mTransportAffinitiesSatisfied =
                isSatisfied
                        && cachedNetworkMetadata != null
                        && cachedNetworkMetadata.satisfiesTransportAffinities;
        if (jobStatus.mCanApplyTransportAffinities) {
            jobStatus.setConstraintSatisfied(
                    2097152,
                    j,
                    this.mFlexibilityController.isFlexibilitySatisfiedLocked(jobStatus));
        }
        if (!constraintSatisfied
                && isSatisfied
                && jobStatus.network != null
                && this.mService.isCurrentlyRunningLocked(jobStatus)) {
            JobSchedulerService jobSchedulerService = this.mStateChangedListener;
            synchronized (jobSchedulerService.mLock) {
                try {
                    JobServiceContext runningJobServiceContextLocked =
                            jobSchedulerService.mConcurrencyManager
                                    .getRunningJobServiceContextLocked(jobStatus);
                    if (runningJobServiceContextLocked != null) {
                        runningJobServiceContextLocked.informOfNetworkChangeLocked(network);
                    }
                } finally {
                }
            }
        }
        jobStatus.network = network;
        if (DEBUG) {
            StringBuilder sb = new StringBuilder("Connectivity ");
            sb.append(constraintSatisfied ? "CHANGED" : "unchanged");
            sb.append(" for ");
            sb.append(jobStatus);
            sb.append(": usable=");
            if (networkCapabilities != null && networkCapabilities.hasCapability(21)) {
                z = true;
            }
            sb.append(z);
            sb.append(" satisfied=");
            sb.append(isSatisfied);
            Slog.i("JobScheduler.Connectivity", sb.toString());
        }
        return constraintSatisfied;
    }

    public final void updateTrackedJobsLocked(Network network, int i) {
        ArraySet arraySet;
        if (i == -1) {
            arraySet = new ArraySet();
            for (int size = this.mTrackedJobs.size() - 1; size >= 0; size--) {
                if (updateTrackedJobsLocked((ArraySet) this.mTrackedJobs.valueAt(size), network)) {
                    arraySet.addAll((ArraySet) this.mTrackedJobs.valueAt(size));
                }
            }
        } else {
            arraySet =
                    updateTrackedJobsLocked((ArraySet) this.mTrackedJobs.get(i), network)
                            ? (ArraySet) this.mTrackedJobs.get(i)
                            : null;
        }
        if (arraySet == null || arraySet.size() <= 0) {
            return;
        }
        this.mStateChangedListener.onControllerStateChanged(arraySet);
    }

    public final boolean updateTrackedJobsLocked(ArraySet arraySet, Network network) {
        boolean z = false;
        if (arraySet != null && arraySet.size() != 0) {
            if (((UidDefaultNetworkCallback)
                            this.mCurrentDefaultNetworkCallbacks.get(
                                    ((JobStatus) arraySet.valueAt(0)).sourceUid))
                    == null) {
                return false;
            }
            JobSchedulerService.sElapsedRealtimeClock.getClass();
            long elapsedRealtime = SystemClock.elapsedRealtime();
            for (int size = arraySet.size() - 1; size >= 0; size--) {
                JobStatus jobStatus = (JobStatus) arraySet.valueAt(size);
                Network networkLocked = getNetworkLocked(jobStatus);
                if (network == null
                        || network.equals(networkLocked)
                        || !Objects.equals(jobStatus.network, networkLocked)) {
                    z |=
                            updateConstraintsSatisfied(
                                    jobStatus,
                                    elapsedRealtime,
                                    networkLocked,
                                    getNetworkMetadata(networkLocked));
                }
            }
        }
        return z;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0066  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0069 A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean updateTransportAffinitySatisfaction(
            com.android.server.job.controllers.ConnectivityController.CachedNetworkMetadata r11) {
        /*
            r10 = this;
            android.net.NetworkCapabilities r0 = r11.networkCapabilities
            com.android.server.job.controllers.FlexibilityController r1 = r10.mFlexibilityController
            java.lang.Object r2 = r1.mLock
            monitor-enter(r2)
            boolean r1 = r1.mFlexibilityEnabled     // Catch: java.lang.Throwable -> L6a
            monitor-exit(r2)     // Catch: java.lang.Throwable -> L6a
            r2 = 1
            r3 = 0
            if (r1 != 0) goto L10
        Le:
            r10 = r2
            goto L62
        L10:
            java.lang.String r1 = "JobScheduler.Connectivity"
            if (r0 != 0) goto L1f
            java.lang.String r0 = "Network constraint satisfied with null capabilities"
            android.util.Slog.wtf(r1, r0)
            com.android.server.job.controllers.ConnectivityController$CcConfig r10 = r10.mCcConfig
            boolean r10 = r10.AVOID_UNDEFINED_TRANSPORT_AFFINITY
        L1d:
            r10 = r10 ^ r2
            goto L62
        L1f:
            android.util.SparseIntArray r4 = com.android.server.job.controllers.ConnectivityController.sNetworkTransportAffinities
            int r4 = r4.size()
            if (r4 != 0) goto L2c
            com.android.server.job.controllers.ConnectivityController$CcConfig r10 = r10.mCcConfig
            boolean r10 = r10.AVOID_UNDEFINED_TRANSPORT_AFFINITY
            goto L1d
        L2c:
            int[] r0 = r0.getTransportTypes()
            int r4 = r0.length
            if (r4 != 0) goto L38
            com.android.server.job.controllers.ConnectivityController$CcConfig r10 = r10.mCcConfig
            boolean r10 = r10.AVOID_UNDEFINED_TRANSPORT_AFFINITY
            goto L1d
        L38:
            int r4 = r0.length
            r5 = r3
        L3a:
            if (r5 >= r4) goto Le
            r6 = r0[r5]
            android.util.SparseIntArray r7 = com.android.server.job.controllers.ConnectivityController.sNetworkTransportAffinities
            int r7 = r7.get(r6, r3)
            boolean r8 = com.android.server.job.controllers.ConnectivityController.DEBUG
            if (r8 == 0) goto L50
            java.lang.String r8 = "satisfiesTransportAffinities transport="
            java.lang.String r9 = " aff="
            com.android.server.asks.ASKSManagerService$$ExternalSyntheticOutline0.m(r6, r7, r8, r9, r1)
        L50:
            if (r7 == 0) goto L58
            r6 = 2
            if (r7 == r6) goto L56
            goto L5f
        L56:
            r10 = r3
            goto L62
        L58:
            com.android.server.job.controllers.ConnectivityController$CcConfig r6 = r10.mCcConfig
            boolean r6 = r6.AVOID_UNDEFINED_TRANSPORT_AFFINITY
            if (r6 == 0) goto L5f
            goto L56
        L5f:
            int r5 = r5 + 1
            goto L3a
        L62:
            boolean r0 = r11.satisfiesTransportAffinities
            if (r0 == r10) goto L69
            r11.satisfiesTransportAffinities = r10
            return r2
        L69:
            return r3
        L6a:
            r10 = move-exception
            monitor-exit(r2)     // Catch: java.lang.Throwable -> L6a
            throw r10
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.job.controllers.ConnectivityController.updateTransportAffinitySatisfaction(com.android.server.job.controllers.ConnectivityController$CachedNetworkMetadata):boolean");
    }
}
