package com.android.server.stats.pull;

import android.app.ActivityManager;
import android.app.usage.NetworkStatsManager;
import android.net.NetworkStats;
import android.os.Handler;
import android.util.ArrayMap;
import android.util.SparseIntArray;

import com.android.internal.util.FrameworkStatsLog;
import com.android.server.KnoxCaptureInputFilter$$ExternalSyntheticOutline0;
import com.android.server.selinux.RateLimiter;

import java.time.Duration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class AggregatedMobileDataStatsPuller {
    public final Handler mMobileDataStatsHandler;
    public final NetworkStatsManager mNetworkStatsManager;
    public final Object mLock = new Object();
    public NetworkStats mLastMobileUidStats = new NetworkStats(0, -1);
    public final RateLimiter mRateLimiter = new RateLimiter(Duration.ofSeconds(1));
    public final Map mUidStats = new ArrayMap();
    public final SparseIntArray mUidPreviousState = new SparseIntArray();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class MobileDataStats {
        public long mRxBytes;
        public long mRxPackets;
        public long mTxBytes;
        public long mTxPackets;
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class UidProcState {
        public final int mState;
        public final int mUid;

        public UidProcState(int i, int i2) {
            this.mUid = i;
            this.mState = i2;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof UidProcState)) {
                return false;
            }
            UidProcState uidProcState = (UidProcState) obj;
            return this.mUid == uidProcState.mUid && this.mState == uidProcState.mState;
        }

        public final int hashCode() {
            return (this.mUid * 31) + this.mState;
        }
    }

    public AggregatedMobileDataStatsPuller(NetworkStatsManager networkStatsManager) {
        this.mNetworkStatsManager = networkStatsManager;
        Handler handler =
                new Handler(
                        KnoxCaptureInputFilter$$ExternalSyntheticOutline0.m(
                                        "MobileDataStatsHandler")
                                .getLooper());
        this.mMobileDataStatsHandler = handler;
        if (networkStatsManager != null) {
            handler.post(
                    new Runnable() { // from class:
                                     // com.android.server.stats.pull.AggregatedMobileDataStatsPuller$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            AggregatedMobileDataStatsPuller aggregatedMobileDataStatsPuller =
                                    AggregatedMobileDataStatsPuller.this;
                            aggregatedMobileDataStatsPuller.updateNetworkStats(
                                    aggregatedMobileDataStatsPuller.mNetworkStatsManager);
                        }
                    });
        }
    }

    public static boolean isEmpty(NetworkStats networkStats) {
        long j;
        long j2;
        Iterator it = networkStats.iterator();
        while (it.hasNext()) {
            NetworkStats.Entry entry = (NetworkStats.Entry) it.next();
            if (entry.getRxPackets() != 0 || entry.getTxPackets() != 0) {
                j = entry.getRxPackets();
                j2 = entry.getTxPackets();
                break;
            }
        }
        j = 0;
        j2 = 0;
        return j + j2 == 0;
    }

    public final MobileDataStats getUidStatsForPreviousStateLocked(int i) {
        UidProcState uidProcState = new UidProcState(i, this.mUidPreviousState.get(i, -1));
        if (((ArrayMap) this.mUidStats).containsKey(uidProcState)) {
            return (MobileDataStats) ((ArrayMap) this.mUidStats).get(uidProcState);
        }
        if (((ArrayMap) this.mUidStats).size() >= 3000) {
            return null;
        }
        MobileDataStats mobileDataStats = new MobileDataStats();
        mobileDataStats.mRxPackets = 0L;
        mobileDataStats.mTxPackets = 0L;
        mobileDataStats.mRxBytes = 0L;
        mobileDataStats.mTxBytes = 0L;
        ((ArrayMap) this.mUidStats).put(uidProcState, mobileDataStats);
        return mobileDataStats;
    }

    public final void pullDataBytesTransfer(List list) {
        synchronized (this.mLock) {
            pullDataBytesTransferLocked(list);
        }
    }

    public final void pullDataBytesTransferLocked(List list) {
        for (Map.Entry entry : ((ArrayMap) this.mUidStats).entrySet()) {
            MobileDataStats mobileDataStats = (MobileDataStats) entry.getValue();
            if (mobileDataStats.mRxPackets != 0
                    || mobileDataStats.mTxPackets != 0
                    || mobileDataStats.mRxBytes != 0
                    || mobileDataStats.mTxBytes != 0) {
                MobileDataStats mobileDataStats2 = (MobileDataStats) entry.getValue();
                list.add(
                        FrameworkStatsLog.buildStatsEvent(
                                FrameworkStatsLog.MOBILE_BYTES_TRANSFER_BY_PROC_STATE,
                                ((UidProcState) entry.getKey()).mUid,
                                ActivityManager.processStateAmToProto(
                                        ((UidProcState) entry.getKey()).mState),
                                mobileDataStats2.mRxBytes,
                                mobileDataStats2.mRxPackets,
                                mobileDataStats2.mTxBytes,
                                mobileDataStats2.mTxPackets));
            }
        }
    }

    public final void updateNetworkStats(NetworkStatsManager networkStatsManager) {
        NetworkStats mobileUidStats = networkStatsManager.getMobileUidStats();
        if (isEmpty(mobileUidStats)) {
            return;
        }
        NetworkStats subtract = mobileUidStats.subtract(this.mLastMobileUidStats);
        this.mLastMobileUidStats = mobileUidStats;
        if (isEmpty(subtract)) {
            return;
        }
        synchronized (this.mLock) {
            try {
                Iterator it = subtract.iterator();
                while (it.hasNext()) {
                    NetworkStats.Entry entry = (NetworkStats.Entry) it.next();
                    if (entry.getRxPackets() != 0 || entry.getTxPackets() != 0) {
                        MobileDataStats uidStatsForPreviousStateLocked =
                                getUidStatsForPreviousStateLocked(entry.getUid());
                        if (uidStatsForPreviousStateLocked != null) {
                            uidStatsForPreviousStateLocked.mTxBytes += entry.getTxBytes();
                            uidStatsForPreviousStateLocked.mRxBytes += entry.getRxBytes();
                            uidStatsForPreviousStateLocked.mTxPackets += entry.getTxPackets();
                            uidStatsForPreviousStateLocked.mRxPackets += entry.getRxPackets();
                        }
                    }
                }
            } finally {
            }
        }
    }
}
